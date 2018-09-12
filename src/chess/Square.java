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
public class Square {

    private Coordinate coordinate;
    private Piece piece;

    public Square(Coordinate c) {
        coordinate = c;
        piece = null;
    }

    public Square(Coordinate c, Piece p) {
        coordinate = c;
        piece = p;
    }

    public char getColumn() {
        return coordinate.getColumn();
    }

    public char getRow() {
        return coordinate.getRow();
    }

    public int getColumnNumber() {
        return coordinate.getColumnNumber();
    }

    public int getRowNumber() {
        return coordinate.getRowNumber();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Piece getPiece() {
        return piece;
    }

    public Piece addPiece(Piece newPiece) {
        Piece previousPiece = piece;
        piece = newPiece;
        return previousPiece;
    }

    public Piece deletePiece() {
        Piece previousPiece = piece;
        piece = null;
        return previousPiece;
    }

    public boolean isOccupied() {
        if (piece == null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String p = (piece == null) ? " " : piece.toString();
        return ("Square" + coordinate.toString() + ":" + p);
    }
}
