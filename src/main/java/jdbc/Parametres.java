package jdbc;

/**
 * classe qui permet de sauvegarder les paramètres de connexion
 * 
 * @author Cédric, Bastien, Elodie
 *
 */
public class Parametres {
	
	private static String user     = "cedric";
	private static String password = "changeme";
	private static String url      = "";
	
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
