package GUI;
import Backend.*;
import DAL.AccessDeniedException;
import java.util.List;

import BL.IBL;
public class AdProductQun {
	private IBL bl;
	
	///////constructor///////////////
	public AdProductQun(IBL bl){
		this.bl= bl;
	}
	
	/////////////////add///////////////
	public ProductQun add(String supNum){
		ProductQun p=null;
		boolean flag= true;
		while(flag){
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("----------------------At any stage press 0 to quit--------------------");
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-ADD PRODUCT TO ORDER-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("Search a product: ");
			System.out.println("Press 1 to search product by name");
			System.out.println("Press 2 to search by area");
			System.out.println("Press 3 to view all supplier products");
			System.out.println("Press 0 to quit");
			int opt= check.checkInt(0, 3,"wrong input...\n"
					+ "Press 1 to search product by name\n"
					+ "Press 2 to search by area \n"
					+ "Press 3 to view all supplier products \n"
					+ "Press 0 to quit");
			switch(opt){
			case 0:
				flag= false;
				break;
			case 1:
				 p= printName(supNum);
				 if (p!=null)
					 flag=false;
				break;
			case 2:
				p= printArea(supNum);
				if (p!=null)
					 flag=false;
				break;
			case 3:
				p= viewAll(supNum);
				if (p!=null)
					 flag=false;
				break;
		}
		}
			return p;		
	}
			
	private ProductQun viewAll(String supNum) {
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("please choose one of the products by their number or press 0 t o return");
		List<AgreementEntity> list= null;
		try{
			list= bl.GetItAllByAgreement(supNum, null, new Product());
		}catch (AccessDeniedException e){
			System.out.println("network problem (adProQun 63");
			check.delay();
			return null;
		}
		System.out.println("");
		if (list==null||list.isEmpty())
			return null;
		int counter=1;
		for(AgreementEntity prod: list){
			System.out.println(counter+")");
			System.out.println((Product)prod);
			counter++;
		}
		System.out.println("Please choose a product to add by his number on the list or 0 to return");
		Product prod=null;
		ProductQun ans= null;
		double qun;
		int Num= check.checkInt(0,list.size() , "please choose a product or type 0 to return");
		if (Num==0){
			System.out.println("quiting...");
			check.delay();
			return null;
		}
				
		else{
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("please enter quantity");
			qun= check.checkDoub("wrong input... please enter a positive number for quntity \n");
			if (qun==0){
				System.out.println("quiting...");
				check.delay();
				return null;
			}
			prod= (Product) list.get(Num-1);
			ans= new ProductQun(prod, qun);
		}
				
		return ans;	
}

	public	ProductQun printName(String supNum1){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				System.out.println("please enter name to search:");
				String toSearch="";
				List<Product> list= null;
				boolean flag= true;
				while (flag){	
					toSearch= check.checkName("wrong input.... \n please enter a word to search shorter then 30 letters \n");
					if (toSearch.equals("0")){
						System.out.println("quiting...");
						check.delay();
						flag=false;
						return null;
					}
							
					try{
						list= bl.GetProductByName(supNum1,null, toSearch);
					}catch (AccessDeniedException e){
						System.out.println("network problems (adProQun 114");
						check.delay();
						return null;
					}
					if (list==null||list.isEmpty()){
						System.out.println("no products with this word");
						System.out.println("press 0 to quit");
						System.out.println("press 1 to search another word");
						int opt= check.checkInt(0, 1, "please choose 0 to quit or 1 to search");
						if (opt==0){
							System.out.println("quiting...");
							check.delay();
							flag=false;
							return null;
						}
					}else
						flag=false;
						
				}
				int counter=1;
				for(Product prod: list){
					System.out.println(counter+")");
					System.out.println(prod);
					counter++;
				}
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				System.out.println("Please choose a product to add by his number on the list or 0 to return");
				Product prod=null;
				ProductQun ans= null;
				double qun;
				int Num= check.checkInt(0,list.size() , "please choose a product or type 0 to return");
				if (Num==0){
					System.out.println("quiting...");
					check.delay();
					return null;
				}
						
				else{
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					System.out.println("please enter quantity");
					qun= check.checkDoub("wrong input... please enter a positive number for quntity \n");
					if (qun==0){
						System.out.println("quiting...");
						check.delay();
						return null;
					}
						
					prod= list.get(Num-1);
					ans= new ProductQun(prod, qun);
				}
						
				return ans;	
		}
				
		
			
		public ProductQun printArea(String supNum1){
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("please enter area to search:");
			String toSearch="";
			List<Product> list= null;
			boolean flag= true;
			while (flag){	
				toSearch= check.checkName("wrong input.... \n please enter a word to search shorter then 30 letters \n");
				if (toSearch.equals("0")){
					System.out.println("quiting...");
					check.delay();
					flag=false;
					return null;
				}
						
				try{
					list= bl.GetProductByArea(supNum1,null, toSearch);
				}catch (AccessDeniedException e){
					System.out.println("network problems (adProQun 114");
					check.delay();
					return null;
				}
				if (list==null||list.isEmpty()){
					System.out.println("no products with this word");
					System.out.println("press 0 to quit");
					System.out.println("press 1 to search another word");
					int opt= check.checkInt(0, 1, "please choose 0 to quit or 1 to search");
					if (opt==0){
						System.out.println("quiting...");
						check.delay();
						flag=false;
						return null;
					}
				}else
					flag=false;
					
			}
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			int counter=1;
			for(Product prod: list){
				System.out.println(counter+")");
				System.out.println(prod);
				counter++;
			}
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("Please choose a product to add by his number on the list or 0 to return");
			Product prod=null;
			ProductQun ans= null;
			double qun;
			int Num= check.checkInt(0,list.size() , "please choose a product or type 0 to return");
			if (Num==0){
				System.out.println("quiting...");
				check.delay();
				return null;
			}
					
			else{
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				System.out.println("please enter quantity");
				qun= check.checkDoub("wrong input... please enter a positive number for quntity \n");
				if (qun==0){
					System.out.println("quiting...");
					check.delay();
					return null;
				}
					
				prod= list.get(Num-1);
				ans= new ProductQun(prod, qun);
			}
					
			return ans;	
	}
				
		
				
				
			
		
}
	


