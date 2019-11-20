package top.produits;

import api.RequetesAPI;

public class App {
	
	private static Menu menu = new Menu();
	private static Controller controller = new Controller();
	
	private static void initialiserLesTables() {
		controller.sauvegarderListeAdditif(RequetesAPI.obtenirListeAdditifs());
		controller.sauvegarderListeProduits(RequetesAPI.obtenirListeTopProduits());
	}

	public static void main(String[] args) {

		if (controller.obtenirListeDeToutLesProduits().size() == 0) {
			initialiserLesTables();
		}

		menu.afficherMenuPrincipal();
	

	}
	

}
