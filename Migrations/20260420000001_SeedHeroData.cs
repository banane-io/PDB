using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace PDB.Migrations
{
    public partial class SeedHeroData : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.Sql(
                """
                INSERT INTO "Heroes" ("Name", "CurrentZoneId", "Strength", "Intelligence", "Agility", "Hp", "Mana", "Wood", "Stone")
                SELECT name, mp."Id", 5, 5, 5, 50, 50, 50, 50
                FROM (VALUES ('test_hero'), ('test_hero2'), ('test_hero3'), ('test_hero4')) AS h(name)
                CROSS JOIN (SELECT "Id" FROM "MapPoints" WHERE "X" = 0 AND "Y" = 0 LIMIT 1) AS mp;
                """
            );
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.Sql(
                """
                DELETE FROM "Heroes" WHERE "Name" IN ('test_hero', 'test_hero2', 'test_hero3', 'test_hero4');
                """
            );
        }
    }
}
