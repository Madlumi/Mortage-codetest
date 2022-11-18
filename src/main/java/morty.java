import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
class morty {
	public static void main(String[] args){
		dataFromFile();//could be modified to send in args to let user specify file
	}

	//the function for parsing data from a file
	public static int dataFromFile(){
		ArrayList<mortData> list=readFile("./Codetest-Mortageplan/prospects.txt");
		if(list.size()<=0) {return -1;}//stop if no prospects found
		
		for(int i = 0; i < list.size();i++){
			double montly=monthlyMortagePayment(
			list.get(i).totalLoan, percentToDecimal(list.get(i).interest),yearToMonth(list.get(i).years));
			list.get(i).monthlyPayment=montly;
		}	
		printMortageData(list);
		return 1;
	}
	
	//calculate a mortgage payment using given data
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
		
		double a = power((1+monthlyInterest),payments);
		 //E=U[b*a]/[a - 1]
		double mp=loanAmmount*(monthlyInterest*a) / (a-1); 
		return mp;

	}
	
	public static ArrayList<mortData> readFile(String filePath){
		ArrayList<mortData> list=	new ArrayList<mortData>();
		try{
			File f = new File(filePath);
			Scanner s = new Scanner(f);
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
		}catch(FileNotFoundException e){
			System.out.println("File not found, "+e);
		}
		return list;
	}
	
	//prints the list of (potential) mortgages
	public static void printMortageData(ArrayList<mortData> l){
		String padding = "****************************************************************************************************";
		String outForm = "Prospect %d: %s wants to borrow %.2f € for a period of %d years and pay %.2f € each month%n";
		for(int i = 0; i < l.size(); i++){
			System.out.println(padding);//looks better above for loop 
			System.out.format(outForm,(i+1),l.get(i).name,l.get(i).totalLoan,l.get(i).years,l.get(i).monthlyPayment);
			System.out.println(padding);
		}
	}


	//helper math funcions
	
	//simple to power off x helper function where power>=0
	public static double power(double n, int pow){
		if(pow<0){
			return -1;
		}
		double r = 1;
		for(int i = 0; i < pow; i++){
			r*=n;
		}
		return r;
	}
	//helper functions because its funny
	public static int yearToMonth(int y) {
		return 12*y;
	}
	public static double percentToDecimal(double p) {
		return p/100;
	}
	
}
