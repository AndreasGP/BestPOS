package ee.ut.math.tvt.vapradjailusad;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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



public class IntroUI {
	
	JFrame raam = new JFrame("BestPos");
	
	 public IntroUI() {
	        
		 	raam.setSize(640, 480);
		 	
		 	
		 	JPanel panel = new JPanel();
		 	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 	
		 	panel.add(Box.createRigidArea(new Dimension(50,50)));
		 	
		 	
	        JLabel nameLabel = new JLabel();        
	        JLabel leader1Label = new JLabel("Team leader:");                
	        JLabel leaderLabel = new JLabel();        	        
	        JLabel emailLabel = new JLabel();        	        
	        JLabel memberLabel = new JLabel("Team members:");        	    	        
	        JLabel membersLabel = new JLabel();        
	        
	        
	        try(BufferedReader br = new BufferedReader(new FileReader("application.properties"))) {
	            nameLabel.setText(br.readLine());
	            leaderLabel.setText(br.readLine());
	            emailLabel.setText(br.readLine());
	            membersLabel.setText(br.readLine());
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
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
				logo = ImageIO.read(new File("assets/logo.jpg"));
				 JLabel picLabel = new JLabel(new ImageIcon(logo));
				 panel.add(picLabel);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Version		
			JLabel versionLabel = new JLabel();        
	        
	        try(BufferedReader br = new BufferedReader(new FileReader("version.properties"))) {
	            String line = br.readLine();
	            while (line != null) { 
	            	if(line.length()>3) {
	                	versionLabel.setText(line);
	                }
	                line = br.readLine();
	                
	            }
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        panel.add(versionLabel);
	       
	       

	        raam.add(panel);
	        raam.setVisible(true);

	       

	        raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	
}
