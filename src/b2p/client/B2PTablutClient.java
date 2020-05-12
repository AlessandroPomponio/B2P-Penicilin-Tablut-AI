package b2p.client;

import b2p.search.aima.TablutGame;
import b2p.search.aima.minmax.IterativeDeepening;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.io.IOException;

public class B2PTablutClient extends TablutClient {

    private static final String PLAYER_NAME = "B2P";
    private final int timeout;

    public B2PTablutClient(String player, int timeout, String ipAddress) throws IOException {
        super(player, PLAYER_NAME, timeout, ipAddress);
        this.timeout = timeout;
    }

    public B2PTablutClient(String player, String ipAddress) throws IOException {
        super(player, PLAYER_NAME, 60000, ipAddress);
        this.timeout = 60000;
    }

    public B2PTablutClient(String player) throws IOException {
        super(player, PLAYER_NAME, 60000);
        this.timeout = 60000;
    }

    public B2PTablutClient(String player, int timeout) throws IOException {
        super(player, PLAYER_NAME, timeout);
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
        BitSetState state;
        State serverState;

        System.out.println("You are player " + this.getPlayer().toString() + "!");
        TablutGame gameInstance = new TablutGame(new BitSetState());
        IterativeDeepening search = new IterativeDeepening(gameInstance, Integer.MIN_VALUE, Integer.MAX_VALUE, timeout / 1000 - 1, this.getPlayer());

        int turn = 0;

        while (true) {

            turn++;
            try {
                this.read();
            } catch (ClassNotFoundException | IOException e1) {
                e1.printStackTrace();
                System.exit(1);
            }

            System.out.println("TURNO CORRENTE: " + turn);
            System.out.println("Current state:");
            serverState = this.getCurrentState();
            System.out.println(serverState.toString());

            state = BitSetUtils.newFromServer((StateTablut) serverState, turn);
            search.setGameState(state);

            if (this.getPlayer().equals(State.Turn.WHITE)) {

                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {

                    searchAndSubmitAction(state, search);
                } else {
                    printTurnComment(state, "YOU WIN!", "YOU LOSE!");
                }

            } else {

                if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {

                    searchAndSubmitAction(state, search);
                } else {
                    printTurnComment(state, "YOU LOSE!", "YOU WIN!");
                }
            }
        }
    }

    private void searchAndSubmitAction(BitSetState state, IterativeDeepening search) {
        try {

            BitSetAction bestMove = search.makeDecision(state);

            System.out.println("Selected move: " + bestMove.toString());

            Action a = new Action(bestMove.getFrom(), bestMove.getTo(), StateTablut.Turn.valueOf(bestMove.getTurn().name()));

            this.write(a);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void printTurnComment(BitSetState state, String whiteWins, String blackWins) {
        if (state.getTurn().equals(StateTablut.Turn.WHITE)) {
            System.out.println("Waiting for your opponent move... ");
        } else if (state.getTurn().equals(StateTablut.Turn.WHITEWIN)) {
            System.out.println(whiteWins);
            System.exit(0);
        } else if (state.getTurn().equals(StateTablut.Turn.BLACKWIN)) {
            System.out.println(blackWins);
            System.exit(0);
        } else if (state.getTurn().equals(StateTablut.Turn.DRAW)) {
            System.out.println("DRAW!");
            System.exit(0);
        }
    }


}

