package storageBl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import storageBackend.Department;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;
public interface IstorageBl {

	public void getExpiried();
	
	public void getMinimum();
	
	public ArrayList<Stock> spesifiedStok(int prodID);
	
	public void catalog();
	
	public ArrayList<Department> allDepartments();
	public ArrayList<Department> specificDepartment(int general);
	
	public void specifiedCatalog(int dep1, int dep2, int dep3);
	
	public Product getProduct(String name);
	
	public ArrayList<Product> getAllProduct();
	
	public Product getProduct(int id);
	
	public void stock();
	
	public void AddProduct(Product p);
	
	public void updateProduct(Product p);
	
	public void deleteProduct(int p_id);
	
	public void UpdateDepartment(int depid,String name);
	
	public void take(int choise, int num);
	
	public void TakeToDameged(int choise, int num);

	public void demaged();
	
	public void UpdateDemagedProduct(int choice, int num);

	public void delateDepartment(int dep);

	public void addDepartment(Department d);

	void addStock(int prod_id, int supid, int amount,String exp_day,boolean remove);;

	
}
