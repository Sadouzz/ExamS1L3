namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class Menu
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("libelle")]
        public string Libelle { get; set; }
        [Column("image_url")]
        public string ImageUrl { get; set; }
        [Column("is_archived")]
        public bool IsArchived { get; set; }
        [Column("prix")]
        public double Prix { get; set; }

        public List<MenuBurger> MenuBurgers { get; set; } = new List<MenuBurger>();
        public List<MenuComplement> MenuComplements { get; set; } = new List<MenuComplement>();
    }
}
