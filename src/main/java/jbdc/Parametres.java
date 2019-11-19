package jbdc;

/**
 * Une classe pour conserver les informations de connexion.
 * 
 * @author Elodie, Cedric, Bastien
 *
 */
public class Parametres {
    
    private static String user = "cedric";
    private static String password = "changeme"; 
    private static String url = "jdbc:mysql://localhost:3306/top-produits?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
     
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