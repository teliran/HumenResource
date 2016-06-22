package storageBackend;

public class Stock {
	private int index;
	private int prod_id;
	private int supplier;
	private int amount;
	private String expiered_day;
	private boolean isRemove;
	
	public int getIndex(){
		return index;
	}
	public void setIndex(int index){
		this.index=index;
	}
	public int getProd_id(){
		return prod_id;
	}
	public void setProd_id(int prod_id){
		this.prod_id=prod_id;
	}
	public int getSupplier(){
		return supplier;
	}
	public void setSupplier_id(int supplier){
		this.supplier=supplier;
	}
	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount){
		this.amount=amount;
	}
	
	public String getExpiered_day(){
		return expiered_day;
	}
	public void setExpiered_day(String expiered_day){
		this.expiered_day=expiered_day;
	}
	public boolean getIsRemove(){
		return isRemove;
	}
	public void setIsRemove(boolean isRemove){
		this.isRemove=isRemove;
	}
	
	
	public Stock(int index, int prod_id, int supplier, int amount, String expiered_day, boolean isRemove){
		this.index = index;
		this.prod_id = prod_id;
		this.supplier = supplier;
		this.amount = amount;
		this.expiered_day = expiered_day;
		this.isRemove = isRemove;
		
		
	}
	public Stock(Stock other){
		this.index = other.index;
		this.prod_id = other.prod_id;
		this.supplier = other.supplier;
		this.amount = other.amount;
		this.expiered_day = other.expiered_day;
		this.isRemove = other.isRemove;
		
		
	}
	public String toString(){
		String s = "Stock product details:\n";
		s=s + "Index Stock: "+ index+"\n";
		s=s + "Product ID: "+ prod_id+"\n";
		s=s + "Supplier: "+ supplier+"\n";
		s=s + "Amount: "+amount+"\n";
		s=s + "Expiered_day: "+expiered_day+"\n";
		s=s + "Remove: "+isRemove+"\n";
		
		return s;
	}
}
