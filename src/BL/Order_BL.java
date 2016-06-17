package BL;
import Backend.*;
import Backend.Agreement.daysOfWeek;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import DAL.AccessDeniedException;
import DAL.IDAL;
import transport.OrderDocument;
import transport.TransManager;

public class Order_BL {

private IDAL dal;
	
protected Order_BL(IDAL dal)
	{
		this.dal=dal;
	}
	

protected List <Order> ConvertEntityListToOrder(List<Entity> orderList)
	{
		List <Order> orderedList=new LinkedList<Order>();
		for(Entity order:orderList)
		{
			orderedList.add((Order)order);
		}
		return orderedList;
	}
	
	protected int getLastOrder(String supNum) throws AccessDeniedException, ParseException{
		List <SupIdSearchable> orders= dal.GetOrderBySupplier(supNum);
		Date max= new Date(0,0,1);
		int ans= -1;
		for(SupIdSearchable order: orders){
			if(((Order)order).getDate().after(max)){
				max= ((Order)order).getDate();
				ans= ((Order)order).getOrderID();
			}
		}
		
		return ans;
	}

	protected int validOrder(String supNum) throws AccessDeniedException{
		int orderNum= -1;
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		//Supplier sup= (Supplier) dal.SearchSupplierById(supNum);
		Agreement agr =(Agreement) dal.getLastAgreementBySupplier(supNum);
		List<daysOfWeek> days= agr.getDeliveryDays();
		Order order= (Order) dal.getLastOrderBySupplier(supNum);
		if (order!=null){
			Date orderDate= order.getDate();
			boolean flag= true;
			if (!days.isEmpty()){
				while(flag||!today.equals(orderDate)){
					int day= today.getDay();
					daysOfWeek dayWeek= null;
					switch(day){
						case 1:
							dayWeek= daysOfWeek.SUNDAY;
							break;
						case 2:
							dayWeek= daysOfWeek.MONDAY;
							break;
						case 3:
							dayWeek= daysOfWeek.TUESDAY;
							break;
						case 4:
							dayWeek= daysOfWeek.WEDNESDAY;
							break;
						case 5:
							dayWeek= daysOfWeek.THURSDAY;
							break;
						case 6:
							dayWeek= daysOfWeek.FRIDAY;
							break;
						case 7:
							dayWeek= daysOfWeek.SATURDAY;
							break;
					}
					if(days.contains(dayWeek))
						flag=false;
					today= new java.sql.Date( today.getTime() + 24*60*60*1000);
							
				}
				
			}
			if(flag)
				orderNum= order.getOrderID();
			
		}	
		
		return orderNum;
	}
	
	protected void takeOrder(List<String> manID, List<String> productManID, List<Double> quntity) throws AccessDeniedException
	{
		for(int i=0;i<manID.size();i++)
		{
			autoOrder(manID.get(i),productManID.get(i),quntity.get(i));
		}
	}
	
	protected void autoOrder(String manId,String proManId, double qun) throws AccessDeniedException{
		Agr_BL agrBL= new Agr_BL(dal);
		String supNum= agrBL.lowestPrice(manId, proManId, qun);
		Agreement agr= (Agreement) dal.getLastAgreementBySupplier(supNum);
		List <AgreementEntity> proList= dal.GetAllProductByAgreement(supNum, agr.getSignDate());
		Product product= null;
		for(AgreementEntity pro: proList){
			if(((Product)pro).getManID().equals(manId)&&((Product)pro).getManuNum().equals(proManId))
				product= (Product) pro;
		}
		ProductQun proQun= new ProductQun(product, qun);
		int orderNum= validOrder(supNum);
		if (orderNum>=0) // i had to an existing order, so the order already sent to transportation
			dal.UpdateProductInOrder(orderNum, supNum, proQun, true);
		else{

			//gets current date
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			
			//gets current time
			java.sql.Time time = new java.sql.Time(Calendar.getInstance().getTime().getTime());
			
			//creating empty order
			double price=0;
			List<ProductQun> productsList= new LinkedList<ProductQun>();
			int orderID=0;
			orderID= dal.getMaxIdOrder()+1;
			Order or= new Order(orderID, supNum, date, time, price, productsList);
			dal.AddOrder(or);
			dal.UpdateProductInOrder(orderID, supNum, proQun, true);
			TransManager.giveNewOrder(or, ((Supplier)dal.SearchSupplierById(supNum)).getArea());  
		}
	}

