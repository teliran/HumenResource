package GUI;

import java.sql.Date;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import BL.IBL;
import Backend.*;
import DAL.AccessDeniedException;

public class Reports {
	private IBL bl;

	public Reports(IBL bl) {
		this.bl=bl;
	}

	public void run() {
		System.out.println("==================REPORTS==================");
		System.out.println("Please type the number of your option");
		System.out.println("0- to go back to main menu");
		System.out.println("1- Show all orders");
		System.out.println("2- Show orders by date range");
		System.out.println("3- Show orders by  a specific date");
		System.out.println("4- Show orders by total price");
		System.out.println("5- Show orders by product");
		int opt= check.checkInt(0, 5, "please choose one of the options above");
		openScreen os= new openScreen(bl);
		switch(opt){
			case 0: os.run();
					break;
			case 1: allOr();
					break;
			case 2: dateOr();
					break;
			case 3: speDateOr();
					break;
			case 4: priceOr();
					break;
			case 5: prodOr();
					break;
		}
	}

	private void speDateOr() {
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-------at any level press 0 to go back---------");
		
				System.out.println("please enter the date for the order: ");
				System.out.println("please enter a day between 1 to 31: ");
				int day = check.checkInt(1, 31,"wrong input, please enter a day between 1 to 31 \n");
				if (day==0){
					System.out.println("quiting");
					check.delay();
					run();
				}
					
				System.out.println("please enter month between 1 to 12: ");
				int month = check.checkInt(1, 12,"wrong input,please enter month between 1 to 12 \n "  );
				if (month==0){
					System.out.println("quiting");
					check.delay();
					run();
				}
					
				System.out.println("please enter year between 1950 to 2150: ");
				int year = check.checkInt(1950, 2150,"wrong input,please enter year between 1950 to 2150 \n");
				if (year==0){
					System.out.println("quiting");
					check.delay();
					run();
					return;
				}
					
				Date firstDate= new Date(year-1900,month-1,day);
				List<Order> list= new LinkedList<Order>();
				try {
					list= bl.AdvOrderSearch("date", null, firstDate.toString(), null);
				} catch (AccessDeniedException | ParseException e) {
					System.out.println("network problems.... (reports 82)");
					check.delay();
					run();
					return;
				}
				if (list.isEmpty()){
					System.out.println("no orders been made in this date");
					System.out.println("press 1 to search for a different date");
					System.out.println("press 0 to go back");
					int opt= check.checkInt(0, 1, "please choose one of the options above");
					if (opt==1){
						speDateOr();
						return;
					}
						
					
					else{
						run();
						return;
					}
						
				}
				for (Order order: list){
					try {
						System.out.println(bl.printOrder(order));
					} catch (AccessDeniedException e) {
						System.out.println("troubles with viewing....");
					}
					System.out.println("----------------------------------------");
				}
				System.out.println("press 0 to go back");
				check.checkInt(0, 0, "please press 0 to go back");
				run();
				return;
	}
		
	

	private void allOr() {
		System.out.println("Printing all orders...");
		check.delay();
		List<Entity> list=new LinkedList<Entity>();
		
		try {
			list= bl.GetItAll(new Order());
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network problems... (Reports 50)");
			check.delay();
			run();
		}
		if(list.isEmpty()){
			System.out.println("no orders in the system...");
			check.delay();
		}else{
			System.out.println("orders list: ");
			for(Entity ent: list){
				try {
					System.out.println(bl.printOrder((Order)ent));
				} catch (AccessDeniedException e) {
					System.out.println("troubles with viewing....");
				}
				System.out.println("-----------------------------------------------");
			}
		}
		System.out.println("press 0 to go back");
		check.checkInt(0, 0, "please type 0 to go back");
		run();
		
	}

