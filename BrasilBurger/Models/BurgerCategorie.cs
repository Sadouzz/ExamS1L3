namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class BurgerCategorie
    {
        [Column("id")]
        public long Id {get; set;}
        [Column("nom")]
        public string Nom {get; set;}
    }
}