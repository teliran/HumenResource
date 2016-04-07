package Emp;
import java.util.Scanner;

public class Employee { 
	private int id;
	private String name;
	private Position pos;
	private int bankNumber;
	private int accountNumber;
	private String startDate;
	private int salaryPerHour;
	public static enum Position{hrManager, stockManager, storekeeper, 
		cashier, driver, storeManager, shiftManager};
		
	public Employee(int id, String name, Position pos, int bankNumber,int accountNumber,String startDate,int salaryPerHour){
		this.id = id;
		this.name = name;
		this.pos = pos;
		this.bankNumber = bankNumber;
		this.accountNumber = accountNumber;
		this.startDate = startDate;
		this.salaryPerHour = salaryPerHour;
	}
	
	public static void showMenu(){
		int usrInput;
		while(true){
			System.out.println("==Employee==");
			System.out.println("1.\t Add new Employee");
			System.out.println("2.\t Search Employee");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					//TODO add new employee menu
					break;
				case 2:
					//TODO search employee 
					break;
				case 3:
					return;
			}
		}	
	}
	
	public static Employee addEmployee(){
		System.out.println("==Add New Employee==");
		System.out.println("Enter Employee ID (9 digits):");
		int id = Store.getNumber();
		System.out.println("Enter Employee First and Last Name:");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.println("Enter Employee position:");
		String pos = sc.nextLine();
		
		System.out.println("Enter Employee First and Last Name:");
		String Name = sc.nextLine();
		return null;
	}
	
	
	
	
	public Position getPosition() {
		return pos;
	}
	public void setPosition(Position pos) {
		 this.pos = pos;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public int getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(int bankNumber) {
		this.bankNumber = bankNumber;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	
}
