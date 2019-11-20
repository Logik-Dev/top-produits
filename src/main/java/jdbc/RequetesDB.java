package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Additif;
import model.Produit;

public class RequetesDB {

	// REQUETES CREATE

	public static void sauvegarderAdditif(Connection connection, Additif additif) throws SQLException {

		String requete = "INSERT INTO additif VALUES (?,?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, additif.getId());
		statement.setString(2, additif.getNom());
		statement.setString(3, additif.getCode());

		statement.execute();
		statement.close();

	}

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

	public static void sauvegarderAdditifsProduit(Connection connection, String additifId, long produitId)
			throws SQLException {

		String requete = "INSERT INTO additifs_produits VALUES (?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, additifId);
		statement.setLong(2, produitId);

		statement.execute();
		statement.close();

	}

	// REQUETES READ

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
    
	public static List<Produit> obtenirListeDesProduits(Connection connection) throws SQLException{
		String requete = "SELECT * FROM produit";
		PreparedStatement statement = connection.prepareStatement(requete);
		
		ResultSet result = statement.executeQuery();
		
		return extraireListeProduits(connection, result);
	}
	
	public static List<Produit> rechercherProduitsParNutriscore(Connection connection, char nutriscore)
			throws SQLException {

		String requete = "SELECT * FROM produit WHERE nutriscore = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, String.valueOf(nutriscore).toUpperCase());
		ResultSet result = statement.executeQuery();

		return extraireListeProduits(connection, result);

	}

	public static List<Produit> rechercherProduitsParNom(Connection connection, String nom) throws SQLException {

		String requete = "SELECT * FROM produit WHERE nom LIKE ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, "%" + nom + "%");

		ResultSet result = statement.executeQuery();

		return extraireListeProduits(connection, result);

	}

	public static List<Produit> rechercherProduitsParAdditif(Connection connection, String code) throws SQLException {

		String requete = "SELECT produit.* FROM additif "
				+ "INNER JOIN additifs_produits ON additif.id=additifs_produits.id_additif "
				+ "INNER JOIN produit ON additifs_produits.id_produit=produit.id " + "WHERE code=?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, code);
		ResultSet result = statement.executeQuery();

		return extraireListeProduits(connection, result);

	}

	public static List<Produit> rechercherProduitsParNombreDefini(Connection connection, int nombre)
			throws SQLException {

		String requete = "SELECT * FROM produit LIMIT ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setInt(1, nombre);
		ResultSet result = statement.executeQuery();

		return extraireListeProduits(connection, result);

	}

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

	// REQUETE UPDATE

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

	// REQUETE DELETE

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