package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;

public class ShowGame extends JFrame implements ActionListener, MouseListener {
	JPanel panel1;
	JTextArea panel2;
	JPanel panel3;
	JTextArea panel4;
	JPanel panel5;
	JLabel panel6;
	JLabel panel7;
	Object[][] showBoard;

	
	JPanel voicePanel;
	JLabel info;
	JLabel voiceDetected;
	
	JButton command;
	JButton record;
	
	JPanel[][] panelHolder;
	Player playerOne;
	Player playerTwo;
	Game game;
	JLabel name1;
	JLabel name2;	
	Clip clip;
	Icon cover;
	Icon empty;
	JLabel leaderAbilityTwo;
	JLabel leaderAbilityOne;
	BufferedImage bufImg;
	LiveSpeechRecognizer recognize;

	
	public ShowGame(Player playerOne , Player playerTwo) throws InterruptedException {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		
		
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("7403.dic");
		configuration.setLanguageModelPath("7403.lm");
		try {
			recognize=new LiveSpeechRecognizer(configuration);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		
		panelHolder = new JPanel[5][5];
		game = new Game(playerOne, playerTwo);
		showBoard= game.getBoard();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize);
		//this.setLayout(new BorderLayout(10,10));
		this.setVisible(true);
		
		playMusic("game theme.wav");
		

		panel1 = new MyJPanel("galaxy north.jpg");
		panel1.setOpaque(false );
		
		cover = new ImageIcon("cover.jpg");
		empty = new ImageIcon("galaxy empty 2.jpg");

		

		
		//panel2 = new MyTextArea("ironman right.png");
		panel2 = new MyTextArea("galaxy right 2.jpg");
		panel2.setForeground(Color.white);
		panel2.setBackground(new Color(1,1,1, (float) 0.01));
		panel2.setOpaque(false);
		
		
		
		

		//panel3 = new MyJPanel("background2.png");
		panel3 = new MyJPanel("galaxy south.jpg");
		panel3.setOpaque(false);
		
		//panel4 =new MyTextArea("ironman left.png");
		panel4 = new MyTextArea("galaxy left.jpg");
		panel4.setForeground(Color.white);
		panel4.setBackground(new Color(1,1,1, (float) 0.01));
		panel4.setOpaque(false);
		//panel4.paintComponents(getGraphics());   
		
	

		
		panel5 = new JPanel();
		panel6 = new JLabel();
		panel7 = new JLabel();
		
		
		panel2.setEditable(false);
		panel4.setEditable(false);
		 
		panel1.setPreferredSize(new Dimension(100,100));
		panel2.setPreferredSize(new Dimension(200,100));
		panel3.setPreferredSize(new Dimension(150,100));
		panel4.setPreferredSize(new Dimension(200,100));
		panel5.setPreferredSize(new Dimension(100,100));
		
		showTurnOrder();
		
		GridLayout grid= new GridLayout(5,5);
		panel5.setLayout(grid);
		JPanel temp; 
		int i = 5;
		int j = 5;
   
		for(int m = 0; m < i; m++) {
			for(int n = 0; n < j; n++) {
		    	temp=  new JPanel();
		    	temp.setBorder(BorderFactory.createLineBorder(Color.white));
		    	panelHolder[m][n] = temp;
		    	panel5.add(temp,m,n);
		    	temp.addMouseListener(this);
		    }}
		updatePanels(panelHolder, showBoard);
  
		 panel1.setLayout(new FlowLayout());
		 panel3.setLayout(new FlowLayout());
		 
		 command=new JButton("VOICE COMMAND");
		 command.setFont(new Font("Ariel",Font.BOLD,20));
		 command.addActionListener(this);
		 
		 record=new JButton("CLICK ME TO START TALKING");
		 record.setFont(new Font("Ariel",Font.BOLD,10));
		 record.addActionListener(this);
		 
		 panel6.setText("Leader Ability Available");
		 panel6.setForeground(Color.cyan);
		 panel7.setText("Leader Ability Available");
		 panel7.setForeground(Color.red);
		 name1 = new JLabel (playerOne.getName()+"   ");
		 name1.setFont(new Font("Ariel",Font.BOLD,25));
		 name2 = new JLabel ("   "+playerTwo.getName());
		 name2.setFont(new Font("Ariel",Font.BOLD,25));
		 updatePlayerTurn();
		 name1.setForeground(Color.cyan);
		 name2.setForeground(Color.red);
		 
		 voicePanel = new JPanel();
		 voicePanel.setLayout(new GridLayout(3,1));
		    info = new JLabel();
			info.setText("With your voice, you can say the following: MOVE UP/DOWN/RIGHT/LEFT  ,, ATTACK UP/DOWN/RIGHT/LEFT ,, ENDTURN ,, CAST ABILITY ,, USE LEADER ABILITY");
			info.setHorizontalAlignment(SwingConstants.CENTER);
			info.setFont(new Font(null, Font.BOLD, 15));
			voicePanel.add(info);
			voiceDetected = new JLabel();
			voiceDetected.setHorizontalAlignment(SwingConstants.CENTER);
			voiceDetected.setFont(new Font(null, Font.BOLD, 15));
			voicePanel.add(voiceDetected);
			voicePanel.add(record);

		 
			
			leaderAbilityOne = new JLabel("LeaderAbility Available      ");
			leaderAbilityOne.setFont(new Font("Ariel",Font.BOLD,25));
			leaderAbilityOne.setForeground(Color.cyan);
			
			leaderAbilityTwo = new JLabel("        LeaderAbility Available");
			leaderAbilityTwo.setFont(new Font("Ariel",Font.BOLD,25));
			leaderAbilityTwo.setForeground(Color.red );
			
		
		panel3.add(leaderAbilityOne);  
		panel3.add(name1);
		 panel3.add(command);
		 panel3.add(name2);
		 panel3.add(leaderAbilityTwo); 
		 
		
		 panel2.setText("This "+Type(game.getCurrentChampion())+" "+ isLeader(game.getCurrentChampion())+"\n" +game.getCurrentChampion().toString()+"\n" + "Condition: " + game.getCurrentChampion().getCondition() );
		 panel2.setFont(new Font("Ariel",Font.BOLD,9 ));
		 panel2.setBorder(BorderFactory.createLineBorder(Color.black));	 
		 
		 this.add(panel1,BorderLayout.NORTH);
		 this.add(panel2,BorderLayout.EAST);
		 this.add(panel3,BorderLayout.SOUTH);
		 this.add(panel4,BorderLayout.WEST);
		 this.add(panel5,BorderLayout.CENTER);
		 
		 
		 
		 
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
		 
		 
	}
	

	
	
