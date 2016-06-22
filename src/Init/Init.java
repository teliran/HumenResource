package Init;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import BL.Entity_BL;
import BL.IBL;
import DAL.Entity_Dal;
import DAL.IDAL;
import GUI.openScreen;
import storagePl.manageMain;


public class Init
{
	private static Connection conn;
	private IDAL adal;
	private IBL abl;
	private openScreen pl;
	private manageMain m;
	
	public Init()  
	{
		   try
		   {
			   Class.forName("org.sqlite.JDBC");
		 	   conn = DriverManager.getConnection("jdbc:sqlite:Li-super.sqlite");
		 	   String query="PRAGMA foreign_keys = ON;";
		 	   PreparedStatement pst;
		 	   pst = conn.prepareStatement(query);
		 	   pst.execute();
		 	   adal = new Entity_Dal(conn);
		 	   abl = new Entity_BL(adal);
		 	   pl = new openScreen(abl);
		 	   m = new manageMain(conn,abl);
		   }
		   catch(Exception e)
		   {
		 	  System.out.println("Error: connect to server failed");
		 	  System.exit(0);
		   } 
	}
	
	public static Connection getCon(){
		return conn;
	}
	
	public openScreen getSupplierPl()
	{
		return pl;
	}
	public manageMain getStorePl()
	{
		return m;
	}
	public void closeConnection()
	{
		abl.CloseConnection();
	}
}