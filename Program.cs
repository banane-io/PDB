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
builder.Services.AddSingleton(new FusionAuthClient("j-cplJSb8fxEu61ULrfs9Q5uJWADjJaeBbRQJ4q4R5QojnEgScL2F3K-",
    "http://localhost:9011"));
// Configure JWT authentication using FusionAuth settings.
builder.Services.AddAuthentication(options =>
    {
        options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
        options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(options =>
    {
        // Set this to the FusionAuth URL where your tenant is configured.
        options.Authority = "http://localhost:9011";

        // These settings are usually configured in your FusionAuth tenant/application.
        // Replace values with your own issuer and audience.
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuer = true,
            ValidIssuer = "http://localhost:9011", // e.g., "http://localhost:9011"
            ValidateAudience = true,
            ValidAudience = "0a463c2e-ff60-4465-a65b-1a793c8841a2", // The client application ID set in FusionAuth.
            ValidateLifetime = true,
            ValidateIssuerSigningKey = true
            // If necessary, provide IssuerSigningKey or configure metadata retrieval.
        };
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
}

app.UseHttpsRedirection();

app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();

app.Run();