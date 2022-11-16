package gamePackage;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main implements KeyListener{

	//make ufo
	String playerPath = "/home/ansgar/Desktop/eclipse/eclipse/gameInJ/src/gamePackage/flappys/flappyAlt.png";
	String playerUpPath = "/home/ansgar/Desktop/eclipse/eclipse/gameInJ/src/gamePackage/flappys/flappyAltUp.png";
	String pipePath = "/home/ansgar/Desktop/pipe.png";
	String pipePathRotated = "/home/ansgar/Desktop/pipeRotated.png";
	Player ufo = new Player(100, 100, 210, 149, playerPath, this);
	//Controller instance
	private Controller c = new Controller(this);
	//random instance
	Random random = new Random();
	//true if player jumps
	static boolean jumpbool;
	//false if player dies
	boolean gameLoopBool = true;
	//all static swing components
	JLabel border;
	JPanel panel;
	JLabel points;
	JLabel gameOver;
	//variable that holds players points
	int pointsCount;
		
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		
		//initialize JFrame. JPanel is displayed on it 
		JFrame frame = new JFrame();
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(null);
		
		//create instance of Main
		Main m = new Main();
				
		//set values of JPanel. Other components are displayed on it 
		m.panel = new JPanel();
		m.panel.setSize(1920, 1080);
		m.panel.setLayout(null);
		m.panel.setBackground(Color.darkGray);
		
		//set values of border
		m.border = new JLabel();
		m.border.setBounds(10, 900, 3000, 10);
		m.border.setBackground(Color.white);
		m.border.setOpaque(true);
		
		//set values for points
		m.points = new JLabel("0");
		m.points.setBounds(1800, -150, 400, 400);		
		m.points.setForeground(Color.white);
		m.points.setFont(new Font("Serif", Font.PLAIN, 70));
		
		m.gameOver = new JLabel("Fucking Opfer XD");
		m.gameOver.setBounds(100, 100, 3000, 600);		
		m.gameOver.setForeground(Color.white);
		m.gameOver.setFont(new Font("Serif", Font.PLAIN, 150));
		
		//add all components to panel
		m.panel.add(m.points);
		m.panel.add(m.ufo);
		m.panel.add(m.border);

		//add panel to frame
		frame.add(m.panel);
		
		//start gameLoop and the creation of Pipes
		m.gameLoop();
		m.createBothPipes();
		
		//set location and visibility of frame 
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
		
		//add key listener to frame 
		frame.addKeyListener(new Main());

	}
	
	 public int hilfJump = 0;
	
	//game loop method
	public void gameLoop() throws AWTException {
	
		//initialization of Robot
		final Robot robot = new Robot();
		//create game loop timer. 40ms 
		Timer gameLoopTimer = new Timer(1000/30, (e) -> {
			
			//don't know why I need this but it stops the lag 
			robot.mouseRelease(0); //some event
			
			//if jumpbool == true
			if(jumpbool == true) {
				
				hilfJump++;
				
				if (hilfJump < 10 ) {
				
					//set y from ufo -1
					ufo.jump(-12);
					System.out.println(hilfJump);
						
				}else if(hilfJump >= 10){
				
					//set jumpbool back to false so ufo.jump is not called forever 
					hilfJump = 0;
					jumpbool = false;
					ufo.setIcon(new ImageIcon(playerPath));	
				}
			}
			//if jumpbool == false
			if(jumpbool != true) {	
				//set y from ufo + 12
				ufo.fall(12);	
			}

			//do this when game is over 
			if(gameLoopBool == false) {
				//stop timer so that game is ended 
				((Timer)e.getSource()).stop();
			}
			
			//call tick from c object 
			c.tick();
		});
		
		//start the timer
		gameLoopTimer.start();
	}
	
	//TODO: give player a point each time he passes a Pipe
	public void plusPoint() {
	
		if(ufo.getX() > c.TempPipe.getX()) {
				
			int oldX = ufo.getX();
			int currentX = ufo.getX(); 
			
			if(currentX + oldX == 10) {
				points.setText(Integer.toString(Integer.parseInt(points.getText()) + 1));		
			}
		}
	}
	
	//method for creating Pipe on the top
	public void createNewPipeTop() {
		
		//random number between 0 & 150 - 700 (as lowest value)
		int xOfPipeTop = random.nextInt(150) - 700;
		//add new Pipe to c
		c.addPipe(new Pipe(1920, xOfPipeTop, 220, 852, new ImageIcon(pipePathRotated), this));
		//needs to be called ones before adding TempPipe to panel 
		c.tick();		
		//add TempPipe to panel
		panel.add(c.TempPipe);
	}
	
	//method for creating Pipe on the bottom
	public void createNewPipeBot() {
		
		//random number between 0 & 150 + 600 (as highest value)
		int xOfPipeBot = random.nextInt(150) + 600;
		//add new Pipe to c
		c.addPipe(new Pipe(1920, xOfPipeBot, 220, 852, new ImageIcon(pipePath), this));
		//needs to be called ones before adding TempPipe to panel 
		c.tick();
		//add TempPipe to panel
		panel.add(c.TempPipe);
	}
	
	//method for combining createNewPipeTop & Bot
	public void createBothPipes() {
		
		createNewPipeTop();
		createNewPipeBot();

		//timer 3 seconds
		Timer timer2 = new Timer(2000, (e) -> {		
			
			if(gameLoopBool) {
				//both methods
				createNewPipeTop();
				createNewPipeBot();	
			}
			
			//do this when game is over 
			if(gameLoopBool == false) {
				//stop timer so that game is ended 
				((Timer)e.getSource()).stop();
			}
		});
		//start timer
		timer2.start();	
		
	}
	
	//key listeners
	@Override
	public void keyTyped(KeyEvent e) {
		 
		//if pressed button is space 
		if(e.getKeyChar() == ' ') {
			
			//set jumpbool to true
			jumpbool = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}