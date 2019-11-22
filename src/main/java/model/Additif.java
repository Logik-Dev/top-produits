package model;

/**
 * Classe qui représente un additif
 * @author Elodie, Bastien, Cédric
 */

public class Additif {

	private String id;
	private String nom;
	private String code;
	
	public Additif(String id, String nom, String code) {
		this.id = id;
		this.nom = nom;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
	
	
	
}
