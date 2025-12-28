<?php

namespace App\Form;

use App\DTO\ComplementSearchDTO;
use App\DTO\LivraisonSearchDTO;
use App\Entity\Enum\StatutLivraison;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class LivraisonSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('statut', ChoiceType::class, [
                'choices' => StatutLivraison::cases(),
                'choice_label' => fn (StatutLivraison $choice) => ucfirst($choice->value),
                'choice_value' => fn (?StatutLivraison $choice) => $choice?->value,
                'placeholder' => 'Choisir un type',
                'required' => false,
                'attr' => ['class' => 'form-select'],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => LivraisonSearchDTO::class,
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}
