package Emp;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;
import Emp.Employee.Position;

public class Constraint {
	private int id;
	private Date startHour;
	private Date endHour;
	private Store.Week day;
	
	
	public Constraint(boolean insert, int id, Store.Week day, Date startHour,Date endHour){
		this.id = id;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		if (insert){
			SimpleDateFormat format=new SimpleDateFormat("HH:mm");
			String startHourTable = format.format(startHour);
			String endHourTable =format.format(endHour);
			String query = "INSERT INTO Constraints(ID, Day, Start, End)" +
						"VALUES ("+id+",'"+day+"', '"+startHourTable+"', '"+endHourTable+"');";
			DB.executeUpdate(query);
		}
	}
	
	
	public static void showMenu(){
		int usrInput;
		while(true){
			System.out.println("==Constraints==");
			System.out.println("1.\t Add New Constraint");
			System.out.println("2.\t Search Constraints");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					showCard(addConstraint());
					break;
				case 2:
					showCard(searchConstraint());
					break;
				case 3:
					return;
			}
		}	
	}

	public static void showCard(Constraint con){
		int usrInput;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("==Employee Constraint==");
			String name = Employee.searchEmployee("ID", con.getId()+"")[0].getName();
			System.out.println("== "+name+" ==");
			System.out.println("1.\t Edit Day (Current : "+con.getDay()+")");
			System.out.println("2.\t Edit Start Hour (Current : "+con.startHour+")");
			System.out.println("3.\t Edit End Hour (Current : "+con.endHour+")");
			System.out.println("4.\t Delete Constraint");
			System.out.println("5.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					System.out.println("Please select the new Day:");
					Store.Week[] dayArr =Store.Week.values();
					int day = Store.selectFromMenu(dayArr);
					con.setDay(dayArr[day]);
					break;
				case 2:
					System.out.println("Please enter the new Start Hour (HH:mm 24Hr):");
					con.setStartHour(Store.stringToHour(sc.nextLine()));
					break;
				case 3:
					System.out.println("Please enter the End Hour(HH:mm 24Hr):");
					con.setEndHour(Store.stringToHour(sc.nextLine()));
					break;
				case 4:
					DB.executeQuery("DELETE FROM Constraints WHERE ID="+con.getId()+"");
					break;
				case 5:
					return;
			}
		}
	}
	
	public static Constraint addConstraint(){
		System.out.println("Add New Constraint");
		System.out.println("Enter Employee ID (9 digits):");
		int id = Store.getNumber();
		while(Employee.searchEmployee("ID",id+"").length == 0){ // validate valid ID
			System.out.println("The enterd ID is not available, Please Try Again");
			id = Store.getNumber();
		}
		System.out.println("Enter Day Constraint:");
		Store.Week[] dayArr = Store.Week.values();
		int j = Store.selectFromMenu(dayArr);
		Store.Week day = dayArr[j];
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Start Hour (HH:mm 24Hr):");
		Date start = Store.stringToHour(sc.nextLine());
		System.out.println("Please enter End Hour (HH:mm 24Hr):");
		Date end = Store.stringToHour(sc.nextLine());
		return new Constraint(true, id, day, start, end);
	}

	public static Constraint[] searchConstraint(String type, String value) {
		ResultSet result = null;
		if(type.equals("ID")){
			result = DB.executeQuery("SELECT * FROM Constraints WHERE ID LIKE '%"+value+"%';");	
		}
		else if(type.equals("All")){
			result = DB.executeQuery("SELECT * FROM Constraints");		
		}
		else if(type.equals("Day")){
			result = DB.executeQuery("SELECT * FROM Constraints WHERE Day= '"+value+"'");		
		}
		return getConsArr(result);
	}
	
	public static Constraint searchConstraint(){
		int userInput,select;
		Scanner sc = new Scanner(System.in);
		Constraint[] arr;
		while(true){
			System.out.println("==Search Constraint==");
			System.out.println("1.\t Search By ID");
			System.out.println("2.\t Search By Day");
			System.out.println("3.\t Show All Constraints");
			System.out.println("4.\t Exit");
			userInput = Store.getNumber();			
			switch (userInput){
				case 1:{
					System.out.println("Please Enter ID:");
					arr = searchConstraint("ID",sc.nextLine());
					if(arr.length == 0){ // if there no results
						System.out.println("No Constraint for this employee!");
						break;
					}
					select=  Store.selectFromMenu(arr);
					return arr[select];
				}
				case 2:{
					System.out.println("Please Enter Day in week:");
					Store.Week[] dayArr =Store.Week.values();
					int selection = Store.selectFromMenu(dayArr);
					arr=searchConstraint("Day",dayArr[selection]+"");
					if(arr.length == 0){ // if there no results
						System.out.println("No Constraint for " +dayArr[selection]);
						break;
					}
					select= Store.selectFromMenu(arr);
					return arr[select];
				}
				case 3:
					arr=searchConstraint("All", "");
					if(arr.length == 0){ // if there no results
						System.out.println("No Result to show");
						break;
					}
					select= Store.selectFromMenu(arr);
					return arr[select];
				case 4:
					return null;
			}
		}
	}
	
	
	private static Constraint[] getConsArr(ResultSet result){
		Vector<Constraint> vector = new Vector<Constraint>();
		while(DB.next(result)){
			vector.addElement(new Constraint(false,DB.getInt(result, "ID"),Store.Week.valueOf(DB.getString(result, "Day")),
			Store.stringToHour(DB.getString(result,"Start")),Store.stringToHour(DB.getString(result,"End"))));
		}
		DB.closeResult(result);
		return vector.toArray(new Constraint[0]);
	}
	
	public String toString(){
		Employee[] myEmp = Employee.searchEmployee("ID", getId()+"");
		String ans = "ID: " +getId()+ " Name: "+myEmp[0].getName()+" Day: " +getDay()+ " Start Hour: " +getStartHour()+ "End Hour: " +getEndHour();
		return ans;
	}

	//Getters AND Setters Methods
	
	public Store.Week getDay() {
		return day;
	}
	
	public void setDay(Store.Week day){
		this.day = day;
		DB.executeUpdate("UPDATE Constraints set Day = '"+day+"' WHERE ID ="+id);
	}

	public Date getStartHour() {
		return startHour;
	}


	public void setStartHour(Date startHour) {
		this.startHour = startHour;
		DB.executeUpdate("UPDATE Constraints set Start = '"+startHour+"' WHERE ID ="+id);
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
		DB.executeUpdate("UPDATE Constraints set End = '"+endHour+"' WHERE ID ="+id);
	}


	public int getId() {
		return id;
	}

}





