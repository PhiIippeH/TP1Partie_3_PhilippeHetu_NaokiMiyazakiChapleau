package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
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
	public void test() {
		fail("Not yet implemented");
	}

}
