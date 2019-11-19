package top.produits;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.Produit;

public class Menu {

	Scanner entree = new Scanner(System.in); 
	App app = new App();

	public void afficheMenuPrincipal() throws SQLException {

		System.out.println(
				"TOP PRODUITS\n\nCette application vous permet de rechercher un produit, ses additifs et son nutriscore "
						+ "parmi une liste de produit les plus populaires du moment.\n"
						+ "Vous pouvez également personnaliser la base de donnée.\n");

		System.out.println("Menu :\n" + "1. Recherche par nom\n" + "2. Recherche par nutriscore\n"
				+ "3. Recherche par additif\n" + "4. Afficher le Top Produit\n");

		int choix = 0;
		do {
			System.out.println("Entrez votre choix :");

			while (!entree.hasNextInt()) {
				System.out.println("Veuillez choisir un nombre entre 1 et 4");
				entree.next();
			}

			choix = entree.nextInt();
            
		} while (choix < 1 || choix > 4);
		
		switch (choix) {
		
		case 1 :
			menuRechercheProduit();
			break;
			
		case 2 :
			
		}

	}

	public void menuRechercheProduit() throws SQLException {
		
		String choix = "";
		entree.nextLine();
		System.out.println("Veuillez saisir un nom de produit:");
		choix = entree.nextLine();
		
		List<Produit> produits = app.afficheProduitParNom(choix);
		while(!choix.equalsIgnoreCase("O") || !choix.equalsIgnoreCase("N") ) {
			System.out.println("Voulez vous consulter la fiche produit? O/N");
			choix = entree.nextLine();	
		}
		if(choix.equalsIgnoreCase("O")) {
			int choixNumero = 0;
			do {
				System.out.println("Veuillez saisir le numéro du produit.");
				
				while(!entree.hasNextInt()) {
					System.out.println("Merci d'entrer un chiffre entre 1 et "+ produits.size());
					entree.next();
				}
				choixNumero = entree.nextInt();
			
			}while(choixNumero < 1 || choixNumero > produits.size());
			
			
		}
		
		
	}
	
	
	public static void main(String[] args) throws SQLException {
		
		Menu test = new Menu();
		test.afficheMenuPrincipal();
		
	}
}


