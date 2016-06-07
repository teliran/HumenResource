package storageBl;

import java.util.ArrayList;

import storageDal.IDAL;
import storageBackend.Department;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;

public class Entity_BL implements IBL {
protected IDAL itsDAL;
private DepartmentBL dep_bl;
private ProductBL prod_bl;
private StockBL stock_bl;
private BL.IBL b;
	
	/////////////////
	///Constructor///
	/////////////////
	public Entity_BL(IDAL dal, BL.IBL b)
    {
		this.b=b;
          itsDAL = dal; 
          dep_bl = new DepartmentBL(dal);
          prod_bl = new ProductBL(dal);
          stock_bl = new StockBL(dal, b);
    }
	@Override
	public boolean isDepartmentExist(String departmentName) {
		return itsDAL.isDepartmentExist(departmentName);
	}

	@Override
	public void UpdateDepartment(int depid,String name) {
		
		dep_bl.UpdateDepartment(depid, name);
		
	}

	@Override
	public void DeleteDepartment(int department_id) {
	
		dep_bl.delateDepartment(department_id);
		
	}

	@Override
	public void addDepartment(String name,int general_id){
	
		dep_bl.addDepartment(name,general_id);
		
	}

	@Override
	public ArrayList<Department> findDepartmentsByGeneralDepartmentID(
			int general_department_id) {
		
		return dep_bl.specificDepartment(general_department_id);
		
	}

	@Override
	public Department findDepartmentByDepartmentID(int department_id) {
		return itsDAL.findDepartmentByDepartmentID(department_id);
	}

	@Override
	public Department findDepartmentByName(String department_name) {
		return itsDAL.findDepartmentByName(department_name);
	}

	@Override
	public ArrayList<Department> findAllDepartment() {
		
		return dep_bl.allDepartments();
	}
	public boolean isProductExist(int prodID, String name){
		return itsDAL.isProductExist(prodID, name);
	}
	@Override
	public Product findProductByID(int prodID) {
		return itsDAL.findProductByID(prodID);
	}

	@Override
	public Product findProductByName(String prodName) {
		return itsDAL.findProductByName(prodName);
	}

	@Override
	public void UpdateProduct(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
			int minimum_amount, int total_amount, int total_damaged,String nextDateOrder, int days, int quntity, boolean isRemove) {
		
		
		
		prod_bl.updateProduct( prod_id,  name,   manufactureID,  manufactureProdID,  department_id1,  department_id2,  department_id3 , 
				 minimum_amount,  total_amount,  total_damaged, nextDateOrder,  days,  quntity,  isRemove);
		
	}

	@Override
	public void UpdateTotalAmountProduct(int amount, int prod_id) {
		itsDAL.UpdateAmountProduct(prod_id, amount);
		
	}

	@Override
	public void UpdateTotalDamagedProduct(int damaged, int prod_id) {
		
		prod_bl.UpdateDemagedProduct(prod_id, damaged);
		
	}
	public void UpdateNextDateOrderProduct(int prod_id, String nextDateOreder){
		itsDAL.UpdateNextDateOrderProduct(prod_id, nextDateOreder);
	}
	@Override
	public void DeleteProduct(int prod_id) {
		
		prod_bl.deleteProduct(prod_id);
		
	}

	@Override
	public void AddProd(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
			int minimum_amount, int total_amount, int total_damaged,String nextDateOrder, int days, int quntity) {
		
		prod_bl.AddProduct(prod_id, name,  manufactureID,  manufactureProdID, department_id1, department_id2, department_id3, minimum_amount, total_amount, total_damaged,
				nextDateOrder,days,quntity);
		
	}
	public void PrintCatalog (){
		
		prod_bl.catalog();
	}
	public void PrintspecifiedCatalog(int dep1, int dep2, int dep3){
		
		prod_bl.specifiedCatalog(dep1, dep2, dep3);
	}
	@Override
	public ArrayList<Product> findAllProducts() {
		return itsDAL.findAllProducts();
	}

	@Override
	public ArrayList<Product> findProductsByDepartments(int department1,
			int department2, int department3) {
		return itsDAL.findProductsByDepartments(department1, department2, department3);
	}

	@Override
	public ArrayList<Product> findProductsByOneDepartment(int department1) {
		return itsDAL.findProductsByOneDepartment(department1);
	}

	

	@Override
	public void getMinimum(){
		
		stock_bl.getMinimum();
	}
	
	
	
	public ArrayList<Stock> spesifiedStok(int prodID) {
		
		return stock_bl.spesifiedStok(prodID);
	}
	public void getallstock(){
		
		stock_bl.getallstock();
	}
	public void take(int choise, int num){
		
		stock_bl.take(choise,num);
	}
	public void TakeToDameged(int choise, int num){
		
		stock_bl.TakeToDameged(choise,num);
	}
	
	public void demaged(){
		
		stock_bl.demaged();
	}
	public void addStock(int prod_id, int supid, int amount,String exp_day,boolean remove) {
		
		stock_bl.addStock(prod_id, supid, amount, exp_day, remove);
	}
	@Override
	public void getExpiried() {
		
		stock_bl.getExpiried();
		
	}
	public void orderMinimumProduct(){
		
		stock_bl.orderMinimumProduct();
	}
	public int CloseConnection(){
		return itsDAL.CloseConnection();
	}

	
	

}
