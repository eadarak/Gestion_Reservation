
# Gestion des Réservations

Ce projet vise à gérer les réservations de ressources informatiques à l’UASZ.

## Installation

### 1. Clonage du Projet

Clonez le projet depuis le dépôt Git :

```bash
git clone https://github.com/eadarak/Gestion_Reservation.git
```

### 2. Accès au Répertoire

Accédez au répertoire du projet :

```bash
cd ./Gestion_Reservation
```

### 3. Installation de la Base de Données

Créez la base de données :

```sql
CREATE DATABASE reservation_db;
```

## Configuration

### 1. Configuration de la Base de Données

#### Backend

Accédez d'abord au projet backend :

```bash
cd ./gestion_reservation_uasz
```

Ensuite, accédez au fichier `application.properties`. Si vous utilisez MySQL, ajoutez les instructions suivantes :

```properties
# Configuration de la source de données (DataSource)
spring.datasource.url=jdbc:mysql://localhost:3306/reservation_db
spring.datasource.username=votre_nom_utilisateur
spring.datasource.password=votre_mot_de_passe

# Configuration du dialecte Hibernate
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

# Activation du mode de création automatique des tables (utilisation uniquement pour le développement)
spring.jpa.hibernate.ddl-auto = update
```

#### Frontend

Accédez au répertoire du frontend :

```bash
cd gestion-reservation-front
```

Installez les dépendances nécessaires :

```bash
npm install --save --legacy-peer-deps
npm install @mui/material @emotion/react @emotion/styled --save --legacy-peer-deps
npm install @fortawesome/react-fontawesome@latest @fortawesome/fontawesome-svg-core @fortawesome/free-solid-svg-icons --save --legacy-peer-deps
npm install --save-dev jsdoc jspdf-autotable --save --legacy-peer-deps
npm install @mui/icons-material --save --legacy-peer-deps
```

## Démarrage des Serveurs

Pour démarrer le backend, exécutez :

```bash
mvn spring-boot:run
```

Pour démarrer le frontend, exécutez :

```bash
npm start
```

Assurez-vous que les deux serveurs sont en cours d'exécution pour utiliser l'application.

---

#AUTEUR
EL Hadji Abdou DRAME
Marieme THIAM
BAYE Mor DIOUF
Safietou DIALLO
