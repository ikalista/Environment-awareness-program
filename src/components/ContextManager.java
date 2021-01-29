package components;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.zeroc.Ice.Communicator;

import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.*;


public class ContextManager
{	
	private static String filename;
	private static String [][] places;
	private static int APO_count;
	private static String bryan_list[];
	private static String james_list[];
	private static int old_temp = 15;
	private static int new_temp;
	private static String currnet_location;
	private static String currnet_location_status;

	/*client 从cm_files里读取所有地点信息，然后返回*/
	public static class ShowPositionDatailI implements ShowPositionDatail
	{
	    public String printString(String interest_place, com.zeroc.Ice.Current current)
	    {
	    	String message;
			switch(interest_place){
				case "Indooroopilly Shopping Centre" :
					message = "Indooroopilly Shopping Centre is a major regional shopping centre in the western suburb\r\n" + 
							"of Indooroopilly, Brisbane, Queensland, Australia. It is the largest shopping centre in the western suburbs\r\n" + 
							"of Brisbane, by gross area, and contains the only Myer store in that region.\r\n" + 
							"services: cinema, restaurants, pool, shops";
				break; 
				case "Garden City" :
					message = "Garden City Shopping Centre is located 10km South of the Brisbane central business district\r\n" + 
							"(CBD) and includes Myer, David Jones, Hoyts Cinema, Freedom and over 230 specialty stores.\r\n" + 
							"services: cinema, restaurants, pool, shops";
					break; 
				case "South Bank Parklands" :
					message = "The South Bank Parklands area was created as part of the rejuvenation\r\n" + 
							"of the industrial water front undertaken for World Expo 1988. The Parklands area contains many shops, a\r\n" + 
							"cinema complex, and a large number of restaurants as well as a man-made beach. A river promenade\r\n" + 
							"stretches the length of South Bank Parklands.\r\n" + 
							"services: restaurants, pool, shops, Ferris wheel";
					break; 
				case "Brisbane City" :
					message = "The Brisbane central business district (CBD), or 'the City' is located on a point on the\r\n" + 
							"northern bank of the Brisbane River. The triangular shaped area is bounded by the Brisbane River to the\r\n" + 
							"east, south and west. The point, known at its tip as Gardens Point, slopes upward to the north-west where\r\n" + 
							"'the city' is bounded by parkland and the inner city suburb of Spring Hill to the north. The City is bounded\r\n" + 
							"to the north-east by the suburb of Fortitude Valley.";
					break; 
				case "Grey Street" :
					message = "Grey Street is south of the South Bank Parklands and hosts many restaurants and bars. The street \r\n" + 
							"is 1.4km in length and is home to attractions such as the Cineplex Cinema, Queensland Museum and Brisbane \r\n" + 
							"Convention & Exhibition Centre.";
					break; 
			    
			    default : 
			    	message = "No match found for item of interest.";
			}
			return message;
	    }
	}
	
	
	/*Allsensor to context manager*/
	// 从Allsensor 传到 Context Manager（本文件） 
	// message： message （用户名，sensor类型，值）
	public static class allSensors2ContextManagerI implements AllSensors2ContextManager
	{
        @Override
        public void tick1(String aqi_message, com.zeroc.Ice.Current current)
        {
    		System.out.println("Helloooooooooooooooooo");
        	APO_count++;
        	//aqi_message = aqi_message;
            System.out.println("从Allsensors 传过来的aqi_message： " + aqi_message);
            String bf[] = aqi_message.replace(" ","").split(",");
            int aqi = Integer.parseInt(bf[2]);
            int medecal_level = Integer.parseInt(bryan_list[1]);
            int threshold_time;
            if ( aqi<=50 & aqi >0) {
            	threshold_time = 15*medecal_level;
        	}else if (aqi > 50 & aqi<=100) {
            	threshold_time = 15*medecal_level;
            }else if (aqi > 100 & aqi<=150) {
            	threshold_time = 10*medecal_level;
            }else if (aqi > 150 & aqi<=200) {
            	threshold_time = 5*medecal_level;
            }else {
				threshold_time = 500;
			}
            if(APO_count>=threshold_time)		
            {
                method("aqi," + aqi +"," + currnet_location+","+ bryan_list[3]);
                APO_count = 0;
            }
            
        }
        public void tick2(String temperature_message, com.zeroc.Ice.Current current)
        {
            System.out.println("从Allsensors 传过来的temperature_message： " + temperature_message);
            String bf[] = temperature_message.replace(" ","").split(",");
            new_temp = Integer.parseInt(bf[2]);
            int yuzhi = Integer.parseInt(bryan_list[2]);
            if(old_temp!= new_temp) {
            	if (new_temp>yuzhi)
            	{
            	
	            	method("temperature," + new_temp+"," + currnet_location);
	            	
            	}
            	old_temp = new_temp;
            }
        }
        //这个是来自Weather alarm的方法
        public void tick3(String weather_message, com.zeroc.Ice.Current current)
        {
            System.out.println("从Weather Alarm 传过来的weather： " + weather_message);
            if(!weather_message.equals("0")) {
            	if(weather_message.equals("1")) {
            		method("weather,heavy rain"+"," + currnet_location);
            	}
            	else if(weather_message.equals("2")) {
            		method("weather,hail storm"+"," + currnet_location);
            	}
            	else if(weather_message.equals("3")) {
            		method("weather,strong wind"+"," + currnet_location);
            	}
            }
            
        }
        public void tick4(String location_message, com.zeroc.Ice.Current current)
        {
            System.out.println("从Location server传过来的Location： " + location_message);
            String bf[] = location_message.replace(" ","").split(",");
            currnet_location = bf[2];
            if (currnet_location.equals("A") | currnet_location.equals("B"))
            {
            	currnet_location_status = "indoor";
            }
            else if (currnet_location.equals("C") | currnet_location.equals("D"))
            {
            	currnet_location_status = "outdoor";
            }
        }
    }
	// 从Allsensor 传到 Context Manager（本文件） 
	// message： message （用户名，sensor类型，值）
	
