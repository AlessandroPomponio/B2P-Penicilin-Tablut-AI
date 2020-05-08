package it.unibo.ai.didattica.competition.tablut.gui;

import javax.swing.JFrame;

import it.unibo.ai.didattica.competition.tablut.domain.State;

/**
 * 
 * This class represent an instrument that control the graphics
 * @author A.Piretti
 *
 */
public class Gui {
	
	Background frame;
	private int game;
	
	public Gui(int game) {
		super();
		this.game = game;
		initGUI();
		show();
	}
	
	
	/**
	 * Update the graphic whit a new state of the game
	 * @param aState represent the new state of the game
	 */
	public void update(State aState) {
		frame.setaState(aState);
		frame.repaint();
	}	
	
	/**
	 * Initialization
	 */
	private void initGUI() {
		switch (this.game) {
		case 1:
			frame = new BackgroundTablut();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(280, 300);
			break;
		case 2:
			frame = new BackgroundTablut();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(280, 300);
			break;
		case 3:
			frame = new BackgroundBrandub();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(280, 300);
			break;
		case 4:
			frame = new BackgroundTablut();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(280, 300);
			break;
		default:
			System.out.println("Error in GUI init");
			System.exit(4);
		}
	}
	
	/**
	 * Display the window
	 */
	private void show() {
		switch (game) {
		case 1:
			frame.setSize(370, 395);
			frame.setTitle("ClassicTablut");
			frame.setVisible(true);
			break;
		case 2:
			frame.setSize(370, 395);
			frame.setTitle("ModernTablut");
			frame.setVisible(true);
			break;
		case 3:
			frame.setSize(300, 330);
			frame.setTitle("Brandub");
			frame.setVisible(true);
			break;
		case 4:
			frame.setSize(370, 395);
			frame.setTitle("Tablut");
			frame.setVisible(true);
			break;
		default:
			System.out.println("Error in GUI show");
			System.exit(4);
		}
	}

}
