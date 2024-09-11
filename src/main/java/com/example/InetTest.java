package com.example;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class InetTest {

	public InetTest() {
	}
	public static void main(String[] args) {
		InetAddress inetAddress ;
    	try{
    		inetAddress = InetAddress.getLocalHost();
    		System.out.println("inetAddress =>" + inetAddress);
    		NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
    		String serverMac = new String(network.getHardwareAddress());
    		String serverIp = inetAddress.getHostAddress();
    		System.out.println(serverMac + "=>" + serverIp);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	}

}
