package storageDal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import storageBackend.Stock;


public class linQStock {
private Connection conn;
	
	public linQStock (Connection conn){
		this.conn = conn;
		
	}
	private int findMaxIndex(){
		int maxid = 0;
	    //Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	      

	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT max(INDEX_STOCK) as max_id FROM Stock;" );
	      
	      maxid = rs.getInt("max_id");
	        
	     
	      rs.close();
	      stmt.close();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    return maxid;
	}
	public int AddStock(Stock s){
		
		//Connection c = null;
	    Statement stmt = null;
	    int index = this.findMaxIndex()+1;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     
	      stmt = conn.createStatement();
	      String sql = "INSERT INTO Stock (INDEX_STOCK,PRODUCT_ID,SUPPLIER,AMOUNT,EXPIERED_DAY,IS_REMOVE) " +
	                   "VALUES ("+index+", "+s.getProd_id()+", "+s.getSupplier()+", "+s.getAmount()+
	                   ", '"+s.getExpiered_day()+"', 'false');"; 
	      stmt.executeUpdate(sql);

	      
	      stmt.close();
	      //conn.commit();
	      
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("stock was added successfully");
	    return index; 
	}
public ArrayList<Stock> SelectStockByProductIDSupplierExpiredDay(int product_id, int suplier,String expire_day ){
		
	ArrayList<Stock> ls = new ArrayList<Stock>();
	
   // Connection c = null;
    Statement stmt = null;
    Stock s = null;
    try {
     /* Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
      c.setAutoCommit(false);*/
     
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     
	  Calendar cal = Calendar.getInstance();
     
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM Stock where PRODUCT_ID = "+product_id+" AND "
      		+ "AMOUNT > 0 AND SUPPLIER = "+suplier+" AND EXPIERED_DAY = '"+expire_day+"' AND "
    		  		+ "EXPIERED_DAY < '"+dateFormat.format(cal.getTime()).toString()+"';");
     
      while ( rs.next() ) {
    	  if (rs.getString("IS_REMOVE").equals("false")){
    	
    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),false);
    	  }else{
    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),true);  
    	  }
    	  ls.add(s);
        
      }
      rs.close();
      stmt.close();
      //c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
    return ls;
     
	   
	}
