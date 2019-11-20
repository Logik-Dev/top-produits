package top.produits;

import java.util.List;
import java.util.Scanner;
import api.RequetesAPI;
import model.Additif;
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
				entree.next();
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

	public void afficherListeAdditifs(List<Additif> additifs) {
		int count = 1;

		for (Additif additif : additifs) {
			System.out.println(count + ". " + additif.getCode() + " " + additif.getNom());
			count++;
		}

	}

	public void afficherMenuPrincipal() {

		System.out.println("\n\t\t\t\t\t\t\tTOP PRODUITS\n\n"
				+ "Cette application vous permet de rechercher un produit, ses additifs et son nutriscore "
				+ "parmi une liste de produit les plus populaires du moment.\n"
				+ "Vous pouvez également personnaliser la base de donnée.\n");

		System.out.println("1. Recherche par nom\n" + "2. Recherche par nutriscore\n" + "3. Recherche par additif\n"
				+ "4. Afficher le Top Produit\n" + "5. Quitter\n");

		int choix = choixNumeroMenu(5);

		switch (choix) {

		case 1:
			menuRechercheProduit();
			break;

		case 2:
			menuRechercheNutriscore();
			break;

		case 3:
			menuRechercheAdditif();
			break;

		case 4:
			menuTopProduits();
			break;

		case 5:
			System.out.println("Au revoir!");
			break;
		}

	}

	public void menuTopProduits() {

		System.out.println("Combien voulez vous afficher de produit?");
		int nombre = entree.nextInt();

		entree.nextLine();
		List<Produit> produits = controller.obtenirListeLimiteeDeProduits(nombre);

		menuSelection(produits);
	}

	public void menuRechercheAdditif() {

		List<Additif> additifs = controller.obtenirListeAdditifs();
		afficherListeAdditifs(additifs);
		menuSelectionAdditif(additifs);
	}

	public void menuRechercheNutriscore() {

		String nutriscore = "";
		System.out.println("Entrez un nutriscore (Entrer pour ignorer):");
		nutriscore = entree.nextLine();

		while (!nutriscore.matches("[a-eA-E]") && !nutriscore.isEmpty()) {

			System.out.println("Veuillez saisir un nutriscore entre A et E");
			nutriscore = entree.nextLine();
		}

		List<Produit> produits = controller.obtenirListeProduitsParNutriscore(nutriscore);
		
		menuSelection(produits);
	}

	public void menuRechercheProduit() {

		String nom = "";

		System.out.println("Veuillez saisir le nom du produit à rechercher :");
		nom = entree.nextLine();

		List<Produit> produits = controller.obtenirListeProduitsParNom(nom);

		if (produits.size() > 0) {
			menuSelection(produits);
		}

		else {
			System.out.println("Le produit recherché n'a pas été trouvé dans la base de donnée");

			if (confirmation("Voulez vous faire une recherche étendue")) {
				produits = RequetesAPI.rechercherProduitsParNom(nom);

				if (produits.size() == 0) {
					System.out.println("Le produit n'est pas référencé\n");
					afficherMenuPrincipal();
				} else {
					menuSelection(produits);
				}
			}
			afficherMenuPrincipal();
		}
	}

	public void menuSelectionAdditif(List<Additif> additifs) {
		while (confirmation("\nSéléctionner un additif")) {

			int choixNumero = choixNumeroMenu(additifs.size());

			Additif additif = additifs.get(choixNumero - 1);

			List<Produit> produits = controller.obtenirListeProduitsParAdditif(additif.getCode());

			if (produits.size() == 0) {
				System.out.println("Il n'y a pas de produit contenant cet additif");

			} else {
				menuSelection(produits);
			}
		}
		afficherMenuPrincipal();
	}

	public void menuSelection(List<Produit> produits){
		
		afficherListeProduits(produits);
		
		if (confirmation("\nSéléctionner un produit")) {

			int choixNumero = choixNumeroMenu(produits.size());

			Produit produit = produits.get(choixNumero - 1);
			System.out.println(produit);

			if (controller.obtenirProduitParNom(produit.getNom()) == null) {

				if (confirmation("\nAjouter le produit")) {
					controller.sauvegarderProduit(produit);
					System.out.println("Produit ajouté à la base de donnée.");
				}
			}

			else if (confirmation("\nVoulez vous supprimer ou modifier le produit")) {
				menuEdition(produit);
			}

			else {
				afficherMenuPrincipal();
			}

		} 
		else {
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
			System.out.println("Produit modifié\n");
			afficherMenuPrincipal();

		} else {
			controller.supprimerProduit(produit);
			System.out.println("Produit supprimé\n");
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
