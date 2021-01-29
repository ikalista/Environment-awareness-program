package components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.zeroc.Ice.Communicator;
import com.zeroc.demos.IceStorm.allSensors2ContextManager.Demo.*;

public class LocationServer
{
	private static String [][] location;
	private static String filename;
	
	
	public static class allSensors2ContextManagerI implements AllSensors2ContextManager
	{
        @Override
        public void tick1(String aqi_message, com.zeroc.Ice.Current current)
        {
        	
        }
        public void tick2(String temperature_message, com.zeroc.Ice.Current current)
        {
            
        }
        //这个是来自Weather alarm的方法
        public void tick3(String weather_message, com.zeroc.Ice.Current current)
        {
            
        }
        public void tick4(String location_message, com.zeroc.Ice.Current current) {
        }
    }
	
	
    public static void main(String[] args)
    {
    	
    }
}