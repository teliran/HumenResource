package storageBl;

import java.util.ArrayList;
import java.util.LinkedList;

import storageDal.IDAL;
import storageBackend.Department;
import storageBackend.Product;
import storageBackend.Stock;

public class ProductBL {
protected IDAL itsDAL;
	
	/////////////////
	///Constructor///
	/////////////////
	public ProductBL(IDAL dal)
    {
          itsDAL = dal; 
    }
	public void catalog(){
		ArrayList<Product> products =itsDAL.findAllProducts();
		for (Product p : products) {
			if (p.getIsRemove()!=true)
			System.out.println(p.getProd_id() +" "+ p.getName());
		}
	}
	
		
		public void specifiedCatalog(int dep1, int dep2, int dep3){
			ArrayList<Product> lp = itsDAL.findProductsByDepartments(dep1, dep2, dep3);
			for (Product p : lp){
				if (p.getIsRemove()==false)
					System.out.println(p.toString());
			}
		}
		
		
		public void AddProduct(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
				int minimum_amount, int total_amount, int total_damaged, String nextDateOrder, int days, int quntity){
			Product p = new Product(prod_id, name, manufactureID,manufactureProdID, department_id1, department_id2, department_id3, minimum_amount, total_amount, total_damaged,
					nextDateOrder, days, quntity,false);
			if (!itsDAL.isProductExist(p.getProd_id(), p.getName()))
				itsDAL.AddProd(p);
			else
				System.out.println("the product name or id already exist");
		}
		
		/*public boolean updateDemaged(Product p, int sup, String exp) throws NotFound{
			return linQs.UpdateStockDamage(p.getProd_id(), sup, exp);
			
		}*/
		public void updateProduct(int prod_id, String name, String manufactureID, String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
				int minimum_amount, int total_amount, int total_damaged,String nextDateOrder, int days, int quntity, boolean isRemove){
			Product p = new Product(prod_id, name, manufactureID,manufactureProdID, department_id1, department_id2, department_id3, minimum_amount, total_amount, total_damaged,nextDateOrder, days, quntity, isRemove);
			Product p1 = itsDAL.findProductByID(p.getProd_id());
			if(p1!=null){
				if((itsDAL.findProductByName( p.getName()).getProd_id()==p1.getProd_id())||(itsDAL.isProductExist(0, p.getName()))){
						itsDAL.UpdateProduct(p);
				}
			}
		}
		public void deleteProduct(int p_id){
			ArrayList<Stock> ls = itsDAL.findStockByProductID(p_id);
			for(Stock s : ls){
				itsDAL.DeleteStock(s);
			}
			itsDAL.DeleteProduct(p_id);
			
		}
		public void UpdateDemagedProduct(int choice, int num) {
			Product p1 = itsDAL.findProductByID(choice);
			if(p1!=null){
				itsDAL.UpdateTotalDamagedProduct(choice, num);
			}
			
			
			
		}
		
}
