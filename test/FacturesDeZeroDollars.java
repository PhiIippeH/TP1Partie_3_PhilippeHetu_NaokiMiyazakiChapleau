package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Factures;

public class FacturesDeZeroDollars {

	@Test
	public void testFactureZero() {
		
		double a = 0;
		//Le chiffre est 0
		assertTrue( Factures.FactureZero( a ) );
		
		a = 0.0;
		//Le chiffre est un double 0.0
		assertTrue( Factures.FactureZero( a ) );
		
		a = -1;
		//Avec un négatif
		assertFalse( Factures.FactureZero( a ) );
		
		a = 2;
		//Avec un chiffre autre que 0
		assertFalse( Factures.FactureZero( a ) );
		
		a = 0.1;
		//Avec un chiffre à virgule
		assertFalse( Factures.FactureZero( a ) );
		
	}

}