/*public boolean UpdateStockDamage(int product_id, int suplier,String expire_day ){
	
	ArrayList<Stock> ls =SelectStockByProductIDSupplierExpiredDay ( product_id,  suplier, expire_day);
	if (ls ==null)
		return false;
	else
		for(Stock s : ls){
			UpdateAmountProduct(s.getIndex(), -1);
			return true;
		}
	return false;
	
   
	   
	}*/
	
	public int findNumOfExpiredProduct(int prod_id){
		
			
			
		   // Connection c = null;
		    Statement stmt = null;
		    int sum_amount = 0;
		    
		    try {
		     /* Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/
		     

		      stmt = conn.createStatement();
		      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		     
			  Calendar cal = Calendar.getInstance();
		      ResultSet rs = stmt.executeQuery( "SELECT sum(AMOUNT) as sum_amount FROM Stock where PRODUCT_ID = "+prod_id+" AND EXPIERED_DAY < '"+dateFormat.format(cal.getTime()).toString()+"' AND IS_REMOVE = 'false';" );
		      while(rs.next()){
		    	  sum_amount =rs.getInt("sum_amount");
		      }
		      rs.close();
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    
		    return sum_amount;
		}
	
		public void UpdateAmountProduct(int stock_index, int amount){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	    
	/*      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     
		  Calendar cal = Calendar.getInstance();*/
	      stmt = conn.createStatement();
	      String sql = "UPDATE Stock set  AMOUNT = AMOUNT - "
	    		  +amount+" where INDEX_STOCK = "+stock_index+/*" AND EXPIERED_DAY < '"+dateFormat.format(cal.getTime()).toString()+"'*/";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	  
	  }
		public ArrayList<Stock> findStockByProductIDAndNotExpiried(int prodID){
			ArrayList<Stock> ls = new ArrayList<Stock>();
			
		    //Connection c = null;
		    Statement stmt = null;
		    Stock s = null;
		    try {
		      /*Class.forName("org.sqlite.JDBC");
		      conn = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      conn.setAutoCommit(false);*/
		   

		      stmt = conn.createStatement();
		      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			     
			  Calendar cal = Calendar.getInstance();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Stock WHERE PRODUCT_ID = "+prodID+" AND EXPIERED_DAY > '"+dateFormat.format(cal.getTime()).toString()+"' AND IS_REMOVE = 'false';" );
		     
		      while ( rs.next() ) {
		    	
		    	  if (rs.getString("IS_REMOVE").equals("false")){
		    	    	
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),false);
		    	  }else{
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),true);  
		    	  }
		    	  ls.add(s);
		        
		      }
		      rs.close();
		      stmt.close();
		     // c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		   
		    return ls;
		}
		public ArrayList<Stock> findStockByProductID(int prodID){
			ArrayList<Stock> ls = new ArrayList<Stock>();
			
		    //Connection c = null;
		    Statement stmt = null;
		    Stock s = null;
		    try {
		      /*Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/
		   

		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Stock WHERE PRODUCT_ID = "+prodID+";" );
		     
		      while ( rs.next() ) {
		    	
		    	  if (rs.getString("IS_REMOVE").equals("false")){
		    	    	
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),false);
		    	  }else{
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),true);  
		    	  }
		    	  ls.add(s);
		        
		      }
		      rs.close();
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		   
		    return ls;
		}
		public Stock findStockByIndex(int index){
			
			
		    //Connection c = null;
		    Statement stmt = null;
		    Stock s = null;
		    try {
		      /*Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/

		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Stock WHERE INDEX_STOCK = "+index+";" );
		     
		      while ( rs.next() ) {
		    	
		    	  if (rs.getString("IS_REMOVE").equals("false")){
		    	    	
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),false);
		    	  }else{
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),true);  
		    	  }
		    	 
		      }
		      rs.close();
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    return s;
		}
		public ArrayList<Stock> findAllStocks(){
			ArrayList<Stock> ls = new ArrayList<Stock>();
			
		   // Connection c = null;
		    Statement stmt = null;
		    Stock s = null;
		    try {
		      /*Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/

		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM Stock;" );
		     
		      while ( rs.next() ) {
		    	
		    	  if (rs.getString("IS_REMOVE").equals("false")){
		    	    	
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),false);
		    	  }else{
		    		  s = new Stock(rs.getInt("INDEX_STOCK"),rs.getInt("PRODUCT_ID"),rs.getInt("SUPPLIER"),rs.getInt("AMOUNT"),rs.getString("EXPIERED_DAY"),true);  
		    	  }
		    	  ls.add(s);
		        
		      }
		      rs.close();
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    return ls;
		}
		public void UpdateStock(Stock s){
			//Connection c = null;
		    Statement stmt = null;
		    try {
		      /*Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/
		     

		      stmt = conn.createStatement();
		      
		      String sql = "UPDATE Stock set PRODUCT_ID = "+s.getProd_id()+", SUPPLIER = "+s.getSupplier()+",  AMOUNT = "+s.getAmount()+", EXPIERED_DAY = '"+s.getExpiered_day()+"' IS_REMOVE = '"+s.getIsRemove()+"' where INDEX_STOCK = "+s.getIndex()+";";
		      stmt.executeUpdate(sql);
		      //conn.commit();

		      
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Update stock");
		  }
		public void DeleteStock(Stock s){
			//Connection c = null;
		    Statement stmt = null;
		    try {
		     /* Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/
		     

		      stmt = conn.createStatement();
		      
		      String sql = "UPDATE Stock set IS_REMOVE = 'true' where INDEX_STOCK = "+s.getIndex()+";";
		      stmt.executeUpdate(sql);
		      //conn.commit();

		      
		      stmt.close();
		     // c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("stock deleated");
		  }
}
