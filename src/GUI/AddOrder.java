package GUI;
import java.util.*;
import java.text.ParseException;

import BL.IBL;
import Backend.*;

import DAL.AccessDeniedException;


public class AddOrder {
	private IBL bl;
	
	
	public AddOrder(IBL bl){
		this.bl= bl;
		
	}
	
	public String add(String supNum){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-ADD NEW ORDR*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("----------------------At any stage press 0 to quit--------------------");
		
		
		//gets current date
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		//gets current time
		java.sql.Time time = new java.sql.Time(Calendar.getInstance().getTime().getTime());
		
		//creating empty order
		double price=0;
		List<ProductQun> productsList= new LinkedList<ProductQun>();
		int orderID=0;
		try{
			orderID= bl.getMaxIdOrder()+1;
		}catch(AccessDeniedException e){
			return "not added... (addOr40)";
		}
		
		Order or= new Order(orderID, supNum, date, time, price, productsList);
		try {
			//add order to data base
			bl.Add(or);
		} catch (AccessDeniedException e) {
			return "network problems... not added (addOrder42)";
		}
		
		System.out.println("Start choosing Products!");
		System.out.println("Press 1 to add/search Product");
		System.out.println("Press 0 to finish order");
		
		AdProductQun addPro= new AdProductQun(bl);
		ProductQun proQun= null;
		Order temp=null;
		
		int opt= check.checkInt(0, 1, "worng input... \n press 1 to add/search Product \n press 0 to finisg order \n");
		while (opt==1){
			
			
			proQun= addPro.add(supNum);// make a product quantity object
			
			if (proQun!=null){
				try{
					//tries to add to order
					boolean ans= bl.UpdateProductInOrder(orderID, supNum, proQun, true);
					if (!ans)
						System.out.println("not added....");
				} catch (AccessDeniedException e) {
					return "network problems... not added (addOrder62)";
				} 
			System.out.println("to add another product press 1");
			System.out.println("to finish order press 0");
			opt = check.checkInt(0, 1, "wrong input... \n to add another product press 1 \n to finish order press 0\n ");
			}
			else{
				System.out.println("trouble with adding product");
				System.out.println("to add another product press 1");
				System.out.println("to finish order press 0");
				opt = check.checkInt(0, 1, "wrong input... \n to add another product press 1 \n to finish order press 0\n ");
			}
			try {
				 temp=(Order) bl.SearchByID(Integer.toString(orderID), new Order());
			} catch (NumberFormatException | AccessDeniedException | ParseException e) {
				return "problem with uproudct list(addOrder 86)";
			}
			List<ProductQun> proList= temp.getList();
			System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
			for(ProductQun pro: proList){
				System.out.println(pro);
				System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
			}
			System.out.println("order until now...");
		}
		//calculate price
		Order order2= new Order();
		try {
			Order order=(Order) bl.SearchByID(Integer.toString(orderID), new Order());
			 order2= new Order(order);
			order2.getPrice();
			
			bl.Edit(order, order2);
		} catch (NumberFormatException | AccessDeniedException | ParseException e) {
			return "problem with updating price (addOrder 83)";
		}
		if (order2.getProductsList().isEmpty()){
			try {
				bl.DeleteEntity(order2);
			} catch (AccessDeniedException e) {
				System.out.println("empty order added addorder 112");
			}
		}

		
		
		return null;
		
	}
	
	

}
