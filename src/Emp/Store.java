package Emp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import javax.print.attribute.standard.DateTimeAtCompleted;

import DB.DB;
import sun.util.resources.cldr.af.CalendarData_af_NA;

public class Store {
	private String name;
	private String password;
	public static Date currentDate  = stringToDate("13/04/2016");
	
	public Store(String name, String password){
		this.name=name;
		this.password=password;
		
	}
	
	public static void main(String[] args) {
		DB.open();
		Store store = new Store("Smliran's Store","1234");
		store.showMenu();
		DB.close();
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
		login();
		while (true){
			System.out.println("Main Menu :");
			System.out.println("1.\t Employees");
			System.out.println("2.\t Shifts");
			System.out.println("3.\t Exit");	
			userInput = getNumber();
			switch (userInput){
			case 1:
				Employee.showMenu();
				break;
			case 2:
				Shift.showMenu();
				break;
			case 3: //EXIT
				return;			
			}
		}	
	}
	
	public void login(){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("Please Enter The password :");		
			String password = sc.nextLine();		
			if(password.equals(this.password)){
				System.out.println("Great! You logged in !!");
				return;
			}
			System.out.println("Wrong password! , Please try again");		
		}	
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
	
	public static String setFormat(Date date){
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
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
