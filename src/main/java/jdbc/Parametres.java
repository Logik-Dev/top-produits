package jdbc;
/**
 * Classe contenant les paramètres de connexion jdbc
 * @author Elodie, Bastien, Cédric
 *
 */
public class Parametres {
	
	private static String user     = "cedric";
	private static String password = "changeme";
	private static String url	   = "jdbc:mysql://localhost:3306/top-produits";
	
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