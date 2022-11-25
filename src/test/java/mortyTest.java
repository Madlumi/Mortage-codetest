import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;

import morty.morty; 
import org.junit.Test;
public class mortyTest{

	private String testlListPath="./test_prospects.txt";
	@Test
	public void Test_GetProspects_noList(){
		morty.setProspList("");
		assertEquals(0, morty.getProspects().size());
	}
	@Test
	public void Test_GetProspects_testList(){
		morty.setProspList(testlListPath);
		assertEquals(4, morty.getProspects().size());
	}
	
	
	@Test
	public void Test_DataFromFile_noFile(){
		morty.setProspList("");
		assertEquals(0, morty.dataFromFile().size());
	}
	@Test
	public void Test_DataFromFile_testFile(){
		morty.setProspList(testlListPath);
		assertEquals(4, morty.dataFromFile().size());
	}
	
	
	@Test
	public void Test_validateProspect_noloan(){
		assertEquals(false, morty.validateProspect("E", 0, 1, 1));
	}
	@Test
	public void Test_validateProspect_noname(){
		assertEquals(false, morty.validateProspect("", 1, 1, 1));
	}
	@Test
	public void Test_validateProspect_notime(){
		assertEquals(false, morty.validateProspect("E", 1, 1, 0));
	}
	@Test
	public void Test_validateProspect_valid(){
		assertEquals(true, morty.validateProspect("E", 1, 1, 1));
	}
	
	
	@Test
	public void Test_newProspect_invalid(){
		morty.setProspList(testlListPath);
		morty.newProspect("E", 0, 1, 1, false);
		assertEquals(4, morty.getProspects().size());
	}
	@Test
	public void Test_newProspect_valid(){
		morty.setProspList(testlListPath);
		morty.newProspect("E", 1, 1, 1, false);
		morty.newProspect("E", 1, 1, 1, false);
		assertEquals(6, morty.getProspects().size());
	}
	@Test
	public void Test_newProspect_correctListEntry(){
		morty.setProspList(testlListPath);
		morty.newProspect("E", 1000, 10, 1, false);
		assertEquals("Prospect 5: E wants to borrow 1000.00 € for a period of 1 years and pay 146.76 € each month\r\n", morty.getProspects().get(4));
	}
	//Currently not tested: appending data to file
	
	@Test
	public void Test_readFile_valid(){
		assertEquals(4,  morty.readFile(testlListPath).size());
	}
	@Test
	public void Test_readFile_invalid(){
		assertEquals(0,  morty.readFile("").size());
	}
	//Currently not tested: readfile return *correct* data
	
	
	@Test
	public void Test_pringMortData() {
		//morty.setProspList(testlListPath);
		//assertEquals(6, morty.pri);
	}
	
	
	
	//Mortgage calc tests
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
		//assertEquals("Karvinen",morty.readFile("./Codetest-Mortageplan/prospects.txt").get(1).name);
	}
	

}
