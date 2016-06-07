package Backend;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public class Agreement implements Entity,SupIdSearchable 
{
	public enum daysOfWeek{SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY};
	public enum deliveryType{PICKUP, DELIVERY};
	public enum orderType{BYORDER, BYDAYS};
	private String supNum;
	private Date signDate;
	private Date validDate;
	private deliveryType delivery;
	private orderType order;
	private List<daysOfWeek> deliveryDays;
	private List<Product> productList;
	
	////////////////////
	////Constructors////
	////////////////////
	
	public Agreement()
	{
		this.supNum= null;
		this.signDate= null;
		this.validDate= null;
		this.delivery= null;
		this.order= null;
		this.deliveryDays= new LinkedList<daysOfWeek>();
		this.productList= new LinkedList<Product>();	
	}
	
	public Agreement(String supNum, Date signDate, Date validDate, deliveryType delivery, orderType order, List<daysOfWeek> deliveryDays, List<Product> productList){
		this.supNum= supNum;
		this.signDate= signDate;
		this.validDate= validDate;
		this.delivery= delivery;
		this.order= order;
		this.deliveryDays= deliveryDays;
		this.productList= productList;
	}
	
	public Agreement(Agreement other ){
		supNum= other.supNum;
		signDate= other.signDate;
		validDate= other.validDate;
		delivery= other.delivery;
		order= other.order;
		deliveryDays= other.deliveryDays;
		productList= other.productList;
	}
	
	///////////////
	////Getters////
	///////////////
	
	public String getSupNum(){
		return supNum;
	}
	
	public Date getSignDate(){
		return signDate;
	}
	
	public Date getValidDate(){
		return validDate;
	}
	
	public deliveryType getDeliveryType(){
		return delivery;
	}
	
	public orderType getOrderType(){
		return order;
	}
	
	public List<daysOfWeek> getDeliveryDays(){
		return deliveryDays;
	}
	
	public List<Product> getProductList(){
		return productList;
	}
	public boolean isValid(){
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		return date.before(validDate);
	}

	//////////////
	///visitors///
	//////////////
	
	@Override
	public String addVisit(Entity_BL en) throws AccessDeniedException 
	{
		return en.addVisit(this);
	}

	@Override
	public String EditVisit(Entity_BL en, Entity sub) throws AccessDeniedException
	{
		return en.EditVisit(this,(Agreement)sub);
	}

	@Override
	public void DeleteVisit(Entity_BL en) throws AccessDeniedException 
	{
		en.DeleteVisit(this);
	}
	
	/////////////
	///Setters///
	/////////////
	
	


	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public void setDelivery(deliveryType delivery) {
		this.delivery = delivery;
	}

	public void setOrder(orderType order) {
		this.order = order;
	}

	public void setDeliveryDays(List<daysOfWeek> deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public void setSupNum(String supNum){
		this.supNum= supNum;
	}
	
	public void setSignDate(Date signDate){
		this.signDate= signDate;
	}
	
	
	
	//ToString.....
	public String toString(){
		/*String products="";
		int i=1;
		for(Product pro: productList){
			products=products+i+") "+pro.toString()+"\n";
			i++;
		}*/
		String temp="";
		if (!deliveryDays.isEmpty()){
			 temp="Supplier number is: "+supNum+" \n"
			+"Agreement sign date is: "+ signDate+" \n"
			+"Agreement validation date is until: "+ validDate+" \n"
			+"Deliver type is: "+ delivery+" \n"
			+"Order type is: "+ order+" \n"
			+"Delivery days are: "+deliveryDays+"\n";
		}else{
			temp="Supplier number is: "+supNum+" \n"
					+"Agreement sign date is: "+ signDate+" \n"
					+"Agreement validation date is until: "+ validDate+" \n"
					+"Deliver type is: "+ delivery+" \n"
					+"Order type is: "+ order+" \n";
		}
		return temp;
	}

	@Override
	public List<SupIdSearchable> SearchBySupIdVisit(String ID,Entity_BL en) throws AccessDeniedException {
		return en.GetEntityBySupplier(ID, this);
	}

	@Override
	public List<Entity> GetItAll(Entity_BL entity) throws AccessDeniedException {
		return entity.GetItAll(this);
	}

}
