using Microsoft.AspNetCore.Mvc;

namespace PDB.Controllers;

[Route("api/[controller]")]
[ApiController]
public class RegisterController : ControllerBase
{
    private readonly ILogger<RegisterController> _logger;

    public RegisterController(ILogger<RegisterController> logger)
    {
        _logger = logger;
    }

    [HttpPost("register")]
    public IActionResult Register([FromBody] RegistrationRequestModel model)
    {
        if (!ModelState.IsValid) return BadRequest(ModelState);

        // TODO: implement registration logic
        _logger.LogInformation("Registration requested for {Email}", model.Email);
        return Ok(new { message = "Registration not yet implemented." });
    }

    public class RegistrationRequestModel
    {
        public string Email { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
    }
}
