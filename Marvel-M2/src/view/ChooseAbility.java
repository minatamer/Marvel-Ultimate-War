package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.Game;
import exceptions.AbilityUseException;
import exceptions.NotEnoughResourcesException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;

import java.util.*;
public class ChooseAbility extends JFrame implements ActionListener {
	Game game;
	ShowGame showGame;
	ArrayList<JButton> abilities; 
	public ChooseAbility(Game game,ShowGame showGame) {
		this.game=game;
		this.showGame=showGame;
		abilities= new ArrayList<JButton>();
		this.setSize(300,300);
		this.setLayout(new FlowLayout());
		for(int i=0;i<game.getCurrentChampion().getAbilities().size();i++) {
			JButton temp=new JButton();
			temp.setText(game.getCurrentChampion().getAbilities().get(i).getName());
			temp.setFont(new Font("Ariel",Font.BOLD,20));
			temp.setBackground(Color.YELLOW);
			abilities.add(temp);
			this.add(temp);
			if(game.getCurrentChampion().getAbilities().get(i).getCurrentCooldown()!=0)
				temp.setEnabled(false);
			temp.addActionListener(this);}
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton ability= (JButton)e.getSource();
		int index= abilities.indexOf(ability);
		Ability a= game.getCurrentChampion().getAbilities().get(index);
		int answer = JOptionPane.showConfirmDialog(null, a.toString() + "\n" + "Do you want to use this ability?" , "Info", JOptionPane.YES_NO_OPTION);
		if(answer ==0) {
			AreaOfEffect areaOfEffect= a.getCastArea();
			switch(areaOfEffect) {
			case TEAMTARGET: case SELFTARGET:case SURROUND:
				try {
					game.castAbility(a);
					showGame.updatePanels(showGame.getPanelHolder(),game.getBoard());
					showGame.updateCurrentInfo();
					showGame.autoEndTurn();
					showGame.showTurnOrder();
					this.dispose();
				} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}break;
			case SINGLETARGET: new ChoosePoint(game, showGame, a);this.dispose();
				try {
					showGame.updatePanels(showGame.getPanelHolder(),game.getBoard());
					showGame.showTurnOrder();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}//
			showGame.updateCurrentInfo();
			showGame.autoEndTurn();
			showGame.showTurnOrder();
			break;
			case DIRECTIONAL: new ChooseDirectionAbility(game, showGame,a);this.dispose();
				try {
					showGame.updatePanels(showGame.getPanelHolder(),game.getBoard());
					showGame.showTurnOrder();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//
			showGame.updateCurrentInfo();
			showGame.showTurnOrder();
			showGame.autoEndTurn();
			break;
			default:break;			
	}}
		
	}
}
