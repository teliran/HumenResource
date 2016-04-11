package Emp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import DB.DB;

public class Shift {
	public static enum ShiftPart{morning, evening};
	public ShiftPart shifts;
	private Date date;
	private HashMap<Employee.Position, Integer> positions;
	
	public Shift(boolean insert,ShiftPart shifts ,Date date){
		this.shifts=shifts;
		this.date = date;
		positions = new HashMap<>();	
	}
	
	public static void showMenu(){
		int usrInput;
		while(true){
			System.out.println("==Shifts==");
			System.out.println("1.\t Show Shifts History");
			System.out.println("2.\t Make Shift For Next Week");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					history();					
					break;
				case 2:
					
					break;
				case 3:
					return;
			}
		}	
	}
	
	public static void history(){
		int usrInput;
		Shift shift;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("==Shifts History==");
			System.out.println("1.\t Show Current Week Shift");
			System.out.println("2.\t Search Shift By Date");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					shift = searchByDate(Store.currentDate);
					if(shift == null){
						System.out.println("No Result to show");
						return;
					}
					showCard(shift);
					break;
				case 2:
					System.out.println("Please Enter Date to Search (DD/MM/YYYY)");
					shift = searchByDate(Store.stringToDate(sc.nextLine()));					
					if(shift == null){
						System.out.println("No Result to show");
						return;
					}
					showCard(shift);
				case 3:
					return;
			}
		}
	}
	
	public static Shift searchByDate(Date date){
		ResultSet result = null;
		date = Store.getFirstDayOfWeek(date, true);
		result = DB.executeQuery("SELECT * FROM Shifts WHERE Shift_Date = '"+Store.setFormat(date)+"'");
		if(!DB.next(result))
			return null;
		System.out.println(DB.getString(result, "Shift"));
		return new Shift(false,ShiftPart.valueOf(DB.getString(result, "Shift")),date);	
	}
	
	public static void showCard(Shift shift){
		System.out.println(shift.getDay());
		System.out.println(shift.getDate());
		System.out.println(shift.getShifts());
	}
	
	public String getDay(){
		 DateFormat format2=new SimpleDateFormat("EEEE"); 
		 String finalDay=format2.format(date);
		 return finalDay;
	}
	public void setShifts(ShiftPart shifts){
		this.shifts=shifts;
	}
	public ShiftPart getShifts(){
		return shifts;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date=format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
