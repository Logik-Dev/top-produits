package top.produits;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.Produit;

public class Menu {

	Scanner entree = new Scanner(System.in);
	App app = new App();

	public boolean confirmation(String message) {
		String choix = "";
		while (!choix.equalsIgnoreCase("O") && !choix.equalsIgnoreCase("N")) {
			System.out.println(message + " O/N");
			choix = entree.nextLine();
		}
		return choix.equalsIgnoreCase("O") ? true : false;
	}

	public int choixNumeroMenu(int max) {
		int choix = 0;
		do {
			System.out.println("Entrez votre choix :");

			while (!entree.hasNextInt()) {
				System.out.println("Merci d'entrer un chiffre entre 1 et " + max);
				entree.hasNext();
			}

			choix = entree.nextInt();

		} while (choix < 1 || choix > max);

		entree.nextLine();
		return choix;
	}

	public void afficheMenuPrincipal() throws SQLException {

		System.out.println(
				"TOP PRODUITS\n\nCette application vous permet de rechercher un produit, ses additifs et son nutriscore "
						+ "parmi une liste de produit les plus populaires du moment.\n"
						+ "Vous pouvez également personnaliser la base de donnée.\n");

		System.out.println("Menu :\n" + "1. Recherche par nom\n" + "2. Recherche par nutriscore\n"
				+ "3. Recherche par additif\n" + "4. Afficher le Top Produit\n");

		int choix = choixNumeroMenu(4);

		switch (choix) {

		case 1:
			menuRechercheProduit();
			break;

		case 2:

		}

	}

	public void menuRechercheProduit() throws SQLException {

		String choix = "";
		entree.nextLine();
		System.out.println("Veuillez saisir un nom de produit:");
		choix = entree.nextLine();

		List<Produit> produits = app.afficheProduitParNom(choix);

		if (confirmation("Voulez vous consulter la fiche produit")) {

			int choixNumero = choixNumeroMenu(produits.size());

			Produit produit = produits.get(choixNumero - 1);
			System.out.println(produit);

			if (confirmation("\nVoulez vous suprrimer ou modifier le produit")) {
				menuEdition(produit);
			} else {
				afficheMenuPrincipal();
			}

		} else {
			afficheMenuPrincipal();
		}

	}

	public void menuEdition(Produit produit) throws SQLException {
		System.out.println("1. Modification\n2. Suppresion");

		int choixNum = choixNumeroMenu(2);

		if (choixNum == 1) {
			String valeur = "";
			System.out.println("Entrez un nom (Entrer pour ignorer):");
			valeur = entree.nextLine();
			if (!valeur.isEmpty()) {
				produit.setNom(valeur);
			}

			System.out.println("Entrez une marque (Entrer pour ignorer):");
			valeur = entree.nextLine();
			if (!valeur.isEmpty()) {
				produit.setMarque(valeur);
			}
			valeur = "z";
			while (!valeur.matches("[a-eA-E]") && !valeur.isEmpty()) {
				System.out.println("Entrez un nutriscore (Entrer pour ignorer):");
				valeur = entree.nextLine();
			}
			if (!valeur.isEmpty()) {
				produit.setNutriscore(valeur.charAt(0));
			}

			System.out.println(produit + "\n");
			app.modifierProduit(produit);
			System.out.println("Produit modifié");
			afficheMenuPrincipal();

		} else {
			app.supprimerProduitParNom(produit.getNom());
			System.out.println("Produit supprimé");
			afficheMenuPrincipal();
		}
	}

	public static void main(String[] args) throws SQLException {

		Menu test = new Menu();
		test.afficheMenuPrincipal();

	}
}
