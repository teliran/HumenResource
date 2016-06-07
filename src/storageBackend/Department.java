package storageBackend;

public class Department {
	private int department_id;
	private String name;
	private int general_department_id;
	
	boolean isRemove;
	
	
	
	public int getDepartment_id(){
		return department_id;
	}
	public void setDepartment_id(int department_id){
		this.department_id=department_id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getGeneral_department_id(){
		return general_department_id;
	}
	public void setGeneral_department_id(int general_department_id){
		this.general_department_id=general_department_id;
	}
	
	public boolean getIsRemove(){
		return isRemove;
	}
	public void setIsRemove(boolean isRemove){
		this.isRemove=isRemove;
	}
	public Department(int department_id, String name, int general_department_id, boolean isRemove){
		this.department_id = department_id;
		this.name = name;
		this.general_department_id = general_department_id;
		
		this.isRemove = isRemove;
		
	}
	public Department(Department other){
		this.department_id = other.department_id;
		this.name = other.name;
		this.general_department_id = other.general_department_id;
		
		this.isRemove = other.isRemove;
		
	}
	public String toString(){
		String s = "Department details:\n";
		s=s + "Department ID: "+ department_id+"\n";
		s=s + "Department Name: "+name+"\n";
		s=s + "General department ID: "+general_department_id+"\n";
		
		s=s + "Remove: "+isRemove+ "\n";
		return s;
	}
}
