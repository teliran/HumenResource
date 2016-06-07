package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Manufacturer;
import Backend.SupIdSearchable;

public class Manufacturer_DAL {


	@SuppressWarnings("unused")
	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	public Manufacturer_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}
	
	protected String AddManufacturerBySupplierId(String id, Backend.Manufacturer manufacturer) throws AccessDeniedException
	{
		String query="insert into manufacturer values(?,?)";//sugar
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, manufacturer.getID());
			pst.setString(2, manufacturer.getName());
			}
			catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to add new manufacturer.");
			}
			try {
				pst.executeUpdate();
				pst.close();
			} catch (SQLException e) 
			{return "manufacturer is already exist";}
			
			String query2="insert into manufacturer_supplier values(?,?)";//sugar
			PreparedStatement pst2=null;
			try {
				pst2=conn.prepareStatement(query2);
				pst2.setString(1, manufacturer.getID());
				pst2.setString(2, id);
				}
				catch (SQLException e) 
				{
					throw new AccessDeniedException("Error: to add connection between manufacture to supplier, probably supplier doesn't exist.");
				}
				try {
					pst2.executeUpdate();
					pst2.close();
				} catch (SQLException e) {return "manufacure has already connection with supplier ";}
		return null;
	}
	
	protected String EditManufacturerBySupplier(String supId,Manufacturer old, Manufacturer newManu) throws AccessDeniedException
	{
		String query="SELECT * FROM manufacturer WHERE id=?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, old.getID());
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE manufacturer SET name = ? WHERE id = ? ");
				 pst.setString(1, newManu.getName());
				 pst.setString(2, old.getID());
				 pst.execute();
				 pst.close();
				 rs.close();
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				return "there is no manufacturer as you describe";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update manufacturer.");
		}
	}
	
	protected List<SupIdSearchable> GetManufacturerBySupplier(String id) throws AccessDeniedException
	{
		String query="SELECT DISTINCT manufacturer.id,manufacturer.name FROM manufacturer JOIN manufacturer_supplier WHERE manufacturer_supplier.supplier_id=? AND manufacturer.id=manufacturer_supplier.manufacturer_id";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			List<SupIdSearchable> manufacturerList=new LinkedList<SupIdSearchable>();
			while(rs.next())
			{
				Manufacturer manu=new Manufacturer(rs.getString(1),rs.getString(2));
				manufacturerList.add(manu);
			}
			rs.close();
			pst.close();
			if(manufacturerList.isEmpty())
				return new LinkedList<SupIdSearchable>();
			return manufacturerList;
			
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Get Manufacturer By Supplier id.");
		}	
	}
	
	protected void DeleteManufacturerBySupplierId(String supId, String Manuid) throws AccessDeniedException
	{
		
		String query="SELECT * FROM manufacturer_supplier WHERE manufacturer_id=? AND supplier_id=?";
		PreparedStatement pst1,pst2;
		ResultSet rs=null;
			try {
				pst1 = conn.prepareStatement(query);
				pst1.setString(1, Manuid);
				pst1.setString(2, supId);
				rs=pst1.executeQuery();
				if(rs.next())
				{
					query="DELETE FROM manufacturer_supplier WHERE manufacturer_id=? AND supplier_id=?";
					pst2 = conn.prepareStatement(query);
					pst2.setString(1,Manuid);
					pst2.setString(2, supId);
					pst2.executeUpdate();
					pst2.close();
				}
				} catch (SQLException e) {
					throw new AccessDeniedException("Error: to delete manufacturer connection probably there are products that still connected to this manudaturer");
				}
			query="SELECT COUNT(*) FROM manufacturer_supplier WHERE manufacturer_id= ? ";
			int x=0;
			try {
				pst1=conn.prepareStatement(query);
				pst1.setString(1, Manuid);
				rs=pst1.executeQuery();
				x=rs.getInt(1);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				try {
					if(x==0)
					{
						query="DELETE FROM manufacturer WHERE id=?";
						pst1 = conn.prepareStatement(query);
						pst1.setString(1,Manuid);
						pst1.execute();
					}
				} catch (NumberFormatException | SQLException e) {
					throw new AccessDeniedException("Error: to delete manufacturer entity");
				}
			
	}
	
	protected IdSearchable SearchManufacturerById(String id) throws AccessDeniedException
	{
		String query="SELECT * FROM manufacturer WHERE id=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
				return new Manufacturer(id,rs.getString(2));
			return null;
		}catch(SQLException e){
			throw new AccessDeniedException("Error: to search manufacturer by id"); 
		}
	}

	protected List<Entity> GetAllManufacturer() throws AccessDeniedException
	{
		String query="SELECT * FROM manufacturer";
		PreparedStatement pst;
		List<Entity> manuList=new LinkedList<Entity>();
		try {
			pst = conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				manuList.add((Entity) new Manufacturer(rs.getString(1),rs.getString(2)));
			return manuList;
		}catch(SQLException e){
			throw new AccessDeniedException("Error: to search manufacturer by id"); 
		}
	}
	
	protected boolean ManufacturerSupplier(int manId, String supID) throws AccessDeniedException
	{
		String query="SELECT * FROM manufacturer_supplier WHERE manufacturer_id=? AND supplier_id=? ";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, manId);
			pst.setString(2, supID);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
				return true;
			return false;
			}catch(SQLException e){throw new AccessDeniedException("Error: to find if manufaturer is working with this supplier");}
	}
}
