using io.fusionauth;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Npgsql;
using PDB;
using PDB.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
var builderConfiguration = new ConfigurationBuilder().AddJsonFile("appsettings.json", false, true);
IConfiguration config = builderConfiguration.Build();
builder.Services.AddSingleton(new FusionAuthClient(config["FusionAuth:Secret"],
    config["FusionAuth:Authority"]));
// Configure JWT authentication using FusionAuth settings.
builder.Services.AddAuthentication(options =>
    {
        options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
        options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(options =>
    {
        // Set this to the FusionAuth URL where your tenant is configured.
        options.Authority = config["FusionAuth:Authority"];

        // These settings are usually configured in your FusionAuth tenant/application.
        // Replace values with your own issuer and audience.
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuer = true,
            ValidIssuer = config["FusionAuth:Authority"],
            ValidateAudience = true,
            ValidAudience = config["FusionAuth:ClientId"],
            ValidateLifetime = true,
            ValidateIssuerSigningKey = true
        };
    });
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAllOrigins",
        builder => builder.AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader());
});

builder.Services.AddAuthorization();
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
    app.UseCors();
}

app.UseHttpsRedirection();

app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();

app.Run();