package storagePl;

import java.util.ArrayList;
import java.util.Scanner;
import Emp.Employee.Position;
import GUI.check;
import storageBl.IBLs;
import storageBackend.Department;
import storageBackend.Product;
import storageBackend.Stock;


public class PLmanagment {
	private IBLs bl;
	
	public PLmanagment(IBLs bl){
		this.bl=bl;
	}
	
	Scanner reader  =new Scanner (System.in) ;
	
	
	private void UpdateDemagedProductNumber(){
		System.out.println("products: ");
		ArrayList<Product> lp = bl.findAllProducts();
		for (Product p : lp){
			if (p.getIsRemove()==false){
				System.out.println("product id: "+p.getProd_id()+" product name: "+p.getName());
			}
		}
		System.out.println("enter product id");
		//int choise = Integer.parseInt(reader.nextLine());
		int choise = check.checkInt(0, Integer.MAX_VALUE, "input invalid");
		boolean isFound = false;
		for (Product p : lp){
			if (p.getIsRemove()==false && p.getProd_id() == choise){
				isFound = true;
			}
		}
		if(!isFound){
			System.out.println("the number is invalid");
		}
		else{
			System.out.println("enter product demaged number to add:");
			int num = check.checkInt(0, Integer.MAX_VALUE, "input invalid");
			
			bl.TakeToDameged(choise, num);
			
		}
	}
	private void productManue(){
		System.out.println("for add new product press 1");
		System.out.println("for get product by name press 2");
		System.out.println("for get product by id press 3");
		System.out.println("for add amount of demaged product press 4");
		System.out.println("for update product press 5");
		System.out.println("for delete product press 6");
		
		System.out.println("for return to main menue press 7");
		int choise = check.checkInt(1, 7, "input invalid");
		switch(choise)
		{
		case 1: // new product
			addproduct();
			break;
		case 2:
			System.out.println("enter product name");
			Product pname = null;
			pname = bl.findProductByName(check.checkName("name invalid need to be no more than 20."));
			if(pname != null)
			System.out.println(pname.toString());
			else{
				System.out.println("no such product");
			}
			break;
		case 3:
			System.out.println("enter product id");
			Product pid = null;
			pid = bl.findProductByID(check.checkInt(1, Integer.MAX_VALUE, "input not valid"));
			if(pid != null)
			System.out.println(pid.toString());
			else{
				System.out.println("no such product");
			}
			break;
		case 4:
			UpdateDemagedProductNumber();
			break;
		case 5:
			UpdateProduct();
			break;
		case 6:
			deleteProduct();
			break;
		default:
			return;
		}
	}
	private void ReportMenue(){
		System.out.println("for catalog of departments press 1");
		System.out.println("for expiried report press 2");
		System.out.println("for demaged products report press 3");
		System.out.println("for under minimum amount products report press 4");
		System.out.println("for return to main menue press 5");
		int choise = check.checkInt(1, 5, "input not valid");
		switch(choise)
		{
		
		case 1:
			spesifideCatalog();
			break;
		
		case 2:
			bl.getExpiried();
			break;
		case 3:
			bl.demaged();
			break;
	
		case 4:
			bl.getMinimum();
			break;
		case 5:
			return;
		default:
			System.out.println("the number is invalid");
			return;
		
		}
	}
	
	private void storageManue(){
		System.out.println("for catalog press 1");
		System.out.println("for get all storage details press 2");		
		System.out.println("for get stock for specific product press 3");		
		System.out.println("for add products to storage press 4");
		System.out.println("for take product from stok press 5");
		System.out.println("for return to main menue press 6");
		
		int choise = check.checkInt(1, 6, "input not valid");
		switch(choise)
		{
		case 1:
			bl.PrintCatalog();
			break;
		case 2: // storage details
			bl.getallstock();
			break;
		
		case 3:
			spesifiedStok();
			break;
		
		case 4:
			addStock();
			break;
		case 5:
			take();
			return;
		
		case 6:
			return;
			default:
				System.out.println("the number is invalid");
				return;
		}
	}
	
	private void addStock() {
		//bl.addStock(1, 12, 12, "2016-05-15 00:00:00", false);
		ArrayList<Product> lp = bl.findAllProducts();
		System.out.println("products:");
		for(Product p :lp){
			if(p.getIsRemove()==false)
			System.out.println("product id: "+ p.getProd_id() + " product name: "+p.getName() );
		}
		
		System.out.println("enter product id");
		
		int prod_id = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		
		System.out.println("enter supplier id");
		int supplier = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter amount");
		int amount = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter expiried day by format: yyyy-MM-dd");
		String expiered_day = check.chek_date("invalid input");
		
		bl.makeAnOrder(prod_id, supplier, amount, expiered_day,false);
		}
		
	

