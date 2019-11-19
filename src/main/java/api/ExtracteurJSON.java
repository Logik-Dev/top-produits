package api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Additif;
import model.Produit;

public class ExtracteurJSON {

	public static Produit extraireProduit(JSONObject produitJsonObject) {
		Produit produit = null;

		long id = produitJsonObject.getLong("_id");
		
		String nom = "";
		if(produitJsonObject.has("product_name_fr")) {
			nom = produitJsonObject.getString("product_name_fr").trim().toLowerCase();
		}
		
		String marque = "";
		if(produitJsonObject.has("brands")) {
			marque = produitJsonObject.getString("brands");
		}
		Character nutriscore = null;
		JSONArray additifsJsonArray = produitJsonObject.getJSONArray("additives_tags");
		List<String> additifs = new ArrayList<String>();

		for (Object additifJson : additifsJsonArray) {
			String additif = String.valueOf(additifJson);
			additifs.add(additif);
		}

		if (produitJsonObject.has("nutriscore_grade")) {
			nutriscore = produitJsonObject.getString("nutriscore_grade").charAt(0);
		}

		if (!nom.isEmpty() && nutriscore != null && !marque.isEmpty()) {
			produit = new Produit(id, nom, marque, nutriscore, additifs);
		}
		return produit;

	}

	public static List<Produit> extraireListeProduits(JSONArray produitsJsonArray) {
		List<Produit> produits = new ArrayList<>();

		for (Object produitObject : produitsJsonArray) {
			JSONObject produitJsonObject = (JSONObject) produitObject;

			Produit produit = extraireProduit(produitJsonObject);

			if (!produits.contains(produit) && produit != null) {
				produits.add(produit);
			}
		}
		return produits;
	}

	public static Additif extraireAdditif(JSONObject additifJsonObject) {
		Additif additif = null;
		String id = additifJsonObject.getString("id");
		String nom = null;
		String code = null;

		if (additifJsonObject.has("name")) {
			String[] nameArray = additifJsonObject.getString("name").split(" - ");
			if (nameArray.length > 1) {
				nom = nameArray[1];
				code = nameArray[0];
			}

		}
		if (nom != null && code != null) {
			additif = new Additif(id, nom, code);
		}
		return additif;
	}

	public static List<Additif> extraireListeAdditifs(JSONArray additifsJsonArray) {
		List<Additif> additifs = new ArrayList<>();

		for (Object additifObject : additifsJsonArray) {
			JSONObject additifJsonObject = (JSONObject) additifObject;
			Additif additif = extraireAdditif(additifJsonObject);
			
			if(additif != null) {
				additifs.add(additif);
			}

		}

		return additifs;
	}

}
