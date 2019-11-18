package model;


	/**
	 * Une classe pour représenter la table produit.
	 * 
	 * @author Elodie, Cedric, Bastien
	 *
	 */
	public class Produit {
		
		private long id;
		private String nom;
		private String marque;
		private char nutriscore;
		
		
		public Produit(int id, String nom, String marque, char nutriscore) {
			this.id = id;
			this.nom = nom;
			this.marque = marque;
			this.nutriscore = nutriscore;
		}


		@Override
		public String toString() {
			return "Produit [id=" + id + ", nom=" + nom + ", marque=" + marque + ", nutriscore=" + nutriscore + "]";
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
		
		
		
		
	}





