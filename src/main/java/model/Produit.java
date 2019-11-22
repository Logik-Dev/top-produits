package model;

import java.util.List;

/**
 * Classe qui représente un produit
 * @author Elodie, Bastien, Cédric
 */
	public class Produit {
		
		private long id;
		private String nom;
		private String marque;
		private char nutriscore;
		private List<String> additifs;
		
		public Produit(){
			
		}
		
		public Produit(long id, String nom, String marque, char nutriscore, List<String> additifs) {
			
			this.id = id;
			this.nom = nom;
			this.marque = marque;
			this.nutriscore = nutriscore;
			this.additifs = additifs;
		}

		@Override
		public String toString() {
			return "\n" + nom.toUpperCase() + "\n- id: " + id + "\n- marque: " + marque + "\n- nutriscore: " + nutriscore + "";
		}
		
		@Override
		public boolean equals(Object o) {
		
		Produit other = (Produit) o;
		return other.getNom().equalsIgnoreCase(this.nom);

		}
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getMarque() {
			return marque;
		}

		public void setMarque(String marque) {
			this.marque = marque;
		}

		public char getNutriscore() {
			return nutriscore;
		}

		public void setNutriscore(char nutriscore) {
			this.nutriscore = nutriscore;
		}
		
		public void setAdditifs(List<String> additifs) {
			this.additifs = additifs;
		}
		
		public List<String> getAdditifs(){
			return this.additifs;
		}
		
		
	}





