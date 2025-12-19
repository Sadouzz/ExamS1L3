namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class Burger
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("libelle")]
        public string Libelle { get; set; }
        [Column("description")]
        public string Desc { get; set; }
        [Column("prix")]
        public double Prix { get; set; }
        [Column("image_url")]
        public string ImageUrl { get; set; }
        [Column("is_archived")]
        public bool IsArchived { get; set; }
        [Column("burger_categorie_id")]
        public long? BurgerCategorieId { get; set; }
        
        [ForeignKey("BurgerCategorieId")]
        public BurgerCategorie BurgerCategorie { get; set; }
    }

}