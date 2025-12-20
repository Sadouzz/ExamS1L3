using Microsoft.EntityFrameworkCore;
using Models;

namespace Data
{
    public class AppDbContext : DbContext
    {
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<Menu> Menus { get; set; }

        public DbSet<MenuBurger> MenuBurgers { get; set; }
        public DbSet<MenuComplement> MenuComplements { get; set; }

        public DbSet<BurgerCategorie> BurgerCategories { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<CommandeItem> CommandeItems { get; set; }
        public DbSet<LivraisonAffection> LivraisonAffections { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Quartier> Quartiers { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Zone> Zones { get; set; }
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Burger>().ToTable("burgers");
            modelBuilder.Entity<BurgerCategorie>().ToTable("burger_categorie");
            modelBuilder.Entity<Commande>().ToTable("commandes");
            modelBuilder.Entity<CommandeItem>().ToTable("commande_item");
            modelBuilder.Entity<Complement>().ToTable("complements");
            modelBuilder.Entity<LivraisonAffection>().ToTable("livraison_affection");

            modelBuilder.Entity<Menu>().ToTable("menus");
            modelBuilder.Entity<MenuBurger>().ToTable("menu_burger");
            modelBuilder.Entity<MenuComplement>().ToTable("menu_complement");

            modelBuilder.Entity<Paiement>().ToTable("paiements");
            modelBuilder.Entity<Quartier>().ToTable("quartiers");
            modelBuilder.Entity<User>().ToTable("users");
            modelBuilder.Entity<Zone>().ToTable("zones");


            base.OnModelCreating(modelBuilder);
        }
    }
}
