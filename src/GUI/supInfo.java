package GUI;
import DAL.AccessDeniedException;
import java.text.ParseException;
import java.util.*;
import Backend.Entity;
import Backend.Supplier;
import BL.IBL;
public  class supInfo {
	private IBL bl;
	public supInfo(IBL bl){
		this.bl=bl;
	}
	
	public  void run(){
		openScreen os= new openScreen(bl);
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("Please choose an option and type the option’s number below:");
		System.out.println("1 - list all suppliers and view");
		System.out.println("2 - add new supplier");
		System.out.println("0 - back");
		int optI= check.checkInt(0, 4,"wrong input.... please choose on of the options above \n");
		switch (optI){
		case 0:	os.run();
				break;
		case 1: opt1();
				break;
		case 2: opt3();
				break;
		}
	

	}
	
	
	// represents a list of all the suppliers and gives option to choose one
	private  void opt1(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		List<Entity> list=null;
		try {
			list = bl.GetItAll(new Supplier());
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("problems with comunication...(supInfo 45)");
			check.delay();
			run();
		}
		int counter=1;
		for (Entity ent: list){
			System.out.println(counter+")");
			System.out.println((Supplier) ent);
			counter++;
			System.out.println("-*-*-*-*-*-*-*-*-*-SUPPLIER*-*-*-*-*-*-*-*-*-*-*-");
		}
		System.out.println("Please enter the number of the supplier below:");
		System.out.println("0 -  to go back");
		int supNum;
		supNum =check.checkInt(0, list.size(), "please choose the number next to the supplier you want to view");
		if (supNum==0){
			run();
			return;
		}
				
		supCheck sc= new supCheck(bl, ((Supplier)(list.get(supNum-1))).getSupNum());
		sc.run();
		}

	

	
	private void opt3(){
		AddSupplier ads= new AddSupplier(bl);
		Supplier sup= ads.add();
		String ans="";
		if (sup==null)
			System.out.println("Supplier havn't been added...");
		else
		{
			try {
				ans=bl.Add(sup);
			} catch (AccessDeniedException e) {
				System.out.println("Supplier havn't been added, comminication problems (supInfo80)");
				check.delay();
				run();
			}
			if (ans==null){
				System.out.println("Supplier sussecfully added");
				check.delay();
			}
			else{
				System.out.println(ans);
				check.delay();
			}
				
			
		}
		run();
	}
		
	

}
