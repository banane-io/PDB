using System.Net;
using System.Net.Http.Json;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc.Testing;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using PDB;
using PDB.Models;

namespace PDB.Tests;

public class MapControllerIntegrationTests : IClassFixture<MapControllerIntegrationTests.PdbFactory>
{
    private readonly HttpClient _client;
    private readonly PdbFactory _factory;

    public MapControllerIntegrationTests(PdbFactory factory)
    {
        _factory = factory;
        _client = factory.CreateClient();
    }

    // ── GetZone ────────────────────────────────────────────────────────────────

    [Fact]
    public async Task GetZone_ExistingId_Returns200WithMapPoint()
    {
        var id = await _factory.SeedPointAsync(5, 5);

        var response = await _client.GetAsync($"/api/map/{id}");

        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        var body = await response.Content.ReadFromJsonAsync<MapPoint>();
        Assert.NotNull(body);
        Assert.Equal(5, body.X);
        Assert.Equal(5, body.Y);
    }

    [Fact]
    public async Task GetZone_UnknownId_Returns404()
    {
        var response = await _client.GetAsync("/api/map/999999");

        Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
    }

    // ── LoadGrid ───────────────────────────────────────────────────────────────

    [Fact]
    public async Task LoadGrid_Returns200WithNonEmptyArray()
    {
        await _factory.SeedPointAsync(0, 0);

        var response = await _client.GetAsync("/api/map");

        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        var body = await response.Content.ReadFromJsonAsync<List<List<MapPoint>>>();
        Assert.NotNull(body);
        Assert.NotEmpty(body);
    }

    // ── Neighbors ─────────────────────────────────────────────────────────────

    [Fact]
    public async Task Neighbors_UnknownId_Returns404()
    {
        var response = await _client.GetAsync("/api/map/999999/neighbors");

        Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
    }

    [Fact]
    public async Task Neighbors_ExistingPointWithNeighbors_Returns200WithCorrectShape()
    {
        var centerId = await _factory.SeedPointAsync(12, 12);
        // Seed one known neighbor (North: same X, Y+1)
        await _factory.SeedPointAsync(12, 13);

        var response = await _client.GetAsync($"/api/map/{centerId}/neighbors");

        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        var body = await response.Content.ReadFromJsonAsync<List<NeighborResponse>>();
        Assert.NotNull(body);
        Assert.NotEmpty(body);
        // Verify Direction and MapPoint fields are serialized (regression for ValueTuple bug)
        var north = body.Single(n => n.MapPoint.X == 12 && n.MapPoint.Y == 13);
        Assert.Equal(0, north.Direction.DeltaX);
        Assert.Equal(1, north.Direction.DeltaY);
    }

    [Fact]
    public async Task Neighbors_ExistingPointNoNeighbors_Returns200WithEmptyList()
    {
        var id = await _factory.SeedPointAsync(3, 3);

        var response = await _client.GetAsync($"/api/map/{id}/neighbors");

        Assert.Equal(HttpStatusCode.OK, response.StatusCode);
        var body = await response.Content.ReadFromJsonAsync<List<NeighborResponse>>();
        Assert.NotNull(body);
        Assert.Empty(body);
    }

    [Fact]
    public async Task Neighbors_OldRouteWithoutId_Returns404()
    {
        var response = await _client.GetAsync("/api/map/neighbors");

        Assert.Equal(HttpStatusCode.NotFound, response.StatusCode);
    }

    // ── DTOs for deserialization ───────────────────────────────────────────────

    private record DirectionDto(int DeltaX, int DeltaY);
    private record NeighborResponse(DirectionDto Direction, MapPoint MapPoint);

    // ── Test factory ───────────────────────────────────────────────────────────

    public class PdbFactory : WebApplicationFactory<Program>
    {
        private readonly string _dbName = Guid.NewGuid().ToString();

        protected override void ConfigureWebHost(IWebHostBuilder builder)
        {
            builder.UseEnvironment("Testing");
            builder.ConfigureServices(services =>
            {
                // Remove all DbContext-related registrations so no two providers coexist
                var toRemove = services
                    .Where(d =>
                        d.ServiceType == typeof(ApplicationContext) ||
                        d.ServiceType == typeof(DbContextOptions<ApplicationContext>) ||
                        d.ServiceType == typeof(DbContextOptions))
                    .ToList();
                foreach (var d in toRemove)
                    services.Remove(d);

                // Register directly as a factory to bypass AddDbContext's TryAdd behavior
                var options = new DbContextOptionsBuilder<ApplicationContext>()
                    .UseInMemoryDatabase(_dbName)
                    .Options;
                services.AddScoped<ApplicationContext>(_ => new ApplicationContext(options));
            });
        }

        public async Task<long> SeedPointAsync(int x, int y)
        {
            using var scope = Services.CreateScope();
            var context = scope.ServiceProvider.GetRequiredService<ApplicationContext>();
            // Avoid duplicate seeds across tests sharing the factory
            var existing = await context.MapPoints.FirstOrDefaultAsync(p => p.X == x && p.Y == y);
            if (existing != null) return existing.Id;
            var point = new MapPoint { X = x, Y = y, Zone = "Test", Terrain = Terrain.Plain };
            context.MapPoints.Add(point);
            await context.SaveChangesAsync();
            return point.Id;
        }
    }
}
