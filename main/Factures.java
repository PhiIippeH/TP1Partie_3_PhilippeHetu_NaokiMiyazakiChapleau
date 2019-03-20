package main;

import java.util.*;

public class Factures {
//Philippe et Naoki
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FicLecture fic = new FicLecture();
		List<String> lignes = fic.fileChoose();
		List<String> clients = new ArrayList<String>();
		List<String> plats = new ArrayList<String>();
		List<String> commandes = new ArrayList<String>();
		boolean fin = false;
		boolean erreur = false;
		int compteur = 0;
		int typeChose = -1;
		while(compteur<lignes.size()&&!fin) {
			String a = lignes.get(compteur);
			if(a.indexOf(":")!=-1) {
				typeChose++;
			} else if(typeChose==-1) {
				fin=true;
				erreur = true;
			} else if(a.indexOf("Fin")==-1){
				if(typeChose==0)
					clients.add(a);
				else if(typeChose==1)
					plats.add(a);
				else if(typeChose==2)
					commandes.add(a);
				else
					erreur=true;
				
			}
			compteur++;
		}
		//V�rification des erreurs dans la liste de commandes
		for(int i=0;i<commandes.size();i++) {
			String[] comSplit = commandes.get(i).split(" ");
			boolean platFound = false;
			if(comSplit.length==3&&clients.contains(comSplit[0])) {
				for(int j=0;j<plats.size();j++) {
					if(plats.get(j).split(" ")[0].equals(comSplit[1]))
						platFound=true;
				}
				if(!platFound)
					erreur=true;
			} else {
				erreur=true;
			}
		}
		
		//Print la facture
		if(erreur) {
			System.out.println("Le fichier ne respecte pas le format demand\u00e9!");
		} else {
			System.out.println("Bienvenue chez Barette!"
					+"\nFactures :");
			for(int i=0;i<clients.size();i++) {
				String nom = clients.get(i);
				double total = 0;
				for(int j=0;j<commandes.size();j++) {
					String[] comSplit = commandes.get(j).split(" ");
					if(comSplit[0].equals(nom)) {
						for(int k=0;k<plats.size();k++) {
							String[] platSplit = plats.get(k).split(" ");
							if(platSplit[0].equals(comSplit[1])) {
								total+=Double.parseDouble(comSplit[2])*Double.parseDouble(platSplit[1]);
							}
						}
					}
				}
				System.out.println(nom+" "+total+"$");
			}
		}
			
	}
}
