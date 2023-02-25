package view;
import engine.*;
import model.world.Champion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;



public class ChooseChamps extends JFrame implements ActionListener{
	
	ArrayList<JButton> allChampions;
	JPanel buttonsChampion;
	JLabel playerOneText;
	JLabel playerTwoText;
	Player playerOne;
	Player playerTwo;
	int turn;
	Image backgroundImage;
	Clip clip;

	
	
	
	public ChooseChamps(String pOne , String pTwo, Clip clip) throws IOException {
		
		this.clip=clip;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 800);
		
		backgroundImage =  new ImageIcon("marvel3.jpg").getImage();
		this.revalidate();
		this.repaint();
		


		playerOneText = new JLabel();
		playerOneText.setSize(100, 100);
		playerOneText.setFont(new Font(null, Font.BOLD, 20));
		playerOneText.setForeground(Color.blue);
		playerOneText.setHorizontalAlignment(SwingConstants.CENTER);
		playerOneText.setText(pOne +  " should pick one champion");
		this.add(playerOneText , BorderLayout.NORTH);

		
		playerTwoText = new JLabel();
		playerTwoText.setSize(100, 100);
		playerTwoText.setFont(new Font(null, Font.BOLD, 20));
		playerTwoText.setHorizontalAlignment(SwingConstants.CENTER);
		playerTwoText.setText(pTwo + " should pick one champion");
		playerTwoText.setForeground(Color.red);
		playerTwoText.setVisible(false);
		this.add(playerTwoText , BorderLayout.SOUTH);
		
		
        
		
	
		
		
     	buttonsChampion = new JPanel();
		buttonsChampion.setLayout(new GridLayout(0, 3));
		add(buttonsChampion, BorderLayout.CENTER); 
		
		allChampions = new ArrayList<JButton>();
		
		turn=1;
	
		playerOne = new Player(pOne);
		playerTwo = new Player(pTwo);
	    Game game = new Game (playerOne , playerTwo);
		
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		
		Icon icon1 = new ImageIcon("hulk2.jpg");
		Icon icon2 = new ImageIcon("deadpool2.jpg");
		Icon icon3 = new ImageIcon("spiderman2.jpg");
		Icon icon4 = new ImageIcon("captain2.jpg");
		Icon icon5 = new ImageIcon("ironman2.jpg");
		Icon icon6 = new ImageIcon("electro2.jpg");
		Icon icon7 = new ImageIcon("drstrange2.jpg");
		Icon icon8 = new ImageIcon("thor2.jpg");
		Icon icon9 = new ImageIcon("ghostrider2.jpg");
		Icon icon10 = new ImageIcon("hela2.jpg");
		Icon icon11 = new ImageIcon("loki2.jpg");
		Icon icon12 = new ImageIcon("venom2.jpg");
		Icon icon13 = new ImageIcon("yellowjacket2.jpg");
		Icon icon14 = new ImageIcon("quicksilver2.jpg");
		Icon icon15 = new ImageIcon("iceman2.jpg");
		
		
		
		for(Champion c : Game.getAvailableChampions()) {
			String text = c.getName();
			JButton button;
			if(text.equals("Hulk"))
				button = new JButton(text , icon1);
			else
			if(text.equals("Spiderman"))
				button = new JButton(text , icon3);
			else
			if(text.equals("Deadpool"))
				button = new JButton(text , icon2);
			else
			if(text.equals("Ironman"))
				button = new JButton(text , icon5);
			else
			if(text.equals("Dr Strange"))
				button = new JButton(text , icon7);
			else
			if(text.equals("Electro"))
				button = new JButton(text , icon6);
			else
			if(text.equals("Captain America"))
				button = new JButton(text , icon4);
			else
			if(text.equals("Ghost Rider"))
				button = new JButton(text , icon9);
			else
			if(text.equals("Loki"))
				button = new JButton(text , icon11);
			else
			if(text.equals("Hela"))
				button = new JButton(text , icon10);
			else
			if(text.equals("Quicksilver"))
				button = new JButton(text , icon14);
			else
			if(text.equals("Thor"))
				button = new JButton(text , icon8);
			else
			if(text.equals("Venom"))
				button = new JButton(text , icon12);
			else
			if(text.equals("Yellow Jacket"))
				button = new JButton(text , icon13);
			else
				button = new JButton(text , icon15);
			
			
			
			
			
			
			button.setFont(new Font(null, Font.BOLD, 20));
			button.setForeground(Color.black);
			button.setBackground(Color.white);
			//button.setSize(100, 100);
			button.addActionListener(this);
			buttonsChampion.add(button);
			allChampions.add(button);
		}


		this.setVisible(true);
		
		
	}
	
/*	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(backgroundImage,0,0,null);
		
	} */
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton championButton = (JButton) e.getSource();
		int champIndex = allChampions.indexOf(championButton);
        Champion champ = Game.getAvailableChampions().get(champIndex);
        int answer = JOptionPane.showConfirmDialog(null, champ.toString() + "\n" + "Do you want this champion in your team?" , "Info", JOptionPane.YES_NO_OPTION);

    
        
        if (turn==1 && answer==0) {
        	championButton.setEnabled(false);;
        	playerOne.getTeam().add(champ);
        	turn=2;
        	playerOneText.setVisible(false);
        	playerTwoText.setVisible(true);
        	this.repaint();
    
        }
        else if(turn==2 && answer==0){
        	championButton.setEnabled(false);
        	playerTwo.getTeam().add(champ);
        	turn=1;
        	playerTwoText.setVisible(false);
        	playerOneText.setVisible(true);
        	this.repaint();

        	
        }
        
        if(playerTwo.getTeam().size()==3) {
        	this.dispose();
        	ChooseLeaderPlayerOne chooseleaders = new ChooseLeaderPlayerOne(playerOne , playerTwo, clip );
        	//ChooseLeadersPlayer1 chooseleaders = new ChooseLeadersPlayer1(playerOne, playerTwo);
        }

		
		
	}

}
