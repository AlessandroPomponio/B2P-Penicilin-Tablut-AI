package b2p.state.bitboard.bitset;

import b2p.model.Turn;

import java.util.ArrayList;
import java.util.BitSet;

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

            // If the king is in the castle, he must be
            // surrounded by 4 black soldiers
            if (king.equals(BitSetPosition.castle)) {

                if (blacks.equals(BitSetPosition.kingSurrounded))
                    specialCaptures.set(BitSetPosition.E5.ordinal());

            } else  {

                if (blacks.equals(BitSetPosition.kingInE4Surrounded) ||
                    blacks.equals(BitSetPosition.kingInD5Surrounded) ||
                    blacks.equals(BitSetPosition.kingInE6Surrounded) ||
                    blacks.equals(BitSetPosition.kingInF5Surrounded))
                    specialCaptures.set(king.nextSetBit(0));

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
