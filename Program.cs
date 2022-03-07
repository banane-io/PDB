using Microsoft.EntityFrameworkCore;
using Npgsql;
using PDB;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var builderConfiguration = new ConfigurationBuilder()
        .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);
IConfiguration config = builderConfiguration.Build();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var connectionString = config["PostgreSql:ConnectionString"];
var dbPassword = config["PostgreSql:DbPassword"];
var builderDbConnection = new NpgsqlConnectionStringBuilder(connectionString)
{
    Password = dbPassword
};
builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(builderDbConnection.ConnectionString));

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