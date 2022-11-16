package gamePackage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pipe extends JLabel{

	//constructor 
	Pipe(int x, int y, int width, int height, ImageIcon ic, Main main){
		
		//set bounds, the icon and Opaque of Pipe instance
		this.setBounds(x, y, width, height);
		this.setIcon(ic);
		this.setOpaque(false);
	}
	
	//method to move Pipe towards Player 
	public void moveToPlayer() {
		
		//set Pipes x - 10
		this.setLocation(this.getX() - 10, this.getY());
	}
}