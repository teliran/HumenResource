package storagePl;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import GUI.check;
import storageBl.Entity_BLs;
import storageDal.Entity_DAL;
import storageDal.SQLiteJDBC;

public class manageMain {
	Connection conn;
	BL.IBL b;
	public manageMain(Connection conn, BL.IBL b)
	{
		this.b=b;
		this.conn=conn;
	}
	/*public static String add_n_days(String date, int n){
		String sourceDate = date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate;
		try {
			myDate = format.parse(sourceDate);
			Calendar cal = Calendar.getInstance();
	        cal.setTime(myDate);
	        cal.add(Calendar.DATE, n); //minus number would decrement the days
	        return format.format(cal.getTime()).toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}*/
	public void run() {
		Entity_DAL dal = new Entity_DAL(conn);
		Entity_BLs bl = new Entity_BLs(dal,b);
		PLmanagment m = new PLmanagment(bl);
		/*System.out.println(add_n_days("2016-12-3", 31));
		if (add_n_days("2016-12-3", 31).equals("2017-01-03")){
			System.out.println("aaaaaaaaaaaaaaaaaa");
		}*/
		//SQLiteJDBC.CreateDepartmentTable();
		//SQLiteJDBC.CreateProductTable();
		

		//SQLiteJDBC.CreateStockTable();
		//SQLiteJDBC.CreateReportSupply();
		System.out.println("welcome, press youre choise");
		boolean bol = true;
		while(bol)
		{
			m.printOptions();
			bol = m.getChoise();
		}
	}

}
