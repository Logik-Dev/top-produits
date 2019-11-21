package top.produits;

import java.util.List;
import java.util.Scanner;
import model.Additif;
import model.Produit;

public class Menu {

	private Scanner entree = new Scanner(System.in);
	private Controller controller = new Controller();
	private final String SAISIR_NOMBRE_MESSAGE = "Entrez un chiffre entre 1 et ";

	/**
	 * Demander à l'utilisateur une confirmation en affichant un message
	 * 
	 * @param message Le message à afficher
	 * @return True si l'utilisateur approuve ou False sinon
	 */
	public boolean confirmation(String message) {
		String choix = "";
		while (!choix.equalsIgnoreCase("O") && !choix.equalsIgnoreCase("N")) {
			System.out.print(message + "? (o/n) : ");
			choix = entree.nextLine();
		}
		return choix.equalsIgnoreCase("O") ? true : false;
	}

	/**
	 * Permettre à l'utilisateur de saisir un nombre correspondant à son choix
	 * 
	 * @param max Le chiffre maximum autorisé
	 * @return Le chiffre choisi par l'uilisateur
	 */
	public int choixNumeroMenu(int max) {
		int choix = 0;
		do {
			System.out.print(SAISIR_NOMBRE_MESSAGE + max + " : ");

			while (!entree.hasNextInt()) {
				System.out.println(SAISIR_NOMBRE_MESSAGE + max + " : ");
				entree.next();
			}

			choix = entree.nextInt();

		} while (choix < 1 || choix > max);

		entree.nextLine();
		return choix;
	}

	/**
	 * Afficher une liste de produits numérotés
	 * 
	 * @param produits La liste à afficher
	 */
	public void afficherListeProduits(List<Produit> produits) {
		int count = 1;

		for (Produit produit : produits) {
			System.out.println(count + ". " + produit.getNom());
			count++;
		}
	}

	/**
	 * Afficher une liste d'additifs numérotés
	 * 
	 * @param additifs La liste à afficher
	 */
	public void afficherListeAdditifs(List<Additif> additifs) {
		int count = 1;

		for (Additif additif : additifs) {
			System.out.println(count + ". " + additif.getCode() + " " + additif.getNom());
			count++;
		}

	}

