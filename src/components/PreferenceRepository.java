package components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.*;

import com.zeroc.Ice.Communicator;

public class PreferenceRepository
{
	private Communicator communicator;
	private static String [][] preference;
	private static String filename;
	
	
	private static class showPreferenceRepositoryI implements showPreferenceRepository
	{
	    public String getPreferenceList(String s, com.zeroc.Ice.Current current)
	    {
	        return preference[0][0]+","+preference[0][1]+","+preference[0][2]+","+preference[0][3]+","+preference[0][4]+","+preference[0][5]+";"+preference[1][0]+","+preference[1][1]+","+preference[1][2]+","+preference[1][3]+","+preference[1][4]+","+preference[1][5];
	    }
	}
	
    public static void main(String[] args)
    {
    	//读取txt文件
    	read_txt();
    	//读取txt文件
    	
    	
    	Communicator communicator = com.zeroc.Ice.Util.initialize(args);
    	
    	System.out.println("Server starting.");
        
        com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10010");
        com.zeroc.Ice.Object object = new showPreferenceRepositoryI();
        adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));
        adapter.activate();
        
        System.out.println("Adapter activated. Waiting for data.");
        communicator.waitForShutdown();
        
        System.out.println("Server ending");
	}
    private static void read_txt() {
    	//预设值测试
		filename = "D:\\Program Files\\Eclipse\\eclipse-workspace\\NewProject\\preference_file.txt";
		preference =  new String[2][6];
		try {
		 	File file = new File(filename);
			Scanner myReader1 = new Scanner(file);
			int count = 0;
			while (myReader1.hasNextLine()) {
				String line = myReader1.nextLine();
				if (line.startsWith("name:")) {
					preference[count][0] = line.substring(6);
				}else if (line.startsWith("Medical Condition Type: ")) {
					preference[count][1] = line.replace("Medical Condition Type: ","");
				}else if (line.startsWith("pref-1: ")) {
					preference[count][2] = line.substring(13, 15);
					preference[count][3] = line.substring(24, line.indexOf("/"));
				}else if (line.startsWith("pref-2: ")) {
					preference[count][4] = line.substring(25, line.indexOf("/"));
				}else if (line.startsWith("pref-3: ")) {
					preference[count][5] = line.substring(29, line.indexOf("/"));
					count ++;
				}else{
				}
			}
			myReader1.close();
	 	} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
	 	}
    }
}