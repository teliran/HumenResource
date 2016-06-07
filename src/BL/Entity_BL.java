package BL;
import DAL.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import Backend.AgreementEntity;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Order;
import Backend.Product;
import Backend.SupIdSearchable;
import Backend.SupplierEntity;

public class Entity_BL implements  IBL{

	protected IDAL itsDAL;
	
	/////////////////
	///Constructor///
	/////////////////
	public Entity_BL(IDAL dal)
    {
          itsDAL = dal; 
    }

	
	////////////////////
	///AddByAgreement///
	////////////////////
	@Override
	public String AddByAgreement(String supId, Date date, AgreementEntity entity) throws AccessDeniedException {
		return entity.addVisit(supId, date, this);
	}
	public String AddByAgreement(String supId, Date date, Backend.Product pro) throws AccessDeniedException {
		return itsDAL.AddProductByAgreement(supId, date, pro);
	}

	/////////////////////
	///EditByAgreement///
	/////////////////////
	
	@Override
	public String EditByAgreement(String SupId, Date date, AgreementEntity old, AgreementEntity newEntity)
			throws AccessDeniedException {
		return old.EditVisit(SupId, date, this, old, newEntity);
	}
	
	public String EditByAgreement(String SupId, Date date, Backend.Product oldPro, Backend.Product newPro)
			throws AccessDeniedException {
		return itsDAL.EditProductByAgreement(SupId, date, oldPro, newPro);
	}

	///////////////////////
	///DeleteByAgreement///
	///////////////////////
	
	@Override
	public void DeleteByAgreement(String supId, Date date, AgreementEntity entity) throws AccessDeniedException {
		entity.DeleteVisit(supId, date, this);
	}
	
	public void DeleteByAgreement(String supId, Date date,Backend.Product pro) throws AccessDeniedException {
		itsDAL.DeleteProductByAgreement(supId, date, pro);
	}

	/////////////////////////
	///GetItAllByAgreement///
	/////////////////////////
	
	@Override
	public List<AgreementEntity> GetItAllByAgreement(String supId, Date date,AgreementEntity entity) throws AccessDeniedException {
		return entity.GetItAllVisit(supId, date, this);
	}
	
	public List<AgreementEntity> GetItAllByAgreement(String supId, Date date,Backend.Product pro) throws AccessDeniedException {
		return itsDAL.GetAllProductByAgreement(supId, date);
	}
	
	
	/////////////
	///imp add///
	/////////////
	
	@Override
	public String Add(Entity entity) throws AccessDeniedException 
	{
		return entity.addVisit(this);
	}
	public String addVisit(Backend.Order order) throws AccessDeniedException
	{
		return itsDAL.AddOrder(order);
	}
	public String addVisit(Backend.Agreement agreement) throws AccessDeniedException
	{
		return itsDAL.AddAgreement(agreement);
	}
	public String addVisit(Backend.Supplier supplier) throws AccessDeniedException
	{
		return itsDAL.AddSupplier(supplier);
	}

	//////////////
	///imp edit///
	//////////////
	
	@Override
	public String Edit(Entity oldEntity, Entity newEntity) throws AccessDeniedException 
	{
		return oldEntity.EditVisit(this, newEntity);
	}
	public String EditVisit(Backend.Order order, Backend.Order newEntity) throws AccessDeniedException
	{
		return itsDAL.EditOrder(order.getOrderID(), newEntity);
	}
	public String EditVisit(Backend.Agreement agreement, Backend.Agreement newEntity) throws AccessDeniedException
	{
		return itsDAL.EditAgreement(agreement.getSupNum(), agreement.getSignDate(), newEntity);
	}
	public String EditVisit(Backend.Supplier supplier, Backend.Supplier newEntity) throws AccessDeniedException
	{
		return itsDAL.EditSupplier(supplier.getSupName(), newEntity);
	}
	
	
	
	////////////////
	///searchById///
	////////////////
	
