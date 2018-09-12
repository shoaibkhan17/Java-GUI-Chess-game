/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import static java.lang.Math.abs;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Cheryl
 * @author Shoaib Khan
 */
public class ChessBoard {

    private Square board[][];
    private ChessColour activeColour;
    private ArrayList<Piece> whiteTakenPieces;
    private ArrayList<Piece> blackTakenPieces;
    private ObservableList<Piece> whiteObservableList;
    private ObservableList<Piece> blackbservableList;
    private int fullMove;

    public ChessBoard() {
        board = new Square[8][8];
        for (int c = 0; c < 8; c++) {
            for (int r = 0; r < 8; r++) {
                board[c][r] = new Square(new Coordinate(c, r));
            }
        }
        reset();
        //takenList = new ArrayList(); 
        whiteTakenPieces = new ArrayList();
        blackTakenPieces = new ArrayList();
        whiteObservableList = FXCollections.observableList(whiteTakenPieces);
        blackbservableList = FXCollections.observableList(blackTakenPieces);
        activeColour = ChessColour.WHITE;
        fullMove = 1;
    }

    public ChessBoard(Coordinate positions[], Piece pieces[]) throws IllegalArgumentException {
        if (positions.length != pieces.length) {
            throw new IllegalArgumentException("The list of positions must correspond to the list of pieces");
        }

        board = new Square[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = new Square(new Coordinate(r, c));
            }
        }
        for (int i = 0; i < positions.length; i++) {
            board[positions[i].getColumnNumber()][positions[i].getRowNumber()].addPiece(pieces[i]);
        }

