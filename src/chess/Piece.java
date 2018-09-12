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
public class Piece {

    private ChessColour colour;
    private ChessPieces name;
    private char shortName;
    private String imageName;

    public Piece(ChessColour colour, ChessPieces name) {
        this.colour = colour;
        this.name = name;
        shortName = this.name.getShortName();

        if (this.colour == ChessColour.BLACK) {
            String col = "" + this.shortName;
            col = col.toLowerCase();
            char c = col.charAt(0);
            shortName = c;
        }
        
        imageName = this.colour.toString().toLowerCase() + "_" + this.name.toString().toLowerCase() + ".png";
    }

    public ChessColour getColour() {
        return colour;
    }

    public ChessPieces getName() {
        return name;
    }
    
    public char getShortName() {
        return shortName;
    }
    
    public String getImageName()
    {
        return imageName;
    }

    public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {
        
        Square srcSquare = board.getSquare(src);
        Piece srcPiece = srcSquare.getPiece();
        Square destSquare = board.getSquare(dest);

        if (destSquare.isOccupied()) {
            Piece destPiece = destSquare.getPiece();

            // If white moves on white or black moves on black
            if (srcPiece.getColour().equals(destPiece.getColour())) {
                return false;
            }
        }
        return true;
        
    }

    @Override
    public String toString() {
        return (colour + " " + name);
    }
}
