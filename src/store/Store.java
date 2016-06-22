package store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import DB.DB;
import Emp.Constraint;
import Emp.Employee;
import Emp.Employee.Position;
import Emp.Shift;
import Init.Init;
import transport.TransManager;

public class Store {
	private String name;
	public static enum Week{Sunday, Monday, Tuesday, Wednesday, Thursday, Friday};
	public static Date currentDate  = new Date();
	public static Position user;
	private static Init init;
	
	
	public Store(String name){
		this.name=name;
	}
	
	public static void main(String[] args) {
		DB.open();
		init = new Init();
		Store store = new Store("Emart Store");
		store.showMenu();
		DB.close();
		init.closeConnection();
	}
	
	/**
	 * get the date of the first day in date
	 * @param date the insert date
	 * @param current true - if current week , false - next week
	 * @return the fixed date
	 */
	public static Date getFirstDayOfWeek(Date date,boolean current){
		int day = date.getDay() +1;
		Calendar c = Calendar.getInstance();    
		c.setTime(date);
		c.add(Calendar.DATE, -(day-1));
		if(!current)
			c.add(Calendar.DATE, 7);
		return c.getTime();	
	}

	public void showMenu(){	
		int userInput;
		System.out.println("Welcome to "+name+"!");	
		while (true){
			login();
			int index =0;
			System.out.println("Main Menu :");
			if(user.equals(Position.hrManager) || user.equals(Position.storeManager)){
				System.out.println((++index)+".\t Employees");
				System.out.println((++index)+".\t Shifts");
				System.out.println((++index)+".\t Constraint");
			}
			if(user.equals(Position.logisticManager) || user.equals(Position.storeManager)){
				System.out.println((++index)+".\t Transport");
			}
			if(user.equals(Position.storeKeeper) || user.equals(Position.storeManager)){
				System.out.println((++index)+".\t Supplier");
				System.out.println((++index)+".\t Storage");
			}
			System.out.println((++index)+".\t Exit");	
			userInput = getNumber();
			if(user.equals(Position.hrManager)){
				if(userInput ==4)
					userInput=7;
				else if (userInput >4 )
					continue;
			}
			if(user.equals(Position.logisticManager)){
				if(userInput ==2)
					userInput=7;
				else if (userInput > 2 )
					continue;
				else
					userInput+= 3;
			}
			if(user.equals(Position.storeKeeper)){
				if(userInput ==3)
					userInput=7;
				else if (userInput >3 )
					continue;
				else
					userInput+= 4;
			}
				
			switch (userInput){
			case 1:
				Employee.showMenu();
				break;
			case 2:
				Shift.showMenu();
				break;
			case 3:
				Constraint.showMenu();
				break;
			case 4:
				TransManager.showMainMenu();
				break;
			case 5: //supplier
				init.getSupplierPl().run();
				break;
			case 6: //storage
				init.getStorePl().run();
				break;
			case 7: //EXIT
				//init.close();
							
			}
		}	
	}
	
	public void login(){
		
		Position[] pos = {Position.hrManager,Position.storeKeeper,Position.storeManager,Position.logisticManager,};
		System.out.println("Please Select Your Position:");
		int ans = selectFromMenu(pos);
		user = pos[ans];
		System.out.println("Login successfully!");
		
	}
	
	public static int getNumber(){
		Scanner sc = new Scanner(System.in);
		String userNum;
		while(true){
			userNum = sc.nextLine();
			if(isNumber(userNum)){
				return Integer.parseInt(userNum);
			}
			System.out.println("Invalid number , Please try again");
		}	
	}
	
	public static boolean isNumber(String number){
		try{
			Integer.parseInt(number);
		}
		catch(Exception c){
			return false;
		}
		return true;
	}
	
	public static Date stringToDate(String date) {
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			System.out.println("Wrong format please try again (dd/MM/yyyy):");
			Scanner sc = new Scanner(System.in);
			return stringToDate(sc.nextLine());
		}
	}
	
	public static Date stringToHour(String hour) {
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		try {
			return format.parse(hour);
		}catch (ParseException e) {
			System.out.println("Wrong format please try again (HH:mm 24Hr):");
			Scanner sc = new Scanner(System.in);
			return stringToHour(sc.nextLine());
		}
	}
		
		
	public static String setFormat(Date date){
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);	
	}
	
	public static String setHour(Date date){
		SimpleDateFormat format=new SimpleDateFormat("HH:mm");
		return format.format(date);	
	}
	
	public static int selectFromMenu(Object[] strArr){
		int i;
		int userNum;
		System.out.println("Enter your choise in range(1 - " +strArr.length+")");
		for (i=0; i<strArr.length; i++){
			System.out.println((i+1)+". "+ strArr[i].toString());
		}
		while (true){
			userNum = getNumber();
			if(userNum<=0 || userNum>strArr.length)
				System.out.println("Enter your choise in range(1 - " +strArr.length+")");
			else
				return userNum-1;
		}
	}
	
	
}
