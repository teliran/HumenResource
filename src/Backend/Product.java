package Backend;
import java.util.*;

import BL.Entity_BL;
import DAL.AccessDeniedException;

import java.sql.Date;

public class Product implements AgreementEntity
{
	private String name;
	private String CatNum;
	private String manID;
	private String area;
	private double price;
	private List<Discount> discount;
	private String manuNum;
	private double weight;
	
	/////////////////
	///Constructor///
	/////////////////
	
	public Product(String name,String manuNum, String CatNum, String manID, String area, double price, List<Discount> discount, double weight)
	{
		this.name= name;
		this.CatNum= CatNum;
		this.manID= manID;
		this.area= area;
		this.price= price;
		this.discount= discount;
		this.manuNum= manuNum;
		this.weight=weight;
	}
	
	public Product(){
		manuNum=null; 
		name= null;
		CatNum= null;
		manID=null;
		area=null;
		price= 0;
		discount= null;
		weight=0;
	}
	
	public Product(Product other){
		name= other.name;
		manuNum=other.manuNum;
		CatNum= other.CatNum;
		manID= other.manID;
		area= other.area;
		price= other.price;
		discount=other.discount;
		this.weight=other.weight;
	}
	
	/////////////
	///Getters///
	/////////////
	
	public String getName()
	{
		return name;
	}
	
	public String getManuNum()
	{
		return manuNum;
	}
	
	
	
	public String getCatNum()
	{
		return CatNum;
	}
	
	public String getManID()
	{
		return manID;
	}
	
	public String getArea()
	{
		return area;
	}

	

	public double getPrice()
	{
		return price;
	}

	public List<Discount> getDisList()
	{
		return discount;
	}

	public double getWeight()
	{
		return weight;
	}

	

	///////////////////
	///visit pattern///
	///////////////////
	@Override
	public String addVisit(String supId, Date date, Entity_BL en) throws AccessDeniedException {
		return en.AddByAgreement(supId, date, this);
	}

	@Override
	public String EditVisit(String supId, Date date, Entity_BL en, AgreementEntity sub, AgreementEntity old)
			throws AccessDeniedException {
		return en.EditByAgreement(supId, date, this,(Backend.Product)sub);
	}

	@Override
	public void DeleteVisit(String supId, Date date, Entity_BL en) throws AccessDeniedException {
		en.DeleteByAgreement(supId, date, this);
	}

	@Override
	public List<AgreementEntity> GetItAllVisit(String supId, Date date, Entity_BL en)
			throws AccessDeniedException {
		return en.GetItAllByAgreement(supId,date,this);
	}

	/////////////
	///Setters///
	/////////////
	
	public void setName(String name) {
		this.name = name;
	}

	

	public void setCatNum(String catNum) {
		CatNum = catNum;
	}

	public void setManID(String manID) {
		this.manID = manID;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setMenuNum(String manuNum) {
		this.manuNum=manuNum;
	}

	public void setDiscount(List<Discount> discount) {
		this.discount = discount;
	}
	
	
	//ToString???????
	
	
	public String toString(){
		//String dis="";
		//for(Discount disc: discount)
		//	dis= dis+disc.toString()+"\n";
		String temp="Product's name: "+name+" CAT number: "+CatNum+" Manufacturer ID: "+manID+" Manufacturer Number: "+manuNum+" Area: "+area+" Price: "+price;
				//+"Discounts: "+"\n"+dis;
		return temp;			
		
	}


}
