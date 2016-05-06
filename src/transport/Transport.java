package transport;
import java.util.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import DB.DB;

public class Transport {
	private int ID;
	private int driverID;
	private int truckID;
	private String desAddress;
	private String fromAddress;
	private Date depDate;
	private Date depTime;
	private int contactPhone;
	private String contactName;
	private String deocNum;
	private static Scanner scan;
	
	public Transport(boolean insert,int ID, int driverID, int trackId, String desAddress, String fromAddress, 
		        	Date date,Date time, int contactPhone, String contactName, String deocNum) {
		this.ID=ID;
		this.driverID = driverID;
		this.truckID = trackId;
		this.desAddress = desAddress;
		this.fromAddress = fromAddress;
		this.depDate = date;
		this.depTime = time;
		this.contactPhone = contactPhone;
		this.contactName = contactName;
		this.deocNum = deocNum;
		if (insert){
			String query ="INSERT INTO Transport (ID,driverID,truckID,desAddress,fromAddress,depDate,depTime,contactPhone,contactName,deocNum) " +
	                "VALUES ("+ID+", "+driverID+", "+trackId+", '"+desAddress+"' ,'"+fromAddress+"' ,'"+date.getDate()+"/"+date.getMonth()+"/"+(date.getYear()+1900)+"' ,'"+time.getHours()+":"+time.getMinutes()+"' ,"+contactPhone+" ,'"+contactName+"' ,"+deocNum+");";
		System.out.println(query);
		DB.executeUpdate(query);
		}
	}
	public static int getNewID(){
		return TransManager.getId();
	}
	public int getID(){
		return ID;
	}
	
	public int getDriverID() {
		return driverID;
	}
	
	public int getTrackId() {
		return truckID;
	}
	
