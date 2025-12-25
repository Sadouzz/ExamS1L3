<?php

namespace App\Entity;

use App\Entity\Enum\StatutLivraison;
use App\Repository\LivraisonAffectationRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LivraisonAffectationRepository::class)]
#[ORM\Table(name: 'livraison_affectation')]
class LivraisonAffectation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false)]
    private ?Commande $commande = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Zone $zone = null;

    #[ORM\Column(enumType: StatutLivraison::class)]
    private ?StatutLivraison $statutLivraison = null;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function setId(string $id): static
    {
        $this->id = $id;

        return $this;
    }

    public function getCommande(): ?Commande
    {
        return $this->commande;
    }

    public function setCommande(Commande $commande): static
    {
        $this->commande = $commande;

        return $this;
    }

    public function getZone(): ?Zone
    {
        return $this->zone;
    }

    public function setZone(?Zone $zone): static
    {
        $this->zone = $zone;

        return $this;
    }

    public function getStatutLivraison(): ?StatutLivraison
    {
        return $this->statutLivraison;
    }

    public function setStatutLivraison(StatutLivraison $statutLivraison): static
    {
        $this->statutLivraison = $statutLivraison;

        return $this;
    }
}
