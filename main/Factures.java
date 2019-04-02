package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Permet de créer une facture basé sur un fichier texte préformatté.
 * 
 * @author Naoki Miyazaki-Chapleau et Philippe Hétu
 *
 */
public class Factures {
	public List<String> clients = new ArrayList<String>();
	public List<String> plats = new ArrayList<String>();
	public List<String> commandes = new ArrayList<String>();
	public List<String> lignes = new ArrayList<String>();
	public String erreur = null;
	public JTextArea texte = new JTextArea();

	public Factures() {

	}

	public Factures(boolean a) {
		// Dialogue d'ouverture de fichier
		FicLecture fic = new FicLecture();
		lignes = fic.fileChoose();
		lireFic();
		if (erreur == null)
			verifierErreurs();
		if (erreur == null)
			printFacture();
		if (erreur != null)
			System.out.println("Une erreur est survenue: " + erreur);
	}

	/**
	 * Lit et amasse les données des commandes.
	 * 
	 * @see FicLecture
	 */

	public String lireFic() {
		erreur = null;

		// l'index courant de l'itération des lignes du fichier
		int compteur = 0;

		// Le type, c'est-à-dire clients, plats ou commandes
		int typeChose = -1;

		// On vérifie que tous les types de données sont présents
		if (lignes == null)

			// Lignes est null
			return erreur = "Erreur de lecture: Le fichier n'a pas pu être lu.";

		else if (!lignes.contains("Clients :"))

			// Clients
			return erreur = "Format non respecté: La donnée 'clients' n'est pas présente.";

		else if (!lignes.contains("Plats :"))

			// Plats
			return erreur = "Format non respecté: La donnée 'plats' n'est pas présente.";

		else if (!lignes.contains("Commandes :"))

			// Commandes
			return erreur = "Format non respecté: La donnée 'commandes' n'est pas présente.";

		else if (!lignes.get(lignes.size() - 1).equals("Fin"))

			// La dernière ligne n'est pas fin
			return erreur = "Format non respecté: La dernière ligne n'est pas 'fin'.";

		while (compteur < lignes.size()) {

			// La ligne à évaluer
			String a = lignes.get(compteur);

			if (a.indexOf(":") != -1) {

				if (a.equals("Clients :"))
					typeChose = 0;
				else if (a.equals("Plats :"))
					typeChose = 1;
				else if (a.equals("Commandes :"))
					typeChose = 2;
				else
					return erreur = "Format non respecté: Présence d'un mauvais ':'. Reçu: ' " + a + " '";

			} else if (typeChose == -1) {

				// Si le type est -1, la première ligne est une donnée
				return erreur = "Format non respecté: La première ligne est une donnée. Reçu: ' " + a + " '";

			} else if (a.indexOf("Fin") == -1) {

				// Si on n'est pas à la dernière ligne (qui s'intitule "Fin"),
				// ajouter la ligne
				// au type respectif
				if (typeChose == 0) {

					clients.add(a);

				} else if (typeChose == 1) {

					String[] platSplit = a.split(" ");

					if (platSplit.length != 2) {

						return erreur = "Format non respecté: la ligne " + compteur + " ne correspond pas à un plat.";

					} else if (!platSplit[1].matches("^\\d+(\\.\\d+)?$")) {

						return erreur = "Données erronées: le prix du plat " + platSplit[0] + " est invalide.";

					} else {

						plats.add(a);

					}

				} else if (typeChose == 2) {

					String[] comSplit = a.split(" ");

					if (comSplit.length != 3) {

						return erreur = "Format non respecté: la ligne " + compteur
								+ " ne correspond pas à une commande.";

					} else if (!comSplit[2].matches("\\d+")) {

						return erreur = "Données erronées: " + comSplit[2] + " n'est pas une quantité valide.";

					} else {

						commandes.add(a);

					}

				}
			}

			compteur++;

		}
		return erreur;

	}

	/**
	 * Vérifie les potentielles erreurs dans la commande.
	 */

