package transport;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;

import DB.DB;
import transport.TransManager.License;

public class Track {
	 public static final String yes ="YES", no = "NO";
	 private int truckID;
	 private License license;
	 private String color;
	 private int NetoWeight;
	 private int CarryingWeight;
	 boolean available;
	 
	public Track(boolean insert, int trackID, String color, int netoWeight, int carryingWeight) {
		this.truckID = trackID;
		this.color = color;
		NetoWeight = netoWeight;
		this.license = assignLicense();
		CarryingWeight = carryingWeight;
		available = true;
		if (insert){
			String query ="INSERT INTO Trucks (TruckID, Color, Truck_Weight, Carrying_Weight, Availability) " +
	                "VALUES ("+trackID+", '"+color+"', "+netoWeight+", "+carryingWeight+", '"+yes+"');";
			System.out.println(query);
			DB.executeUpdate(query);
		}
	}
	
	public static void showTrucksMenu(){
		int selection;
		while(true){
			System.out.println("============================");
			System.out.println("--------Trucks Menu--------");
			System.out.println("============================");
			System.out.println("1)   Search Truck");
			System.out.println("2)   Add New Truck");
			System.out.println("3)   Return");
			System.out.println("============================");
			System.out.println("--------Trucks Menu--------");
			System.out.println("============================");
			selection = TransManager.getInputNumber();
			switch (selection){
			case 1:
				showTruck(searchTruck());
				break;
			case 2:
				showTruck(addTruck());
				break;
			case 3:
				return;
			}
		}
	}
	
	private static Track addTruck() {
		System.out.println("===============================");
		System.out.println("-----------Add Truck----------");
		System.out.println("===============================");
		System.out.println("Enter Truck License Plate (7 digits):");
		int id = TransManager.getInputNumber();
		while (id>9999999 && id<1000000){
			System.out.println("Enter Truck License Plate (7 digits):");
			id = TransManager.getInputNumber();
		}
		//Now checking the unique ID	
		while(searchTruck("TruckID",id+"").length != 0){ // result array isn't empty
			System.out.println("The entered Plate already exists");
			id = TransManager.getInputNumber();
		}
		System.out.println("Enter the Truck's Color:");
		Scanner sc = new Scanner(System.in);
		String color = sc.nextLine();
		System.out.println("Enter Truck's Weight:");
		int weight = TransManager.getInputNumber();
		System.out.println("Enter Truck's carrying Weight:");
		int carry = TransManager.getInputNumber();
		return new Track(true, id, color, weight, carry);	
	}
	
	@Override
	public String toString() {
		return "Track [truckID=" + truckID + ", license=" + license + ", color=" + color + ", NetoWeight=" + NetoWeight
				+ ", CarryingWeight=" + CarryingWeight + "]";
	}

