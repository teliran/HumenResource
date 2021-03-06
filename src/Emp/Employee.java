package Emp;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;

public class Employee { 
	private int id;
	private String name;
	private Position pos;
	private int bankNumber;
	private int accountNumber;
	private Date startDate;
	private int salaryPerHour;
	public static enum Position{hrManager, stockManager, storeKeeper, 
		cashier, driver, storeManager, shiftManager};
	
	//Construct	
	public Employee(boolean insert,int id, String name, Position pos, int bankNumber,int accountNumber,Date startDate,int salaryPerHour){
		this.id = id;
		this.name = name;
		this.pos = pos;
		this.bankNumber = bankNumber;
		this.accountNumber = accountNumber;
		this.startDate = startDate;
		this.salaryPerHour = salaryPerHour;
		if(insert){
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
			String date =format.format(startDate); 
			String query = "INSERT INTO Employees (ID,Name,Position,StartDate,AccountNumber,BankNumber,SalaryPerHour) " +
	                   "VALUES ("+id+", '"+name+"', '"+pos+"' ,'"+date+"',"+accountNumber+","+bankNumber+","+salaryPerHour+");";
			DB.executeUpdate(query);
		}
	}
	
	//Getters AND Setters Methods
	
	public String toString(){
		String ans = "ID: " +getId()+ " Name: " +getName()+" Position: "+pos;
		return ans;
	}	
	public Position getPosition() {
		return pos;
	}	
	public void setPosition(Position pos) {
		 this.pos = pos;
		 DB.executeUpdate("UPDATE Employees set Position = '"+pos+"' WHERE ID ="+id);
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		DB.executeUpdate("UPDATE Employees set Name = '"+name+"' WHERE ID ="+id);
	}
	public int getId() {
		return id;
	}
	public int getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(int bankNumber) {
		this.bankNumber = bankNumber;
		DB.executeUpdate("UPDATE Employees set BankNumber = '"+bankNumber+"' WHERE ID ="+id);
	}
	public void setSalaryPerHour(int salary){
		this.salaryPerHour=salary;
		DB.executeUpdate("UPDATE Employees set SalaryPerHour = '"+salary+"' WHERE ID ="+id);
	}
	public int getSalaryPerHour(){
		return salaryPerHour;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
		DB.executeUpdate("UPDATE Employees set AccountNumber = '"+accountNumber+"' WHERE ID ="+id);
	}
	public Date getStartDate() {
		return startDate;
	}
	
