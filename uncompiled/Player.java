package gamePackage;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel{

	Main main;
	
	//constructor
	Player(int x, int y, int width, int height, String playerImage, Main main){
		
		//set bounds, the icon and Opaque of Player instance
		this.setBounds(x, y, width, height);
		this.setIcon(new ImageIcon(playerImage));
		this.setOpaque(false);
		this.main = main;
	}
	
	//fall method
	public void fall(int fallHeight) {
		
		//set y of this instance to + 12
		this.setLocation(this.getX(), this.getY() + fallHeight);
	}
	
	//jump method
	public void jump(int jumpHeight) {
		
		//set y of this instance to -1
		this.setLocation(this.getX(), this.getY() + jumpHeight);
		this.setIcon(new ImageIcon(main.playerUpPath));
	}

	//collision detection method that gives back boolean
	public boolean intersects(Pipe p) {
		
		//width and height of this instance
		int tw = this.getWidth() - 50;
		int th = this.getHeight() - 50;
		//width and height of parameter Pipe 
		int rw = p.getWidth();
		int rh = p.getHeight();
		
		//if one of the previous was 0, return false
		if(rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		//x and y of this instance 
		int tx = this.getX();
		int ty = this.getY();
		//x and y of parameter Pipe
		int rx = p.getX();
		int ry = p.getY();
		
		//add parameter Pipes width + x 
		rw += rx;
		//add parameters height + y
		rh += ry;
		//add this instances width + x
		tw += tx;
		//add this instances height + y 
		th += ty;
		
		//check if this instance and parameter Pipe collide and return boolean
		return ((rw < rx || rw > tx) &&
				(rh < ry || rh > ty) &&
				(tw < tx || tw > rx) &&
				(th < ty || th > ry));	
	}
}
