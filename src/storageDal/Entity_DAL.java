package storageDal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import storageBackend.Department;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;
import storageDal.IDAL;

public class Entity_DAL implements IDAL{
	private Connection conn;
	private linQDepartment linq_d;
	private linQProduct linq_p;
	private linQStock linq_s;
	private linQReportSupply linq_rs;
	
	public Entity_DAL(Connection conn){
		
	      this.conn = conn;
	      linq_d = new linQDepartment(conn);
	      linq_p = new linQProduct(conn);
	      linq_s = new linQStock(conn);
	      linq_rs = new linQReportSupply(conn);
	      
	     
	}

	@Override
	public boolean isDepartmentExist(String departmentName) {
		
		return linq_d.isDepartmentExist(departmentName);
		
	}

	@Override
	public void UpdateDepartment(Department d) {
		
		 linq_d.UpdateDepartment(d);
		
	}

	@Override
	public void DeleteDepartment(int department_id) {
		
		linq_d.DeleteDepartment(department_id);
		
	}

	@Override
	public void AddDepartment(Department d) {
		// TODO Auto-generated method stub
		
		linq_d.AddDepartment(d);
	}

	@Override
	public ArrayList<Department> findDepartmentsByGeneralDepartmentID(
			int general_department_id) {
		
		return linq_d.findDepartmentsByGeneralDepartmentID(general_department_id);
	}

	@Override
	public Department findDepartmentByDepartmentID(int department_id) {
		
		return linq_d.findDepartmentByDepartmentID(department_id);
	}

	@Override
	public Department findDepartmentByName(String department_name) {
		
		return linq_d.findDepartmentByName(department_name);
	}

	@Override
	public ArrayList<Department> findAllDepartment() {
		
		return linq_d.findAllDepartment();
	}
	public boolean isProductExist(int prodID, String name){
		
		return linq_p.isProductExist(prodID, name);
	}
	public Product findProductByManufactureIDAndManufactureProdID(String manID, String manProdID){
		return linq_p.findProductByManufactureIDAndManufactureProdID(manID, manProdID);
	}
	@Override
	public Product findProductByID(int prodID) {
		
		return linq_p.findProductByID(prodID);
	}

	@Override
	public Product findProductByName(String prodName) {
		
		return linq_p.findProductByName(prodName);
	}

	@Override
	public void UpdateProduct(Product p) {
		
		linq_p.UpdateProduct(p);
	}
	public void UpdateNextDateOrderProduct(int prod_id, String nextDateOreder){
		linq_p.UpdateNextDateOrderProduct(prod_id, nextDateOreder);
	}
	@Override
	public void UpdateTotalAmountProduct(int amount, int prod_id) {
		
		linq_p.UpdateTotalAmountProduct(amount, prod_id);
		
	}

	@Override
	public void UpdateTotalDamagedProduct(int damaged, int prod_id) {
		
		linq_p.UpdateTotalDamagedProduct(damaged, prod_id);
		
	}

	@Override
	public void DeleteProduct(int prod_id) {
		
		linq_p.DeleteProduct(prod_id);
		
	}

	@Override
	public void AddProd(Product p) {
		
		linq_p.AddProd(p);
		
	}

	@Override
	public ArrayList<Product> findAllProducts() {
		
		return linq_p.findAllProducts();
	}

	@Override
	public ArrayList<Product> findProductsByDepartments(int department1,
			int department2, int department3) {
		
		return linq_p.findProductsByDepartments(department1, department2, department3);
	}

	@Override
	public ArrayList<Product> findProductsByOneDepartment(int department1) {
		
		return linq_p.findProductsByOneDepartment(department1);
	}

	@Override
	public int AddStock(Stock s) {
		
		return linq_s.AddStock(s);
	}

	@Override
	public ArrayList<Stock> SelectStockByProductIDSupplierExpiredDay(
			int product_id, int suplier, String expire_day) {
		
		return linq_s.SelectStockByProductIDSupplierExpiredDay(product_id, suplier, expire_day);
	}

	@Override
	public int findNumOfExpiredProduct(int prod_id) {
		
		return linq_s.findNumOfExpiredProduct(prod_id);
	}

	@Override
	public void UpdateAmountProduct(int stock_index, int amount) {
		
		linq_s.UpdateAmountProduct(stock_index, amount);
		
	}

	@Override
	public ArrayList<Stock> findStockByProductIDAndNotExpiried(int prodID) {
		
		return linq_s.findStockByProductIDAndNotExpiried(prodID);
	}

	@Override
	public ArrayList<Stock> findStockByProductID(int prodID) {
		
		return linq_s.findStockByProductID(prodID);
	}

	@Override
	public Stock findStockByIndex(int index) {
		
		return linq_s.findStockByIndex(index);
	}

	@Override
	public ArrayList<Stock> findAllStocks() {
		
		return linq_s.findAllStocks();
	}

	@Override
	public void UpdateStock(Stock s) {
		
		linq_s.UpdateStock(s);
		
	}

	@Override
	public void DeleteStock(Stock s) {
		
		linq_s.DeleteStock(s);
		
	}

	@Override
	public void AddReportSupply(ReportSupply rs) {
		
		linq_rs.AddReportSupply(rs);
		
	}
	public int CloseConnection() {
		try {
			conn.close();
			return 1;
		} catch (SQLException e) {
			return 0;
		}
	}
	
}
