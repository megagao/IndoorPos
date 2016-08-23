package org.hqu.indoor_pos.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientMain {

	public static void main(String[] args) {
		
		Socket socket = null;  
        DataOutputStream os = null;  
        DataInputStream is = null;
		try {  
            socket = new Socket("localhost", 5006);  
            
            while(true){
            	
            	//Thread.sleep(1000);
            	is = new DataInputStream(socket.getInputStream()); 
            	System.out.println(is.readUTF());
            	
            	
            }
           /* is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
            Object obj = is.readObject();  */
            
        } catch(IOException ex) {  
            ex.printStackTrace(); 
        } /*catch (InterruptedException e) {
			e.printStackTrace();
		} */finally {  
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
}
