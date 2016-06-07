package DAL;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import Backend.Agreement;
import Backend.AgreementEntity;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Order;
import Backend.Product;
import Backend.ProductQun;
import Backend.SupIdSearchable;


public interface IDAL {

	////////////
	//supplier//
	////////////
	
	String AddSupplier(Backend.Supplier entity) throws AccessDeniedException;//
	String EditSupplier(String id,Backend.Supplier newEntity) throws AccessDeniedException;//
	IdSearchable SearchSupplierById(String id) throws AccessDeniedException;//
	void DeleteSupplier(Backend.Supplier supplier) throws AccessDeniedException;
	List<Entity> GetAllSupplier() throws AccessDeniedException;// 
	List<SupIdSearchable> GetsupplierBySupplier(String id) throws AccessDeniedException;
	
	/////////////
	//agreement//
	/////////////
	
	String AddAgreement(Backend.Agreement entity) throws AccessDeniedException;//
	String EditAgreement(String supId, Date signDate,Backend.Agreement newEntity) throws AccessDeniedException;//
	void DeleteAgreement(Agreement agreement) throws AccessDeniedException;
	List<Entity> GetAllAgreement() throws AccessDeniedException; 
	List<SupIdSearchable> GetagreementBySupplier(String id) throws AccessDeniedException;//
	//add it please###
	Entity getLastAgreementBySupplier(String supId) throws AccessDeniedException;
	
	///////////
	//Contact//
	///////////V
	
	String AddContactBySupplierId(String supId,Backend.Contact entity) throws AccessDeniedException;//
	String EditContactBySupplierId(String SupId,Backend.Contact old, Backend.Contact newEntity)throws AccessDeniedException;
	List<SupIdSearchable> GetContactBySupplier(String id) throws AccessDeniedException;//
	void DeleteContactBySupplierId(String supId,Backend.Contact entity) throws AccessDeniedException;//
	
	////////////////
	//Manufacturer//
	////////////////
	
	String AddManufacturerBySupplierId(String supId,Backend.Manufacturer manufacturer) throws AccessDeniedException;//
	String EditManufacturerBySupplierId(String supId,Backend.Manufacturer old, Backend.Manufacturer newEntity) throws AccessDeniedException;//
	List<SupIdSearchable> GetManufacturerBySupplier(String id) throws AccessDeniedException;//
	IdSearchable SearchManufacturerById(String id) throws AccessDeniedException;
	void DeleteManufacturerBySupplierId(String supId, String Manuid) throws AccessDeniedException;// here i have to check if connection exist delete only connection else delete all
	List<Entity> GetAllManufacturer() throws AccessDeniedException;
	public boolean ManufacturerSupplier(int manId, String supID) throws AccessDeniedException;
	
	/////////
	//Order//
	/////////
	List<Order> AdvOrderSearch(String value1, String value2) throws ParseException, AccessDeniedException;
	int getMaxIdOrder() throws AccessDeniedException;
	String AddOrder(Backend.Order entity) throws AccessDeniedException;
	String EditOrder(int orderId, Backend.Order newEntity) throws AccessDeniedException;
	IdSearchable SearchOrderById(int id) throws ParseException, AccessDeniedException;
	void DeleteOrder(int id) throws AccessDeniedException;
	List<Entity> GetAllOrder() throws AccessDeniedException, ParseException;
	List<SupIdSearchable> GetOrderBySupplier(String id) throws AccessDeniedException, ParseException;
	boolean UpdateProductInOrder(int orderNum, String supId, ProductQun pro, boolean toDo) throws AccessDeniedException;
	//add please###
	Entity getLastOrderBySupplier(String supNum) throws AccessDeniedException;
	///////////
	//Product//
	///////////V
	
	String AddProductByAgreement(String supId, Date date, Product entity) throws AccessDeniedException;//
	String EditProductByAgreement(String SupId, Date date, Product old, Product newEntity) throws AccessDeniedException;
	void DeleteProductByAgreement(String supId, Date date, Product entity) throws AccessDeniedException;
	List<AgreementEntity> GetAllProductByAgreement(String supId, Date date) throws AccessDeniedException;//
	Product getProduct(String supId,String catNum,Date signDate) throws AccessDeniedException;
	
	
	/////////////////////
	///closeconnection///
	/////////////////////
	/**
	 * @return  1 if close, 0 if not.
	 */
	int CloseConnection();
}