	private void spesifiedStok(){
		bl.PrintCatalog();
		System.out.println("enter product id");
		int prodID = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		ArrayList<Stock> ls = bl.spesifiedStok(prodID);
		for (Stock s : ls){
			System.out.println(s.toString());
		}
	}
	
	private void spesifideCatalog(){
		System.out.println("first departments:");
		ArrayList<Department> ld =bl.findDepartmentsByGeneralDepartmentID(0);
		if (!ld.isEmpty()){
		System.out.println("enter first department number or -1 for all");
		int choise1 = check.checkInt(-1, Integer.MAX_VALUE, "input not valid");
		boolean isFound = false;
		for (Department d :ld){
			if (d.getDepartment_id()==choise1){
				isFound = true;
			}
				
		}
		if (isFound == false && choise1!=-1){
			System.out.println("the number is invalid");
		}
		else{
			
				if(choise1 != -1){
					System.out.println("second departments:");
					ld = bl.findDepartmentsByGeneralDepartmentID(choise1);
					System.out.println("enter second department number or -1 for all");
					int choise2 = check.checkInt(-1, Integer.MAX_VALUE, "input not valid");
					isFound = false;
					for (Department d :ld){
						if (d.getDepartment_id()==choise2){
							isFound = true;
						}
							
					}
					if (isFound == false && choise2!=-1){
						System.out.println("the number is invalid");
					}
					else
					{
						
						if(choise2 != -1){
							System.out.println("3nd departments:");
							ld = bl.findDepartmentsByGeneralDepartmentID(choise2);
							System.out.println("enter third department number or -1 for all");
							int choise3 = check.checkInt(-1, Integer.MAX_VALUE, "input not valid");
							isFound = false;
							for (Department d :ld){
								if (d.getDepartment_id()==choise3){
									isFound = true;
								}
									
							}
							if (isFound == false && choise3!=-1){
								System.out.println("the number is invalid");
							}
							else{
								if(choise3!=-1)
								bl.PrintspecifiedCatalog(choise1,choise2,choise3);
								else
									bl.PrintspecifiedCatalog(choise1,choise2,-1);
							}
						}
						else{
							bl.PrintspecifiedCatalog(choise1,-1,-1);
						}
					}
			}
				else{
					bl.PrintspecifiedCatalog(-1,-1,-1);
				}
		
		}
		}
		
	}
	
	public void printOptions(){
		if(!store.Store.user.equals(Position.storeManager)){
		System.out.println("for exit press 1");
		
		System.out.println("for storage manue press 2");
		System.out.println("for product manue press 3");
		System.out.println("for department menue press 4");
		System.out.println("for report menue press 5");}
		else{
			System.out.println("for exit press 1");
			System.out.println("for report menue press 2");
		}
	}
	
	private void take(){
		System.out.println("the products that in the store:");
		bl.PrintCatalog();
		System.out.println("enter product id");
		int choise = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter amount to take");
		int num = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		bl.take(choise, num);
		
	}
	
	public boolean getChoise(){
		if(!store.Store.user.equals(Position.storeManager)){
		int choise = check.checkInt(1, 5, "input not valid");
		switch(choise)
		{
		case 1:
			bl.orderMinimumProduct();
			return false;
		
		case 2:
			storageManue();
			break;
		case 3:
			productManue();
			break;
		case 4:
			departmentMenue();
			break;
		case 5:
			ReportMenue();
			break;
		default:{

			return false;
		}
		}}
	    else
	    {
	    			int choise = check.checkInt(1, 5, "input not valid");
	    			switch(choise)
	    			{
	    			case 1:
	    				bl.orderMinimumProduct();
	    				return false;
	    			
	    			case 2:
	    				ReportMenue();
	    				break;
	    			default:{

	    				return false;
	    			}
	    			}
	    }
		return true;
		
	}
	
	private void departmentMenue() {
		System.out.println("for delate department press 1");
		System.out.println("for add department press 2");
		System.out.println("for all departments press 3");
		System.out.println("for update department press 4");
		System.out.println("for return to main menue press 5");
		int choise = check.checkInt(1, 5, "input not valid");
		switch(choise)
		{
		case 1: 
			deleteDepartment();
			break;
		case 2:
			addDepartment();
			break;
		case 3:
			allDepartments();
			break;
		case 4:
			updateDepartment();
			break;
		default:
			return;
		}
		
	}

	private void allDepartments() {
		
		 bl.findAllDepartment();
		
	}

