using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Npgsql;
using PDB;
using PDB.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var builderConfiguration = new ConfigurationBuilder().AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);
IConfiguration config = builderConfiguration.Build();
builder.Services.AddIdentityApiEndpoints<IdentityUser>()
    .AddEntityFrameworkStores<ApplicationContext>();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var connectionString = config["ConnectionStrings:DefaultConnection"];

var builderDbConnection = new NpgsqlConnectionStringBuilder(connectionString);

builder.Services.AddDbContext<ApplicationContext>(options => options.UseNpgsql(builderDbConnection.ConnectionString));
builder.Services.AddTransient<IMapPointService, MapPointService>();
builder.Services.AddAuthorization();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
    app.MapSwagger().RequireAuthorization();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapIdentityApi<IdentityUser>();
app.MapControllers();
app.MapPost("/logout", async (SignInManager<IdentityUser> signInManager,
        [FromBody] object empty) =>
    {
        if (empty != null)
        {
            await signInManager.SignOutAsync();
            return Results.Ok();
        }
        return Results.Unauthorized();
    }).RequireAuthorization();

app.Run();