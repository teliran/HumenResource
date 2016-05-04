package Junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import DB.DB;
import Emp.Constraint;
import Emp.Employee;
import Emp.Shift;
import store.Store;

public class Tests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DB.open();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DB.close();
	}

	@Test
	public void testGetFirstDayOfWeek() {
		Date date =  Store.getFirstDayOfWeek(Store.stringToDate("29/04/2016"),true);
		assertTrue(date.getTime() == Store.stringToDate("24/04/2016").getTime());
		Date dateNextWeek =  Store.getFirstDayOfWeek(Store.stringToDate("29/04/2016"),false);
		assertTrue(dateNextWeek.getTime() == Store.stringToDate("01/05/2016").getTime());
	}
	
	@Test
	public void testInsertEmployee() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Employee[] arr = Employee.searchEmployee("ID" , "-37");
		assertTrue(arr.length ==1);
		assertTrue(arr[0].getName().equals("test"));	
		DB.executeUpdate("DELETE FROM Employees WHERE ID ="+emp.getId());	
	}
	
	@Test
	public void testSearchEmployee() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Employee[] arr = Employee.searchEmployee("ID" , "-37");
		assertTrue(arr.length ==1);
		DB.executeUpdate("DELETE FROM Employees WHERE ID ="+emp.getId());	
		arr = Employee.searchEmployee("ID" , "-37");
		assertTrue(arr.length ==0);		
	}
	
	@Test
	public void testUpdateEmployee() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		emp.setAccountNumber(38);
		emp.setBankNumber(29);
		emp.setName("Rotem");
		Employee[] arr = Employee.searchEmployee("ID" , "-37");
		assertTrue(arr[0].getAccountNumber() == 38);
		assertTrue(arr[0].getBankNumber() == 29);
		assertTrue(arr[0].getName().equals("Rotem"));	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");	
	}

	@Test
	public void testInsertCons() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("14:30"));
		Constraint[] arr = Constraint.searchConstraint("ID", emp.getId()+"");
		assertTrue(arr.length ==1);
		assertTrue(arr[0].getId() == emp.getId());	
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId()+" AND Day='"+con.getDay()+"'");	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
	}
	
	@Test
	public void testSearchCons() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("14:30"));
		Constraint[] arr = Constraint.searchConstraint("ID", emp.getId()+"");
		assertTrue(arr.length ==1);
		assertTrue(arr[0].getId() == emp.getId());	
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId()+" AND Day='"+con.getDay()+"'");	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
		arr = Constraint.searchConstraint("ID", emp.getId()+"");
		assertTrue(arr.length==0);
	}
	
	@Test
	public void testUpdateCons() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("14:30"));
		con.setDay(Store.Week.Sunday);
		con.setStartHour(Store.stringToHour("09:30"));
		Constraint[] arr = Constraint.searchConstraint("ID", emp.getId()+"");
		assertTrue(arr[0].getDay().toString().equals(Store.Week.Sunday.toString()));
		assertTrue(arr[0].getStartHour().getTime() == Store.stringToHour("09:30").getTime());	
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId()+" AND Day='"+con.getDay()+"'");	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
	}
	
	@Test
	public void testIsAvailable() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("12:30"));
		assertFalse(Constraint.isAvailable(emp, Store.stringToDate("29/04/2016"), Shift.ShiftPart.morning));
		assertTrue(Constraint.isAvailable(emp, Store.stringToDate("29/04/2016"), Shift.ShiftPart.evening));
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId()+" AND Day='"+con.getDay()+"'");	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
	}
	
	@Test
	public void testGetNumberOfConstraint() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		assertTrue(Constraint.getNumberOfConstraint(emp)==0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("12:30"));
		assertTrue(Constraint.getNumberOfConstraint(emp)==1);
		con = new Constraint(true, emp.getId(), Store.Week.Sunday, Store.stringToHour("08:30"), Store.stringToHour("12:30"));
		assertTrue(Constraint.getNumberOfConstraint(emp)==2);
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId());	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
	}
	
	@Test
	public void testShiftsInCon() {
		Employee emp = new Employee(true, -37, "test", Employee.Position.cashier, 0, 0, Store.stringToDate("1/1/2016"), 0);
		Constraint con = new Constraint(true, emp.getId(), Store.Week.Friday, Store.stringToHour("08:30"), Store.stringToHour("12:30"));
		assertTrue(Constraint.shiftsInCon(con)==1);
		con.setEndHour(Store.stringToHour("21:30"));
		assertTrue(Constraint.shiftsInCon(con)==2);
		con.setStartHour(Store.stringToHour("16:00"));
		assertTrue(Constraint.shiftsInCon(con)==1);
		DB.executeUpdate("DELETE FROM Constraints WHERE ID="+con.getId());	
		DB.executeUpdate("DELETE FROM Employees WHERE ID =-37");
	}
}
