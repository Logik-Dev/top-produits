package jdbc;

public class Parametres {
	
	private static String user     = "root";
	private static String password = "@Bacoon17!";
	private static String url	   = "jdbc:mysql://localhost:3308/Top-Produits?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
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