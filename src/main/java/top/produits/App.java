package top.produits;

public class App {
	
	private static Menu menu = new Menu();
	private static Controller controller = new Controller();
	
	public static void main(String[] args) {
		controller.initialiserLesTables();
		menu.afficherMenuPrincipal();
	}
	

}
