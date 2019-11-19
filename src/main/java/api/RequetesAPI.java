package api;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import model.Additif;
import model.Produit;

public class RequetesAPI {

	public static List<Additif> obtenirListeAdditifs() {

		JSONArray additifsJsonArray = null;

		try {
			URL additifsURL = new URL("https://fr.openfoodfacts.org/additives.json");
			String additifsString = IOUtils.toString(additifsURL, Charset.forName("UTF-8"));
			additifsJsonArray = new JSONObject(additifsString).getJSONArray("tags");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste des additifs depuis l'API.");
		}

		return ExtracteurJSON.extraireListeAdditifs(additifsJsonArray);
	}

	public static List<Produit> rechercherProduitsParNom(String nom) {

		String urlString = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + nom
				+ "&search_simple=1&action=process&json=1&page_size=1000";
		URL url = null;
		JSONArray produitsJsonArray = null;

		try {
			url = new URL(urlString);
			String produitsString = IOUtils.toString(url, Charset.forName("UTF-8"));
			produitsJsonArray = new JSONObject(produitsString).getJSONArray("products");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste de produits depuis l'API.");
		}

		return ExtracteurJSON.extraireListeProduits(produitsJsonArray);

	}

	public static List<Produit> obtenirListeTopProduits() {

		JSONArray produitsJsonArray = null;

		try {
			URL url = new URL(
					"https://fr.openfoodfacts.org/cgi/search.pl?search_simple=1&action=process&json=1&page_size=1000&sort_by=unique_scans_n");
			String produitsString = IOUtils.toString(url, Charset.forName("UTF-8"));
			produitsJsonArray = new JSONObject(produitsString).getJSONArray("products");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste de produits depuis l'API.");
		}

		return ExtracteurJSON.extraireListeProduits(produitsJsonArray);

	}

}
