package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import main.Factures;

class TaxesTest {

	Factures facturesTest;
	
	@Before
	void setUp() throws Exception {
		facturesTest = new Factures();
	}

	@Test
	void taxesTest() {
		
		//Taxes valides
		facturesTest.lignes = Arrays.asList("Clients :", "Roger", "Plats :", "Poutine 10.5", "Commandes :",
				"Roger Poutine 1", "Fin");
		facturesTest.lireFic();
		facturesTest.printFacture();
		//assertEquals
		resetDonnees();
		
	}
	
	@Ignore
	void resetDonnees() {
		facturesTest.lignes = new ArrayList<String>();
		facturesTest.clients = new ArrayList<String>();
		facturesTest.commandes = new ArrayList<String>();
		facturesTest.plats = new ArrayList<String>();
		facturesTest.erreur = null;
	}

}
