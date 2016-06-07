package BL;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import Backend.*;
import DAL.AccessDeniedException;
import DAL.IDAL;

public class Agr_BL {
	
	private IDAL dal;
	
	protected Agr_BL(IDAL dal)
		{
			this.dal=dal;
		}
	
	protected List <Agreement> getAllValidAgreement() throws AccessDeniedException{
		List <Entity> list= dal.GetAllAgreement();
		List<Agreement> ans= new LinkedList<Agreement>();
		for(Entity ent: list){
			if(((Agreement)ent).isValid())
				ans.add((Agreement) ent);
		}
		list= dal.GetAllSupplier();
		List<Agreement> ans2= new LinkedList<Agreement>();
		for(Entity ent: list){
			String supNum= ((Supplier)ent).getSupNum();
			Date max= new Date(1900,1,1);
			for(Agreement agr: ans){
				if(agr.getSupNum().equals(supNum))
					//&&agr.getSignDate().after(max))
					max=agr.getSignDate();
			}
			for(Agreement agr: ans){
				if(agr.getSupNum().equals(supNum)&&agr.getSignDate().equals(max))
						ans2.add(agr);
			}
		}
				
				
		return ans2;		
		
		
	}
	
	
	//returns supplier number
	protected String lowestPrice(String manId,String manNum, double qun ) throws AccessDeniedException{
		List <Agreement> agrList= getAllValidAgreement();
		String supNum= null;
		double price= Double.MAX_VALUE;
		for (Agreement agr: agrList){
			List<Product> proList= agr.getProductList();
			for(Product pro: proList){
				if(pro.getManID().equals(manId)&&pro.getManuNum().equals(manNum)){
					double proPrice=pro.getPrice()*qun;
					List<Discount> disList=pro.getDisList();
					for(Discount dis: disList){
						double temp= (dis.getDisc()*qun*pro.getPrice())/100;
						if(qun>=dis.getQun()&&proPrice>temp)
							proPrice=temp;
					}
					if (proPrice<price){
						price= proPrice;
						supNum= agr.getSupNum();
					}
				}
			}
		}
		return supNum;
		
	}
	
	
	
	


}
