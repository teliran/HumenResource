package Emp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;

public class Shift {
	public static enum ShiftPart{morning, evening};
	public ShiftPart shift;
	private Date date;
	private HashMap<Employee.Position, Vector<Employee>> positions;
	
	public Shift(boolean insert,ShiftPart shift ,Date date,HashMap<Employee.Position, Vector<Employee>> pos){
		this.shift=shift;
		this.date = date;
		positions = pos;
	}
	
	public boolean hasAmount(Employee.Position pos){
		return positions.containsKey(pos);
	}
	public int getAmount(Employee.Position pos){
		int ans=0;
		if(positions.containsKey(pos))
			ans = positions.get(pos).size();
		return ans ;
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
					addShift();
					break;
				case 3:
					return;
			}
		}	
	}
	
	public static void addShift(){
		int usrInput;
		Calendar c = Calendar.getInstance();    
		Date firstDay = Store.getFirstDayOfWeek(Store.currentDate, false);
		while(true){
			System.out.println("==Create Shift==");
			System.out.println("1.\t Edit Amount Employees on days");
			System.out.println("2.\t Make Shift!");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					System.out.println("Please Select Day To Update Amount Of Employee That Needed");
					String days[] = new String[11];
					for(int i=0; i<11; i++){
						c.setTime(firstDay);
						c.add(Calendar.DATE,i/2);
						days[i]= Store.setFormat(c.getTime())+" At Morning";
						i++;
						if(i != 11) // in Friday there are only morning shift
							days[i]= Store.setFormat(c.getTime())+" At Evening";
					}
					int ans = Store.selectFromMenu(days);
					c.setTime(firstDay);
					c.add(Calendar.DATE,ans/2);
					ShiftPart shift = ans%2==0 ? ShiftPart.morning : ShiftPart.evening;
					amountDay(searchByDate(c.getTime(),shift));				
					break;
				case 2:
					makeShift(firstDay);
					return;
				case 3:
					return;
			}
		}
				
	}
	
	public static int getAmountLastWeek(Employee.Position pos, Date date, ShiftPart shift){
		int ans=0;
		Calendar c = Calendar.getInstance();    	
		c.setTime(date);
		c.add(Calendar.DATE,-7);
		Shift s = searchByDate(c.getTime(), shift);
		if(s!= null)
			ans=s.getAmount(pos);
		return ans;
	}
	
	public static void amountDay(Shift shift){
		int usrInput;
		Employee.Position[] positions = Employee.Position.values();
		HashMap<Employee.Position,Integer> map = new HashMap<>();
		for( Employee.Position pos : positions)
			if(shift.hasAmount(pos))
				map.put(pos, shift.getAmount(pos));
			else{
				map.put(pos,getAmountLastWeek(pos,shift.date,shift.shift));
			}				
		while(true){
			System.out.println("===Workers Per Shift "+Store.setFormat(shift.date)+" At "+shift.shift+"===");
			
			String[] str = new String[positions.length];
			for(int i =0; i<str.length; i++)
				str[i] = positions[i] +" (Current :"+map.get(positions[i])+")";		
		}
		
	}
	
	public static void makeShift(Date date){
		HashMap<Employee, int[]> map = new HashMap<>();
		Employee[] empArr = Employee.searchEmployee("ALL", "");
		for(Employee emp : empArr){
			int[] arr = new int[2]; // 0 - shift in week , 1- number of Constraint
			arr[1] = Constraint.getNumberOfConstraint(emp);
			map.put(emp, arr);
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
					showWeek(Store.getFirstDayOfWeek(Store.currentDate, true));					
					break;
				case 2:
					System.out.println("Please Enter Date to Search (DD/MM/YYYY)");
					Shift.ShiftPart parts [] = Shift.ShiftPart.values();
					Date ans =Store.stringToDate(sc.nextLine());
					System.out.println("Please Select Shift");
					shift = searchByDate(ans,parts[Store.selectFromMenu(parts)]);					
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
	
	public static void showWeek(Date date){
		Calendar c = Calendar.getInstance();    	
		for(int i=0; i<6; i++){ // Sunday to Friday
			c.setTime(date);
			c.add(Calendar.DATE,i);
			showCard(searchByDate(c.getTime(), ShiftPart.morning));
			if(i != 5) // in Friday there are no evening shift
				showCard(searchByDate(c.getTime(), ShiftPart.evening));
		}	
	}
	
	public static Shift searchByDate(Date date ,ShiftPart shift){
		ResultSet result = null;
		Employee emp = null;
		Vector<Integer> vector = new Vector();
		HashMap<Employee.Position, Vector<Employee>> positions = new HashMap<>();	
		result = DB.executeQuery("SELECT * FROM Scheduling WHERE date = '"+Store.setFormat(date)+"' AND Shift ='"+shift+"'");
		while(DB.next(result)){ // create vector of id
			vector.addElement(DB.getInt(result, "ID"));
		}
		DB.closeResult(result);
		for(Integer id : vector){
			emp = Employee.searchEmployee("ID",id+"")[0];
			if(!positions.containsKey(emp.getPosition()))
				positions.put(emp.getPosition(), new Vector<>());
			positions.get(emp.getPosition()).add(emp);
		}	
		return new Shift(false,shift,date, positions);	
	}
	
	public static void showCard(Shift shift){
		System.out.print("***"+shift.getDay()+" ");
		System.out.print(Store.setFormat(shift.getDate())+" ");
		System.out.println("At "+shift.getShift()+"***");
		Iterator it = shift.positions.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry pair = (HashMap.Entry)it.next();
			System.out.println(pair.getKey()+"s:("+((Vector<Employee>)pair.getValue()).size()+")");
			System.out.print("\t");
			for(Employee emp : (Vector<Employee>)pair.getValue())
				System.out.print(emp+"; ");
			System.out.println();
		}
	}
	
	public String getDay(){
		 DateFormat format2=new SimpleDateFormat("EEEE"); 
		 String finalDay=format2.format(date);
		 return finalDay;
	}
	public void setShift(ShiftPart shifts){
		this.shift=shifts;
	}
	public ShiftPart getShift(){
		return shift;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.date=format.parse(date);
		} catch (ParseException e) {
		}
	}
	
	
	
	

}
