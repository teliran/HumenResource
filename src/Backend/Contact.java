package Backend;

import java.util.List;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public class Contact implements SupIdSearchable ,SupplierEntity
{
	private String firstName;
	private String lastName;
	private int phoneNum;

	
	//////////////////
	///Constructors///
	//////////////////
	
	public Contact()
	{
		firstName= null;
		lastName= null;
		phoneNum= 0;
	}
	
	public Contact(String firstName, String lastName, int phoneNum){
		this.firstName= firstName;
		this.lastName= lastName;
		this.phoneNum= phoneNum;
		
	}
	
	public Contact(Contact other){
		firstName= other.firstName;
		lastName= other.lastName;
		phoneNum= other.phoneNum;
	}
	
	/////////////
	///Getters///
	/////////////
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public int getPhoneNum(){
		return phoneNum;
	}
	
	
		
	//////////////
	///visitors///
	//////////////


	@Override
	public List<SupIdSearchable> SearchBySupIdVisit(String id,Entity_BL en) throws AccessDeniedException {
		return en.GetEntityBySupplier(id, this);
	}
	
	@Override
	public String addVisit(String supId, Entity_BL en) throws AccessDeniedException {
		return en.AddBySupplierId(supId, this);
	}

	@Override
	public String EditVisit(String supId, Entity_BL en,SupplierEntity sub,SupplierEntity old) throws AccessDeniedException {
		return en.EditBySupplierId(supId,(Backend.Contact)old, this);
	}

	@Override
	public void DeleteVisit(String supId, Entity_BL en) throws AccessDeniedException {
		en.DeleteBySupplierId(supId, this);
	}
	
	/////////////
	///Setters///
	/////////////	

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNum(int phoneNum) {
		this.phoneNum = phoneNum;
	}

	
	
	
	//ToString
	
	public String toString(){
		return "First name: "+firstName+"\n"
				+"Last name: "+lastName+"\n"
				+"Phone Number: "+ phoneNum+ "\n";
	}
}