	public String isLeader(Champion c) {
		if(c.equals((Champion)game.getFirstPlayer().getLeader())||c.equals((Champion)game.getSecondPlayer().getLeader()))
			return "Is the leader";
		else
			return "Is not the leader";
		}
	
	public Icon addImage(Champion c) {
		
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
		
		if(c.getName().equals("Hulk"))
			return icon1;
		else if(c.getName().equals("Deadpool"))
			return icon2;
		else if(c.getName().equals("Spiderman"))
			return icon3;
		else if(c.getName().equals("Captain America"))
			return icon4;
		else if(c.getName().equals("Ironman"))
			return icon5;
		else if(c.getName().equals("Electro"))
			return icon6;
		else if(c.getName().equals("Dr Strange"))
			return icon7;
		else if(c.getName().equals("Thor"))
			return icon8;
		else if(c.getName().equals("Ghost Rider"))
			return icon9;
		else if(c.getName().equals("Hela"))
			return icon10;
		else if(c.getName().equals("Loki"))
			return icon11;
		else if(c.getName().equals("Venom"))
			return icon12;
		else if(c.getName().equals("Yellow Jacket"))
			return icon13;
		else if(c.getName().equals("Quicksilver"))
			return icon14;
		else 
			return icon15;
		
	}
	
	
	