	private void addDepartment() {
		int choise1;
		int choise2;
		
		boolean isCorrect = true;
		boolean isFaund = false;
		ArrayList<Department> ld;
		/*blayer.catalog();
		
		System.out.println("enter general department id or 0 to general one");
		int genid = Integer.parseInt(reader.nextLine());*/
		System.out.println("enter department name");
		String name = check.checkName("input invalid");
		//System.out.println("enter department id");
		//int id = Integer.parseInt(reader.nextLine());
		int genid = 0;
		/*Department d = new Department(id, name, genid, false);
		blayer.addDepartment(d);*/
		System.out.println("in department:");
		ld =bl.findDepartmentsByGeneralDepartmentID(0);
		System.out.println("enter first general department number or 0 for general one");
		choise1 = check.checkInt(0, Integer.MAX_VALUE, "input not valid");
		if(choise1 != 0 ){
			for (Department d : ld){
				if (d.getDepartment_id() == choise1)
					isFaund = true;
			}
			if ( isFaund){
				isFaund = false;
				System.out.println("second departments:");
				ld= bl.findDepartmentsByGeneralDepartmentID(choise1);
				System.out.println("enter second department number or 0 for general");
				choise2 = check.checkInt(0, Integer.MAX_VALUE, "input not valid");
				for (Department d : ld){
					if (d.getDepartment_id() == choise2)
						isFaund = true;
				}
				if(isCorrect && choise2 != 0){
					if (isFaund){
							genid = choise2;
					}
					else{
						System.out.println("the number is invalid");
						isCorrect = false;
					}
						
				}else{
					genid = choise1;
				}
			}else{
					System.out.println("the number is invalid");
					isCorrect = false;
				}
			}else{
				genid=0;
			}
			
		
		if(isCorrect){
		
		bl.addDepartment(name, genid);
		}
	}
	private void updateDepartment() {
		int choise1;
		int choise2;
		
		boolean isCorrect = true;
		boolean isFaund = false;
		ArrayList<Department> ld;
		System.out.println("departments:");
		ld = bl.findAllDepartment();
		
		
		System.out.println("enter department id");
		int dep_id =  check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		boolean isFound = false;
		for (Department d1 : ld)
			if(d1.getIsRemove()==false && d1.getDepartment_id()==dep_id)
				isFaund = true;
		if(!isFaund){
			System.out.println("the nuber is invalid");
		}
		else{
		/*blayer.catalog();
		
		System.out.println("enter general department id or 0 to general one");
		int genid = Integer.parseInt(reader.nextLine());*/
		System.out.println("enter department name");
		String name = check.checkName("invalid output");
		//System.out.println("enter department id");
		//int id = Integer.parseInt(reader.nextLine());
		
		
		
		bl.UpdateDepartment(dep_id,name);
		
		}
	}

