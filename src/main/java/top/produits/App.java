package top.produits;
/**
 * Point d'entrée de l'application
 * @author Elodie, Bastien, Cédric
 *
 */
public class App {
	
	private static Menu menu = new Menu();
	private static Controller controller = new Controller();
	
	public static void main(String[] args) {
		controller.initialiserLesTables();
		menu.afficherMenuPrincipal();
	}
	

}
