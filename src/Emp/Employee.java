package Emp;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;

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
		
	public Employee(boolean insert,int id, String name, Position pos, int bankNumber,int accountNumber,String startDate,int salaryPerHour){
		this.id = id;
		this.name = name;
		this.pos = pos;
		this.bankNumber = bankNumber;
		this.accountNumber = accountNumber;
		this.startDate = startDate;
		this.salaryPerHour = salaryPerHour;
		if(insert){
			String query = "INSERT INTO Employees (ID,Name,Position,StartDate,AccountNumber,BankNumber,SalaryPerHour) " +
	                   "VALUES ("+id+", '"+name+"', '"+pos+"' ,'"+startDate+"',"+accountNumber+","+bankNumber+","+salaryPerHour+");";
			DB.executeUpdate(query);
		}
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
					showCard(addEmployee());
					break;
				case 2:
					showCard(searchEmployee());
					break;
				case 3:
					return;
			}
		}	
	}
	
	public static void showCard(Employee emp){
		int usrInput;
		while(true){
			System.out.println("==Employee Card==");
			System.out.println("== "+emp+" ==");
			System.out.println("1.\t Edit Name");
			System.out.println("2.\t Edit Position");
			System.out.println("3.\t Edit Account Number");
			System.out.println("4.\t Edit Bank Number");
			System.out.println("5.\t Edit Salary Per Hour");
			System.out.println("6.\t Delete Employee");
			System.out.println("7.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					addEmployee();
					break;
				case 2:
					searchEmployee();
					break;
				case 7:
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
		Position[] posArr = Position.values();
		int j = Store.selectFromMenu(Position.values());
		Position pos = posArr[j];
		System.out.println("Enter Employee Bank Number:(2 digits)");
		int bNum = Store.getNumber();
		System.out.println("Enter Employee Account Number:(5 digits)");
		int accNum = Store.getNumber();		
		System.out.println("Enter Employee Start Date:(dd/MM/yyyy)");
		String stDate = sc.nextLine(); //TO-DO parse string to Date
		System.out.println("Enter Employee payment per hour:(int)");
		int salary = Store.getNumber();
		return new Employee(true, id, name, pos, bNum, accNum, stDate, salary);	
	}
	
	public String toString(){
		String ans = "ID: " +getId()+ " Name: " +getName();
		return ans;
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
	
	public static Employee searchEmployee(){
		int userInput,select;
		Scanner sc = new Scanner(System.in);
		Employee[] arr;
		while(true){
			System.out.println("==Search Employee==");
			System.out.println("1.\t Search By ID");
			System.out.println("2.\t Search By Name");
			System.out.println("3.\t Show All Employees");
			System.out.println("4.\t Exit");	
			userInput = Store.getNumber();			
			switch (userInput){
			case 1:{
				System.out.println("Please Enter ID:");
				arr=searchEmployee("ID",sc.nextLine());
				select=  Store.selectFromMenu(arr);
				return arr[select];
			}
				
			case 2:		
				System.out.println("Please Enter Name:");
				arr=searchEmployee("Name",sc.nextLine());
				select=  Store.selectFromMenu(arr);
				return arr[select];
				
			case 3:
				arr=searchEmployee("All","");
				select=  Store.selectFromMenu(arr);
				return arr[select];
				
			case 4: //EXIT
				return null;		
			}	
		}
	}
	
	public static Employee[] searchEmployee(String type, String value){
		ResultSet result = null;
		if(type.equals("ID")){
			result = DB.executeQuery("SELECT * FROM Employees WHERE ID ="+value+";");	
		}
		else if(type.equals("Name")){
			result = DB.executeQuery("SELECT * FROM Employees WHERE Name ='"+value+"'");		
		}
		else if(type.equals("All")){
			result = DB.executeQuery("SELECT * FROM Employees");		
		}	
		return getEmployeeArr(result);
	}
	
	public static Employee[] getEmployeeArr(ResultSet result){
		Vector<Employee> vector = new Vector<Employee>();
		while(DB.next(result)){
			vector.addElement( new Employee(false,DB.getInt(result, "ID"),DB.getString(result, "Name"),
					Position.valueOf(DB.getString(result, "Position")),DB.getInt(result, "BankNumber"),
					DB.getInt(result, "AccountNumber"),DB.getString(result, "StartDate"),
					DB.getInt(result, "SalaryPerHour")));			
		}
		DB.closeResult(result);
		return vector.toArray(new Employee[0]);
	}
	
}
