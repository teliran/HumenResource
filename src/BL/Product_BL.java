package BL;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import Backend.Agreement;
import Backend.AgreementEntity;
import Backend.Product;
import DAL.AccessDeniedException;
import DAL.IDAL;
import storageDal.Entity_DAL;

public class Product_BL {

	private IDAL dal;
	
	protected Product_BL(IDAL dal)
		{
			this.dal=dal;
		}
		
	
	protected List<Backend.Product> GetProductByArea(String supNum, Date date, String Area) throws AccessDeniedException
	{
		List<AgreementEntity> productList=dal.GetAllProductByAgreement(supNum, date);
		List<Backend.Product> proByAreaList=new LinkedList<Backend.Product>();
		for(AgreementEntity pro: productList)
			if(((Backend.Product) pro).getArea().equals(Area))
				proByAreaList.add((Product) pro);
		return proByAreaList;
	}
	
	protected Backend.Product SearchProductByCat(String catNum, String supId, Date date) throws AccessDeniedException
	{
		List<AgreementEntity> productList=dal.GetAllProductByAgreement(supId, date);
		for(AgreementEntity pro: productList)
			if(((Backend.Product) pro).getCatNum().equals(catNum))
				return (Product) pro;
		return null;
	}
	
	protected List<Product> GetProductByName(String supNum, Date date, String name) throws AccessDeniedException
	{
		List<AgreementEntity> productList=dal.GetAllProductByAgreement(supNum, date);
		List<Backend.Product> proByNameList=new LinkedList<Backend.Product>();
		for(AgreementEntity pro: productList)
			if(((Backend.Product) pro).getName().equals(name))
				proByNameList.add((Product) pro);
		return proByNameList;
	}
	
	protected boolean IsProductExists(String manuNum,String productNum,String area) throws AccessDeniedException
	{
		Agr_BL agr=new Agr_BL(dal);
		List<Agreement> agreementList=agr.getAllValidAgreement(area);
		if(agreementList!=null)
		{
			for(Agreement agreement: agreementList)
			{
				List<Product> productList=agreement.getProductList();
				for(Product product:productList)
				{
					if(product.getManID().equals(manuNum) && product.getManuNum().equals(productNum))
						return true;
				}
			}
		}
		return false;
	}
}
