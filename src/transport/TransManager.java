package transport;
import java.util.Date;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Vector;

import BL.Entity_BL;
import BL.IBL;
import BL.Order_BL;
import Backend.Order;
import DB.DB;
import Emp.Employee;
import Emp.Employee.Position;
import store.Store;
/**
 * 
 * @author Hen&Avner
 * Reponsible for transportation management
 *
 */
public class TransManager {
	public static final int north = 0, south = 1;
	private static int _docID = getLastId("DocID", "Doc_History");
	private static int _ID = getLastId("ID", "Transport");
	private static String[][] _destArr = {{"SuperLee NorthWest", "SuperLee NorthEast"}, {"SuperLee SouthWest", "SuperLee SouthEast"}};
	private static int _area;
	//private static Order _ord = null;
	private static HashMap<Integer, Order> tranOrdersMap = new HashMap<>();// maps a transportID to the Order
	private static Vector<Order> ordVecN = new Vector<>(); //saves given orders when program is active
	private static Vector<Order> ordVecS = new Vector<>();
	private static boolean hasOrder;
	public static int getLastId(String id, String table){
		int ret = -1;
		String query = "SELECT MAX("+id+") FROM "+table;
		ResultSet result = DB.executeQuery(query);
		if (DB.next(result)){
			ret = DB.getInt(result, "MAX("+id+")");
			DB.closeResult(result);
			return ret;
		}
		DB.closeResult(result);
		return ret;
	}
	
	public static int getDocId(){
		_docID++;
		return _docID;
	}
	public static int getId(){
		_ID++;
		return _ID;
	}

	public static String[][] get_destArr() {
		return _destArr;
	}
	
	public static enum License{
		A, B, C
	}

	
	public static int selectFromChoises(Object[] arr){
		if (arr.length==0){
			return -1;
		}
		int selection;
		System.out.println("========= Enter your choise between range(1 - " +arr.length+") ==========");
		for (int i=0; i<arr.length; i++){
			System.out.println((i+1)+")  "+ arr[i].toString());
		}
		while (true){
			selection = getInputNumber();
			if(selection<=0 || selection>arr.length)
				System.out.println("========= Enter your choise between range(1 - " +arr.length+") ==========");
			else
				return selection-1;
		}
	}
	//--------------------------------------------------------------------------------------
	//-------------------------------Added in 17.06.16---------------------------------------	
	//--------------------------------------------------------------------------------------
	public static Order getOrderFromMap(int transId){
		Order ret = null;
		if (tranOrdersMap.containsKey(transId))
			ret = tranOrdersMap.get(transId);
		return ret;
	}
	public static void giveNewOrder(Order or, String area) {
		if (area.equals("North")|| area.equals("north")){
			Order ord = new Order(or);
			ordVecN.addElement(ord);
			hasOrder = true;
		}
		else if (area.equals("South")|| area.equals("south")){
			Order ord = new Order(or);
			ordVecS.addElement(ord);
			hasOrder = true;
		}
		else System.out.println("Areas are: North or South, order did not received!");
	}
	
	public static String  giveOrderDoc(int transId){
		if (!hasOrder){
			return null;
		}
		Order ord = ordVecN.remove(0);
		tranOrdersMap.put(transId, ord);
		String ret ="";
		int zone=-1;
		if(!ordVecN.isEmpty())
		{
			zone=0;
		}
		else
		if(!ordVecS.isEmpty())
		{
			zone=1;
		}
		if (zone!=-1)
		{
		String tempSource = ord.getSupNum();
		int destNum = (int)(Math.random()*_destArr[zone].length)+1;
		int[]memo = new int[_destArr[zone].length];
		for (int i=0; i<destNum; i++){
			int p = (int) (Math.random()*_destArr[zone].length);
			while (memo[p]==1)
				p = (int) (Math.random()*_destArr[zone].length);
			String tempDest = _destArr[zone][p];
			memo[p]=1;
			if (ord!=null){
				OrderDocument doc = new OrderDocument(ord, tempDest);
				doc.addDoc(transId);
				ret = ret+doc.get_docId()+"@"+tempSource+"@"+tempDest+"%";
			}
		}
		}
		if (ordVecN.isEmpty()&ordVecS.isEmpty())
			hasOrder = false;
		return ret;
	}
//--------------------------------------------------------------------------------------	
//--------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------	
//--------------------------------------------------------------------------------------
	public static int getInputNumber(){
		Scanner sc = new Scanner(System.in);
		while(true){
			String input = sc.nextLine();
			boolean isNum = false;
			try{
				Integer.parseInt(input);
				isNum = true;
			}catch(NumberFormatException e){
				isNum = false;
			}
			if (isNum)
				return Integer.parseInt(input);
			else{
				System.out.println("Invalid input, please retry");
			}
			
		}
	}
	public static Date stringToDate(String date) {
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			System.out.println("Wrong format please try again (dd/MM/yyyy):");
			Scanner sc = new Scanner(System.in);
			return stringToDate(sc.nextLine());
		}
	}
	

	private static  void showOrderDocsHistory() throws SQLException {
		System.out.println("============================");
		System.out.println("--------Docs History--------");
		System.out.println("============================");
		int i = 1;
		ResultSet result = DB.executeQuery("SELECT * FROM Doc_History");
		ResultSetMetaData rsmd = result.getMetaData();
		int colNum = rsmd.getColumnCount();
		while(DB.next(result)){
			System.out.print(i+") ");
			for (int j=1; j<=colNum; j++){
				if (j>1)
					System.out.print(", ");
				String val = result.getString(j);
				System.out.print(val + " " + rsmd.getColumnName(j));
			}
			System.out.println();
			i++;
		}
		DB.closeResult(result);
		System.out.println("============================");
		System.out.println("--------Docs History--------");
		System.out.println("============================");
		System.out.println();
		System.out.println("press any key to continue");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		System.out.println();
		
	}
	public static void showMainMenu() {
		checkTransDaly();
			int selection;
			while(true){
				System.out.println("============================");
				System.out.println("--------Welcome to Transportatin Module--------");
				System.out.println("============================");
				System.out.println("1)   Transport Menu");
				System.out.println("2)   Drivers Menu");
				System.out.println("3)   Trucks Menu");
				System.out.println("4)   View Order documents history");
				System.out.println("5)   Return");
				System.out.println("============================");
				System.out.println("------------------------------------------------");
				System.out.println("============================");
				selection = TransManager.getInputNumber();
				switch (selection){
				case 1:
					Transport.showTransMenu();
					break;
				case 2:
					Driver.showDriversMenu();;
					break;
				case 3:
					Track.showTrucksMenu();
					break;
				case 4:
					try {
						showOrderDocsHistory();
					} catch (SQLException e) {
						System.out.println("Problem with loading from DB, docs-History");
						e.printStackTrace();
					}
					break;
				case 5:
					return;	
				}
			}
		}
	private static void checkTransDaly() {
		Transport[] temp= Transport.searchTrans("pending","Status");
		for(int i=0;i<temp.length;i++)
		{
			int dec = Integer.parseInt(temp[0].getDeocNum());
			long DAY_IN_MS = 1000 * 60 * 60 * 24;
			IBL a = new Entity_BL(null);
			if(a.getDateByOrderId(dec).before(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS))))
			{
				Employee.requestEmployee(Store.currentDate,Position.driver,2);
			}
				
		}
	}
}
