package it.unibo.ai.didattica.competition.tablut.domain;

import java.io.Serializable;


/**
 * This class represents a state of a match of the smallest version of tablut
 * @author A.Piretti
 * 
 */
public class StateBrandub extends State implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public StateBrandub() 
	{
		super();
		this.board = new Pawn[7][7];
		for(int i=0; i<7; i++)
		{
			for(int j=0; j<7; j++)
			{
				this.board[i][j]=Pawn.EMPTY;
			}
		}
		
		this.board[3][3] = Pawn.THRONE;
		
		this.turn=Turn.BLACK;
		
		this.board[3][3] = Pawn.KING;
		
		this.board[3][4] = Pawn.WHITE;
		this.board[3][2] = Pawn.WHITE;
		this.board[4][3] = Pawn.WHITE;
		this.board[2][3] = Pawn.WHITE;
		
		this.board[3][5] = Pawn.BLACK;
		this.board[3][6] = Pawn.BLACK;
		this.board[6][3] = Pawn.BLACK;
		this.board[5][3] = Pawn.BLACK;
		
		this.board[3][0] = Pawn.BLACK;
		this.board[3][1] = Pawn.BLACK;
		this.board[0][3] = Pawn.BLACK;
		this.board[1][3] = Pawn.BLACK;
		
	}
	
}
