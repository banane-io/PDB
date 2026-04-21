# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
dotnet build                    # Build project
dotnet run                      # Run development server (localhost:5079 HTTP, localhost:7079 HTTPS)
dotnet clean                    # Clean build artifacts
dotnet ef database update       # Apply pending migrations
dotnet ef migrations add <Name> # Create a new migration
docker compose up               # Run app + PostgreSQL via Docker
```

Swagger UI is available at `/swagger` when running in Development mode.

## Architecture

**PDB** is an ASP.NET Core 9 Web API backend for a text-based browser RPG (25×25 grid world). The frontend lives in a separate repository (`pdb-frontend`).

**Stack:** C# / ASP.NET Core 9 / Entity Framework Core 9 / PostgreSQL 17

**Layer structure:**
- `Controllers/` — API endpoints (thin, delegate to services)
- `Services/` — Business logic (e.g., `MapPointService` handles grid loading with 5-cell radius, neighbor calculation via `Direction` offsets)
- `Models/` — Domain entities (`MapPoint`, `Hero`, `Base`) and enums (`Direction`, `Terrain`, `Action`)
- `ApplicationContext.cs` — EF Core DbContext with `MapPoints`, `Heroes`, `Bases` DbSets
- `Migrations/` — EF Core migrations
- `Program.cs` — Service registration, CORS (AllowAllOrigins)

**Game world:** Coordinates 0–24 on both axes. `Direction` enum encodes 8-directional offsets (N, NE, E, SE, S, SW, W, NW). Map loading fetches a 5-cell radius grid around a center point.

## Database

Requires PostgreSQL. For local dev without Docker:
```sql
CREATE DATABASE pdb;
CREATE USER pdb WITH ENCRYPTED PASSWORD 'password1';
GRANT ALL PRIVILEGES ON DATABASE pdb TO pdb;
```

Connection string is in `appsettings.json`. Local secrets/overrides go in `appsettings.local.json` (gitignored pattern).

## Docker

`compose.yaml` runs both the app (port 5001) and PostgreSQL (port 5432). Migrations are applied automatically at startup via `db.Database.Migrate()` in `Program.cs`.
