package GUI;
import java.util.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import BL.IBL;
import Backend.*;
import DAL.AccessDeniedException;
 public class AddProduct {
	 private IBL bl;
	 
	 public AddProduct(IBL bl){
		 this.bl=bl;
	 }
	 
 		 
	public String add(String supID, Date signDate){
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-ADD PRODUCT-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("----------------------At any stage press 0 to quit--------------------");
		 String ans="";
		 String name;
		 String CatNum="";
		 String manID;
		 String manuNum= "";
		 String area;
		 double price;
		
		 System.out.println("Please enter CatNum: ");
		 boolean flag= false;
		 while(!flag){
			 flag=true;
			 Product pro=null;
			 CatNum= check.checkNameP("wrong input... \n please enter product CAT number in less than 50 letters \n");
			 try {
				  pro= bl.SearchProductByCat(CatNum, supID , signDate);
				} catch (AccessDeniedException e) {
					flag=true;
					return "network problems.... addpro(37)";
			}
			 
			 if (pro!=null)	
				 flag=false;
			
			 
			 if (CatNum.equals("0")){
				 flag=true;
				 return "quiting";
			 }
			 if (!flag)
				 System.out.println("CATnum already exists, please enter diffrent CATnum");
		 }
		
		 System.out.println("Please enter product name: ");
		 name= check.checkNameP("wrong input... \n please enter product name in less than 50 letters \n");
		 if (name.equals("0"))
			 return "quiting";
		 
		 
		System.out.println("Please enter area: ");
		 area= check.checkNameP("wrong input... \n please enter product area in less than 50 letters \n");
		 if (area.equals("0"))
			 return "quiting";
		
		 
		 System.out.println("Please enter price: ");
		 price= check.checkDoub("wrong input... \n please enter a positive price for the product \n");
		 if (price==0)
			 return "quiting"; 
		 
		
		 List<SupIdSearchable> manList= null;
			try {
				manList= bl.GetEntityBySupplier(supID, new Manufacturer());
			} catch (AccessDeniedException | ParseException e) {
				return "Problems with network (adPro74)";
			}
			if (!manList.isEmpty()){
				System.out.println("Please choose the number near the Manufacturer: ");
				int counter= 1;
				for (SupIdSearchable man: manList){
					System.out.println(counter+")");
					System.out.println((Manufacturer)man);
					counter++;
				}
				int opt= check.checkInt(0, manList.size(), "Please choose a number near on of the Manufacurers");
				Manufacturer man= (Manufacturer) manList.get(opt-1);
				manID= man.getID();
			}
			else
				return "no manufacturers";
			
			
			System.out.println("please enter the manufcurer product number or 0 to quit");
			manuNum= check.checkNameP("please enter a number or press 0 to quit");
			 if (manuNum.equals("0"))
				 return "quiting"; 
		 List<Discount> list= new LinkedList<Discount>();
		 Product pro= new Product(name, manuNum ,CatNum, manID,area, price, list);
		 System.out.println(pro.getManuNum());
		 try {
			 ans=bl.AddByAgreement(supID, signDate, pro);
		} catch (AccessDeniedException e) {
			return "problems with comunication... (addPro 97)";
		}
		 
		 System.out.println("if product have discount list press 1 to add discount or 0 to finish");
		  int opt= check.checkInt(0, 1, "wrong input... \n press 1 to add a discount \n press 0 to finish \n");
		 if (opt==1){
			 Product plus= new Product(pro);
			 addDiscount disc= new addDiscount();
			 while (opt==1){
				 list.add(disc.add());
				 System.out.println("if you have more discounts to add press 1 to finish press 0");
				 opt= check.checkInt(0, 1,"wrong input... \n press 1 to add another discount \n press 0 to finish \n");
			 }
			plus.setDiscount(list);	
			try {
				ans= bl.EditByAgreement(supID, signDate, pro, plus);
			} catch (AccessDeniedException e) {
				return "discount list have'nt been added";
			}
		 }
		 return ans;
	}
}	 
 	 
	