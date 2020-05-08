package it.unibo.ai.didattica.competition.tablut.tester;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import it.unibo.ai.didattica.competition.tablut.domain.*;
import it.unibo.ai.didattica.competition.tablut.domain.State.*;
import it.unibo.ai.didattica.competition.tablut.gui.Gui;

public class TestGuiFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gui theGui;
	private State state;
	private Game theGame;
	private Label l1;
	private Label l2;
	private Label l3;
	private Label l4;
	private JPanel pannello1;
	private JPanel pannello2;
	private JTextField nere;
	private JTextField bianche;
	private JTextField re;
	private JTextField azione;
	private JButton aggNere;
	private JButton aggBianche;
	private JButton aggRe;
	private JButton aggAzione;
	private JRadioButton turno;
	private JRadioButton turno2;
	
	
	
	public TestGuiFrame(int game) {
		
		super();
		
		switch (game) {
		case 1:
			Pawn board[][] = new Pawn[9][9];
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					board[i][j]=Pawn.EMPTY;
				}
			}
			board[4][4] = Pawn.THRONE;
			state = new StateTablut();
			theGame=new GameTablut(10);
			state.setBoard(board);
			break;
		case 2:
			Pawn board1[][] = new Pawn[9][9];
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					board1[i][j]=Pawn.EMPTY;
				}
			}
			board1[4][4] = Pawn.THRONE;
			state = new StateTablut();
			theGame=new GameModernTablut(10);
			state.setBoard(board1);
			break;
		case 3:
			Pawn board2[][] = new Pawn[7][7];
			for(int i=0;i<7;i++){
				for(int j=0;j<7;j++){
					board2[i][j]=Pawn.EMPTY;
				}
			}
			board2[3][3] = Pawn.THRONE;
			state = new StateBrandub();
			theGame=new GameTablut(10);
			state.setBoard(board2);
			break;	
		case 4:
			Pawn board3[][] = new Pawn[9][9];
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					board3[i][j]=Pawn.EMPTY;
				}
			}
			board3[4][4] = Pawn.THRONE;
			state = new StateTablut();
			state.setTurn(State.Turn.WHITE);
			theGame=new GameAshtonTablut(0, -1, "test", "testW", "testB");
			state.setBoard(board3);
			break;	
		default:
			System.out.println("Error in game selection");
			System.exit(4);
		}
		
		theGui=new Gui(game);
		theGui.update(state);
		
		pannello1 = new JPanel();
		pannello2 = new JPanel();
		nere = new JTextField("");
		turno = new JRadioButton("Nero");
		turno2 = new JRadioButton("Bianco");
		ButtonGroup bg = new ButtonGroup();
		bg.add(turno);
		bg.add(turno2);
		bianche = new JTextField("");
		re = new JTextField("");
		azione = new JTextField("");
		aggNere = new JButton("Aggiungi nera");
		aggBianche = new JButton("Aggiungi bianca");
		aggRe = new JButton("Aggiungi re");
		aggAzione = new JButton("Testa azione");
		pannello1.setLayout(new GridLayout(5,0));
		l1 = new Label("Aggiungi pedina nera: ");
		aggNere.addActionListener(new AggiungiNero(theGui, nere, state, this));
		aggBianche.addActionListener(new AggiungiBianco(theGui, bianche, state, this));
		aggRe.addActionListener(new AggiungiRe(theGui, re, state, this));
		aggAzione.addActionListener(new CheckerMove(theGui, azione, state, this, theGame, turno));
		pannello1.add(l1);
		pannello1.add(nere);
		pannello1.add(aggNere);
		l2 = new Label("Aggiungi pedina bianca: ");
		pannello1.add(l2);
		pannello1.add(bianche);
		pannello1.add(aggBianche);
		l3 = new Label("Aggiungi re: ");
		pannello1.add(l3);
		pannello1.add(re);
		pannello1.add(aggRe);
		l4 = new Label("Mossa: ");
		pannello1.add(l4);
		pannello1.add(azione);
		pannello1.add(aggAzione);
		pannello2.add(new Label("Turno:"));
		turno.setSelected(true);
		pannello2.add(turno);
		pannello2.add(turno2);
		this.add(pannello1, BorderLayout.NORTH);
		this.add(pannello2, BorderLayout.SOUTH);
	}




	public void setState(State state) {
		this.state = state;
	}


	
	
}
