package b2p.state.bitboard.bitset;

import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetMove {

    public static ArrayList<BitSetAction> getMovesForPawn(int pawnPosition, BitSetState state) {

        // 16 is the maximum amount of moves we can possibly have:
        // it ensures no further allocations are needed
        ArrayList<BitSetAction> moves = new ArrayList<>(16);
        BitSetPosition from = BitSetPosition.values()[pawnPosition];

        //
        BitSet forbiddenCells = state.getBoard();

        // The castle cell is forbidden to step on
        forbiddenCells.set(BitSetPosition.E5.ordinal());

        // Camps may or may not be forbidden to step on
        if (state.getTurn() == Turn.WHITE) {

            // Whites can never go on the camps
            forbiddenCells.or(BitSetPosition.camps);

        } else {

            // Blacks can't go back on the camps
            if (!BitSetUtils.newFromPositions(new int[]{pawnPosition}).intersects(BitSetPosition.camps)){
                forbiddenCells.or(BitSetPosition.camps);
            }

        }

        // Check for moves
        // Up
        for (int cell = pawnPosition - 9; cell > 0; cell -= 9) {

            // When we find a forbidden cell, we can stop
            if (forbiddenCells.get(cell))
                break;

            moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

        }

        // Down
        for (int cell = pawnPosition + 9; cell < BitSetState.boardDimension; cell += 9) {

            // When we find a forbidden cell, we can stop
            if (forbiddenCells.get(cell))
                break;

            moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

        }

        // Left: check only if the pawn isn't on column A
        if (pawnPosition % 9 != 0) {

            // Make sure we don't end up out of the board
            // or one row above in column I
            for (int cell = pawnPosition - 1; cell >= 0 && cell % 9 != 8; cell--) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }

        }

        // Right: check only if the pawn isn't on column I
        if (pawnPosition % 9 != 8) {

            // Make sure we don't end up out of the board
            // or one row below in column A
            for (int cell = pawnPosition + 1; cell < BitSetState.boardDimension && cell % 9 != 0; cell++) {

                // When we find a forbidden cell, we can stop
                if (forbiddenCells.get(cell))
                    break;

                moves.add(new BitSetAction(from.getName(), BitSetPosition.values()[cell].getName(), state.getTurn()));

            }

        }

        return moves;

    }

    public static BitSet getCapturedPawns(int from, int to, BitSetState state) {

        if (state.getTurn() == Turn.BLACK)
            return getCapturesForBlack(from, to, state);

        return getCapturesForWhite(from, to, state);

    }

    private static BitSet getCapturesForBlack(int from, int to, BitSetState state) {

        //
        BitSet blacks = BitSetUtils.copy(state.getBlackPawns());
        blacks.clear(from);

        BitSet king = BitSetUtils.copy(state.getKing());
        BitSet whites = BitSetUtils.copy(state.getWhitePawns());

        //
        BitSet specialCaptures = new BitSet(BitSetState.boardDimension);
        blacks.set(to);

        // Check if the king needs a special capture
        if (king.intersects(BitSetPosition.specialKingCells)) {

            int kingPosition = king.nextSetBit(0);

            // If the king is in the castle, he must be
            // surrounded by 4 black soldiers
            if (kingPosition == BitSetPosition.E5.ordinal()) {

                BitSet tempBlacks = BitSetUtils.copy(blacks);
                tempBlacks.and(BitSetPosition.kingSurrounded);
                if (tempBlacks.cardinality() == 4)
                    specialCaptures.set(BitSetPosition.E5.ordinal());

            } else  {

                switch (kingPosition) {

                    // BitSetPosition.E4.ordinal() = 31
                    case 31:

                        BitSet e4Check = BitSetUtils.copy(blacks);
                        e4Check.and(BitSetPosition.kingInE4Surrounded);

                        if (e4Check.cardinality() == 3)
                            specialCaptures.set(king.nextSetBit(0));

                        break;

                    // BitSetPosition.D5.ordinal() = 39
                    case 39:

                        BitSet d5Check = BitSetUtils.copy(blacks);
                        d5Check.and(BitSetPosition.kingInD5Surrounded);

                        if (d5Check.cardinality() == 3)
                            specialCaptures.set(king.nextSetBit(0));

                        break;

                    // BitSetPosition.E6.ordinal() = 49
                    case 49:

                        BitSet e6Check = BitSetUtils.copy(blacks);
                        e6Check.and(BitSetPosition.kingInE6Surrounded);

                        if (e6Check.cardinality() == 3)
                            specialCaptures.set(king.nextSetBit(0));

                        break;

                    // BitSetPosition.F5.ordinal() = 41
                    case 41:

                        BitSet f5Check = BitSetUtils.copy(blacks);
                        f5Check.and(BitSetPosition.kingInF5Surrounded);

                        if (f5Check.cardinality() == 3)
                            specialCaptures.set(king.nextSetBit(0));

                        break;

                }

            }

        } else {
            whites.or(king);
        }

        // Normal captures
        BitSet captures = getNormalCaptures(to, blacks, whites);
        captures.or(specialCaptures);
        return captures;

    }

    private static BitSet getCapturesForWhite(int from, int to, BitSetState state) {

        BitSet blacks = BitSetUtils.copy(state.getBlackPawns());
        BitSet whites = BitSetUtils.copy(state.getWhitePawns());
        whites.or(state.getKing());
        whites.clear(from);

        return getNormalCaptures(to, whites, blacks);

    }

    private static BitSet getNormalCaptures(int position, BitSet attack, BitSet defense) {

        BitSet captured = new BitSet(BitSetState.boardDimension);

        //Check UP
        int oneUpCell = position - 9;
        if (oneUpCell > 0 && defense.get(oneUpCell)) {

            // If the defense pawn isn't in the first row
            // to be captured it can:
            //   - have an attack pawn above
            //   - have the castle above
            //   - be inside of a camp
            if (oneUpCell > 8) {

                int twoUpCell = oneUpCell - 9;
                if (attack.get(twoUpCell) || BitSetPosition.obstacles.get(twoUpCell)) {
                    captured.set(oneUpCell);
                }

            }

        }

        //Check LEFT
        int oneLeftCell = position - 1;
        if (oneLeftCell > 0 && defense.get(oneLeftCell)) {

            // If the defense pawn isn't in the first column
            // to be captured it can:
            //   - have an attack pawn on the left
            //   - have the castle on the left
            //   - be inside of a camp
            if (oneLeftCell % 9 != 0) {

                int twoLeftCell = oneLeftCell - 1;
                if (attack.get(twoLeftCell) || BitSetPosition.obstacles.get(twoLeftCell)) {
                    captured.set(oneLeftCell);
                }

            }

        }

        //Check DOWN
        int oneDownCell = position + 9;
        if (oneDownCell < BitSetState.boardDimension && defense.get(oneDownCell)) {

            // If the defense pawn isn't in the last row
            // to be captured it can:
            //   - have an attack pawn below
            //   - have the castle below
            //   - be inside of a camp
            if (oneDownCell < BitSetState.boardDimension - 8) {

                int twoDownCell = oneDownCell + 9;
                if (attack.get(twoDownCell) || BitSetPosition.obstacles.get(twoDownCell)) {
                    captured.set(oneDownCell);
                }

            }

        }

        //Check RIGHT
        int oneRightCell = position + 1;
        if (oneRightCell > 0 && defense.get(oneRightCell)) {

            // If the defense pawn isn't in the last column
            // to be captured it can:
            //   - have an attack pawn on the right
            //   - have the castle on the right
            //   - be inside of a camp
            if (oneRightCell % 9 != 8) {

                int twoRightCell = oneRightCell + 1;
                if (attack.get(twoRightCell) || BitSetPosition.obstacles.get(twoRightCell)) {
                    captured.set(oneRightCell);
                }

            }

        }

        return captured;
    }

    public static int kingEscapesInOneMove(BitSetState state) {

        List<BitSetAction> moves = getMovesForPawn(state.getKing().nextSetBit(0), state);
        int escapes = 0;

        // Check if one of the escape cells is in our reach
        for (BitSetAction move : moves) {
            if (BitSetPosition.escapeHashSet.contains(move.getTo()))
                escapes++;
        }

        return escapes;

    }

    public static boolean kingHasMoreThanOneEscapePath(BitSetState state) {

        List<BitSetAction> moves = state.getAvailableKingMoves();
        int escapes = 0;

        // Check if one of the escape cells is in our reach
        for (BitSetAction move : moves) {

            BitSetState newState = (BitSetState) state.clone();
            newState.performMove(move);

            List<BitSetAction> newMoves = newState.getAvailableKingMoves();
            escapes = 0;
            for (BitSetAction newMove : newMoves) {

                if (BitSetPosition.escapeHashSet.contains(newMove.getTo()))
                    escapes++;

            }

            if (escapes > 1)
                return true;

        }

        return false;

    }

    public static int kingStatus(BitSetState state) {
        //
        BitSet king = BitSetUtils.copy(state.getKing());
        BitSet blacks = BitSetUtils.copy(state.getBlackPawns());
        BitSet whites = BitSetUtils.copy(state.getWhitePawns());

        //
        int enemies, allies;

        // Special capture needed
        if (king.intersects(BitSetPosition.specialKingCells)) {

            // The king is in the castle:
            // check how many allied and enemy
            // pawns are surrounding it.
            if (king.equals(BitSetPosition.castle)) {

                blacks.and(BitSetPosition.kingSurrounded);
                whites.and(BitSetPosition.kingSurrounded);

                return blacks.cardinality() - whites.cardinality();


//                return blacks.cardinality();

            } else {

                int menace = 0;
                int kingPos = king.nextSetBit(0);

                if (blacks.get(kingPos - 1))
                    menace++;

                if (whites.get(kingPos - 1))
                    menace--;

                if (blacks.get(kingPos + 1))
                    menace++;

                if (whites.get(kingPos -1))
                    menace--;

                if (blacks.get(kingPos - 9))
                    menace++;

                if (whites.get(kingPos -9))
                    menace--;

                if (blacks.get(kingPos + 9))
                    menace++;

                if (whites.get(kingPos-9))
                    menace--;

                return menace;

            }

        } else {

            int menace = 0;
            int kingPos = king.nextSetBit(0);

            if (kingPos % 9 != 0) {

                if (whites.get(kingPos - 1))
                    menace--;
                else if (blacks.get(kingPos - 1) || BitSetPosition.camps.get(kingPos - 1))
                    menace++;

            }

            if (kingPos % 9 != 8) {

                if (whites.get(kingPos + 1))
                    menace--;
                else if (blacks.get(kingPos + 1) || BitSetPosition.camps.get(kingPos + 1))
                    menace++;

            }

            if (kingPos > 9) {

                if (whites.get(kingPos - 9))
                    menace--;
                else if (blacks.get(kingPos - 9) || BitSetPosition.camps.get(kingPos - 9))
                    menace++;

            }

            if (kingPos < 72) {

                if (whites.get(kingPos + 9))
                    menace--;
                else if (blacks.get(kingPos + 9) || BitSetPosition.camps.get(kingPos + 9))
                    menace++;

            }

            return menace * 2;

        }

    }

    public static int dangerToKing(BitSetState state) {

        //
        BitSet king = BitSetUtils.copy(state.getKing());
        BitSet blacks = BitSetUtils.copy(state.getBlackPawns());

        // Special capture needed
        if (king.intersects(BitSetPosition.specialKingCells)) {

            if (king.equals(BitSetPosition.castle)) {

                blacks.and(BitSetPosition.kingSurrounded);
                return blacks.cardinality();

            } else {

                int menace = 0;
                int kingPos = king.nextSetBit(0);

                if (blacks.get(kingPos - 1))
                    menace++;

                if (blacks.get(kingPos + 1))
                    menace++;

                if (blacks.get(kingPos - 9))
                    menace++;

                if (blacks.get(kingPos + 9))
                    menace++;

                return menace;

            }

        } else {

            int menace = 0;
            int kingPos = king.nextSetBit(0);

            if (kingPos % 9 != 0 && (blacks.get(kingPos - 1) || BitSetPosition.camps.get(kingPos - 1)))
                menace++;

            if (kingPos % 9 != 8 && (blacks.get(kingPos + 1) || BitSetPosition.camps.get(kingPos + 1)))
                menace++;

            if (kingPos > 9 && (blacks.get(kingPos - 9) || BitSetPosition.camps.get(kingPos - 9)))
                menace++;

            if (kingPos < 72 && (blacks.get(kingPos + 9) || BitSetPosition.camps.get(kingPos + 9)))
                menace++;

            return menace * 2;

        }
    }

    public static int movesNeededForKingEscape(BitSetState state) {
        return movesNeededForKingEscape_rec(state, 1);
    }

    private static int movesNeededForKingEscape_rec(BitSetState state, int step) {

        // Cutoff if we've searched already enough
        if (step > 3)
            return 50;

        if (state.getKing().intersects(BitSetPosition.escape))
            return step;

        // King moves
        ArrayList<BitSetAction> moves = getMovesForPawn(state.getKing().nextSetBit(0), state);

        // Check if one of the escape cells is in our reach
        for (BitSetAction move : moves) {
            if (BitSetPosition.escapeHashSet.contains(move.getTo()))
                return step;
        }

        // Needed for performMove
        // This way, it'll actually move the king around
        state.setTurn(Turn.WHITE);

        // Perform iteratively all the moves the king has
        // and check how long it takes us to get to an escape cell
        int shortestEscape = 50;
        for (BitSetAction move : moves) {

            BitSetState newState = (BitSetState) state.clone();
            newState.performMove(move);

            int movesFromThisPosition = movesNeededForKingEscape_rec(newState, step+1);
            if (movesFromThisPosition < shortestEscape) {
                shortestEscape = movesFromThisPosition;
            }

        }

        return shortestEscape;

    }


}
