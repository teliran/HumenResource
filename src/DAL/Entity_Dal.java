package DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import Backend.Agreement;
import Backend.AgreementEntity;
import Backend.Contact;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Manufacturer;
import Backend.Order;
import Backend.Product;
import Backend.ProductQun;
import Backend.SupIdSearchable;
import Backend.Supplier;

public class Entity_Dal implements IDAL {

	private Connection conn;
	public Entity_Dal(Connection con) throws AccessDeniedException
    {
          this.conn=con;
    }
	//////////////
	///Supplier///
	//////////////V
	
	@Override
	public String AddSupplier(Supplier entity) throws AccessDeniedException {//V
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		return supplier.AddSupplier(entity);
	}
	
	@Override
	public IdSearchable SearchSupplierById(String id) throws AccessDeniedException//V 
	{
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		return supplier.SearchSupplierById(id);
	}
	
	@Override
	public List<Entity> GetAllSupplier() throws AccessDeniedException {//V
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		return supplier.GetAllSupplier();
	}
		

	@Override
	public String EditSupplier(String id, Supplier newEntity) throws AccessDeniedException {//V
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		return supplier.EditSupplier(id, newEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupIdSearchable> GetsupplierBySupplier(String id) throws AccessDeniedException {//V
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		return (List<SupIdSearchable>) supplier.SearchSupplierById(id);
	}

	@Override
	public void DeleteSupplier(Supplier sup) throws AccessDeniedException {//V
		Supplier_DAL supplier=new Supplier_DAL(conn,this);
		supplier.DeleteSupplier(sup);
	}
	
	///////////////
	///Agreement///
	///////////////V
	
	@Override
	public String AddAgreement(Agreement entity) throws AccessDeniedException {//V
		Agreement_DAL agreement=new Agreement_DAL(conn,this);
		return agreement.AddAgreement(entity);
	}
	@Override
	public String EditAgreement(String supId, Date signDate, Agreement newEntity) throws AccessDeniedException {//V
		Agreement_DAL agreement=new Agreement_DAL(conn,this);
		return agreement.EditAgreement(supId, (java.sql.Date) signDate, newEntity);
	}
	@Override
	
	public List<SupIdSearchable> GetagreementBySupplier(String id) throws AccessDeniedException {//V
		Agreement_DAL agreement=new Agreement_DAL(conn,this);
	    return agreement.GetagreementBySupplier(id);
	}
	@Override
	public List<Entity> GetAllAgreement() throws AccessDeniedException {//V
		Agreement_DAL agreement=new Agreement_DAL(conn,this);
		return agreement.GetAllAgreement();
	}
	@Override
	public void DeleteAgreement(Agreement agrement) throws AccessDeniedException {//V
		Agreement_DAL agreement=new Agreement_DAL(conn,this);
		agreement.DeleteAgreement(agrement);
	}
	
	
		
	//////////////////
	///Manufacturer///
	//////////////////V
	
	@Override
	public List<SupIdSearchable> GetManufacturerBySupplier(String id) throws AccessDeniedException {//V
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
	    return manu.GetManufacturerBySupplier(id);
	}
	
	@Override//V
	public String AddManufacturerBySupplierId(String supId, Manufacturer manufacturer) throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
		return manu.AddManufacturerBySupplierId(supId, manufacturer);
	}

	@Override//V
	public String EditManufacturerBySupplierId(String supId, Manufacturer old, Manufacturer newEntity) throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
		return manu.EditManufacturerBySupplier(supId, old, newEntity);
	}

