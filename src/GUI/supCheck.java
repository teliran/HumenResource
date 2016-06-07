package GUI;
import BL.IBL;

import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import Backend.*;
import Backend.Supplier.paymentMethod;
import DAL.AccessDeniedException;

public class supCheck {
	private IBL da;
	private String supNum;
	public supCheck(IBL da, String supNum){
		this.da=da;
		this.supNum= supNum;
	}
	 public void run(){
		supInfo si= new supInfo(da);
		Supplier sup= new Supplier();
		System.out.println("=============================");
		try {
			sup= (Supplier) da.SearchByID(supNum, sup);
		} catch (AccessDeniedException | NumberFormatException | ParseException e) {
			System.out.println("Problems with the network :-( (supCheck 26) ");
			check.delay();
			si.run();
			return;
		}
		
		System.out.println(sup);
		System.out.println("BANK ACOUNT NUMBER "+sup.getBAN());
		System.out.println("PAYMENT METHOD: "+sup.getPaymentMethod());
		System.out.println("=============================");
		System.out.println("Please choose an option and type the option’s number below:");
		System.out.println("1 - show all contacts");
		System.out.println("2 - add new contact");
		System.out.println("3 - delete contact");
		System.out.println("4 - update bank account number");
		System.out.println("5 - update payment method");
		System.out.println("6 - view contracts");
		System.out.println("7 - add contract");
		System.out.println("8 - show all manufacturer");
		System.out.println("9 - add manufacturer");
		System.out.println("10 - delete manufacturer");
		System.out.println("0 - back");
		int opt= check.checkInt(0, 10, "wrong input.... please choose a number for one of the options above \n");
		switch (opt){
		case 0:	si.run();
				break;
		case 1: opt1();
				break;
		case 2: opt2();
				break;
		case 3: opt3();
				break;
		case 4: opt4();
				break;
		case 5: opt5();
				break;
		case 6: opt6();
				break;
		case 7: opt7();
				break;
		case 8: opt8();
				break;
		case 9: opt9();
				break;
		case 10: opt10();
				break;
		}
		return;
	}
	
	
	// prints all contacts
	private void opt1(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*CONTACTS*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		List<SupIdSearchable> list=null;
		Contact con= new Contact();
		try{
			list= da.GetEntityBySupplier(supNum, con);
		}catch( AccessDeniedException | ParseException e){
			System.out.println("toruble with network (supCheck) 83");
			check.delay();
			run();
			return;
		}
		if (list.isEmpty())
			System.out.println("supplier has no contacts");
		else{
			for(SupIdSearchable ent: list)
				System.out.println((Contact) ent); 
		}
		System.out.println("press 0 - to go back");
		check.checkInt(0,0,"wrong input.... please press 0 to go back");
		run();
		return;

	}
	
	// add new contact
	private void opt2(){
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*ADD CONTACT*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("press 1 to add contact");
		System.out.println("press 0 to go back");
		int optI= check.checkInt(0, 1,"wrong input.... \n press 1 to add contact \n press 0 to go back \n");
		AddContact ad= new AddContact();
		String ans= null;
		Contact cont= null;
		while (optI==1){
			cont= ad.add();
			if (cont!=null){
				try{
					ans= da.AddBySupplierId(supNum, cont);
				}catch( AccessDeniedException e){
					System.out.println("Problem with adding contact (supcheck 115)");
				}
				if (null!=ans)
					System.out.println(ans);
				else
					System.out.println("Contact added!");
			}
			System.out.println("press 1 to add another contact");
			System.out.println("press 0 to go back");
			optI= check.checkInt(0, 1, "wrong input...\n"
					+ "press 1 to add another contact \n"
					+ "press 0 to go back \n");
		}
		run();
		return;
		
	}
	
