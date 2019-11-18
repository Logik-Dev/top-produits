package jbdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Additif;

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
	

}
