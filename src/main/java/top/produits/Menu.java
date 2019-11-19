package top.produits;

import java.util.Scanner;

public class Menu {

	Scanner entree = new Scanner(System.in);

	public void afficheMenuPrincipal() {

		System.out.println(
				"TOP PRODUITS\n\nCette application vous permet de rechercher un produit, ses additifs et son nutriscore"
						+ "parmi une liste de produit les plus populaires du moment.\n"
						+ "Vous pouvez également personnaliser la base de donnée.");

		System.out.println("Menu :\n" + "1. Recherche par nom" + "2. Recherche par nutriscore"
				+ "3. Recherche par additif" + "4. Afficher le Top Produit");

		int choix = 0;
		do {
			System.out.println("Entrez votre choix :");

			while (!entree.hasNextInt()) {
				System.out.println("Veuillez choisir un nombre entre 1 et 4");
			}

			choix = entree.nextInt();

		} while (choix < 1 || choix > 4);
		

	}

}