	public void updatePanels(JPanel[][] panelHolder, Object[][] board) throws InterruptedException {
		if(game.checkGameOver()!=null) {
			this.dispose();
			clip.stop();
			new Winner(game.checkGameOver());
			
			return;}
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				JLabel hp=new JLabel();
				if(board[i][j] instanceof Cover) {
					//panelHolder[i][j].setBackground(Color.green);
					hp.setText(((Cover)board[i][j]).getCurrentHP()+"");
					hp.setFont(new Font("Plain",Font.BOLD,16));
					panelHolder[i][j].removeAll();
					panelHolder[i][j].addMouseListener(this);//in case removeAll removes listener
					panelHolder[i][j].add(hp);
					JLabel jj= new JLabel();//
					jj.setIcon(cover);//
					panelHolder[i][j].add(jj);//
					}
				else if(board[i][j] instanceof Champion) {
					if(playerOne.getTeam().contains((Champion)board[i][j])) {
						panelHolder[i][j].setBackground(new Color(100,149,237));
						hp.setText(((Champion)board[i][j]).getCurrentHP()+"");
						hp.setFont(new Font("Plain",Font.BOLD,16));
						panelHolder[i][j].removeAll();
						panelHolder[i][j].addMouseListener(this);
						panelHolder[i][j].add(hp);
						JLabel jj= new JLabel();//
						jj.setIcon(addImage((Champion)board[i][j]));//
						panelHolder[i][j].add(jj);//
					
					}
					else {
						panelHolder[i][j].setBackground(new Color(204,0,42));
						hp.setText(((Champion)board[i][j]).getCurrentHP()+"");
						hp.setFont(new Font("Plain",Font.BOLD,16));
						panelHolder[i][j].removeAll();
						panelHolder[i][j].addMouseListener(this);
						panelHolder[i][j].add(hp);      
				      	JLabel jj= new JLabel();//
					    jj.setIcon(addImage((Champion)board[i][j]));//
					    panelHolder[i][j].add(jj);//
					    }
				
				
				}
				else {
					panelHolder[i][j].setBackground(Color.white);
				    panelHolder[i][j].removeAll();
				    JLabel jj= new JLabel();//
				    jj.setSize( panelHolder[i][j].getWidth(), panelHolder[i][j].getHeight());
				    jj.setIcon(empty);//
				    panelHolder[i][j].add(jj);//
				    panelHolder[i][j].addMouseListener(this);
				    
				    
				    }
				
				panel5.add(panelHolder[i][j] , i , j);
				
			}
			}
		this.repaint();
		this.revalidate();
	}
	
	public void updatePlayerTurn() {
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion())) {
			name1.setEnabled(true);
			name2.setEnabled(false);}
		else {
			name2.setEnabled(true);
			name1.setEnabled(false);
		}
	}
	public String Type(Champion c) {
		if(c instanceof Hero)
			return "Hero";
		else
			if(c instanceof Villain)
				return "Villain";
			else
				return "AntiHero"; 
			
			
		
		
	}
	public void showTurnOrder() {
		panel1.removeAll();
		PriorityQueue temp=new PriorityQueue(6);
		while(!game.getTurnOrder().isEmpty()) {
			JLabel img= new JLabel();
			img.setIcon((addImage((Champion)game.getTurnOrder().peekMin())));
			temp.insert(game.getTurnOrder().remove());
			panel1.add(img);}
		while(!temp.isEmpty()) {
			game.getTurnOrder().insert(temp.remove());}
		updateCurrentInfo(); 
		this.revalidate();
		this.repaint();
	}
	
	
	
public void updateCurrentInfo() {
	 panel2.setText("This "+Type(game.getCurrentChampion())+" "+isLeader(game.getCurrentChampion())+"\n" +game.getCurrentChampion().toString() +"\n" + "Condition: " + game.getCurrentChampion().getCondition());
}

public void autoEndTurn() {
	if (game.getCurrentChampion().getCurrentActionPoints()==0) {
		game.endTurn();updatePlayerTurn();showTurnOrder();
        panel2.setText("This "+Type(game.getCurrentChampion())+" "+isLeader(game.getCurrentChampion())+"\n" +game.getCurrentChampion().toString() +"\n" + "Condition: " + game.getCurrentChampion().getCondition());
	}
}
 