	String printOrder(Order order) throws AccessDeniedException{
		Supplier_BL sup_bl= new Supplier_BL(dal);
		String supNum= order.getSupNum();
		int phoneNum= sup_bl.getContactNum(supNum);
		String address= sup_bl.getAddress(supNum);
		String supName= sup_bl.getName(supNum);
		int orderNum= order.getOrderID();
		String date= order.getDate().toString();
		String ans= "Supplier name: "+supName+"; address: "+address+"; order number: "+orderNum+"\n";
		ans= ans+ "Supplier number: "+supNum+"; order date: "+date+"; contact phone: "+phoneNum+"\n";
		List <ProductQun> proList= order.getProductsList();
		int count=1;
		for(ProductQun pro: proList){
			String proName= pro.getPro().getName();
			String catNum= pro.getPro().getCatNum();
			double qun= pro.getQun();
			double price= pro.getPro().getPrice();
			List<Discount> disList= pro.getPro().getDisList();
			double discount =0;
			for(Discount dis: disList){
				if (qun>=dis.getQun()&& dis.getDisc()> discount)
					discount= dis.getDisc();
			}
			double totalPrice;
			if (discount==0)
				totalPrice= price*qun;
			else
				totalPrice= price*qun*discount/100;
			
			ans= ans+ count+") CAT num:"+ catNum+"; product name: "+proName+"; quantity: "
					+qun+"; price: "+price+"; discount: "+discount+"%; total price: "+totalPrice+"\n";
			count++;				
		}
		
		return ans;
	}
	
	private List <Order> byPrice(List <Order> orderList,String price1,String price2) throws AccessDeniedException, ParseException
	{
		double a,b;
		a=Double.parseDouble(price1);
		List<Order> orderedList=new LinkedList<Order>();
		if(orderList==null)
			orderList=ConvertEntityListToOrder(dal.GetAllOrder());
		if(price2!=null)
		{
			b=Double.parseDouble(price2);
			for(Order order:orderList)
			{
				if(order.getPrice()>=a && order.getPrice()<=b)
					orderedList.add(order);
			}
		}
		else
		{
			for(Order order:orderList)
			{
				if(order.getPrice()==a)
					orderedList.add(order);
			}
		}
		return orderedList;
	}
	
	private List <Order> byDate(List <Order> orderList,String val1,String val2) throws ParseException, AccessDeniedException
	{
		List<Order> orderedList= new LinkedList<Order>();
		if(orderList==null)
			orderList=ConvertEntityListToOrder(dal.GetAllOrder());
		java.sql.Date a,b;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(val1);
		a = new java.sql.Date(date.getTime());
		if(val2!=null)
		{
			date = sdf1.parse(val2);
			b = new java.sql.Date(date.getTime());
			for(Order order:orderList)
			{
				if(order.getDate().after(a) && order.getDate().before(b))
					orderedList.add(order);
			}
		}
		else
		{
			for(Order order:orderList)
			{
				if(order.getDate().compareTo(a)==0)
					orderedList.add(order);
			}
		}
		return orderedList;
	}
	
	protected List <Order> AdvOrderSearch(String field1, String field2, String value1,String value2) throws ParseException, AccessDeniedException{
		if(field1==null && field2==null)
		{
			return new LinkedList<Order>();
		}
		else if (field2==null)
		{
			switch(field1){
			case "product":return dal.AdvOrderSearch(value1, null);
			case "price":return byPrice(null, value1, null);
			case "date":return byDate(null, value1, null);
				default: return new LinkedList<Order>();
			}
		}
		else if(field1==null)
		{
			switch(field2){
			case "product":return dal.AdvOrderSearch(value2, null);
			case "price":return byPrice(null, value2, null);
			case "date":return byDate(null, value2, null);
				default: return new LinkedList<Order>();
			}
		}
		else
		{
			if(field1.equals("product"))
			{
			   if(field2.equals("product"))
				{
					return dal.AdvOrderSearch(value1, value2);
				}
				else if(field2.equals("price"))
				{
					List<Order> ordList=dal.AdvOrderSearch(value1, null);
					return byPrice(ordList, value2, null);
				}
				else
				{
					List<Order> ordList=dal.AdvOrderSearch(value1, null);
					return byDate(ordList, value2, null);
				}
			}
			else if(field1.equals("price"))
			{
				if(field2.equals("product"))
				{
					List<Order> ordList=dal.AdvOrderSearch(value2, null);
					return byPrice(ordList, value1, null);
				}
				else if(field2.equals("price"))
				{
					return byPrice(null, value1, value2);
				}
				else
				{
					List<Order> ordList= byDate(null,value2, null);
					return byDate(ordList, value1, null);
				}
			}
			else
			{
				if(field2.equals("product"))
				{
					List<Order> ordList=dal.AdvOrderSearch(value2, null);
					return byDate(ordList, value1, null);
				}
				else if(field2.equals("price"))
				{
					List<Order> ordList= byDate(null,value1, null);
					return byPrice(ordList, value2, null);
				}
				else
				{
					return byDate(null, value1, value2);
				}
			}
			
		}
	}
	
}
