<?php

namespace App\Form;

use App\DTO\CommandeSearchFormDto;
use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use App\Entity\Quartier;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommandeSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('quartier', EntityType::class, [
                'class' => Quartier::class,
                'choice_label' => 'nom',
                'required' => false,
                'placeholder' => 'Tous les quartiers',
                'label' => 'Quartier',
                'attr' => ['class' => 'form-select'],
            ])
            ->add('statut', ChoiceType::class, [
                'required' => false,
                'placeholder' => 'Tous les statuts',
                'choices' => StatutCommande::cases(),
                'choice_label' => fn (StatutCommande $s) => $s->value,
                'choice_value' => fn (?StatutCommande $s) => $s?->value,
                'attr' => ['class' => 'form-select'],
            ])

            ->add('typeRetrait', ChoiceType::class, [
                'required' => false,
                'placeholder' => 'Tous les types',
                'choices' => TypeRetrait::cases(),
                'choice_label' => fn (TypeRetrait $t) => $t->value,
                'choice_value' => fn (?TypeRetrait $t) => $t?->value,
                'attr' => ['class' => 'form-select'],
            ])
            ->add('isPaid', ChoiceType::class, [
                'required' => false,
                'label' => 'Payée',
                'placeholder' => 'Toutes',
                'choices' => [
                    'Payée' => true,
                    'Non payée' => false,
                ],
                'attr' => [
                    'class' => 'form-select',
                ],
            ])
            ->add('typeProduit', ChoiceType::class, [
                'required' => false,
                'placeholder' => 'Burger ou Menu',
                'choices' => [
                    'Burger' => 'burger',
                    'Menu' => 'menu',
                ],
                'attr' => ['class' => 'form-select'],
            ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => CommandeSearchFormDto::class,
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}
