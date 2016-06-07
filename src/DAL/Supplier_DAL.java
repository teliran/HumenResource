package DAL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import Backend.Contact;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Manufacturer;
import Backend.SupIdSearchable;
import Backend.Supplier;
import Backend.Supplier.paymentMethod;

public class Supplier_DAL {

	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	protected Supplier_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}
	
	/////////////
	///Methods///
	/////////////
	
	protected String AddSupplier(Supplier sup) throws AccessDeniedException
	{
		String query="insert into supplier(id, name, 'bank account', 'payment type', department, address) values(?,?,?,?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, sup.getSupNum());
			pst.setString(2, sup.getSupName());
			pst.setInt(3, sup.getBAN());
			pst.setString(4, sup.getPaymentMethod().toString());
			pst.setString(5, sup.getArea());
			pst.setString(6,sup.getAddress() );
			}
		catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to add new supplier.");
		}
			
			try {
				pst.executeUpdate();
				pst.close();
				for(int i=0;i<sup.getContactsList().size();i++)
					entity_dal.AddContactBySupplierId(sup.getSupName(), sup.getContactsList().get(i));
				return null;
				
				}
			catch (SQLException e) 
			{
				return "supplier is already exist";
			}
	} 
	
	protected String EditSupplier(String id, Supplier sup) throws AccessDeniedException
	{
		String query="SELECT * FROM supplier WHERE id=?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, sup.getSupNum());
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE supplier SET name = ?, 'bank account' = ?, 'payment type' = ?, department = ?, address = ? WHERE id = ? ");
				 pst.setString(1, sup.getSupName());
				 pst.setInt(2, sup.getBAN());
				 pst.setString(3, sup.getPaymentMethod().toString());
				 pst.setString(4, sup.getArea());
				 pst.setString(5, sup.getAddress());
				 pst.setString(6, sup.getSupNum());
				 pst.execute();
				 pst.close();
				 rs.close();
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				return "supplier id is wrong";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update supplier.");
		}
	}
	
	protected IdSearchable SearchSupplierById(String id) throws AccessDeniedException 
	{
		String query="SELECT * FROM supplier WHERE id=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				List<SupIdSearchable> contact=entity_dal.GetContactBySupplier(id);
				List<SupIdSearchable> manu=entity_dal.GetManufacturerBySupplier(id);
				Supplier sup=new Supplier(rs.getString(1),rs.getString(2),rs.getInt(3),paymentMethod.valueOf(rs.getString(4)),rs.getString(5),(List<Contact>)(List<?>) contact,((List<Manufacturer>)(List<?>)manu),rs.getString(6));
				rs.close();
				pst.close();
				return sup;
			}
			else
			{
				rs.close();
				pst.close();
				return null;
			}
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to search supplier by id.");
		}
	}
	
	protected List<Entity> GetAllSupplier() throws AccessDeniedException
	{
		List<Entity>list = new LinkedList<Entity>();
		String query="SELECT * FROM supplier";
		try {
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				List<Manufacturer> manuList=new LinkedList<Manufacturer>();
				for(SupIdSearchable manu:entity_dal.GetManufacturerBySupplier(rs.getString(1)))
					manuList.add((Manufacturer)manu);
				List<Contact> contList=new LinkedList<Contact>();
				for(SupIdSearchable cont:entity_dal.GetContactBySupplier(rs.getString(1)))
					contList.add((Contact)cont);
				list.add(new Supplier(rs.getString(1),rs.getString(2),rs.getInt(3),paymentMethod.valueOf(rs.getString(4)),rs.getString(5),contList,manuList,rs.getString(6)));
			}
			pst.close();
			rs.close();
			return list;
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to get all supplier.");
		}
	}
	
	protected void DeleteSupplier(Supplier supplier) throws AccessDeniedException
	{
		List<Manufacturer> manList=supplier.getManList();
		List<Contact> contactsList=supplier.getContactsList();
		for(Manufacturer manu: manList)
			entity_dal.DeleteManufacturerBySupplierId(supplier.getSupNum(), manu.getID());
		for(Contact cont: contactsList)
			entity_dal.DeleteContactBySupplierId(supplier.getSupNum(), cont);
		String query="DELETE * FROM supplier WHERE id=? ";
		try {
				PreparedStatement pst=conn.prepareStatement(query);
				pst.setString(1, supplier.getSupName());
				pst.executeQuery();
			}catch(SQLException e){throw new AccessDeniedException("Error: to Delete supplier.");}
	}
}