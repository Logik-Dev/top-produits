# Top Produits (Version Beta)

## Composition de l'equipe
----
- Cédric : !Chef de projet
- Elodie : Git MASTER et secrétaire en chef
- Bastien : Débugger ou inspecteur des travaux finis

## Objectifs de l'application
---
L'objectif principal de l'application est de permettre de consulter la fiche complète d'un produit parmi le TOP 500 des produits préférés des français afin de mieux contrôler son alimentation.

### Composition d'une fiche Produit :
- Identifiant 
- Nom du produit 
- Marque
- Nutriscore 
- Additifs

## Rechercher une fiche produit
---
Afin de consulter la fiche d'un produit spécifique, plusieurs options de recherche ont été mise en place directement accessible par le menu principale

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

Si le produit n'est pas présent dans le Top 500 des produits préférés des français, l'utilisateur a alors le choix d'effectuer une recherche étendue à l'ensemble des produits présents sur le marché. Une nouvelle liste de produit est alors retourné à l'utilisateur si le produit existe. Il peut consulter la fiche de ce produit et a alors la possibilité d'ajouter ce produit à la base de donnée. 

### 2. Recherche par nutriscore
L'utilisateur peut effectuer une recherche par nutriscore (A,B, C, D ou E). Le logiciel lui renvoit une liste de produit correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

### 3. Recherche par additif
L'utilisateur peut effectuer une recherche par additif pour éviter les produits possédant des additifs contreversés !

Une liste des additifs est proposée afin que l'utilisateur puisse sélectionner l'additif qui l'intéresse. Le logiciel lui renvoit une liste de produit correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

### 4. Afficher le Top Produit
L'utilisateur peut décider d'afficher le nombre de produit qu'il souhaite voir à l'écran parmi la liste du Top Produit. Le logiciel lui renvoit une liste de produit correspondant aux critères. Il peut consulter la fiche, la modifier ou la supprimer.

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