	@Override//V
	public void DeleteManufacturerBySupplierId(String supId, String Manuid) throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
	    manu.DeleteManufacturerBySupplierId(supId, Manuid);
	}

	@Override//V
	public IdSearchable SearchManufacturerById(String id) throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
	    return manu.SearchManufacturerById(id);
	}
	@Override//V
	public List<Entity> GetAllManufacturer() throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
	    return manu.GetAllManufacturer();
	}
	
	@Override
	public boolean ManufacturerSupplier(int manId, String supID) throws AccessDeniedException {
		Manufacturer_DAL manu=new Manufacturer_DAL(conn,this);
		return manu.ManufacturerSupplier(manId, supID);
	}
	
	////////////
	///Order////
	////////////V

	@Override
	public List<Order> AdvOrderSearch(String value1, String value2) throws ParseException, AccessDeniedException {
		Order_DAL order=new Order_DAL(conn,this);
		return order.GetOrderByProductName(value1, value2);
	}

	@Override
	public String AddOrder(Order entity) throws AccessDeniedException {//V
		Order_DAL order=new Order_DAL(conn,this);
		return order.AddOrder(entity);
	}
	
	@Override
	public String EditOrder(int orderId, Order newEntity) throws AccessDeniedException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.EditOrder(orderId, newEntity);
	}

	@Override
	public boolean UpdateProductInOrder(int orderNum, String supId, ProductQun proQun , boolean toDo) throws AccessDeniedException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.UpdateProductInOrder(orderNum, supId, proQun, toDo);
	}
	
	
	@Override
	public IdSearchable SearchOrderById(int id) throws ParseException, AccessDeniedException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.SearchOrderById(id);
	}
	
	@Override
	public List<SupIdSearchable> GetOrderBySupplier(String id) throws AccessDeniedException, ParseException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.GetOrderBySupplier(id);
	}
	
	@Override
	public List<Entity> GetAllOrder() throws AccessDeniedException, ParseException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.GetAllOrder();
	}
	
	@Override
	public void DeleteOrder(int id) throws AccessDeniedException {
		Order_DAL ord=new Order_DAL(conn,this);
		ord.DeleteOrder(id);
	}
	
	@Override
	public int getMaxIdOrder() throws AccessDeniedException {
		Order_DAL ord=new Order_DAL(conn,this);
		return ord.GetMaxOrderId();
	}
	
	
	
	/////////////
	///Contact///
	/////////////V
	
	@Override//V
	public List<SupIdSearchable> GetContactBySupplier(String id) throws AccessDeniedException {
		Contact_DAL contact=new Contact_DAL(conn,this);
	    return contact.GetContactBySupplier(id);
	}

	@Override//V
	public String AddContactBySupplierId(String supId, Contact contact) throws AccessDeniedException {
		Contact_DAL cont=new Contact_DAL(conn,this);
		return cont.AddContact(supId, contact); 
	}

	@Override//V
	public void DeleteContactBySupplierId(String supId,Backend.Contact contact)
			throws AccessDeniedException {
		Contact_DAL cont=new Contact_DAL(conn,this);
		cont.DeleteContact(supId, contact); 		
	}

	@Override//V
	public String EditContactBySupplierId(String supId, Backend.Contact old, Backend.Contact newContact)throws AccessDeniedException 
	{
			Contact_DAL cont=new Contact_DAL(conn,this);
			return cont.EditContact(supId, old, newContact);
	}

	/////////////
	///Product///
	/////////////V

	@Override//V
	public String AddProductByAgreement(String supId, Date date, Product product) throws AccessDeniedException {
		Product_DAL pro=new Product_DAL(conn, this);
		return pro.AddProductByAgreement(supId,(java.sql.Date) date, product);
	}

	@Override//V
	public String EditProductByAgreement(String SupId, Date date, Product old, Product newPro) throws AccessDeniedException {
		Product_DAL pro=new Product_DAL(conn, this);
		return pro.EditProductByAgreement(SupId,(java.sql.Date) date, old, newPro);
	}

	@Override//V
	public List<AgreementEntity> GetAllProductByAgreement(String supId, Date date) throws AccessDeniedException {
		Product_DAL pro=new Product_DAL(conn, this);
		return pro.GetAllProductByAgreement(supId,date);
	}

	@Override//V
	public void DeleteProductByAgreement(String supId, Date date, Product entity) throws AccessDeniedException {
		Product_DAL pro=new Product_DAL(conn, this);
		pro.DeleteProductByAgreement(supId, (java.sql.Date) date, entity);
	}

	@Override
	public Product getProduct(String supId, String catNum, Date signDate) throws AccessDeniedException {
		Product_DAL pro=new Product_DAL(conn, this);
		return pro.getProduct(supId, catNum, (java.sql.Date) signDate);
	}
	protected void AddDiscountQuantity(String supId, Date signDate, String catNum,double quantity,double discount)
	{
		
	}

	@Override
	public int CloseConnection() {
		try {
			conn.close();
			return 1;
		} catch (SQLException e) {
			return 0;
		}
	}

	@Override
	public Entity getLastAgreementBySupplier(String supId) throws AccessDeniedException {
		Agreement_DAL agreement= new Agreement_DAL(conn, this);
		return agreement.getLastAgreementBySupplier(supId);
	}

	@Override
	public Entity getLastOrderBySupplier(String supNum) throws AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
