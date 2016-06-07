package storageDal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import storageBackend.Department;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;

public interface IDAL {

//Department
	public boolean isDepartmentExist(String departmentName);
	public void UpdateDepartment(Department d);
	public void DeleteDepartment(int department_id);
	public void AddDepartment(Department d);
	public ArrayList<Department> findDepartmentsByGeneralDepartmentID(int general_department_id);
	public Department findDepartmentByDepartmentID(int department_id);
	public Department findDepartmentByName(String department_name);
	public ArrayList<Department> findAllDepartment();

//Product
	public boolean isProductExist(int prodID, String name);
	public Product findProductByID(int prodID);
	public Product findProductByName(String prodName);
	public void UpdateProduct(Product p);
	public void UpdateTotalAmountProduct(int amount, int prod_id);
	public void UpdateTotalDamagedProduct(int damaged, int prod_id);
	public void DeleteProduct(int prod_id);
	public void AddProd(Product p);
	public ArrayList<Product> findAllProducts();
	public ArrayList<Product> findProductsByDepartments(int department1, int department2, int department3);
	public ArrayList<Product> findProductsByOneDepartment(int department1);
	public void UpdateNextDateOrderProduct(int prod_id, String nextDateOreder);
//Stack
	public int AddStock(Stock s);
	public ArrayList<Stock> SelectStockByProductIDSupplierExpiredDay(int product_id, int suplier,String expire_day );
	public int findNumOfExpiredProduct(int prod_id);
	public void UpdateAmountProduct(int stock_index, int amount);
	public ArrayList<Stock> findStockByProductIDAndNotExpiried(int prodID);
	public ArrayList<Stock> findStockByProductID(int prodID);
	public Stock findStockByIndex(int index);
	public ArrayList<Stock> findAllStocks();
	public void UpdateStock(Stock s);
	public void DeleteStock(Stock s);

//ReportSupply
	public void AddReportSupply(ReportSupply rs);
	
	
	public int CloseConnection();
}
