import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class mortyTest{

	
	//Mortgage calc tests
	
	// double monthlyMortagePayment(double loanAmmount, double monthlyInterest, int payments)
	/*formula:
	 * E = U[b(1 + b)^p]/[(1 + b)^p - 1]
	 * E=montly payement
	 * b=monthly interest
	 * U=total loan
	 * p=number of payments
	 */
	@Test
	public void TestMortageCalcIsCorrect(){
		assertEquals(537.804878049,morty.monthlyMortagePayment(1000,.05,2),.00001);
		assertEquals(0.26379748079,morty.monthlyMortagePayment(1,.1,5),.00001);
	}
	@Test
	public void TestMortageCalcInvalidInput(){
		assertEquals(-1,morty.monthlyMortagePayment(0,.05,2),.00001);
		assertEquals(-1,morty.monthlyMortagePayment(1000,.05,0),.00001);
		assertEquals(-1,morty.monthlyMortagePayment(1000,0,2),.00001);
	}
	
	//datafromfile
	@Test
	public void testDataFromFile(){
		morty.dataFromFile();	
	}
	//printmortagedata
	@Test
	public void TestPrintingData(){
		
	}
	//readfile
	@Test
	public void TestReadFileEmpty(){
		assertEquals(0,morty.readFile("").size());
	}
	@Test
	public void TestReadFile(){
		assertEquals(4,morty.readFile("./Codetest-Mortageplan/prospects.txt").size());
		assertEquals("Karvinen",morty.readFile("./Codetest-Mortageplan/prospects.txt").get(1).name);
	}
	
	
	//helper functions
	@Test
	public void TestYearToMontht(){
		assertEquals(60,morty.yearToMonth(5));
	}
	@Test
	public void TestPercentageToDecimal(){
		assertEquals(.05,morty.percentToDecimal(5),.000001);
	}
}
