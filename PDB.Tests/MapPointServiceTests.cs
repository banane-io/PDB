using Microsoft.EntityFrameworkCore;
using PDB;
using PDB.Models;
using PDB.Services;

namespace PDB.Tests;

public class MapPointServiceTests
{
    private static ApplicationContext CreateContext(string dbName)
    {
        var options = new DbContextOptionsBuilder<ApplicationContext>()
            .UseInMemoryDatabase(dbName)
            .Options;
        return new ApplicationContext(options);
    }

    private static MapPoint MakePoint(int x, int y) =>
        new() { X = x, Y = y, Zone = "Test", Terrain = Terrain.Plain };

    // Seeds a full 25x25 grid (0-24 on both axes)
    private static async Task SeedFullGrid(ApplicationContext context)
    {
        for (int x = 0; x <= 24; x++)
            for (int y = 0; y <= 24; y++)
                context.MapPoints.Add(MakePoint(x, y));
        await context.SaveChangesAsync();
    }

    // ── GetMapPoint ────────────────────────────────────────────────────────────

    [Fact]
    public async Task GetMapPoint_ExistingId_ReturnsMapPoint()
    {
        await using var context = CreateContext(nameof(GetMapPoint_ExistingId_ReturnsMapPoint));
        context.MapPoints.Add(MakePoint(5, 5));
        await context.SaveChangesAsync();
        var id = context.MapPoints.Single().Id;

        var service = new MapPointService(context);
        var result = await service.GetMapPoint(id);

        Assert.NotNull(result);
        Assert.Equal(5, result.X);
        Assert.Equal(5, result.Y);
    }

    [Fact]
    public async Task GetMapPoint_NonExistingId_ReturnsNull()
    {
        await using var context = CreateContext(nameof(GetMapPoint_NonExistingId_ReturnsNull));
        var service = new MapPointService(context);

        var result = await service.GetMapPoint(999);

        Assert.Null(result);
    }

    // ── LoadGrid ───────────────────────────────────────────────────────────────

    [Fact]
    public async Task LoadGrid_CenterInMiddle_Returns11x11Grid()
    {
        await using var context = CreateContext(nameof(LoadGrid_CenterInMiddle_Returns11x11Grid));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var grid = await service.LoadGrid(MakePoint(12, 12));
        var columns = grid.ToList();

        Assert.Equal(11, columns.Count);
        Assert.All(columns, col => Assert.Equal(11, col.Count()));
    }

    [Fact]
    public async Task LoadGrid_CenterAtOrigin_ClampsToMapBounds()
    {
        await using var context = CreateContext(nameof(LoadGrid_CenterAtOrigin_ClampsToMapBounds));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var grid = await service.LoadGrid(MakePoint(0, 0));
        var allPoints = grid.SelectMany(col => col).ToList();

        Assert.All(allPoints, p => Assert.True(p.X >= 0 && p.Y >= 0));
        Assert.True(allPoints.Count > 0);
    }

    [Fact]
    public async Task LoadGrid_CenterAtMaxCorner_ClampsToMapBounds()
    {
        await using var context = CreateContext(nameof(LoadGrid_CenterAtMaxCorner_ClampsToMapBounds));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var grid = await service.LoadGrid(MakePoint(24, 24));
        var allPoints = grid.SelectMany(col => col).ToList();

        Assert.All(allPoints, p => Assert.True(p.X <= 24 && p.Y <= 24));
        Assert.True(allPoints.Count > 0);
    }

    [Fact]
    public async Task LoadGrid_GroupedByX_EachColumnHasSameX()
    {
        await using var context = CreateContext(nameof(LoadGrid_GroupedByX_EachColumnHasSameX));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var grid = await service.LoadGrid(MakePoint(12, 12));

        foreach (var column in grid)
        {
            var xs = column.Select(p => p.X).Distinct().ToList();
            Assert.Single(xs);
        }
    }

    [Fact]
    public async Task LoadGrid_PointsAreOrdered_AscendingXThenY()
    {
        await using var context = CreateContext(nameof(LoadGrid_PointsAreOrdered_AscendingXThenY));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var grid = await service.LoadGrid(MakePoint(12, 12));
        var columns = grid.ToList();

        // X groups are ascending
        var xValues = columns.Select(col => col.First().X).ToList();
        Assert.Equal(xValues.OrderBy(x => x).ToList(), xValues);

        // Y values within each group are ascending
        foreach (var column in columns)
        {
            var yValues = column.Select(p => p.Y).ToList();
            Assert.Equal(yValues.OrderBy(y => y).ToList(), yValues);
        }
    }

    // ── Neighbors ─────────────────────────────────────────────────────────────

    [Fact]
    public async Task Neighbors_CenterInMiddle_Returns8Neighbors()
    {
        await using var context = CreateContext(nameof(Neighbors_CenterInMiddle_Returns8Neighbors));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var result = await service.Neighbors(MakePoint(12, 12));

        Assert.Equal(8, result.Count);
    }

    [Fact]
    public async Task Neighbors_CornerOrigin_Returns3Neighbors()
    {
        await using var context = CreateContext(nameof(Neighbors_CornerOrigin_Returns3Neighbors));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var result = await service.Neighbors(MakePoint(0, 0));

        Assert.Equal(3, result.Count);
    }

    [Fact]
    public async Task Neighbors_EdgeLeft_Returns5Neighbors()
    {
        await using var context = CreateContext(nameof(Neighbors_EdgeLeft_Returns5Neighbors));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var result = await service.Neighbors(MakePoint(0, 12));

        Assert.Equal(5, result.Count);
    }

    [Fact]
    public async Task Neighbors_DirectionsAreCorrect()
    {
        await using var context = CreateContext(nameof(Neighbors_DirectionsAreCorrect));
        await SeedFullGrid(context);
        var service = new MapPointService(context);

        var result = await service.Neighbors(MakePoint(12, 12));

        foreach (var (direction, point) in result)
        {
            Assert.Equal(12 + direction.DeltaX, point.X);
            Assert.Equal(12 + direction.DeltaY, point.Y);
        }
    }

    [Fact]
    public async Task Neighbors_NoSurroundingPoints_ReturnsEmptyList()
    {
        await using var context = CreateContext(nameof(Neighbors_NoSurroundingPoints_ReturnsEmptyList));
        // Only seed the center point itself, no neighbors
        context.MapPoints.Add(MakePoint(12, 12));
        await context.SaveChangesAsync();
        var service = new MapPointService(context);

        var result = await service.Neighbors(MakePoint(12, 12));

        Assert.Empty(result);
    }
}
