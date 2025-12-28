<?php

namespace App\DTO;

use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use App\Entity\Quartier;
use DateTimeInterface;

class CommandeSearchFormDto
{
    public ?DateTimeInterface $date = null;
    public ?Quartier $quartier = null;
    public ?bool $isPaid = null;
    public ?StatutCommande $statut = null;
    public ?TypeRetrait $typeRetrait = null;
    public ?string $typeProduit = null;
}

