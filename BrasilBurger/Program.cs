using Data;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.EntityFrameworkCore;
using Models;
using Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(options =>
    {
        options.LoginPath = "/Auth/Login";
        options.AccessDeniedPath = "/Auth/AccessDenied";
        options.ExpireTimeSpan = TimeSpan.FromHours(2);
    });

builder.Services.AddAuthorization();

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"),
        o => o.MapEnum<TypeRetrait>()
              .MapEnum<StatutCommande>()
              .MapEnum<StatutLivraison>()
              .MapEnum<TypeComplement>()
              .MapEnum<MoyenPaiement>()
              .MapEnum<RoleUser>()
    )
);

builder.Services.AddScoped<BurgerService>();
builder.Services.AddScoped<MenuService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();

app.MapStaticAssets();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Catalog}/{action=Index}/{id?}")
    .WithStaticAssets();


app.Run();