@Override
public void actionPerformed(ActionEvent e) {
	JButton button = (JButton) e.getSource();
	if (button == command) {
		this.remove(panel3);
		this.add(voicePanel , BorderLayout.SOUTH);
		this.revalidate();
		this.repaint(); 
	}
	
	else
		if(button==record) {
			
			try {
				
				
				recognize.startRecognition(true);
				SpeechResult speechResult;
				
				boolean flag=false;
				if((speechResult=recognize.getResult())!=null ) {
					String command= speechResult.getHypothesis();
					voiceDetected.setText("What was heard: " + command);
					voicePanel.revalidate();   
					voicePanel.repaint(); 
					System.out.println(command);
					
					switch(command) {
					case "MOVE UP":
						try {
							game.move(Direction.UP);
							this.updatePanels(panelHolder,game.getBoard());
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.repaint();
							this.revalidate();  
							recognize.stopRecognition();	
							     
					     
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();	
						}
						flag=true;
						break;
					case "MOVE DOWN":
						try {
							game.move(Direction.DOWN);
							this.updatePanels(panelHolder,game.getBoard());
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition();
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "MOVE LEFT":
						try {
							game.move(Direction.LEFT);
							this.updatePanels(panelHolder,game.getBoard());//
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition();
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "MOVE RIGHT":	
						try {
							game.move(Direction.RIGHT);
							this.updatePanels(panelHolder,game.getBoard());
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition();    
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "ATTACK UP":
						try {
							game.attack(Direction.UP);
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.updatePanels(panelHolder,game.getBoard());
							this.showTurnOrder();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition(); 
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "ATTACK DOWN":	
						try {
							game.attack(Direction.DOWN);
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.updatePanels(panelHolder,game.getBoard());
							this.showTurnOrder();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition(); 
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "ATTACK LEFT":
						try {
							game.attack(Direction.LEFT);
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.updatePanels(panelHolder,game.getBoard());
							this.showTurnOrder();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition(); 
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "ATTACK RIGHT":
						try {
							game.attack(Direction.RIGHT);
							this.updateCurrentInfo();
							this.autoEndTurn();
							this.updatePanels(panelHolder,game.getBoard());
							this.showTurnOrder();
							this.repaint();
							this.revalidate();
							recognize.stopRecognition(); 
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "CAST ABILITY": 
						new ChooseAbility(game,this);
						this.updatePanels(panelHolder,game.getBoard());
						this.updateCurrentInfo();
						this.autoEndTurn();
						this.showTurnOrder();   
						this.repaint();
						this.revalidate();				
						flag=true;
						break;
					case "USE LEADER ABILITY": case "LEADER ABILITY" :  
						try {
							String heroInfo = "This Champion is of type Hero, a Hero can deal 50% more damage to Villains and AntiHeros" + "\n" +
									 "A Hero can use this leader ability once per game:" + "\n" + "Heros remove any debuffs from their team and applies an Embrace effect on them for 2 turns" + "\n" +
									"Embrace effect increases Mana & HP permenantly and temporarily increases Speed & Attack Damage ";
							
							String villainInfo = "This Champion is of type Villain, a Villain can deal 50% more damage to Heros and AntiHeros" + "\n" +
							"A Villain can use this leader ability once per game:" + "\n" + "Villains knockout any enemy champion that is below 30% of their maximum HP";
		   					
							String antiheroInfo = "This Champion is of type AntiHero, an AntiHero can deal 50% more damage to Heros and Villains" + "\n" + 
							"An AntiHero can use this leader ability once per game:" + "\n" + "AntiHeros stun all champions on the board except the leaders for 2 turns";
							Champion champ= game.getCurrentChampion();
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
					        	game.useLeaderAbility();
								updateCurrentInfo();
								showTurnOrder();
								autoEndTurn();
								recognize.stopRecognition(); 
								this.updatePanels(panelHolder, game.getBoard());
								if(game.isFirstLeaderAbilityUsed())
									leaderAbilityOne.setEnabled(false);
								if(game.isSecondLeaderAbilityUsed())
									leaderAbilityTwo.setEnabled(false);
					        }	
						} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
							autoEndTurn();
							} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						flag=true;
						break;
					case "END TURN": 	
						game.endTurn();updatePlayerTurn();showTurnOrder(); 
						recognize.stopRecognition();     
						flag=true;
						break;
						
					default: voiceDetected.setText("What was heard: " + command);
					record.setText("CLICK ME AGAIN BUT TRY TO SAY THE PHRASE CLEARER"); 
					voicePanel.revalidate();   
					voicePanel.repaint();  
					
						break;	
					}
					
					if (flag == true) {
						voiceDetected.setText("");
						record.setText("CLICK ME TO START TALKING");
						this.remove(voicePanel);
						this.add(panel3 , BorderLayout.SOUTH);
						recognize.stopRecognition();
						return; 
					}
					
					else {
						recognize.stopRecognition(); 
					}
				}	
				  
				return;
			} //catch (IOException e3) {
				//e3.printStackTrace();
			//    } 
		catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}   
		
			
		}
	
	
	
}

public JPanel[][] getPanelHolder() {
	return panelHolder;
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	panel4.setText(" ");
}
@Override
public void mouseEntered(MouseEvent e) {
	JPanel x=(JPanel) e.getSource();
	for(int i=0;i<5;i++) {
		for(int j=0;j<5;j++) {
			if(x==panelHolder[i][j]&&showBoard[i][j] instanceof Champion)   {
				Champion c= (Champion)showBoard[i][j];
				panel4.setText("This "+Type(c)+" "+isLeader((Champion)showBoard[i][j] )+"\n"+(Champion)showBoard[i][j] +"\n" + "Condition: " + ((Champion)showBoard[i][j]).getCondition());
			    panel4.setFont(new Font("Ariel", Font.BOLD ,9));
			    
			    
			}
	}}
	

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
           FloatControl gainControl =  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
           gainControl.setValue(-20.0f);
  
       }
       else
       
           System.out.println("Can't find file"); }
    catch(Exception ex)
    {
       ex.printStackTrace();

    }}
}
