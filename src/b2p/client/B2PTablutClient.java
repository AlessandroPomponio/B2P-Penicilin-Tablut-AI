package b2p.client;

import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import b2p.state.bitboard.bitset.aima.adversarial.TablutGame;
import b2p.state.bitboard.bitset.aima.adversarial.TablutGameIterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.io.IOException;
import java.net.UnknownHostException;

public class B2PTablutClient extends TablutClient {

    private int timeout;

    public B2PTablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
        this.timeout = timeout;
    }

    public B2PTablutClient(String player, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, "B2P", timeout, ipAddress);
        this.timeout = timeout;
    }

    public B2PTablutClient(String player, String ipAddress) throws UnknownHostException, IOException {
        super(player, "B2P", 60000, ipAddress);
        this.timeout = 60000;
    }

    public B2PTablutClient(String player) throws UnknownHostException, IOException {
        super(player, "B2P", 60000);
        this.timeout = 60000;
    }

    public B2PTablutClient(String player, int timeout) throws UnknownHostException, IOException {
        super(player, "B2P", timeout);
        this.timeout = timeout;
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
        //Problem problem;
        System.out.println("You are player " + this.getPlayer().toString() + "!");
//        timeout = 10;
        TablutGame gameInstance = new TablutGame(new BitSetState());
        TablutGameIterativeDeepeningAlphaBetaSearch search = new TablutGameIterativeDeepeningAlphaBetaSearch(gameInstance, Integer.MIN_VALUE, Integer.MAX_VALUE, timeout/1000 - 1, this.getPlayer());

        int turn = 0;

        while (true) {

            turn++;
            try {
                // Leggo lo stato mandato dal server
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.exit(1);
            }

            System.out.println("TURNO CORRENTE: " + turn);
            System.out.println("Current state:");
            serverState = this.getCurrentState();
            System.out.println(serverState.toString());

            // Traduco lo stato del server in uno stato bitboard
            state = BitSetUtils.newFromServer((StateTablut) serverState, turn);
            search.setGameState(state);
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
                        long millis = timeout - (System.currentTimeMillis() - curMillis);
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
                    } }

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
                        long millis = timeout - (System.currentTimeMillis() - curMillis);
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

