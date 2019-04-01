package test;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.Factures;

public class AffichageEtEditionFichier {

	Factures fic;

	@Before
	public void setUp() {
		fic = new Factures();
	}

	@After
	public void tearDown() {
		fic.clients.clear();
		fic.commandes.clear();
		fic.lignes.clear();
		fic.plats.clear();
	}

	public AffichageEtEditionFichier() {

	}

	@Test
	public void testEnregistrer() {

		// Instance de Date
		Date now = new Date();

		// Formatter la date
		SimpleDateFormat dateFormatter = new SimpleDateFormat( "y-M-d'-'h-m-s" );

		//Test pour savoir si le fichier est pareil lors de l'ouverture.
		fic.enregisterFichier( "Facture-du-" + dateFormatter.format( now ) + ".txt" );
		assertEquals(ouvrir("Facture-du-" + dateFormatter.format( now ) + ".txt"), fic.texte.getText() );

	}

	public String ouvrir(String tx) {
		
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat( "y-M-d'-'h-m-s" );
		JTextArea texte = new JTextArea();
		texte.setText( tx );
		String chaine = "";
		BufferedReader br = null;

			try {

				br = new BufferedReader( new FileReader( "Facture-du-" + dateFormatter.format( now ) + ".txt" ) );
				String ligne;
				while ( ( ligne = br.readLine() ) != null ) {
					chaine = ligne;
				}
				texte.setText( chaine );

			} catch ( FileNotFoundException e ) {
				JOptionPane.showMessageDialog( texte, "Fichier non trouv\u00E9" );
			} catch ( IOException e ) {
				JOptionPane.showMessageDialog( texte, "Probl\u00E9me de lecture du fichier" );
			} finally {
				try {
					br.close();
				} catch ( IOException e ) {
					JOptionPane.showMessageDialog( texte, "Probl\u00E9me de fermeture du flux" );
				}
			}

		
		return texte.getText();
	}

}