        //takenList = new ArrayList();
        whiteTakenPieces = new ArrayList();
        blackTakenPieces = new ArrayList();
        whiteObservableList = FXCollections.observableList(whiteTakenPieces);
        blackbservableList = FXCollections.observableList(blackTakenPieces);
        activeColour = ChessColour.WHITE;
        fullMove = 1;
    }

    private void reset() {
        // White and Black Pawns
        for (int c = 0; c < 8; c++) {
            board[c][6].addPiece(new Pawn(ChessColour.BLACK));
            board[c][1].addPiece(new Pawn(ChessColour.WHITE));
        }

        // Remaining White Pieces
        board[0][7].addPiece(new Rook(ChessColour.BLACK));
        board[1][7].addPiece(new Knight(ChessColour.BLACK));
        board[2][7].addPiece(new Bishop(ChessColour.BLACK));
        board[3][7].addPiece(new Queen(ChessColour.BLACK));
        board[4][7].addPiece(new King(ChessColour.BLACK));
        board[5][7].addPiece(new Bishop(ChessColour.BLACK));
        board[6][7].addPiece(new Knight(ChessColour.BLACK));
        board[7][7].addPiece(new Rook(ChessColour.BLACK));

        // Remaining Black Pieces 
        board[0][0].addPiece(new Rook(ChessColour.WHITE));
        board[1][0].addPiece(new Knight(ChessColour.WHITE));
        board[2][0].addPiece(new Bishop(ChessColour.WHITE));
        board[3][0].addPiece(new Queen(ChessColour.WHITE));
        board[4][0].addPiece(new King(ChessColour.WHITE));
        board[5][0].addPiece(new Bishop(ChessColour.WHITE));
        board[6][0].addPiece(new Knight(ChessColour.WHITE));
        board[7][0].addPiece(new Rook(ChessColour.WHITE));

        // Deleting Pieces in the middle in case we reset the board in the middle
        // of the game.
        Piece p;
        for (int c = 0; c < 8; c++) {
            for (int r = 2; r < 6; r++) {
                p = board[c][r].deletePiece();
            }
        }

        // Deleting all pieces from the taken lists. Not part of the assignment
        /* Dont need it right now 
        takenList.clear();
        whiteTakenPieces.clear();
        blackTakenPieces.clear();
         */
    }

    protected Square getSquare(Coordinate c) {
        return board[c.getColumnNumber()][c.getRowNumber()];
    }

    // For visual effects to change the button green if it is a valid move
    // Not part of the assignment
    public boolean possibleMoves(Coordinate src, Coordinate dest) {
        Square srcSquare = this.getSquare(src);

        if (!srcSquare.isOccupied()) {
            return false;
        }

        Piece p = srcSquare.getPiece();

        if (!p.getColour().equals(activeColour)) {
            return false;
        }

        // It is a bit difficult for pawn, because firstMove is a private variable. We have no get and set menthods.
        if (p instanceof Pawn) {
            Square destSquare = this.getSquare(dest);
            int deltaY = abs(src.getRowNumber() - dest.getRowNumber());
            int deltaX = abs(src.getColumnNumber() - dest.getColumnNumber());

            if (p.getColour() == ChessColour.WHITE && src.getRowNumber() == 1) {
                if (deltaY == 1 && deltaX == 0 && destSquare.isOccupied() == false) {
                    return true;
                }

                if (deltaY == 2 && deltaX == 0 && destSquare.isOccupied() == false) {
                    return true;
                }
            }
            
            else if (p.getColour() == ChessColour.BLACK && src.getRowNumber() == 6) {
                if (deltaY == 1 && deltaX == 0 && destSquare.isOccupied() == false) {
                    return true;
                } else if (deltaY == 2 && deltaX == 0 && destSquare.isOccupied() == false) {
                    return true;
                }
            } 
            
            else {
                return p.isLegalMove(this, src, dest);
            }
        } 
        
        else {
            return p.isLegalMove(this, src, dest);
        }

        return false;
    }

    public boolean move(Coordinate src, Coordinate dest) {

        Square srcSquare = this.getSquare(src);
        if (!srcSquare.isOccupied()) {
            return false;
        }

        Piece p = srcSquare.getPiece();
        if (!p.getColour().equals(activeColour)) {
            return false;
        }

        if (p.isLegalMove(this, src, dest)) {
            Square destSquare = this.getSquare(dest);
            Piece takenPiece = destSquare.addPiece(p);

            if (takenPiece != null) // If a piece was returned 
            {
                //takenList.add(takenPiece);  
                if (takenPiece.getColour() == ChessColour.BLACK) {
                    blackbservableList.add(takenPiece);
                } 
                
                else {
                    whiteObservableList.add(takenPiece);
                }
            }

            srcSquare.deletePiece();
            activeColour = (activeColour == ChessColour.BLACK) ? ChessColour.WHITE : ChessColour.BLACK;

            if (activeColour == ChessColour.WHITE) {
                fullMove++;
            }

            return true;
        } 
        
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        String boardPrint = "Board:Board\n";

        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                if (board[c][r].getPiece() != null) {
                    boardPrint += board[c][r].getPiece().getShortName();
                } 
                
                else {
                    boardPrint += " ";
                }

                boardPrint += "_ ";
            }

            boardPrint += "\n";
        }

        return boardPrint;
    }

    public String toFEN() {
        String boardFEN = "FEN:";
        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                if (board[c][r].getPiece() != null) {
                    boardFEN += board[c][r].getPiece().getShortName();
                } 
                
                else {
                    boardFEN += " ";
                }
            }

            boardFEN += "/";
        }

        boardFEN += (" w " + fullMove + "\n");
        return boardFEN;
    }

    // Returns a string containing the list of short names of all pieces taken.
    // All WHITE pieces are listed first, followed by a comma,
    // followed by the list of all BLACK pieces (no spaces).
    public String toTakenString() {
        String takenString = "";

        // For all white pieces 
        for (int i = 0; i < whiteTakenPieces.size(); i++) {
            if (Character.isUpperCase(whiteTakenPieces.get(i).getShortName())) {
                takenString += whiteTakenPieces.get(i).getShortName();
            }
        }

        takenString += ",";

        // For all black pieces
        for (int i = 0; i < blackTakenPieces.size(); i++) {
            if (!Character.isUpperCase(blackTakenPieces.get(i).getShortName())) {
                takenString += blackTakenPieces.get(i).getShortName();
            }
        }

        return takenString;
    }

    public void addTakenObserver(ListChangeListener listener) {
        whiteObservableList.addListener(listener);
        blackbservableList.addListener(listener);
    }
}
