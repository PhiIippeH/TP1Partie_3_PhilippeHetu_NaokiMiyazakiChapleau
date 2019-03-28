package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Factures;

public class FacturesTest {
	
	Factures facturesTest;
	
	@Before
	public void beforeTest() throws Exception {
		facturesTest = new Factures();
	}
	
	@After
	public void afterTest() throws Exception {
		facturesTest.lignes.clear();
		facturesTest.clients.clear();
		facturesTest.commandes.clear();
		facturesTest.plats.clear();
		facturesTest.erreur=null;
	}
	
	@Test
	public void lireFicTest() {
		//facturesTest.clients.addAll(Arrays.asList("Roger","Céline","Steeve"));
		//facturesTest.plats.addAll(Arrays.asList("Poutine 10.5","Frites 2.5","Repas_Poulet 15.75"));
		//facturesTest.commandes.addAll(Arrays.asList("Roger Poutine 1","Céline Frites 2","Céline Repas_Poulet 1"));
		//facturesTest.lignes.addAll(Arrays.asList("Clients :","Roger","Céline","Steeve","Plats :","Poutine 10.5","Frites 2.5","Repas_Poulet 15.75","Commandes :","Roger Poutine 1","Céline Frites 2","Céline Repas_Poulet 1","Fin"));
		facturesTest.lignes.addAll(Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine 1","Fin"));
		assertNull(facturesTest.lireFic());
	}

}
