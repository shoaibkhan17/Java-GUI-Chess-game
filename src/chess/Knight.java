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
public class Knight extends Piece {

    public Knight(ChessColour colour) {
        super(colour, ChessPieces.KNIGHT);
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

        // 2 moves up or down and 1 move left or right
        if (Math.abs(srcRow - destRow) == 2 && Math.abs(srcColoumn - destColoumn) == 1) {
            return true;
        } 

        // 1 move up or down and 2 moves left or right
        else if (Math.abs(srcRow - destRow) == 1 && Math.abs(srcColoumn - destColoumn) == 2) {
            return true;
        }

        return false;
    }
}
