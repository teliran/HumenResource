package Backend;
import java.util.*;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public class Supplier implements Entity, IdSearchable, SupIdSearchable
{
	public enum paymentMethod{CASH, CREDIT, CHECK};
	private String supNum;
	private String supName;
	private int bAN;
	private paymentMethod method;
	private String area;
	private List<Manufacturer> manList;
	private List<Contact> contactsList;
	private String address;
	
	/////////////////
	///Constructor///
	/////////////////
	
	public Supplier()
	{
		supNum= null;
		supName= null;
		bAN=0;
		method= null;
		area= null;
		manList= null;
		contactsList= null;
		address= null;
	}

	public Supplier(Supplier other){
		supNum= other.supNum;
		supName= other.supName;
		bAN= other.bAN;
		method= other.method;
		area= other.area;
		contactsList= other.contactsList;
		manList= other.manList;
		address= other.address;
	}
	
	public Supplier(String supNum, String supName, int bAN, paymentMethod method,String area, List<Contact> contactsList, List<Manufacturer> manList, String address )
	{
		this.supNum= supNum;
		this.supName= supName;
		this.bAN= bAN;
		this.method= method;
		this.area= area;
		this.contactsList= contactsList;
		this.manList= manList;
		this.address= address;
	}
	

	
	
	/////////////
	///Getters///
	/////////////
	
	public String getSupNum()
	{
		return supNum;
	}
	
	public String getSupName()
	{
		return supName;
	}
	
	public int getBAN()
	{
		return bAN;
	}
	
	public paymentMethod getPaymentMethod()
	{
		return method;
	}
	
	public String getArea()
	{
		return area;
	}
	
	public List<Contact> getContactsList()
	{
		return contactsList;
	}
	
	public List<Manufacturer> getManList()
	{
		return manList;
	}
	
	public String getAddress(){
		return address;
	}
	/////////////
	///Setters///
	/////////////
	
	public void setSupNum(String supNum) {
		this.supNum = supNum;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public void setbAN(int bAN) {
		this.bAN = bAN;
	}

	public void setMethod(paymentMethod method) {
		this.method = method;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}

	public void setManList(List<Manufacturer> manList) {
		this.manList = manList;
	}
	
	public void setAddress(String address){
		this.address= address;
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
		return en.EditVisit(this,(Supplier)sub);
	}

	@Override
	public void DeleteVisit(Entity_BL en) throws AccessDeniedException {
		en.DeleteVisit(this);		
	}	
	
	//////////////
	///Tostring///
	//////////////
	
	public String toString(){
		
		return "Supplier number: "+ supNum+"\n"
				+"Supplier name: "+ supName+"\n"
				+"Area: "+area+"\n";
			
	}
	@Override
	public IdSearchable SearchByIdVisit(String ID, Entity_BL en) throws AccessDeniedException {
		return en.SearchByID(ID, this);
		
	}

	@Override
	public List<SupIdSearchable> SearchBySupIdVisit(String ID, Entity_BL en) throws AccessDeniedException {
		return en.GetEntityBySupplier(ID, this);
	}

	@Override
	public List<Entity> GetItAll(Entity_BL entity) throws AccessDeniedException {
		return entity.GetItAll(this);
	}
	
}
