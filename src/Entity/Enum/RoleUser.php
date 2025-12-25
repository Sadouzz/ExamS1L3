<?php

namespace App\Entity\Enum;
enum RoleUser: string
{
    case GESTIONNAIRE = 'GESTIONNAIRE';
    case LIVREUR = 'LIVREUR';
    case CLIENT = 'CLIENT';
}
