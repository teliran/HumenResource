package Emp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Shift {
	public static enum ShiftPart{morning, evening};
	public ShiftPart shifts;
	private Date date;
	private HashMap<Employee.Position, Integer> positions;
	
	public Shift(ShiftPart shifts ,Date date){
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
					Shift select = history();
					if(select == null)
						break;
					
					break;
				case 2:
					
					break;
				case 3:
					return;
			}
		}	
	}
	
	public static Shift history(){
		int usrInput;
		while(true){
			System.out.println("==Shifts History==");
			System.out.println("1.\t Show Current Week Shift");
			System.out.println("2.\t Search Shift By Date");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					break;
				case 2:
					
					break;
				case 3:
					return null;
			}
		}
	}
	
	public static Shift searchByDate(Date date){
		
		
		return null;
		
	}
	
	public static void showCard(Shift shift){
		
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
