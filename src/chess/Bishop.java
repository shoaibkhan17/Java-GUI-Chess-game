/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Shoaib Khan
 */
public class Bishop extends Piece {

    public Bishop(ChessColour colour) {
        super(colour, ChessPieces.BISHOP);
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {

        // Moving to the same colour piece
        if (!super.isLegalMove(board, src, dest)) {
            return false;
        }

        int srcColoumn = src.getColumnNumber();
        int srcRow = src.getRowNumber();
        int destColoumn = dest.getColumnNumber();
        int destRow = dest.getRowNumber();

        // All Diagonal Moves (two check if something is in the path is not required right now for assignment 2)
        if (Math.abs(srcColoumn - destColoumn) > 1 && Math.abs(srcRow - destRow) > 1) {
            if (Math.abs(srcColoumn - destColoumn) == Math.abs(srcRow - destRow)) {
                return true;
            }
        }

        return false;
    }
}
