package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Game;
import engine.Player;

public class PlayerNames extends JFrame implements ActionListener{

	JButton button;
	JTextField textField1;
	JTextField textField2;
	Image backgroundImage;
	JLayeredPane layer;
	 Clip clip;
	
	public PlayerNames(){
		
		//backgroundImage = new ImageIcon("marvel2.png").getImage();
		backgroundImage = new ImageIcon("big ironman.jpg").getImage();
		
		playMusic("theme.wav");
		
	
		button = new JButton("Ready!");
		button.setBounds(950, 375, 100, 50);
		button.addActionListener(this);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		button.setFont(new Font("Times New Roman",Font.BOLD,15));
		
		
		textField1 = new JTextField();
		textField1.setBounds(400, 300, 250 ,75);
		textField1.setFont(new Font("Times New Roman",Font.BOLD,20));
		textField1.setForeground(Color.white);
		textField1.setBackground(Color.black);
		textField1.setCaretColor(Color.white);
		textField1.setText("Player One Name");
		
		textField2 = new JTextField();
		textField2.setBounds(400, 400, 250, 75);
		textField2.setFont(new Font("Times New Roman",Font.BOLD,20));
		textField2.setForeground(Color.white);
		textField2.setBackground(Color.black);
		textField2.setCaretColor(Color.white);
		textField2.setText("Player Two Name");
		
		
		layer = new JLayeredPane();
		layer.setSize(2000,2000);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(2000, 2000);
		this.add(layer);
		layer.add(button , JLayeredPane.DRAG_LAYER);
		
		this.add(textField1);
		this.add(textField2);
		this.setVisible(true);
		//this.setResizable(false);
		
		this.repaint();
		this.revalidate();
	}
	
	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
		

		
	} 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(textField1.getText().isBlank() || textField2.getText().isBlank() ) {
			JOptionPane.showMessageDialog(null, "Please enter your player names", "Error", JOptionPane.PLAIN_MESSAGE);
		}
		else {
			
			if(e.getSource()==button) {
				textField1.setEditable(false);
				textField2.setEditable(false);
			
						
				try {
					ChooseChamps chooseChamps = new ChooseChamps(textField1.getText(),textField2.getText(), clip);}
				catch (IOException e1) {
					e1.printStackTrace();}
				//clip.stop();
				this.dispose();
			}
			
		}
		
	
		
	}
	

	public void playMusic(String musicLocation)
	{
	    try
	    {
	       File musicPath=new File(musicLocation);
	       if(musicPath.exists())
	       {
	           AudioInputStream audioInput =  AudioSystem.getAudioInputStream(musicPath);
	           clip = AudioSystem.getClip();
	           clip.open(audioInput);
	           clip.start();
	           clip.loop(Clip.LOOP_CONTINUOUSLY);
	  
	       }
	       else
	       
	           System.out.println("Can't find file"); }
	    catch(Exception ex)
	    {
	       ex.printStackTrace();
	
	    }}
	
	public static void main (String [] args) {
		new PlayerNames();
	}
	
}
