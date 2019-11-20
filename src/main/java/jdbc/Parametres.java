package jdbc;

/**
 * classe qui permet de sauvegarder les paramètres de connexion
 * 
 * @author Cédric, Bastien, Elodie
 *
 */
public class Parametres {
	
	private static String user     = "root";
	private static String password = "root";
	private static String url		= "jdbc:mysql://localhost:3306/top-produits";
	
	public static String getUser() {
		return user;
	}
	public static String getPassword() {
		return password;
	}
	public static String getUrl() {
		return url;
	}
	
	
}
