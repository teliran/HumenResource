package GUI;
import BL.IBL;
import Backend.*;
import DAL.AccessDeniedException;

import java.text.ParseException;
import java.util.*;
public class MakeOrder {
	private IBL da;
	public MakeOrder(IBL da){
		this.da= da;
	}
	
	public void run(){
		//choosing supplier
		System.out.println("========================ORDER MENU==========================");
		System.out.println("Press 1 to choose supplier");
		System.out.println("Press 0 to go back");
		int opt= check.checkInt(0, 1, "wrong input... \n"
				+ "Press 1 to choose supplier \n"
				+ "Press 0 to go back \n");
	
		openScreen os= new openScreen(da);
		Supplier sup= new Supplier();
		
	//quits
		if (opt==0){
			os.run();
			return;
		}
			
		System.out.println("Please choose supplier by his number from the list:");
		List<Entity> list= null;
		
		//get suppliers list
		try {
			list= da.GetItAll(sup);
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network trouble (makeOrder 35)");
			os.run();
			return;
		}
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		
		//prints suppliers list
		int counter= 1;
		for (Entity ent: list){
			System.out.println(counter+")");
			System.out.println((Supplier) ent);
			counter++;
		}
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		//checks option chose
		int num= check.checkInt(0, list.size(), "please choose a supplier by his number or press 0 to go back");
		if (num==0){
			System.out.println("quiting");
			check.delay();
			os.run();
			return;
		}
		
		//gets supplier number
		String supNum=((Supplier)list.get(num-1)).getSupNum();
		List<SupIdSearchable> listCont= new LinkedList<SupIdSearchable>();
		try {
			 listCont =da.GetEntityBySupplier(supNum, new Contact());
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("troubles with getting suppliers...");
			check.delay();
			os.run();
			return;
		}
		if (listCont.isEmpty()){
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("supplier have no contacts and order can't be made by him...");
			System.out.println("press 0 to go back...");
			System.out.println("press 1 to choose another supplier...");
			num= check.checkInt(0, 1, "please choose one of the options above");
			if (num==1)
				run();
			else{
				os.run();
				return;
			}
				
		}
		else
			options(supNum);
		return;
	}
		private void options( String supNum){
		//asks for options
		System.out.println("=======================ORDERS OPTIONS==========================");
		System.out.println("press 1 to  add/remove to products to an existing order");
		System.out.println("press 2 to start new order");
		System.out.println("press 3 to view orders");
		System.out.println("press 4 to delete order");
		System.out.println("press 0 to go back");
		int opt= check.checkInt(0, 4, "wrong input... \n"
				+ "press 1 to  add to products to an existing order \n"
				+ "press 2 to start new order \n"
				+ "press 3 to view orders \n"
				+ "press 4 to delete order \n"
				+ "press 0 to go back \n");
		switch(opt){
		case 0:
			run();
			break;
		case 1:
			addOr(supNum);
			break;
		case 2:
			newOr(supNum);
			break;
		case 3:
			viewOr(supNum);
			break;
		case 4:
			delOr(supNum);
			break;
			
		}
		
		
		
	}

	
	
	

	private void delOr(String supNum) {
		System.out.println("====================================REMOVE ORDER==========================");
		List<SupIdSearchable> list= null;
		try {
			list= da.GetEntityBySupplier(supNum, new Order());
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network troubles.. (makeOrder 189)");
			check.delay();
			run();
			return;
		}
		if (list.isEmpty()){
			System.out.println("Supplier has no orders");
			check.delay();
			options(supNum);
			return;
		}
		
		//prints list of order to choose from
		System.out.println("please choose an order from list by the number next to it");
		int counter= 1;
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		for(SupIdSearchable ent: list){
			System.out.println(counter+")");
			System.out.println("Order number: "+((Order) ent).getOrderID());
			System.out.println("Date: "+((Order) ent).getDate());
			System.out.println("Time: "+((Order) ent).getTime());
			counter++;
		}
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		
		//takes options
		int num= check.checkInt(0, list.size(), "please choose the nuber next to an order to delete or 0 to go back");
		
		//quits
		if (num==0){
			System.out.println("quiting");
			check.delay();
			options(supNum);
		}
		Order order= (Order) list.get(num-1);
	
		//displays order
		try {
			da.DeleteEntity(order);
		} catch (AccessDeniedException e) {
			System.out.println("order not deleted... (Makeorder 159)");
		}
		System.out.println("press 0 to go back");
		check.checkInt(0, 0, "please perss 0 t o go back");
		options(supNum);
			
		}

