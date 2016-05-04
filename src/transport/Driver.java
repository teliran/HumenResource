package transport;
import java.sql.*;
import java.util.Scanner;
import java.util.Vector;
import DB.DB;
import transport.TransManager.License;
public class Driver {
	
	private String _firstName;
	private String _lastName;
	private int _id;
	private License _lisence;
	
	public Driver(boolean toAdd,String _firstName, String _lastName, int _id, License _lisence) {
		this._firstName = _firstName;
		this._lastName = _lastName;
		this._id = _id;
		this._lisence = _lisence;
		if (toAdd){
			String query = "INSERT INTO Drivers (ID,First_Name,Last_Name,License) " +
	                   "VALUES ("+_id+", '"+_firstName+"', '"+_lastName+"' ,'"+getLicenseString()+"');";
			DB.executeUpdate(query);
		}
	}

	public String get_firstName() {
		return _firstName;
	}

	public String get_lastName() {
		return _lastName;
	}

	public int get_id() {
		return _id;
	}

	public License get_lisence() {
		return _lisence;
	}
	public String getLicenseString(){
		String ret ="";
		switch (_lisence){
		case A:
			ret = "A";
			break;
		case B:
			ret = "B";
			break;
		case C:
			ret ="C";
			break;
		}
		return ret;
	}
	
	private static License getStringToLicense(String lis){
		switch (lis){
		case "A":
			return License.A;
		case "B":
			return License.B;
		case "C":
			return License.C;
		}
		return License.A;
	}

	@Override
	public String toString() {
		return "Driver [ID: "+_id+" First Name: " + _firstName + ", Last Name: " + _lastName + ", Lisence: " + getLicenseString() + "]";
	}
	
	public static void showDriversMenu(){
		int selection;
		while(true){
			System.out.println("============================");
			System.out.println("--------Drivers Menu--------");
			System.out.println("============================");
			System.out.println("1)   Search Driver");
			System.out.println("2)   Show Available Drivers");
			System.out.println("3)   Add New Driver");
			//System.out.println("4)   Delete Driver");
			System.out.println("4)   Return");
			System.out.println("============================");
			System.out.println("--------Drivers Menu--------");
			System.out.println("============================");
			selection = TransManager.getInputNumber();
			switch (selection){
			case 1:
				showDriver(searchDriver());
				break;
			case 2:
				showAvailableDrivers();
				break;
			case 3:
				addDriver();
				break;
			case 4:
				return;
			}
		}
	}

	private static void showDriver(Driver d) {
		System.out.println("===================================");
		System.out.println("-----------Driver: "+d.get_id()+"----------");
		System.out.println("===================================");
		System.out.println("First Name: "+ d.get_firstName());
		System.out.println("Last Name: "+ d.get_lastName());
		System.out.println("License: "+d.getLicenseString());
		System.out.println("===================================");
		System.out.println("-----------Driver: "+d.get_id()+"----------");
		System.out.println("===================================");
	}

	private static Driver addDriver() {
		System.out.println("===============================");
		System.out.println("-----------Add Driver----------");
		System.out.println("===============================");
		System.out.println("Enter Driver ID (9 digits):");
		int id = TransManager.getInputNumber();
		while(searchDriver("ID",id+"").length != 0){ // result array isn't empty
			System.out.println("The entered ID already exists");
			id = TransManager.getInputNumber();
		}
		System.out.println("Enter Driver First Name:");
		Scanner sc = new Scanner(System.in);
		String firstName = sc.nextLine();
		System.out.println("Enter Driver Last Name:");
		String lastName = sc.nextLine();
		System.out.println("Enter Driver License: A, B, C");
		String license = sc.nextLine();
		while (!(license.equals("A")||license.equals("B")||license.equals("C"))){
			System.out.println("Not a Valid License, Try again");
			license = sc.nextLine();
		}
		return new Driver(true, firstName, lastName, id, getStringToLicense(license));	
	}

	private static void showAvailableDrivers() {
		int i = 1;
		ResultSet result = DB.executeQuery("SELECT * FROM Drivers");
		while(DB.next(result)){
			Driver tempD = new Driver(false, DB.getString(result, "First_Name"), 
					DB.getString(result, "Last_Name"), DB.getInt(result, "ID"), 
					getStringToLicense((DB.getString(result, "License"))));
			System.out.println(i+")   "+ tempD.toString());
			i++;
		}
		DB.closeResult(result);
		
	}

	/**
	 * Finds a driver according to given options.
	 * Options are: ID, First Name, Last Name, License
	 * Options are presented in a menu search.
	 * @return a requested driver
	 */
	public static Driver searchDriver(){
		int primSelect; //Primary selection
		int secSelect; //Secondary selection
		Driver [] drv;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("===============================");
			System.out.println("--------Search Driver--------");
			System.out.println("===============================");
			System.out.println("1)   Search By ID");
			System.out.println("2)   Search By First Name");
			System.out.println("3)   Search By Last Name");
			System.out.println("4)   Search By License");
			System.out.println("5)   Exit");
			System.out.println("===============================");
			System.out.println("--------Search Driver--------");
			System.out.println("===============================");
			primSelect = TransManager.getInputNumber();
			switch(primSelect){
			case 1:
				System.out.println("Please Enter ID:");
				drv=searchDriver("ID",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];
			
			case 2:
				System.out.println("Please Enter First Name:");
				drv=searchDriver("First Name",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];	
			
			case 3:
				System.out.println("Please Enter Last Name:");
				drv=searchDriver("Last Name",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];
				
			case 4:
				System.out.println("Please Enter License:");
				drv=searchDriver("License",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];
				
			case 5:
				return null;
			}
		}
			
	}

	public static Driver[] searchDriver(String field,String value){
		ResultSet result = null;
		Vector<Driver> driversResult = new Vector<Driver>();
		switch(field){
		case "ID":
		case "id":
			result = DB.executeQuery("SELECT * FROM Drivers WHERE ID LIKE '%"+value+"%';");
			break;
		case "First Name": 
		case "First_Name": 
		case "first name":
			result = DB.executeQuery("SELECT * FROM Drivers WHERE First_Name LIKE '%"+value+"%'");
			break;
		case "Last Name": 
		case "Last_Name": 
		case "last name":
			result = DB.executeQuery("SELECT * FROM Drivers WHERE Last_Name LIKE '%"+value+"%'");
			break;
		case "License":
		case "license":
			result = DB.executeQuery("SELECT * FROM Drivers WHERE License LIKE '%"+value+"%'");
			break;	
		}
		while(DB.next(result)){
			Driver tempD = new Driver(false, DB.getString(result, "First_Name"), 
					DB.getString(result, "Last_Name"), DB.getInt(result, "ID"), 
					getStringToLicense((DB.getString(result, "License"))));
			driversResult.add(tempD);
		}
		DB.closeResult(result);
		return driversResult.toArray(new Driver[0]);	
	}
	
	
	
}
