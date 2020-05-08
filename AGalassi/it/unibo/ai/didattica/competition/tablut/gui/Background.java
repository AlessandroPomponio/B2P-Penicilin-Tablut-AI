package it.unibo.ai.didattica.competition.tablut.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Background extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Image background;
	protected Image black;
	protected Image white;
	protected Image king;
	protected State aState;
	
	public Background()
	{
		super();
	}
	
	public State getaState() {
		return aState;
	}
	
	public void setaState(State aState) {
		this.aState = aState;
	}
	
	@Override
	public void paint( Graphics g ) { 
	    super.paint(g);
	    g.drawImage(background, 10, 30, null);
	    for(int i=0;i<this.aState.getBoard().length;i++)
	    {
	    	for(int j=0;j<this.aState.getBoard().length;j++)
	    	{
	    		if(this.aState.getPawn(i, j).equalsPawn("B"))
	    		{
	    			int posX= 34 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(black, posY, posX,null);
	    		}	
	    		if(this.aState.getPawn(i, j).equalsPawn("W"))
	    		{
	    			int posX= 35 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(white, posY, posX,null);
	    		}	
	    		if(this.aState.getPawn(i, j).equalsPawn("K"))
	    		{
	    			int posX= 34 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(king, posY, posX,null);
	    		}	
	    	}
	    }
	    g.dispose();
	}

}