	// delete contact
	private void opt3(){
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*DELETE CONTACT*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("press 1 to delete contact");
			System.out.println("press 0 to go back");
			int optI= check.checkInt(0, 1, "wrong input.... \n press 1 to delete contact \n press 0 to go back \n");
			
			while (optI==1){
				List<SupIdSearchable> conList= null;
				try {
					conList= da.GetEntityBySupplier(supNum, new Contact());
				} catch (AccessDeniedException | ParseException e) {
					System.out.println("Problems with network supCheck 144)");
					check.delay();
					run();
					return;
				}
				if (!conList.isEmpty()){
				System.out.println("Please choose the number near the contact you want to delete: ");
				int counter= 1;
				for (SupIdSearchable contact: conList){
					System.out.println(counter+")");
					System.out.println((Contact)contact);
					counter++;
				}
				int opt= check.checkInt(0, conList.size(), "Please choose a number near on of the contacts");
				if (opt==0){
					System.out.println("quiting...");
					check.delay();
					run();
					return;
				}
					
				Contact cont= (Contact) conList.get(opt-1);
				try {
					da.DeleteBySupplierId(supNum, cont);
				} catch (AccessDeniedException e) {
					System.out.println("Problems with network, contact not deleted (supCheck 166)");
					check.delay();
					run();
					return;
				}
				System.out.println("contact deleted");
				System.out.println("press 1 to delete another contact");
				System.out.println("press 0 to go back");
				optI= check.checkInt(0, 1, "wrong input... \n press 1 to delete another contact \n press 0 to go back \n");
				}else{
					System.out.println("supplier has no contacts");
					System.out.println("please press 0 to return");
					optI= check.checkInt(0, 0, "please press 0 to return");
				}
			}
			run();
			return;
	 }
		

	
	//change bank number
	private void opt4(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-UPDATE BANK ACOUNT*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("please enter new bank account number");
		int bAN= check.checkInt(10000000, 999999999, "wrong input.... \n"
				+ "please enter apositve number less then 10 digits \n");
		if (bAN==0){
			System.out.println("quiting...");
			check.delay();
			run();
			return;
		}
			
		String ans= null;
		Supplier old= null;
		Supplier newer= null;
		try{
			 old= (Supplier) da.SearchByID(supNum, new Supplier());
			 newer= (Supplier) da.SearchByID(supNum, new Supplier());
		}catch(AccessDeniedException | NumberFormatException | ParseException e){
			System.out.println("network problems, (supCheck 204)");
			check.delay();
			run();
			return;
		}
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		newer.setbAN(bAN);
		try {
			ans= da.Edit(old, newer);
		} catch (AccessDeniedException e) {
			System.out.println("network problems, (supCheck 213)");
			check.delay();
			run();
			return;
		}
		if (ans==null){
			System.out.println("new bank account number: "+bAN);
			System.out.println("was succesfully updated");
		}
		else
			System.out.println("trouble with updating .....");
		System.out.println("0 - back");
		check.checkInt(0,0, "wrong input... please press 0 to go back \n");
		run();
		return;

		
	}
	
	
	//change payment method
	private void opt5(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-PAYMENT METHOD-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("please enter new payment method:");
		System.out.println("1 for cash");
		System.out.println("2 for credit");
		System.out.println("3 for checks");
		System.out.println("0 to go back");
		int opt= check.checkInt(0,3, "wrong input... please choose on of the options for payment method or 0 to go back");
		String ans= null;
		Supplier old= null;
		Supplier newer= null;
		paymentMethod method=null;
		switch (opt){
			case 0: run();
			
			case 1: method= paymentMethod.CASH;
			
			break;
			case 2:  method= paymentMethod.CREDIT;
			
			break;
			case 3:  method= paymentMethod.CHECK;
			
			break;
		}
		try{
			 old= (Supplier) da.SearchByID(supNum, new Supplier());
			 newer= (Supplier) da.SearchByID(supNum, new Supplier());
		}catch(AccessDeniedException | NumberFormatException | ParseException e){
			System.out.println("network problems.. (supCheck 261)");
			check.delay();
			run();
			return;
		}
		
		newer.setMethod(method);
		try {
			ans= da.Edit(old, newer);
		} catch (AccessDeniedException e) {
			System.out.println("network problems... (supCheck 270)");
			check.delay();
			run();
			return;
		}
		if (ans==null)
			System.out.println("succesfully updated");
		
		else
			System.out.println("trouble with updating .....");
		System.out.println("0 - back");
		check.checkInt(0,0, "wrong input... please press 0 to go back \n");
		run();
		return;
		}
	 
	 
	 