	public String getDesAddress() {
		return desAddress;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	
	public Date getDepDate() {
		return depDate;
	}
	public Date getDepTime() {
		return depTime;
	}
	public int getContactPhone() {
		return contactPhone;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public String getDeocNum() {
		return deocNum;
	}
	public static void showTransMenu(){
		int selection;
		while(true){
			System.out.println("============================");
			System.out.println("--------Transport Menu--------");
			System.out.println("============================");
			System.out.println("1)   Search Transport");
			System.out.println("2)   Add New Transport");
			System.out.println("3)   Edit Transport");
			System.out.println("4)   Delete Transport");
			System.out.println("5)   Show all");
			System.out.println("6)   Return");
			System.out.println("============================");
			System.out.println("--------Transport Menu--------");
			System.out.println("============================");
			selection = TransManager.getInputNumber();
			switch (selection){
			case 1:
				searchTrans();
				break;
			case 2:
				addTrans();
				break;
			case 3:
				if(editTrans())
					System.out.println("Update successfully");
				else System.out.println("Update failed"); 
				break;
			case 4:
				boolean flag=true;
				Transport[] t = new Transport[0];
				System.out.println("Enter transport ID (enter 'quit' to exit tis menu)");
				int ID=0;
				String s="";
				while(t.length!=1){
					while(flag){
						try{
							scan = new Scanner(System.in);
							s =  scan.next();
							if(s.equals("quit")){
								ID=-1;
								flag=false;
							}
							else{
							ID=Integer.parseInt(s);
							if (ID<0)
								throw new NumberFormatException();
							if (ID%1!=0)
								throw new NumberFormatException();
							flag = false;
				
						}
						}
						catch (NumberFormatException e){
								System.out.println("Invalid ID");	
						}		
					}
					if(ID!=-1)
					t=searchTrans(ID+"","ID");
					else break;
				}
				deleteTrans(ID+"");
				System.out.println(ID+" Deleted successfully");
				break;
			case 5:
				prettyPrinting(getTransArray(DB.executeQuery("SELECT * FROM Transport")));
				break;
			case 6:
				return;
			}
		}
	}
	
	public static Transport addTrans(){
	    int driverID=1;
	    int ID=getNewID();
	    System.out.println("Enter driver ID 9 digit");
		boolean flag=true;
		while(flag){
			try{
				scan = new Scanner(System.in);
				driverID =  scan.nextInt();
				if (Driver.searchEmployee("ID",driverID+"").length==0)
					throw new InputMismatchException();
				flag = false;		
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}
		}
	    int truckID=1;
	    System.out.println("Enter truck ID 7 digit");
		flag=true;
		while(flag){
			try{
				scan = new Scanner(System.in);
				truckID =  scan.nextInt();
				if (truckID<1000000 | truckID>9999999)
					throw new InputMismatchException();
				if (Track.searchTruck("TruckID",truckID+"").length==0)
				    throw new InputMismatchException();
				if(Track.searchTruck("TruckID",truckID+"")[0].getLicense()==Driver.searchDriver("ID",driverID+"")[0].get_lisence())
					throw new NumberFormatException();
					if (truckID%1!=0)
					throw new InputMismatchException();
				flag = false;		
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}
			catch(NumberFormatException r){
				System.out.println("Truck and driver License mismatch");
			}
		}
		Date date=new Date(1,1,1);
		System.out.println("Enter departure date (dd/MM/yyyy)");
		flag=true;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		while (flag){
			try{
				scan = new Scanner(System.in);
				String str = scan.next();
				date = sdf.parse(str);
				flag=false;
			}
			catch(ParseException e){
				 System.out.println("Invalid date");
			}
		 }
		Date time=new Date(1);
		System.out.println("Enter departure time (hh:mm)");
		flag=true;
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		while (flag){
			try{
				scan = new Scanner(System.in);
				String str = scan.next();
				time = sdf2.parse(str);
				flag=false;
			}
			catch(ParseException e){
				System.out.println("Invalid hour");
			}
		}
	    int contact=1;
	    System.out.println("Enter contact number 9 digit");
		flag=true;
		while(flag){
			try{
				scan = new Scanner(System.in);
				contact =  scan.nextInt();
				if (contact<100000000 | contact>999999999)
					throw new InputMismatchException();
				if (contact%1!=0)
					throw new InputMismatchException();
				flag = false;		
			}
			catch (InputMismatchException e){
				System.out.println("Invalid phone number");	
			}
		}
		System.out.println("Enter contact name");
		scan = new Scanner(System.in);
		String con = scan.next();
	    String deco="";
		String des="";
		String from="";
		String temp  = TransManager.giveOrderDoc(ID);
		while(temp.length()!=0){
			int endIndex=0;
			while(temp.charAt(endIndex)!='%')
				endIndex++;
			String Helper = temp.substring(0, endIndex);
			temp=temp.substring(endIndex+1);
			endIndex=0;
			while(Helper.length()>0&&Helper.charAt(endIndex)!='@')
				endIndex++;
			deco = deco+Helper.substring(0, endIndex);
			Helper=Helper.substring(endIndex+1);
			endIndex=0;
			while(Helper.length()>0&&Helper.charAt(endIndex)!='@')
				endIndex++;
			from = from+Helper.substring(0, endIndex);
			Helper=Helper.substring(endIndex+1);
			endIndex=0;
			while(Helper.length()>endIndex&&Helper.charAt(endIndex)!='@')
				endIndex++;
			des = des+Helper.substring(0, endIndex);
		}
		return new Transport(true,ID,driverID,truckID,des,from,date ,time,contact,con,deco);
	}
	
	public static Transport[] searchTrans(){
		int choice=1;
		String type="";
		String value="";
		boolean flag=true;
		System.out.println("Chose an option to search by:");
		System.out.println("1. by ID.");
		System.out.println("2. by driver ID.");
		System.out.println("3. by truck ID.");
		System.out.println("4. by departure address.");
		System.out.println("5, by destination address.");
		System.out.println("6. by departure date.");
		System.out.println("7. by departure time.");
		System.out.println("8, by contact number.");
		System.out.println("9. by contact name.");
		System.out.println("10. by document Number.");
		while(flag){
			try{
			Scanner scan = new Scanner(System.in);
			choice =  scan.nextInt();
			if (choice<=0 | choice > 10)
				throw new InputMismatchException();
			if (choice%1!=0)
				throw new InputMismatchException();
			flag = false;		
			}
			catch (InputMismatchException e){
				System.out.println("Invalid choice");	
			}
			}
		flag=true;
		switch(choice){
		case(1):
			type="ID";
		System.out.println("Enter driver ID");
		int ID=0;
		while(flag){
			try{
			scan = new Scanner(System.in);
			ID =  scan.nextInt();
			if (ID<0)
				throw new InputMismatchException();
			if (ID%1!=0)
				throw new InputMismatchException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}		
			}
		value=ID+"";
			break;
		case(2):
			type="driverID";
		System.out.println("Enter driver ID");
		int driverID=0;
		while(flag){
			try{
			scan = new Scanner(System.in);
			driverID =  scan.nextInt();
			if (driverID<100000000 | driverID>999999999)
				throw new InputMismatchException();
			if (driverID%1!=0)
				throw new InputMismatchException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid driverID");	
			}		
			}
		value=driverID+"";
			break;
		case(3):
			type="truckID";
		System.out.println("Enter truck ID");
		int truckID=0;
		while(flag){
			try{
			scan = new Scanner(System.in);
			truckID =  scan.nextInt();
			if (truckID<1000000 | truckID>9999999)
				throw new InputMismatchException();
			if (truckID%1!=0)
				throw new InputMismatchException();
			flag = false;
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}		
			}
		value=truckID+"";
			break;
		case(4):
			type="fromAddress";
		    System.out.println("Enter departure address");
			scan = new Scanner(System.in);
			value = scan.next();	
			break;
		case(5):
			type="desAddress";
	    System.out.println("Enter destination address");
		scan = new Scanner(System.in);
		value = scan.next();
			break;
		case(6):
			Date date=new Date(1,1,1);
		type="depDate";
		 System.out.println("Enter departure date (dd/MM/yyyy)");
		 flag=true;
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 while (flag){
			 try{
			scan = new Scanner(System.in);
			String str = scan.next();
			date = sdf.parse(str);
				flag=false;
	
		 }
			 catch(ParseException e){
				 System.out.println("Invalid date");
			 }
		 }
		 value=date+"";
			break;
		case(7):
			type="depTime";
			Date time=new Date(1);
		 System.out.println("Enter departure time (hh:mm)");
		 flag=true;
		 sdf = new SimpleDateFormat("HH:mm");
		 while (flag){
			 try{
			scan = new Scanner(System.in);
			String str = scan.next();
			date = sdf.parse(str);
				flag=false;
	
		 }
			 catch(ParseException e){
				 System.out.println("Invalid hour");
			 }
		 }
		 value=time+"";
			break;
		case(8):
			type="contactPhone";
		    int contact=1;
		    System.out.println("Enter contact number 9 digit");
			flag=true;
			while(flag){
			try{
			scan = new Scanner(System.in);
			contact =  scan.nextInt();
			if (contact<100000000 | contact>999999999)
				throw new InputMismatchException();
			if (contact%1!=0)
				throw new InputMismatchException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid phone number");	
			}
			}
			value=contact+"";
			break;
		case(9):
			type="contactName";
	    System.out.println("Enter contact name");
		scan = new Scanner(System.in);
		value = scan.next();	
			break;
		case(10):
		    int deco=1;
		    System.out.println("Enter order number");
			flag=true;
			while(flag){
			try{
			scan = new Scanner(System.in);
			deco =  scan.nextInt();
			if (searchTrans(deco+"","deocNum")!=null)
				throw new InputMismatchException();
			if (deco%1!=0)
				throw new InputMismatchException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid order number");	
			}
			}	
			type="deocNum";
			value=deco+"";
			break;
		}
		Transport[] toPrint = searchTrans(value,type);
		prettyPrinting(toPrint);
		return toPrint;
	}
	public static void prettyPrinting(Transport[] trans){
		if (trans.length==0)
			System.out.println("There are no items to show");
		for(int i=0;i<trans.length;i++){
			System.out.println("");
			System.out.println("~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=");
			System.out.println("ID: " +trans[i].ID+".");
			System.out.println("Driver ID: " +trans[i].driverID+".");
			System.out.println("Track ID: " +trans[i].truckID+".");
			System.out.println("Departure address: " +trans[i].fromAddress+".");
			System.out.println("Destination address: " +trans[i].desAddress+".");
			System.out.println("Departure date: " +trans[i].depDate+".");
			System.out.println("Departure Time: " +trans[i].depTime+".");
			System.out.println("Contect phone number: " +trans[i].contactPhone+".");
			System.out.println("Contact name: " +trans[i].contactName+".");
			System.out.println("Document Number: " +trans[i].deocNum+".");
			System.out.println("~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=");
		}
	}
	public static boolean editTrans(){
		int choice=1;
		String type="";
		String value="";
		boolean flag=true;
		Transport[] t = new Transport[0];
		System.out.println("Enter ID (enter 'quit' to exit tis menu)");
		int ID=0;
		String s="";
		while(t.length!=1){
			while(flag){
				try{
					scan = new Scanner(System.in);
					s =  scan.next();
					if(s.equals("quit")){
						ID=-1;
						flag=false;
					}
					else{
					ID=Integer.parseInt(s);
					if (ID<0)
						throw new NumberFormatException();
					if (ID%1!=0)
						throw new NumberFormatException();
					flag = false;
		
				}
				}
				catch (NumberFormatException e){
						System.out.println("Invalid ID");	
				}		
			}
			if(ID!=-1)
			t=searchTrans(ID+"","ID");
			else break;
		}
		flag=true;
		if(ID!=-1){
		System.out.println("Chose a filde to edit:");
		System.out.println("1. by driver ID.");
		System.out.println("2. by truck ID.");
		System.out.println("3. by departure date.");
		System.out.println("4. by departure time.");
		System.out.println("5, by contact number.");
		System.out.println("6. by contact name.");
		while(flag){
			try{
			Scanner scan = new Scanner(System.in);
			choice =  scan.nextInt();
			if (choice<=0 | choice > 9)
				throw new InputMismatchException();
			if (choice%1!=0)
				throw new InputMismatchException();
			flag = false;		
			}
			catch (InputMismatchException e){
				System.out.println("Invalid choice");	
			}
			}
		flag=true;
		switch(choice){
		case(1):
			type="driverID";
		System.out.println("Enter driver ID");
		int driverID=0;
		while(flag){
			try{
			scan = new Scanner(System.in);
			driverID =  scan.nextInt();
			if (driverID<100000000 | driverID>999999999)
				throw new InputMismatchException();
			if (driverID%1!=0)
				throw new InputMismatchException();
			if(Track.searchTruck(t[0].getTrackId()+"","ID")[0].getLicense()==Driver.searchDriver("ID",driverID+"")[0].get_lisence())
				throw new NumberFormatException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}
			catch(NumberFormatException r){
				System.out.println("Truck and driver license mismatch");
			}
			}
		value=driverID+"";
			break;
		case(2):
			type="truckID";
		System.out.println("Enter truck ID");
		int truckID=0;
		while(flag){
			try{
			scan = new Scanner(System.in);
			truckID =  scan.nextInt();
			if (truckID<1000000 | truckID>9999999)
				throw new InputMismatchException();
			if (truckID%1!=0)
				throw new InputMismatchException();
			if(Track.searchTruck(truckID+"","ID")[0].getLicense()==Driver.searchDriver("ID",t[0].getDriverID()+"")[0].get_lisence())
				throw new NumberFormatException();
			flag = false;
			}
			catch (InputMismatchException e){
				System.out.println("Invalid ID");	
			}		
			}
		value=truckID+"";
			break;
		case(3):
			Date date=new Date(1,1,1);
		type="depDate";
		 System.out.println("Enter departure date (dd/MM/yyyy)");
		 flag=true;
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 while (flag){
			 try{
			scan = new Scanner(System.in);
			String str = scan.next();
			date = sdf.parse(str);
				flag=false;
	
		 }
			 catch(ParseException e){
				 System.out.println("Invalid date");
			 }
		 }
		 value=date+"";
			break;
		case(4):
			type="depTime";
			Date time=new Date(1);
			System.out.println("Enter departure time (hh:mm)");
			flag=true;
			sdf = new SimpleDateFormat("HH:mm");
			while (flag){
				try{
					scan = new Scanner(System.in);
					String str = scan.next();
					date = sdf.parse(str);
					flag=false;
				 }
				 catch(ParseException e){
					 System.out.println("Invalid hour");
				 }
		 }
		 value=time+"";
			break;
		case(5):
			type="contactPhone";
		    int contact=1;
		    System.out.println("Enter contact number 9 digit");
			flag=true;
			while(flag){
			try{
			scan = new Scanner(System.in);
			contact =  scan.nextInt();
			if (contact<100000000 | contact>999999999)
				throw new InputMismatchException();
			if (contact%1!=0)
				throw new InputMismatchException();
			flag = false;
			
			}
			catch (InputMismatchException e){
				System.out.println("Invalid phone number");	
			}
			}
			value=contact+"";
			break;
		case(6):
			type="contactName";
	    System.out.println("Enter contact name");
		scan = new Scanner(System.in);
		value = scan.next();	
			break;
		}
		return editTrans(value,type,ID+"");
		}
		return false;
	}
	public static boolean editTrans(String value, String filde,String ID){
		String temp = "UPDATE Transport";
		ResultSet result=DB.executeQuery("SELECT * FROM Transport WHERE ID="+ID);
		if(DB.next(result)){
		switch (filde){
			case("driverID"):
				temp=temp+" SET driverID='"+value+"'";
			break;
			case("truckID"):
				temp=temp+" SET truckID='"+value+"'";
			break;
			case("depDate"):
				temp=temp+" SET depDate='"+value+"'";
			break;
			case("depTime"):
				temp=temp+" SET depTime='"+value+"'";
			break;
			case("contactPhone"):
				temp=temp+" SET contactPhone='"+value+"'";
			break;
			case("contactName"):
				temp=temp+" SET contactName='"+value+"'";
			break;
			default:
				System.out.println("Invalid filed");
			}
		}
		else System.out.println("This ID dosn't exist");
		temp =temp+" WHERE ID="+ID;
		return DB.executeUpdate(temp);
	}
	public static Transport[] searchTrans(String value, String filde){
		String tmp = "SELECT * FROM Transport WHERE ";
		switch (filde){
		case("ID"):
			tmp=tmp+"ID="+value;
		break;
		case("driverID"):
			tmp = tmp+"driverID LIKE '%"+value+"%';";	
		break;
		case("truckID"):
			tmp = tmp+"truckID LIKE '%"+value+"%'";
		break;
		case("desAddress"):
			tmp = tmp+"desAddress LIKE '%"+value+"%'";
		break;
		case("fromAddress"):
			tmp = tmp+"fromAddress LIKE '%"+value+"%'";
		break;
		case("depDate"):
			tmp = tmp+"depDate LIKE '%"+value+"%'";
		break;
		case("depTime"):
			tmp = tmp+"depTime LIKE '%"+value+"%'";
		break;
		case("contactPhone"):
			tmp = tmp+"contactPhone LIKE '%"+value+"%'";
		break;
		case("contactName"):
			tmp = tmp+"contactName LIKE '%"+value+"%'";
		break;
		case("deocNum"):
			tmp = tmp+"deocNum LIKE '%"+value+"%'";
		break;
			
		default:
			return null;
		}
		return getTransArray(DB.executeQuery(tmp));
	}

	public static Transport[] getTransArray(ResultSet result){
		Vector<Transport> vector = new Vector<Transport>();
		
		while(DB.next(result)){
			vector.add ( new Transport(false,DB.getInt(result, "ID"), DB.getInt(result, "driverID"),DB.getInt(result, "truckID"),DB.getString(result, "desAddress"),
					DB.getString(result, "fromAddress"),stringToDate(DB.getString(result, "depDate")),stringToTime(DB.getString(result, "depTime")),DB.getInt(result, "contactPhone"),DB.getString(result, "contactName"),DB.getString(result, "deocNum") ));			
		}
		
		DB.closeResult(result);
		return vector.toArray(new Transport[0]);
	}
	public static Date stringToDate(String s){
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 try{
				 return sdf.parse(s);
	
		 }
			 catch(ParseException e){
			System.out.println("Error: date database error");
		}
		 return null;
	}
	public static Date stringToTime(String s){
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 try{
				 return sdf.parse(s);
	
		 }
			 catch(ParseException e){
			System.out.println("Error: time database error");
		}
		 return null;
	}
	public static boolean deleteTrans(String ID){
		return DB.executeUpdate("DELETE FROM Transport Where ID="+ID);
	}

}