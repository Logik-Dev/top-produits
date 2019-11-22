package api;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import model.Additif;
import model.Produit;

/**
 * Effectue les requètes sur l'API d'OpenFoodFact
 * 
 * @author Elodie, Bastien, Cédric
 *
 */
public class RequetesAPI {
	
	/**
	 * Obtenir la liste des additifs depuis l'API 
	 * @return Une ArrayList d'objets Additif
	 */
	public static List<Additif> obtenirListeAdditifs() {

		JSONArray additifsJsonArray = null;
		
		try {
			URL additifsURL = new URL("https://fr.openfoodfacts.org/additives.json");
			String additifsString = IOUtils.toString(additifsURL, StandardCharsets.UTF_8);
			additifsJsonArray = new JSONObject(additifsString).getJSONArray("tags");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste des additifs depuis l'API.");
		}

		return ExtracteurJSON.extraireListeAdditifs(additifsJsonArray);
	}

	/**
	 * Rechecher un produit sur l'API
	 * @param nom Le nom du produit à rechercher
	 * @param produitsDejaPresent La liste des produits déja présent en base de donnée
	 * @return Une ArrayList d'objets Produit correspondant à la recherche
	 */
	public static List<Produit> rechercherProduitsParNom(String nom, List<Produit> produitsDejaPresent) {

		String urlString = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + nom
				+ "&search_simple=1&action=process&json=1&page_size=1000";
		URL url;
		JSONArray produitsJsonArray = null;

		try {
			url = new URL(urlString);
			String produitsString = IOUtils.toString(url, StandardCharsets.UTF_8);
			produitsJsonArray = new JSONObject(produitsString).getJSONArray("products");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste de produits depuis l'API.");
		}

		return ExtracteurJSON.extraireListeProduits(produitsJsonArray, produitsDejaPresent);

	}

	/**
	 * Obtenir la liste des produits le plus populaires depuis L'API
	 * @return Une ArrayList d'objets Produit
	 */
	public static List<Produit> obtenirListeTopProduits() {

		JSONArray produitsJsonArray = null;

		try {
			URL url = new URL(
					"https://fr.openfoodfacts.org/cgi/search.pl?search_simple=1&action=process&json=1&page_size=1000&sort_by=unique_scans_n");
			String produitsString = IOUtils.toString(url, StandardCharsets.UTF_8);
			produitsJsonArray = new JSONObject(produitsString).getJSONArray("products");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossible d'obtenir la liste de produits depuis l'API.");
		}

		return ExtracteurJSON.extraireListeProduits(produitsJsonArray, new ArrayList<Produit>());

	}

}
