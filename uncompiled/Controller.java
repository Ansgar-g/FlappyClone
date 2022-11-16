package gamePackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JLabel;

public class Controller extends JLabel{

	//linked list that holds all Pipes
	LinkedList<Pipe> p = new LinkedList<Pipe>(); 
	//Pipe Template
	Pipe TempPipe;
	//Main instance 
	Main main;
	
	//Constructor
	public Controller(Main main) {
		
		//main
		this.main = main;
	}
	
	//tick method
	public void tick() {
		
		//for every Pipe in Linked list p 
		for(int i = 0; i < p.size(); i++) {
			
			//set TempPipe p.get(i)
			TempPipe = p.get(i);	
			
			//call plusPoint method 
			main.plusPoint();
		
			//determine rather player collides with Pipe or border. using intersects method 
			if(main.ufo.intersects(TempPipe) || main.ufo.getY() >= main.border.getY() - main.ufo.getHeight()) {
				
				//set gameLoopBool to false
				main.gameLoopBool = false; 
				main.panel.add(main.gameOver);
			}
			
			//call moveToPlayer 
			TempPipe.moveToPlayer();
		}
	}
	
	//method for adding a Pipe to the linked list 
	public void addPipe(Pipe block) {
		
		p.add(block);
	}
	
	//method for removing a Pipe
	public void removePipe(Pipe block) {
		
		//remove Pipe from linked list
		p.remove(block);
		//remove Pipe from panel
		main.panel.remove(block);
		
	}
}