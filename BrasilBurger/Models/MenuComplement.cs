namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class MenuComplement
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("menu_id")]
        public long MenuId { get; set; }
        [Column("complement_id")]
        public long ComplementId { get; set; }
        [Column("quantite")]
        public int Quantite { get; set; }

        [ForeignKey("ComplementId")]
        public Complement Complement { get; set; }
        [ForeignKey("MenuId")]
        public Menu Menu { get; set; }
    }

}