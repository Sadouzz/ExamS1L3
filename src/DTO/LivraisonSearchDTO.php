<?php

namespace App\DTO;

use App\Entity\Enum\StatutLivraison;
use App\Entity\Zone;

class LivraisonSearchDTO
{
    public ?StatutLivraison $statut = null;
    public ?Zone $zone = null;
}
