package jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	public static void sauvegarderAdditifProduit(Connection connection, String additifId, long produitId) throws SQLException {
		
		String requete = "INSERT INTO additifs_produits VALUES (?,?)";
		PreparedStatement statement = connection.prepareStatement(requete);
		
		statement.setString(1, additifId);
		statement.setLong(2, produitId);
		
		statement.execute();
		statement.close();
		
	}
	
	

}
