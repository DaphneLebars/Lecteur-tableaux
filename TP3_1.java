/*
 *Daphné Le Bars Caillé
 **/

import java.util.*;
import java.io.*;

/*Programme pour lire un fichier, */

class Nation implements Comparable<Nation>
{
  private char code;
  private String nom, 
  	             capitale;
  private double superficie,
                 population;
                 
  public Nation(char code, String nom, String capitale, double superficie,
                 double population)
  {
                 	this.code=code;
                 	this.nom=nom;
                 	this.capitale=capitale;
                 	this.superficie=superficie;
                 	this.population=population;
  }
  
  public String toString()
  {
  	return String.format("%1c %30s %25s %12.2f %9.2f", code, nom, capitale, superficie, population);
  }
  
  public char getCode()
  {
  	return this.code;
  }
  
  public String getNom()
  {
  	return this.nom;
  }
  
 	public void setCode(char nouvCode)
 	{
 		this.code = nouvCode;
 	}
 	
 	public void setCapitale(String nouvCapitale)
 	{
 		this.capitale=nouvCapitale;
 	}
 	
 	public void setPopulation( double nouvPop)
 	{
 		this.population = nouvPop;
 	}
 	
 	public double getPopulation()
 	{
 		return this.population;
 	}
 	
 	public int compareTo(Nation pays2 ) 
 		{
        return this.nom.toUpperCase().trim().compareTo( pays2.nom.toUpperCase().trim() );
        }
   
   public void ecrire(DataOutputStream aCreer)
	   throws IOException
	   {	
			aCreer.writeChar(this.code);
			aCreer.writeChars(this.nom);
			
			aCreer.writeDouble(this.superficie);
			aCreer.writeChars(this.capitale);
			aCreer.writeDouble(this.population);
	   }
   
}	
 
	

public class TP3_1
{	
	
	//1) Lit fichier, remplit et retourne vecteur de pays:
	static Vector<Nation> lireEtRemplir(String fichier)
		throws IOException
		{
			Vector<Nation> v = new Vector<Nation>(); //vide
			boolean fichierExiste =true; 
				
			FileReader fr = null;
			
			try{
				fr = new FileReader (fichier);
			}
			
			catch (java.io.FileNotFoundException echec) {
				System.out.println("Probleme a l'ouverture du fichier "+ fichier);
				fichierExiste = false;
			}
			
			if(fichierExiste)
			{
				BufferedReader in = new BufferedReader(fr);
				boolean fichierFin = false;
				
				while(!fichierFin){
					String ligne = in.readLine();
					
					if(ligne == null){
					
						fichierFin=true;
					}
					else{
						char   code = ligne.charAt(0);
                        String nom = ligne.substring(1, 35).trim();  
                        String capitale = ligne.substring(36,50).trim();
                        double superficie = Double.parseDouble(ligne.substring(60,70).trim()),
 				               population= Double.parseDouble(ligne.substring(71, 85).trim());
						
						v.add(new Nation (code, nom, capitale, superficie, population));
					}
				}
			in.close();	
			}
			return v;
		}
		
		//2) Affiche nombre de pays voulu a partir de l'indice 0
		static void afficherVect(Vector<Nation> vectNation, int nbPays)
		{
			for(int i=0; i<nbPays; i++)
			{
				System.out.printf("\n%3d) %s", i, vectNation.get(i));
			}
			System.out.printf("\n");
		}
		
		//Methode pour chercher pays grace a binarySearch et affiche ses infos
		static void chercheNation(Nation aChercher, String pays, Vector<Nation> p)
		{
		
		int k = Collections.binarySearch(p, aChercher);
		
		if(k>=0)
		{
		System.out.printf("\n\nInfos de %s:\n", pays);
		System.out.printf("\n%3d) %s", k, p.get(k));
		}
		else{
			System.out.printf("\n\nPays %s introuvable\n", pays);
		}
		}
		
		
	//Créer fichier binaire à partir d'un vecteur de pays	
	static void creerBinaire(Vector<Nation> vectNation, char codeConti,
                                                   String nomFichier)	
                 throws IOException
	{

	  DataOutputStream aCreer = new DataOutputStream
	                        ( new FileOutputStream(nomFichier));

	  for (int i = 0; i < vectNation.size(); i++)
	  {
	     Nation pays =  vectNation.get(i);
	     if ( pays.getCode() == codeConti){
	     
	  			  pays.ecrire(aCreer);
	     }
	  }

	  aCreer.close();
	  System.out.println("\nOn vient de creer le fichier binaire : " + nomFichier);
	}
		
	
	public static void main (String[] args) 
		throws IOException
		{
		
		//1)Lit fichier,  remplit et retourne vecteur de pays	
		Vector<Nation> vectNation = lireEtRemplir("pays_a17.txt");
		
		//2) Affiche 15 premiers pays
		afficherVect(vectNation, 15); 
			
	    //3) a)change continent de la Russie
			vectNation.elementAt(2).setCode('5');
			//b) change la capitale de la Chine
			vectNation.elementAt(1).setCapitale("PEKIN");
			//c) change la population d'allemagne
			vectNation.elementAt(5).setPopulation(vectNation.elementAt(5).getPopulation()*10);
			//d) supprime pays ouragan
			vectNation.remove(6);
			
			//Affiche 20 premiers pays
			afficherVect(vectNation,20);
			
		//4) Trier selon le nom des pays avec Collection.sort()	
			Collections.sort(vectNation);
			System.out.printf("\n10 premiers pays apres trie:\n");
			afficherVect(vectNation,10);
			
		//5)Recherche avec Collections.BinarySearch:
		
		//Canada
		Nation paysCherche=new Nation('2', "CANADA", "", 0,0);
		chercheNation(paysCherche,"Canada",vectNation);
		
		//France
		paysCherche=new Nation('5', "FRANCE", "", 0,0);
	    chercheNation(paysCherche,"France",vectNation);
		
		//Japon
		paysCherche=new Nation('3', "Japon", "", 0,0);
		chercheNation(paysCherche,"Japon",vectNation);
		
		//Chili
		paysCherche=new Nation('2', "CHILI", "", 0,0);
		chercheNation(paysCherche,"Chili",vectNation);
		
		//Mexique
		paysCherche= new Nation('2', "mexique","",0,0);
		chercheNation(paysCherche, "mexique", vectNation);
		
		//6)Créer fichier binaire Amerique.bin 
		creerBinaire(vectNation, '2',"Amerique.bin");
		
    }
}