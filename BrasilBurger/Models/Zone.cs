namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class Zone
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("nom")]
        public string Nom { get; set; }
        [Column("prix_livraison")]
        public double PrixLivraison { get; set; }
    }

}