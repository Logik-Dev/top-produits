package jdbc;
/**
 * Classe contenant les paramètres de connexion jdbc
 * @author Elodie, Bastien, Cédric
 *
 */
public class Parametres {
	
	private static String user     = "root";
	private static String password = "root";
	private static String url	   = "jdbc:mysql://localhost:3308/top-produits?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
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