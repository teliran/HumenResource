package GUI;

import java.sql.Date;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import Backend.*;
import Backend.Agreement.daysOfWeek;
import Backend.Agreement.deliveryType;
import Backend.Agreement.orderType;
import DAL.AccessDeniedException;
import BL.IBL;
public class AddAgreement {
	private IBL ba;
	//constructor
	public AddAgreement(IBL ba){
		this.ba= ba;
	}
	
	//adding agreement
	public  Agreement add(String supNum){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-ADD AGREEMENT*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("----------------------At any stage press 0 to quit--------------------");
		// input from user for sign date
		boolean flag=true;
		int day, month, year;
		Date signDate=null;
		while (flag){
			flag= false;
			System.out.println("please enter the sign date: ");
			System.out.println("please enter a day between 1 to 31: ");
			 day = check.checkInt(1, 31,"wrong input, please enter a day between 1 to 31 \n");
			if (day==0){
				System.out.println("quiting");
				check.delay();
				return null;
			}
				
			System.out.println("please enter month between 1 to 12: ");
			 month = check.checkInt(1, 12,"wrong input,please enter month between 1 to 12 \n "  );
			if (month==0){
				System.out.println("quiting");
				check.delay();
				return null;
			}
				
			System.out.println("please enter year between 1950 to 2150: ");
			 year = check.checkInt(1950, 2150,"wrong input,please enter year between 1950 to 2150 \n");
			if (year==0){
				System.out.println("quiting");
				check.delay();
				return null;
			}
			signDate= new Date(year-1900,month-1,day);
			List<SupIdSearchable> list= new LinkedList<SupIdSearchable>();
			try{
				list= ba.GetEntityBySupplier(supNum, new Agreement());
			}catch (AccessDeniedException | ParseException e){
				System.out.println("problems with network (58)");
				check.delay();
				return null;
			}
			for (SupIdSearchable ent: list){
				if(((Agreement)ent).getSignDate().equals(signDate))
					flag=true;
			}
			if (flag){
				System.out.println("Agreement with the same sign date already exists int the system...");
				System.out.println("press 1 to enter a diffrent date");
				System.out.println("press 0 to go back");
				int opt= check.checkInt(0, 1, "please chooose one of the options above");
				if (opt==0){
					flag= false;
					System.out.println("quiting...");
					check.delay();
					return null;
				}
			}

		}
					
		

		Date ValDate= null;
		// input from user for validation date
		flag= false;
		while (!flag){
			System.out.println("please enter the validation date: ");
			System.out.println("please enter day between 1 to 31: ");
			day = check.checkInt(1, 31,"wrong input, please enter a day between 1 to 31 \n");
			if (day==0){
				System.out.println("quiting");
				check.delay();
				flag=true;
				return null;
			}
				
			System.out.println("please enter month between 1 to 12: ");
			month = check.checkInt(1, 12,"wrong input,please enter month between 1 to 12 \n "  );
			if (month==0){
				System.out.println("quiting");
				check.delay();
				flag=true;
				return null;
			}
				
			System.out.println("please enter year between 1950 to 2150: ");
			year = check.checkInt(1950, 2150,"wrong input,please enter year between 1950 to 2150 \n");
			if (year==0){
				System.out.println("quiting");
				check.delay();
				flag=true;
				return null;
			}
				
			ValDate= new Date(year-1900,month-1,day);
			flag= signDate.before(ValDate);
			if (!flag)
				System.out.println("please enter validation date that is later then the sign date");
			
		}
		
		
		// input from user for way of transportation
		
		System.out.println("please enter choose way of trasportation");
		System.out.println("1 for delivery");
		System.out.println("2 for pickup");
		deliveryType delivery;
		int opt= check.checkInt(1, 2, "worng input... \n 1 for delivery \n 2 for pickup \n");
		if (opt==0){
			System.out.println("quiting");
			check.delay();
			return null;
		}
			
		if (opt==1)
			delivery=deliveryType.DELIVERY;
		else
			delivery=deliveryType.PICKUP;
		
		// input from user for way of order type
		
		System.out.println("please enter choose order type");
		System.out.println("1 for by order");
		System.out.println("2 for by days");
		orderType order;
		 opt= check.checkInt(1, 2, "worng input... \n 1 for by order \n 2 by days \n");
		 if (opt==0){
			 	System.out.println("quiting");
				check.delay();
				return null;
			}
		 
		if (opt==1)
			order=orderType.BYORDER;
		else
			order=orderType.BYDAYS;
		
		
		// if order type is by days: get the days from the user
		
		flag =true;
		List<daysOfWeek> deliveryDays= new LinkedList<daysOfWeek>();
		if (order== orderType.BYDAYS){
			//choose days
			System.out.println("in case of regular tranpartions days:");	
			while (flag){
				System.out.println("please enter their number:");
				System.out.println("1 for sunday");
				System.out.println("2 for monday");
				System.out.println("3 for tuesday");
				System.out.println("4 for wendesday");
				System.out.println("5 for thurday");
				System.out.println("6 for friday");
				System.out.println("7 for saturday");
				System.out.println("0 to finish");
				String msg="wrong input.... \n please enter transportation days number:/n "
						+ "1 for sunday \n"+"2 for monday \n"+"3 for tuesday \n"+ "4 for wendesday \n"
						+"5 for thurday \n"+"6 for friday \n"+ "7 for saturday \n"+ "0 to finish \n";
				opt= check.checkInt(0, 7,msg);
				switch(opt){
				case(1):
					deliveryDays.add(daysOfWeek.SUNDAY);
					break;
				case(2):
					deliveryDays.add(daysOfWeek.MONDAY);
					break;
				case(3):
					deliveryDays.add(daysOfWeek.TUESDAY);
					break;
				case(4):
					deliveryDays.add(daysOfWeek.WEDNESDAY);
					break;
				case(5):
					deliveryDays.add(daysOfWeek.THURSDAY);
					break;
				case(6):
					deliveryDays.add(daysOfWeek.FRIDAY);
					break;
				case(7):
					deliveryDays.add(daysOfWeek.SATURDAY);
					break;
				case(0):
					flag=false;
					break;
				}
				
		}
		}
		
		
			  /// Add Agreement!!!!
			/// now u can add products :)
			///and only after you add a product you can add quantity discount list
			
			
		// get the products list from the user
		
		List<Product> productList= new LinkedList<Product>();
		return  new Agreement(supNum, signDate, ValDate,delivery, order, deliveryDays, productList);
		
	    }
}
