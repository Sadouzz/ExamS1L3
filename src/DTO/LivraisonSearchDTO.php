<?php

namespace App\DTO;

use App\Entity\Enum\StatutLivraison;

class LivraisonSearchDTO
{
    public ?StatutLivraison $statut = null;
}
