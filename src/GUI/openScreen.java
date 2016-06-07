package GUI;

import BL.IBL;

public  class openScreen {
	IBL bl;
	
	public openScreen(IBL bl){
		this.bl=bl;
	}
	 public  void run(){
		
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
 		System.out.println("Wellcome to the suppliers system!");
		System.out.println("Please choose an option and type the option’s number below:");
		System.out.println("1 - view/set/update suppliers information");
		System.out.println("2 - place an order from a supplier/ view orders");
		System.out.println("3 - print reports");
		System.out.println("0 - quit");
		int opt= check.checkInt(0, 3, "wrong input.... please choose a number for one of the options above");
		supInfo spInfo= new supInfo(bl);
		MakeOrder mo= new MakeOrder(bl);
		Reports reports= new Reports(bl);
		switch (opt){
		case 0:
		{
			
			break;
		}
				
		case 1: spInfo.run();
				break;
		case 2: mo.run();
				break;
		case 3: reports.run();
				break;
	
		}
		

	}
	

}
