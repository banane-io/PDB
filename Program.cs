using Microsoft.EntityFrameworkCore;
using Npgsql;
using PDB;
using PDB.Services;

Console.WriteLine($"[DEBUG] Starting PDB server");

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var builderConfiguration = new ConfigurationBuilder().AddJsonFile("appsettings.json", false, true).AddJsonFile("appsettings.local.json", true, true).AddEnvironmentVariables();
IConfiguration config = builderConfiguration.Build();

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAllOrigins",
        builder => builder.AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader());
});

builder.Services.AddControllers();

// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var connectionString = config["ConnectionStrings:DefaultConnection"];

var builderDbConnection = new NpgsqlConnectionStringBuilder(connectionString);

builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(builderDbConnection.ConnectionString));
builder.Services.AddTransient<IMapPointService, MapPointService>();

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<ApplicationContext>();
    db.Database.Migrate();
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
    app.MapSwagger();
    app.UseCors();
}

app.UseHttpsRedirection();

app.MapControllers();

app.Run();
