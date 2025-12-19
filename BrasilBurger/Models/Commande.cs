namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    using NpgsqlTypes;

    public class Commande
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("adresse")]
        public string Adresse { get; set; }
        [Column("montant_hors_livraison")]
        public double MontantHorsLivraison { get; set; }
        [Column("montant_total")]
        public double MontantTotal { get; set; }
        [Column("created_at")]
        public DateTime CreatedAt { get; set; }
        [Column("updated_at")]
        public DateTime UpdatedAt { get; set; }
        [Column("is_paid")]
        public bool IsPaid { get; set; }
        [Column("statut")]
        public StatutCommande Statut { get; set; }
        [Column("type_retrait")]
        public TypeRetrait TypeRetrait { get; set; }
        [Column("client_id")]
        public long ClientId {get; set;}
        [Column("quartier_id")]
        public long QuartierId {get; set;}

        public List<CommandeItem> CommandeItems {get; set;} = new List<CommandeItem>();
    }
    public enum StatutCommande { 
        [PgName("EN_ATTENTE")] 
        EN_ATTENTE,
        [PgName("ANNULEE")] 
        ANNULEE,
        [PgName("TERMINEE")] 
        TERMINEE, 
        [PgName("VALIDEE")] 
        VALIDEE }
    public enum TypeRetrait {
        [PgName("SUR_PLACE")]
        SUR_PLACE,
        [PgName("A_EMPORTER")]
        A_EMPORTER,
        [PgName("LIVRAISON")]
        LIVRAISON
    }

}