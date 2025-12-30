<?php

namespace App\Repository;

use App\Entity\Commande;
use DateTimeImmutable;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Commande>
 */
class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    public function findByDate(DateTimeImmutable $start, DateTimeImmutable $end): array {
        return $this->createQueryBuilder('c')
            ->andWhere('c.createdAt >= :start')
            ->andWhere('c.createdAt < :end')
            ->setParameter('start', $start)
            ->setParameter('end', $end)
            ->orderBy('c.id', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findByDateArray(DateTimeImmutable $date): array
    {
        $start = $date->setTime(0, 0, 0, 0);
        $end   = $date->setTime(23, 59, 59, 999999);

        return $this->createQueryBuilder('c')
            ->andWhere('c.createdAt BETWEEN :start AND :end')
            ->setParameter('start', $start)
            ->setParameter('end', $end)
            ->orderBy('c.id', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function countBySearch(array $filters, ?string $typeProduit, ?DateTimeImmutable $date): int
    {
        $qb = $this->createQueryBuilder('c')
            ->select('COUNT(DISTINCT c.id)')
            ->leftJoin('c.commandeItems', 'ci');

        foreach ($filters as $field => $value) {
            $qb->andWhere("c.$field = :$field")
                ->setParameter($field, $value);
        }

        if ($date !== null) {
            $start = $date->setTime(0, 0, 0);
            $end   = $date->setTime(23, 59, 59);

            $qb->andWhere('c.createdAt BETWEEN :start AND :end')
                ->setParameter('start', $start)
                ->setParameter('end', $end);
        }

        if ($typeProduit === 'burger') {
            $qb->andWhere('ci.burger IS NOT NULL');
        }

        if ($typeProduit === 'menu') {
            $qb->andWhere('ci.menu IS NOT NULL');
        }

        return (int) $qb->getQuery()->getSingleScalarResult();
    }


    public function findBySearch(array $filters, ?string $typeProduit, ?DateTimeImmutable $date, int $limit, int $offset): array {
        $qb = $this->createQueryBuilder('c')
            ->leftJoin('c.commandeItems', 'ci')
            ->addSelect('ci');

        foreach ($filters as $field => $value) {
            $qb->andWhere("c.$field = :$field")
                ->setParameter($field, $value);
        }

        if ($date !== null) {
            $start = $date->setTime(0, 0, 0, 0);
            $end   = $date->setTime(23, 59, 59, 999999);

            $qb->andWhere('c.createdAt BETWEEN :start AND :end')
                ->setParameter('start', $start)
                ->setParameter('end', $end);
        }

        if ($typeProduit === 'burger') {
            $qb->andWhere('ci.burger IS NOT NULL');
        }

        if ($typeProduit === 'menu') {
            $qb->andWhere('ci.menu IS NOT NULL');
        }

        return $qb
            ->distinct()
            ->setFirstResult($offset)
            ->setMaxResults($limit)
            ->orderBy('c.id', 'ASC')
            ->getQuery()
            ->getResult();
    }


//    /**
//     * @return Commande[] Returns an array of Commande objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('c.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Commande
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
