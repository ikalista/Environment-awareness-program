package components;

import java.util.Scanner;

import com.zeroc.Ice.Communicator;
import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.*;


//��Ӧ����client�ļ�

public class EnviroAppUI
{
	/*�����Allsensor�������ݺ󣬴�CM��UI��������*/

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
						System.out.println("Please enter name of item of interest: ");//��Ҫ�ĸ���������Ϣ �������� ���� ��Ϣ cm_files.txt����
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
						System.out.println("Please enter name of item of interest: ");//��Ҫ�ĸ���������Ϣ �������� ���� ��Ϣ cm_files.txt����
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
						System.out.println("Please enter name of item of interest: ");//��Ҫ�ĸ���������Ϣ �������� ���� ��Ϣ cm_files.txt����
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
	/*�����Allsensor�������ݺ󣬴�CM��UI��������*/
	
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
			//�������г������
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
				System.out.println("Please enter name of item of interest: ");//��Ҫ�ĸ���������Ϣ �������� ���� ��Ϣ cm_files.txt����
				Scanner scan3 = new Scanner(System.in); 
				String item_of_interest_selection = scan3.nextLine(); 
				String message = showPositionDatailWorker.printString(item_of_interest_selection);
				System.out.println(message);
				break; 
		    case "2" :
		    	break; 
		    case "E":
			       //���ע��Ȼ���˳�
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