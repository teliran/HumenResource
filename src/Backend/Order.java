package Backend;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public class Order implements Entity,IdSearchable,SupIdSearchable
{
	private int orderID;
	private String supNum;
	private Date date;
	private Time time;
	private double totalPrice;
	private List<ProductQun> productsList;

	//////////////////
	///Constructors///
	//////////////////
	
	public Order()
	{
		orderID=0;
		supNum=null;
		date= null;
		time= null;
		totalPrice=0;
		productsList= null;
	}

	
	public Order(int orderID, String supNum, Date date, Time time, double totalPrice, List<ProductQun> productsList)
	{
		this.orderID= orderID;
		this.supNum= supNum;
		this.date= date;
		this.time= time;
		this.totalPrice= totalPrice;
		this.productsList= productsList;
	}
	
	public Order(Order other){
		orderID= other.orderID;
		supNum= other.supNum;
		date= other.date;
		time= other.time;
		totalPrice= other.totalPrice;
		productsList= other.productsList;
	}
	
	/////////////
	///Getters///
	/////////////
	

	public double getTotalPrice() {
		return totalPrice;
	}

	public List<ProductQun> getProductsList() {
		return productsList;
	}

	public int getOrderID()
	{
		return orderID;
	}

	public String getSupNum(){
		return supNum;
	}

	public Date getDate(){
		return date;
	}

	public Time getTime(){
		return time;
	}

	public double getPrice(){
		double temp=0;
		for (ProductQun proQ: productsList){
			double qun= proQ.getQun();
			Product pro= proQ.getPro();
			double price= pro.getPrice();
			List<Discount> disList= pro.getDisList();
			
			if (disList.isEmpty())
				temp=temp+qun*price;
			else{
				double max=1;
				boolean flag= false;
				for (Discount dis: disList){
					if (dis.getQun()<=qun){
						max=dis.getDisc();
						flag= true;
					}
						
				}
				if (flag)
					max= max/100;
				temp= temp+qun*price*max;
			}
		}
		totalPrice= temp;
		return totalPrice;
	}

	public List<ProductQun> getList(){
		return productsList;
	}

	/////////////
	///Setters///
	/////////////
	

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public void setSupNum(String supNum) {
		this.supNum = supNum;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProductsList(List<ProductQun> productsList) {
		this.productsList = productsList;
	
	}
	//ToString????
	public  String toString(){
		String products="";
		int i=1;
		for(ProductQun pro: productsList){
			products=products+i+") "+pro.getPro().toString()+"\n"
					+"quantity: "+pro.getQun()+"\n";
			i++;
		}
		String temp="The order ID is: "+ orderID+"\n"
				+"the supplier number is: "+supNum+"\n"
				+"Date: "+date+" Time: "+time+"\n"+
				"products list: \n"+products+
				"\n Total Price is:"+totalPrice;
		return temp;
	}
	
	

	///////////////////
	///visit pattern///
	///////////////////

	@Override
	public String addVisit(Entity_BL en) throws AccessDeniedException {
		return en.addVisit(this);
	}

	@Override
	public String EditVisit(Entity_BL en, Entity sub) throws AccessDeniedException {
		return en.EditVisit(this,(Order)sub);
	}

	@Override
	public void DeleteVisit(Entity_BL en) throws AccessDeniedException {
		en.DeleteVisit(this);
		
	}


	@Override
	public IdSearchable SearchByIdVisit(String ID, Entity_BL en) throws NumberFormatException, AccessDeniedException, ParseException  {
		return en.SearchByID(Integer.parseInt(ID), this);
	}

	@Override
	public List<SupIdSearchable> SearchBySupIdVisit(String ID, Entity_BL en) throws AccessDeniedException, ParseException  {
		return en.GetEntityBySupplier(ID, this);
	}

	@Override
	public List<Entity> GetItAll(Entity_BL entity) throws AccessDeniedException, ParseException {
		return entity.GetItAll(this);
	}
}
