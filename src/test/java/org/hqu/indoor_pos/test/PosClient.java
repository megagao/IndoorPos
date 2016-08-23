package org.hqu.indoor_pos.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PosClient {
	
	public static void main(String[] args) {
		
		int i = 0;
		while(i<1500){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Socket socket = null;  
			        DataOutputStream os = null;  
			        DataInputStream is = null;
					try {  
			            socket = new Socket("localhost", 50006);  
			            
			            while(true){
			            	
			            	Thread.sleep(100);
			            	os = new DataOutputStream(socket.getOutputStream()); 
			            	os.write(("10001,-67;10001,-67;10001,-67;10004,-86;10001,-66;10001,-67;10003,-80;10002,-83;10001,-66;10001,-68;10001,-66;10001,-65;10004,-81;10001,-64;10003,-77;10002,-72;10001,-64;10001,-64;10001,-63;10001,-64;869511023026822"+"\n").getBytes());
			            	//System.out.println(is.readUTF());
			            	
			            	
			            }
			           /* is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
			            Object obj = is.readObject();  */
			            
			        } catch(IOException ex) {  
			            ex.printStackTrace(); 
			        } catch (InterruptedException e) {
						e.printStackTrace();
					} finally {  
			            try {  
			            } catch(Exception ex) {}  
			            try {  
			                os.close();  
			            } catch(Exception ex) {}  
			            try {  
			                socket.close();  
			            } catch(Exception ex) {}  
			        }  
				}
					
			}).start();
			i++;
		}	
		System.out.println("`````````````````````ready!````````````````````````````````````");
	}
}