	// contract details
	private void opt6(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*VIEW AGREEMENTS*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		List<SupIdSearchable> list=null;
		
		try {
			list = da.GetEntityBySupplier(supNum, new Agreement());
		} 
		catch (AccessDeniedException | ParseException e) {
			System.out.println("netwrok Problems... (supCheck 295)");
			check.delay();
			run();
			return;
		}
		if (!list.isEmpty()){
			boolean flag =true;
			while(flag){
				int counter =1;
				for(SupIdSearchable ent: list){
					System.out.println(counter+") ");
					System.out.println(((Agreement) ent).getSignDate());
					counter++;
				}
				System.out.println("Please choose a number near  a contract day to view contracts details:");
				int opt= check.checkInt(0, list.size(), "please choose one of the numbers to view a contract");
				if (opt==0){
					System.out.println("quiting...");
					check.delay();
					run();
					return;
				}
				Agreement agr= (Agreement) list.get(opt-1);
				System.out.println(agr);
				List<Product> proList =agr.getProductList();
				if (!proList.isEmpty()){
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					System.out.println("Products List: ");
					int count=1;
					for(Product pro: proList){
						System.out.println(count+")");
						System.out.println(pro);
						List<Discount> disList= pro.getDisList();
						if (!disList.isEmpty()){
							System.out.println("Discount List:");
							for(Discount dis: disList)
								System.out.println("for: "+dis.getQun()+" discount is: "+dis.getDisc()+"%");
						}
						count++;
						System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					}
						
					
				}
				
				System.out.println("to view another contract press 1");
				System.out.println("to go back to supplier menu press 0");
				opt= check.checkInt(0, 1, "please choose on of the options above");
				if (opt==0)
					flag=false;
			}
		}else{
			System.out.println("supplier have no contracts......");
			System.out.println("please press 0 to return");
			int opt= check.checkInt(0, 0, "please press 0 to return");
		}
		run();
		return;
	}	

	 
	// add contract 
	private void opt7(){
			
			Manufacturer man= new Manufacturer();
			List<SupIdSearchable> list=null;
			try {
				list = da.GetEntityBySupplier(supNum, man);
			} catch (AccessDeniedException | ParseException e) {
				System.out.println("netwrok Problems...");
				run();
				return;
			}
			if (list.isEmpty()){
				System.out.println("no manufactures for this supplier, please add first \n"
						+ "and then add an agreement \n");
				check.delay();
			}
			else{
				AddAgreement addAgreement= new AddAgreement(da);
				Agreement toAdd= addAgreement.add(supNum);
					if (toAdd!=null){
					Date signDate= toAdd.getSignDate();
					String ans="";
					try {
						ans=da.Add(toAdd);
					} catch (AccessDeniedException e) {
						System.out.println("network problems... (supCheck 377)");
						check.delay();
						run();
						return;
					}
					if (ans!=null){
						System.out.println(ans+" (supCheck 382)");
						check.delay();
						run();
						return;
					}
					System.out.println("Agreement added");
					int opt;
					boolean flag= true;
					AddProduct addP= new AddProduct(da);
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					while (flag){
						System.out.println("Products List::");
						System.out.println("press 1 to add an item");
						System.out.println("press 0 to finish the list");
						opt= check.checkInt(0, 1, "wrong input...\n press 1 to add an item \n press 0 to finish the list \n ");
						if (opt==1){
							ans= addP.add(supNum, signDate);
							if (ans==null)
								System.out.println("added succesfully");
							else
								System.out.println(ans+ " (supCheck 400)");
						}
							
						else
							flag= false;
					}
				}
			}
			run();
			return;
		}	