	public static void showTruck(Track truck){
		if (truck == null)
			return;
		int selection;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("===================================");
			System.out.println("-----------Truck: "+truck.getTrackID()+"----------");
			System.out.println("===================================");
			System.out.println("1)   Edit Color (Current color is: "+truck.getColor()+")");
			System.out.println("2)   Edit Weight Limit (Current color is: "+truck.getCarryingWeight()+")");
			System.out.println("3)   Edit Availability (Current Avilability is: "+truck.getAvailable()+")");
			System.out.println("4)   Delete Truck");
			System.out.println("5)   Back");
			System.out.println("===================================");
			System.out.println("-----------Truck: "+truck.getTrackID()+"----------");
			System.out.println("===================================");
			selection = TransManager.getInputNumber();
			switch(selection){
				case 1: //edit color
					System.out.println("Please enter the new Color:");
					truck.setColor(sc.nextLine());
					break;
				case 2: // edit carry weight
					System.out.println("Please select the new Weight Limit:");
					int limit = TransManager.getInputNumber();
					while (limit > truck.getNetoWeight()*1.2){
						System.out.println("The truck can not support this weight \n"
								+ "Max Weight is: "+truck.getNetoWeight()*1.2);
						limit = TransManager.getInputNumber();
					}
					truck.setWeightLimit(limit);
					break;
				case 3:
					System.out.println("Please Enter Availbillity (YES / NO):");
					String resp = sc.nextLine();
					while(!(resp.equals(yes) || resp.equals(no))){
						System.out.println("please enter YES or NO, or 'q' to return");
						resp = sc.nextLine();
						if (resp.equals("q"))
							break;
					}
					if (resp.equals(yes))
						truck.setAvailabilty(true);
					else
						truck.setAvailabilty(false);
					break;	
				case 4: // delete truck
					DB.executeUpdate("DELETE FROM Trucks WHERE TruckID ="+truck.getTrackID());
					return;
				case 5:
					return;
			}
		}	
	}
	
	public static Track searchTruck(){
		int primSelect; //Primary selection
		int secSelect; //Secondary selection
		Track [] trk;
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("===============================");
			System.out.println("--------Search Truck--------");
			System.out.println("===============================");
			System.out.println("1)   Search By Truck ID (License Plate)");
			System.out.println("2)   Search By Truck Weight");
			System.out.println("3)   Search By Truck Carrying Weight");
			System.out.println("4)   Search By Color");
			System.out.println("5)   Search By Availability");
			System.out.println("6)   Show all trucks");
			System.out.println("7)   Exit");
			System.out.println("===============================");
			System.out.println("--------Search Truck--------");
			System.out.println("===============================");
			primSelect = TransManager.getInputNumber();
			switch(primSelect){
			case 1:
				System.out.println("Please Enter Truck ID (7 digits):");
				int id = TransManager.getInputNumber();
				while (id>9999999 && id<1000000){
					System.out.println("Enter Truck License Plate (7 digits):");
					id = TransManager.getInputNumber();
				}
				trk=searchTruck("TruckID",id+"");
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];
			
			case 2:
				System.out.println("Please Enter Trucks Weight:");
				trk=searchTruck("Truck_Weight",sc.nextLine());
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];	
			
			case 3:
				System.out.println("Please Enter Trucks Carrying Weight:");
				trk=searchTruck("Carrying_Weight",sc.nextLine());
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];
			
			case 4:
				System.out.println("Please Enter Truck Color:");
				trk=searchTruck("Color",sc.nextLine());
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];
				
			case 5:
				System.out.println("Please Enter Availbillity (YES / NO):");
				String resp = sc.nextLine();
				while(!(resp.equals(yes) || resp.equals(no))){
					System.out.println("please enter YES or NO, or 'q' to return");
					resp = sc.nextLine();
					if (resp.equals("q"))
						return null;
				}
				trk=searchTruck("Availability",resp);
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];
			case 6:
				trk=searchTruck("All","");
				if (trk.length==0){
					System.out.println("No Results found");
					break;
				}
				secSelect = TransManager.selectFromChoises(trk);
				return trk[secSelect];
			case 7:
				return null;
			}
		}
			
	}
	
	public static Track[] searchTruck(String field,String value){
		ResultSet result = null;
		Vector<Track> trucks = new Vector<Track>();
		switch(field){
		case "TruckID":
			result = DB.executeQuery("SELECT * FROM Trucks WHERE TruckID LIKE '%"+value+"%';");
			break;
		case "Truck_Weight": 
			result = DB.executeQuery("SELECT * FROM Trucks WHERE Truck_Weight LIKE '%"+value+"%'");
			break;
		case "Carrying_Weight":
			result = DB.executeQuery("SELECT * FROM Trucks WHERE Carrying_Weight LIKE '%"+value+"%'");
			break;
		case "Color": 
			result = DB.executeQuery("SELECT * FROM Trucks WHERE Color LIKE '%"+value+"%'");
			break;
		case "Availability":
			result = DB.executeQuery("SELECT * FROM Trucks WHERE Availability LIKE '%"+value+"%'");
			break;
		case "All":
			result = DB.executeQuery("SELECT * FROM Trucks");
			break;
		}
		while(DB.next(result)){
			Track tempT = new Track(false, DB.getInt(result, "TruckID"), 
					DB.getString(result, "Color"), DB.getInt(result, "Truck_Weight"), 
					DB.getInt(result, "Carrying_Weight"));
			trucks.add(tempT);
		}
		DB.closeResult(result);
		return trucks.toArray(new Track[0]);	
	}
	
	
	private void setWeightLimit(int limit) {
		CarryingWeight = limit;
		DB.executeUpdate("UPDATE Trucks set Carrying_Wieght = '"+limit+"' WHERE TruckID = "+truckID);
	}

	private void setColor(String newColor) {
		this.color = newColor;
		DB.executeUpdate("UPDATE Trucks set Color = '"+color+"' WHERE TruckID = "+truckID);
	}

	public int getTrackID() {
		return truckID;
	}
	
	public License getLicense() {
		return license;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getNetoWeight() {
		return NetoWeight;
	}
	
	public int getCarryingWeight() {
		return CarryingWeight;
	}
	public boolean checkIfDriverSuitable(Driver d){
		if (d.get_lisence()==License.C)
			return true;
		else if (d.get_lisence()==License.B)
			return (license == License.B || license == License.A);
		else
			return (license == License.A);
	}
	
	private License assignLicense(){
		if (NetoWeight<=4500)
			return License.A;
		else if (NetoWeight>4500 && NetoWeight<=9000)
			return License.B;
		else
			return License.C;
	}
	public String getAvailable(){
		if (available)
			return yes;
		else return no;
	}
	public boolean setAvailabilty(boolean flag){
		available = flag;
		String query="";
		if (flag)
			query = "UPDATE Trucks set Availability = '"+yes+"' WHERE TruckID = "+truckID;
		else
			query = "UPDATE Trucks set Availability = '"+no+"' WHERE TruckID = "+truckID;
			
		return DB.executeUpdate(query);
	}
}
