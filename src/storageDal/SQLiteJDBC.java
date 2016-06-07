package storageDal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
public class SQLiteJDBC {


	
	public static void CreateProductTable(){
		
		    Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      System.out.println("Opened database successfully");
		      /*stmt = c.createStatement();
		      String sql1 = "DROP TABLE Product;";
		      stmt.executeUpdate(sql1);
		      stmt.close();*/
		      stmt = c.createStatement();
		      String sql = "CREATE TABLE Product " +
		                   "(ID INT 			PRIMARY KEY     NOT NULL," +
		                   " NAME           	TEXT    NOT NULL UNIQUE, " + 
		                   " MANUFACTURE_ID    	TEXT    NOT NULL, " + 
		                   " MANUFACTURE_PROD_ID    	TEXT    NOT NULL, " + 
		                   " FIRST_DEPARTMENT 	INT     NOT NULL, " + 
		                   
		                   " SECOND_DEPARTMENT 	INT     NOT NULL, " + 
		                  
		                   " THIRD_DEPARTMENT 	INT     NOT NULL, " + 
		                   
		                   " MINIMUM_AMOUNT 	INT     NOT NULL, " +
		                   " TOTAL_AMOUNT 		INT     NOT NULL, " +
		                   " TOTAL_DAMAGED 		INT     NOT NULL, " +
		                   " NEXT_DATE_ORDER	TEXT    NOT NULL, " +
		                   " DAYS       		INT     NOT NULL, " +
		                   " QUNTITY      		INT     NOT NULL, " +
		                   " IS_REMOVE		    TEXT    NOT NULL, " +
		                   " FOREIGN KEY(FIRST_DEPARTMENT) REFERENCES Department(ID),"+
		                   " FOREIGN KEY(SECOND_DEPARTMENT) REFERENCES Department(ID),"+
		                   " FOREIGN KEY(THIRD_DEPARTMENT) REFERENCES Department(ID));";
		                  
		      stmt.executeUpdate(sql);
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Table created successfully");
		
	}
	public static void CreateStockTable(){
		
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Stock " +
	                   "(INDEX_STOCK 				INT PRIMARY KEY     NOT NULL," +
	                   " PRODUCT_ID 				INT     NOT NULL, " +	                   
	                   " SUPPLIER           		INT    NOT NULL, " + 	                   
	                   " AMOUNT 					INT     NOT NULL, " + 
	                   " EXPIERED_DAY           	TEXT    NOT NULL, "+
	                   " IS_REMOVE                  TEXT    NOT NULL, "+
	                   " FOREIGN KEY(PRODUCT_ID) REFERENCES Product(ID));";
	                  
	                  
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	
}
public static void CreateReportSupply(){
		
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE ReportSupply " +
	                   "(PRODUCT_ID 				INT     NOT NULL," +
	                   " PRODUCT_NAME 				TEXT    NOT NULL, " +	                   
	                   " SUPPLIER           		INT     NOT NULL, " +
	                   " EXPIERED_DAY           	TEXT    NOT NULL, "+
	                   " AMOUNT 					INT     NOT NULL, " + 
	                   " DATE_OF_EXPORT_FROM_THE_WAREHOUSE           	TEXT    NOT NULL, "+
	                   "PRIMARY KEY (PRODUCT_ID, SUPPLIER, EXPIERED_DAY, DATE_OF_EXPORT_FROM_THE_WAREHOUSE) " +
	                   " FOREIGN KEY(PRODUCT_ID) REFERENCES Product(ID));";
	                  
	                  
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	
}
	
	public static void CreateDepartmentTable(){
		
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE Department " +
	                   "(ID 					INT PRIMARY KEY     NOT NULL," +
	                   " NAME           		TEXT    NOT NULL UNIQUE, " + 	                   
	                   " GENERAL_DEPARTMENT_ID 	INT, " + 
	                   " IS_REMOVE		    	TEXT    NOT NULL, "+
	                   " FOREIGN KEY(GENERAL_DEPARTMENT_ID) REFERENCES Department(ID));";	                  
	                  
	                  
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	
	}
	
}
