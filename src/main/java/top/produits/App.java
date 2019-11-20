package top.produits;

import api.RequetesAPI;

public class App {
	
	
	private static void initialiserLesTables() {

		Controller controller = new Controller();
		controller.sauvegarderListeAdditif(RequetesAPI.obtenirListeAdditifs());
		controller.sauvegarderListeProduits(RequetesAPI.obtenirListeTopProduits());
	}

	public static void main(String[] args) {

		if (new Controller().obtenirListeDeToutLesProduits().size() == 0) {
			initialiserLesTables();
		}

		Menu menu = new Menu();
	

	}
	

}
