package storageDal;

import java.sql.*;
import java.util.*;

import DAL.Entity_Dal;
import storageBackend.Product;


public class linQProduct {
	private Connection conn;
	
	public linQProduct (Connection conn){
		this.conn = conn;
		
	}
	public boolean isProductExist(int prodID, String name){
		
			boolean isFound = false;
		    //Connection c = null;
		    Statement stmt = null;
		    try {
		     /* Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
		      c.setAutoCommit(false);*/
		     

		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT ID FROM Product WHERE ID = "+prodID+" OR NAME = '"+name+"';" );
		      while ( rs.next() ) {
		    	  isFound = true;
		        
		      }
		      rs.close();
		      stmt.close();
		      //c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		   
		    return isFound;
	}
	public Product findProductByID(int prodID){
		
		
	   // Connection c = null;
	    Statement stmt = null;
	    Product p = null;
	    try {
	     /* Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	    	
	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Product WHERE ID = "+prodID+";" );
	     
	      while ( rs.next() ) {
	    	  if(rs.getString("IS_REMOVE").equals("true")){
	    	  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
	    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),true);
	    	  }
	    	  else{
	    		  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
		    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),false);
	    	  }
	        
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    return p;
	}
public Product findProductByName(String prodName){
		
		
	   // Connection c = null;
	    Statement stmt = null;
	    Product p = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Product WHERE NAME = '"+prodName+"';" );
	     
	      while ( rs.next() ) {
	    	  if(rs.getString("IS_REMOVE").equals("true")){
	    	  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
	    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),true);
	    	  }
	    	  else{
	    		  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
		    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),false);
	    	  }
	        
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.out.println("not such product");
	      return null;
	    }
	   
	    return p;
	}
	public void UpdateProduct(Product p){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      String sql = "UPDATE Product set NAME = '"+p.getName()+"', MANUFACTURE_ID = '"+p.getManufactureID()+"', MANUFACTURE_PROD_ID = '"+p.getManufactureProdID()+"', FIRST_DEPARTMENT = "+p.getDepartment_id1()+
	    		  ",  SECOND_DEPARTMENT=" +p.getDepartment_id2()+
	    		  ", THIRD_DEPARTMENT = "+p.getDepartment_id3()+", MINIMUM_AMOUNT = "+p.getMinimum_amount()+", TOTAL_AMOUNT = "
	    		  +p.getTotal_amount()+", TOTAL_DAMAGED = "+p.getTotal_damaged()+", NEXT_DATE_ORDER = '" +p.getNextDateOrder()+"', DAYS = "+p.getDays()+", QUNTITY = "+p.getQuntity()+
	    		  ",  IS_REMOVE = '"+p.getIsRemove()+"' where ID = "+p.getProd_id()+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();
	     
	      
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	  }
	public void UpdateTotalAmountProduct(int amount, int prod_id){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	      

	      stmt = conn.createStatement();
	      String sql = "UPDATE Product set  TOTAL_AMOUNT = TOTAL_AMOUNT + "
	    		  +amount+" where ID = "+prod_id+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the product update succsessfuly");
	  }
	public void UpdateTotalDamagedProduct(int damaged, int prod_id){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	      

	      stmt = conn.createStatement();
	      String sql = "UPDATE Product set  TOTAL_DAMAGED = TOTAL_DAMAGED + "
	    		  +damaged+" where ID = "+prod_id+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the total damaged product update succsessfuly");
	  }
	public void UpdateNextDateOrderProduct(int prod_id, String nextDateOreder){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	      

	      stmt = conn.createStatement();
	      String sql = "UPDATE Product set  NEXT_DATE_ORDER = '"
	    		  +nextDateOreder+"' where ID = "+prod_id+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the total damaged product update succsessfuly");
	  }
	public void DeleteProduct(int prod_id){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      String sql = "UPDATE Product set  IS_REMOVE = 'true' where ID = "+prod_id+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the product deleted succsecfully");
	  }
	public void AddProd(Product p){
		
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	    	
	    	
	      stmt = conn.createStatement();
	      String sql = "INSERT INTO Product (ID,NAME,MANUFACTURE_ID,MANUFACTURE_PROD_ID,FIRST_DEPARTMENT,SECOND_DEPARTMENT,THIRD_DEPARTMENT,"
	      		+ "MINIMUM_AMOUNT,TOTAL_AMOUNT,TOTAL_DAMAGED,NEXT_DATE_ORDER,DAYS,QUNTITY,IS_REMOVE) " +
	                   "VALUES ("+p.getProd_id()+", '"+p.getName()+"', '"+p.getManufactureID()+"', '"+p.getManufactureProdID()+"', "+p.getDepartment_id1()+
	                   ", "+p.getDepartment_id2()+", "+p.getDepartment_id3()
	                   +", "+p.getMinimum_amount()+", "+p.getTotal_amount()+", "+p.getTotal_damaged()+
	                   ", '"+p.getNextDateOrder()+"', "+p.getDays()+", "+p.getQuntity()+", '"+p.getIsRemove()+"');"; 
	      stmt.executeUpdate(sql);

	      

	      stmt.close();
	      //conn.commit();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the product added succsesfully");
	}
public ArrayList<Product> findAllProducts(){
	ArrayList<Product> lp = new ArrayList<Product>();
		
		
	   // Connection c = null;
	    Statement stmt = null;
	    Product p = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Product;" );
	     
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
		    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),true);
		    	  }
		    	  else{
		    		  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
			    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),false);
		    	  }
	    	  lp.add(p);
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    return lp;
	}
public ArrayList<Product> findProductsByDepartments(int department1, int department2, int department3){
	ArrayList<Product> lp = new ArrayList<Product>();

	   // Connection c = null;
	    Statement stmt = null;
	    Product p = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      ResultSet rs;
	      if(department1 >=0 && department2 >=0 && department3 >= 0){
	      rs= stmt.executeQuery( "SELECT * FROM Product where FIRST_DEPARTMENT = "+department1+" AND SECOND_DEPARTMENT = "+department2
	    		  +" AND THIRD_DEPARTMENT = "+department3+";" );
	      }
	      else{
	    	  if(department1 >=0 && department2 >=0)
	    	      rs= stmt.executeQuery( "SELECT * FROM Product where FIRST_DEPARTMENT = "+department1+" AND SECOND_DEPARTMENT = "+department2
	    	    		  +";" );
	    	  else{
	    		  if(department1 >=0)
	    		      rs= stmt.executeQuery( "SELECT * FROM Product where FIRST_DEPARTMENT = "+department1
	    		    		  +";" );
	    		  else{
	    			  rs= stmt.executeQuery( "SELECT * FROM Product;" );
	    		  }
	    		 
	    				  }
	    			  }
	      			
	    

	    		  
	     
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
		    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),true);
		    	  }
		    	  else{
		    		  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
			    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),false);
		    	  }
	    	  lp.add(p);
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return lp;
	}
public ArrayList<Product> findProductsByOneDepartment(int department1){
	ArrayList<Product> lp = new ArrayList<Product>();

	   // Connection c = null;
	    Statement stmt = null;
	    Product p = null;
	    try {
	     /* Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      ResultSet rs;
	      
	      rs= stmt.executeQuery( "SELECT * FROM Product where FIRST_DEPARTMENT = "+department1+" OR SECOND_DEPARTMENT = "+department1
	    		  +" OR THIRD_DEPARTMENT = "+department1+";" );
	      
	     
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
		    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),true);
		    	  }
		    	  else{
		    		  p = new Product(rs.getInt("ID"),rs.getString("NAME"),rs.getString("MANUFACTURE_ID"),rs.getString("MANUFACTURE_PROD_ID"),rs.getInt("FIRST_DEPARTMENT"),rs.getInt("SECOND_DEPARTMENT"),
			    			  rs.getInt("THIRD_DEPARTMENT"),rs.getInt("MINIMUM_AMOUNT"),rs.getInt("TOTAL_AMOUNT"),rs.getInt("TOTAL_DAMAGED"),rs.getString("NEXT_DATE_ORDER"),rs.getInt("DAYS"),rs.getInt("QUNTITY"),false);
		    	  }
	    	  lp.add(p);
	      }
	      rs.close();
	      stmt.close();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return lp;
	}

}
