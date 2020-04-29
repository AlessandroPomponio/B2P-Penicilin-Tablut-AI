package b2p.state.bitboard.bitset;

import b2p.model.Turn;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BitSetMoveTest {
    /*
     * funzione : getMovesForPawn
     *
     * Funzione che ritorna la bitboard con tutte le mosse che può fare una certa pedina
     * Il modo con cui genera le postazioni è: valuta le caselle dalla più vicina a quella più lontana in una direzione,
     * poi ripete la stessa valutazione rispetto a tutte le direzioni. Le direzioni sono, in ordine:
     * (Sopra, Sotto, Sinistra, Destra)
     *
     */

    /**
     * funzione : testGetMovesForWhitePawnsAtStart
     * testa la funzione getMovesForPawn per tutte le pedine bianche allo stato iniziale della partita
     * L'ordine di testing comincia dalla pedina in alto a sinitra, finisce con quella in basso a destra
     */

    @org.junit.jupiter.api.Test
    void testGetMovesForWhitePawnsAtStart() {

        BitSetState start = new BitSetState();
        BitSetAction[] wanted;


        wanted = new BitSetAction[]{
                new BitSetAction("E3", "D3", Turn.WHITE),
                new BitSetAction("E3", "C3", Turn.WHITE),
                new BitSetAction("E3", "B3", Turn.WHITE),
                new BitSetAction("E3", "A3", Turn.WHITE),
                new BitSetAction("E3", "F3", Turn.WHITE),
                new BitSetAction("E3", "G3", Turn.WHITE),
                new BitSetAction("E3", "H3", Turn.WHITE),
                new BitSetAction("E3", "I3", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E3.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("E4", "D4", Turn.WHITE),
                new BitSetAction("E4", "C4", Turn.WHITE),
                new BitSetAction("E4", "B4", Turn.WHITE),
                new BitSetAction("E4", "F4", Turn.WHITE),
                new BitSetAction("E4", "G4", Turn.WHITE),
                new BitSetAction("E4", "H4", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E4.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("C5", "C4", Turn.WHITE),
                new BitSetAction("C5", "C3", Turn.WHITE),
                new BitSetAction("C5", "C2", Turn.WHITE),
                new BitSetAction("C5", "C1", Turn.WHITE),
                new BitSetAction("C5", "C6", Turn.WHITE),
                new BitSetAction("C5", "C7", Turn.WHITE),
                new BitSetAction("C5", "C8", Turn.WHITE),
                new BitSetAction("C5", "C9", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.C5.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("D5", "D4", Turn.WHITE),
                new BitSetAction("D5", "D3", Turn.WHITE),
                new BitSetAction("D5", "D2", Turn.WHITE),
                new BitSetAction("D5", "D6", Turn.WHITE),
                new BitSetAction("D5", "D7", Turn.WHITE),
                new BitSetAction("D5", "D8", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.D5.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{

        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E5.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("F5", "F4", Turn.WHITE),
                new BitSetAction("F5", "F3", Turn.WHITE),
                new BitSetAction("F5", "F2", Turn.WHITE),
                new BitSetAction("F5", "F6", Turn.WHITE),
                new BitSetAction("F5", "F7", Turn.WHITE),
                new BitSetAction("F5", "F8", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.F5.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G3", Turn.WHITE),
                new BitSetAction("G5", "G2", Turn.WHITE),
                new BitSetAction("G5", "G1", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "G7", Turn.WHITE),
                new BitSetAction("G5", "G8", Turn.WHITE),
                new BitSetAction("G5", "G9", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("E6", "D6", Turn.WHITE),
                new BitSetAction("E6", "C6", Turn.WHITE),
                new BitSetAction("E6", "B6", Turn.WHITE),
                new BitSetAction("E6", "F6", Turn.WHITE),
                new BitSetAction("E6", "G6", Turn.WHITE),
                new BitSetAction("E6", "H6", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E6.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("E7", "D7", Turn.WHITE),
                new BitSetAction("E7", "C7", Turn.WHITE),
                new BitSetAction("E7", "B7", Turn.WHITE),
                new BitSetAction("E7", "A7", Turn.WHITE),
                new BitSetAction("E7", "F7", Turn.WHITE),
                new BitSetAction("E7", "G7", Turn.WHITE),
                new BitSetAction("E7", "H7", Turn.WHITE),
                new BitSetAction("E7", "I7", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E7.ordinal(), start).toArray(), wanted);
    }

    /**
     * funzione : testGetMovesForBlackPawnsAtStart
     * testa la funzione getMovesForPawn per le pedine dell'accampameno in alto
     *
     */
    @org.junit.jupiter.api.Test
    void testGetMovesForBlackPawnsAtStart() {

        BitSetState start = new BitSetState();
        BitSetAction[] wanted;

        start.setTurn(Turn.BLACK);

        wanted = new BitSetAction[]{
                new BitSetAction("D1", "D2", Turn.BLACK),
                new BitSetAction("D1", "D3", Turn.BLACK),
                new BitSetAction("D1", "D4", Turn.BLACK),
                new BitSetAction("D1", "C1", Turn.BLACK),
                new BitSetAction("D1", "B1", Turn.BLACK),
                new BitSetAction("D1", "A1", Turn.BLACK),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.D1.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{

        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E1.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("F1", "F2", Turn.BLACK),
                new BitSetAction("F1", "F3", Turn.BLACK),
                new BitSetAction("F1", "F4", Turn.BLACK),
                new BitSetAction("F1", "G1", Turn.BLACK),
                new BitSetAction("F1", "H1", Turn.BLACK),
                new BitSetAction("F1", "I1", Turn.BLACK),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.F1.ordinal(), start).toArray(), wanted);

        wanted = new BitSetAction[]{
                new BitSetAction("E2", "D2", Turn.BLACK),
                new BitSetAction("E2", "C2", Turn.BLACK),
                new BitSetAction("E2", "B2", Turn.BLACK),
                new BitSetAction("E2", "A2", Turn.BLACK),
                new BitSetAction("E2", "F2", Turn.BLACK),
                new BitSetAction("E2", "G2", Turn.BLACK),
                new BitSetAction("E2", "H2", Turn.BLACK),
                new BitSetAction("E2", "I2", Turn.BLACK),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.E2.ordinal(), start).toArray(), wanted);
    }

    /**
     * funzione : testCampCollision
     * testa la funzione getMovesForPawn per verificare la collisione con gli accampamenti
     *
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   | N |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   | B | N |  2
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
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.jupiter.api.Test
    void testCampCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.H2,                              // Pedina le cui posizioni possibili sono da valutare
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.I2,
                        BitSetPosition.H1,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("H2", "H3", Turn.WHITE),
                new BitSetAction("H2", "H4", Turn.WHITE),
                new BitSetAction("H2", "G2", Turn.WHITE),
                new BitSetAction("H2", "F2", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.H2.ordinal(), current).toArray(), wanted);
    }

    /**
     * funzione : testCastleCollision
     * testa la funzione getMovesForPawn per verificare la collisione con il castello
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   | N |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   | B | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   | N |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.jupiter.api.Test
    void testCastleCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.G5,                              // Pedina le cui posizioni possibili sono da valutare
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere
                        BitSetPosition.G3,
                        BitSetPosition.G7,
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "F5", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), current).toArray(), wanted);
    }

    /**
     * funzione : testBorderCollision
     * testa la funzione getMovesForPawn per verificare la collisione con i bordi
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   | B |  1
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
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.jupiter.api.Test
    void testBorderCollision(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche
                        BitSetPosition.I1,                              // Pedina le cui posizioni possibili sono da valutare
                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king

                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("I1", "I2", Turn.WHITE),
                new BitSetAction("I1", "I3", Turn.WHITE),
                new BitSetAction("I1", "H1", Turn.WHITE),
                new BitSetAction("I1", "G1", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.I1.ordinal(), current).toArray(), wanted);
    }

    /**
     * funzione : testKingCollision
     * testa la funzione getMovesForPawn per verificare la collisione del re con castello e bordi
     *
     *
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  1
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  2
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  3
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  4
     * +---+---+---+---+---+---+---+---+---+
     * | A | A |   |   | C |   | R | A | A |  5
     * +---+---+---+---+---+---+---+---+---+
     * | A |   |   |   |   |   |   |   | A |  6
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   |   |   |   |   |   |  7
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   |   | A |   |   |   |   |  8
     * +---+---+---+---+---+---+---+---+---+
     * |   |   |   | A | A | A |   |   |   |  9
     * +---+---+---+---+---+---+---+---+---+
     *   A   B   C   D   E   F   G   H   I
     *
     */
    @org.junit.jupiter.api.Test
    void testKingCollisions(){

        BitSetAction[] wanted;
        BitSetState current = new BitSetState(
                Turn.WHITE,
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine bianche

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizioni delle pedine nere

                }),
                BitSetUtils.newFromPositions(new BitSetPosition[]{      // Posizione della pedina king
                        BitSetPosition.G5,
                })
        );

        wanted = new BitSetAction[]{
                new BitSetAction("G5", "G4", Turn.WHITE),
                new BitSetAction("G5", "G3", Turn.WHITE),
                new BitSetAction("G5", "G2", Turn.WHITE),
                new BitSetAction("G5", "G1", Turn.WHITE),
                new BitSetAction("G5", "G6", Turn.WHITE),
                new BitSetAction("G5", "G7", Turn.WHITE),
                new BitSetAction("G5", "G8", Turn.WHITE),
                new BitSetAction("G5", "G9", Turn.WHITE),
                new BitSetAction("G5", "F5", Turn.WHITE),
        };
        assertArrayEquals(BitSetMove.getMovesForPawn(BitSetPosition.G5.ordinal(), current).toArray(), wanted);
    }

    /**
     * Funzione che ritorna la bitboard con le pedine che vengono mangiate facendo una determinata mossa
     * Bianco:  Funzione che fa la cattura standard dai due lati più casi limite (accampamenti, castello e ostacoli)
     * Nero:    Funzione che fa la cattura come quella dei bianchi, più le catture speciali.
     */
    @org.junit.jupiter.api.Test
    void testGetCapturedPawns() {

    }
}