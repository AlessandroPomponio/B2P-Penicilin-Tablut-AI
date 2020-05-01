package it.unibo.ai.didattica.competition.tablut.domain;

import java.io.File;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import it.unibo.ai.didattica.competition.tablut.exceptions.*;

/**
 * 
 * This class represents the pure game, with all the rules;
 * it check the move, the state of the match and is a pawn is eliminated or not
 * @author A.Piretti
 *
 */
public class GameTablut implements Game {
	
	private int movesDraw;
	private int movesWithutCapturing;
	private String gameLogName;
	private File gameLog;
	private FileHandler fh;
	private Logger loggGame;
	
	public GameTablut() {
		this(0);
	}
	
	public GameTablut(int moves) {
		super();
		this.movesDraw = moves;
		this.movesWithutCapturing=0;
		this.gameLogName = (new Date().getTime())+"_gameLog.txt";
		this.setGameLog(new File(this.gameLogName));
		fh = null;
		try
		{
			fh = new FileHandler(gameLogName, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		this.loggGame = Logger.getLogger("GameLog");
		loggGame.addHandler(this.fh);
		this.fh.setFormatter(new SimpleFormatter());
		loggGame.setLevel(Level.FINE);
		loggGame.fine("Inizio partita");
	}

	/**
	 * This method checks an action in a state: if it is correct the state is going to be changed, 
	 * if it is wrong it throws a specific exception
	 *  
	 * @param state the state of the game
	 * @param a the action to be analyzed
	 * @return the new state of the game
	 * @throws BoardException try to move a pawn out of the board
	 * @throws ActionException the format of the action is wrong
	 * @throws StopException try to not move any pawn
	 * @throws PawnException try to move an enemy pawn
	 * @throws DiagonalException try to move a pawn diagonally 
	 * @throws ClimbingException try to climb over another pawn
	 * @throws ThroneException try to move a pawn into the throne box
	 * @throws OccupitedException try to move a pawn into an ccupited box
	 */
	public State checkMove(State state, Action a) throws BoardException, ActionException, StopException, PawnException, DiagonalException, ClimbingException, ThroneException, OccupitedException
	{
		//this.loggGame.fine(a.toString());
		//controllo la mossa
		if(a.getTo().length()!=2 || a.getFrom().length()!=2)
		{
			this.loggGame.warning("Formato mossa errato");
			throw new ActionException(a);
		}
		int columnFrom = a.getColumnFrom();
		int columnTo = a.getColumnTo();
		int rowFrom = a.getRowFrom();
		int rowTo = a.getRowTo();
		
		//controllo se sono fuori dal tabellone
		if(columnFrom>state.getBoard().length-1 || rowFrom>state.getBoard().length-1 || rowTo>state.getBoard().length-1 || columnTo>state.getBoard().length-1 || columnFrom<0 || rowFrom<0 || rowTo<0 || columnTo<0)
		{
			this.loggGame.warning("Mossa fuori tabellone");
			throw new BoardException(a);			
		}
		
		//controllo che non vada sul trono
		if(state.getPawn(rowTo, columnTo).equalsPawn(State.Pawn.THRONE.toString()))
		{
			this.loggGame.warning("Mossa sul trono");
			throw new ThroneException(a);
		}
		
		//controllo la casella di arrivo
		if(!state.getPawn(rowTo, columnTo).equalsPawn(State.Pawn.EMPTY.toString()))
		{
			this.loggGame.warning("Mossa sopra una casella occupata");
			throw new OccupitedException(a);
		}
		
		//controllo se cerco di stare fermo
		if(rowFrom==rowTo && columnFrom==columnTo)
		{
			this.loggGame.warning("Nessuna mossa");
			throw new StopException(a);
		}
		
		//controllo se sto muovendo una pedina giusta
		if(state.getTurn().equalsTurn(State.Turn.WHITE.toString()))
		{
			if(!state.getPawn(rowFrom, columnFrom).equalsPawn("W") && !state.getPawn(rowFrom, columnFrom).equalsPawn("K"))
			{
				this.loggGame.warning("Giocatore "+a.getTurn()+" cerca di muovere una pedina avversaria");
				throw new PawnException(a);
			}
		}
		if(state.getTurn().equalsTurn(State.Turn.BLACK.toString()))
		{
			if(!state.getPawn(rowFrom, columnFrom).equalsPawn("B"))
			{
				this.loggGame.warning("Giocatore "+a.getTurn()+" cerca di muovere una pedina avversaria");
				throw new PawnException(a);
			}
		}
		
		//controllo di non muovere in diagonale
		if(rowFrom != rowTo && columnFrom != columnTo)
		{
			this.loggGame.warning("Mossa in diagonale");
			throw new DiagonalException(a);
		}
		
		//controllo di non scavalcare pedine
		if(rowFrom==rowTo)
		{
			if(columnFrom>columnTo)
			{
				for(int i=columnTo; i<columnFrom; i++)
				{
					if(!state.getPawn(rowFrom, i).equalsPawn(State.Pawn.EMPTY.toString()))
					{
						this.loggGame.warning("Mossa che scavalca una pedina");
						throw new ClimbingException(a);
					}
				}
			}
			else
			{
				for(int i=columnFrom+1; i<=columnTo; i++)
				{
					if(!state.getPawn(rowFrom, i).equalsPawn(State.Pawn.EMPTY.toString()))
					{
						this.loggGame.warning("Mossa che scavalca una pedina");
						throw new ClimbingException(a);
					}
				}
			}
		}
		else
		{
			if(rowFrom>rowTo)
			{
				for(int i=rowTo; i<rowFrom; i++)
				{
					if(!state.getPawn(i, columnFrom).equalsPawn(State.Pawn.EMPTY.toString()) && !state.getPawn(i, columnFrom).equalsPawn(State.Pawn.THRONE.toString()))
					{
						this.loggGame.warning("Mossa che scavalca una pedina");
						throw new ClimbingException(a);
					}
				}
			}
			else
			{
				for(int i=rowFrom+1; i<=rowTo; i++)
				{
					if(!state.getPawn(i, columnFrom).equalsPawn(State.Pawn.EMPTY.toString()) && !state.getPawn(i, columnFrom).equalsPawn(State.Pawn.THRONE.toString()))
					{
						this.loggGame.warning("Mossa che scavalca una pedina");
						throw new ClimbingException(a);
					}
				}
			}
		}
		
		//se sono arrivato qui, muovo la pedina
		state = this.movePawn(state, a);
		
		//a questo punto controllo lo stato per eventuali catture
		if(state.getTurn().equalsTurn("W"))
		{
			state = this.checkCaptureBlack(state, a);
		}
		if(state.getTurn().equalsTurn("B"))
		{
			state = this.checkCaptureWhite(state, a);
		}
		
		this.loggGame.fine("Stato: "+state.toString());
		
		return state;
	}

	
	/**
	 * This method move the pawn in the board
	 * @param state is the initial state
	 * @param a is the action of a pawn
	 * @return is the new state of the game with the moved pawn
	 */
	private State movePawn(State state, Action a) {
		State.Pawn pawn = state.getPawn(a.getRowFrom(), a.getColumnFrom());
		State.Pawn[][] newBoard = state.getBoard();
		//State newState = new State();
		this.loggGame.fine("Movimento pedina");
		//libero il trono o una casella qualunque
		if(newBoard.length==9)
		{
			if(a.getColumnFrom()==4 && a.getRowFrom()==4)
			{
				newBoard[a.getRowFrom()][a.getColumnFrom()]= State.Pawn.THRONE;
			}
			else
			{
				newBoard[a.getRowFrom()][a.getColumnFrom()]= State.Pawn.EMPTY;
			}
		}
		if(newBoard.length==7)
		{
			if(a.getColumnFrom()==3 && a.getRowFrom()==3)
			{
				newBoard[a.getRowFrom()][a.getColumnFrom()]= State.Pawn.THRONE;
			}
			else
			{
				newBoard[a.getRowFrom()][a.getColumnFrom()]= State.Pawn.EMPTY;
			}
		}
		
		//metto nel nuovo tabellone la pedina mossa
		newBoard[a.getRowTo()][a.getColumnTo()]=pawn;
		//aggiorno il tabellone
		state.setBoard(newBoard);
		//cambio il turno
		if(state.getTurn().equalsTurn(State.Turn.WHITE.toString()))
		{
			state.setTurn(State.Turn.BLACK);
		}
		else
		{
			state.setTurn(State.Turn.WHITE);
		}
		
		
		return state;
	}
	
	
	/**
	 * This method check if a pawn is captured and if the game ends
	 * @param state the state of the game
	 * @param a the action of the previous moved pawn
	 * @return the new state of the game
	 */
	private State checkCaptureWhite(State state, Action a)
	{
		//controllo se mangio a destra
		if(a.getColumnTo()<state.getBoard().length-2 && state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("B") && (state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("W")||state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("T")||state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("K")))
		{
			state.removePawn(a.getRowTo(), a.getColumnTo()+1);
			this.movesWithutCapturing=-1;
			this.loggGame.fine("Pedina nera rimossa in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
		}
		//controllo se mangio a sinistra
		if(a.getColumnTo()>1 && state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("B") && (state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("W")||state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("T")||state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("K")))
		{
			state.removePawn(a.getRowTo(), a.getColumnTo()-1);
			this.movesWithutCapturing=-1;
			this.loggGame.fine("Pedina nera rimossa in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
		}
		//controllo se mangio sopra
		if(a.getRowTo()>1 && state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("B") && (state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("W")||state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("T")||state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("K")))
		{
			state.removePawn(a.getRowTo()-1, a.getColumnTo());
			this.movesWithutCapturing=-1;
			this.loggGame.fine("Pedina nera rimossa in: "+state.getBox(a.getRowTo()-1, a.getColumnTo()));
		}
		//controllo se mangio sotto
		if(a.getRowTo()<state.getBoard().length-2 && state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("B") && (state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("W")||state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("T")||state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("K")))
		{
			state.removePawn(a.getRowTo()+1, a.getColumnTo());
			this.movesWithutCapturing=-1;
			this.loggGame.fine("Pedina nera rimossa in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
		}
		//controllo se ho vinto
		if(a.getRowTo()==0 || a.getRowTo()==state.getBoard().length-1 || a.getColumnTo()==0 || a.getColumnTo()==state.getBoard().length-1)
		{
			if(state.getPawn(a.getRowTo(), a.getColumnTo()).equalsPawn("K"))
			{
				state.setTurn(State.Turn.WHITEWIN);
				this.loggGame.fine("Bianco vince con re in "+a.getTo());
			}
		}
		
		//controllo il pareggio
		if(this.movesWithutCapturing>=this.movesDraw && (state.getTurn().equalsTurn("B")||state.getTurn().equalsTurn("W")))
		{
			state.setTurn(State.Turn.DRAW);
			this.loggGame.fine("Stabilito un pareggio per troppe mosse senza mangiare");
		}
		this.movesWithutCapturing++;
		return state;
	}
	
	/**
	 * This method check if a pawn is captured and if the game ends
	 * @param state the state of the game
	 * @param a the action of the previous moved pawn
	 * @return the new state of the game
	 */
	private State checkCaptureBlack(State state, Action a)
	{
		//controllo se mangio a destra
		if(a.getColumnTo()<state.getBoard().length-2 && (state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("W")||state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("K")) && (state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("B")||state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("T")))
		{
			//nero-re-trono N.B. No indexOutOfBoundException perchè se il re si trovasse sul bordo il giocatore bianco avrebbe già vinto
			if(state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("K") && state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("T"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
				}
			}
			//nero-re-nero
			if(state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("K") && state.getPawn(a.getRowTo(), a.getColumnTo()+2).equalsPawn("B"))
			{
				//mangio il re?
				if(!state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("T") && !state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("T"))
				{
					if(!(a.getRowTo()*2 + 1==9 && state.getBoard().length==9) && !(a.getRowTo()*2 + 1==7 && state.getBoard().length==7))
					{
						state.setTurn(State.Turn.BLACKWIN);
						this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
					}	
				}						
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("T"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
				}
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("T") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
				}
			}			
			//nero-bianco-trono/nero
			if(state.getPawn(a.getRowTo(), a.getColumnTo()+1).equalsPawn("W"))
			{
				state.removePawn(a.getRowTo(), a.getColumnTo()+1);
				this.movesWithutCapturing=-1;
				this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(a.getRowTo(), a.getColumnTo()+1));
			}
			
		}
		//controllo se mangio a sinistra
		if(a.getColumnTo()>1 && (state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("W")||state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("K")) && (state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("B")||state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("T")))
		{
			//trono-re-nero
			if(state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("K") && state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("T"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
				}
			}
			//nero-re-nero
			if(state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("K") && state.getPawn(a.getRowTo(), a.getColumnTo()-2).equalsPawn("B"))
			{
				//mangio il re?
				if(!state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("T") && !state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("T"))
				{
					if(!(a.getRowTo()*2 + 1==9 && state.getBoard().length==9) && !(a.getRowTo()*2 + 1==7 && state.getBoard().length==7))
					{
						state.setTurn(State.Turn.BLACKWIN);
						this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
					}
				}
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("T"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
				}
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("T") && state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
				}
			}
			//trono/nero-bianco-nero
			if(state.getPawn(a.getRowTo(), a.getColumnTo()-1).equalsPawn("W"))
			{
				state.removePawn(a.getRowTo(), a.getColumnTo()-1);
				this.movesWithutCapturing=-1;
				this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
			}
		}
		//controllo se mangio sopra
		if(a.getRowTo()>1 && (state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("W")||state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("K")) && (state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("B")||state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("T")))
		{
			//nero-re-trono 
			if(state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("K") && state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("T"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo()-1, a.getColumnTo()));
				}
			}			
			//nero-re-nero
			if(state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("K") && state.getPawn(a.getRowTo()-2, a.getColumnTo()).equalsPawn("B"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("T"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
				}
				if(state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("T") && state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
				}
				//mangio il re?
				if(!state.getPawn(a.getRowTo()-1, a.getColumnTo()-1).equalsPawn("T") && !state.getPawn(a.getRowTo()-1, a.getColumnTo()+1).equalsPawn("T"))
				{
					if(!(a.getRowTo()*2 + 1==9 && state.getBoard().length==9) && !(a.getRowTo()*2 + 1==7 && state.getBoard().length==7))
					{
						state.setTurn(State.Turn.BLACKWIN);
						this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo(), a.getColumnTo()-1));
					}
				}
			}			
			//nero-bianco-trono/nero
			if(state.getPawn(a.getRowTo()-1, a.getColumnTo()).equalsPawn("W"))
			{
				state.removePawn(a.getRowTo()-1, a.getColumnTo());
				this.movesWithutCapturing=-1;
				this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(a.getRowTo()-1, a.getColumnTo()));
			}
		}
		//controllo se mangio sotto
		if(a.getRowTo()<state.getBoard().length-2 && (state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("W")||state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("K")) && (state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("B")||state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("T")))
		{
			//nero-re-trono
			if(state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("K") && state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("T"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
				}
			}			
			//nero-re-nero
			if(state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("K") && state.getPawn(a.getRowTo()+2, a.getColumnTo()).equalsPawn("B"))
			{
				//ho circondato su 3 lati il re?
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("B") && state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("T"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
				}
				if(state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("T") && state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("B"))
				{
					state.setTurn(State.Turn.BLACKWIN);
					this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
				}
				//mangio il re?
				if(!state.getPawn(a.getRowTo()+1, a.getColumnTo()+1).equalsPawn("T") && !state.getPawn(a.getRowTo()+1, a.getColumnTo()-1).equalsPawn("T"))
				{
					if(!(a.getRowTo()*2 + 1==9 && state.getBoard().length==9) && !(a.getRowTo()*2 + 1==7 && state.getBoard().length==7))
					{
						state.setTurn(State.Turn.BLACKWIN);
						this.loggGame.fine("Nero vince con re catturato in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
					}
				}
			}		
			//nero-bianco-trono/nero
			if(state.getPawn(a.getRowTo()+1, a.getColumnTo()).equalsPawn("W"))
			{
				state.removePawn(a.getRowTo()+1, a.getColumnTo());
				this.movesWithutCapturing=-1;
				this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(a.getRowTo()+1, a.getColumnTo()));
			}			
		}
		//controllo il re completamente circondato
		if(state.getPawn(4, 4).equalsPawn(State.Pawn.KING.toString()) && state.getBoard().length==9)
		{
			if(state.getPawn(3, 4).equalsPawn("B") && state.getPawn(4, 3).equalsPawn("B") && state.getPawn(5, 4).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B"))
			{
				state.setTurn(State.Turn.BLACKWIN);
				this.loggGame.fine("Nero vince con re catturato sul trono");
			}
		}
		if(state.getPawn(3, 3).equalsPawn(State.Pawn.KING.toString()) && state.getBoard().length==7)
		{
			if(state.getPawn(3, 4).equalsPawn("B") && state.getPawn(4, 3).equalsPawn("B") && state.getPawn(2, 3).equalsPawn("B") && state.getPawn(3, 2).equalsPawn("B"))
			{
				state.setTurn(State.Turn.BLACKWIN);
				this.loggGame.fine("Nero vince con re catturato sul trono");
			}
		}
		//controllo regola 11
		if(state.getBoard().length==9)
		{
			if(a.getColumnTo()==4 && a.getRowTo()==2)
			{
				if(state.getPawn(3, 4).equalsPawn("W") && state.getPawn(4, 4).equalsPawn("K") && state.getPawn(4, 3).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B") && state.getPawn(5, 4).equalsPawn("B"))
				{
					state.removePawn(3, 4);
					this.movesWithutCapturing=-1;
					this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(3, 4));
				}
			}
			if(a.getColumnTo()==4 && a.getRowTo()==6)
			{
				if(state.getPawn(5, 4).equalsPawn("W") && state.getPawn(4, 4).equalsPawn("K") && state.getPawn(4, 3).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B") && state.getPawn(3, 4).equalsPawn("B"))
				{
					state.removePawn(5, 4);
					this.movesWithutCapturing=-1;
					this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(5, 4));
				}
			}
			if(a.getColumnTo()==2 && a.getRowTo()==4)
			{
				if(state.getPawn(4, 3).equalsPawn("W") && state.getPawn(4, 4).equalsPawn("K") && state.getPawn(3, 4).equalsPawn("B") && state.getPawn(5, 4).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B"))
				{
					state.removePawn(4, 3);
					this.movesWithutCapturing=-1;
					this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(4, 3));
				}
			}
			if(a.getColumnTo()==6 && a.getRowTo()==4)
			{
				if(state.getPawn(4, 5).equalsPawn("W") && state.getPawn(4, 4).equalsPawn("K") && state.getPawn(4, 3).equalsPawn("B") && state.getPawn(5, 4).equalsPawn("B") && state.getPawn(3, 4).equalsPawn("B"))
				{
					state.removePawn(4, 5);
					this.movesWithutCapturing=-1;
					this.loggGame.fine("Pedina bianca rimossa in: "+state.getBox(4, 5));
				}
			}
		}
		
		
		//controllo il pareggio
		if(this.movesWithutCapturing>=this.movesDraw && (state.getTurn().equalsTurn("B")||state.getTurn().equalsTurn("W")))
		{
			state.setTurn(State.Turn.DRAW);
			this.loggGame.fine("Stabilito un pareggio per troppe mosse senza mangiare");
		}
		this.movesWithutCapturing++;
		return state;
	}
	
	
	
	public File getGameLog() {
		return gameLog;
	}

	public void setGameLog(File gameLog) {
		this.gameLog = gameLog;
	}

	@Override
	public void endGame(State state) {
		this.loggGame.fine("Stato: "+state.toString());
	}

	
	
}
