package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Permet de cr�er une facture bas� sur un fichier texte pr�formatt�.
 * 
 * @author Naoki Miyazaki-Chapleau et Philippe H�tu
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
	 * Lit et amasse les donn�es des commandes.
	 * 
	 * @see FicLecture
	 */

	public String lireFic() {
		erreur = null;

		// l'index courant de l'it�ration des lignes du fichier
		int compteur = 0;

		// Le type, c'est-�-dire clients, plats ou commandes
		int typeChose = -1;

		// On v�rifie que tous les types de donn�es sont pr�sents
		if (lignes == null)

			// Lignes est null
			return erreur = "Erreur de lecture: Le fichier n'a pas pu �tre lu.";

		else if (!lignes.contains("Clients :"))

			// Clients
			return erreur = "Format non respect�: La donn�e 'clients' n'est pas pr�sente.";

		else if (!lignes.contains("Plats :"))

			// Plats
			return erreur = "Format non respect�: La donn�e 'plats' n'est pas pr�sente.";

		else if (!lignes.contains("Commandes :"))

			// Commandes
			return erreur = "Format non respect�: La donn�e 'commandes' n'est pas pr�sente.";

		else if (!lignes.get(lignes.size() - 1).equals("Fin"))

			// La derni�re ligne n'est pas fin
			return erreur = "Format non respect�: La derni�re ligne n'est pas 'fin'.";

		while (compteur < lignes.size()) {

			// La ligne � �valuer
			String a = lignes.get(compteur);

			if (a.indexOf(":") != -1) {

				if (a.equals("Clients :"))
					typeChose = 0;
				else if (a.equals("Plats :"))
					typeChose = 1;
				else if (a.equals("Commandes :"))
					typeChose = 2;
				else
					return erreur = "Format non respect�: Pr�sence d'un mauvais ':'. Re�u: ' " + a + " '";

			} else if (typeChose == -1) {

				// Si le type est -1, la premi�re ligne est une donn�e
				return erreur = "Format non respect�: La premi�re ligne est une donn�e. Re�u: ' " + a + " '";

			} else if (a.indexOf("Fin") == -1) {

				// Si on n'est pas � la derni�re ligne (qui s'intitule "Fin"),
				// ajouter la ligne
				// au type respectif
				if (typeChose == 0) {

					clients.add(a);

				} else if (typeChose == 1) {

					String[] platSplit = a.split(" ");

					if (platSplit.length != 2) {

						return erreur = "Format non respect�: la ligne " + compteur + " ne correspond pas � un plat.";

					} else if (!platSplit[1].matches("^\\d+(\\.\\d+)?$")) {

						return erreur = "Donn�es erron�es: le prix du plat " + platSplit[0] + " est invalide.";

					} else {

						plats.add(a);

					}

				} else if (typeChose == 2) {

					String[] comSplit = a.split(" ");

					if (comSplit.length != 3) {

						return erreur = "Format non respect�: la ligne " + compteur
								+ " ne correspond pas � une commande.";

					} else if (!comSplit[2].matches("\\d+")) {

						return erreur = "Donn�es erron�es: " + comSplit[2] + " n'est pas une quantit� valide.";

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
	 * V�rifie les potentielles erreurs dans la commande.
	 */

	public String verifierErreurs() {
		
		// V�rification des erreurs dans la liste de commandes
		for (int i = 0; i < commandes.size(); i++) {
			// Commandes est formatt� comme suit: <Client> <Plat>
			String[] comSplit = commandes.get(i).split(" ");
			boolean platFound = false;

			// Si il y a 3 donn�es dans la ligne de commande et que la premi�re
			// est un
			// client qui existe dans Clients
			if (comSplit.length == 3 && clients.contains(comSplit[0])) {
				for (int j = 0; j < plats.size(); j++) {

					// Si il y a un plat qui correspond � la deuxi�me donn�e de
					// la commande,
					// c'est-�-dire le plat.
					// plats est compos� de deux donn�es, soit le nom du plat et
					// le prix. On cherche
					// seulement le nom, donc split(" ")[0].
					if (plats.get(j).split(" ")[0].equals(comSplit[1]))
						platFound = true;
				}

				// Si le plat n'a pas �t� trouv�, retourner une erreur
				if (!platFound)
					return erreur = "Le plat " + comSplit[1] + " n'est pas dans le menu.";
			} else {

				// Si le client n'a pas �t� trouv�, retourner une erreur
				return erreur = "Le client " + comSplit[0] + " n'est pas dans la liste.";
			}
		}
		return erreur;
	}

	/**
	 * Imprime la facture � l'�cran.
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
					// �t� d�clench�e ou non

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