	public String verifierErreurs() {
		
		// Vérification des erreurs dans la liste de commandes
		for (int i = 0; i < commandes.size(); i++) {
			// Commandes est formatté comme suit: <Client> <Plat>
			String[] comSplit = commandes.get(i).split(" ");
			boolean platFound = false;

			// Si il y a 3 données dans la ligne de commande et que la première
			// est un
			// client qui existe dans Clients
			if (comSplit.length == 3 && clients.contains(comSplit[0])) {
				for (int j = 0; j < plats.size(); j++) {

					// Si il y a un plat qui correspond à la deuxième donnée de
					// la commande,
					// c'est-à-dire le plat.
					// plats est composé de deux données, soit le nom du plat et
					// le prix. On cherche
					// seulement le nom, donc split(" ")[0].
					if (plats.get(j).split(" ")[0].equals(comSplit[1]))
						platFound = true;
				}

				// Si le plat n'a pas été trouvé, retourner une erreur
				if (!platFound)
					return erreur = "Le plat " + comSplit[1] + " n'est pas dans le menu.";
			} else {

				// Si le client n'a pas été trouvé, retourner une erreur
				return erreur = "Le client " + comSplit[0] + " n'est pas dans la liste.";
			}
		}
		return erreur;
	}

	/**
	 * Imprime la facture à l'écran.
	 */
	public void printFacture() {
		String temp = "Bienvenue chez Barette!" + "\nFactures :\n";
		// Print la facture
		System.out.println("Bienvenue chez Barette!" + "\nFactures :");

		// Pour chaque client
		for (int i = 0; i < clients.size(); i++) {

			// Le nom du client
			String nom = clients.get(i);
			// Total de la facture
			double total = 0;

			// Pour chaque commande
			for (int j = 0; j < commandes.size(); j++) {

				// On va cherche les 3 données, soit le nom du client, le plat, et la quantité
				String[] comSplit = commandes.get(j).split(" ");

				// On compare le nom du client au nom du client dans la commande
				if (comSplit[0].equals(nom)) {

					// Pour chaque plat
					for (int k = 0; k < plats.size(); k++) {

						// On obtient le nom du plat et le prix du plat
						String[] platSplit = plats.get(k).split(" ");

						// On compare le nom du plat au nom du plat dans la commande
						if (platSplit[0].equals(comSplit[1])) {

							// On rajoute au total l'équation qui est <quantité> * <prix> du plat

							total += Double.parseDouble(comSplit[2]) * Double.parseDouble(platSplit[1]);
						}
					}
				}
			}

			// Il n'y a pas de moyen d'échouer cette étape. L'échouement de cette étape
			// signifie un problème dans la vérification des erreurs ou dans la recherche
			// des commandes dans printFacture()
			if (!FactureZero(total)) {

				Double[] totalAvecTaxes = facturerTaxes(total);
				enregisterFichier(nom + " " + totalAvecTaxes[0] + "$\tTPS: " + totalAvecTaxes[1] + "$\tTVQ: " + totalAvecTaxes[2] + "$\n");
				System.out.println(nom + " " + totalAvecTaxes[0] + "$\tTPS: " + totalAvecTaxes[1] + "$\tTVQ: " + totalAvecTaxes[2] + "$\n");

			}
			
		}
		enregisterFichier(temp);
	}

	public Double[] facturerTaxes(Double prix) {
		return new Double[] {prix*1.15,prix*0.05,prix*0.1};
	}

	public void enregisterFichier(String tx) {

		texte.setText(tx);
		// Instance de Date
		Date now = new Date();

		// Formatter la date
		SimpleDateFormat dateFormatter = new SimpleDateFormat("y-M-d'-'h-m-s");
		PrintWriter pw = null;

		try {

			pw = new PrintWriter(new FileWriter("Facture-du-" + dateFormatter.format(now) + ".txt"));

			pw.write(texte.getText());

		} catch (IOException exc) {

			JOptionPane.showMessageDialog(null, exc.getMessage()/*
																 * texte, "Probl\u00E8me d'enregistrement du fichier"
																 */ );

		} finally {// pour garantir la fermeture qu'une exception ait
					// été déclenchée ou non

			if (pw != null)

				pw.close();

		}

	}

	public static boolean FactureZero(double total) {
		boolean temp;

		if (total == 0) {

			temp = true;

		} else {

			temp = false;

		}

		return temp;
	}

}
