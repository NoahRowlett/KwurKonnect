package kwurKonnect;

import com.jcraft.jsch.*;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class klient{

	private int status;
	private int lport;
	private String rhost;
	private int rport;
	private String username;
	private String password;
	private Session sesh;
	
	public klient (String user, String pass){
		username = user;
		password = pass;
		status = 0;
	}

	public void runKonnect() throws JSchException{
		status = 1;
		JSch jsch=new JSch();
		status = 2;
		String host="kwurmail.wustl.edu";

		status = 3;
		lport=4600;
		rhost="unicron";
		rport=80;

		sesh=jsch.getSession(username, host, 26000);
		sesh.setPassword(password);
		
		status = 4;
		java.util.Properties config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");
		sesh.setConfig(config);
		
		status = 5;
		sesh.connect();
		if(sesh.isConnected())
			status = 6;
		else
			status = 7;

		sesh.setPortForwardingL(lport, rhost, rport);
		
	}

	public int getStatus() {
		return status;
	}
	
	public boolean isConnected(){
		return sesh.isConnected();
	}
	
	public void disconnect(){
		sesh.disconnect();
	}
	
	

}