	private void addproduct(){
		 int prod_id;
		 String name;
		 String manufactureID;
		 String manufactureProdID;
		 int department_id1 = 0;
		 int department_id2 = 0;
		 int department_id3 = 0;
		 int minimum_amount;
		 String nextDateOrder;
		 int days;
		 int quntity;
		 
		 
		
		System.out.println("enter product id");
		prod_id =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter product name");
		name =check.checkName("invalid output");
		System.out.println("enter product manufacture id");
		manufactureID =""+check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter product manufacture product id");
		manufactureProdID =reader.nextLine();//""+check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("first departments:");
		ArrayList<Department> ld = bl.findDepartmentsByGeneralDepartmentID(0);
		System.out.println("enter first department number");
		int choise1 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");;
		boolean isFound = false;
		for (Department d : ld){
			if (d.getDepartment_id() == choise1){
				department_id1 = choise1;
				isFound = true;
			}
		}
		if(!isFound){
			System.out.println("the number is invalid");
		}
		else{
			isFound = false;
			System.out.println("second departments:");
			ld = bl.findDepartmentsByGeneralDepartmentID(choise1);
			System.out.println("enter second department number");
			int choise2 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
			for (Department d : ld){
				if (d.getDepartment_id() == choise2){
					department_id2 =choise2;
					isFound = true;
				}
			}
			if (!isFound){
				System.out.println("the number is invalid");
			}
			else{
				isFound = false;
				System.out.println("3nd departments:");
				ld = bl.findDepartmentsByGeneralDepartmentID(choise2);
				System.out.println("enter third department number");
				int choise3 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
				for (Department d : ld){
					if (d.getDepartment_id() == choise3){
						department_id3 =choise3;
						isFound = true;
					}
				}
				if (isFound){
					System.out.println("enter product minimum amount");
					minimum_amount = check.checkInt(0, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter product next day to order from soplier in format yyyy-mm-dd");
					nextDateOrder =check.chek_date("invalid input");
					System.out.println("enter how much day to order:");
					days =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter number to order");
					quntity =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
					
					
					bl.AddProd(prod_id, name,  manufactureID,  manufactureProdID, department_id1, department_id2, department_id3, minimum_amount, 0, 0,
							nextDateOrder,days,quntity);

				}
			}
		}
		
		
		
		
		
		
	}
	private void UpdateProduct(){
		 int prod_id;
		 String name;
		 String manufactureID;
		 String manufactureProdID;
		 int department_id1 =0;
		 int department_id2=0;
		 int department_id3=0;
		 int minimum_amount;
		 int total_amount;
		 int total_damaged;
		 String nextDateOrder;
		 int days;
		 int quntity;
		
		ArrayList<Product> lp = bl.findAllProducts();
		System.out.println("prducts:");
		for (Product p1: lp){
			System.out.println("product id:" +p1.getProd_id()+" product name "+p1.getName());
		}
		
		System.out.println("enter product id");
		prod_id =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		boolean isFound = false;
		for (Product p1: lp){
			if (p1.getProd_id()==prod_id){
				isFound = true;
			}
		}
		if (!isFound){
			System.out.println("the product num is invalid");
		}
		else{
		
		
		System.out.println("enter product name");
		name =check.checkName("invalid output");
		System.out.println("enter product manufacture id");
		manufactureID =""+check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("enter product manufacture product id");
		manufactureProdID =""+check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		System.out.println("first departments:");
		ArrayList<Department> ld = bl.findDepartmentsByGeneralDepartmentID(0);
		System.out.println("enter first department number");
		int choise1 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		isFound = false;
		for (Department d : ld){
			if (d.getDepartment_id() == choise1){
				department_id1=choise1;
				isFound = true;
			}
		}
		if(!isFound){
			System.out.println("the number is invalid");
		}
		else{
			isFound = false;
			System.out.println("second departments:");
			ld = bl.findDepartmentsByGeneralDepartmentID(choise1);
			System.out.println("enter second department number");
			int choise2 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
			for (Department d : ld){
				if (d.getDepartment_id() == choise2){
					department_id2 =choise2;
					isFound = true;
				}
			}
			if (!isFound){
				System.out.println("the number is invalid");
			}
			else{
				isFound = false;
				System.out.println("3nd departments:");
				ld = bl.findDepartmentsByGeneralDepartmentID(choise2);
				System.out.println("enter third department number");
				int choise3 = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
				for (Department d : ld){
					if (d.getDepartment_id() == choise3){
						department_id3 =choise3;
						isFound = true;
					}
				}
				if (isFound){
					System.out.println("enter product minimum amount");
					minimum_amount =check.checkInt(0, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter product total amount");
					total_amount =check.checkInt(0, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter product total demaged");
					total_damaged =check.checkInt(0, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter product next day to order from soplier in format yyyy-mm-dd");
					nextDateOrder =reader.nextLine();
					System.out.println("enter how much day to order:");
					days =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
					System.out.println("enter number to order");
					quntity =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
				
					bl.UpdateProduct(prod_id, name, manufactureID,  manufactureProdID, department_id1, department_id2, department_id3, minimum_amount, total_amount, total_damaged,nextDateOrder,days,quntity, false);

				}
			}
		}
		}
		
		
		
		
		
		
		
	}
	private void deleteProduct(){
		ArrayList<Product> lp = bl.findAllProducts();
		System.out.println("prducts:");
		for (Product p1: lp){
			System.out.println("product id:" +p1.getProd_id()+" product name "+p1.getName());
		}
		int num;
		System.out.println("enter product id");
		num =check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		boolean isFound = false;
		for (Product p1: lp){
			if (p1.getProd_id()==num){
				isFound = true;
			}
		}
		if (!isFound){
			System.out.println("the product num is invalid");
		}
		else{
			bl.DeleteProduct(num);
		}
	}
		
	
	private void deleteDepartment(){
		System.out.println("departments:");
		ArrayList<Department> ld =bl.findAllDepartment();
		boolean isFound = false;
		System.out.println("enter department id");
		int dep = check.checkInt(1, Integer.MAX_VALUE, "input not valid");
		for (Department d  : ld){
			if (dep==d.getDepartment_id()){
				isFound = true; 
			}
		}
		if(isFound){
		bl.DeleteDepartment(dep);
		for (Department d  : bl.findDepartmentsByGeneralDepartmentID(dep)){
			for (Department d2 : bl.findDepartmentsByGeneralDepartmentID(d.getDepartment_id())){
				if (d2.getIsRemove()==false)
					bl.DeleteDepartment(d2.getDepartment_id());
			}
			if (d.getIsRemove()==false)
				bl.DeleteDepartment(d.getDepartment_id());
		}
		}
		else{
			System.out.println("the number is invalid");
			
		}
	}
	
 	

}
