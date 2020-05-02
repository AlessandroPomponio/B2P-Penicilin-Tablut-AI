package b2p.client;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import b2p.search.minimax.MinMaxAlphaBeta;
import b2p.search.strategy.IterativeDeepening;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetMove;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import b2p.state.bitboard.bitset.aima.*;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.BitSet;

public class B2PTablutClient extends TablutClient {

    private int timeout;

    public B2PTablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
    }

    public B2PTablutClient(String player, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, "B2P", timeout, ipAddress);
    }

    public B2PTablutClient(String player, String ipAddress) throws UnknownHostException, IOException {
        super(player, "B2P", 60000, ipAddress);
    }

    public B2PTablutClient(String player) throws UnknownHostException, IOException {
        super(player, "B2P", 60000);
    }

    @Override
    public void run() {

        // Declare the name first thing
        try {
            this.declareName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // States
        BitSetState state = null;
        State serverState = new StateTablut();

        // Algoritmo di ricerca
        Problem problem;
        IterativeDeepeningAlphaBetaSearch search = new IterativeDeepeningAlphaBetaSearch(new TablutGame(), Integer.MIN_VALUE, Integer.MAX_VALUE, 10);

        /***************************************************************************************************************
         * Ponendo il timeout a pochi secondi e debuggando (facendo quindi scadere il tempo limite), la funzione ritorna
         * un valore corretto, per cui di per sè restituisce correttamente una mossa. Non debuggando, invece, si arriva
         * ad uno stato impossibile. Perchè?
         * - Errore di impostazione di valori minimi e massimi?
         * - Errore di impostazione di qualche funzione implementata in TablutGame?
         * - La funzione dev'essere chiamata in un'altra maniera?
         * Serve ulteriore debug.
         **************************************************************************************************************/

        System.out.println("You are player " + this.getPlayer().toString() + "!");

        while (true) {
            try {
                // Leggo lo stato mandato dal server
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.exit(1);
            }

            System.out.println("Current state:");
            serverState = this.getCurrentState();
            System.out.println(serverState.toString());

            // Traduco lo stato del server in uno stato bitboard
            state = BitSetUtils.newFromServer((StateTablut) serverState);
            System.out.println("STATO CONVERTITO:\n" + BitSetUtils.toBitString(state.getBoard()));
//          tieChecker.addState(bitboardState);
/*
            problem = new Problem(
                    state,
                    new BitSetActionsFunction(),
                    new BitSetResultFunction(),
                    new BitSetGoalTest(),
                    new BitSetStepCostFunction()
            );
*/
            if (this.getPlayer().equals(State.Turn.WHITE)) {
                /** TURNO BIANCO **/
                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {

                    long curMillis = System.currentTimeMillis();
                    try {
                        long millis = 60000 - (System.currentTimeMillis() - curMillis);
/*
                        while (millis > 3000) {
                            curMillis = System.currentTimeMillis();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Shouldn't happen...");
                                e.printStackTrace();
                            }
                            millis -= System.currentTimeMillis() - curMillis;

                        }
*/

                        BitSetAction bestMove = (BitSetAction) search.makeDecision(state);
                        millis -= System.currentTimeMillis() - curMillis;
                        System.out.println("Thread time duration: " + millis);

                        System.out.println("Chosen move: " + bestMove.toString());
                        // Create move readible from the server
                        Action a = new Action(bestMove.getFrom(), bestMove.getTo(), StateTablut.Turn.valueOf(bestMove.getTurn().name()));

                        this.write(a);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.exit(1);
                    }
                }

                //  il turno dell'avversario
                else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
                    System.out.println("Waiting for your opponent move... ");
                } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }

            } else {
                /**    TURNO NERO 	**/
                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {

                    long curMillis = System.currentTimeMillis();
                    try {
                        long millis = 60000 - (System.currentTimeMillis() - curMillis);
/*
                    while (millis > 3000) {
                        curMillis = System.currentTimeMillis();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("Shouldn't happen...");
                            e.printStackTrace();
                        }
                        millis -= System.currentTimeMillis() - curMillis;

                    }
*/

                        BitSetAction bestMove = (BitSetAction) search.makeDecision(state);
                        millis -= System.currentTimeMillis() - curMillis;
                        System.out.println("Thread time duration: " + millis);

                        System.out.println("Chosen move: " + bestMove.toString());
                        // Create move readible from the server
                        Action a = new Action(bestMove.getFrom(), bestMove.getTo(), StateTablut.Turn.valueOf(bestMove.getTurn().name()));

                        this.write(a);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
                    System.out.println("Waiting for your opponent move... ");
                } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                    System.out.println("YOU LOSE!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                    System.out.println("YOU WIN!");
                    System.exit(0);
                } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
                    System.out.println("DRAW!");
                    System.exit(0);
                }
            }
        }
    }


}

