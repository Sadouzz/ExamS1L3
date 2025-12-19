namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    using NpgsqlTypes;

    public class Paiement
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("montant")]
        public double Montant { get; set; }
        [Column("ref_transaction")]
        public string RefTransaction { get; set; }
        [Column("date")]
        public DateTime Date { get; set; }
        [Column("moyen_paiement")]
        public MoyenPaiement MoyenPaiement { get; set; }
        [Column("commande_id")]
        public long CommandeId { get; set; }
    }
    public enum MoyenPaiement
    {
        [PgName("WAVE")]
        WAVE,
        [PgName("OM")]
        OM
    }

}