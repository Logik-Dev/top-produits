/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package top.produits;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import jbdc.Requetes;
import jbdc.Session;
import model.Additif;
import model.Produit;

public class App {

	private Session session = new Session();
	private JSONArray produitsJson;
	private JSONArray additifsJson;

	public void recupererAdditifs() throws MalformedURLException, IOException {

		
		URL additifsURL = new URL("https://fr.openfoodfacts.org/additives.json");
		String additifsString = IOUtils.toString(additifsURL, Charset.forName("UTF-8"));
		this.additifsJson = new JSONObject(additifsString).getJSONArray("tags");

	}

	public void sauvegarderAdditifs() throws SQLException {

		for (Object additifObject : additifsJson) {
			JSONObject additifJsonObject = (JSONObject) additifObject;
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
				Additif additif = new Additif(id, nom, code);
				Requetes.sauvegarderAdditif(session.getConnection(), additif);
			}
		}
	}

	public void recupererProduits() throws IOException {

		URL url = new URL(
				"https://fr.openfoodfacts.org/cgi/search.pl?search_simple=1&action=process&json=1&page_size=1000&sort_by=unique_scans_n");
		String produitsString = IOUtils.toString(url, Charset.forName("UTF-8"));
		this.produitsJson = new JSONObject(produitsString).getJSONArray("products");

	}

	public void sauvegarderProduits() throws SQLException {

		List<Produit> produitList = new ArrayList<>();

		for (Object produitObject : produitsJson) {

			JSONObject produitJsonObject = (JSONObject) produitObject;
			long id = produitJsonObject.getLong("_id");
			String nom = produitJsonObject.getString("product_name_fr").trim().toLowerCase();
			String marque = produitJsonObject.getString("brands");
			Character nutriscore = null;
			JSONArray additifsJsonArray = produitJsonObject.getJSONArray("additives_tags");

			if (produitJsonObject.has("nutriscore_grade")) {
				nutriscore = produitJsonObject.getString("nutriscore_grade").charAt(0);
			}

			if (!nom.isEmpty() && nutriscore != null) {
				Produit produit = new Produit(id, nom, marque, nutriscore);

				if (!produitList.contains(produit)) {
                    produitList.add(produit);
                    
					if (!additifsJsonArray.isEmpty()) {
						Requetes.sauvegarderProduit(session.getConnection(), produit);

						for (Object additifIdObject : additifsJsonArray) {
							String additifId = String.valueOf(additifIdObject);
							Requetes.sauvegarderAdditifProduit(session.getConnection(), additifId, produit.getId());
						}
					}
				}

			}
		}

	}
	
	public void afficheNombreDefiniDeProduit(int nombre) throws SQLException {
		List<Produit> produitList = Requetes.rechercherProduitsParNombreDefini(session.getConnection(), nombre);
		for(Produit produit: produitList) {
			System.out.println(produit.getNom());
		}
	}
	
	
	
	public void afficheProduitParNutriscore(char nutriscore) throws SQLException {
		List<Produit> produitList = Requetes.rechercherProduitsParNutriscore(session.getConnection(), nutriscore);
		for(Produit produit: produitList) {
			System.out.println(produit.getNom());
		}
	}
	
	public void afficheProduitParNom(String nom) throws SQLException {
		List<Produit> produitList = Requetes.rechercherProduitsParNom(session.getConnection(), nom);
		for(Produit produit: produitList) {
			System.out.println(produit.getNom());
		}
	}
	
	public void afficheProduitParAdditif(String codeAdditif) throws SQLException {
		List<Produit> produitList = Requetes.rechercherProduitsParAdditif(session.getConnection(), codeAdditif);
		for(Produit produit: produitList) {
			System.out.println(produit.getNom());
		}
	}
	
	

	public static void main(String[] args) throws MalformedURLException, IOException, SQLException {
		
		App test = new App();
//		test.recupererAdditifs();
//		test.sauvegarderAdditifs();
//		test.recupererProduits();
//		test.sauvegarderProduits();
//		test.afficheProduitParNutriscore('e');
//		test.afficheProduitParNom("aux");
//		test.afficheProduitParAdditif("E579");
//		test.afficheNombreDefiniDeProduit(1);

	}
}
