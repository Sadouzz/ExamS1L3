<?php

namespace App\DTO;

use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use App\Entity\Quartier;

class CommandeSearchFormDto
{
    public ?Quartier $quartier = null;
    public ?bool $isPaid = null;
    public ?StatutCommande $statut = null;
    public ?TypeRetrait $typeRetrait = null;
}

