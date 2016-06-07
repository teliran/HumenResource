package Emp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import DB.DB;
import Emp.Employee.Position;
import Emp.Shift.ShiftPart;
import store.Store;
import store.Store.Week;

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
			int index=0;
			System.out.println("==Constraints==");
			if(Store.user.equals(Position.hrManager))
				System.out.println((++index)+".\t Add New Constraint");
			System.out.println((++index)+".\t Search Constraints");
			System.out.println((++index)+".\t Back");
			usrInput = Store.getNumber();
			if(Store.user.equals(Position.storeManager)){
				if(usrInput == 2)
					usrInput=3;
				else if(usrInput == 3)
					continue;
				else
					usrInput++;
			}
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
		if (con == null){
			return;
		}
		int usrInput;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("==Employee Constraint==");
			String name = Employee.searchEmployee("ID", con.getId()+"")[0].getName();
			System.out.println("== "+name+" ==");
			System.out.println("1.\t Edit Day (Current : "+con.getDay()+")");
			System.out.println("2.\t Edit Start Hour (Current : "+Store.setHour(con.startHour)+")");
			System.out.println("3.\t Edit End Hour (Current : "+Store.setHour(con.endHour)+")");
			System.out.println("4.\t Delete Constraint");
			System.out.println("5.\t Back");
			usrInput = Store.getNumber();
			if(Store.user.equals(Position.storeManager) && usrInput != 5){
				System.out.println("Manager Cannot Change Items!");
				continue;
			}
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
					DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId()+" AND Day='"+con.getDay()+"'");
					break;
				case 5:
					return;
			}
		}
	}
	
	private static boolean isFree(int id , Week day){
		Constraint[] arr = searchConstraint("ID", id+"");
		for(Constraint cons : arr ){
			if (cons.getDay().equals(day))
				return false;
		}
		return true;
	}
	
	public static Constraint addConstraint(){
		System.out.println("Add New Constraint");
		Employee myEmp = Employee.searchEmployee();
		if (myEmp == null){
			return null;
		}
		System.out.println("Enter Day Constraint:");
		Store.Week[] dayArr = Store.Week.values();
		int j = Store.selectFromMenu(dayArr);
		if(! isFree(myEmp.getId(), dayArr[j])){
			System.out.println("The selected day is not free");
			return null;
		}		
		Store.Week day = dayArr[j];
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Constraint Start Hour (HH:mm 24Hr):");
		Date start = Store.stringToHour(sc.nextLine());
		System.out.println("Please enter Constraint End Hour (HH:mm 24Hr):");
		Date end = Store.stringToHour(sc.nextLine());
		return new Constraint(true, myEmp.getId(), day, start, end);
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
		String ans = "ID: " +getId()+ "\n   Name: "+myEmp[0].getName()+"\n   Day: " +getDay()+ "\n   Start Hour: " +Store.setHour(getStartHour())+ "\n   End Hour: " +Store.setHour(getEndHour())+"\n";
		return ans;
	}
	/*
	 * @param employee
	 * return the employees constraints amount
	 */
	public static int getNumberOfConstraint(Employee emp){
		int ans=0;
		for(Store.Week day: Store.Week.values()){
			Constraint[] dayCon;
			dayCon = searchConstraint("Day",day+"");
			for(int i=0; i<dayCon.length; i++){
				if(dayCon[i].getId()==emp.getId()){
					ans+= shiftsInCon(dayCon[i]);
				}
			}
		}		
		return ans;
		
	}
	/*
	 * @param empCon constraint of specific employee
	 * return the employees constraint in shifts
	 * 0-No constraints
	 * 1- Morning OR Evening Shift
	 * 2- Morning AND Evening Shift
	 */
	public static int shiftsInCon(Constraint empCon){
		int ans=0;
		Date morning =Store.stringToHour("15:00");
		if(empCon.getStartHour().before(morning)){
			ans++;
		}
		if(empCon.getEndHour().after(morning)){
			ans++;
		}
		return ans;
	}
	
	/*
	 * 
	 * return the employees Availability in specific day and shift
	 */
	public static boolean isAvailable(Employee emp, Date day, Shift.ShiftPart shift){
		DateFormat format=new SimpleDateFormat("EEEE"); 
		String finalDay=format.format(day);
		Constraint[] empCon = searchConstraint("Day", finalDay);
		Vector<Constraint> vec = new Vector<>();
		for(Constraint cons : empCon)
			if(cons.id== emp.getId())
				vec.addElement(cons);
		empCon = (Constraint[]) vec.toArray(new Constraint[0]);
		Date morning =Store.stringToHour("15:00");
		if(empCon.length == 0)
			return true;
		if (shift.equals(ShiftPart.morning)){
			if (empCon[0].getStartHour().before(morning))
				return false;	
		}
		else if (empCon[0].getEndHour().after(morning)){
				return false ;
		}
		return true;
		
	}

	//Getters AND Setters Methods
	
	public Store.Week getDay() {
		return day;
	}
	
	public void setDay(Store.Week day){
		if(! isFree(this.getId(), day)){
			System.out.println("The selected day is not free");
			return;
		}
		DB.executeUpdate("UPDATE Constraints set Day = '"+day+"' WHERE ID ="+id +" AND Day ='"+day+"'");
		this.day = day;	
	}

	public Date getStartHour() {
		return startHour;
	}


	public void setStartHour(Date startHour) {
		this.startHour = startHour;
		DB.executeUpdate("UPDATE Constraints set Start = '"+Store.setHour(startHour)+"' WHERE ID ="+id +" AND Day ='"+day+"'");
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
		DB.executeUpdate("UPDATE Constraints set End = '"+Store.setHour(endHour)+"' WHERE ID ="+id +" AND Day ='"+day+"'");
	}


	public int getId() {
		return id;
	}

}





