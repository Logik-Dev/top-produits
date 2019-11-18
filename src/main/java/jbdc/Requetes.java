package jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Additif;
import model.Produit;

public class Requetes {

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

	public static void sauvegarderAdditifProduit(Connection connection, String additifId, long produitId)
			throws SQLException {

		String requete = "INSERT INTO additifs_produits VALUES (?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, additifId);
		statement.setLong(2, produitId);

		statement.execute();
		statement.close();

	}

	public static List<Produit> rechercherProduitsParNutriscore(Connection connection, char nutriscore)
			throws SQLException {

		String requete = "SELECT * FROM produit WHERE nutriscore = ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, String.valueOf(nutriscore).toUpperCase());

		List<Produit> produits = new ArrayList<>();
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			long id = result.getLong("id");
			String nom = result.getString("nom");
			String marque = result.getString("marque");
			char score = result.getString("nutriscore").charAt(0);
			Produit produit = new Produit(id, nom, marque, score);
			produits.add(produit);
		}

		return produits;
	}

	public static List<Produit> rechercherProduitsParNom(Connection connection, String nom) throws SQLException {

		String requete = "SELECT * FROM produit WHERE nom LIKE ?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, "%" + nom + "%");

		List<Produit> produits = new ArrayList<>();
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			long id = result.getLong("id");
			String name = result.getString("nom");
			String marque = result.getString("marque");
			char score = result.getString("nutriscore").charAt(0);
			Produit produit = new Produit(id, name, marque, score);
			produits.add(produit);

		}
		return produits;
	}

	public static List<Produit> rechercherProduitsParAdditif(Connection connection, String code) throws SQLException {

		String requete = "SELECT produit.* FROM additif "
				+ "INNER JOIN additifs_produits ON additif.id=additifs_produits.id_additif "
				+ "INNER JOIN produit ON additifs_produits.id_produit=produit.id " + "WHERE code=?";
		PreparedStatement statement = connection.prepareStatement(requete);

		statement.setString(1, code);

		List<Produit> produits = new ArrayList<Produit>();
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			long id = result.getLong("id");
			String name = result.getString("nom");
			String marque = result.getString("marque");
			char score = result.getString("nutriscore").charAt(0);
			Produit produit = new Produit(id, name, marque, score);
			produits.add(produit);

		}
		return produits;
	}
	
	public static List<Produit> rechercherProduitsParNombreDefini(Connection connection, int nombre) throws SQLException{
		String requete = "SELECT * FROM produit LIMIT ?";
		PreparedStatement statement = connection.prepareStatement(requete);
		
		statement.setInt(1, nombre);

		List<Produit> produits = new ArrayList<Produit>();
		ResultSet result = statement.executeQuery();

		while (result.next()) {
			long id = result.getLong("id");
			String name = result.getString("nom");
			String marque = result.getString("marque");
			char score = result.getString("nutriscore").charAt(0);
			Produit produit = new Produit(id, name, marque, score);
			produits.add(produit);
		}
		return produits;
	}
	
	
	
	
	
	
}
