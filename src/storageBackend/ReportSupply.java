package storageBackend;

public class ReportSupply {
	
	private int prod_id;
	private String prod_name;
	
	private int supplier;
	private String expiered_day;
	private int amount;
	private String date_of_export_from_warehouse;
	
	
	
	public int getProd_id(){
		return prod_id;
	}
	public void setProd_id(int prod_id){
		this.prod_id=prod_id;
	}
	public String getProd_name(){
		return prod_name;
	}
	public void setProd_name(String prod_name){
		this.prod_name=prod_name;
	}
	
	public int getSupplier(){
		return supplier;
	}
	public void setSupplier_id(int supplier){
		this.supplier=supplier;
	}
	public String getExpiered_day(){
		return expiered_day;
	}
	public void setExpiered_day(String expiered_day){
		this.expiered_day=expiered_day;
	}
	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount){
		this.amount=amount;
	}
	public String getDate_of_export_from_warehouse(){
		return date_of_export_from_warehouse;
	}
	public void setDate_of_export_from_warehouse(String date_of_export_from_warehouse){
		this.date_of_export_from_warehouse=date_of_export_from_warehouse;
	}
	
	
	
	
	
	public ReportSupply(int prod_id, String prod_name, int supplier, String expiered_day, int amount, String date_of_export_from_warehouse){
		
		this.prod_id = prod_id;
		this.prod_name = prod_name;
		this.supplier = supplier;
		
		this.expiered_day = expiered_day;
		this.amount = amount;
		this.date_of_export_from_warehouse = date_of_export_from_warehouse;
		
		
	}
	public ReportSupply(ReportSupply other){
		this.prod_id = other.prod_id;
		this.prod_name = other.prod_name;
		this.supplier = other.supplier;
		
		this.expiered_day = other.expiered_day;
		this.amount = other.amount;
		this.date_of_export_from_warehouse = other.date_of_export_from_warehouse;
		
		
	}
	public String toString(){
		String s = "Stock product details:\n";
		
		s=s + "Product ID: "+ prod_id+"\n";
		s=s + "Product Name: "+ prod_name+"\n";
		s=s + "Supplier: "+ supplier+"\n";
		s=s + "Expiered day: "+expiered_day+"\n";
		s=s + "Amount: "+amount+"\n";
		s=s + "Date of export from warehouse: "+date_of_export_from_warehouse+"\n";
		
		return s;
	}
}
