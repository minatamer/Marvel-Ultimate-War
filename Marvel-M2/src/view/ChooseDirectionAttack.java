package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import engine.Game;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.world.Direction;



public class ChooseDirectionAttack extends JFrame implements KeyListener {
	Game game;
	ShowGame showGame;
	JTextPane info;
	Icon backgroundImage;
	JLabel image;
	
	public ChooseDirectionAttack(Game game,ShowGame showGame) {
		this.game=game;
		this.showGame=showGame;
		this.setSize(300,300);
		this.addKeyListener(this);
		this.setLayout(new GridLayout(2,1));
		this.setBackground(Color.white );
		this.setBackground(Color.white);
		
		backgroundImage = new ImageIcon("wasd 3.jpg");
		
		
		info=new JTextPane();
		image=new JLabel();
		
	    info.setBounds(0, 0, 300, 50);
	    info.setEditable(false);
	    info.setFocusable(false);
	    info.setText("You can move up,left,down,right    Using WASD Keys on your Keyboard");
	    info.setFont(new Font(null,Font.BOLD,15));
	    this.add(info);
	    
	    
	    	    
	
		image.setIcon(backgroundImage);
		image.setSize(image.getWidth(),image.getHeight());
		image.setBounds(0, 0, image.getWidth(), image.getHeight());
		this.add(image);
	
		

		this.setVisible(true);
		this.revalidate();
		this.repaint();
	
}

	
			
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyChar()){
	    case'a': 
	    	try {
				game.attack(Direction.LEFT);
			} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
			}
	    
	        break;
	    case'w':
	    	try {
				game.attack(Direction.UP);
			} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
			}
	    	
	        break;
	    case's':
	    	try {
				game.attack(Direction.DOWN);
			} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
			}
	    	
	        break;
	    case'd':try {
				game.attack(Direction.RIGHT);
			} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
			}
	    
	        break;
	    case 27 : this.dispose();
	    }
		try {
			showGame.updatePanels(showGame.getPanelHolder(),game.getBoard());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//
		showGame.showTurnOrder();
		showGame.updateCurrentInfo();
		showGame.autoEndTurn();
		this.dispose();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}