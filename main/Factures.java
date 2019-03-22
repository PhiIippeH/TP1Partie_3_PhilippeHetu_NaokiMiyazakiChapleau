package main;

import java.util.*;

/**
 * Permet de créer une facture basé sur un fichier texte préformatté.
 * @author Nuvm
 *
 */
public class Factures {
	// Philippe et Naoki
	private List<String> clients = new ArrayList<String>();
	private List<String> plats = new ArrayList<String>();
	private List<String> commandes = new ArrayList<String>();
	private String erreur = "";

	public Factures() {
		if (lireFic() != null) {
			verifierErreurs();
			printFacture();
		} else {
			System.out.println(erreur);
		}
	}
/**
 * Lit un fichier en utilisant la classe externe FicLecture et amasse les données des commandes.
 * @return Une liste qui contient clients, plats et commandes.
 * @see FicLecture
 */
	public List<List<String>> lireFic() {
		erreur = "";
		// Dialogue d'ouverture de fichier
		FicLecture fic = new FicLecture();
		List<String> lignes = fic.fileChoose();

		// Fin du while
		boolean fin = false;

		// l'index courant de l'itération des lignes du fichier
		int compteur = 0;

		// Le type, c'est-à-dire clients, plats ou commandes
		int typeChose = -1;
		if (lignes != null) {
			while (compteur < lignes.size() && !fin) {

				// La ligne à évaluer
				String a = lignes.get(compteur);

				if (a.indexOf(":") != -1) {

					// Si il y a un :, on procède au prochain type d'objet (Ex.: "Menu:")
					typeChose++;

				} else if (typeChose == -1) {

					// Si le type est -1, la donnée client n'a jamais été présente
					fin = true;
					erreur = "Format non respecté: La donnée client est indisponible.";

				} else if (a.indexOf("Fin") == -1) {

					// Si on n'est pas à la dernière ligne (qui s'intitule "fin"), ajouter la ligne
					// au type respectif
					if (typeChose == 0)
						clients.add(a);

					else if (typeChose == 1)
						plats.add(a);

					else if (typeChose == 2)
						commandes.add(a);

					// Si le type est au-dessus de 2, il y a une anomalie
					else
						erreur = "Format non respecté: 'Fin' n'est pas la dernière ligne.";

				}

				compteur++;

			}
			//TODO Remplacer ce bloc pour renvoyer les données nécessaires au testage de la méthode et remplacer le return de la méthode (en haut) conséquemment
			List<List<String>> total = new ArrayList<List<String>>();
			total.add(clients);
			total.add(plats);
			total.add(commandes);

			return total;

		} else {
			erreur = "Veuillez choisir un fichier.";
		}
		return null;

	}
	
	/**
	 * Vérifie les potentielles erreurs dans la commande.
	 */
	public void verifierErreurs() {
		// Vérification des erreurs dans la liste de commandes
		for (int i = 0; i < commandes.size(); i++) {
			//Commandes est formatté comme suit: <Client> <Plat> 
			String[] comSplit = commandes.get(i).split(" ");
			boolean platFound = false;
			
			//Si il y a 3 données dans la ligne de commande et que la première est un client qui existe dans Clients
			if (comSplit.length == 3 && clients.contains(comSplit[0])) {
				for (int j = 0; j < plats.size(); j++) {
					
					//Si il y a un plat qui correspond à la deuxième donnée de la commande, c'est-à-dire le plat.
					//plats est composé de deux données, soit le nom du plat et le prix. On cherche seulement le nom, donc split(" ")[0].
					if (plats.get(j).split(" ")[0].equals(comSplit[1]))
						platFound = true;
				}
				//Si le plat n'a pas été trouvé, retourner une erreur
				if (!platFound)
					erreur = "Le plat n'est pas dans le menu.";
			} else {
				//Si le client n'a pas été trouvé, retourner une erreur
				erreur = "Ce client n'est pas dans la liste.";
			}
		}
	}

	/**
	 * Imprime la facture à l'écran.
	 */
	public void printFacture() {
		// Print la facture
		
		//Si il y a une erreur, afficher l'erreur
		//TODO Deprecated. Il faut implémenter un moyen de retourner les erreurs durant le processus plutôt qu'à la dernière méthode du programme.
		if (erreur != "") {
			System.out.println("ERREUR: " + erreur);
		} else {
			System.out.println("Bienvenue chez Barette!" + "\nFactures :");
			//Pour chaque client
			for (int i = 0; i < clients.size(); i++) {
				//Le nom du client
				String nom = clients.get(i);
				//Total de la facture
				double total = 0;
				//Pour chaque commande
				for (int j = 0; j < commandes.size(); j++) {
					//On va cherche les 3 données, soit le nom du client, le plat, et la quantité
					String[] comSplit = commandes.get(j).split(" ");
					//On compare le nom du client au nom du client dans la commande
					if (comSplit[0].equals(nom)) {
						//Pour chaque plat
						for (int k = 0; k < plats.size(); k++) {
							//On obtient le nom du plat et le prix du plat
							String[] platSplit = plats.get(k).split(" ");
							//On compare le nom du plat au nom du plat dans la commande
							if (platSplit[0].equals(comSplit[1])) {
								//On rajoute au total l'équation qui est <quantité> * <prix> du plat
								total += Double.parseDouble(comSplit[2]) * Double.parseDouble(platSplit[1]);
							}
						}
					}
				}
				//Il n'y a pas de moyen d'échouer cette étape. L'échouement de cette étape signifie un problème dans la vérification des erreurs ou dans la recherche des commandes dans printFacture()
				System.out.println(nom + " " + total + "$");
			}
		}
	}
}
