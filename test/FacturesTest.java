package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
		resetDonnees();
	}
	
	@Test
	public void lireFicTest() {
		//facturesTest.clients.addAll(Arrays.asList("Roger","Céline","Steeve"));
		//facturesTest.plats.addAll(Arrays.asList("Poutine 10.5","Frites 2.5","Repas_Poulet 15.75"));
		//facturesTest.commandes.addAll(Arrays.asList("Roger Poutine 1","Céline Frites 2","Céline Repas_Poulet 1"));
		//facturesTest.lignes.addAll(Arrays.asList("Clients :","Roger","Céline","Steeve","Plats :","Poutine 10.5","Frites 2.5","Repas_Poulet 15.75","Commandes :","Roger Poutine 1","Céline Frites 2","Céline Repas_Poulet 1","Fin"));
		
		//Données valides
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine 1","Fin");
		assertNull(facturesTest.lireFic());
		resetDonnees();
		
		//Manque la fin
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine 1");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Manque clients
		facturesTest.lignes = Arrays.asList("Plats :","Poutine 10.5","Commandes :","Roger Poutine 1","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Manque plats
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Commandes :","Roger Poutine 1","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Manque commandes
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Un ":" qui n'a pas lieu
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger: Poutine 1","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Lignes est vide
		facturesTest.lignes = Arrays.asList("");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//La première ligne est une donnée
		facturesTest.lignes = Arrays.asList("Joséphine","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine 1","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Lignes est null
		facturesTest.lignes = null;
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
	}
	
	@Test
	public void verifierErreursTest() {
		
		//Données valides
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine 1","Fin");
		facturesTest.lireFic();
		assertNull(facturesTest.verifierErreurs());
		resetDonnees();
		
		//Plats prix negatif
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine -10.5","Commandes :","Roger Poutine 1","Fin");
		facturesTest.lireFic();
		assertNotNull(facturesTest.verifierErreurs());
		resetDonnees();
		
		//Commandes quantite negative
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine -1","Fin");
		assertNotNull(facturesTest.lireFic());
		resetDonnees();
		
		//Commandes quantite negative
		facturesTest.lignes = Arrays.asList("Clients :","Roger","Plats :","Poutine 10.5","Commandes :","Roger Poutine -1","Fin");
		facturesTest.lireFic();
		assertNotNull(facturesTest.verifierErreurs());
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
