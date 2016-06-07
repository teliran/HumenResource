package GUI;
import Backend.*;
public class addManfacturer {
	public addManfacturer(){
	}
	public Manufacturer add(){
		 String ID;
		 String name;
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-ADD MANFACTURER*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("----------------------At any stage press 0 to quit--------------------");
		 System.out.println("Please enter Manufacturer ID: " );
		 ID= check.checkNameP("wrong input... please enter the manufcturer id, no more then 30 letters ");
		 if (ID.equals("0")){
			 System.out.println("quiting");
				check.delay();
				return null;
			}
		 
		 System.out.println("Please enter Manufacturer name: " );
		 name= check.checkNameP("wrong input... please enter the manufcturer name, no more then 30 letters");
		 if (name.equals("0")){
			 System.out.println("quiting");
				check.delay();
				return null;
			}
		 
		return new Manufacturer(ID, name);
		 
	}
	 

}
