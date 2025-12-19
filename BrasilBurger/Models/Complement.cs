namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    using NpgsqlTypes;

    public class Complement
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("libelle")]
        public string Libelle { get; set; }
        [Column("prix")]
        public double Prix { get; set; }
        [Column("image_url")]
        public string ImageUrl { get; set; }
        [Column("is_archived")]
        public bool IsArchived { get; set; }
        [Column("type_complement")]
        public TypeComplement TypeComplement { get; set; }
    }
    public enum TypeComplement
    {
        [PgName("BOISSON")]
        BOISSON,
        [PgName("FRITE")]
        FRITE
    }

}