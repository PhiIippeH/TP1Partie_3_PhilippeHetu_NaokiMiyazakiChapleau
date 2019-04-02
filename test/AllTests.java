package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AffichageEtEditionFichier.class, CommandesErreursTest.class, FacturesDeZeroDollars.class,
		TaxesTest.class, CoverageTester.class })
public class AllTests {
	
}
