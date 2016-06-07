
package DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Backend.Agreement;
import Backend.Agreement.daysOfWeek;
import Backend.Agreement.deliveryType;
import Backend.Agreement.orderType;
import Backend.AgreementEntity;
import Backend.Entity;
import Backend.Product;
import Backend.SupIdSearchable;

public class Agreement_DAL {


	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	protected Agreement_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}
	
	protected String AddAgreement(Backend.Agreement agreement) throws AccessDeniedException
	{
		String query="insert into agreement values(?,?,?,?,?)";//sugar
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, agreement.getSupNum());
			pst.setDate(2,new java.sql.Date(agreement.getSignDate().getTime()));
			pst.setDate(3,new java.sql.Date(agreement.getValidDate().getTime()));
			pst.setString(4,agreement.getOrderType().toString());
			pst.setString(5,agreement.getDeliveryType().toString());
			}
		catch (SQLException e)//if the query isn't good. 
		{
			throw new AccessDeniedException("Error: to add new supplier.");
		}
			
			try {
				pst.executeUpdate();
				pst.close();
				}
			catch (SQLException e) //try to add object with the same key
			{
				return "The agreement is already exist.";
			}
			for(int i=0;i<agreement.getDeliveryDays().size();i++)
				AddDeliveryDays(agreement.getSupNum(),(Date)agreement.getSignDate(),agreement.getDeliveryDays().get(i).toString());
			for(int j=0;j<agreement.getProductList().size();j++)
			{
				entity_dal.AddProductByAgreement(agreement.getSupNum(), agreement.getSignDate(), agreement.getProductList().get(j));
			}
			return null;
	}

	protected String EditAgreement(String supId, Date signDate,Backend.Agreement newAgreement) throws AccessDeniedException
	{
		String query="SELECT * FROM agreement WHERE supplier_id=? AND sign_date = ?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setDate(2, signDate);
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE agreement SET expiration_date = ?, order_type = ?, transportation_type = ? WHERE supplier_id = ? AND sign_date = ? ");
				 pst.setDate(1, (Date) newAgreement.getValidDate());
				 pst.setString(2, newAgreement.getOrderType().toString());
				 pst.setString(3, newAgreement.getDeliveryType().toString());
				 pst.setString(4, supId);
				 pst.setDate(5, signDate);
				 pst.execute();
				 pst.close();
				 rs.close();
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				return "there is no agreement as you describe";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update supplier.");
		}
	}
	
	private void AddDeliveryDays(String id,Date signDate,String deliveryDay) throws AccessDeniedException
	{	
		String query="insert into agreement_delivery_days values(?,?,?)";//sugar
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			pst.setDate(2, (java.sql.Date) signDate);
			pst.setString(3, deliveryDay);
			}
			catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to add new Delivery day.");
			}
			try {
				pst.execute();
				pst.close();
			} catch (SQLException e) {throw new AccessDeniedException("Error: to add new Delivery day.");}
	}
	
	private List<Product> GetAgreementProductList(String id,Date signDate) throws AccessDeniedException
	{
		List<AgreementEntity>productList=entity_dal.GetAllProductByAgreement(id, signDate);
		List<Product>proList=new LinkedList<Product>();
		for(int i=0;i<entity_dal.GetAllProductByAgreement(id, signDate).size();i++)
			proList.add((Product) productList.get(i));
		return proList;
	}
	
	List<SupIdSearchable> GetagreementBySupplier(String id) throws AccessDeniedException
	{
		String query="SELECT * FROM agreement WHERE supplier_id=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			List<SupIdSearchable> agreementList=new LinkedList<SupIdSearchable>();
			while(rs.next())
			{
				Agreement agreement=new Agreement(rs.getString(1),rs.getDate(2),rs.getDate(3),deliveryType.valueOf(rs.getString(5)),orderType.valueOf(rs.getString(4)),getDeliveryDays(id,rs.getDate(2)),GetAgreementProductList(rs.getString(1),rs.getDate(2)));
				agreementList.add(agreement);
			}
			rs.close();
			pst.close();
			if(agreementList.isEmpty())
				new LinkedList<SupIdSearchable>();
			return agreementList;
			
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Get Contact By Supplier id.");
		}
	}
	
	private List<daysOfWeek> getDeliveryDays(String id,Date signDate) throws AccessDeniedException
	{	
		String query="SELECT * FROM agreement_delivery_days WHERE supplier_id=? AND sign_date=? ";
		PreparedStatement pst=null;
		List<daysOfWeek> daysOfWeekList=new LinkedList<daysOfWeek>();
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			pst.setDate(2, signDate);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				daysOfWeekList.add(daysOfWeek.valueOf(rs.getString(3)));
			}catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to add new Delivery day.");
			}
		return daysOfWeekList;
	}

	protected List<Entity> GetAllAgreement() throws AccessDeniedException
	{
		String query="SELECT * FROM agreement ";
		PreparedStatement pst=null;
		List<Entity> agreementList=new LinkedList<Entity>();
		try {
				pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				while(rs.next())
				{
					agreementList.add(new Agreement(rs.getString(1), rs.getDate(2), rs.getDate(3),deliveryType.valueOf(rs.getString(5)), orderType.valueOf(rs.getString(4)), getDeliveryDays(rs.getString(1), rs.getDate(2)),GetAgreementProductList(rs.getString(1),rs.getDate(2)) ));
				}
				pst.close();
				return agreementList;
			}catch(SQLException e){throw new AccessDeniedException("Error: to get all agreements.");
}
	}

	protected void DeleteAgreement(Agreement agreement) throws AccessDeniedException
	{
		List<Product>productList=agreement.getProductList();
		for(Product pro: productList )
		{
			entity_dal.DeleteProductByAgreement(agreement.getSupNum(), agreement.getSignDate(), pro);
		}
		String query="DELETE FROM agreement_delivery_days WHERE supplier_id = ? AND sign_date=? ";
		try {
			PreparedStatement pst=conn.prepareStatement(query);
			pst.setString(1,agreement.getSupNum());
			pst.setDate(2,(Date) agreement.getSignDate());
			pst.execute();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to delete deliveryDays for agreement");
		}
		query="DELETE FROM agreement WHERE supplier_id=? AND sign_date=? ";
		try {
			PreparedStatement pst=conn.prepareStatement(query);
			pst.setString(1, agreement.getSupNum());
			pst.setDate(2, (Date) agreement.getSignDate());
			pst.execute();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Delete agreement");
		}
	}

	protected Entity getLastAgreementBySupplier(String supId) throws AccessDeniedException
	{
		String query="SELECT Max(expiration_date) FROM agreement WHERE supplier_id = ?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		Date signDate=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, supId);
			rs=pst.executeQuery();
			signDate=rs.getDate(1);
			pst.close();
			rs.close();
		} catch (SQLException e1) {
			return null;
		}
		if(signDate==null)
			return null;
		query="SELECT * FROM agreement WHERE supplier_id=? AND expiration_date = ? ";
		PreparedStatement pst1;
		try {
			pst1 = conn.prepareStatement(query);
			pst1.setString(1, supId);
			pst1.setDate(2, signDate);
			ResultSet rs1=pst1.executeQuery();
			Entity agreement=new Agreement(rs1.getString(1),rs1.getDate(2),rs1.getDate(3),deliveryType.valueOf(rs1.getString(5)),orderType.valueOf(rs1.getString(4)),getDeliveryDays(supId,rs1.getDate(2)),GetAgreementProductList(rs1.getString(1),rs1.getDate(2)));
			rs1.close();
			pst1.close();
			return agreement;
			
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to to get last agreement by supplier.");
		}
		
	}
}