using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Models;
using System.Security.Claims;
using Data;

public class AuthController : Controller
{
    private readonly AppDbContext _db;
    private readonly PasswordHasher<User> _hasher = new();

    public AuthController(AppDbContext db)
    {
        _db = db;
    }

    public IActionResult Login() => View();

    [HttpPost]
    public async Task<IActionResult> Login(LoginViewModel model)
    {
        if (!ModelState.IsValid) return View(model);

        var user = _db.Users.FirstOrDefault(u => u.Email == model.Email && !u.IsArchived);
        if (user == null)
        {
            ModelState.AddModelError("", "Email ou mot de passe incorrect");
            return View(model);
        }

        var result = _hasher.VerifyHashedPassword(user, user.Password, model.Password);
        if (result == PasswordVerificationResult.Failed)
        {
            ModelState.AddModelError("", "Email ou mot de passe incorrect");
            return View(model);
        }

        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim(ClaimTypes.Name, $"{user.Prenom} {user.Nom}"),
            new Claim(ClaimTypes.Role, user.Role.ToString()),
        };

        var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
        await HttpContext.SignInAsync(
            CookieAuthenticationDefaults.AuthenticationScheme,
            new ClaimsPrincipal(identity));

        return RedirectToAction("Index", "Catalog");
    }
}