	public boolean equals(Object o){
		try{
			Employee emp = (Employee)o ;
			return emp.getId() == this.id;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public int hashCode(){
		return id;
	}
	
	// Static Methods 
	
	/**
	 * Show Welcome menu to employees screen
	 */
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
	
	/**
	 * Show Card menu to the selected Employee
	 * @param emp The selected employee
	 */
	public static void showCard(Employee emp){
		int usrInput;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("==Employee Card==");
			System.out.println("== "+emp+" ==");
			System.out.println("1.\t Edit Name (Current : "+emp.getName()+")");
			System.out.println("2.\t Edit Position (Current : "+emp.getPosition()+")");
			System.out.println("3.\t Edit Account Number (Current : "+emp.getAccountNumber()+")");
			System.out.println("4.\t Edit Bank Number (Current : "+emp.getBankNumber()+")");
			System.out.println("5.\t Edit Salary Per Hour (Current : "+emp.getSalaryPerHour()+")");
			System.out.println("6.\t Delete Employee");
			System.out.println("7.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1: //edit name
					System.out.println("Please enter the new name:");
					emp.setName(sc.nextLine());
					break;
				case 2: //Edit Position
					System.out.println("Please select the new Position:");
					Position[] posArr = Position.values();
					int j = Store.selectFromMenu(Position.values());
					emp.setPosition(posArr[j]);
					break;
				case 3: // Edit account number
					System.out.println("Please enter the new Account Number:");
					emp.setAccountNumber(Store.getNumber());
					break;
				case 4: // Edit bank number
					System.out.println("Please enter the new Bank Number:");
					emp.setBankNumber(Store.getNumber());
					break;
				case 5: // Edit Salary per hour
					System.out.println("Please enter the new Salary:");
					emp.setSalaryPerHour(Store.getNumber());
					break;
				case 6: // delete employee
					DB.executeUpdate("DELETE FROM Employees WHERE ID ="+emp.getId());
					return;
				case 7:
					return;
			}
		}	
	}
	
	/**
	 * Add new employee menu
	 * @return object of the created employee
	 */
	public static Employee addEmployee(){
		System.out.println("==Add New Employee==");
		System.out.println("Enter Employee ID (9 digits):");
		int id = Store.getNumber();
		while(searchEmployee("ID",id+"").length != 0){ // validate valid ID
			System.out.println("The enterd ID is not available, Please Try Again");
			id = Store.getNumber();
		}
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
		Date stDate = Store.stringToDate(sc.nextLine()); 
		System.out.println("Enter Employee payment per hour:(int)");
		int salary = Store.getNumber();
		return new Employee(true, id, name, pos, bNum, accNum, stDate, salary);	
	}
	
	/**
	 * Open Search menu of employees
	 * @return the chosen employee
	 */
	public static Employee searchEmployee(){
		int userInput,select;
		Scanner sc = new Scanner(System.in);
		Employee[] arr;
		while(true){
			System.out.println("==Search Employee==");
			System.out.println("1.\t Search By ID");
			System.out.println("2.\t Search By Name");
			System.out.println("3.\t Show All Employees");
			System.out.println("4.\t Search By Position");
			System.out.println("5.\t Exit");	
			userInput = Store.getNumber();			
			switch (userInput){
			case 1:{
				System.out.println("Please Enter ID:");
				arr=searchEmployee("ID",sc.nextLine());
				if(arr.length == 0){ // if there no results
					System.out.println("No Result to show");
					break;
				}
				select=  Store.selectFromMenu(arr);
				return arr[select];
			}
				
			case 2:		
				System.out.println("Please Enter Name:");
				arr=searchEmployee("Name",sc.nextLine());
				if(arr.length == 0){ // if there no results
					System.out.println("No Result to show");
					break;
				}
				select=  Store.selectFromMenu(arr);
				return arr[select];
				
			case 3:
				arr=searchEmployee("All","");
				if(arr.length == 0){ // if there no results
					System.out.println("No Result to show");
					break;
				}
				select=  Store.selectFromMenu(arr);
				return arr[select];
			case 4:
				System.out.println("Please Select employee position:");
				Position posArr[] = Position.values();
				int selection = Store.selectFromMenu(posArr);
				arr=searchEmployee("Position",posArr[selection]+"");
				if(arr.length == 0){ // if there no results
					System.out.println("No Result to show");
					break;
				}
				select=  Store.selectFromMenu(arr);
				return arr[select];
				
			case 5: //EXIT
				return null;		
			}	
		}
	}
	
	/**
	 * Search Employee by selected type
	 * @param type the type of search (ID,Name,All)
	 * @param Parameter to Search
	 * @return array of the Appropriate results
	 */
	public static Employee[] searchEmployee(String type, String value){
		ResultSet result = null;
		if(type.equals("ID")){
			result = DB.executeQuery("SELECT * FROM Employees WHERE ID LIKE '%"+value+"%';");	
		}
		else if(type.equals("Name")){
			result = DB.executeQuery("SELECT * FROM Employees WHERE Name LIKE '%"+value+"%'");		
		}
		else if(type.equals("All")){
			result = DB.executeQuery("SELECT * FROM Employees");		
		}
		else if(type.equals("Position")){
			result = DB.executeQuery("SELECT * FROM Employees WHERE Position = '"+value+"'");
		}
		return getEmployeeArr(result);
	}
	
	/**
	 * Create Employee array from ResultSet
	 * @param result from what we build the array
	 * @return Array of Employee
	 */
	private static Employee[] getEmployeeArr(ResultSet result){
		Vector<Employee> vector = new Vector<Employee>();
		while(DB.next(result)){
			vector.addElement( new Employee(false,DB.getInt(result, "ID"),DB.getString(result, "Name"),
					Position.valueOf(DB.getString(result, "Position")),DB.getInt(result, "BankNumber"),
					DB.getInt(result, "AccountNumber"),Store.stringToDate(DB.getString(result, "StartDate")),
					DB.getInt(result, "SalaryPerHour")));			
		}
		DB.closeResult(result);
		return vector.toArray(new Employee[0]);
	}
	
}
