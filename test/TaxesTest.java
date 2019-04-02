package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.Factures;

public class TaxesTest {

	Factures facturesTest;
	
	@Before
	public void setUp() throws Exception {
		facturesTest = new Factures();
	}

	@Test
	public void taxesTest() {
		
		//On vérifie si le total est bien taxé
		Double a = 20.0;
		Double b = 23.0;
		assertEquals(b,facturesTest.facturerTaxes(a)[0]);
		
		a = 55.0;
		b = 63.25;
		assertEquals(b,facturesTest.facturerTaxes(a)[0]);
		
		//On vérifie si la TPS est correcte
		a = 30.0;
		b = 1.5;
		assertEquals(b,facturesTest.facturerTaxes(a)[1]);
		
		a = 44.8;
		b = 2.24;
		assertEquals(b,facturesTest.facturerTaxes(a)[1]);
		
		//On vérifie si la TVQ est correcte
		a = 2.44;
		b = 0.24;
		assertEquals(b,facturesTest.facturerTaxes(a)[2]);
		
		a = 16.19;
		b = 1.62;
		assertEquals(b,facturesTest.facturerTaxes(a)[2]);
		
	}

}
