package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Backend.Contact;
import Backend.SupIdSearchable;

public class Contact_DAL {
	
	@SuppressWarnings("unused")
	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	protected Contact_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}

	protected String AddContact(String supId,Backend.Contact contact) throws AccessDeniedException
	{
		String query="insert into supplier_contacts(supplier_id, first_name, last_name, phone_number) values(?,?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, contact.getFirstName());
			pst.setString(3, contact.getLastName());
			pst.setInt(4, contact.getPhoneNum());
			}
			catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to add new Contact.");
			}
			try {
				pst.executeUpdate();
				pst.close();
				return null;
				} catch (SQLException e) {return "contact is already exist";}
	}
	
	protected String EditContact(String supId,Contact old, Contact newContact) throws AccessDeniedException
	{
		String query="SELECT * FROM supplier_contacts WHERE supplier_id=? AND first_name = ? AND last_name=? AND phone_number=?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, old.getFirstName());
			pst.setString(3, old.getLastName());
			pst.setInt(4, old.getPhoneNum());
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE supplier_contacts SET first_name = ?, last_name = ?, phone_number=? WHERE supplier_id=? AND first_name = ? AND last_name=? AND phone_number=?");
				 pst = conn.prepareStatement(query);
			   	 pst.setString(1, newContact.getFirstName());
				 pst.setString(2, newContact.getLastName());
				 pst.setInt(3, newContact.getPhoneNum());
				 pst.setString(4, supId);
				 pst.setString(5, old.getFirstName());
				 pst.setString(6, old.getLastName());
				 pst.setInt(7, old.getPhoneNum());
			     pst.execute();
				 pst.close();
				 rs.close();
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				return "there is no contact as you describe";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update contact.");
		}
	}
	
	protected List<SupIdSearchable> GetContactBySupplier(String id) throws AccessDeniedException
	{
		String query="SELECT * FROM supplier_contacts WHERE supplier_id=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			List<SupIdSearchable> contactList=new LinkedList<SupIdSearchable>();
			while(rs.next())
			{
				Contact contact=new Contact(rs.getString(2),rs.getString(3),rs.getInt(4));
				contactList.add(contact);
			}
			rs.close();
			pst.close();
			if(contactList.isEmpty())
				new LinkedList<SupIdSearchable>();
			return contactList;
			
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Get Contact By Supplier id.");
		}
		
	}
	
	protected void DeleteContact(String supId, Backend.Contact contact) throws AccessDeniedException
	{
		String query="DELETE FROM supplier_contacts WHERE supplier_id=? AND first_name=? AND last_name=? AND phone_number=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, contact.getFirstName());
			pst.setString(3, contact.getLastName());
			pst.setInt(4, contact.getPhoneNum());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Delete contact.");
		}
	}
	
	
}
