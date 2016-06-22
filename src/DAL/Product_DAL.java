package DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import Backend.Discount;
import Backend.Product;
import Backend.AgreementEntity;

public class Product_DAL {


	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	protected Product_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}

	protected String AddProductByAgreement(String supId, Date date, Product pro) throws AccessDeniedException
	{//add to product manu also
		AddManufacturerProduct(pro.getManuNum(), pro.getManID(),pro.getWeight());
		String query="insert into product_supply(supplier_id,manu_num,catalog_number,sign_date,name,department,price,manufacturer_id) values(?,?,?,?,?,?,?,?)";
		PreparedStatement pst=null;	
			try {
				pst=conn.prepareStatement(query);
				pst.setString(1,supId);
				pst.setString(2, pro.getManuNum());
				pst.setString(3,pro.getCatNum());
				pst.setDate(4,date);
				pst.setString(5,pro.getName());
				pst.setString(6,pro.getArea());
				pst.setDouble(7, pro.getPrice());
				pst.setString(8, pro.getManID());
				pst.executeUpdate();
				pst.close();	
				}
			catch (SQLException e) 
			{
				return "The product is already exist at the system.";
			}
			List<Discount> dis=pro.getDisList();
			for(int i=0;i<dis.size();i++)
			{
				AddDiscountQuantity(supId,date,pro.getCatNum(),dis.get(i).getQun(),dis.get(i).getDisc());
			}
			return null;
	}
	
	private void AddManufacturerProduct(String proNum,String proManu,double weight) throws AccessDeniedException
	{
		String query="insert into product_manufacturer(Product_Num,Manufcturer_Num,Weight) values(?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, proNum);
			pst.setString(2, proManu);
			pst.setDouble(3, weight);
			}
		catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to AddManufacturerProduct.");
		}
			
			try {
				pst.executeUpdate();
				pst.close();
			}
			catch (SQLException e) 
			{
				throw new AccessDeniedException("Error:ManufacturerProduct is already exist.");
			}
	}
	
	protected void AddDiscountQuantity(String supId, Date signDate, String catNum,double quantity,double discount) throws AccessDeniedException
	{
		String query="INSERT OR REPLACE INTO quantities_list(supplier_id,sign_date,catalog_number,quantity,discount) VALUES(?,?,?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1,supId);
			pst.setDate(2,signDate);
			pst.setString(3,catNum);
			pst.setDouble(4,quantity);
			pst.setDouble(5,discount);
			pst.executeUpdate();
			pst.close();
			}
		catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to add new product quantity discount list.");
		}
	}
	
	protected String EditProductByAgreement(String supId, Date date, Backend.Product old, Backend.Product newPro) throws AccessDeniedException
	{
		String query="SELECT * FROM product_supply WHERE supplier_id=? AND catalog_number = ? AND sign_date=? ";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, old.getCatNum());
			pst.setDate(3, date);
			rs=pst.executeQuery();
			if(rs.next())
			{
				 query="UPDATE product_supply SET name = ?, manu_num = ?, department = ?, price=? WHERE supplier_id=? AND catalog_number =? AND sign_date=?";
				 pst = conn.prepareStatement(query);
				 pst.setString(1, newPro.getName());
				 pst.setString(2, newPro.getManuNum());
				 pst.setString(3, newPro.getArea());
				 pst.setDouble(4, newPro.getPrice());
				 pst.setString(5, supId);
		  		 pst.setString(6, old.getCatNum());
		  		 pst.setDate(7, date);
				 pst.executeUpdate();
				 pst.close();
				 rs.close();
				 List<Discount> dis=newPro.getDisList();
				for(int i=0;i<dis.size();i++)
				{
					AddDiscountQuantity(supId,date,newPro.getCatNum(),dis.get(i).getQun(),dis.get(i).getDisc());
				}
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				pst.close();
				return "there is no product as you describe";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update product. probably because dependecies");
		}
	}
	
	
	protected List<AgreementEntity> GetAllProductByAgreement(String id, java.util.Date date) throws AccessDeniedException
	{	
		String query="SELECT Max(sign_date) FROM agreement WHERE supplier_id = ?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		Date signDate=(Date) date;
		if(date==null){
			try {
				pst=conn.prepareStatement(query);
				pst.setString(1, id);
				rs=pst.executeQuery();
				signDate=rs.getDate(1);
			} catch (SQLException e1) {
				throw new AccessDeniedException("Error: to find the recent date to acheive products from agreement");
			}
		}
		query="SELECT DISTINCT name,manu_num,catalog_number,manufacturer_id,department,price,weight,sign_date,supplier_id FROM product_supply join product_manufacturer WHERE supplier_id=? AND sign_date=? ";
		pst=null;
		List<AgreementEntity> productList=new LinkedList<AgreementEntity>();
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			pst.setDate(2, signDate);
			rs=pst.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(2)); // 77 
			System.out.println(rs.getString(4)); // 98
				productList.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6) ,getDiscountOfProduct(rs.getString(9),rs.getDate(8),rs.getString(3)),rs.getDouble(7)));
			}}catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to add new Delivery day.");
			}
		return productList;
	}
	
	private String getSupplierManId(String manuNum) throws AccessDeniedException
	{
		String query2="SELECT * FROM product_manufacturer WHERE Product_Num= ?";
		PreparedStatement pst2=null;
		ResultSet rs2=null;
		try {
			pst2=conn.prepareStatement(query2);
			pst2.setString(1, manuNum);
			rs2=pst2.executeQuery();
			return rs2.getString(2);
			}catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: get Supplier Man Id.");
			}
	}
	
	private List<Discount> getDiscountOfProduct(String id,Date signDate, String catNum) throws AccessDeniedException
	{
		String query="SELECT * FROM quantities_list WHERE supplier_id=? AND sign_date=? AND catalog_number=? ORDER BY quantity";
		PreparedStatement pst=null;
		List<Discount> discountList=new LinkedList<Discount>();
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, id);
			pst.setDate(2, signDate);
			pst.setString(3, catNum);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				discountList.add(new Discount(rs.getDouble(4),rs.getDouble(5)));
			}catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to get Discount.");
			}
		return discountList;
	}

	
	protected void DeleteProductByAgreement(String supId, Date date, Product pro) throws AccessDeniedException
	{
		String query="DELETE FROM product_supply WHERE supplier_id=? AND catalog_number=? AND sign_date=? ";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, pro.getCatNum());
			pst.setDate(3, date);
			pst.execute();
			pst.close();
			for(Backend.Discount dis: pro.getDisList())
				DeleteDiscountOfProduct(supId, date, pro.getCatNum(),dis.getQun());
		Manufacturer_product_Dal mpd=new Manufacturer_product_Dal(conn, entity_dal);
		mpd.DeleteManufacturerProduct(pro.getManuNum());
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Delete Product.");
		}
	}
	
	private void DeleteDiscountOfProduct(String supplierId, Date signDate, String catNum, double quantity) throws AccessDeniedException
	{
		String query="DELETE FROM quantities_list WHERE supplier_id=? AND sign_date=? AND catalog_number=? AND quantity=? ";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, supplierId);
			pst.setDate(2, signDate);
			pst.setString(3, catNum);
			pst.setDouble(3, quantity);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to Delete quantity list of Product.");
		}
	}
	
	protected Product getProduct(String supId,String catNum,Date signDate) throws AccessDeniedException
	{
		Product pro=null;
		String query="SELECT DISTINCT name,manu_num,catalog_number,manufacturer_id,department,price,weight,sign_date FROM product_supply join product_manufacturer WHERE supplier_id = ? AND catalog_number = ? AND sign_date = ? ";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, supId);
			pst.setString(2, catNum);
			pst.setDate(3, signDate);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
				pro=new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6) , getDiscountOfProduct(supId,signDate,catNum),rs.getDouble(7));
			pst.close();
			rs.close();
			return pro;
		}catch(SQLException e){throw new AccessDeniedException("Error: to get product.");}
	}
}
