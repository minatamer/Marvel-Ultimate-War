package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.Game;
import exceptions.AbilityUseException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;

public class ChoosePoint extends JFrame implements ActionListener{
	Game game;
	ShowGame showGame;
	Ability a;
	public ChoosePoint(Game game, ShowGame showGame, Ability a) {
		this.game= game;
		this.showGame=showGame;
		this.a=a;
		this.setSize(500,500);
		this.setLayout(new GridLayout(5,5));
		for(int i=5;i>0;i--) {
			for(int j=1;j<=5;j++) {
				JButton button = new JButton(i+" , "+j);
				this.add(button);
				button.addActionListener(this);
				
			}
		}
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		int x= Integer.parseInt(""+button.getText().charAt(0));
		x--;
		int y= Integer.parseInt(""+button.getText().charAt(4));
		y--;
		try {
			game.castAbility(a, x, y);
			showGame.updatePanels(showGame.getPanelHolder(),game.getBoard());
			showGame.updateCurrentInfo();
			showGame.autoEndTurn();
			showGame.showTurnOrder();
		} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
				| CloneNotSupportedException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.dispose();
		
		
	}

}
