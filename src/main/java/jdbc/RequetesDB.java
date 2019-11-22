package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Additif;
import model.Produit;


/**
 * Classe effectuant les requètes vers la base de donnée. 
 * Elle nécécite une connexion préalablement créée.
 * @author Cédric, Bastien, Elodie
 */
public class RequetesDB {

	// CREATE

   /**
   * Enregistrer un additif dans la base de donnée.
   * @param connection Un objet Connection
   * @param additif L'objet Additif à enregistrer
   * @throws SQLException
    */
	public static void sauvegarderAdditif(Connection connection, Additif additif) throws SQLException {

		String requete = "INSERT INTO additif VALUES (?,?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, additif.getId());
		statement.setString(2, additif.getNom());
		statement.setString(3, additif.getCode());

		statement.execute();
		statement.close();

	}

	
	/**
	 * Enregistrer un produit dans la base de donnée.
	 * @param connection Un objet Connection
	 * @param produit L' objet Produit à enregister
	 * @throws SQLException
	 */
	public static void sauvegarderProduit(Connection connection, Produit produit) throws SQLException {

		String requete = "INSERT INTO produit VALUES (?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setLong(1, produit.getId());
		statement.setString(2, produit.getNom());
		statement.setString(3, produit.getMarque());
		statement.setString(4, String.valueOf(produit.getNutriscore()).toUpperCase());

		statement.execute();
		statement.close();
	}

	
	/**
	 * Enregistrer un additif d'un produit dans la base de donnée.
	 * @param connection Un objet Connnection
	 * @param additifId L'id de l'additif
	 * @param produitId L'id du produit
	 * @throws SQLException
	 */
	public static void sauvegarderAdditifProduit(Connection connection, String additifId, long produitId)
			throws SQLException {

		String requete = "INSERT INTO additifs_produits VALUES (?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, additifId);
		statement.setLong(2, produitId);

		statement.execute();
		statement.close();

	}

		
	// READ

	/**
	 * Extraire une liste de produits d'un objet ResultSet
	 * @param connection Un objet Connection
	 * @param result Un objet ResultSet contenant le résultat d'une requète
	 * @return produits Une ArrayList d'objet Produit
	 * @throws SQLException
	 */
	private static List<Produit> extraireListeProduits(Connection connection, ResultSet result) throws SQLException {
		List<Produit> produits = new ArrayList<>();

		while (result.next()) {
			long id = result.getLong("id");
			String nom = result.getString("nom");
			String marque = result.getString("marque");
			char score = result.getString("nutriscore").charAt(0);
			List<String> additifs = obtenirListeAdditifsId(connection, id);
			Produit produit = new Produit(id, nom, marque, score, additifs);
			produits.add(produit);
		}

		return produits;
	}
	
	/**
	 * Obtenir la liste des additifs de la base de donnée
	 * @param connection Un objet Connection
	 * @return Une ArrayList d'objets Additif
	 * @throws SQLException
	 */
	public static List<Additif> obtenirListeAdditifs(Connection connection) throws SQLException{
		
		String requete = "SELECT * FROM additif ";
		PreparedStatement statement = connection.prepareStatement(requete);
		ResultSet result = statement.executeQuery();
		
		List<Additif> additifs = new ArrayList<>();
		while(result.next()) {
			String id = result.getString("id");
			String nom = result.getString("nom");
			String code = result.getString("code");
			Additif additif = new Additif(id, nom, code);
	        additifs.add(additif);
		}
		statement.close();
		return additifs;
	}
	
	/**
	 * Obtenir un additif de la base de donnée
	 * @param connection Un objet Connection
	 * @param idAdditif L'id de l'additif voulu
	 * @return Un objet Additif
	 * @throws SQLException
	 */
	public static Additif obtenirAdditifParId(Connection connection, String idAdditif) throws SQLException{
		
		String requete = "SELECT * FROM additif WHERE id = ?";
		PreparedStatement statement = connection.prepareStatement(requete);
		
		statement.setString(1, idAdditif);
		ResultSet result = statement.executeQuery();
		Additif additif = null;
		
		while(result.next()) {
			String id = result.getString("id");
			String nom = result.getString("nom");
			String code = result.getString("code");
			additif = new Additif(id, nom, code);
		}
	
		return additif;
	}
	
	/**
	 * permet d'obtenir la liste des id des additifs
	 * @param connection
	 * @param idProduit
	 * @return additifsIds
	 * @throws SQLException
	 */
	public static List<String> obtenirListeAdditifsId(Connection connection, long idProduit) throws SQLException {

		String requete = "SELECT id_additif FROM additifs_produits WHERE id_produit = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setLong(1, idProduit);

		List<String> additifsIds = new ArrayList<>();
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			additifsIds.add(result.getString("id_additif"));
		}

