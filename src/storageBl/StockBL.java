package storageBl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;






import java.util.Random;

import BL.Entity_BL;
import BL.IBL;
import Backend.Order;
import Backend.ProductQun;
import DAL.AccessDeniedException;
import storageBackend.Product;
import storageBackend.ReportSupply;
import storageBackend.Stock;
import storageDal.IDAL;

public class StockBL {
protected IDAL itsDAL;
private BL.IBL b;
	public String erea = "North";
	/////////////////
	///Constructor///
	/////////////////
	public StockBL(IDAL dal, BL.IBL b)
    {
		this.b=b;
          itsDAL = dal; 
    }
	public void getExpiried(){
		ArrayList<Product> products =itsDAL.findAllProducts();
		for (Product p : products) {
				int num = itsDAL.findNumOfExpiredProduct(p.getProd_id());	
				if(num>0)
				System.out.println(num +" of "+ p.getName() + " is expiried");
			
			}
		
	}
	
	public void getMinimum(){
		ArrayList<Product> products =itsDAL.findAllProducts();
		for (Product p : products) {
			if(p.getMinimum_amount()>p.getTotal_amount()- p.getTotal_damaged()-itsDAL.findNumOfExpiredProduct(p.getProd_id()))
				System.out.println(""+ p.getName() +" need " + p.getMinimum_amount() + " have " + (p.getTotal_amount()- p.getTotal_damaged()-itsDAL.findNumOfExpiredProduct(p.getProd_id())));
		}
	}
	
	
	public ArrayList<Stock> spesifiedStok(int prodID) {
		
		ArrayList<Stock> s = itsDAL.findStockByProductID(prodID);
		return s;
	}
	public void getallstock(){
		ArrayList<Stock> s = itsDAL.findAllStocks();
		System.out.println(s.size());
		for (Stock st : s) {
			System.out.println(st.toString());
		}
	}
	public void take(int choise, int num) {
		Product p = itsDAL.findProductByID(choise);
		if (p!=null && p.getIsRemove() == false){
		if(p.getTotal_amount() - p.getTotal_damaged() - itsDAL.findNumOfExpiredProduct(choise)>=num){
		itsDAL.UpdateTotalAmountProduct(-num, choise);
		System.out.println(num + " of " + p.getName() + " where saccessfuly taken");
		ArrayList<Stock> stock = itsDAL.findStockByProductIDAndNotExpiried(choise);
		for (Stock s : stock) {
			if(s.getAmount()>=num)
			{
				int index = s.getIndex();
				itsDAL.UpdateAmountProduct(index, num);
				num =0;
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				ReportSupply r = new ReportSupply(choise, p.getName(), s.getSupplier(), s.getExpiered_day(), -num, dateFormat.format(cal.getTime()).toString());
				itsDAL.AddReportSupply(r);
				System.out.println(r.toString());
				break;
			}
			else{
				num = num - s.getAmount();
				int index = s.getIndex();
				itsDAL.UpdateAmountProduct(index, s.getAmount());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				ReportSupply r = new ReportSupply(choise, p.getName(), s.getSupplier(), s.getExpiered_day(), -s.getAmount(), dateFormat.format(cal.getTime()).toString());
				itsDAL.AddReportSupply(r);
				System.out.println(r.toString());
				
			}
		}
		
		}
		else{
			System.out.println("dosn't have enuf products");
		}
		p = itsDAL.findProductByID(choise);
		if(p.getMinimum_amount()>p.getTotal_amount() - p.getTotal_damaged() - itsDAL.findNumOfExpiredProduct(choise))
			System.out.println("product under minimum stock");
		}
		else{
			System.out.println("product was no found");
		}
		
	}
	public void TakeToDameged(int choise, int num){
		ArrayList<Stock> stock = itsDAL.findStockByProductIDAndNotExpiried(choise);
		Product p = itsDAL.findProductByID(choise);
		if(p.getTotal_amount() - p.getTotal_damaged() - itsDAL.findNumOfExpiredProduct(choise)>=num){
		for (Stock s : stock) {
			if(s.getAmount()>=num)
			{
				int index = s.getIndex();
				itsDAL.UpdateAmountProduct(index, num);
				num = 0;
				
				break;
			}
			else{
				num = num - s.getAmount();
				int index = s.getIndex();
				itsDAL.UpdateAmountProduct(index, s.getAmount());
				
				
			}
		}
		}
		else{
			System.out.println("there not enoght products");
			itsDAL.UpdateTotalDamagedProduct(choise, num);
		}
		
	}

	
	public void demaged() {
		ArrayList<Product> products =itsDAL.findAllProducts();
		System.out.println("demaged products");
		for (Product p : products) {
			if(p.getTotal_damaged()>0)
				System.out.println(p.getProd_id() +" " + p.getName() +" demaged in storage " + p.getTotal_damaged());
		}
		
	}
	public void makeAnOrder(int prod_id, int supid, int amount,String exp_day,boolean remove) {
		IBL bl_bl;
		bl_bl = b;
		List<String> manID = new LinkedList<String>();
		List<String> productManID = new LinkedList<String>();
		List<Double> quntity = new LinkedList<Double>();
		Product p = itsDAL.findProductByID(prod_id);
		manID.add(p.getManufactureID());						
		productManID.add(p.getManufactureProdID());
		Double d = new Double((double)amount);
		quntity.add(d);
		try {
			bl_bl.takeOrder( manID,productManID,  quntity,erea);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String randomDate(){
		Random rand = new Random();

		int  day = rand.nextInt(31) + 1;
		int  month = rand.nextInt(12) + 1;
		int  year = rand.nextInt(2016) + 2026;
		return ""+day+""+month+""+year;
	}
	public void UpdateStoreSupply(Order order){
		IBL bl_bl;
		bl_bl = b;
		List<ProductQun> lpq = order.getList();
		
		for(ProductQun lpq_one : lpq){
			Product p = itsDAL.findProductByManufactureIDAndManufactureProdID(lpq_one.getPro().getManID(), lpq_one.getPro().getManuNum());
			if (p!=null){
				Stock s = new Stock(0, p.getProd_id(), Integer.parseInt(order.getSupNum()), (int)(lpq_one.getQun()), randomDate(), false);
				
				itsDAL.AddStock(s);
				itsDAL.UpdateTotalAmountProduct(s.getAmount(), s.getProd_id());
			}
		}
		try {
			bl_bl.editOrder(order);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addStock(int prod_id, int supid, int amount,String exp_day,boolean remove) {
		
			if (itsDAL.isProductExist(prod_id, "bla") && itsDAL.findProductByID(prod_id).getIsRemove()==false){
				Stock s = new Stock(0,prod_id,supid,amount,exp_day,remove);
				
						itsDAL.AddStock(s);
						itsDAL.UpdateTotalAmountProduct(s.getAmount(), s.getProd_id());
						
						
						
					
				}
				else{
					System.out.println("there no product id: "+prod_id);
				}
		
		
	}
	private  String add_n_days(String date, int n){
		String sourceDate = date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate;
		try {
			
			myDate = format.parse(sourceDate);
			Calendar cal = Calendar.getInstance();
	        cal.setTime(myDate);
	        cal.add(Calendar.DATE, n); //minus number would decrement the days
	        
	        return format.format(cal.getTime()).toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	public void orderMinimumProduct(){
		IBL bl_bl;
		bl_bl = b;
		List<String> manID = new LinkedList<String>();
		List<String> productManID = new LinkedList<String>();
		List<Double> quntity = new LinkedList<Double>();
		List<Product> products = new LinkedList<Product>();
		ArrayList<Product> lp = itsDAL.findAllProducts();
		for(Product p :lp){
			if(!p.getIsRemove()){
				if(p.getMinimum_amount()>p.getTotal_amount() - p.getTotal_damaged() - itsDAL.findNumOfExpiredProduct(p.getProd_id())){
						
					try {
						boolean bo = bl_bl.IsProductExists( p.getManufactureID(), p.getManufactureProdID(),erea);
						if(bo){
							manID.add(p.getManufactureID());						
							productManID.add(p.getManufactureProdID());
							Double d = new Double((double)(p.getMinimum_amount()-(p.getTotal_amount() - p.getTotal_damaged() - itsDAL.findNumOfExpiredProduct(p.getProd_id()))));
							
							quntity.add(d);
							products.add(p);
							
						}

					} catch (AccessDeniedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");				     
				  Calendar cal = Calendar.getInstance();
				if(p.getNextDateOrder().equals(dateFormat.format(cal.getTime()).toString())){
					try {
						if(bl_bl.IsProductExists( p.getManufactureID(), p.getManufactureProdID(),erea)){
							manID.add(p.getManufactureID());						
							productManID.add(p.getManufactureProdID());
							Double d = new Double((double)(p.getQuntity()));
							itsDAL.UpdateNextDateOrderProduct(p.getProd_id(), add_n_days(p.getNextDateOrder(), p.getDays()));
							
							quntity.add(d);
							products.add(p);
							
						}

					} catch (AccessDeniedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		try {
			bl_bl.takeOrder( manID,productManID,  quntity,erea);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for (int i=0; i<lp.size();i++){
			
			Product p = products.get(i);
			
			Stock s = new Stock(0,p.getProd_id(),0,(quntity.get(i)).intValue(),"16/12/2016 00:00:00",false);
			
			itsDAL.AddStock(s);
			itsDAL.UpdateTotalAmountProduct(s.getAmount(), s.getProd_id());
			
		}*/
	}


	
}

