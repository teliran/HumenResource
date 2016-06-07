package storageBl;

import java.util.ArrayList;

import storageBackend.Department;
import storageBackend.Product;
import storageBackend.Stock;
import storageDal.IDAL;

public class DepartmentBL {
protected IDAL itsDAL;
	
	/////////////////
	///Constructor///
	/////////////////
	public DepartmentBL(IDAL dal)
    {
          itsDAL = dal; 
    }
public ArrayList<Department> allDepartments(){
		
		ArrayList<Department> Department =itsDAL.findAllDepartment();
		for (Department d : Department) {
			if(!d.getIsRemove())
			System.out.println("general id: " + d.getGeneral_department_id() + " id: " + d.getDepartment_id() +" name: "+ d.getName());
		}
		return Department;
		}
public void UpdateDepartment(int depid,String name){
	Department d = itsDAL.findDepartmentByDepartmentID(depid);
	if (!itsDAL.isDepartmentExist(name)){
		d.setName(name);
		itsDAL.UpdateDepartment(d);
	}
	else
		System.out.println("name alredy exist");
}
public void delateDepartment(int dep) {
	
	ArrayList<Product> lp;
	for (Department d  : specificDepartment(dep)){
		for (Department d2 : specificDepartment(d.getDepartment_id())){
			
			lp = itsDAL.findProductsByOneDepartment(d2.getDepartment_id());
			for(Product p : lp){
				if(p.getIsRemove()==false){
					ArrayList<Stock> ls = itsDAL.findStockByProductID(p.getProd_id());
					for(Stock s : ls){
						itsDAL.DeleteStock(s);
					}
					itsDAL.DeleteProduct(p.getProd_id());
				}
			}
			
			
			if (d2.getIsRemove()==false)
				itsDAL.DeleteDepartment(d2.getDepartment_id());
				
		}
		lp = itsDAL.findProductsByOneDepartment(d.getDepartment_id());
		for(Product p : lp){
			if(p.getIsRemove()==false){
				ArrayList<Stock> ls = itsDAL.findStockByProductID(p.getProd_id());
				for(Stock s : ls){
					itsDAL.DeleteStock(s);
				}
				itsDAL.DeleteProduct(p.getProd_id());
			}
		}
		if (d.getIsRemove()==false)
			delateDepartment(d.getDepartment_id());
	}
	
	
	lp = itsDAL.findProductsByOneDepartment(dep);
	for(Product p : lp){
		if(p.getIsRemove()==false){
			ArrayList<Stock> ls = itsDAL.findStockByProductID(p.getProd_id());
			for(Stock s : ls){
				itsDAL.DeleteStock(s);
			}
			itsDAL.DeleteProduct(p.getProd_id());
		}
		
	}
	itsDAL.DeleteDepartment(dep);
	
	
	
	
	
}
public ArrayList<Department> specificDepartment(int general){
	
	ArrayList<Department> Department = itsDAL.findDepartmentsByGeneralDepartmentID(general);
	if ( Department.size()!=0){
		System.out.println("sub departments number: " + Department.size());
		
	}	
	for (Department d : Department) {
		
		
		
		System.out.println("id: " + d.getDepartment_id() +" name: "+ d.getName());
	}
	return Department;
}

public void addDepartment(String name,int general_id) {
	
	if (!itsDAL.isDepartmentExist(name)){
		Department d =new Department(0, name, general_id, false);
		itsDAL.AddDepartment(d);
	}
	else{
		System.out.println("the department name already exist");
	}
}


}
