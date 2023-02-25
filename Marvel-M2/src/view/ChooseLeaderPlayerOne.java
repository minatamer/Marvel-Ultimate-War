package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import engine.Game;
import engine.Player;
import model.world.Champion;
import model.world.Hero;
import model.world.Villain;

public class ChooseLeaderPlayerOne extends JFrame implements ActionListener {
	
	JLabel chooser;
	JPanel buttonsPanel;
	ArrayList<JButton> team;
	Player playerOne;
	Player playerTwo;
	Clip clip;

	public ChooseLeaderPlayerOne(Player playerOne , Player playerTwo, Clip clip) {
		this.clip=clip;
		team = new ArrayList<JButton>();

		
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 400);
		
		
		
		chooser  = new JLabel();
		chooser.setText(playerOne.getName() + " should choose only one leader");
		chooser.setFont(new Font(null, Font.BOLD, 20));
		chooser.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(chooser , BorderLayout.NORTH);
		
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(0, 3));
		add(buttonsPanel, BorderLayout.CENTER); 
		
		
		
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
		
		
		
		
		
		
		
		
		
		
		for(Champion c : playerOne.getTeam()) {
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
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			button.setForeground(Color.black);
			button.setBackground(Color.white);
			button.setFont(new Font(null, Font.BOLD, 20));
			buttonsPanel.add(button);
			team.add(button);
			button.addActionListener(this);
		}
		
		this.setVisible(true);
		
				
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String heroInfo = "This Champion is of type Hero, a Hero can deal 50% more damage to Villains and AntiHeros" + "\n" +
				 "A Hero can use this leader ability once per game:" + "\n" + "Heros remove any debuffs from their team and applies an Embrace effect on them for 2 turns" + "\n" +
				"Embrace effect increases Mana & HP permenantly and temporarily increases Speed & Attack Damage ";
		
		String villainInfo = "This Champion is of type Villain, a Villain can deal 50% more damage to Heros and AntiHeros" + "\n" +
		"A Villain can use this leader ability once per game:" + "\n" + "Villains knockout any enemy champion that is below 30% of their maximum HP";
		
		String antiheroInfo = "This Champion is of type AntiHero, an AntiHero can deal 50% more damage to Heros and Villains" + "\n" + 
		"An AntiHero can use this leader ability once per game:" + "\n" + "AntiHeros stun all champions on the board except the leaders for 2 turns";
		
		
		
		JButton championButton = (JButton) e.getSource();
		int champIndex = team.indexOf(championButton);
        Champion champ = playerOne.getTeam().get(champIndex);
        int answer;
        if(champ instanceof Hero) {
        	answer = JOptionPane.showConfirmDialog(null, heroInfo + "\n" + "Do you want this champion to be your leader?" , "Info", JOptionPane.YES_NO_OPTION);
        }
        else
        	if(champ instanceof Villain) {
        		answer = JOptionPane.showConfirmDialog(null, villainInfo + "\n" + "Do you want this champion to be your leader?" , "Info", JOptionPane.YES_NO_OPTION);
        	}
        	else
        		answer = JOptionPane.showConfirmDialog(null, antiheroInfo + "\n" + "Do you want this champion to be your leader?" , "Info", JOptionPane.YES_NO_OPTION);
        
        
        if(answer==0) {
        	 playerOne.setLeader(champ);
     		this.dispose();
     		ChooseLeaderPlayerTwo choose = new ChooseLeaderPlayerTwo(playerOne , playerTwo, clip);
        	
        }
        
        
       
		
		
	}
	
	


}
