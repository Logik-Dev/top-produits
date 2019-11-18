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
        nom        Varchar(100) UNIQUE NOT NULL ,
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