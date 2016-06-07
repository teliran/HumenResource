package storageBackend;

public class Product {
	private int prod_id;
	private String name;
	private String manufactureID;
	private String manufactureProdID;
	private int department_id1;
	private int department_id2;
	private int department_id3;
	private int minimum_amount;
	private int total_amount;
	private int total_damaged;
	boolean isRemove;
	private String nextDateOrder;
	private int days;
	private int quntity;
	
	public int getProd_id(){
		return prod_id;
	}
	public void setProd_id(int prod_id){
		this.prod_id=prod_id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getManufactureID(){
		return manufactureID;
	}
	public void setManufactureID(String manufactureID){
		this.manufactureID=manufactureID;
	}
	public String getManufactureProdID(){
		return manufactureProdID;
	}
	public void setManufactureProdID(String manufactureProdID){
		this.manufactureProdID=manufactureProdID;
	}
	public int getDepartment_id1(){
		return department_id1;
	}
	public void setDepartment_id1(int department_id1){
		this.department_id1=department_id1;
	}
	public int getDepartment_id2(){
		return department_id2;
	}
	public void setDepartment_id2(int department_id2){
		this.department_id2=department_id2;
	}
	public int getDepartment_id3(){
		return department_id3;
	}
	public void setDepartment_id3(int department_id3){
		this.department_id3=department_id3;
	}
	public int getMinimum_amount(){
		return minimum_amount;
	}
	public void setMinimum_amount(int minimum_amount){
		this.minimum_amount=minimum_amount;
	}
	public int getTotal_amount(){
		return total_amount;
	}
	public void setTotal_amount(int total_amount){
		this.total_amount=total_amount;
	}
	public int getTotal_damaged(){
		return total_damaged;
	}
	public void setTotal_damaged(int total_damaged){
		this.total_damaged=total_damaged;
	}
	public boolean getIsRemove(){
		return isRemove;
	}
	public void setIsRemove(boolean isRemove){
		this.isRemove=isRemove;
	}
	public String getNextDateOrder(){
		return nextDateOrder;
	}
	public void setNextDateOrder(String nextDateOrder){
		this.nextDateOrder=nextDateOrder;
	}
	public int getDays(){
		return days;
	}
	public void setDays(int days){
		this.days=days;
	}
	public int getQuntity(){
		return quntity;
	}
	public void setQuntity(int quntity){
		this.quntity=quntity;
	}
	public Product(int prod_id, String name, String manufactureID,  String manufactureProdID, int department_id1, int department_id2, int department_id3 , 
			int minimum_amount, int total_amount, int total_damaged, String nextDateOrder, int days, int quntity , boolean isRemove){
		this.prod_id = prod_id;
		this.name = name;
		this.manufactureID = manufactureID;
		this.manufactureProdID = manufactureProdID;
		this.department_id1 = department_id1;
		this.department_id2 = department_id2;
		this.department_id3 = department_id3;
		this.minimum_amount = minimum_amount;
		this.total_amount = total_amount;
		this.total_damaged = total_damaged;
		this.isRemove = isRemove;
		this.nextDateOrder = nextDateOrder;
		this.days = days;
		this.quntity = quntity;
		
	}
	public Product(){
		this.prod_id = 0;
		this.name = "";
		this.manufactureID = "";
		this.manufactureProdID = "";
		this.department_id1 = 0;
		this.department_id2 = 0;
		this.department_id3 = 0;
		this.minimum_amount = 0;
		this.total_amount = 0;
		this.total_damaged = 0;
		this.isRemove = false;
		this.nextDateOrder = "";
		this.days = 0;
		this.quntity = 0;
		
	}
	public Product(Product other){
		this.prod_id = other.prod_id;
		this.name = other.name;
		this.manufactureID = other.manufactureID;
		this.manufactureProdID = other.manufactureProdID;

		this.department_id1 = other.department_id1;
		this.department_id2 = other.department_id2;
		this.department_id3 = other.department_id3;
		this.minimum_amount = other.minimum_amount;
		this.total_amount = other.total_amount;
		this.total_damaged = other.total_damaged;
		this.isRemove = other.isRemove;
		this.nextDateOrder = other.nextDateOrder;
		this.days = other.days;
		this.quntity = other.quntity;
	}
	public String toString(){
		String s = "Product details:\n";
		s=s + "Product ID: "+ prod_id+"\n";
		s=s + "Product Name: "+name+"\n";
		s=s + "Manufacture ID: "+manufactureID+"\n";
		s=s + "Manufacture prod ID: "+manufactureProdID+"\n";
		s=s + "First Department ID: "+department_id1+"\n";
		s=s + "Second Department ID: "+department_id2+"\n";
		s=s + "Theird Department ID: "+department_id3+"\n";
		s=s + "Minimum amount: "+minimum_amount+"\n";
		s=s + "Total amount:"+total_amount+"\n";
		s=s + "Total damaged:"+total_damaged+"\n";
		s=s + "Next period order: "+nextDateOrder+"\n";
		s=s + "evry x days x="+days+"\n";
		s=s + "Quntity:"+quntity+"\n";
		s=s + "Remove: "+isRemove+ "\n";
		return s;
	}
	
	
}