	private void newOr(String supNum) {
		System.out.println("=================================NEW ORDER===========================");
		//creates add order "interface"
		AddOrder orderadd= new AddOrder(da);
		
		//gets answer
		String ans= orderadd.add(supNum);
		if (ans!=null)
			System.out.println(ans);
		else
			System.out.println("order added");
		check.delay();
		options(supNum);
			
	}

	
	
	
	private void addOr(String supNum) {
		System.out.println("===============================ADD / REMOVE==================================");
		// checks if supplier have any orders
		List<SupIdSearchable> list= null;
		try {
			list= da.GetEntityBySupplier(supNum, new Order());
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("network troubles.. (makeOrder 113)");
			check.delay();
			options(supNum);
			return;
		}
		if (list.isEmpty()){
			System.out.println("Supplier has no orders");
			check.delay();
			options(supNum);
			return;
		}
		
		//prints list of order to choose from
		System.out.println("please choose an order from list by the number next to it");
		int counter= 1;
		for(SupIdSearchable ent: list){
			System.out.println(counter+")");
			System.out.println("Order number: "+((Order) ent).getOrderID());
			System.out.println("Date: "+((Order) ent).getDate());
			System.out.println("Time: "+((Order) ent).getTime());
			counter++;
		}

		
		//takes options
		
		int num= check.checkInt(0, list.size(), "please choose the nuber next to an order or 0 to go back");
		
		//quits
		if (num==0){
			System.out.println("quiting");
			check.delay();
			options(supNum);
			return;
		}
		
		//gets order number from list
		
		int orderNum= ((Order)list.get(num-1)).getOrderID();
		System.out.println("press 1 to add product to the order");
		System.out.println("press 2 to remove product from order");
		System.out.println("press 0 to go back");
		int opt=check.checkInt(0, 2, "please choose one of the options above...");
		while (opt!=0){
			Order order= null;
			try{
				 order=(Order) da.SearchByID(Integer.toString(orderNum),new Order());
			}catch(AccessDeniedException | NumberFormatException | ParseException e){
				System.out.println("network problems... makeOrder258");
				check.delay();
				options(supNum);
				return;
			}
			
			
			if (opt==1){
				//creates AdProductQun "interface" to make products
				AdProductQun addPro= new AdProductQun(da);
				ProductQun product= null;
				//creates product
				product= addPro.add(supNum);
				if (product==null)
					System.out.println("product not added, troubles with creating (makeOrder 263)");
				//tries to add it to order
				else{
					try{
						boolean ans=da.UpdateProductInOrder(orderNum, supNum, product, true);
						
						if (ans)
							System.out.println("added to order");
						else
							System.out.println("not added to order (makeOrder 282)");
					}catch(AccessDeniedException e){
						System.out.println("not added to order (makeOrder 284)");
					}
				}
			}else{
				
				List <ProductQun> proList =order.getList();
				if (proList.isEmpty())
					System.out.println("no items to delete");
				else{
					int index=1;
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					System.out.println("please choose the number next to the product you want to remove or 0 to go back..");
					for(ProductQun pro: proList){
						System.out.println(index+")");
						System.out.println(pro);
						System.out.println("-------------------------------------------------------");
						index++;
					}
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					int toDel=check.checkInt(0, list.size(), "please enter a number of a product or 0 to quit");
					if (toDel!=0){
						
						ProductQun pro=proList.get(toDel-1);
						try{
							boolean ans=da.UpdateProductInOrder(orderNum, supNum, pro, false);
							
							if (ans)
								System.out.println("deleted from order");
							else
								System.out.println("not deleted from order (makeOrder 303)");
						}catch(AccessDeniedException | NumberFormatException e){
							System.out.println("not deleted from order (makeOrder 305)");
						}
						
					}
					
				}
			}
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println(" press 1 to add another product to the order");
			System.out.println("press 2 to remove another product from order");
			System.out.println("press 0 to go back");
			opt=check.checkInt(0, 2, "please choose one of the options above...");
			
		}
		
		
		
		
		
		try {
			Order order1=(Order) da.SearchByID(Integer.toString(orderNum),new Order());
			Order order2=new Order(order1);
			order2.getPrice();
			da.Edit(order1, order2);
			if (order2.getList().isEmpty())
				da.DeleteEntity(order2);
		} catch (NumberFormatException | AccessDeniedException | ParseException e) {
			System.out.println("price not updated, makeOrder 331");
			check.delay();
		}
		
		options(supNum);
		return;
	}

	private void viewOr(String supNum) {
		// checks if supplier have any orders
		System.out.println("=============================VIEW ORDER====================================");
				List<SupIdSearchable> list= null;
				try {
					list= da.GetEntityBySupplier(supNum, new Order());
				} catch (AccessDeniedException | ParseException e) {
					System.out.println("network troubles.. (makeOrder 347)");
					check.delay();
					run();
					return;
				}
				if (list.isEmpty()){
					System.out.println("Supplier has no orders");
					check.delay();
					options(supNum);
					return;
				}
				
				//prints list of order to choose from
				System.out.println("please choose an order from list by the number next to it");
				int counter= 1;
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				for(SupIdSearchable ent: list){
					System.out.println(counter+")");
					System.out.println("Order number: "+((Order) ent).getOrderID());
					System.out.println("Date: "+((Order) ent).getDate());
					System.out.println("Time: "+((Order) ent).getTime());
					counter++;
				}
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				
				//takes options
				int num= check.checkInt(0, list.size(), "please choose the nuber next to an order or 0 to go back");
				
				//quits
				if (num==0){
					System.out.println("quiting");
					check.delay();
					options(supNum);
					return;
				}
				Order order= (Order) list.get(num-1);
			
				//displays order
				order.getPrice();
				try {
					System.out.println(da.printOrder(order));
				} catch (AccessDeniedException e) {
					System.out.println("ttouble with viewing order....");
				}
				
				
				System.out.println("press 0 to go back");
				int quit= check.checkInt(0, 0, "please prss 0 t o go back");
				options(supNum);
				return;
				
				
		
	}
}
