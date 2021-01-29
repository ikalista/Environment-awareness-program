package components;

import java.util.Scanner;

import com.zeroc.Ice.Communicator;
import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.*;


//这应该是client文件

public class EnviroAppUI
{
	/*处理从Allsensor来的数据后，从CM向UI发出警告*/

	public static class cm2UIwarningI implements Cm2UIwarning
	{
		public void printString(String s, com.zeroc.Ice.Current current)
	    {
		    java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("yy/MM/dd HH:mm:ss");
			if(s.startsWith("temperature"))
			{
				System.out.println(s);
				String bf[] = s.replace(" ", "").split(",");
				System.out.println("--------------------" + date.format(new java.util.Date()) +"---------------------");
				System.out.println("Warning, TEMPERATURE is now " + bf[1]);
				System.out.println("Current location: " + bf[2]);
				System.out.println("Suggestion - please go to an indoor <user preferred location type e.g. pool> at one of these locations: <item of interest name 1>, <item of interest name 2>."); 
				System.out.println("");
				System.out.println("--Please select an option--: ");
				System.out.println("1. Search for information on a specific item of interest ");
				System.out.println("2. Search for items of interest in current location ");
				System.out.println("E. Exit");
				Scanner scan2 = new Scanner(System.in); 
				String main_Menu_choose = scan2.nextLine(); 
				switch(main_Menu_choose){
					case "1" :
						Communicator communicator1 = com.zeroc.Ice.Util.initialize();
				        com.zeroc.Ice.ObjectPrx base = communicator1.stringToProxy("SimplePrinter:default -p 10003");
				        showPositionDatailWorker = ShowPositionDatailPrx.checkedCast(base);
				        if(showPositionDatailWorker == null)
				        {
				            throw new Error("Invalid proxy");
				        }
						System.out.println("Please enter name of item of interest: ");//你要哪个地区的信息 输入名字 返回 信息 cm_files.txt内容
						Scanner scan3 = new Scanner(System.in); 
						String item_of_interest_selection = scan3.nextLine(); 
						String message = showPositionDatailWorker.printString(item_of_interest_selection);
						System.out.println(message);
						break; 
				    case "2" :
				    	break; 
				    case "E":
				    	System.exit(0);
				    	break; 
				    default : 
				    	
				}
			}
			if(s.startsWith("weather")) {
				System.out.println(s);
				String bf[] = s.replace(" ", "").split(",");
				
				System.out.println("--------------------" + date.format(new java.util.Date()) +"---------------------");
				System.out.println("Warning, EXTREME WEATHER is detected, the current weather alarm is " + bf[1]);
				System.out.println("Current location: " + bf[2]);
				System.out.println("Suggestion - please go to an indoor <user preferred location type e.g. pool> at one of these locations: <item of interest name 1>, <item of interest name 2>."); 
				System.out.println("");
				System.out.println("--Please select an option--: ");
				System.out.println("1. Search for information on a specific item of interest ");
				System.out.println("2. Search for items of interest in current location ");
				System.out.println("E. Exit");
				Scanner scan2 = new Scanner(System.in); 
				String main_Menu_choose = scan2.nextLine(); 
				switch(main_Menu_choose){
					case "1" :
						Communicator communicator1 = com.zeroc.Ice.Util.initialize();
				        com.zeroc.Ice.ObjectPrx base = communicator1.stringToProxy("SimplePrinter:default -p 10003");
				        showPositionDatailWorker = ShowPositionDatailPrx.checkedCast(base);
				        if(showPositionDatailWorker == null)
				        {
				            throw new Error("Invalid proxy");
				        }
						System.out.println("Please enter name of item of interest: ");//你要哪个地区的信息 输入名字 返回 信息 cm_files.txt内容
						Scanner scan3 = new Scanner(System.in); 
						String item_of_interest_selection = scan3.nextLine(); 
						String message = showPositionDatailWorker.printString(item_of_interest_selection);
						System.out.println(message);
						break; 
				    case "2" :
				    	break; 
				    case "E":
				    	System.exit(0);
				    	break; 
				    default : 
				    	
				}
			}
			
			if(s.startsWith("aqi")) {
				System.out.println(s);
				String bf[] = s.replace(" ", "").split(",");
				System.out.println("--------------------" + date.format(new java.util.Date()) +"---------------------");
				System.out.println("Warning, SIGNIFICANT AIR POLLUTION LEVEL is detected, the current AQI is "+ bf[1]);
				System.out.println("Current location: " + bf[2]);
				System.out.println("Suggestion - please go to an indoor <user preferred location type e.g. pool> at one of these locations: <item of interest name 1>, <item of interest name 2>."); 
				System.out.println("");
				System.out.println("--Please select an option--: ");
				System.out.println("1. Search for information on a specific item of interest ");
				System.out.println("2. Search for items of interest in current location ");
				System.out.println("E. Exit");
				Scanner scan2 = new Scanner(System.in); 
				String main_Menu_choose = scan2.nextLine(); 
				switch(main_Menu_choose){
					case "1" :
						Communicator communicator1 = com.zeroc.Ice.Util.initialize();
				        com.zeroc.Ice.ObjectPrx base = communicator1.stringToProxy("SimplePrinter:default -p 10003");
				        showPositionDatailWorker = ShowPositionDatailPrx.checkedCast(base);
				        if(showPositionDatailWorker == null)
				        {
				            throw new Error("Invalid proxy");
				        }
						System.out.println("Please enter name of item of interest: ");//你要哪个地区的信息 输入名字 返回 信息 cm_files.txt内容
						Scanner scan3 = new Scanner(System.in); 
						String item_of_interest_selection = scan3.nextLine(); 
						String message = showPositionDatailWorker.printString(item_of_interest_selection);
						System.out.println(message);
						break; 
				    case "2" :
				    	break; 
				    case "E":
				    	System.exit(0);
				    	break; 
				    default : 
				    	
				}
			}
			
	    }
		
	}
	/*处理从Allsensor来的数据后，从CM向UI发出警告*/
	