	@Override
	public IdSearchable SearchByID(String ID, IdSearchable entity) throws AccessDeniedException, NumberFormatException, ParseException {
		return entity.SearchByIdVisit(ID, this);
	}
	
	public IdSearchable SearchByID(String ID, Backend.Supplier sup) throws AccessDeniedException {
		return itsDAL.SearchSupplierById(ID);
	}
	
	public IdSearchable SearchByID(String ID, Backend.Manufacturer manu) throws AccessDeniedException {
		return itsDAL.SearchManufacturerById(ID);
	}
	
	public IdSearchable SearchByID(int ID, Backend.Order order) throws AccessDeniedException, ParseException {
		return itsDAL.SearchOrderById(ID);
	}



	/////////////
	///imp Del///
	/////////////
	
	public void DeleteEntity(Entity entity) throws AccessDeniedException
	{
		entity.DeleteVisit(this);
	}
	public void DeleteVisit(Backend.Supplier supplier) throws AccessDeniedException
	{
		itsDAL.DeleteSupplier(supplier);
	}
	public void DeleteVisit(Backend.Agreement agreement) throws AccessDeniedException
	{
		itsDAL.DeleteAgreement(agreement);
	}
	public void DeleteVisit(Backend.Order order) throws AccessDeniedException
	{
		itsDAL.DeleteOrder(order.getOrderID());
	}
	
	///////////////////////////////
	///Imp GetEntityBySupplierId///
	///////////////////////////////

	@Override
	public List<SupIdSearchable> GetEntityBySupplier(String id, SupIdSearchable entity) throws AccessDeniedException, ParseException {
		return entity.SearchBySupIdVisit(id, this);
	}
	
	public List<SupIdSearchable> GetEntityBySupplier(String id, Backend.Agreement agreement) throws AccessDeniedException 
	{
		return itsDAL.GetagreementBySupplier(id);
	}
	
	public List<SupIdSearchable> GetEntityBySupplier(String id, Backend.Order order) throws AccessDeniedException, ParseException 
	{
		return itsDAL.GetOrderBySupplier(id);
	}
	
	public List<SupIdSearchable> GetEntityBySupplier(String id, Backend.Contact contact) throws AccessDeniedException 
	{
		return itsDAL.GetContactBySupplier(id);
	}
	
	public List<SupIdSearchable> GetEntityBySupplier(String id, Backend.Manufacturer manu) throws AccessDeniedException 
	{
		return itsDAL.GetManufacturerBySupplier(id);
	}
	
	public List<SupIdSearchable> GetEntityBySupplier(String id, Backend.Supplier supplier) throws AccessDeniedException 
	{
		return itsDAL.GetsupplierBySupplier(id);
	}

	
	//////////////
	///GetItAll///
	//////////////
	
	@Override
	public List<Entity> GetItAll(Entity entity) throws AccessDeniedException, ParseException {
		return entity.GetItAll(this);
	}
	public List<Entity> GetItAll(Backend.Supplier sup) throws AccessDeniedException {
		return itsDAL.GetAllSupplier();
	}
	public List<Entity> GetItAll(Backend.Agreement agreement) throws AccessDeniedException {
		return itsDAL.GetAllAgreement();
	}
	public List<Entity> GetItAll(Backend.Order order) throws AccessDeniedException, ParseException {
		return itsDAL.GetAllOrder();
	}
	

	
	////////////////////
	///imp AddBySupId///
	////////////////////
	
	
	@Override
	public String AddBySupplierId(String supId, SupplierEntity entity) throws AccessDeniedException {
		return entity.addVisit(supId, this);
	}

	public String AddBySupplierId(String supId, Backend.Contact contact) throws AccessDeniedException {
		return itsDAL.AddContactBySupplierId(supId, contact);
	}
	
	public String AddBySupplierId(String supId, Backend.Manufacturer manufacturer) throws AccessDeniedException {
		return itsDAL.AddManufacturerBySupplierId(supId, manufacturer);
	}
	