	/**
	 * Gestion du menu principal
	 */
	public void afficherMenuPrincipal() {

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\n\t\t\t\t\t\t\tTOP PRODUITS\n\n"
				+ "Cette application vous permet de rechercher un produit, ses additifs et son nutriscore "
				+ "parmi une liste de produits les plus populaires du moment.\n"
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

	/**
	 * Afficher une liste limitée des produits les plus populaires
	 */
	public void menuTopProduits() {

		int limit = 0;

		do {
			System.out.print("Combien voulez vous afficher de produits ? : ");

			while (!entree.hasNextInt()) {
				System.out.println(SAISIR_NOMBRE_MESSAGE + 1000);
				entree.next();
			}

			limit = entree.nextInt();

		} while (limit < 1 || limit > 1000);

		entree.nextLine();

		List<Produit> produits = controller.obtenirListeLimiteeDeProduits(limit);

		menuSelectionProduit(produits);
	}

	/**
	 * Menu pour rechercher un produit par additif
	 */
	public void menuRechercheAdditif() {

		List<Additif> additifs = controller.obtenirListeAdditifs();
		afficherListeAdditifs(additifs);
		menuSelectionAdditif(additifs);
	}

	/**
	 * Menu pour rechercher un produit par nutriscore
	 */
	public void menuRechercheNutriscore() {

		String nutriscore = "";
		System.out.print("Entrez un nutriscore (Entrer pour ignorer) : ");
		nutriscore = entree.nextLine();

		while (!nutriscore.matches("[a-eA-E]") && !nutriscore.isEmpty()) {

			System.out.print("Veuillez saisir un nutriscore entre A et E : ");
			nutriscore = entree.nextLine();
		}

		List<Produit> produits = controller.obtenirListeProduitsParNutriscore(nutriscore);

		menuSelectionProduit(produits);
	}

	/**
	 * Gestion du menu de recherche de produit
	 */
	public void menuRechercheProduit() {

		String nom = "";

		System.out.print("\nVeuillez saisir le nom du produit à rechercher : ");
		nom = entree.nextLine();

		List<Produit> produits = controller.obtenirListeProduitsParNom(nom);

		if (!produits.isEmpty()) {
			menuSelectionProduit(produits);
		}

		else {
			System.out.println("Le produit recherché n'a pas été trouvé dans la base de donnée !");

			if (confirmation("Voulez vous faire une recherche étendue")) {
				produits = controller.obtenirProduitsParNomAPI(nom);

				if (produits.isEmpty()) {
					System.out.println("Le produit n'est pas référencé\n");
					afficherMenuPrincipal();
				} else {
					menuSelectionProduit(produits);
				}
			} else {
				afficherMenuPrincipal();
			}

		}
	}

	/**
	 * Gestion du menu de séléction d'additif
	 * 
	 * @param additifs
	 */
	public void menuSelectionAdditif(List<Additif> additifs) {
		while (confirmation("\nSéléctionner un additif")) {

			int choixNumero = choixNumeroMenu(additifs.size());

			Additif additif = additifs.get(choixNumero - 1);

			List<Produit> produits = controller.obtenirListeProduitsParAdditif(additif.getCode());

			if (produits.isEmpty()) {
				System.out.println("Il n'y a pas de produit contenant cet additif");

			} else {
				menuSelectionProduit(produits);
			}
		}
		afficherMenuPrincipal();
	}

	/**
	 * Permettre de séléctionner un produit parmi une liste
	 * 
	 * @param produits La liste de produits à afficher
	 */
	public void menuSelectionProduit(List<Produit> produits) {

		afficherListeProduits(produits);

		if (confirmation("\nSéléctionner un produit")) {

			int choixNumero = choixNumeroMenu(produits.size());

			Produit produit = produits.get(choixNumero - 1);
			List<String> codes = controller.obtenirCodesAdditifsParIds(produit.getAdditifs());

			System.out.println(produit);
			System.out.println("- additifs: " + codes);

			if (controller.obtenirProduitParNom(produit.getNom()) == null) {

				if (confirmation("\nAjouter le produit")) {
					controller.sauvegarderProduit(produit);
					System.out.println("Produit ajouté à la base de donnée.");
					afficherMenuPrincipal();
				}
		
				else {
					afficherMenuPrincipal();
				}
			}

			else if (confirmation("\nVoulez vous supprimer ou modifier le produit")) {
				menuEdition(produit);
			}

			else {
				afficherMenuPrincipal();
			}

		} else {
			afficherMenuPrincipal();
		}
	}

	/**
	 * Permettre d'ajouter, de modifier ou de supprimer un produit
	 * 
	 * @param produit Le produit à éditer
	 */
	public void menuEdition(Produit produit) {
		System.out.println("1. Modification\n2. Suppression\n3. Revenir au menu principal");

		int choixNum = choixNumeroMenu(3);

		if (choixNum == 1) {
			String valeur = "";
			System.out.print("Entrez un nom (Entrer pour ignorer) : ");
			valeur = entree.nextLine();
			if (!valeur.isEmpty()) {
				produit.setNom(valeur);
			}

			System.out.print("Entrez une marque (Entrer pour ignorer) : ");
			valeur = entree.nextLine();
			if (!valeur.isEmpty()) {
				produit.setMarque(valeur);
			}
			valeur = "z";
			while (!valeur.matches("[a-eA-E]") && !valeur.isEmpty()) {
				System.out.print("Entrez un nutriscore (Entrer pour ignorer) : ");
				valeur = entree.nextLine();
			}
			if (!valeur.isEmpty()) {
				produit.setNutriscore(valeur.charAt(0));
			}

			System.out.println(produit + "\n");
			controller.modifierProduit(produit);
			System.out.println("Produit modifié\n");
			afficherMenuPrincipal();

		} else if (choixNum == 2) {
			controller.supprimerProduit(produit);
			System.out.println("Produit supprimé\n");
			afficherMenuPrincipal();
		
		}else {
			afficherMenuPrincipal();
		}
	}

}
