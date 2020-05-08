package it.unibo.ai.didattica.competition.tablut.tester;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Game;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;
import it.unibo.ai.didattica.competition.tablut.gui.Gui;

public class CheckerMove implements ActionListener {

	private Gui theGui;
	private JTextField posizione;
	private State state;
	private TestGuiFrame ret;
	private Game game;
	private JRadioButton turno;

	public CheckerMove(Gui theGui, JTextField field, State state, TestGuiFrame ret, Game game, JRadioButton jr) {
		super();
		this.setTheGui(theGui);
		this.posizione = field;
		this.state = state;
		this.ret = ret;
		this.game = game;
		this.turno = jr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Turn t;
		if (turno.isSelected()) {
			this.state.setTurn(Turn.BLACK);
			t = Turn.BLACK;
		} else {
			this.state.setTurn(Turn.WHITE);
			t = Turn.WHITE;
		}
		if (posizione.getText().length() != 5) {
			System.out.println(
					"Wrong format of the move. Write moves as \"A1 A2\" where A1 is the starting cell and A2 the destination cell");
		} else {
			String da = "" + posizione.getText().charAt(0) + posizione.getText().charAt(1);
			String a = "" + posizione.getText().charAt(3) + posizione.getText().charAt(4);
			posizione.setText("");
			Action az = null;
			try {
				az = new Action(da, a, t);
			} catch (Exception ex) {

			}

			try {
				state = this.game.checkMove(state, az);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("Mossa non consentita");
				System.out.println(e1.getMessage());
			}

			this.ret.setState(state);
			this.theGui.update(state);
		}

	}

	public Gui getTheGui() {
		return theGui;
	}

	public void setTheGui(Gui theGui) {
		this.theGui = theGui;
	}

}