	/////////////////////
	///imp EditBySupId///
	/////////////////////
	
	@Override
	public String EditBySupplierId(String SupId,SupplierEntity old, SupplierEntity newEntity) throws AccessDeniedException {
		return old.EditVisit(SupId, this, newEntity, old);
	}
	public String EditBySupplierId(String SupId,Backend.Contact old, Backend.Contact newEntity) throws AccessDeniedException {
		return itsDAL.EditContactBySupplierId(SupId, old, newEntity);
	}
	public String EditBySupplierId(String SupId,Backend.Manufacturer old, Backend.Manufacturer newEntity) throws AccessDeniedException {
		return itsDAL.EditManufacturerBySupplierId(SupId, old, newEntity);
	}
	
		
	///////////////////////
	///imp DeleteBySupId///
	///////////////////////
	@Override
	public void DeleteBySupplierId(String supId, SupplierEntity entity) throws AccessDeniedException {
		entity.DeleteVisit(supId, this);
	}
	public void DeleteBySupplierId(String supId, Backend.Contact contact) throws AccessDeniedException {
		itsDAL.DeleteContactBySupplierId(supId, contact);
	}
	public void DeleteBySupplierId(String supId, Backend.Manufacturer manufaturer) throws AccessDeniedException {
		itsDAL.DeleteManufacturerBySupplierId(supId, manufaturer.getID());
	}

/////
	@Override
	public boolean ManufacturerSupplier(int manId, String supID) throws AccessDeniedException {
		return itsDAL.ManufacturerSupplier(manId, supID);
	}
	
	
	///////////////////////
	///product_functions///
	///////////////////////
	
	@Override
	public List<Product> GetProductByArea(String supNum,Date date, String Area) throws AccessDeniedException {
		Product_BL pro=new Product_BL(itsDAL);
		return pro.GetProductByArea(supNum,date, Area);
	}
	

	@Override
	public Product SearchProductByCat(String catNum, String supId, Date date) throws AccessDeniedException {
		Product_BL pro=new Product_BL(itsDAL);
		return pro.SearchProductByCat(catNum, supId,date);
	}

	
	@Override
	public Product getProduct(String supId, String catNum, Date signDate) throws AccessDeniedException {
		return itsDAL.getProduct(supId, catNum, signDate);
	}	
	
	
	
	
	@Override
	public List<Product> GetProductByName(String supNum, Date date, String name) throws AccessDeniedException {
		Product_BL pro=new Product_BL(itsDAL);
		return pro.GetProductByName(supNum, date, name);
		
	}
	
	@Override
	public boolean UpdateProductInOrder(int orderNum, String supId, Backend.ProductQun pro, boolean toDo) throws AccessDeniedException {
		return itsDAL.UpdateProductInOrder(orderNum, supId, pro, toDo);
	}

	@Override
	public int getMaxIdOrder() throws AccessDeniedException {
		return itsDAL.getMaxIdOrder();
	}


	@Override
	public List<Order> AdvOrderSearch(String field, String field2, String value, String value2) throws ParseException, AccessDeniedException {
		Order_BL ord= new Order_BL(itsDAL);
		return ord.AdvOrderSearch(field, field2, value, value2);
	}


	@Override
	public int CloseConnection() {
		return itsDAL.CloseConnection();
	}


	@Override
	public boolean IsProductExists(String productNum, String manuNum) throws AccessDeniedException {
		Product_BL pro=new Product_BL(itsDAL);
		return pro.IsProductExists(productNum, manuNum);
		
	}


	@Override
	public void takeOrder(List<String> manID, List<String> productManID, List<Double> quntity) throws AccessDeniedException {
		Order_BL ord=new Order_BL(itsDAL);
		ord.takeOrder(manID, productManID, quntity);
	}


	@Override
	public String printOrder(Order order) throws AccessDeniedException {
		Order_BL ord=new Order_BL(itsDAL);
		return ord.printOrder(order);
	}

///////////////////////////////////////////////////////////////////

	
}
