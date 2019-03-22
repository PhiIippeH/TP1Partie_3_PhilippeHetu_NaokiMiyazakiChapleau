package main;


import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe qui permet d'ouvrir un fichier texte avec une fenêtre Swing.
 * @author Nuvm
 *
 */
public class FicLecture {

	/**
	 * Le constructeur vide. Il permet d'accéder aux méthodes offertes par cette classe.
	 */
	public FicLecture() {
		
	}
	/**
	 * Permet à l'utilisateur de choisir un fichier texte de amnière visuelle.
	 * @return Une liste contenant un string correspondant à chaque ligne du fichier.
	 */
	public List<String> fileChoose(){
		// TODO Auto-generated method stub
		List<String> lignes = new ArrayList<String>();
		boolean old = UIManager.getBoolean("FileChooser.readOnly");
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser jFileChooser = new JFileChooser();
		UIManager.put("FileChooser.readOnly", old);
		jFileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
		if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Path file = Paths.get(jFileChooser.getSelectedFile().getPath());
			try {
				lignes = Files.readAllLines(file,Charset.forName("ISO-8859-1"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Veuillez choisir un fichier.");
			return null;
		}
		return lignes;
	}

}
