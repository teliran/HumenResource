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
