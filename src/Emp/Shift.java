package Emp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;
import Emp.Employee.Position;

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
		boolean ans= false;
		ResultSet result = DB.executeQuery("SELECT * FROM Shifts WHERE Shift_Date = '"+Store.setFormat(date)+"' "
				+ "AND Shift ='"+shift+"' AND Position ='"+pos+"'");
		while(DB.next(result)){ // create vector of id
			ans=true;
		}
		return ans;
	}
	public int getAmount(Employee.Position pos){
		int ans=0;
		ResultSet result = DB.executeQuery("SELECT * FROM Shifts WHERE Shift_Date = '"+Store.setFormat(date)+"' "
				+ "AND Shift ='"+shift+"' AND Position ='"+pos+"'");
		while(DB.next(result)){ // create vector of id
			ans=DB.getInt(result, "Amount");
		}
		return ans ;
	}
	
	public static void showMenu(){
		int usrInput;
		while(true){
			System.out.println("==Shifts==");
			System.out.println("1.\t Show Shifts History");
			System.out.println("2.\t Make Shift For Next Week");
			System.out.println("3.\t Swap Shift");
			System.out.println("4.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					history();					
					break;
				case 2:
					addShift();
					break;
				case 3:
					swapShifts();
					break;
				case 4:
					return;
			}
		}	
	}
	
	public static boolean isShiftForNextWeek(){
		ResultSet result = DB.executeQuery("SELECT * FROM Scheduling WHERE date = '"+Store.setFormat(Store.getFirstDayOfWeek(Store.currentDate, false))+"'");
		if(DB.next(result)){ // create vector of id
			DB.closeResult(result);
			return true;
		}
		DB.closeResult(result);
		return false;	
	}
	
	public static void hasShiftNextWeek(){
		int usrInput;
		while(true){
			System.out.println("==You Already Have Shift To The Next Week==");
			System.out.println("1.\t Cancel Schedule ");
			System.out.println("2.\t Show Schedule");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:{
					Calendar c = Calendar.getInstance();
					for(int i =0; i<6; i++){
						c.setTime(Store.getFirstDayOfWeek(Store.currentDate, false));
						c.add(Calendar.DATE,i);
						DB.executeUpdate("DELETE FROM Scheduling WHERE Date ='"+Store.setFormat(c.getTime())+"'");
					}		
					System.out.println("Cancel Complete");
					return;				
				}					
				case 2:
					showWeek(Store.getFirstDayOfWeek(Store.currentDate, false));
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
		if(isShiftForNextWeek()){
			hasShiftNextWeek();
			return;	
		}				
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
	
	public void setAmount(Employee.Position pos,int amount){
		if(!hasAmount(pos)){
			String query = "INSERT INTO Shifts (Shift_Date,Shift,Position,Amount) " +
	                   "VALUES ('"+Store.setFormat(date)+"', '"+shift+"', '"+pos+"' ,'"+amount+"');";
			DB.executeUpdate(query);
		}
		else{
			DB.executeUpdate("UPDATE Shifts set Amount = '"+amount+"' WHERE Shift_Date ='"+Store.setFormat(date)+"' AND "
					+ "Shift='"+shift+"' AND Position = '"+pos+"'");
			
		}
		positions.get(pos);
	}
	
	public static void amountDay(Shift shift){
		Employee.Position[] positions = Employee.Position.values();
		HashMap<Employee.Position,Integer> map = new HashMap<>();
		for( Employee.Position pos : positions)
			if(pos.equals(Employee.Position.shiftManager))
				map.put(pos,1);
			else if(shift.hasAmount(pos))
				map.put(pos, shift.getAmount(pos));
			else{
				map.put(pos,getAmountLastWeek(pos,shift.date,shift.shift));
			}				
		while(true){
			System.out.println("===Workers Per Shift "+Store.setFormat(shift.date)+" At "+shift.shift+"===");			
			String[] str = new String[positions.length+1];
			int i;
			for(i =0; i<positions.length; i++)
				str[i] = positions[i] +" (Current :"+map.get(positions[i])+")";
			

			str[i]="Return Back";
			int ans = Store.selectFromMenu(str);
			if(ans == i)
				return;
			if(positions[ans].equals(Employee.Position.shiftManager)){
				System.out.println("You must have 1 Shift Manager on a shift!");
				continue;
			}
			System.out.println("Please Enter The New Amount:");
			int number =Store.getNumber();
			shift.setAmount(positions[ans],number );
			map.put(positions[ans], number);
		}
		
	}
	
	public static void makeShift(Date date){
		// Init
		Shift temp = null;
		Calendar c = Calendar.getInstance(); 
		HashMap<Employee, int[]> map = new HashMap<>();
		Employee[] empArr = Employee.searchEmployee("All", "");
		for(Employee emp : empArr){
			int[] arr = new int[2]; // 0 - shift in week , 1- number of Constraint
			arr[1] = Constraint.getNumberOfConstraint(emp);
			map.put(emp, arr);
		}
		int current = 0;
		Shift[] shiftArr = new Shift[11];
		//finish Init	
		// run over the dates
		for(int i =0; i<6; i++){
			c.setTime(date);
			c.add(Calendar.DATE,i);
			temp =makeShift(c.getTime(),ShiftPart.morning,map);
			if(temp == null)
				return;
			shiftArr[current] = temp;
			current++;
			
			if(i != 5){
				temp = makeShift(c.getTime(),ShiftPart.evening,map);
				if(temp == null)
					return;
				shiftArr[current] = temp;
				current++;
			}			
		}
		System.out.println("Build Complete!");
		insertToDb(shiftArr);
		
	}
	
	private static void insertToDb(Shift[] arr){
		for(Shift s : arr){
			Iterator<?> it = s.positions.entrySet().iterator();
			while(it.hasNext()){
				HashMap.Entry<Employee.Position, Vector<Employee>> pair = (HashMap.Entry)it.next();
				for(Employee emp : pair.getValue()){
					String query = "INSERT INTO Scheduling(ID, Date, Shift)" +
							"VALUES ("+emp.getId()+",'"+Store.setFormat(s.date)+"', '"+s.shift+"');";
					DB.executeUpdate(query);
				}
			}	
		}
		showWeek(Store.getFirstDayOfWeek(Store.currentDate, false));
		
	}
	
	private static Shift makeShift(Date date , ShiftPart shiftPart , HashMap<Employee, int[]> map){		
		Employee.Position[] positions = Employee.Position.values();
		HashMap<Employee.Position,Integer> posMap = new HashMap<>(); // position and number that needed
		Shift shift = searchByDate(date,shiftPart);
		for( Employee.Position pos : positions){
			if(pos.equals(Employee.Position.shiftManager))
				posMap.put(pos,1);
			else if(shift.hasAmount(pos))
				posMap.put(pos, shift.getAmount(pos));
			else
				posMap.put(pos,getAmountLastWeek(pos,shift.date,shift.shift));
		}
		HashMap<Position, Vector<Employee>> posVec = new HashMap<>();
		Iterator<?> it = posMap.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry<Employee.Position, Integer> pair = (HashMap.Entry)it.next();
			if((int)pair.getValue() == 0)
				continue ;
			
			Employee[] empArr = Employee.searchEmployee("Position",pair.getKey()+"");
			sortArr(empArr,map);
			Vector<Employee> vec = new Vector<>();
			for(int i=0; (int)pair.getValue() > 0 &&  i<empArr.length; i++){
				if(Constraint.isAvailable(empArr[i], date, shiftPart)){
					vec.addElement(empArr[i]);
					pair.setValue((int)pair.getValue()-1);  //decrease needed amount 
					map.get(empArr[i])[0]++;
				}
			}
			if ((int)pair.getValue() > 0){
				System.out.println("Problem on "+Store.setFormat(date)+" "+shiftPart);
				System.out.println("We need more "+pair.getValue()+" "+pair.getKey());
				return null;
			}
			posVec.put(pair.getKey(), vec);
		}
		return new Shift(false, shiftPart, date, posVec);		
	}
	
	private static void sortArr(Employee[] empArr, HashMap<Employee, int[]> map){
		List<Employee> list = new ArrayList<>();
		for(Employee emp : empArr)
			list.add(emp);
		 list.sort(new Comparator<Employee>() {
		        public int compare(Employee o1, Employee o2) {
		            Integer x1 = map.get((Employee)o1)[0];
		            Integer x2 = map.get((Employee)o2)[0];
		            int sComp = x1.compareTo(x2); // the number of shift is from low to high

		            if (sComp != 0) {
		               return sComp;
		            } else {
		            	Integer y1 = map.get((Employee)o1)[1];
			            Integer y2 = map.get((Employee)o2)[1];
		               return y2.compareTo(y1); // the number of constraint is from high to low
		            }
		    }});

		 for(int i=0; i<empArr.length; i++){
			 empArr[i] = list.get(i);
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
		Vector<Integer> vector = new Vector<Integer>();
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
		Iterator<?> it = shift.positions.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry pair = (HashMap.Entry)it.next();
			System.out.println("\t"+pair.getKey()+"s:("+((Vector<Employee>)pair.getValue()).size()+")");
			for(Employee emp : (Vector<Employee>)pair.getValue())
				System.out.println("\t\t"+emp+" ");
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
	
	public static void swapShifts(){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter shift Date of first employee");
		Date date = Store.stringToDate(sc.nextLine());
		System.out.println("please enter morning OR evening");
		ShiftPart[] shiftPart = ShiftPart.values();
		int part = Store.selectFromMenu(shiftPart);
		System.out.println("please enter employee position");
		Employee.Position[] pos = Employee.Position.values();
		int i = Store.selectFromMenu(pos);
		Shift firstShift = searchByDate(date, shiftPart[part]);
		Vector <Employee> vecEmp = firstShift.positions.get(pos[i]);
		if (vecEmp == null){
			System.out.println("No such Employee in givven shift");
			return;
		}
			
		Employee[] empArr = firstShift.positions.get(pos[i]).toArray(new Employee[0]);
		Employee firstEmp = empArr[Store.selectFromMenu(empArr)];
		/*Second Employee*/
		System.out.println("please enter shift Date of second employee");
		Date date1 = Store.stringToDate(sc.nextLine());
		System.out.println("please enter morning OR evening");
		int part1 = Store.selectFromMenu(shiftPart);
		Shift secondShift = searchByDate(date1,shiftPart[part1]);
		Vector <Employee> vecEmp1 = firstShift.positions.get(pos[i]);
		if (vecEmp1 == null){
			System.out.println("No such Employee in givven shift");
			return;
		}
		Employee[] empArr1 = secondShift.positions.get(pos[i]).toArray(new Employee[0]);
		Employee secondEmp = empArr1[Store.selectFromMenu(empArr1)];		
		if(secondShift.positions.get(pos[i]).contains(firstEmp)){
			System.out.println("Ileagal Shift Replace!!");
			return;
		}
		DB.executeUpdate("DELETE FROM Scheduling WHERE ID = "+firstEmp.getId()+" AND Date ='"+ Store.setFormat(date)+"' AND Shift='"+shiftPart[part]+"'");
		DB.executeUpdate("DELETE FROM Scheduling WHERE ID = "+secondEmp.getId()+" AND Date ='"+ Store.setFormat(date1)+"' AND Shift='"+shiftPart[part1]+"'");
		DB.executeUpdate("INSERT INTO Scheduling (ID,Date,Shift) VALUES ("+firstEmp.getId()+",'"+ Store.setFormat(date1)+"','"+shiftPart[part1]+"')");
		DB.executeUpdate("INSERT INTO Scheduling (ID,Date,Shift) VALUES ("+secondEmp.getId()+",'"+ Store.setFormat(date)+"','"+shiftPart[part]+"')");
		System.out.println("Shifts has replaced !!!");
	}
	
}
