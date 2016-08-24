package org.hqu.indoor_pos.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A GUI program that is used for indoor-position test.
 * @author Gao
 *
 */
public class DispMain extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/*An image of interior that will show in the background*/
	private Image image;
	
	/*The initial width of this image*/
	public int iniWScale;
	
	/*The initial height of this image*/
	private int iniHScale;
	
	/*The base offset that makes target device transform to the coordinate system of displayer*/
	private double baseW;
	private double baseH;
	
	/*The origin location of our target environment*/
	private int basePointW;
	private int basePointH;
	
	/*The size of the target device profile which will show in the frame*/
	private int radius = 3; 
	
	/*The real location of the target device with respect the origin*/
	double loc[] =new double[2];
	
	public static void main(String[] args){
		
		DispMain disp = new DispMain();
		disp.lauchFrame();
		
		Thread connThread =new Thread(disp.new ConnThread());  //�������ӷ������߳�
		connThread.start();
		
	}
	
	public void lauchFrame() {
		
		/*Get the size of screen*/
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int screenWidth = (int) screenSize.getWidth();
		
		/*Set the initial value of this frame.*/
		this.setSize(screenWidth/2, screenHeight/2+200);
		this.setLocation(400, 150);
		this.setTitle("DrawTest");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		
		add(new DrawComponent());
		
		iniWScale = 600;
		iniHScale = 395;
		
		/*Get the image from filepath.*/
		image = Toolkit.getDefaultToolkit().getImage("image\\test.jpg");
		
		new Thread(new PaintThread()).start();
	}
	

	
	private class ConnThread implements Runnable{
		
		@Override
		public void run() {
			
			Socket socket = null;  
	        ObjectOutputStream os = null;  
	        ObjectInputStream is = null; 
	        
			try {  
				
	            socket = new Socket("localhost", 50005);  
	            
	            while(true){
	            	
	            	Thread.sleep(1000);
	            	
	            	is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	            	loc = (double[]) is.readObject();
	            	System.out.println(loc[0]);
	            	System.out.println(loc[1]);
	            	
	            }
	            
	        } catch(IOException ex) {  
	            ex.printStackTrace(); 
	        } catch (Exception e) {
				e.printStackTrace();
			} finally {  
	            try {  
	                is.close();  
	            } catch(Exception ex) {}  
	            try {  
	                socket.close();  
	            } catch(Exception ex) {}  
	        }  
		}
	}

	
class DrawComponent extends JComponent{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			
			/*The actual width and height of image that will display in the program*/
			int width;
			int height;
			
			/*The cornor point of the location where the image display.*/
			int LocPointX;
			int LocPointY;
			
			Graphics2D g2 = (Graphics2D) g;
			Dimension size = this.getSize();  
/*System.out.println(size.height);
System.out.println(size.width);*/
			if(size.width<1066){
			   width = size.width-200;
			   height = width*iniHScale/iniWScale;
			}else{
				width = 1066;
				height = 1066*iniHScale/iniWScale;
			}
			LocPointX = (size.width-width)/2;
			LocPointY = (size.height-height)/2;
			g2.drawImage(image,LocPointX, LocPointY,width,height, this);
			
			Ellipse2D circle = new Ellipse2D.Double();
			
			basePointW = (width*((600-486)/2))/600+LocPointX;
			basePointH = (height*((395-256)/2))/395+LocPointY;
			
			baseW =  (480*width/(16.0*600));
			baseH =  (268*height/(9.0*395));
			circle.setFrameFromCenter(basePointW, basePointH, basePointW+radius, basePointH+radius);
			Ellipse2D circle1 = new Ellipse2D.Double();
			circle1.setFrameFromCenter(loc[0]*baseW+basePointW, loc[1]*baseH+basePointH, loc[0]*baseW+basePointW+radius, loc[1]*baseH+basePointH+radius);
			g2.setPaint(Color.red);
			g2.draw(circle);
			g2.fill(circle);
			g2.draw(circle1);
			g2.fill(circle1);
			   
		}
	}
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				System.out.println(loc[0]);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
