CREATE TYPE role_user AS ENUM ('GESTIONNAIRE', 'LIVREUR', 'CLIENT');
CREATE TYPE statut_commande AS ENUM ('EN_ATTENTE', 'ANNULEE', 'TERMINEE', 'VALIDEE');
CREATE TYPE type_retrait AS ENUM ('LIVRAISON', 'SUR_PLACE', 'A_EMPORTER');
CREATE TYPE moyen_paiement AS ENUM ('WAVE', 'OM');
CREATE TYPE statut_livraison AS ENUM ('EN_ATTENTE', 'EN_COURS', 'TERMINEE');
CREATE TYPE type_complement AS ENUM ('BOISSON', 'FRITE');
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    tel VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    role role_user NOT NULL,
    is_archived BOOLEAN DEFAULT FALSE
);
CREATE TABLE burger_categorie (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL UNIQUE
);
CREATE TABLE burgers (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL,
    description TEXT,
    prix DOUBLE PRECISION NOT NULL,
    image_url VARCHAR(255),
    is_archived BOOLEAN DEFAULT FALSE,
    burger_categorie_id BIGINT REFERENCES burger_categorie(id)
);
CREATE TABLE complements (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL,
    prix DOUBLE PRECISION NOT NULL,
    image_url VARCHAR(255),
    is_archived BOOLEAN DEFAULT FALSE,
    type_complement type_complement NOT NULL
);
CREATE TABLE menus (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL,
    image_url VARCHAR(255),
    is_archived BOOLEAN DEFAULT FALSE,
    prix DOUBLE PRECISION NOT NULL
);
CREATE TABLE menu_burger (
    id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT REFERENCES menus(id) ON DELETE CASCADE,
    burger_id BIGINT REFERENCES burgers(id),
    quantite INT NOT NULL CHECK (quantite > 0)
);
CREATE TABLE menu_complement (
    id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT REFERENCES menus(id) ON DELETE CASCADE,
    complement_id BIGINT REFERENCES complements(id),
    quantite INT NOT NULL CHECK (quantite > 0)
);
CREATE TABLE zones (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prix_livraison DOUBLE PRECISION NOT NULL
);
CREATE TABLE quartiers (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    zone_id BIGINT REFERENCES zones(id)
);
CREATE TABLE commandes (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT REFERENCES users(id),
    adresse TEXT,
    quartier_id BIGINT REFERENCES quartiers(id),
    montant_hors_livraison DOUBLE PRECISION NOT NULL DEFAULT 0,
    montant_total DOUBLE PRECISION NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    is_paid BOOLEAN DEFAULT FALSE,
    statut statut_commande DEFAULT 'EN_ATTENTE',
    type_retrait type_retrait NOT NULL
);
CREATE TABLE commande_item (
    id BIGSERIAL PRIMARY KEY,
    commande_id BIGINT REFERENCES commandes(id) ON DELETE CASCADE,
    burger_id BIGINT REFERENCES burgers(id),
    menu_id BIGINT REFERENCES menus(id),
    complement_id BIGINT REFERENCES complements(id),
    quantite INT NOT NULL CHECK (quantite > 0),
    prix_total DOUBLE PRECISION NOT NULL,

    CHECK (
        (burger_id IS NOT NULL)::int
        + (menu_id IS NOT NULL)::int
        + (complement_id IS NOT NULL)::int = 1
    )
);
CREATE TABLE paiements (
    id BIGSERIAL PRIMARY KEY,
    montant DOUBLE PRECISION NOT NULL,
    ref_transaction VARCHAR(255),
    date TIMESTAMP DEFAULT NOW(),
    moyen_paiement moyen_paiement NOT NULL,
    commande_id BIGINT UNIQUE REFERENCES commandes(id)
);
CREATE TABLE livraison_affection (
    id BIGSERIAL PRIMARY KEY,
    commande_id BIGINT UNIQUE REFERENCES commandes(id),
    livreur_id BIGINT REFERENCES users(id),
    zone_id BIGINT REFERENCES zones(id),
    statut statut_livraison DEFAULT 'EN_ATTENTE'
);
