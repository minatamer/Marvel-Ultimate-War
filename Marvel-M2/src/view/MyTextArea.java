package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class MyTextArea extends JTextArea {

    private Image img;
    
    public MyTextArea(String image) {
    	super();
    	 try{
             img = ImageIO.read(new File(image));
         } catch(IOException e) {
             System.out.println(e.toString());
         }
    }
    
    


 
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img,0,0,null);
        super.paintComponent(g);
        this.repaint();
    }
}