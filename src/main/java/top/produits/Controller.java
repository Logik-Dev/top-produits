package top.produits;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.RequetesAPI;
import jdbc.RequetesDB;
import jdbc.Session;
import model.Additif;
import model.Produit;

/**
 * Classe qui fait le lien entre l'API, la base de donnée et les actions utilisateur
 * 
 * @author Elodie, Bastien, Cédric
 * 
 */
public class Controller {

	private Session session = new Session();
	private final String ERREUR_LIST = "Impossible d'obtenir la liste de produits.";
	
	/**
	 * Remplir les tables si elles sont vides
	 */
	public void initialiserLesTables() {
		System.out.println("Merci de patienter pendant l'initialisation des tables de la base de donnée...");
		
		if(obtenirListeAdditifs() == null || obtenirListeAdditifs().isEmpty()) {
			sauvegarderListeAdditif(obtenirAdditifsAPI());
		}
		if(obtenirListeDeToutLesProduits() == null || obtenirListeDeToutLesProduits().isEmpty()) {
			sauvegarderListeProduits(obtenirTopProduitsAPI());
		}

	}
	/**
	 * Sauvegarder un additif en base de donnée
	 * 
	 * @param additif L'additif à sauvegarder
	 */
	public void sauvegarderAdditif(Additif additif) {

		try {
			RequetesDB.sauvegarderAdditif(session.getConnection(), additif);
		}

		catch (SQLException e) {
			System.out.println("Impossible de sauvegarder l'additif.");
		}
	}

