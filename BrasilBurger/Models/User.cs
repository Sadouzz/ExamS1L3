namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    using NpgsqlTypes;

    public class User
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("nom")]
        public string Nom { get; set; }
        [Column("prenom")]
        public string Prenom { get; set; }
        [Column("tel")]
        public string Tel { get; set; }
        [Column("email")]
        public string Email { get; set; }
        [Column("password")]
        public string Password { get; set; }
        [Column("created_at")]
        public DateTime CreatedAt { get; set; }
        [Column("role")]
        public RoleUser Role { get; set; }
        [Column("is_archived")]
        public bool IsArchived { get; set; }
    }
    public enum RoleUser {
        [PgName("GESTIONNAIRE")]
        GESTIONNAIRE,
        [PgName("LIVREUR")] 
        LIVREUR,
        [PgName("CLIENT")] 
        CLIENT
    }

}