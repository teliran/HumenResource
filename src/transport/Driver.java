package transport;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import com.sun.java.util.jar.pack.DriverResource;

import DB.DB;
import Emp.Employee;
import store.Store;
import transport.TransManager.License;
public class Driver extends Employee {
	private License _lisence;
	private String _available;
	
	public Driver(boolean insert,Employee emp , License license, String available) {
		super(false, emp.getId(), emp.getName(), emp.getPosition(), emp.getBankNumber() ,emp.getAccountNumber(),emp.getStartDate(), emp.getSalaryPerHour());
		this._lisence=license;
		_available = available;
		if (insert){
			String query = "INSERT INTO Drivers (ID,License,Available) " +
	                   "VALUES ("+emp.getId()+",'"+license+"', 'YES');";
			DB.executeUpdate(query);
		}
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
	
	public static void showDriversMenu(){
		int selection;
		while(true){
			System.out.println("============================");
			System.out.println("--------Drivers Menu--------");
			System.out.println("============================");
			System.out.println("1)   Search Driver");
			System.out.println("2)   Show Available Drivers");
			System.out.println("3)   Return");
			System.out.println("============================");
			System.out.println("--------Drivers Menu--------");
			System.out.println("============================");
			selection = TransManager.getInputNumber();
			switch (selection){
			case 1:
				showDriver(searchDriver());
				break;
			case 2:
				showDriver(showAvailableDrivers());
				break;
			case 3:
				return;
			}
		}
	}

	private static void showDriver(Driver d) {
		if (d==null)
			System.out.println("***No Driver to show***");
		else{
			System.out.println("===================================");
			System.out.println("-----------Driver: "+d.getId()+"----------");
			System.out.println("===================================");
			System.out.println("Name: "+ d.getName());
			System.out.println("License: "+d.getLicenseString());
			System.out.println("===================================");
		}
		System.out.println("Press any key to return");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();

	}


	//Here to put the choice
	public static Driver showAvailableDrivers() {
		Driver[] empArr = createDriverArr(Employee.getEmpOnShift(Store.currentDate, Position.driver));
		Vector<Driver> vecd = new Vector<Driver>();
		/*for(int i=0; i<empArr.length; i++){
			String qry = "SELECT "
		}*/
		int select = TransManager.selectFromChoises(empArr);
		if (select == -1)
			return null;
		return empArr[select];
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
			System.out.println("2)   Search By Name");
			System.out.println("3)   Search By License");
			System.out.println("4)   Exit");
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
				System.out.println("Please Enter Name:");
				drv=searchDriver("Name",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];	
			
				
			case 3:
				System.out.println("Please Enter License:");
				drv=searchDriver("License",sc.nextLine());
				if (drv.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(drv);
				return drv[secSelect];
				
			case 4:
				return null;
			}
		}		
	}
	
	@Override
	public String toString() {
		return super.toString() + " License: " + _lisence;
	}
	
	private static License getEmpLicense(Employee emp){
		License lis = null;
		ResultSet result = DB.executeQuery("SELECT * FROM Drivers WHERE ID = "+emp.getId());
		if(DB.next(result)){
			lis = License.valueOf(DB.getString(result, "License"));
		}
		DB.closeResult(result);
		return lis;
	}
	
	private static String getDriverAvailable(Employee d){
		String ret = null;
		ResultSet result = DB.executeQuery("SELECT * FROM Drivers WHERE ID = "+d.getId());
		if(DB.next(result)){
			ret= DB.getString(result, "Available");
		}
		DB.closeResult(result);
		return ret;
	}
	
	private static Driver[] createDriverArr (Employee[] empArr){
		Vector<Driver> driversResult = new Vector<Driver>();
		for(Employee emp : empArr){
			License lic = getEmpLicense(emp);
			String avl = getDriverAvailable(emp);
			if(lic != null){
				driversResult.add(new Driver(false, emp, lic, avl));
			}
		}
		return driversResult.toArray(new Driver[0]);
	}

	public static Driver[] searchDriver(String field,String value){
		ResultSet result = null;
		Employee[] empArr;
		Vector<Driver> driversResult = new Vector<Driver>();
		switch(field){
		case "ID":
		case "id":
			empArr = Employee.searchEmployee("ID",value);
			return createDriverArr(empArr);
		case "Name": 
			empArr = Employee.searchEmployee("Name",value);
			return createDriverArr(empArr);
		case "License":
		case "license":
			result = DB.executeQuery("SELECT * FROM Drivers WHERE License LIKE '%"+value+"%'");	
			Vector<Integer> vecId = new Vector();
			Vector<String> vecLic = new Vector();
			Vector<String> vecAvl = new Vector();
			while(DB.next(result)){
				vecId.addElement(DB.getInt(result, "ID"));
				vecLic.addElement(DB.getString(result, "License"));
				vecAvl.addElement(DB.getString(result, "Available"));
			}
			DB.closeResult(result);
			int i=0;
			for(Integer ID : vecId){
				empArr = Employee.searchEmployee("ID", ID+"");
				if(empArr.length==0){				
					return new Driver[0];	
				}				
				Employee emp = empArr[0];
				Driver tempD = new Driver(false, emp, License.valueOf(vecLic.elementAt(i)), vecAvl.elementAt(i));
				driversResult.add(tempD);
				i++;
			}
			return driversResult.toArray(new Driver[0]);	
		}
		return null;
	}
	
	
	
}
