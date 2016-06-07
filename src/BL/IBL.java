package BL;
import Backend.AgreementEntity;
import Backend.Entity;
import Backend.IdSearchable;
import Backend.Product;
import Backend.Order;
import Backend.SupIdSearchable;
import Backend.SupplierEntity;
import DAL.AccessDeniedException;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public interface IBL {

    /// <summary> Adding entities. </summary>
    /// <param name="entity">the entity to add</param>
    /// <returns> returns NUll if succeded ,else return String with the error</returns>
	String Add(Entity entity) throws AccessDeniedException; 

    /// <summary> Adding SupplierEntities. </summary>
    /// <param name="entity">the SupplierEntity to add</param>
	/// <param name="supId">the supplier id which the SupllierEntity has relation</param>
    /// <returns> returns NUll if succeded ,else return String with the error</returns>
	String AddBySupplierId(String supId,SupplierEntity entity) throws AccessDeniedException; 
	
	/// <summary> Adding AgreementEntity </summary>
	/// <param name="supId">the supplier id which the AgreementEntity has relation</param>
	/// <param name="date">the agreement date which the AgreementEntity has relation</param>
    /// <param name="entity">the AgreementEntity to add</param>
    /// <returns> returns NUll if succeded ,else return String with the error</returns>
	String AddByAgreement(String supId, Date date, AgreementEntity entity) throws AccessDeniedException; 

    /// <summary>Edit existing entity</summary>
    /// <param name="oldEntity">the old entity</param>
    /// <param name="newEntity">the new entity</param>
    /// <returns>returns null if succeed, or string if failed</returns>
	String Edit(Entity oldEntity, Entity newEntity) throws AccessDeniedException;

	/// <summary>Edit existing SupplierEntity</summary>
    /// <param name="old">the old SupplierEntity</param>
    /// <param name="newEntity">the new SupplierEntity</param>
    /// <returns>returns null if succeed, or string if failed</returns>
	String EditBySupplierId(String SupId,SupplierEntity old, SupplierEntity newEntity) throws AccessDeniedException;
	
	/// <summary>Edit existing AgreementEntity</summary>g
    /// <param name="oldEntity">the old AgreementEntity</param>
    /// <param name="newEntity">the new AgreementEntity</param>
    /// <returns>returns null if succeed, or string if failed</returns>
	String EditByAgreement(String SupId, Date date, AgreementEntity old, AgreementEntity newEntity) throws AccessDeniedException;

	// i added Entity
    /// <summary>Search the IdSearchable entity by ID number</summary>
    /// <param name="ID">the ID number to search</param>
    /// <returns>returns the specific entity with this id, or null if there isnt any</returns>
	IdSearchable SearchByID(String ID, IdSearchable entity) throws AccessDeniedException, NumberFormatException, ParseException;

	
	/// CHANGED second parameter to entity
	/// search some kind of type by supplier
	/// <summary>Search the entity by Supplier id</summary>
    /// <param name="id">the id to search</param>
    /// <param name="entity">the id to search</param>
    /// <returns>returns List of SupIdHolder with this id, or empty List if there isn't any</returns>
	List<SupIdSearchable> GetEntityBySupplier (String id, SupIdSearchable entity)throws AccessDeniedException, ParseException;
	
	
	/// <summary>Search if the manufacturer exist, for this Supplier</summary>
    /// <param name="manId">the id to search</param>
	/// <param name="supID">the supid to search</param>
    /// <returns>return true if there is , else return false</returns>
	boolean ManufacturerSupplier(int manId, String supID)throws AccessDeniedException;
	
	
	/// <summary>Search product by category</summary>
    /// <param name="catNum">the catNum to search</param>
	/// <param name="supId">the supId to search</param>
    /// <returns>return product,else return null</returns>
	Product SearchProductByCat(String catNum, String supId , Date date)throws AccessDeniedException;
	
	/// <summary>Delete entity</summary>
    /// <param name="entity">the entity to delete</param>
    void DeleteEntity(Entity entity)throws AccessDeniedException;

    /// <summary>Delete SupplierEntity</summary>
    /// <param name="entity">the SupplierEntity to delete</param>
    void DeleteBySupplierId(String supId,SupplierEntity entity)throws AccessDeniedException;

  /// <summary>Delete AgreementEntity</summary>
    /// <param name="entity">the AgreementEntity to delete</param>
    void DeleteByAgreement(String supId,Date date, AgreementEntity entity)throws AccessDeniedException;

    /// <summary>Gets all the list of Entities</summary>
    /// <param name="entity">get Entity</param>
    /// <returns>List of all the Entities</returns>
    List<Entity> GetItAll(Entity entity) throws AccessDeniedException, ParseException;
   
    
    /// <summary>Gets all the list of AgreementEntities</summary>
    /// <param name="entity">the AgreementEntity you want to search the agreement relation with</param>
    /// <param name="date">date the agreement was signed, if null the last agreement</param>
    /// <returns>List of all the entities</returns>
    List<AgreementEntity> GetItAllByAgreement(String supId, Date date,AgreementEntity entity) throws AccessDeniedException;
    

    /**
     * 
     * @param orderNum number of order
     * @param supId id of supplier 
     * @param pro product to update in order
     * @param toDo true- insert new product to order or upgrade if exist, else delete.
     * @return true if there wasn't any problem, else false.
     * @throws AccessDeniedException 
     */
    boolean UpdateProductInOrder(int orderNum,String supId,Backend.ProductQun pro, boolean toDo) throws AccessDeniedException;
    
    
    
    /**
     * 
     * @param supNum the sup id the product relation with
     * @param Area to search 
     * @param sign of the agreement the product relation to if NULL- the last agreement else the agreement mentioned 
     * @return list of product from the mentioned agreement 
     * @throws AccessDeniedException
     */
    List<Product> GetProductByArea(String supNum, Date date, String Area)throws AccessDeniedException;
    
    /**
     * 
     * @param supNum the sup id the product relation with
     * @param Area the atre to search 
     * @param sign of the agreement the product relation to if NULL- the last agreement else the agreement mentioned 
     * @return list of product from the mentioned agreement 
     * @throws AccessDeniedException
     */
    List<Product> GetProductByName(String supNum,Date date, String Area)throws AccessDeniedException;
 ///////////////////////////////////////////////////////////////////////////////////
    /**
     * check if the product exist in any current valid date agreement
     * @param productNum product number
     * @param manuNum manufacturer number
     * @return true if exist , else false
     * @throws AccessDeniedException 
     */
    boolean IsProductExists(String productNum,String manuNum) throws AccessDeniedException;
    
    /**
     * make order by the chipest price 
     * @param manID
     * @param productManID
     * @param quntity
     * @throws AccessDeniedException 
     */
    void takeOrder(List<String> manID,List<String> productManID,List<Double> quntity) throws AccessDeniedException;
 ///////////////////////////////////////////////////////////////////////////////////
    /***
     * 
     * @param supId the supplier Id
     * @param catNum the catalog number
     * @param signDate the agreement date this product belong to
     * @return product.
     * @throws AccessDeniedException 
     */
    Product getProduct(String supId,String catNum,Date signDate) throws AccessDeniedException;
    
    
    /***
     * 
     *	@param none
     *	@return highst order number in the system
     *  @throws AccessDeniedException 
     */
     int getMaxIdOrder() throws AccessDeniedException;
    
      /**
       * 
       * @param field the feild to search- date, product price
       * @param value product-name, date-specific date, price-specific price, if both same field- by range
       * @return list of orders.
     * @throws ParseException 
     * @throws AccessDeniedException 
       */
      List <Order> AdvOrderSearch(String field, String field2, String value,String value2) throws ParseException, AccessDeniedException;
      //return a string;
      String printOrder(Order order) throws AccessDeniedException;
      /**
       * 
       * @return 1 if close , else 0 .
       */
      int CloseConnection();
}