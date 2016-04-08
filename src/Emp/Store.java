package Emp;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import DB.DB;

public class Store {
	private String name;
	private String password;
	
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
				break;
			}
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
	
	public static int selectFromMenu(Object[] strArr){
		int i;
		int userNum;
		Scanner sc = new Scanner(System.in);
		for (i=0; i<strArr.length; i++){
			System.out.println((i+1)+". "+ strArr[i].toString());
		}
		while (true){
			userNum = getNumber();
			if(userNum<=0 || userNum>strArr.length)
				System.out.println("Enter your choise in range(0 - " +strArr.length+")");
			else
				return userNum-1;
		}
	}
}
