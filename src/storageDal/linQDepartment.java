package storageDal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import DAL.Entity_Dal;
import storageBackend.Department;


public class linQDepartment {
	private Connection conn;
	
	public linQDepartment  (Connection conn){
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
	      ResultSet rs = stmt.executeQuery( "SELECT max(ID) as max_id FROM Department;" );
	      
	      maxid = rs.getInt("max_id");
	        
	     
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    return maxid;
	}
	public boolean isDepartmentExist(String departmentName){
		
		boolean isFound = false;
	    //Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT ID FROM Department WHERE NAME = '"+departmentName+"';" );
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
	public void UpdateDepartment(Department d){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     

	      stmt = conn.createStatement();
	      
	      String sql = "UPDATE Department set NAME = '"+d.getName()+"', GENERAL_DEPARTMENT_ID = "+d.getGeneral_department_id()+",  IS_REMOVE = '"+d.getIsRemove()+"' where ID = "+d.getDepartment_id()+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();

	      
	      stmt.close();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the department updated succseccfuly");
	  }
	public void DeleteDepartment(int department_id){
		//Connection c = null;
	    Statement stmt = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	    

	      stmt = conn.createStatement();
	     
	      String sql = "UPDATE Department set IS_REMOVE = 'true' where ID = "+department_id+";";
	      stmt.executeUpdate(sql);
	      //conn.commit();
	      
	      stmt.close();
	     // c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("the department deleted succseccfully");
	  }
public void AddDepartment(Department d){
	
	//Connection c = null;
    Statement stmt = null;
    try {
     /* Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
      c.setAutoCommit(false);*/
     

      stmt = conn.createStatement();
      if (d.getGeneral_department_id()!=0){
      String sql = "INSERT INTO Department (ID,NAME,GENERAL_DEPARTMENT_ID,IS_REMOVE) " +
                   "VALUES ("+(this.findMaxIndex()+1)+", '"+d.getName()+"', "+d.getGeneral_department_id()+", '"+d.getIsRemove()+"');"; 
      stmt.executeUpdate(sql);
      }
      else{
    	  String sql = "INSERT INTO Department (ID,NAME,GENERAL_DEPARTMENT_ID,IS_REMOVE) " +
                  "VALUES ("+(this.findMaxIndex()+1)+", '"+d.getName()+"', NULL, '"+d.getIsRemove()+"');"; 
    	  stmt.executeUpdate(sql); 
      }

      

      stmt.close();
      //conn.commit();
      //c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("the department added succsesfully:)");
}
public ArrayList<Department> findDepartmentsByGeneralDepartmentID(int general_department_id){
	ArrayList<Department> ld = new ArrayList<Department>();
		
		
	    //Connection c = null;
	    Statement stmt = null;
	    Department d = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     
	      ResultSet rs;
	      
	      if (general_department_id!=0){
	      stmt = conn.createStatement();
	      rs = stmt.executeQuery( "SELECT * FROM Department where GENERAL_DEPARTMENT_ID = "+general_department_id+" AND IS_REMOVE = 'false';" );
	      }
	      else{
	    	  stmt = conn.createStatement();
		      rs = stmt.executeQuery( "SELECT * FROM Department where GENERAL_DEPARTMENT_ID is NULL AND IS_REMOVE = 'false';" );
	      }
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),true);
		    	  }
		    	  else{
		    		  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),false);
		    	  }
	    	  ld.add(d);
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return ld;
	}
public Department findDepartmentByDepartmentID(int department_id){
	
		
		
	    //Connection c = null;
	    Statement stmt = null;
	    Department d = null;
	    try {
	     /* Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     
	      ResultSet rs;
	      
	     
	      stmt = conn.createStatement();
	      rs = stmt.executeQuery( "SELECT * FROM Department where ID = "+department_id+";" );
	     
	     
	      while ( rs.next() ) {
	    	  if(rs.getString("IS_REMOVE").equals("true")){
	    	  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),true);
	    	  }
	    	  else{
	    		  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),false);
	    	  }
	    	
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return d;
	}
public Department findDepartmentByName(String department_name){
	
		
		
	    //Connection c = null;
	    Statement stmt = null;
	    Department d = null;
	    try {
	      /*Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	     
	      ResultSet rs;
	      
	     
	      stmt = conn.createStatement();
	      rs = stmt.executeQuery( "SELECT * FROM Department where NAME = '"+department_name+"';" );
	     
	     
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),true);
		    	  }
		    	  else{
		    		  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),false);
		    	  }
	    	  
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    return d;
	}
public ArrayList<Department> findAllDepartment(){
	ArrayList<Department> ld = new ArrayList<Department>();
		
		
	   // Connection c = null;
	    Statement stmt = null;
	    Department d = null;
	    try {
	    /*  Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:Assignment1.db");
	      c.setAutoCommit(false);*/
	   
	      ResultSet rs;
	      
	      
	      stmt = conn.createStatement();
	      rs = stmt.executeQuery( "SELECT * FROM Department;" );
	     
	     
	      while ( rs.next() ) {
	    	
	    	  if(rs.getString("IS_REMOVE").equals("true")){
		    	  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),true);
		    	  }
		    	  else{
		    		  d = new Department(rs.getInt("ID"),rs.getString("NAME"),rs.getInt("GENERAL_DEPARTMENT_ID"),false);
		    	  }
	    	  ld.add(d);
	      }
	      rs.close();
	      stmt.close();
	      //c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	  
	    return ld;
	}
}
