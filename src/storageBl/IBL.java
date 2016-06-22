package storageBl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Backend.Order;
import storageBackend.Department;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;

public interface IBL {
	//Department
		public boolean isDepartmentExist(String departmentName);
		public void UpdateDepartment(int depid,String name);
		public void DeleteDepartment(int department_id);
		public void addDepartment(String name,int general_id);
		public ArrayList<Department> findDepartmentsByGeneralDepartmentID(int general_department_id);
		public Department findDepartmentByDepartmentID(int department_id);
		public Department findDepartmentByName(String department_name);
		public ArrayList<Department> findAllDepartment();

	//Product
		public void PrintCatalog ();
		public void PrintspecifiedCatalog(int dep1, int dep2, int dep3);
		
		public boolean isProductExist(int prodID, String name);
		public Product findProductByID(int prodID);
		public Product findProductByName(String prodName);
		public void UpdateProduct(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
				int minimum_amount, int total_amount, int total_damaged,String nextDateOrder, int days, int quntity, boolean isRemove);
		public void UpdateTotalAmountProduct(int amount, int prod_id);
		public void UpdateTotalDamagedProduct(int damaged, int prod_id);
		public void DeleteProduct(int prod_id);
		public void AddProd(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
				int minimum_amount, int total_amount, int total_damaged,String nextDateOrder, int days, int quntity);
		public ArrayList<Product> findAllProducts();
		public ArrayList<Product> findProductsByDepartments(int department1, int department2, int department3);
		public ArrayList<Product> findProductsByOneDepartment(int department1);
		public void UpdateNextDateOrderProduct(int prod_id, String nextDateOreder);
	//Stack
		public void getExpiried();
		
		public void getMinimum();
		public void makeAnOrder(int prod_id, int supid, int amount,String exp_day,boolean remove); 
		public void UpdateStoreSupply(Order order);
		
		public ArrayList<Stock> spesifiedStok(int prodID) ;
		public void getallstock();
		public void take(int choise, int num);
		public void TakeToDameged(int choise, int num);
		
		public void demaged(); 
		public void addStock(int prod_id, int supid, int amount,String exp_day,boolean remove) ;
		public void orderMinimumProduct();
		
		public int CloseConnection();
		
	
}
