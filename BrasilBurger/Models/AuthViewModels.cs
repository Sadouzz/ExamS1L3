using System.ComponentModel.DataAnnotations;

namespace Models
{
    public class LoginViewModel
    {
        [Required, EmailAddress]
        public string Email { get; set; }

        [Required, DataType(DataType.Password)]
        public string Password { get; set; }
    }

    public class RegisterViewModel
    {
        [Required]
        public string Nom { get; set; }

        [Required]
        public string Prenom { get; set; }

        [Required]
        public string Tel { get; set; }

        [Required, EmailAddress]
        public string Email { get; set; }

        [Required, MinLength(6), DataType(DataType.Password)]
        public string Password { get; set; }
    }
}
