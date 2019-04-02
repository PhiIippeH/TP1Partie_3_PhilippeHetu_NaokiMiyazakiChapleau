package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import main.Factures;

public class CommandesErreursTest {

	Factures facturesTest;

	@Before
	public void setUp() throws Exception {
		facturesTest = new Factures();
	}

	@Test
	public void nomsExistentTest() {

		// Le client dans les commandes n'existe pas
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Bob Poutine 1", "Fin");
		facturesTest.lireFic();
		assertNotNull(facturesTest.verifierErreurs());
		resetDonnees();

		// Le plat n'existe pas
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Banane 1", "Fin");
		facturesTest.lireFic();
		assertNotNull(facturesTest.verifierErreurs());
		resetDonnees();

	}

	@Test
	public void formatValideTest() {
		// Données valides
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine 1", "Fin");
		assertNull(facturesTest.lireFic());
		resetDonnees();

		// Manque la fin
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine 1");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Manque clients
		facturesTest.lignes = Arrays.asList("Plats :", "Poutine 10.5", "Commandes :", "Roger Poutine 1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Manque plats
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Commandes :", "Roger Poutine 1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Manque commandes
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Un ":" qui n'a pas lieu
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger: Poutine 1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Lignes est vide
		facturesTest.lignes = Arrays.asList("");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// La première ligne est une donnée
		facturesTest.lignes = Arrays.asList("Joséphine", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine 1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Lignes est null
		facturesTest.lignes = null;
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
	}

	@Test
	public void chiffresErrones() {
		// Plats prix negatif
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine -10.5", "Commandes :",
				"Roger Poutine 1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Commandes quantite negative
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine -1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();

		// Commandes quantite negative
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine -1", "Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
	}

	@Ignore
	public void resetDonnees() {
		facturesTest.lignes = new ArrayList<String>();
		facturesTest.clients = new ArrayList<String>();
		facturesTest.commandes = new ArrayList<String>();
		facturesTest.plats = new ArrayList<String>();
		facturesTest.erreur = null;
	}

}
