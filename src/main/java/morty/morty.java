 package morty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//dataholder class
class mortData{
	String name;
	double totalLoan;
	double interest;
	int years;
	double monthlyPayment;
	public mortData(String name, double totalLoan, double interest, int years){
		this.name=name;
		this.totalLoan=totalLoan;
		this.interest=interest;
		this.years=years;
	}
}

@SpringBootApplication
public class morty {
	
	private static ArrayList<String> ProspectList = new ArrayList<String>();
	private static String filepath = "./Codetest-Mortageplan/prospects.txt";
	
	
	public static void main(String[] args){
		SpringApplication.run(morty.class, args);
	}
	
	//returns list of prospects print formated
	public static ArrayList<String> getProspects(){
		if(ProspectList.size()<=0) {
			ProspectList = dataFromFile();
		}
		return ProspectList;
	}
	
	//the function for parsing data from a file
	public static ArrayList<String> dataFromFile(){
		//parse file into mortData
		ArrayList<mortData> plist=readFile(filepath);
		if(plist.size()<=0) {return null;}
		
		//calculate mortage for list
		for(int i = 0; i < plist.size();i++){
			plist.get(i).monthlyPayment=monthlyMortagePayment(plist.get(i));
		}	
		
		//format (and print) list
		ArrayList<String> s= printMortageData(plist, true);
		return s;
	}
	
	//validate data
	public static boolean validateProspect(String name, double loanammount, double intr, int years) {
		if(loanammount<=0) {return false;}
		if(years<=0) {return false;}
		return true;
	}
	
	//new prospect added through web interface
	public static void newProspect(String name, double loanammount, double intr, int years, boolean save){
		//make sure list is loaded to prevent a bug
		getProspects();
		//validate prospect data
		if(!validateProspect(name, loanammount, intr, years)) {return;}
		//add to list
		 ArrayList<mortData> md = new ArrayList<mortData>();
		 mortData m = new mortData( name, loanammount, intr,  years);
		 m.monthlyPayment=monthlyMortagePayment(m);
		 md.add(m);
		ProspectList.addAll(printMortageData(md, false,ProspectList.size()/3));
		//append to file
		if(!save) {return;}
		String outForm = "%n%s,%.2f,%.2f,%d";
		if(name.contains(",")) {
			name="\""+name+"\"";
		}
		String s = String.format(outForm,name,loanammount,intr,years);
		try {
			Files.write(
				      Paths.get(filepath), 
				      s.getBytes(),
				      StandardOpenOption.APPEND);
					} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//read the prospects from file
	public static ArrayList<mortData> readFile(String filePath){
		ArrayList<mortData> list=	new ArrayList<mortData>();
		try{
			File f = new File(filePath);
			Scanner s = new Scanner(f,Charset.forName("UTF-8"));
			//quick regex to read data, if more fields were to be added one would want to be using a csv parser instead
			Pattern p1 =Pattern.compile("^([^,]*),([0-9.]+),([0-9.]+),([0-9]+)$");
			Pattern p2 =Pattern.compile("^\"(.*)\",([0-9.]+),([0-9.]+),([0-9]+)$");			
			Matcher m;
			while(s.hasNextLine()){
				String line=s.nextLine();
				m=p1.matcher(line);
				if(!m.find(0)){m=p2.matcher(line);}
				if(m.find(0)){
					list.add(new mortData(
						m.group(1),
						Double.parseDouble(m.group(2)),
						Double.parseDouble(m.group(3)),
						Integer.parseInt(m.group(4))));
				}
			}
			s.close();
		}catch(Exception e){
			System.out.println("File not found, "+e);
		}
		return list;
	}
	
	//prints the list of (potential) mortgages
	//returns list of print output
	public static ArrayList<String> printMortageData(ArrayList<mortData> l, boolean print){
		return  printMortageData(l,  print,0);
	}
	public static ArrayList<String> printMortageData(ArrayList<mortData> l, boolean print, int prevpros){
		String padding = "****************************************************************************************************";
		String outForm = "Prospect %d: %s wants to borrow %.2f € for a period of %d years and pay %.2f € each month%n";
		ArrayList<String> s=	new ArrayList<String>();
		for(int i = 0; i < l.size(); i++){
			
			String ss = String.format(outForm,(i+1+prevpros),l.get(i).name,l.get(i).totalLoan,l.get(i).years,l.get(i).monthlyPayment);
			s.add(padding);
			s.add(ss);
			s.add(padding);
			if(print) {
				System.out.println(padding);
				System.out.format(ss);
				System.out.println(padding);
			}
		}
		return  s;
	}
	
	//calculate a mortgage payment using given data
	public static double monthlyMortagePayment(mortData m) {
		return monthlyMortagePayment(m.totalLoan,  mortMath.percentToDecimal(m.interest), mortMath.yearToMonth(m.years));
	}
	public static double monthlyMortagePayment(double loanAmmount, double monthlyInterest, int payments){
		//input validation
		if(loanAmmount<=0) {
			return -1;
		}
		if(payments<1) {
			return -1;
		}
		if(monthlyInterest==0) {
			return -1;
			/*
			 * a 0% interest would cause a division by zero with the supplied formula
			 * If a 0% interest rate is allowed one could simply "return loanAmmount/payments" here, but i will stick to the supplied formula
			 * for now I will assume 0% is not allowed
			 */
			//not sure if there should be a limit on monthlyInterest being negative or above 100%
		}
		/*
		Mortage formula:
		E = Fixed monthly payment
		b = Interest on a monthly basis
		U = Total loan
		p = Number of payments
		 
		 E = U[b(1 + b)^p]/[(1 + b)^p - 1]
		 */
		
		//we can break out "b(1 + b)^p" and reuse it as "a"
		
		double a = mortMath.power((1+monthlyInterest),payments);
		 //E=U[b*a]/[a - 1]
		double mp=loanAmmount*(monthlyInterest*a) / (a-1); 
		return mp;

	}
	
}
