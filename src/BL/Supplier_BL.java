package BL;
import Backend.Supplier;

import java.util.List;

import Backend.SupIdSearchable;
import DAL.AccessDeniedException;
import DAL.IDAL;

public class Supplier_BL {

	private IDAL dal;
	
	protected Supplier_BL(IDAL dal)
		{
			this.dal=dal;
		}
	
	protected String getName(String supId) throws AccessDeniedException{
		Supplier sup=(Supplier) dal.SearchSupplierById(supId);
		if (sup!= null)
			return sup.getSupName();
		else
			return null;
	}
	
	protected String getAddress(String supId) throws AccessDeniedException{
		Supplier sup=(Supplier) dal.SearchSupplierById(supId);
		if (sup!= null)
			return sup.getAddress();
		else
			return null;
	}
	
	protected int getContactNum(String supId) throws AccessDeniedException{
		Supplier sup=(Supplier) dal.SearchSupplierById(supId);
		if (sup!= null && sup.getContactsList().size()>0)
			return sup.getContactsList().get(0).getPhoneNum();
		else
			return 0;
		
	}
	
	
	

}
