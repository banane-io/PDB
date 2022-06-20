using Microsoft.EntityFrameworkCore;
using Npgsql;
using PDB;
using PDB.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var builderConfiguration = new ConfigurationBuilder().AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);
IConfiguration config = builderConfiguration.Build();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var connectionString = config["ConnectionStrings:DefaultConnection"];

var builderDbConnection = new NpgsqlConnectionStringBuilder(connectionString);

builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(builderDbConnection.ConnectionString));
builder.Services.AddTransient<IMapPointService, MapPointService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();