	// 在没一次Allsonser远程调用tick1 and tick12时，在tick1 与tick12内部调用method方法
	// 根据由Preference De双向调用传回来的阈值列表，处理sensor数据，远程调用UI的打印警报方法传递警报。
	public static void method(String message) {
		com.zeroc.Ice.Communicator communicator1 = com.zeroc.Ice.Util.initialize();
  
        com.zeroc.Ice.ObjectPrx base = communicator1.stringToProxy("newid2:default -p 10008");
        Cm2UIwarningPrx cm2UIwarningworker = Cm2UIwarningPrx.checkedCast(base);
        
        if(cm2UIwarningworker == null)
        {
            throw new Error("Invalid proxy");
        }
        
        cm2UIwarningworker.printString(message);
	}
	// 根据由Preference De双向调用传回来的阈值列表，处理sensor数据，远程调用UI的打印警报方法传递警报。
	
	public static void read_txt() {
		//预设值测试
		filename = "cm_file.txt";
		places =  new String[5][4];
		try {
		 	File file = new File(filename);
			Scanner myReader1 = new Scanner(file);
			int count = 0;
			String information_bf = "";
			while (myReader1.hasNextLine()) {
				String line = myReader1.nextLine();
				if (line.startsWith("name:")) {
					places[count][0] = line.replace("name: ","");
					information_bf = "";
				}else if (line.startsWith("location: ")) {
					places[count][1] = line.replace("location: ","");
				}else if (line.startsWith("information:")) {
					information_bf = information_bf + line.replace("information: ","");
				}else if (line.startsWith("services:")) {
					places[count][2] = information_bf;
					places[count][3] = line.replace("services: ","");
					count ++;
				}else{
					information_bf = information_bf + line;
				}
			}
			myReader1.close();
	 	} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
	 	}
	}
	
	public static void load_prefer ()
	{
		Communicator communicator_PR = com.zeroc.Ice.Util.initialize();
        com.zeroc.Ice.ObjectPrx base = communicator_PR.stringToProxy("SimplePrinter:default -p 10010");
        showPreferenceRepositoryPrx showPreferenceRepositoryworker = showPreferenceRepositoryPrx.checkedCast(base);
        if(showPreferenceRepositoryworker == null)
        {
            throw new Error("Invalid proxy");
        }
        String list = showPreferenceRepositoryworker.getPreferenceList("");
        String[] bf = list.split(";");
        bryan_list = bf[0].split(",");
        james_list = bf[1].split(",");

		
	}
	
    public static void main(String[] args)
    {
    	//filename = args[0];
    	filename = "cm_file.txt";
        //读取cm_files.txt
    	read_txt();
        //读取cm_files.txt
    	
    	//读取preference
    	load_prefer();
    	//读取preference
    	
       
    	
    	//加载实时的位子是indoor还是outdoor
    	//get_location_status();
    	//加载实时的位子是indoor还是outdoor
        
    	//Context Manager 使用icestorm作为订阅者 接受Allsensor的每秒发布 温度和AP sensor参数
    	//订阅主题为AQI&TEMP
    	int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        com.zeroc.Ice.Communicator AS2CM_communicator = com.zeroc.Ice.Util.initialize(args, "configfiles\\config.sub", extraArgs);
        AS2CM_communicator.getProperties().setProperty("Ice.Default.Package", "com.zeroc.demos.IceStorm.allSensors2ContextManager");
        //
        // Destroy AS2CM_communicator during JVM shutdown
        //
        Thread destroyHook = new Thread(() -> AS2CM_communicator.destroy());
        Runtime.getRuntime().addShutdownHook(destroyHook);
        try
        {
            status = run(AS2CM_communicator, destroyHook, extraArgs.toArray(new String[extraArgs.size()]));

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            status = 1;
        }
        if(status != 0)
        {
            System.exit(status);
        }
        //Context Manager 使用icestorm作为订阅者 接受Allsensor的每秒发布 温度和AP sensor参数
    	
    	
       	
    	//UI向context manager请求感兴趣的地点的介绍//
    	
        com.zeroc.Ice.Communicator CM2UI_communicator = com.zeroc.Ice.Util.initialize(args);
    	
    	System.out.println("Server starting.");
        
        com.zeroc.Ice.ObjectAdapter adapter = CM2UI_communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10004");
        com.zeroc.Ice.Object object = new ShowPositionDatailI();
        
        adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));
        adapter.activate();
        
        System.out.println("Adapter activated. Waiting for data.");
        CM2UI_communicator.waitForShutdown();
        
        System.out.println("Server ending");
    	
    	/*UI 2 context manager 部分*/
        
    	//获取特定用户的偏好列表，如果有就返回列表，没有就返回None，进而可以确定用户是否存在
    	
    	
    	//Bryan_AQI = ;
    }
    
    public static void usage()
    {
        System.out.println("Usage: [--batch] [--datagram|--twoway|--ordered|--oneway] " +
                           "[--retryCount count] [--id id] [topic]");
    }

    private static int run(com.zeroc.Ice.Communicator communicator, Thread destroyHook, String[] args)
    {	
    	//System.out.println(args[0].toString());
    	args = communicator.getProperties().parseCommandLineOptions("AllSensors2ContextManager", args);
        String topicName_AQI_TEMP = "AQI&TEMP";
        String topicName_Location = "Location";
        String topicName_Weather = "Weather";
        String option = "None";
        boolean batch = false;
        String id = null;
        String retryCount = null;
        

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
        com.zeroc.IceStorm.TopicPrx topic_AQI_TEMP;
        com.zeroc.IceStorm.TopicPrx topic_Weather;
        
        try
        {
        	topic_AQI_TEMP = manager.retrieve(topicName_AQI_TEMP);
        }
        catch(com.zeroc.IceStorm.NoSuchTopic e)
        {
            try
            {
            	topic_AQI_TEMP = manager.create(topicName_AQI_TEMP);
            }
            catch(com.zeroc.IceStorm.TopicExists ex)
            {
                System.err.println("temporary failure, try again.");
                return 1;
            }
        }
        
        try
        {
        	topic_Weather = manager.retrieve(topicName_Weather);
        }
        catch(com.zeroc.IceStorm.NoSuchTopic e)
        {
            try
            {
            	topic_Weather = manager.create(topicName_Weather);
            }
            catch(com.zeroc.IceStorm.TopicExists ex)
            {
                System.err.println("temporary failure, try again.");
                return 1;
            }
        }
        
        com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Clock.Subscriber");

        //
        // Add a servant for the Ice object. If --id is used the
        // identity comes from the command line, otherwise a UUID is
        // used.
        //
        // id is not directly altered since it is used below to detect
        // whether subscribeAndGetPublisher can raise
        // AlreadySubscribed.
        //
        com.zeroc.Ice.Identity subId = new com.zeroc.Ice.Identity(id, "");
        if(subId.name == null)
        {
            subId.name = java.util.UUID.randomUUID().toString();
        }
        com.zeroc.Ice.ObjectPrx subscriber = adapter.add(new allSensors2ContextManagerI(), subId);

        //
        // Activate the object adapter before subscribing.
        //
        adapter.activate();

        java.util.Map<String, String> qos = new java.util.HashMap<>();
        //
        // Set up the proxy.
        //
        if(option.equals("Datagram"))
        {
            if(batch)
            {
                subscriber = subscriber.ice_batchDatagram();
            }
            else
            {
                subscriber = subscriber.ice_datagram();
            }
        }
        else if(option.equals("Twoway"))
        {
            // Do nothing to the subscriber proxy. Its already twoway.
        }
        else if(option.equals("Ordered"))
        {
            // Do nothing to the subscriber proxy. Its already twoway.
            qos.put("reliability", "ordered");
        }
        else if(option.equals("Oneway") || option.equals("None"))
        {
            if(batch)
            {
                subscriber = subscriber.ice_batchOneway();
            }
            else
            {
                subscriber = subscriber.ice_oneway();
            }
        }

        try
        {
            topic_AQI_TEMP.subscribeAndGetPublisher(qos, subscriber);
            topic_Weather.subscribeAndGetPublisher(qos, subscriber);
        }
        catch(com.zeroc.IceStorm.AlreadySubscribed e)
        {
            // This should never occur when subscribing with an UUID
            if(id == null)
            {
                e.printStackTrace();
                return 1;
            }
            System.out.println("reactivating persistent subscriber");
        }
        catch(com.zeroc.IceStorm.InvalidSubscriber e)
        {
            e.printStackTrace();
            return 1;
        }
        catch(com.zeroc.IceStorm.BadQoS e)
        {
            e.printStackTrace();
            return 1;
        }

        //
        // Replace the shutdown hook to unsubscribe during JVM shutdown
        //
        final com.zeroc.IceStorm.TopicPrx topicF_AQI_TEMP = topic_AQI_TEMP;
        final com.zeroc.IceStorm.TopicPrx topicF_Weather = topic_Weather;
        final com.zeroc.Ice.ObjectPrx subscriberF = subscriber;
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try
            {
            	topicF_AQI_TEMP.unsubscribe(subscriberF);
            	topicF_Weather.unsubscribe(subscriberF);
            }
            finally
            {
                communicator.destroy();
            }
        }));
        Runtime.getRuntime().removeShutdownHook(destroyHook); // remove old destroy-only shutdown hook

        return 0;
    }
    
}
