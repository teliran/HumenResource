package DAL;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Backend.Contact;
import Backend.Manufacturer;
import Backend.SupIdSearchable;
import Backend.Supplier;
import Backend.Supplier.paymentMethod;

public class JunitDal {

	Connection conn;
	@Before
	public void setUp() throws Exception {
		   Class.forName("org.sqlite.JDBC");
      	   conn=DriverManager.getConnection("jdbc:sqlite:Li-super.sqlite");
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}

	@Test
	public void GetContactBySupplier() throws AccessDeniedException, SQLException
	{
		Contact_DAL cont=new Contact_DAL(conn, null);
		Contact contact=new Contact("test", "test", 000000);
		String query="INSERT INTO supplier_contacts VALUES(?,?,?,?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		pst.setString(2, "test");
		pst.setString(3, "test");
		pst.setInt(4, 000000);
		pst.executeUpdate();
		List<SupIdSearchable> contactList=cont.GetContactBySupplier("test");
		for(SupIdSearchable contactOther: contactList)
		{
			assertEquals(contact.getFirstName(),((Contact)contactOther).getFirstName());
			assertEquals(contact.getLastName(),((Contact)contactOther).getLastName());
			assertEquals(contact.getPhoneNum(),((Contact)contactOther).getPhoneNum());
			
		}
		query="DELETE FROM supplier_contacts WHERE supplier_id=? AND first_name = ? AND last_name=? AND phone_number=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		pst.setString(2, "test");
		pst.setString(3, "test");
		pst.setInt(4, 000000);
		pst.executeUpdate();
	}
	@Test
	public void AddManu() throws AccessDeniedException, SQLException {
		Manufacturer_DAL manuD=new Manufacturer_DAL(conn, null);
		Manufacturer man=new Manufacturer("123","test");
		manuD.AddManufacturerBySupplierId("test", man);
		String query="SELECT id FROM manufacturer WHERE id=?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		ResultSet rs=pst.executeQuery();
		assertTrue(rs.next());
		query="DELETE FROM manufacturer WHERE id=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		pst.executeUpdate();
	}
	
	@Test
	public void GetManuByIdTest() throws AccessDeniedException, SQLException {
		Manufacturer_DAL manuD=new Manufacturer_DAL(conn, null);
		Manufacturer man=new Manufacturer("123","test");
		String query="INSERT INTO manufacturer VALUES(?,?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		pst.setString(2, "test");
		pst.executeUpdate();
		Manufacturer manOther=(Manufacturer)manuD.SearchManufacturerById("123");
		assertEquals(man.getID(), manOther.getID());
		assertEquals(man.getName(), manOther.getName());
		query="DELETE FROM manufacturer WHERE id=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		pst.executeUpdate();
	}
	
	
	@Test
	public void AddSupplier() throws AccessDeniedException, SQLException
	{
		Supplier_DAL sup=new Supplier_DAL(conn, null);
		Supplier supplier=new Supplier("test", "test", 0, paymentMethod.valueOf("CASH"), "test", new LinkedList<Contact>(), new LinkedList<Manufacturer>(),"test");
		sup.AddSupplier(supplier);
		String query="SELECT id FROM supplier WHERE id=?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		ResultSet rs=pst.executeQuery();
		assertEquals("test", rs.getString(1));
		query="DELETE FROM supplier WHERE id=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		pst.executeUpdate();
	}

	@Test
	public void AddContact() throws AccessDeniedException, SQLException
	{
		Contact_DAL cont=new Contact_DAL(conn, null);
		Contact contact=new Contact("test", "test", 000000);
		cont.AddContact("test", contact);
		String query="SELECT * FROM supplier_contacts WHERE supplier_id=? AND first_name = ? AND last_name=? AND phone_number=?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		pst.setString(2, "test");
		pst.setString(3, "test");
		pst.setInt(4, 000000);
		ResultSet rs=pst.executeQuery();
		assertEquals("test", rs.getString(1));
		assertEquals("test", rs.getString(2));
		assertEquals("test", rs.getString(3));
		assertEquals(000000, rs.getInt(4));
		query="DELETE FROM supplier_contacts WHERE supplier_id=? AND first_name = ? AND last_name=? AND phone_number=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "test");
		pst.setString(2, "test");
		pst.setString(3, "test");
		pst.setInt(4, 000000);
		pst.executeUpdate();
	}
	
	@Test
	public void EditManu() throws AccessDeniedException, SQLException
	{
		Manufacturer_DAL manuD=new Manufacturer_DAL(conn, null);
		Manufacturer man=new Manufacturer("123","TEST");
		Manufacturer manNew=new Manufacturer("123","TESTO");
		String query="INSERT INTO manufacturer VALUES(?,?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		pst.setString(2, "TEST");
		pst.executeUpdate();
		manuD.EditManufacturerBySupplier("123", man, manNew);
	    query="SELECT * FROM manufacturer WHERE id=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		ResultSet rs=pst.executeQuery();
		assertEquals("TESTO", rs.getString(2));
		query="DELETE FROM manufacturer WHERE id=?";
		pst = conn.prepareStatement(query);
		pst.setString(1, "123");
		pst.executeUpdate();
	}

}
