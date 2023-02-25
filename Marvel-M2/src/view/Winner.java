package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import engine.Player;
import javax.swing.Timer;

public class Winner extends JFrame {
	final int PANEL_WIDTH = 900;
	final int PANEL_HEIGHT = 900;
	JLabel winner;
	Timer timer;
	int xVelocity = 1;
	int yVelocity = 1;
	int x = 0;
	int y = 0;
	Clip clip;
	
	
	public Winner(Player player) throws InterruptedException  {
		
		playMusic("birthday.wav");
		this.setLayout(new GridLayout(3,1));
		this.setSize(900,900);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Icon gif = new ImageIcon("thanos.gif");
		
		
		winner=new JLabel();
		winner.setIcon(gif);
		winner.setOpaque(true);
		
		
		JLabel winner2=new JLabel();
		winner2.setIcon(gif);
		winner2.setFont(new Font("Ariel",Font.BOLD,50));
		winner2.setOpaque(true);
		
		JLabel winner3=new JLabel();
		winner3.setIcon(gif);
		winner3.setFont(new Font("Ariel",Font.BOLD,50));
		winner3.setOpaque(true);
		
		JLabel winner4=new JLabel();
		winner4.setIcon(gif);
		winner4.setFont(new Font("Ariel",Font.BOLD,50));
		winner4.setOpaque(true);
	
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,2));
		north.add(winner);
		north.add(winner2);
		this.add(north);
		
		
		
		JLabel mid = new JLabel();
		mid.setText("  "+player.getName().toUpperCase()+" IS THE WINNER");
		mid.setFont(new Font("Ariel",Font.BOLD,50));
		mid.setHorizontalAlignment(SwingConstants.CENTER);
		mid.setBackground(Color.white);
		mid.setOpaque(true);
		this.add(mid);
		
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,2));
		south.add(winner3);
		south.add(winner4);
		this.add(south);
		
		
		
		this.setVisible(true);

		this.repaint();
		this.revalidate();

		
	
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
	
	
	}


	


