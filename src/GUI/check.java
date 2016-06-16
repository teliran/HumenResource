package GUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public final class check {
	
	public static int checkInt(int i, int j, String msg){
		String opt= null;
		Scanner reader = new Scanner(System.in);
		boolean flag= true;
				while (flag){
				opt = reader.next();
			try{
				if (opt.equals("0"))
					flag= false;
				else
					flag= opt==null || Integer.parseInt(opt)<i || Integer.parseInt(opt)>j;
			}catch (Exception e){
				
			}
			if (flag)
				System.out.println(msg);
		}
		return Integer.parseInt(opt);
	}
	
	public static int checkIntWithNoWhile(String num){
		
		
		boolean flag= true;
			
				
			try{
				if (num.equals("0")){
					flag= false;
					return -1;
				}
				else
					flag= num==null || Integer.parseInt(num)>0;
			}catch (Exception e){
				return -1;
			}
			
		
		return Integer.parseInt(num);
	}

	public static int checkPhone(String msg){
		String opt= null;
		int phone=0;
		Scanner reader = new Scanner(System.in);
		boolean flag= true;
				while (flag){
				opt = reader.next();
				try{
				phone= Integer.parseInt(opt);
				if (opt.length()<11&&phone>=0)
					flag= false;
			}catch (Exception e){
				
			}
			if (flag)
				System.out.println(msg);
		}
		return phone;
	}
	
	public static String checkName(String msg){
		Scanner reader = new Scanner(System.in);
		String name= null;
		boolean flag= true;
		while (flag){
			name= reader.nextLine();
			if (name.length()>20)
				System.out.println(msg);
			else
				flag= false;
		}
		return name;
	}
	public static String checkNameWhithNoWhile(String msg){
		Scanner reader = new Scanner(System.in);
		String name= null;
		boolean flag= true;
		
			name= reader.nextLine();
			if (name.length()>20)
				System.out.println(msg);
			else
				flag= false;
		
		return name;
	}
	public static String chek_date( String msg){
		Scanner reader = new Scanner(System.in);
		String dateToValidate = null;
		boolean flag = false;
		while(!flag){
			dateToValidate= reader.nextLine();
		if(dateToValidate == null){
			System.out.println(msg);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		
		try {
			flag = true;;
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			
			return dateToValidate;
		
		} catch (Exception e) {
			
			System.out.println(msg);
			flag = false;
			
			
		}
		}
		return "";
		
	}
	public static String checkNameP(String msg){
		Scanner reader = new Scanner(System.in);
		String name= null;
		boolean flag= true;
		while (flag){
			name= reader.nextLine();
			if (name.length()>50)
				System.out.println(msg);
			else
				flag= false;
		}
		return name;
	}
	
	public static double checkDoub(String msg){
		String opt= null;
		double ans=0;
		Scanner reader = new Scanner(System.in);
		boolean flag= true;
				while (flag){
				opt = reader.next();
			try{
				ans=Double.parseDouble(opt);
				flag= ans<0;
			}catch (Exception e){
				
			}
			if (flag)
				System.out.println(msg);
		}
		return ans;
	}
	
	public static double checkDisc(String msg){
		String opt= null;
		double ans=0;
		Scanner reader = new Scanner(System.in);
		boolean flag= true;
				while (flag){
				opt = reader.next();
			try{
				ans=Double.parseDouble(opt);
				if (ans==0)
					flag= false;
				if(ans>0 && ans<100)
					flag= false;
			}catch (Exception e){
				
			}
			if (flag)
				System.out.println(msg);
		}
		return ans;
	}
	
	public static void delay(){
		long start = new Date().getTime();
		while(new Date().getTime() - start < 1000L){}
	}
	
	
	
	
}
