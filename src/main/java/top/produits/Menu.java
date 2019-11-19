package top.produits;

import java.util.List;
import java.util.Scanner;
import api.RequetesAPI;
import model.Produit;

public class Menu {

	Scanner entree = new Scanner(System.in);
	Controller controller = new Controller();

	public boolean confirmation(String message) {
		String choix = "";
		while (!choix.equalsIgnoreCase("O") && !choix.equalsIgnoreCase("N")) {
			System.out.println(message + " ? (o/n)");
			choix = entree.nextLine();
		}
		return choix.equalsIgnoreCase("O") ? true : false;
	}

	public int choixNumeroMenu(int max) {
		int choix = 0;
		do {
			System.out.println("\nEntrez le numéro de votre choix :");

			while (!entree.hasNextInt()) {
				System.out.println("Merci d'entrer un chiffre entre 1 et " + max);
				entree.hasNext();
			}

			choix = entree.nextInt();

		} while (choix < 1 || choix > max);

		entree.nextLine();
		return choix;
	}

	public void afficherListeProduits(List<Produit> produits) {
		int count = 1;

		for (Produit produit : produits) {
			System.out.println(count + ". " + produit.getNom());
			count++;
		}

	}

	public void afficherMenuPrincipal() {

		System.out.println("\n\t\t\t\t\t\t\tTOP PRODUITS\n\n"
				+ "Cette application vous permet de rechercher un produit, ses additifs et son nutriscore "
				+ "parmi une liste de produit les plus populaires du moment.\n"
				+ "Vous pouvez également personnaliser la base de donnée.\n");

		System.out.println("1. Recherche par nom\n" + "2. Recherche par nutriscore\n" + "3. Recherche par additif\n"
				+ "4. Afficher le Top Produit\n");

		int choix = choixNumeroMenu(4);

		switch (choix) {

		case 1:
			menuRechercheProduit();
			break;

		case 4:
			List<Produit> produits = controller.obtenirListeDeToutLesProduits();
			afficherListeProduits(produits);
			menuSelection(produits);
			break;
		}

	}

	public void menuRechercheProduit() {

		String nom = "";

		System.out.println("Veuillez saisir le nom du produit à rechercher :");
		nom = entree.nextLine();

		List<Produit> produits = controller.obtenirListeProduitsParNom(nom);
		afficherListeProduits(produits);

		if (produits.size() > 0) {
			menuSelection(produits);
		}
		
		else {
			System.out.println("Le produit recherché n'a pas été trouvé dans la base de donnée");

			if (confirmation("Voulez vous faire une recherche étendue")) {
				produits = RequetesAPI.rechercherProduitsParNom(nom);
				afficherListeProduits(produits);

				if (produits.size() == 0) {
					System.out.println("Le produit n'est pas référencé\n\n\n\n");
					afficherMenuPrincipal();
				}
			}
		}
	}
	
    public void menuSelection(List<Produit> produits) {
		if (confirmation("\nSéléctionner un produit")) {

			int choixNumero = choixNumeroMenu(produits.size());

			Produit produit = produits.get(choixNumero - 1);
			System.out.println(produit);

			if (confirmation("\nVoulez vous supprimer ou modifier le produit")) {
				menuEdition(produit);
			} else {
				afficherMenuPrincipal();
			}

		} else {
			afficherMenuPrincipal();
		}
    }
    
	public void menuEdition(Produit produit) {
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
			controller.modifierProduit(produit);
			System.out.println("Produit modifié\n\n\n\n");
			afficherMenuPrincipal();

		} else {
			controller.supprimerProduit(produit);
			System.out.println("Produit supprimé\n\n\n\n");
			afficherMenuPrincipal();
		}
	}

	private static void initialiserLesTables() {

		Controller controller = new Controller();
		controller.sauvegarderListeAdditif(RequetesAPI.obtenirListeAdditifs());
		controller.sauvegarderListeProduits(RequetesAPI.obtenirListeTopProduits());
	}

	public static void main(String[] args) {

		if (new Controller().obtenirListeDeToutLesProduits().size() == 0) {
			initialiserLesTables();
		}

		Menu test = new Menu();
		test.afficherMenuPrincipal();

	}
}