	/**
	 * Sauvegarder une liste d'additifs en base de donnée
	 * 
	 * @param additifs Une ArrayList d'objets Additif à sauvegarder
	 */
	public void sauvegarderListeAdditif(List<Additif> additifs) {

		try {
			for (Additif additif : additifs) {
				sauvegarderAdditif(additif);

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible de sauvegarder la liste d'additifs.");
		}
	}

	/**
	 * Sauvegarder un produit en base de donnée
	 * 
	 * @param produit L'objet Produit à sauvegarder
	 */
	public void sauvegarderProduit(Produit produit) {

		try {
			RequetesDB.sauvegarderProduit(session.getConnection(), produit);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de sauvegarder le produit.");
		}

		try {
			if (!produit.getAdditifs().isEmpty()) {
				for (String additif : produit.getAdditifs()) {
					RequetesDB.sauvegarderAdditifsProduit(session.getConnection(), additif, produit.getId());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de sauvegarder les additifs du produits");
		}
	}

	/**
	 * Sauvegarder une liste de produits en base de donnée
	 * 
	 * @param produits Une ArrayList d'objets Produit à sauvegarder
	 */
	public void sauvegarderListeProduits(List<Produit> produits) {

		try {
			for (Produit produit : produits) {
				sauvegarderProduit(produit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible de sauvegarder la liste de produits.");
		}
	}
	
	/**
	 * Obtenir une liste de produit par nom depuis l'API
	 * @param nom Le nom du produit recherché
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirProduitsParNomAPI(String nom){
		List<Produit> produitsDejaPresent = obtenirListeDeToutLesProduits();
		return RequetesAPI.rechercherProduitsParNom(nom, produitsDejaPresent);
	}
	
	/**
	 * Obtenir la liste des produits populaires depuis l'API
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirTopProduitsAPI(){
		return RequetesAPI.obtenirListeTopProduits();
	}
	
	/**
	 * Obtenir la liste des additifs depuis l'API
	 * @return Une ArrayList d'objets Additif
	 */
	public List<Additif> obtenirAdditifsAPI(){
		return RequetesAPI.obtenirListeAdditifs();
	}
	
	/**
	 * Obtenir la liste de tout les produits en base de donnée
	 * 
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirListeDeToutLesProduits() {
		List<Produit> produits = null;

		try {
			produits = RequetesDB.obtenirListeDesProduits(session.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(ERREUR_LIST);
		}
		return produits;
	}

	/**
	 * Obtenir une liste limitée de produit depuis la base de donnée
	 * 
	 * @param nombre Le nombre de produits souhaités
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirListeLimiteeDeProduits(int nombre) {
		List<Produit> produits = null;

		try {
			produits = RequetesDB.rechercherProduitsParNombreDefini(session.getConnection(), nombre);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(ERREUR_LIST);
		}
		return produits;
	}

	/**
	 * Obtenir une liste de produits correspondant au nutriscore choisi depuis la
	 * base de donnée
	 * 
	 * @param nutriscore Le nutriscore choisi
	 * @return Une ArrayList d'objet Produit
	 */
	public List<Produit> obtenirListeProduitsParNutriscore(String nutriscore) {
		List<Produit> produits = null;

		try {
			produits = RequetesDB.rechercherProduitsParNutriscore(session.getConnection(), nutriscore);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(ERREUR_LIST);
		}
		return produits;
	}

	/**
	 * Obtenir une liste de produits contenant le nom depuis la base de donnée
	 * 
	 * @param nom Le nom à rechercher
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirListeProduitsParNom(String nom) {
		List<Produit> produits = null;

		try {
			produits = RequetesDB.rechercherProduitsParNom(session.getConnection(), nom);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(ERREUR_LIST);
		}
		return produits;
	}

	/**
	 * Obtenir une liste de produits contenant l'additif fourni depuis la base de
	 * donnée
	 * 
	 * @param codeAdditif Le code de l'additif
	 * @return Une ArrayList d'objets Produit
	 */
	public List<Produit> obtenirListeProduitsParAdditif(String codeAdditif) {
		List<Produit> produits = null;

		try {
			produits = RequetesDB.rechercherProduitsParAdditif(session.getConnection(), codeAdditif);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(ERREUR_LIST);
		}

		return produits;

	}

	/**
	 * Obtenir une liste de codes additifs par Id depuis la base de donnée
	 * 
	 * @param ids
	 * @return une ArrayList de codes additifs par Id
	 */
	public List<String> obtenirCodesAdditifsParIds(List<String> ids) {

		List<String> codesAdditifs = new ArrayList<>();

		try {
			for (String id : ids) {
				Additif additif = RequetesDB.obtenirAdditifParId(session.getConnection(), id);
				if (additif != null) {
					codesAdditifs.add(additif.getCode());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste des additifs.");
		}

		return codesAdditifs;
	}

	/**
	 * Obtenir la liste d'additifs depuis la base de donnée
	 * 
	 * @return l'ArrayList liste d'additifs
	 */
	public List<Additif> obtenirListeAdditifs() {

		List<Additif> additifs = null;

		try {
			additifs = RequetesDB.obtenirListAdditifs(session.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste d'additifs");
		}
		return additifs;
	}

	/**
	 * Obtenir un produit par nom depuis la base de donnée
	 * 
	 * @param nom Le nom du produit recherché
	 * @return Un objet Produit
	 */
	public Produit obtenirProduitParNom(String nom) {
		Produit produit = null;

		try {
			produit = RequetesDB.selectionnerProduitParNom(session.getConnection(), nom);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir le produit.");
		}

		return produit;
	}

	/**
	 * Modifier un produit en base de donnée
	 * 
	 * @param produit Le nouvel objet Produit à enregistrer
	 */
	public void modifierProduit(Produit produit) {

		try {
			RequetesDB.modifierProduit(session.getConnection(), produit);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de modifier le produit.");
		}

	}

	/**
	 * Supprimer un produit dans la base de donnée
	 * 
	 * @param produit L'objet Produit à supprimer
	 */
	public void supprimerProduit(Produit produit) {

		try {
			RequetesDB.supprimerProduit(session.getConnection(), produit);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Impossible de supprimer le produit.");
		}
	}

}
