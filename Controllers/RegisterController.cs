using io.fusionauth;
using io.fusionauth.domain;
using io.fusionauth.domain.api.user;
using Microsoft.AspNetCore.Mvc;

namespace PDB.Controllers;

[Route("api/[controller]")]
[ApiController]
public class RegisterController : ControllerBase
{
    private readonly FusionAuthClient _fusionAuthClient;

    public RegisterController(FusionAuthClient fusionAuthClient)
    {
        _fusionAuthClient = fusionAuthClient;
    }

    /// <summary>
    ///     Registers a new user in FusionAuth.
    /// </summary>
    /// <param name="model">The registration fields provided by the client.</param>
    /// <returns>A status message and, on success, the new user's identifier.</returns>
    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] RegistrationRequestModel model)
    {
        if (!ModelState.IsValid) return BadRequest(ModelState);

        // Create a new FusionAuth user object.
        var user = new User
        {
            email = model.Email,
            password = model.Password, // Plain-text; FusionAuth will handle secure hashing.
            firstName = model.FirstName,
            lastName = model.LastName
        };

        // Create a registration object and specify the Application ID.
        var userRegistration = new UserRegistration
        {
            applicationId = new Guid("0a463c2e-ff60-4465-a65b-1a793c8841a2")
            // Additional registration properties such as roles or data can be set here.
        };

        // Build the complete registration request.
        var registrationRequest = new RegistrationRequest
        {
            user = user,
            registration = userRegistration
        };

        // Call FusionAuth to register the user.
        var response = await _fusionAuthClient.RegisterAsync(null, registrationRequest);

        if (response.WasSuccessful())
            // Optionally, return the created user's identifier.
            return Ok(new
            {
                message = "Registration successful.",
                userId = response.successResponse.user?.id
            });

        // Log errors in your logging system as needed.
        return BadRequest(response.errorResponse);
    }

    // Model that represents data coming in from the client.
    public class RegistrationRequestModel
    {
        public string Email { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
    }
}