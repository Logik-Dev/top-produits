package jbdc;

/**
 * Une classe pour conserver les informations de connexion.
 * 
 * @author Elodie, Cedric, Bastien
 *
 */
public class Parametres {
    
    private static String password = "root"; 
    private static String user = "root";
    private static String url = "jdbc:mysql://localhost:3308/top-produits?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
     
    public static String getPassword() {
        return password;
    }
    public static String getUrl() {
        return url;
    }
    public static String getUser() {
        return user;
    }
}