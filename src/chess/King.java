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
public class King extends Piece {

    public King(ChessColour colour) {
        super(colour, ChessPieces.KING);
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {

        // Moving to the same colour piece
        if (!super.isLegalMove(board, src, dest)) {
            return false;
        }

        int deltaX = src.getColumn() - dest.getColumn();
        int deltaY = src.getRow() - dest.getRow();
        
        if ( Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) 
        {
            return false;
        }
        
        return true;
    }
}
