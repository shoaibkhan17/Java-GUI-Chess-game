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
public class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(ChessColour colour) {
        super(colour, ChessPieces.PAWN);
        firstMove = true;
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {
        
        //Moving to the same colour piece
        if (!super.isLegalMove(board, src, dest)) {
            return false;
        }
        
        int deltaX = src.getColumn() - dest.getColumn();
        int deltaY = src.getRow() - dest.getRow();
        
        if (this.getColour() == ChessColour.BLACK && deltaY <= 0) 
        {
            return false;
        } 
        
        if (super.getColour() == ChessColour.WHITE && deltaY >= 0) 
        {
            return false;
        }
        
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        
        //if (src.getColumnNumber() == 7)
        
        if (deltaX == 0 && deltaY == 1 & !board.getSquare(dest).isOccupied())
            {   
                firstMove = false; 
                return true; 
            }
        
        if (firstMove && deltaX == 0 && deltaY == 2 & !board.getSquare(dest).isOccupied()) 
            {
                firstMove = false;
                return true; 
            }
                
        if (deltaX == 1 && deltaY == 1 && board.getSquare(dest).isOccupied())
            {   
                firstMove = false; 
                return true;   
            }
                
        return false;
    }
}
