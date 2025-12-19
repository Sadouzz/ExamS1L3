namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class CommandeItem
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("commande_id")]
        public long CommandeId { get; set; }
        [Column("burger_id")]
        public long? BurgerId { get; set; }
        [Column("menu_id")]
        public long? MenuId { get; set; }
        [Column("complement_id")]
        public long? ComplementId { get; set; }
        [Column("quantite")]
        public int Quantite { get; set; }
        [Column("prix_total")]
        public double PrixTotal { get; set; }

        [ForeignKey("BurgerId")]
        public Burger? Burger { get; set; }

        [ForeignKey("MenuId")]
        public Menu? Menu { get; set; }

        [ForeignKey("ComplementId")]
        public Complement? Complement { get; set; }
    }

}