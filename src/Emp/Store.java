package Emp;

import java.sql.ResultSet;
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
		store.showManu();
		DB.close();
	}

	
	public void showManu(){	
		int userInput;
		System.out.println("Welcome to "+name+"!");
		login();
		while (true){
			System.out.println("Main Menu :");
			System.out.println("1.\t Employees");
			System.out.println("2.\t Employees");
			System.out.println("3.\t Exit");	
			userInput = getNumber();
			switch (userInput){
			case 1:{			
				ResultSet rs = DB.executeQuery( "SELECT * FROM Employees;" );
				while (DB.next(rs)) {
					int id = DB.getInt(rs,"ID");
					String  name = DB.getString(rs,"Name");
					System.out.println( "ID = " + id );
					System.out.println( "NAME = " + name );
					System.out.println();
				}
				DB.closeResult(rs);	
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
}
