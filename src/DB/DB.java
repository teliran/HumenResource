package DB;

import java.sql.*;

public final class DB
{
	private static Connection connection;
	private static Statement db;
	
	private DB(){
		connection=null;
		db=null;
	}
	
	public static ResultSet executeQuery(String query){
		try {
			return db.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Problem with reading from database");
			e.printStackTrace();
			System.exit(0);
			return null;
		}	
	}
	
	public static boolean executeUpdate(String query){
		try {
			db.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println("Problem with Edit database");
			e.printStackTrace();
			return false;
		}	
	}
	
	public static boolean next(ResultSet set){
		try {
			return set.next();
		} catch (SQLException e) {
			System.out.println("Problem with reading from database");
			e.printStackTrace();
			System.exit(0);
			return false;
		}
	}
	
	public static int getInt(ResultSet set,String id){
		try {
			return set.getInt(id);
		} catch (SQLException e) {
			System.out.println("Problem with reading from database");
			e.printStackTrace();
			System.exit(0);
			return 0;
		}
	}
	
	public static String getString(ResultSet set,String id){
		try {
			return set.getString(id);
		} catch (SQLException e) {
			System.out.println("Problem with reading from database");
			e.printStackTrace();
			System.exit(0);
			return "";
		}
	}
	
	public static int getSize(ResultSet set){
		try {
			return set.getFetchSize();
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public static void closeResult(ResultSet set){
		try {
			set.close();
		} catch (SQLException e) {
			System.out.println("Problem with reading from database");
			e.printStackTrace();
			System.exit(0);
		}		
	}
	
	public static void open(){
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:main.db");
			connection.setAutoCommit(true);
			db = connection.createStatement();
		} catch (Exception e)  {
			System.out.println("Error on Loading Database");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
	    }
	}
	
	public static void close(){
		try {
			db.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error on Clossing Database");
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

}
