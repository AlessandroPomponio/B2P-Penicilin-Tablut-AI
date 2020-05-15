package b2p.state.bitboard.bitset.test;

import b2p.model.IAction;
import b2p.state.bitboard.bitset.BitSetAction;
import b2p.state.bitboard.bitset.BitSetPosition;
import b2p.state.bitboard.bitset.BitSetState;
import b2p.state.bitboard.bitset.BitSetUtils;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BitSetStateTest {

    //region getAvailablePawnMoves

    /**
     * funzione : testGetAvailablePawnMovesEmpty
     * testa la funzione getAvailablePawnMoves per verificare che non restituisca mosse quando non deve restituirle
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   | N |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   | N | B | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testGetAvailablePawnMovesEmpty() {

        IAction[] expected;
        IAction[] actual;

        BitSetState current = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.H3,
                        BitSetPosition.G4,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H4,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );
        expected = new IAction[]{

        };
        actual = current.getAvailablePawnMoves().toArray(new IAction[]{});

        assertArrayEquals(expected, actual);
    }


    /**
     * funzione : testCollisionGetAvailablePawnMoves
     * testa la funzione getAvailablePawnMoves per verificare che valuti le collisioni correttamente
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   | N | N | N |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A | B |   | B |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testCollisionGetAvailablePawnMoves() {

        IAction[] expected;
        IAction[] actual;

        BitSetState current = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G8,
                        BitSetPosition.H8,
                        BitSetPosition.I8,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G9,
                        BitSetPosition.I9,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );
        expected = new IAction[]{
                new BitSetAction("G9", "H9", Turn.WHITE),
                new BitSetAction("I9", "H9", Turn.WHITE),
        };
        actual = current.getAvailablePawnMoves().toArray(new IAction[]{});

        assertArrayEquals(expected, actual);
    }
    //endregion

    //region performMoveAction

    /**
     * funzione : testPerformMoveAction
     * testa la funzione performMove(IAction) per verificare che esegua correttamente la mossa
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveAction() {

        String from = "D3", to = "F3";
        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(new BitSetAction(from, to, Turn.WHITE));
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testPerformMoveString
     * testa la funzione performMove(String, String) per verificare che esegua correttamente la mossa
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveString() {

        String from = "D7", to = "F7";
        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D7,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F7,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(from, to);
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testPerformMoveStandard
     * testa la funzione performMove(int, int) per verificare che esegua correttamente la mossa
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B |   | B'|   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveStandard() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(BitSetPosition.D3.ordinal(), BitSetPosition.F3.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testPerformMoveFromMatch
     * testa la funzione performMove(int, int) per verificare che gli stati siano coerenti, in seguito ad un errore
     * nel match.
     * (da completare)
     * <p>
     * Stato 1, turno Nero
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B | N |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   | B |   |   |   |   | N |  4
     * +---+---+---+---+---+---+---+---+---+
     * | N | N | B | B | R | B | B | N | N |  5
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | B |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | A |   |   |   | N |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     * <p>
     * Mossa D1 -> C1
     * <p>
     * Stato 2, turno Bianco
     * +---+---+---+---+---+---+---+---+---+
     * |   |   | N | A | N | N |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B | N |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   | B |   |   |   |   | N |  4
     * +---+---+---+---+---+---+---+---+---+
     * | N | N | B | B | R | B | B | N | N |  5
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | B |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | A |   |   |   | N |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     * <p>
     * Mossa E5 -> E3
     * <p>
     * Stato 3, turno Nero
     * +---+---+---+---+---+---+---+---+---+
     * |   |   | N | A | N | N |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | B | N |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | R |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   | B |   |   |   |   | N |  4
     * +---+---+---+---+---+---+---+---+---+
     * | N | N | B | B | C | B | B | N | N |  5
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | B |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | B |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * | N |   |   |   | A |   |   |   | N |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | N | N |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */

    @org.junit.Test
    public void testPerformMoveFromMatch() {

        BitSetState initialState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D1,
                        BitSetPosition.E1,
                        BitSetPosition.F1,
                        BitSetPosition.E2,
                        BitSetPosition.A4,
                        BitSetPosition.I4,
                        BitSetPosition.A5,
                        BitSetPosition.B5,
                        BitSetPosition.H5,
                        BitSetPosition.I5,
                        BitSetPosition.A6,
                        BitSetPosition.A8,
                        BitSetPosition.I8,
                        BitSetPosition.D9,
                        BitSetPosition.E9,
                        BitSetPosition.F9,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D2,
                        BitSetPosition.D4,
                        BitSetPosition.C5,
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.G5,
                        BitSetPosition.E6,
                        BitSetPosition.E7,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5
                }), Turn.BLACK
        );

        BitSetState finalState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.C1,
                        BitSetPosition.E1,
                        BitSetPosition.F1,
                        BitSetPosition.E2,
                        BitSetPosition.A4,
                        BitSetPosition.I4,
                        BitSetPosition.A5,
                        BitSetPosition.B5,
                        BitSetPosition.H5,
                        BitSetPosition.I5,
                        BitSetPosition.A6,
                        BitSetPosition.A8,
                        BitSetPosition.I8,
                        BitSetPosition.D9,
                        BitSetPosition.E9,
                        BitSetPosition.F9,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.D2,
                        BitSetPosition.D4,
                        BitSetPosition.C5,
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.G5,
                        BitSetPosition.E6,
                        BitSetPosition.E7,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E3,
                }), Turn.BLACK
        );

        initialState.performMove(new BitSetAction("D1", "C1", Turn.BLACK));
        initialState.performMove(new BitSetAction("E5", "E3", Turn.WHITE));
        assertEquals(BitSetUtils.toBitString(initialState.getBoard()), BitSetUtils.toBitString(finalState.getBoard()));
        assert (initialState.getTurn() == finalState.getTurn());

    }


    /**
     * funzione : testPerformMoveNoCaptureBetweenPawns
     * testa la funzione performMove(int, int) per verificare che la pedina mossa, quando si sposti tra due pedine del
     * colore opposto, non venga catturata
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | N | B'| N |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | B |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveNoCaptureBetweenPawns() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D3,
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.E4,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D3,
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.E3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(BitSetPosition.E4.ordinal(), BitSetPosition.E3.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testPerformMoveNoCaptureBetweenPawnAndCamp
     * testa la funzione performMove(int, int) per verificare che la pedina mossa, quando si sposti tra una pedina del
     * colore opposto ed un accampamento, non venga catturata
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | B'|   |   | B |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   | N |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveNoCaptureBetweenPawnAndCamp() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.I2,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.F3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F2,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(BitSetPosition.I2.ordinal(), BitSetPosition.F2.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testPerformMoveNoCaptureBetweenPawnAndCastle
     * testa la funzione performMove(int, int) per verificare che la pedina mossa, quando si sposti tra una pedina del
     * colore opposto ed il castello, non venga catturata, ma che catturi l'altra.
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C | B'| N | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A | B |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testPerformMoveNoCaptureBetweenPawnAndCastle() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G5,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F8,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.F5,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.BLACK
        );

        fromState.performMove(BitSetPosition.F8.ordinal(), BitSetPosition.F5.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
    }


    /**
     * funzione : testKingInCastleIllegalCapture
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | R |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testKingInCastleIllegalCapture() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.A4,
                        BitSetPosition.E6,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                }), Turn.BLACK
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E4,
                        BitSetPosition.E6,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5
                }), Turn.WHITE
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (!fromState.blackHasWon());
    }


    /**
     * funzione : testKingInCastleIllegalSpecialCapture
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   | N | R | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testKingInCastleIllegalSpecialCapture() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.A4,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                }), Turn.BLACK
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.E4,
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5
                }), Turn.WHITE
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (!fromState.blackHasWon());
    }
    //endregion

    //region hasWhiteWon

    /**
     * funzione : testWhiteWinningCondition
     * testa la funzione whiteHasWon per verificare le condizioni di vittoria del giocatore bianco,
     * simulando la mossa tramite performeMove, per testare anch'essa sul re
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | R | - | - | - | > | R'|  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testWhiteStandardWinningCondition() {
        BitSetPosition from = BitSetPosition.D3, to = BitSetPosition.I3;

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        from,
                }), Turn.WHITE
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        to,
                }), Turn.BLACK
        );

        fromState.performMove(from.ordinal(), to.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (toState.whiteHasWon());
    }
    //endregion

    //region hasBlackWon

    /**
     * funzione : testBlackStandardWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura standard del re circondato da due pedine nere quando il re
     * non si trova nè in una posizione adiacente al castello, nè dentro di esso
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | - | - | > | N'| R | N |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testBlackStandardWinningCondition() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.A3,
                        BitSetPosition.I3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.H3,
                }), Turn.BLACK
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G3,
                        BitSetPosition.I3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );
        fromState.performMove(BitSetPosition.A3.ordinal(), BitSetPosition.G3.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (fromState.blackHasWon());
    }


    /**
     * funzione : testKingNearCastleBlackWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova in una posizione adiacente al castello
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   | N | R | N |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testKingNearCastleBlackWinningCondition() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D4,
                        BitSetPosition.F4,
                        BitSetPosition.A3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E4,
                }), Turn.BLACK
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D4,
                        BitSetPosition.F4,
                        BitSetPosition.E3,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );
        fromState.performMove(BitSetPosition.A3.ordinal(), BitSetPosition.E3.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (fromState.blackHasWon());
    }


    /**
     * funzione : testKingInCastleBlackWinningCondition
     * testa la funzione blackHasWon per verificare le condizioni di vittoria del giocatore nero, simulando la mossa
     * con la performMove per verificare anche la cattura speciale del re circondato da tre pedine nere quando il re
     * si trova nel castello
     * <p>
     * <p>
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | N | - | - | > | N'|   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   | N | R | N |   | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   | N |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     * A   B   C   D   E   F   G   H   I
     */
    @org.junit.Test
    public void testKingInCastleBlackWinningCondition() {

        BitSetState fromState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.A4,
                        BitSetPosition.E6,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.E5,
                }), Turn.BLACK
        );

        BitSetState toState = new BitSetState(
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.D5,
                        BitSetPosition.F5,
                        BitSetPosition.E4,
                        BitSetPosition.E6,
                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }), BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                }), Turn.WHITE
        );
        fromState.performMove(BitSetPosition.A4.ordinal(), BitSetPosition.E4.ordinal());
        assertEquals(BitSetUtils.toBitString(toState.getBoard()), BitSetUtils.toBitString(fromState.getBoard()));
        assert (fromState.getTurn() == toState.getTurn());
        assert (fromState.blackHasWon());
    }
    //endregion
}
