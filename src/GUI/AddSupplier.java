package GUI;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import BL.IBL;
import Backend.Supplier.paymentMethod;
import DAL.AccessDeniedException;
import Backend.*;
public class AddSupplier {
 private IBL bl;
	 
	 public AddSupplier(IBL bl){
		 this.bl=bl;
	 }
	 public Supplier add(){
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-ADD SUPPLIER*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("----------------------At any stage press 0 to quit--------------------");
		String supNum=null;
		String supName;
		int bAN;
		paymentMethod method= null;
		String area;
		String address;
		List<Manufacturer> manList= new LinkedList<Manufacturer>();
		List<Contact> contactsList= new LinkedList<Contact>();
		System.out.println("please enter supplier number:");
		Supplier sup= new Supplier();
		boolean flag = true;
		while(flag){
			supNum= check.checkNameP("wrong input... \n please enter supplier number under 50 letters");
			if (supNum.equals("0")){
				System.out.println("quiting...");
				check.delay();
				return null;
			}
				
			try {
				if (bl.SearchByID(supNum, sup)==null)
					flag = false;
			} catch (AccessDeniedException | NumberFormatException | ParseException e) {
				return null;
			}
			if (flag){
				System.out.println("supplier with this number already exists in th system");
				System.out.println("Please enter a diffrent number");
			}
		}
		
		
		System.out.println("please enter the supplier's name:");
		supName= check.checkNameP("wrong input... \n please enter supplier name under 50 letters");
		if (supName.equals("0")){
			System.out.println("quiting...");
			check.delay();
			return null;
		}

		System.out.println("please enter new payment method:");
		System.out.println("1 for cash");
		System.out.println("2 for credit");
		System.out.println("3 for checks");
		int opt= check.checkInt(1,3,"wrong input... \n "+"please enter new payment method:\n"
				+ "1 for cash \n"+"2 for credit \n"+"3 for checks \n");
		switch (opt){
			case 0: System.out.println("quiting...");
					check.delay();
					return null;
			
			case 1: method= paymentMethod.CASH;
					break;
			case 2: method= paymentMethod.CREDIT;
					break;
			case 3: method= paymentMethod.CHECK;
					break;
		}
		
		System.out.println("please enter new bank account number");
		bAN= check.checkInt(10000000, 999999999,"wrong input... \n please enter a bank account number in range: \n"
				+ "(a positive number with less then 10 digits)");
		if (bAN==0){
			System.out.println("quiting...");
			check.delay();
			return null;
		}
		
		
		System.out.println("Please enter suppplier's area:");
		area= check.checkNameP("wrong input... \n please enter supplier area under 50 letters");
		if (area.equals("0")){
			System.out.println("quiting...");
			check.delay();
			return null;
		}
		
		System.out.println("Please enter suppplier's address:");
		address= check.checkNameP("wrong input... \n please enter supplier area under 50 letters");
		if (address.equals("0")){
			System.out.println("quiting...");
			check.delay();
			return null;
		}
			
		
		return new Supplier(supNum,supName,bAN, method,area, contactsList,manList, address );		 
	 }

}
