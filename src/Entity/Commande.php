<?php

namespace App\Entity;

use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use App\Repository\CommandeRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeRepository::class)]
#[ORM\Table(name: 'commandes')]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $adresse = null;

    #[ORM\ManyToOne(inversedBy: 'commandes')]
    private ?Quartier $quartier = null;

    #[ORM\Column]
    private ?float $montantHorsLivraison = null;

    #[ORM\Column]
    private ?float $montantTotal = null;

    #[ORM\Column]
    private ?\DateTimeImmutable $createdAt = null;

    #[ORM\Column]
    private ?\DateTimeImmutable $updatedAt = null;

    #[ORM\Column]
    private ?bool $isPaid = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?StatutCommande $statut = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?TypeRetrait $typeRetrait = null;

    /**
     * @var Collection<int, CommandeItem>
     */
    #[ORM\OneToMany(targetEntity: CommandeItem::class, mappedBy: 'commande')]
    private Collection $commandeItems;

    public function __construct()
    {
        $this->commandeItems = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function setId(string $id): static
    {
        $this->id = $id;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): static
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getQuartier(): ?Quartier
    {
        return $this->quartier;
    }

    public function setQuartier(?Quartier $quartier): static
    {
        $this->quartier = $quartier;

        return $this;
    }

    public function getMontantHorsLivraison(): ?float
    {
        return $this->montantHorsLivraison;
    }

    public function setMontantHorsLivraison(float $montantHorsLivraison): static
    {
        $this->montantHorsLivraison = $montantHorsLivraison;

        return $this;
    }

    public function getMontantTotal(): ?float
    {
        return $this->montantTotal;
    }

    public function setMontantTotal(float $montantTotal): static
    {
        $this->montantTotal = $montantTotal;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeImmutable
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeImmutable $createdAt): static
    {
        $this->createdAt = $createdAt;

        return $this;
    }

    public function getUpdatedAt(): ?\DateTimeImmutable
    {
        return $this->updatedAt;
    }

    public function setUpdatedAt(\DateTimeImmutable $updatedAt): static
    {
        $this->updatedAt = $updatedAt;

        return $this;
    }

    public function isPaid(): ?bool
    {
        return $this->isPaid;
    }

    public function setIsPaid(bool $isPaid): static
    {
        $this->isPaid = $isPaid;

        return $this;
    }

    public function getStatut(): ?StatutCommande
    {
        return $this->statut;
    }

    public function setStatut(?StatutCommande $statut): static
    {
        $this->statut = $statut;

        return $this;
    }

    public function getTypeRetrait(): ?TypeRetrait
    {
        return $this->typeRetrait;
    }

    public function setTypeRetrait(?TypeRetrait $typeRetrait): static
    {
        $this->typeRetrait = $typeRetrait;

        return $this;
    }

    /**
     * @return Collection<int, CommandeItem>
     */
    public function getCommandeItems(): Collection
    {
        return $this->commandeItems;
    }

    public function addCommandeItem(CommandeItem $commandeItem): static
    {
        if (!$this->commandeItems->contains($commandeItem)) {
            $this->commandeItems->add($commandeItem);
            $commandeItem->setCommande($this);
        }

        return $this;
    }

    public function removeCommandeItem(CommandeItem $commandeItem): static
    {
        if ($this->commandeItems->removeElement($commandeItem)) {
            // set the owning side to null (unless already changed)
            if ($commandeItem->getCommande() === $this) {
                $commandeItem->setCommande(null);
            }
        }

        return $this;
    }
}
