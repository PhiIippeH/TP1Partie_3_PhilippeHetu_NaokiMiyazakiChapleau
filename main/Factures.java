package main;

import java.util.*;

/**
 * Permet de cr�er une facture bas� sur un fichier texte pr�formatt�.
 * 
 * @author Naoki Miyazaki-Chapleau et Philippe H�tu
 *
 */
public class Factures {
	private List<String> clients = new ArrayList<String>();
	private List<String> plats = new ArrayList<String>();
	private List<String> commandes = new ArrayList<String>();
	private List<String> lignes;
	private String erreur = "";

	public Factures() {
		
	}
	
	public Factures(boolean a) {
		// Dialogue d'ouverture de fichier
		FicLecture fic = new FicLecture();
		setLignes(fic.fileChoose());
	}

	public void setLignes(List<String> testLignes) {
		lignes = new ArrayList<String>();
	}

	/**
	 * Lit et amasse les donn�es des commandes.
	 * 
	 * @see FicLecture
	 */
	
	public String lireFic() {
		erreur = null;
		

		// Fin du while
		boolean fin = false;

		// l'index courant de l'it�ration des lignes du fichier
		int compteur = 0;

		// Le type, c'est-�-dire clients, plats ou commandes
		int typeChose = -1;
		if (lignes != null) {
			while (compteur < lignes.size() && !fin) {

				// La ligne � �valuer
				String a = lignes.get(compteur);

				if (a.indexOf(":") != -1) {

					// Si il y a un :, on proc�de au prochain type d'objet (Ex.: "Menu:")
					typeChose++;

				} else if (typeChose == -1) {

					// Si le type est -1, la donn�e client n'a jamais �t� pr�sente
					fin = true;
					return "Format non respect�: La donn�e client est indisponible.";

				} else if (a.indexOf("Fin") == -1) {

					// Si on n'est pas � la derni�re ligne (qui s'intitule "fin"), ajouter la ligne
					// au type respectif
					if (typeChose == 0)
						clients.add(a);

					else if (typeChose == 1)
						plats.add(a);

					else if (typeChose == 2)
						commandes.add(a);

					// Si le type est au-dessus de 2, il y a une anomalie
					else
						return "Format non respect�: 'Fin' n'est pas la derni�re ligne.";

				}

				compteur++;

			}

		} else {
			return "Erreur de lecture: lignes est null.";
		}
		return erreur;

	}

	/**
	 * V�rifie les potentielles erreurs dans la commande.
	 */

	public String verifierErreurs() {
		
		// V�rification des erreurs dans la liste de commandes
		for (int i = 0; i < commandes.size(); i++) {
			// Commandes est formatt� comme suit: <Client> <Plat>
			String[] comSplit = commandes.get(i).split(" ");
			boolean platFound = false;

			// Si il y a 3 donn�es dans la ligne de commande et que la premi�re est un
			// client qui existe dans Clients
			if (comSplit.length == 3 && clients.contains(comSplit[0])) {
				for (int j = 0; j < plats.size(); j++) {

					// Si il y a un plat qui correspond � la deuxi�me donn�e de la commande,
					// c'est-�-dire le plat.
					// plats est compos� de deux donn�es, soit le nom du plat et le prix. On cherche
					// seulement le nom, donc split(" ")[0].
					if (plats.get(j).split(" ")[0].equals(comSplit[1]))
						platFound = true;
				}
				// Si le plat n'a pas �t� trouv�, retourner une erreur
				if (!platFound)
					erreur = "Le plat n'est pas dans le menu.";
			} else {
				// Si le client n'a pas �t� trouv�, retourner une erreur
				erreur = "Ce client n'est pas dans la liste.";
			}
		}
		return erreur;
	}

	/**
	 * Imprime la facture � l'�cran.
	 */
	public void printFacture() {
		// Print la facture

		// Si il y a une erreur, afficher l'erreur
		// TODO Deprecated. Il faut impl�menter un moyen de retourner les erreurs durant
		// le processus plut�t qu'� la derni�re m�thode du programme.
		
		if (erreur != "") {
			System.out.println("ERREUR: " + erreur);
		} else {
			System.out.println("Bienvenue chez Barette!" + "\nFactures :");
			// Pour chaque client
			for (int i = 0; i < clients.size(); i++) {
				// Le nom du client
				String nom = clients.get(i);
				// Total de la facture
				double total = 0;
				// Pour chaque commande
				for (int j = 0; j < commandes.size(); j++) {
					// On va cherche les 3 donn�es, soit le nom du client, le plat, et la quantit�
					String[] comSplit = commandes.get(j).split(" ");
					// On compare le nom du client au nom du client dans la commande
					if (comSplit[0].equals(nom)) {
						// Pour chaque plat
						for (int k = 0; k < plats.size(); k++) {
							// On obtient le nom du plat et le prix du plat
							String[] platSplit = plats.get(k).split(" ");
							// On compare le nom du plat au nom du plat dans la commande
							if (platSplit[0].equals(comSplit[1])) {
								// On rajoute au total l'�quation qui est <quantit�> * <prix> du plat
								total += Double.parseDouble(comSplit[2]) * Double.parseDouble(platSplit[1]);
							}
						}
					}
				}
				// Il n'y a pas de moyen d'�chouer cette �tape. L'�chouement de cette �tape
				// signifie un probl�me dans la v�rification des erreurs ou dans la recherche
				// des commandes dans printFacture()
				System.out.println(nom + " " + total + "$");
			}
		}
	}
}
