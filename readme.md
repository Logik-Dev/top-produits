# Top Produits (Version Beta)

## Composition de l'equipe
----
- Cédric : !Chef de projet
- Elodie : Git MASTER et secrétaire en chef
- Bastien : Débugger ou inspecteur des travaux finis

## Objectifs de l'application
---
L'objectif principal de l'application est de permettre de consulter la fiche complète d'un produit parmi le TOP 1000 des produits préférés des français afin de mieux contrôler son alimentation.

### Composition d'une fiche Produit :
- Identifiant 
- Nom du produit 
- Marque
- Nutriscore 
- Additifs

## Rechercher une fiche produit
---
Afin de consulter la fiche d'un produit spécifique, plusieurs options de recherche ont été mises en place directement accessible par le menu principal

#### Menu principal
1. Recherche par nom
2. Recherche par nutriscore
3. Recherche par additif
4. Afficher le Top Produit
5. Quitter

### 1. Recherche par nom
L'utilisateur peut effectuer une recherche en écrivant directement le nom d'un produit.
Le logiciel lui renvoit une liste de produits proche de sa recherche. 

Il a alors la possibilité de sélectionner directement le numéro du produit qu'il souhaite consulter ou de revenir au menu de départ.

Lorque l'utilisateur consulte la fiche du produit, il lui est offert la possibilité de modifier les valeurs de la fiche ou de supprimer totalement le produit.

Si le produit n'est pas présent dans le Top 1000 des produits préférés des français, l'utilisateur a alors le choix d'effectuer une recherche étendue à l'ensemble des produits présents sur le marché. Une nouvelle liste de produits est alors retournée à l'utilisateur si le produit existe. Il peut consulter la fiche de ce produit et a alors la possibilité d'ajouter ce produit à la base de donnée. 

### 2. Recherche par nutriscore
L'utilisateur peut effectuer une recherche par nutriscore (A, B, C, D ou E). Le logiciel lui renvoie une liste de produit correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

### 3. Recherche par additif
L'utilisateur peut effectuer une recherche par additif pour éviter les produits possédant des additifs contreversés !

Une liste des additifs est proposée afin que l'utilisateur puisse sélectionner l'additif qui l'intéresse. Le logiciel lui renvoie une liste de produits correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

### 4. Afficher le Top Produit
L'utilisateur peut décider d'afficher le nombre de produit qu'il souhaite voir à l'écran parmi la liste du Top Produit. Le logiciel lui renvoit une liste de produits correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

### 5. Quitter
L'utilisateur peut quitter l'application en faisant ce choix.

## Technologies utilisées
---
Languages de programmation :
- JAVA Version 11
- mySQL Version 8

Dépendances :
- mySQL Connector/J : mysql-connector-java-8.0.18.jar
- Appache Commons IO : commons-io-2.6.jar
- JSON in Java : json-20190722.jar

## Base de données
---
### Structures MCD

![alt text](/mcd.jpg)

### Création de la base de données vide
```sql
#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

# Suppression des tables existantes

DROP TABLE IF EXISTS additifs_produits;
DROP TABLE IF EXISTS produit;
DROP TABLE IF EXISTS additif;

#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------


CREATE TABLE produit(
        id         BigInt NOT NULL ,
        nom        Varchar(100) NOT NULL ,
        marque     Varchar (80) NOT NULL ,
        nutriscore Char (5) NOT NULL
    ,CONSTRAINT produit_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: additif
#------------------------------------------------------------

CREATE TABLE additif(
        id   Varchar (15) NOT NULL ,
        nom  Varchar (100) NOT NULL ,
        code Varchar (10) NOT NULL
    ,CONSTRAINT additif_PK PRIMARY KEY (id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: additifs_produits
#------------------------------------------------------------

CREATE TABLE additifs_produits(
        id_additif Varchar (15) NOT NULL ,
        id_produit BigInt NOT NULL
    ,CONSTRAINT additifs_produits_PK PRIMARY KEY (id_additif,id_produit)

    ,CONSTRAINT additifs_produits_additif_FK FOREIGN KEY (id_additif) REFERENCES additif(id)
    ,CONSTRAINT additifs_produits_produit_FK FOREIGN KEY (id_produit) REFERENCES produit(id)
)ENGINE=InnoDB;
````

## Utilisation des API
Open Food Facts répertorie les produits alimentaires du monde entier. Ce projet collaboratif a permis de constituer une base de données libre et ouverte qui a été la base de travail de notre application.

L'utilisation de l'API proposée par le site permet de récupérer un format JSON selon les critères sélectionnés.
 - Liste des additifs : https://fr.openfoodfacts.org/additives.json
 - Liste du Top 1000 produits : https://world.openfoodfacts.org/cgi/search.plsearch_terms=&search_simple=1&action=process&json=1&page_size=1000


