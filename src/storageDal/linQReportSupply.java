package storageDal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import storageBackend.ReportSupply;


public class linQReportSupply {
private Connection conn;
	
	public linQReportSupply (Connection conn){
		this.conn = conn;
		
	}
public void AddReportSupply(ReportSupply rs){
		
		//Connection c = null;
	    Statement stmt = null;
	    try {
	     /* Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	      
	      stmt = conn.createStatement();
	      
	      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     
		  Calendar cal = Calendar.getInstance();
	      String sql = "INSERT INTO ReportSupply (PRODUCT_ID,PRODUCT_NAME,SUPPLIER,EXPIERED_DAY,AMOUNT,DATE_OF_EXPORT_FROM_THE_WAREHOUSE) " +
	                   "VALUES ("+rs.getProd_id()+", '"+rs.getProd_name()+"', "+rs.getSupplier()+", '"+rs.getExpiered_day()+"', "+rs.getAmount()+
	                   ", '"+dateFormat.format(cal.getTime()).toString()+"');"; 
	      stmt.executeUpdate(sql);

	      

	      stmt.close();
	      //conn.commit();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	}
}