	//private  PrinterPrx printerWorker;
	private  static ShowPositionDatailPrx showPositionDatailWorker;
	
    public static void main(String[] args)
    {
    	System.out.println("Context-aware Enviro Smart Application\r\n");
		System.out.println("Please enter your user name:");
		Scanner scan1 = new Scanner(System.in); 
		String username = scan1.nextLine();
		if(!username.equals("James") & !username.equals("Bryan"))
		{
			System.out.println("Error: The provided name was not found. Please check the name, restart the user interface, and enter the name again.");
			//结束所有程序代码
			System.exit(0);
		}
        java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("MM/dd/yy HH:mm:ss:SSS");

		System.out.println("-------------------------" + date.format(new java.util.Date()) + "-------------------------");
		System.out.println(
			"Context-aware Enviro Smart Application Main Menu" + "\n\n" +
			"--Please select an option--: " + "\n" +
			"1. Search for information on a specific item of interest " + "\n" +
			"2. Search for items of interest in current location E. Exit"
		);	
		
		Scanner scan2 = new Scanner(System.in); 
		String main_Menu_choose = scan2.nextLine(); 

		switch(main_Menu_choose){
			case "1" :
				Communicator communicator1 = com.zeroc.Ice.Util.initialize(args);
		        com.zeroc.Ice.ObjectPrx base = communicator1.stringToProxy("SimplePrinter:default -p 10004");
		        showPositionDatailWorker = ShowPositionDatailPrx.checkedCast(base);
		        if(showPositionDatailWorker == null)
		        {
		            throw new Error("Invalid proxy");
		        }
				System.out.println("Please enter name of item of interest: ");//你要哪个地区的信息 输入名字 返回 信息 cm_files.txt内容
				Scanner scan3 = new Scanner(System.in); 
				String item_of_interest_selection = scan3.nextLine(); 
				String message = showPositionDatailWorker.printString(item_of_interest_selection);
				System.out.println(message);
				break; 
		    case "2" :
		    	break; 
		    case "E":
			       //解除注册然后退出
		    	break; 
		    default : 
		    	
		}
		build();
    }
    
    /*			
    "Context-aware Enviro Smart Application Main Menu" + "\n\n" +
	"--Please select an option--: " + "\n" +
	"1. Search for information on a specific item of interest " + "\n" +
	"2. Search for items of interest in current location E. Exit"
	*/
    private static void selection(ShowPositionDatailPrx showPositionDatailWorker) {
    	
    }
    
    private static void build()
    {
    	com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize();
		System.out.println("UI starting.");
		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("newid2Adapter", "default -p 10008");
		com.zeroc.Ice.Object object = new cm2UIwarningI();
		adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("newid2"));
		adapter.activate();
		
		System.out.println("Adapter activated. Waiting for data.");
		communicator.waitForShutdown();
		
		System.out.println("Server ending");
    }
}