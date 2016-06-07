package GUI;
import Backend.*;
public  class AddContact {
	
	public AddContact(){
	}
	
	public  Contact add(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*ADD CONTACT*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("----------------------At any stage press 0 to quit--------------------");
		System.out.println("please enter first name and press enter (20 letters max)");
		String firstName= check.checkName("wrong input... the name's length should be maximum of 20 letters");
		if(firstName.equals("0")){
			System.out.println("quiting");
			check.delay();
			return null;
		}
		
		System.out.println("please enter last name and press enter (20 letters max)");
		String lastName= check.checkName("wrong input...  the name's length should be maximum of 20 letters");
		if (lastName.equals("0")){
			System.out.println("quiting");
			check.delay();
			return null;
		}
		
		System.out.println("please enter phone number and press enter(10 digits max)");
		int phone= check.checkPhone("wrong input... phone number should contain only numbers \n and it's maximum length is 10");
		if (phone==0){
			System.out.println("quiting");
			check.delay();
			return null;
		}
		
		Contact cont= new Contact(firstName, lastName, phone);
		 return cont;
	}
	 

	

}
