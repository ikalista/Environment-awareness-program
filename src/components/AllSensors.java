package components;

import java.util.Scanner;

import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.AllSensors2ContextManager;
import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.AllSensors2ContextManagerPrx;

import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors


public class AllSensors
{
 	private String[][] AQI_array;
 	private String[][] Location_array;
 	private String[][] Temperature_array;
 	private String username;
	
 	public AllSensors(String username) {
 		this.username = username;
 	}
 	
    public static void main(String[] args)
    {
    	AllSensors bryanAllSensors = new AllSensors("Bryan");
    	AllSensors JamesAllSensors = new AllSensors("James");
    	bryanAllSensors.read_txt();
    	JamesAllSensors.read_txt();
    	//根据用户名读取txt
    	
    	
        //Allsensor 使用icestorm作为发布者 向 Context Manager每秒发布 温度和AP sensor参数
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        //
        // Try with resources block - communicator is automatically destroyed
        // at the end of this try block
        //
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "configfiles\\config.sub", extraArgs))
        {
            communicator.getProperties().setProperty("Ice.Default.Package", "com.zeroc.demos.IceStorm.allSensors2ContextManager");
            //
            // Install shutdown hook to (also) destroy communicator during JVM shutdown.
            // This ensures the communicator gets destroyed when the user interrupts the application with Ctrl-C.
            //
            Runtime.getRuntime().addShutdownHook(new Thread(() -> communicator.destroy()));

            status = run(communicator, extraArgs.toArray(new String[extraArgs.size()]));
        }
        System.exit(status);
        //Allsensor 使用icestorm作为发布者 向 Context Manager每秒发布 温度和AP sensor参数
    }
    
    
    public void read_txt() {
	 	AQI_array = new String[3][2];
	 	Location_array = new String[3][2];
	 	Temperature_array = new String[3][2];
        try {
			File AQI_file = new File(username + "AQI.txt");
			File Location_file = new File(username +  "Location.txt");
			File Temperature_file = new File(username + "Temperature.txt");
			Scanner myReader1 = new Scanner( AQI_file);
			Scanner myReader2 = new Scanner( Location_file);
			Scanner myReader3 = new Scanner( Temperature_file);
			
			int count0 = 0;
			int count1 = 1;
			while (myReader1.hasNextLine()) {
				String data1 = " " +myReader1.nextLine();
				String[] bf = data1.replace(" ","").split(",");
				AQI_array[count0][0] = bf[0];
				AQI_array[count0][1] = bf[1];
				count0 ++;
				count1 ++;
			}
 			count0 = 0;
 			count1 = 1;
 			while (myReader2.hasNextLine()) {
 				String data2 = " " +myReader2.nextLine();
 				String[] bf = data2.replace(" ","").split(",");
 				Location_array[count0][0] = bf[0];
 				Location_array[count0][1] = bf[1];
 				count0 ++;
 				count1 ++;
 			}
 			count0 = 0;
 			count1 = 1;
 			while (myReader3.hasNextLine()) {
 				String data3 = " " +myReader3.nextLine();
 				String[] bf = data3.replace(" ","").split(",");
 				Temperature_array[count0][0] = bf[0];
 				Temperature_array[count0][1] = bf[1];
 				count0 ++;
 				count1 ++;
 			}
 			myReader1.close();
 			myReader2.close();
 			myReader3.close();
        } catch (FileNotFoundException e) {
 			System.out.println("An error occurred.");
 			e.printStackTrace();
        }
		
	}
    
    
    public static void usage()
    {
        System.out.println("Usage: [--datagram|--twoway|--oneway] [topic]");
    }

    private int run(com.zeroc.Ice.Communicator communicator, String[] args)
    {
        String option = "None";
        String topicName = "AQI&TEMP";
        //String topicName2 = "Location";
        int i;

        for(i = 0; i < args.length; ++i)
        {
            String oldoption = option;
            if(args[i].equals("--datagram"))
            {
                option = "Datagram";
            }
            else if(args[i].equals("--twoway"))
            {
                option = "Twoway";
            }
            else if(args[i].equals("--oneway"))
            {
                option = "Oneway";
            }
            else if(args[i].startsWith("--"))
            {
                usage();
                return 1;
            }
            else
            {
                topicName = args[i++];
                break;
            }

            if(!oldoption.equals(option) && !oldoption.equals("None"))
            {
                usage();
                return 1;
            }
        }

        if(i != args.length)
        {
            usage();
            return 1;
        }

        com.zeroc.IceStorm.TopicManagerPrx manager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(
            communicator.propertyToProxy("TopicManager.Proxy"));
        if(manager == null)
        {
            System.err.println("invalid proxy");
            return 1;
        }

        //
        // Retrieve the topic.
        //
        com.zeroc.IceStorm.TopicPrx topic;
        try
        {
            topic = manager.retrieve(topicName);
        }
        catch(com.zeroc.IceStorm.NoSuchTopic e)
        {
            try
            {
                topic = manager.create(topicName);
            }
            catch(com.zeroc.IceStorm.TopicExists ex)
            {
                System.err.println("temporary failure, try again.");
                return 1;
            }
        }

        //
        // Get the topic's publisher object, and create a Clock proxy with
        // the mode specified as an argument of this application.
        //
        com.zeroc.Ice.ObjectPrx publisher = topic.getPublisher();
        if(option.equals("Datagram"))
        {
            publisher = publisher.ice_datagram();
        }
        else if(option.equals("Twoway"))
        {
            // Do nothing.
        }
        else // if(oneway)
        {
            publisher = publisher.ice_oneway();
        }
        // proxy代理
        AllSensors2ContextManagerPrx clock = AllSensors2ContextManagerPrx.uncheckedCast(publisher);
        //ClockPrx clock2 = ClockPrx.uncheckedCast(publisher);

        System.out.println("publishing tick events. Press ^C to terminate the application.");

        try
        {
            //温度和AQI发给ContextManager
            // AQI_array
            // Temperature_array
            int value1 = Integer.parseInt( AQI_array[0][1]);
            int value2 = value1 + Integer.parseInt( AQI_array[1][1]);
            int value3 = value2 + Integer.parseInt( AQI_array[2][1]);
            
            int value4 = Integer.parseInt( Temperature_array[0][1]);
            int value5 = value1 + Integer.parseInt( Temperature_array[1][1]);
            
            int value7 = Integer.parseInt( Location_array[0][1]);
            int value8 = value7 + Integer.parseInt( Location_array[1][1]);
            int value9 = value8 + Integer.parseInt( Location_array[2][1]);
            
            
            int AQI_count = 0;
            int Temperature_count = 0;
            int Location_count = 0;
            while(true)
            {				
            	if( AQI_count <=  value1) {
            		//clock.tick(date.format(new java.util.Date()));
            		clock.tick1(username + ",AQI,"+ AQI_array[0][0]);
        		}
            	else if(AQI_count <= value2 && AQI_count > value1 ) {
            		clock.tick1(username + ",AQI,"+ AQI_array[1][0]);
            	}
            	else if(AQI_count <= value3 && AQI_count > value2 )
            	{
            		clock.tick1(username + ",AQI,"+ AQI_array[2][0]);
            	}
            	
            	if( Temperature_count <=  value4) {
            		//clock.tick(date.format(new java.util.Date()));
            		clock.tick2(username + ",Temperature,"+ Temperature_array[0][0]);
        		}
            	else if(Temperature_count <= value5 && Temperature_count > value4 ) {
            		clock.tick2(username + ",Temperature,"+ Temperature_array[1][0]);
            	}
            	
            	if( Location_count <=  value7) {
            		//clock.tick(date.format(new java.util.Date()));
            		clock.tick4(username + ",Location,"+ Location_array[0][0]);
        		}
            	else if(Location_count <= value8 && Location_count > value7 ) {
            		clock.tick4(username + ",Location,"+ Location_array[1][0]);
            	}
            	else if(Location_count <= value9 && Location_count > value8 )
            	{
            		clock.tick4(username + ",Location,"+ Location_array[2][0]);
            	}
            	
            	AQI_count++;
                if (AQI_count == value3)
                {
                	AQI_count = 0;
                }
                Temperature_count++;
                if (Temperature_count == value5)
                {
                	Temperature_count = 0;
                }
                Location_count++;
                if (Location_count == value9)
                {
                	Location_count = 0;
                }
                try
                {
                    Thread.currentThread();
                    Thread.sleep(1000);
                }
                catch(java.lang.InterruptedException e)
                {
                }
            }
        }
        catch(com.zeroc.Ice.CommunicatorDestroyedException ex)
        {
            // Ctrl-C triggered shutdown hook, which destroyed communicator - we're terminating
        }
        return 0;
    }
}