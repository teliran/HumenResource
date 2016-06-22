package DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import Backend.Agreement;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Order;
import Backend.ProductQun;
import Backend.SupIdSearchable;

public class Order_DAL {


	private Entity_Dal entity_dal;
	private Connection conn;
	
	/////////////////
	///Constructor///
	/////////////////
	
	protected Order_DAL(Connection conn, Entity_Dal entity_dal) 
	{
		this.entity_dal= entity_dal;
		this.conn = conn;
	}
	
	protected String AddOrder(Backend.Order order) throws AccessDeniedException
	{
		String query="insert into orders values(?,?,?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1,order.getOrderID());
			pst.setDate(2,(java.sql.Date)order.getDate());
			pst.setTime(3,order.getTime());
			pst.setString(4,order.getSupNum());
			pst.setDouble(5,order.getTotalPrice());
			}
		catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to add new order.");
		}
			
			try {
				pst.executeUpdate();
				pst.close();
	
				for(int i=0;i<order.getList().size();i++)
				{
					ProductQun proquen=order.getList().get(i);
					addProductToOrder(order.getOrderID(),order.getSupNum(),proquen.getPro().getCatNum(),proquen.getQun(),((Agreement)entity_dal.getLastAgreementBySupplier(order.getSupNum())).getSignDate()  );
				}
				return null;	
				}
			catch (SQLException e) 
			{
				return "The order is already exist at the system.";
			}
	}

	private void addProductToOrder(int orderId, String supId, String catNum, double quantity, Date date) throws AccessDeniedException
	{
		String query="insert into order_product_supply values(?,?,?,?,?)";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1, orderId);
			pst.setString(2,supId);
			pst.setString(3,catNum);
			pst.setDate(4, date);
			pst.setDouble(5,quantity);
			pst.execute();
			pst.close();
			}
		catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to add new product to order.");
		}
	}

	protected String EditOrder(int orderId, Order newEntity) throws AccessDeniedException
	{
		String query="SELECT * FROM orders WHERE id=?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, orderId);
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE orders SET date = ?, time = ?, total_price = ? WHERE id = ? ");
				 pst.setDate(1, (Date) newEntity.getDate());
				 pst.setTime(2, newEntity.getTime());
				 pst.setDouble(3, newEntity.getPrice());
				 pst.setInt(4, orderId);
				 pst.executeUpdate();
				 pst.close();
				 rs.close();
				 List<ProductQun> orderProducts=newEntity.getProductsList();
				 for(ProductQun p:orderProducts)
					 updateQuantitysInOrder(orderId, newEntity.getSupNum(), p.getPro().getCatNum() ,p.getQun());
				 return null;
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
				return "there is order as you describe";
			}
		} catch (SQLException e) 
		{
			throw new AccessDeniedException("Error: to update an order details.");
		}
	}	
	
	private void updateQuantitysInOrder(int orderId,String supId, String catNum, double quantity)
	{ 
		String query="SELECT * FROM order_product_supply WHERE order_id=? AND supplier_id =? AND catalog_number=?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, orderId);
			pst.setString(2, supId);
			pst.setString(3, catNum);
			rs=pst.executeQuery();
			if(rs.next())
			{
				 pst = conn.prepareStatement("UPDATE order_product_supply SET quantity = ? WHERE order_id=? AND supplier_id =? AND catalog_number=?");
				// pst = conn.prepareStatement(query);
			   	 pst.setDouble(1, quantity);
			   	 pst.setInt(2, orderId);
				 pst.setString(3, supId);
				 pst.setString(4, catNum);
				 pst.execute();
				 pst.close();
				 rs.close();
			}
			else //rs is empty means there were not result for the query
			{
				rs.close();
			}
		} catch (SQLException e) 
		{
			try {
				throw new AccessDeniedException("Error: to update an order.");
			} catch (AccessDeniedException e1) {
				System.out.println("an error while edit an product quantity");
			}
		}
	}
	protected int GetMaxOrderId() throws AccessDeniedException
	{
		String query="SELECT Max(id) FROM orders";
		PreparedStatement pst=null;
		ResultSet rs=null;
		int x;
		try {
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				x=rs.getInt(1);
				pst.close();
				rs.close();
				return x;
			} catch (SQLException e1) {
				throw new AccessDeniedException("Error: failed while try to get max order id");
			}
		
	}
	
	protected boolean UpdateProductInOrder(int orderNum, String supId, ProductQun proQun , boolean toDo) throws AccessDeniedException
	{
		String query="SELECT Max(sign_date) FROM agreement WHERE supplier_id=?";
		PreparedStatement pst=null,pst1=null;
		ResultSet rs=null;
		Date signDate;
		try {
				pst=conn.prepareStatement(query);
				pst.setString(1, supId);
				rs=pst.executeQuery();
				signDate=rs.getDate(1);
			} catch (SQLException e1) {
				throw new AccessDeniedException("Error: to find the recent date to acheive products from order");
			}
		
		if(!toDo)
		{
			query="DELETE FROM order_product_supply WHERE order_id = ? AND supplier_id = ? AND catalog_number = ? AND sign_date=?";
			try {
				pst = conn.prepareStatement(query);
				pst.setInt(1, orderNum);
				pst.setString(2, supId);
				pst.setString(3, proQun.getPro().getCatNum());
				pst.setDate(4, signDate);
				pst.execute();
				pst.close();
				return true;
			} catch (SQLException e) {				throw new AccessDeniedException("Error: to Delete a product in order.");
			}
		}
		else
		{
			 query="INSERT OR REPLACE INTO order_product_supply(order_id,supplier_id,catalog_number,sign_date,quantity) VALUES(?,?,?,?,?)";
			try {
				pst1=conn.prepareStatement(query);
				pst1.setInt(1, orderNum);
				pst1.setString(2, supId);
				pst1.setString(3, proQun.getPro().getCatNum());
				pst1.setDate(4, (Date)signDate);
				pst1.setDouble(5, proQun.getQun());
				pst1.executeUpdate();
				pst1.close();
				return true;
				}
			catch (SQLException e) 
			{
				System.out.println(e);
				throw new AccessDeniedException("Error: to add or replace product to order.");
			}
		}
	}

	protected IdSearchable SearchOrderById(int id) throws ParseException, AccessDeniedException
	{
		String query="SELECT * FROM orders WHERE id=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				Order order=new Order(id, rs.getString(4), rs.getDate(2), rs.getTime(3), rs.getDouble(5), GetProductsInOrder(id));
				pst.close();
				rs.close();
				return order;
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
	
	private List<ProductQun> GetProductsInOrder(int orderId) throws AccessDeniedException 
	{
		String query="SELECT * FROM order_product_supply WHERE order_id=? ";
		PreparedStatement pst=null;
		List<ProductQun> productQunList=new LinkedList<ProductQun>();
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1, orderId);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
				productQunList.add(new ProductQun(entity_dal.getProduct(rs.getString(2), rs.getString(3), rs.getDate(4)), rs.getDouble(5)));
			pst.close();
			rs.close();
			return productQunList;
		}catch (SQLException e) 
			{
				throw new AccessDeniedException("Error: to get  the products in particular order.");
			}
	}

	protected List<SupIdSearchable> GetOrderBySupplier(String id) throws AccessDeniedException, ParseException
	{
		String query="SELECT * FROM orders WHERE supplier_id=?";
		PreparedStatement pst;
		List<SupIdSearchable> orderBySupList=new LinkedList<SupIdSearchable>();
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				
				Order order=new Order(rs.getInt(1), id , rs.getDate(2),rs.getTime(3), rs.getDouble(5), GetProductsInOrder(rs.getInt(1)));
				orderBySupList.add(order);
			}
			return orderBySupList;
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to search supplier by id.");
		}
	}

	protected List<Entity> GetAllOrder() throws AccessDeniedException, ParseException
	{
		String query="SELECT * FROM orders";
		PreparedStatement pst;
		List<Entity> orderList=new LinkedList<Entity>();
		try {
			pst = conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				Order order=new Order(rs.getInt(1), rs.getString(4) , rs.getDate(2), rs.getTime(3), rs.getDouble(5), GetProductsInOrder(rs.getInt(1)));
				orderList.add(order);
			}
			return orderList;
		} catch (SQLException e) {
			System.out.println(e);
			throw new AccessDeniedException("Error: get all orders.");
		}
	}

	protected void DeleteOrder(int id) throws AccessDeniedException
	{
		String query="DELETE FROM order_product_supply WHERE order_id = ? ";
		PreparedStatement pst=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1,id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to delete products in order");
		}
		query="DELETE FROM orders WHERE id = ? ";
		try {
			pst=conn.prepareStatement(query);
			pst.setInt(1,id);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to delete an order");
		}
	}
	
	protected List<Order> GetOrderByProductName(String name1,String name2) throws ParseException, AccessDeniedException
	{
		String query;
		if(name2!=null)
		{
		query="SELECT DISTINCT orders.id FROM orders JOIN order_product_supply NATURAL JOIN product_supply WHERE order_product_supply.order_id=orders.id AND (product_supply.name=? OR product_supply.name=?)";
		}
		else
			query="SELECT DISTINCT orders.id FROM orders JOIN order_product_supply NATURAL JOIN product_supply WHERE order_product_supply.order_id=orders.id AND product_supply.name=?";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, name1);
			if(name2!=null)
				pst.setString(2, name2);
			ResultSet rs=pst.executeQuery();
			List<Order> orderList=new LinkedList<Order>();
			while(rs.next())
			{
				orderList.add((Order) SearchOrderById(rs.getInt(1)));
			}
			rs.close();
			pst.close();
			return orderList;
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: to get order by product num.");
		}		
	}

	protected Entity getLastOrderBySupplier(String supNum) throws AccessDeniedException
	{
		String query="SELECT Max(date) FROM orders WHERE supplier_id = ?";
		PreparedStatement pst=null;
		ResultSet rs=null;
		Date signDate=null;
		try {
			pst=conn.prepareStatement(query);
			pst.setString(1, supNum);
			rs=pst.executeQuery();
			signDate=rs.getDate(1);
			pst.close();
			rs.close();
		} catch (SQLException e1) {
			return null;
		}
		if(signDate==null)
			return null;
		query="SELECT * FROM orders WHERE date = ? ";
		PreparedStatement pst1;
		try {
			pst1 = conn.prepareStatement(query);
			pst1.setDate(1, signDate);
			ResultSet rs1=pst1.executeQuery();
			Order order=new Order(rs1.getInt(1), rs1.getString(4) , rs1.getDate(2), rs1.getTime(3), rs1.getDouble(5), GetProductsInOrder(rs1.getInt(1)));				
			pst1.close();
			rs1.close();
			return order;
		} catch (SQLException e) {
			throw new AccessDeniedException("Error: get last order.");
		}
	}
}
