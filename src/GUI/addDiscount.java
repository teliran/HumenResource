package GUI;
import Backend.*;

public  class addDiscount {
	public Discount add(){
		double quantity;
		double disc;
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*ADD DISCOUNT*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("----------------------At any stage press 0 to quit--------------------");
		System.out.println("Please enter quantity: ");
		quantity= check.checkDoub("wrong input... \n please enter a positive number for quintity:");
		if (quantity==0){
			System.out.println("quiting");
			check.delay();
			return null;
		}
		
		System.out.println("Please enter Discount between 0 to 100:  (precentege)");
		disc= check.checkDisc("wrong input...\n please enter a number between 0 to 100 for your discount:");
		if (disc==0){
			System.out.println("quiting");
			check.delay();
			return null;
		}
		
		return new Discount(quantity,disc);
	}

}
