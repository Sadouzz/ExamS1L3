<?php

namespace App\Entity;

use App\Repository\MenuRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: MenuRepository::class)]
#[ORM\Table(name: 'menus')]
class Menu
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $libelle = null;

    #[ORM\Column(length: 255)]
    private ?string $imageUrl = null;

    #[ORM\Column]
    private ?float $prix = null;

    #[ORM\Column]
    private ?bool $isArchived = null;

    /**
     * @var Collection<int, MenuBurger>
     */
    #[ORM\OneToMany(targetEntity: MenuBurger::class, mappedBy: 'menu')]
    private Collection $menuBurgers;

    /**
     * @var Collection<int, MenuComplement>
     */
    #[ORM\OneToMany(targetEntity: MenuComplement::class, mappedBy: 'menu')]
    private Collection $menuComplements;

    public function __construct()
    {
        $this->menuBurgers = new ArrayCollection();
        $this->menuComplements = new ArrayCollection();
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

    public function getLibelle(): ?string
    {
        return $this->libelle;
    }

    public function setLibelle(string $libelle): static
    {
        $this->libelle = $libelle;

        return $this;
    }

    public function getImageUrl(): ?string
    {
        return $this->imageUrl;
    }

    public function setImageUrl(string $imageUrl): static
    {
        $this->imageUrl = $imageUrl;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): static
    {
        $this->prix = $prix;

        return $this;
    }

    public function isArchived(): ?bool
    {
        return $this->isArchived;
    }

    public function setIsArchived(bool $isArchived): static
    {
        $this->isArchived = $isArchived;

        return $this;
    }

    /**
     * @return Collection<int, MenuBurger>
     */
    public function getMenuBurgers(): Collection
    {
        return $this->menuBurgers;
    }

    public function addMenuBurger(MenuBurger $menuBurger): static
    {
        if (!$this->menuBurgers->contains($menuBurger)) {
            $this->menuBurgers->add($menuBurger);
            $menuBurger->setMenu($this);
        }

        return $this;
    }

    public function removeMenuBurger(MenuBurger $menuBurger): static
    {
        if ($this->menuBurgers->removeElement($menuBurger)) {
            // set the owning side to null (unless already changed)
            if ($menuBurger->getMenu() === $this) {
                $menuBurger->setMenu(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, MenuComplement>
     */
    public function getMenuComplements(): Collection
    {
        return $this->menuComplements;
    }

    public function addMenuComplement(MenuComplement $menuComplement): static
    {
        if (!$this->menuComplements->contains($menuComplement)) {
            $this->menuComplements->add($menuComplement);
            $menuComplement->setMenu($this);
        }

        return $this;
    }

    public function removeMenuComplement(MenuComplement $menuComplement): static
    {
        if ($this->menuComplements->removeElement($menuComplement)) {
            // set the owning side to null (unless already changed)
            if ($menuComplement->getMenu() === $this) {
                $menuComplement->setMenu(null);
            }
        }

        return $this;
    }
}