		return additifsIds;
	}
		
    /**
     * Obtenir la liste des produits présent en base de donnée
     * @param connection Un objet Connection
     * @return Une ArrayList d'objet Produit
     * @throws SQLException
     */
	public static List<Produit> obtenirListeDesProduits(Connection connection) throws SQLException{
		String requete = "SELECT * FROM produit";
		PreparedStatement statement = connection.prepareStatement(requete);
		
		ResultSet result = statement.executeQuery();
		List<Produit> produits = extraireListeProduits(connection, result);
		
		statement.close();
		return produits;
	}
		
	/**
	 * Obtenir une liste de produits par nutriscore
	 * @param connection Un objet Connection
	 * @param nutriscore Le nutriscore des produits recherchés
	 * @return Une ArrayList d'objets Produit
	 * @throws SQLException
	 */
	public static List<Produit> rechercherProduitsParNutriscore(Connection connection, String nutriscore)
			throws SQLException {

		String requete = "SELECT * FROM produit WHERE nutriscore = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, String.valueOf(nutriscore).toUpperCase());
		ResultSet result = statement.executeQuery();

		List<Produit> produits = extraireListeProduits(connection, result);
		statement.close();
		
		return produits;

	}

	/**
	 * Rechercher une liste de produit par nom
	 * @param connection Un objet Connection
	 * @param nom Le nom recherché
	 * @return Une ArrayList d'objets Produit
	 * @throws SQLException
	 */
	public static List<Produit> rechercherProduitsParNom(Connection connection, String nom) throws SQLException {

		String requete = "SELECT * FROM produit WHERE nom LIKE ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, "%" + nom + "%");

		ResultSet result = statement.executeQuery();

		List<Produit> produits = extraireListeProduits(connection, result);
		statement.close();
		
		return produits;

	}
	
	/**
	 * Rechercher une liste de produit par additif
	 * @param connection Un objet Connection
	 * @param code Le code de l'additif
	 * @return Une ArrayList d'objets Produit
	 * @throws SQLException
	 */
	public static List<Produit> rechercherProduitsParAdditif(Connection connection, String code) throws SQLException {

		String requete = "SELECT produit.* FROM additif "
				+ "INNER JOIN additifs_produits ON additif.id=additifs_produits.id_additif "
				+ "INNER JOIN produit ON additifs_produits.id_produit=produit.id " + "WHERE code=?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, code);
		ResultSet result = statement.executeQuery();

		List<Produit> produits = extraireListeProduits(connection, result);
		statement.close();
		
		return produits;

	}
	
	/**
	 * Obtenir une liste limitée de produits
	 * @param connection Un objet Connection
	 * @param nombre La nombre d'élément de la liste
	 * @return Une ArrayList d'objets Produit
	 * @throws SQLException
	 */
	public static List<Produit> rechercherProduitsParNombreDefini(Connection connection, int nombre)
			throws SQLException {

		String requete = "SELECT * FROM produit LIMIT ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setInt(1, nombre);
		ResultSet result = statement.executeQuery();

		List<Produit> produits = extraireListeProduits(connection, result);
		statement.close();
		
		return produits;
	}
	
	/**
	 * Rechercher un produit par nom
	 * @param connection Un objet Connection
	 * @param nom Le nom du produit recherché
	 * @return Un objet Produit
	 * @throws SQLException
	 */
	public static Produit selectionnerProduitParNom(Connection connection, String nom) throws SQLException {

		String requete = "SELECT * FROM produit WHERE nom = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, nom);
		ResultSet result = statement.executeQuery();
		Produit produit = null;

		while (result.next()) {
			long id = result.getLong("id");
			String name = result.getString("nom");
			String marque = result.getString("marque");
			char nutriscore = result.getString("nutriscore").charAt(0);
			List<String> additifs = obtenirListeAdditifsId(connection, id);

			produit = new Produit(id, name, marque, nutriscore, additifs);
		}
		
		statement.close();
		return produit;
	}

	
	// UPDATE

	/**
	 * Modifier un produit de la base de donnée
	 * @param connection Un objet Connection
	 * @param produitModifie L'objet Produit modifié
	 * @throws SQLException
	 */
	public static void modifierProduit(Connection connection, Produit produitModifie) throws SQLException {

		String requete = "UPDATE produit SET nom=?, marque=?, nutriscore=? WHERE id=?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, produitModifie.getNom());
		statement.setString(2, produitModifie.getMarque());
		statement.setString(3, String.valueOf(produitModifie.getNutriscore()).toUpperCase());
		statement.setLong(4, produitModifie.getId());

		statement.execute();
		statement.close();
	}

	
	// DELETE
	
	/**
	 * Supprimer un produit de la base de donnée
	 * @param connection Un objet Connection
	 * @param produit L'objet Produit à supprimer
	 * @throws SQLException
	 */
	public static void supprimerProduit(Connection connection, Produit produit) throws SQLException {

		String requete = "DELETE FROM additifs_produits WHERE id_produit = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setLong(1, produit.getId());

		statement.execute();
		statement.close();

		requete = "DELETE FROM produit WHERE id=?";
		statement = connection.prepareStatement(requete);

		statement.setLong(1, produit.getId());

		statement.execute();
		statement.close();

	}

}
