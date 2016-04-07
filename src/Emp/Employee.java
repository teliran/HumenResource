package Emp;



public class Employee { 
	private int id;
	private String name;
	private Position pos;
	private int bankNumber;
	private int accountNumber;
	private String startDate;
	public static enum Position{hrManager, stockManager, storekeeper, 
		cashier, driver, storeManager, shiftManager};
		
	public Employee(int id, String name, Position pos, int bankNumber,int accountNumber,String startDate){
		this.id = id;
		this.name = name;
		this.pos = pos;
		this.bankNumber = bankNumber;
		this.accountNumber = accountNumber;
		this.startDate = startDate;	
	}
	
	public static void showMenu(){
		int usrInput;
		while(true){
			System.out.println("==Employee==");
			System.out.println("1.\t Add new Employee");
			System.out.println("2.\t Search Employee");
			System.out.println("3.\t Back");
			usrInput = Store.getNumber();
			switch(usrInput){
				case 1:
					//TODO add new employee menu
					break;
				case 2:
					//TODO search employee 
					break;
				case 3:
					return;
			}
		}	
	}
	
	
	
	
	public Position getPosition() {
		return pos;
	}
	public void setPosition(Position pos) {
		 this.pos = pos;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public int getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(int bankNumber) {
		this.bankNumber = bankNumber;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	
}
