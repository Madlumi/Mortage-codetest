package morty;

public class mortMath {

	
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