	// man list
	private void opt8(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*MANUFACTURERS LIST*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		Manufacturer man= new Manufacturer();
		List<SupIdSearchable> list=null;
		try {
			list = da.GetEntityBySupplier(supNum, man);
		} catch (AccessDeniedException | ParseException e) {
			System.out.println("netwrok Problems...");
			run();
			return;
		}
		if (list.isEmpty())
			System.out.println("no manufactures for this supplier");
		else{
			for (SupIdSearchable ent: list)
				System.out.println((Manufacturer) ent);
		}
		System.out.println("0 - back");
		check.checkInt(0,0, "wrong input... please press 0 to go back \n");
		run();
		return;


	}
	 
	 // add manufacturer
	private void opt9(){
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*ADD MANFCTURER-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("press 1 to add manufacturer");
			System.out.println("press 0 to go back");
			int optI= check.checkInt(0, 1,"wrong input.... \n press 1 to add manufacturer \n press 0 to go back \n");
			addManfacturer ad= new addManfacturer();
			String ans= null;
			Manufacturer man= null;
			while (optI==1){
				man= ad.add();
				try{
					ans= da.AddBySupplierId(supNum, man);
				}catch( AccessDeniedException e){
					System.out.println("Problem with adding manufacturer (supCheck 450)");
				}
				if (null!=ans)
					System.out.println(ans+ " (supCheck 453)");
				else
					System.out.println("Manufaturer added!");
				System.out.println("press 1 to add another manufacturer");
				System.out.println("press 0 to go back");
				optI= check.checkInt(0, 1, "wrong input...\n"
						+ "press 1 to add another manufacturer \n"
						+ "press 0 to go back \n");
			}
			run();
			return;
	 }
	 
	 // delete manufacturer
	private void opt10() {
		 System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*DELETE MANUFACTURER*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		 System.out.println("press 1 to delete manufarer");
			System.out.println("press 0 to go back");
			int optI= check.checkInt(0, 1, "wrong input.... \n press 1 to delete manufacturer \n press 0 to go back \n");
			
			while (optI==1){
				List<SupIdSearchable> manList= null;
				try {
					manList= da.GetEntityBySupplier(supNum, new Manufacturer());
				} catch (AccessDeniedException | ParseException e) {
					System.out.println("Problems with network (supCheck 477)");
					run();
					return;
				}
				System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
				if (!manList.isEmpty()){
					System.out.println("Please choose the number near the Manufacturer you want to delete: ");
					int counter= 1;
					for (SupIdSearchable man: manList){
						System.out.println(counter+")");
						System.out.println((Manufacturer)man);
						counter++;
					}
					System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
					
					int opt= check.checkInt(0, manList.size(), "Please choose a number near on of the Manufacurers");
					if (opt==0){
						optI=0;
						run();
						return;
					}
						
					Manufacturer man= (Manufacturer) manList.get(opt-1);
					List<SupIdSearchable> list= null;
					try {
						list=da.GetEntityBySupplier(supNum, new Agreement());
					} catch (AccessDeniedException | ParseException e1) {
						System.out.println("Problems with network, manufacturer not deleted");
						run();
						return;
					}
					boolean flag= true;
					for(SupIdSearchable agr: list){
						List<Product> proList=((Agreement)agr).getProductList();
						for(Product pro: proList){
							if(pro.getManID().equals(man.getID()))
								flag=false;
						}
					}
					if (flag){
						try {
							da.DeleteBySupplierId(supNum, man);
						} catch (AccessDeniedException e) {
							System.out.println("Problems with network, manufacturer not deleted");
							run();
							return;
						}
						System.out.println("manufacturer deleted");
					}else{
						System.out.println("Manifcurer still have products in agreements an can't be deleted");
					}
					System.out.println("press 1 to delete another manufacturer");
					System.out.println("press 0 to go back");
					optI= check.checkInt(0, 1, "wrong input... \n press 1 to delete another manufacturer \n press 0 to go back \n");
				}
				else{
					System.out.println("supplier has no manufacturers");
					System.out.println("please press 0 to return");
					optI= check.checkInt(0, 0, "please press 0 to return");
				}
			}
			run();
			return;
		}
	 
	
	 
	
	

}
