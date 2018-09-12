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
public class Queen extends Piece {

    public Queen(ChessColour colour) {
        super(colour, ChessPieces.QUEEN);
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {

        //Moving to the same colour piece
        if (!super.isLegalMove(board, src, dest)) {
            return false;
        }

        int srcColoumn = src.getColumnNumber();
        int srcRow = src.getRowNumber();
        int destColoumn = dest.getColumnNumber();
        int destRow = dest.getRowNumber();

        // All Horizontal and Vertical Moves (two check if something is in the path is not required right now for assignment 2)
        if (srcColoumn == destColoumn || srcRow == destRow) {
            return true;
        } 

        // All Diagonal Moves (two check if something is in the path is not required right now for assignment 2)
        else if (Math.abs(srcColoumn - destColoumn) > 1 && Math.abs(srcRow - destRow) > 1) {
            if (Math.abs(srcColoumn - destColoumn) == Math.abs(srcRow - destRow)) {
                return true;
            }
        }

        return false;
    }
}
