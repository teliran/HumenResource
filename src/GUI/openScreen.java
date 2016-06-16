package GUI;
import BL.IBL;
import Emp.Employee.Position;
import store.Store;

public  class openScreen {
	IBL bl;
	String[] menu;
	Boolean[] manager;
	Boolean[] storekeeper;
	public openScreen(IBL bl)
	{
		this.bl=bl;
		menu=new String[4];
		storekeeper=new Boolean[4];
		manager=new Boolean[]{true,true,false,true};
		storekeeper=new Boolean[]{true,true,true,false};
		menu[0]="quit";
		menu[1]="view/set/update suppliers information";
		menu[2]="place an order from a supplier/ view orders";
		menu[3]="print reports";
	}
	
	private void nextMenu(int opt)
	{
		supInfo spInfo= new supInfo(bl);
		MakeOrder mo= new MakeOrder(bl);
		Reports reports= new Reports(bl);
		switch (opt)
		{
		case 0: break;
		case 1: spInfo.run();
				break;
		case 2: mo.run();
				break;
		case 3: reports.run();
				break;
		}
	}
	
	public  void run()
	{	
		System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
 		System.out.println("Wellcome to the suppliers system!");
		System.out.println("Please choose an option and type the option’s number below:");
		int count=0,opt;
		if(Store.user.equals(Position.storeManager))
		{
			for(int i=0;i<menu.length;i++)
			{
				if(manager[i])
				{
					System.out.println(count + ". " + menu[i]);
					count++;
				}
			}
			opt= check.checkInt(0, count-1, "wrong input..." + '\n' + "please choose a number for one of the options above:");
			if(opt==2)
				opt++;
			nextMenu(opt);
		}
		else if(Store.user.equals(Position.storeKeeper))
		{
			for(int i=0;i<menu.length;i++)
			{
				if(storekeeper[i])
				{
					System.out.println(count + ". " + menu[i]);
					count++;
				}
			}
			opt= check.checkInt(0, count-1, "wrong input..." + '\n' + "please choose a number for one of the options above:");
			nextMenu(opt);
		}
		else
		{
			System.out.println("0. quit");
			opt= check.checkInt(0, count, "wrong input..." + '\n' + "please choose a number for one of the options above:");
			nextMenu(opt);
		}
	}
}
