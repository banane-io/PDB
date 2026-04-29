using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PDB.Migrations
{
    public partial class SeedMapData : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            var csvPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "data", "map.csv");
            var lines = File.ReadAllLines(csvPath).Skip(1); // skip header

            var values = lines.Select(line =>
            {
                var cols = line.Split(',');
                return $"({cols[0]},{cols[1]},'{cols[2]}',{cols[3]})";
            });

            migrationBuilder.Sql(
                $"INSERT INTO \"MapPoints\" (\"X\", \"Y\", \"Zone\", \"Terrain\") VALUES {string.Join(",", values)};"
            );
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
        }
    }
}
