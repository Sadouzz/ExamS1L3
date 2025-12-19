namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    using NpgsqlTypes;

    public class LivraisonAffection
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("commande_id")]
        public long CommandeId { get; set; }
        [Column("livreur_id")]
        public long LivreurId { get; set; }
        [Column("zone_id")]
        public long ZoneId { get; set; }
        [Column("statut")]
        public StatutLivraison Statut { get; set; }
    }
    public enum StatutLivraison
    {
        [PgName("EN_ATTENTE")]
        EN_ATTENTE,
        [PgName("EN_COURS")]
        EN_COURS,
        [PgName("TERMINEE")]
        TERMINEE
    }

}