	@SuppressWarnings("deprecation")
	private void dateOr() {
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-------at any level press 0 to go back---------");
		
		System.out.println("please enter range of dates for orders...");
		
				System.out.println("please enter the first date: ");
				System.out.println("please enter a day between 1 to 31: ");
				int day = check.checkInt(1, 31,"wrong input, please enter a day between 1 to 31 \n");
				if (day==0){
					System.out.println("quiting");
					check.delay();
					run();
				}
					
				System.out.println("please enter month between 1 to 12: ");
				int month = check.checkInt(1, 12,"wrong input,please enter month between 1 to 12 \n "  );
				if (month==0){
					System.out.println("quiting");
					check.delay();
					run();
				}
					
				System.out.println("please enter year between 1950 to 2150: ");
				int year = check.checkInt(1950, 2150,"wrong input,please enter year between 1950 to 2150 \n");
				if (year==0){
					System.out.println("quiting");
					check.delay();
					run();
					return;
				}
					
				Date firstDate= new Date(year-1900,month-1,day);

				Date secondDate= null;
				
				boolean flag= false;
				while (!flag){
					System.out.println("please enter the second date: ");
					System.out.println("please enter day between 1 to 31: ");
					day = check.checkInt(1, 31,"wrong input, please enter a day between 1 to 31 \n");
					if (day==0){
						System.out.println("quiting");
						check.delay();
						flag=true;
						run();
						return;
					}
						
					System.out.println("please enter month between 1 to 12: ");
					month = check.checkInt(1, 12,"wrong input,please enter month between 1 to 12 \n "  );
					if (month==0){
						System.out.println("quiting");
						check.delay();
						flag=true;
						run();
						return;
					}
						
					System.out.println("please enter year between 1950 to 2150: ");
					year = check.checkInt(1950, 2150,"wrong input,please enter year between 1950 to 2150 \n");
					if (year==0){
						System.out.println("quiting");
						check.delay();
						flag=true;
						run();
						return;
					}
						
					secondDate= new Date(year-1900,month-1,day);
					flag= firstDate.before(secondDate);
					if (!flag)
						System.out.println("please enter second date that is later then the first date");
					
				}
				List<Order> list= new LinkedList<Order>();
				
				try {
					list= bl.AdvOrderSearch("date", "date", firstDate.toString(), secondDate.toString());
				} catch (AccessDeniedException | ParseException e) {
					System.out.println("network problems.... (reports 145)");
					check.delay();
					run();
					return;
				}
				if (list.isEmpty()){
					System.out.println("no orders been made in this range of dates");
					System.out.println("press 1 to search for different dates");
					System.out.println("press 0 to go back");
					int opt= check.checkInt(0, 1, "please choose one of the options above");
					if (opt==1){
						dateOr();
						return;
					}
						
					
					else{
						run();
						return;
					}
						
				}
				for (Order order: list){
					try {
						System.out.println(bl.printOrder(order));
					} catch (AccessDeniedException e) {
						System.out.println("troubles with viewing....");
					}
					System.out.println("----------------------------------------");
				}
				System.out.println("press 0 to go back");
				check.checkInt(0, 0, "please press 0 to go back");
				run();
				return;
	}

	private void priceOr() {
		System.out.println("-------at any level press 0 to go back---------");
		System.out.println("please enter the lower price for orders range...");
		double priceLow= check.checkDoub("please enter a positive price");
		if (priceLow==0){
			System.out.println("quiting");
			check.delay();
			run();
			return;
		}
		System.out.println("please enter the higher price for orders range...");
		double priceHigh= check.checkDoub("please enter a positive price");
		if (priceHigh==0){
			System.out.println("quiting");
			check.delay();
			run();
			return;
		}
		List<Order> list= new LinkedList<Order>();
		try {
			list= bl.AdvOrderSearch("price", "price",Double.toString(priceLow), Double.toString(priceHigh));
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network problems... (reports 195)");
			check.delay();
			run();
			return;
		}
		if (list.isEmpty()){
			System.out.println("no orders been made in this range of price");
			System.out.println("press 1 to search for different range");
			System.out.println("press 0 to go back");
			int opti= check.checkInt(0, 1, "please choose one of the options above");
			if (opti==1){
				priceOr();
				return;
			}
				
			else{
				run();
				return;
			}
				
		}
		
		for(Order order: list){
			try {
				System.out.println(bl.printOrder(order));
			} catch (AccessDeniedException e) {
				System.out.println("troubles with viewing....");
			};
			System.out.println("-------------------------------------------------------");
		}
		System.out.println("press 0 to go back");
		check.checkInt(0, 0, "please press 0 to go back");
		run();
		
	}

	private void prodOr() {
		System.out.println("-------at any level press 0 to go back---------");
		System.out.println("please enter the name of the product you want to search in orders:");
		String name= check.checkNameP("please enter a nmae or 0 to return");
		if (name.equals("0")){
			System.out.println("quiting");
			check.delay();
			run();
			return;
		}
		List<Order> list= new LinkedList<Order>();
		try {
			list=bl.AdvOrderSearch("product", null, name, null);
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network problems... (reports 233)");
			check.delay();
			run();
			return;
		}
		if (list.isEmpty()){
			System.out.println("no orders been made with this product");
			System.out.println("press 1 to search with a different product");
			System.out.println("press 0 to go back");
			int opti= check.checkInt(0, 1, "please choose one of the options above");
			if (opti==1){
				priceOr();
				return;
			}
				
			else{
				run();
				return;
			}
				
		}

		for(Order order: list){
			try {
				System.out.println(bl.printOrder(order));
			} catch (AccessDeniedException e) {
				System.out.println("troubles with viewing....");
			}
			System.out.println("-------------------------------------------------------");
		}
		System.out.println("press 0 to go back");
		check.checkInt(0, 0, "please press 0 to go back");
		run();
		return;
	}

}
