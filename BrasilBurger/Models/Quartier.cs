namespace Models
{
    using System.ComponentModel.DataAnnotations.Schema;
    public class Quartier
    {
        [Column("id")]
        public long Id { get; set; }
        [Column("nom")]
        public string Nom { get; set; }
        [Column("zone_id")]
        public long ZoneId { get; set; }
        [ForeignKey("ZoneId")]
        public Zone Zone { get; set; }
    }

}