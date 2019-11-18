package jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Initialise une connexion vers la base à partir des informations se trouvant dans la classe Parametres.
 * 
 * @author Elodie, Cedric , Bastien
 *
 */
public class Session {

	private Connection connection;

	/**
	 * Initialise la connection à la création.
	 */
	public Session() {
		try {
			initConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("connection à la base impossible");
		}
	}
	
	/**
	 * Retourne la connection déjà initialisée.
	 * @return la connection déjà initialisée.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Initialise la connection.
	 * 
	 * @throws SQLException
	 */
	private void initConnection() throws SQLException {
		connection = DriverManager.getConnection(Parametres.getUrl(), Parametres.getUser(), Parametres.getPassword());
	}

}