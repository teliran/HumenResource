package Backend;

import java.util.List;

import BL.Entity_BL;
import DAL.AccessDeniedException;

public class Manufacturer implements IdSearchable,SupIdSearchable,SupplierEntity
{
	private String ID;
	private String name;
	
	//////////////////
	///Constructors///
	//////////////////
	
	public Manufacturer()
	{
		ID= null;
		name =null;
	}
	
	public Manufacturer(String ID, String name )
	{
		this.ID= ID;
		this.name= name;
	}
	
	public Manufacturer(Manufacturer other){
		ID= other.ID;
		name= other.name;
	}
	
	/////////////
	///Getters///
	/////////////
	
	public String getName()
	{
		return name;
	}
	public String getID()
	{
		return ID;
	}

	///////////////////
	///visit pattern///
	///////////////////

	@Override
	public IdSearchable SearchByIdVisit(String ID, Entity_BL en) throws AccessDeniedException {
		return en.SearchByID(ID, this);

	}

	@Override
	public List<SupIdSearchable> SearchBySupIdVisit(String ID, Entity_BL en) throws AccessDeniedException {
		return en.GetEntityBySupplier(ID, this);
	}

	@Override
	public String addVisit(String supId, Entity_BL en) throws AccessDeniedException {
		return en.AddBySupplierId(supId, this);
	}

	@Override
	public String EditVisit(String supId,Entity_BL en, SupplierEntity sub,SupplierEntity old) throws AccessDeniedException {
		return en.EditBySupplierId(supId,old,this);
	}

	@Override
	public void DeleteVisit(String supId, Entity_BL en) throws AccessDeniedException {
		en.DeleteBySupplierId(supId, this);
	}



	/////////////
	///Setters///
	/////////////
	
	public void setID(String iD) {
		ID = iD;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//ToString
	
	public String toString(){
		String temp="Manufacturer name is: "+name+"\n"
		+"Manufacturer ID is: "+ID+"\n";
		return temp;
	}
}
