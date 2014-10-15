package ee.ut.math.tvt.vapradjailusad;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class IntroUI extends JFrame {
	
	Properties applicationProps = new Properties(); 
	Properties versionProps = new Properties();
	
	
        
        

	 public IntroUI() {
	        
		 	setSize(640, 480);
		 	
		 	
		 	JPanel panel = new JPanel();
		 	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 	
		 	panel.add(Box.createRigidArea(new Dimension(50,50)));
		 	
		 	
	        JLabel nameLabel = new JLabel();        
	        JLabel leader1Label = new JLabel("Team leader:");                
	        JLabel leaderLabel = new JLabel();        	        
	        JLabel emailLabel = new JLabel();        	        
	        JLabel memberLabel = new JLabel("Team members:");        	    	        
	        JLabel membersLabel = new JLabel();        
	        
	        	InputStream inputStream = this.getClass().getClassLoader()
	        	        .getResourceAsStream("application.properties");
	        	try {
					applicationProps.load(inputStream);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	nameLabel.setText(applicationProps.getProperty("name"));
	        	leaderLabel.setText(applicationProps.getProperty("leader"));
	        	emailLabel.setText(applicationProps.getProperty("leadermail"));
	        	membersLabel.setText(applicationProps.getProperty("members"));
	            
	        
	        panel.add(nameLabel);
	        panel.add(Box.createRigidArea(new Dimension(0,10)));
	        panel.add(leader1Label);
	        panel.add(leaderLabel);
	        panel.add(emailLabel);
	        panel.add(Box.createRigidArea(new Dimension(0,10)));
	        panel.add(memberLabel);
	        panel.add(membersLabel);
	        panel.add(Box.createRigidArea(new Dimension(0,10)));
	        
	        
	        //Logo
	        BufferedImage logo;
			try {
				logo = ImageIO.read(getClass().getResource("/assets/logo.jpg"));				
				 JLabel picLabel = new JLabel(new ImageIcon(logo));
				 panel.add(picLabel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Version		
			JLabel versionLabel = new JLabel(); 
			
			InputStream inputStream2 = this.getClass().getClassLoader()
        	        .getResourceAsStream("version.properties");
        	try {
				versionProps.load(inputStream2);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
        	versionLabel.setText(versionProps.getProperty("build.number"));
	        
	        panel.add(versionLabel);
	       
	       

	        add(panel);
	    }
	
}
