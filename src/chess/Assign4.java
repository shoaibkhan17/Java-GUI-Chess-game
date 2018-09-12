/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.FileNotFoundException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Shoaib Khan
 */
public class Assign4 extends Application implements ListChangeListener {

    private ChessBoard board;
    private Button[] whiteTakenSquares;
    private Button[] blackTakenSquares;
    private Button[][] chessSquares;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        BorderPane root = new BorderPane();
        GridPane center = new GridPane();
        Pane left = new VBox();
        Pane right = new VBox();

        // Menu bar feature. Not part of the assignment
        /*
        MenuBar menuBar = new MenuBar();
        Menu newGame = new Menu("New Game");
        menuBar.getMenus().addAll(newGame);
        menuBar.setStyle("-fx-background-color: grey;");
        root.setTop(menuBar);
         */
        root.setCenter(center);
        root.setLeft(left);
        root.setRight(right);

        //Scene scene = new Scene(root, 900, 720);   Reduced this because of my screen size
        Scene scene = new Scene(root, 800, 640);

        board = new ChessBoard();     // Creating a new chessboard
        EventHandler<ActionEvent> sharedHandler = new SquareEventHandler(board);

        whiteTakenSquares = new Button[16];
        for (int i = 0; i < whiteTakenSquares.length; i++) {
            whiteTakenSquares[i] = new Button();
            whiteTakenSquares[i].setStyle("-fx-border-color:white;-fx-background-color: grey;");
            //whiteTakenSquares[i].setMinSize(90,45);    Reduced this because of my screen size
            whiteTakenSquares[i].setMinSize(80, 40);
            left.getChildren().add(whiteTakenSquares[i]);
        }

        blackTakenSquares = new Button[16];
        for (int i = 0; i < blackTakenSquares.length; i++) {
            blackTakenSquares[i] = new Button();
            blackTakenSquares[i].setStyle("-fx-border-color:white;-fx-background-color: grey;");
            //blackTakenSquares[i].setMinSize(90,45);    Reduced this because of my screen size
            blackTakenSquares[i].setMinSize(80, 40);
            right.getChildren().add(blackTakenSquares[i]);
        }

        // Easiest way to colour the chess squares grey and white
        int counter = 2;
        chessSquares = new Button[8][8];

        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c < 8; c++) {
                Coordinate coordinate = new Coordinate(c, r);
                Square square = board.getSquare(coordinate);
                chessSquares[c][r] = new Button(coordinate.name());
                chessSquares[c][r].setOnAction(sharedHandler);

                if (square.getPiece() != null) {
                    Image icon = new Image("file:src/chess/images/" + square.getPiece().getImageName());
                    chessSquares[c][r].setGraphic(new ImageView(icon));
                }

                if (counter % 2 == 0) {
                    chessSquares[c][r].setStyle("-fx-background-color: grey;");
                    chessSquares[c][r].setId("grey");
                } 
                
                else {
                    chessSquares[c][r].setStyle("-fx-background-color: white;");
                    chessSquares[c][r].setId("white");
                }

                counter++;

                //chessSquares[c][r].setMinSize(90, 90);  Reduced this because of my screen size
                chessSquares[c][r].setMinSize(80, 80);
                center.add(chessSquares[c][r], c, 7 - r);
            }

            counter++;
        }

        board.addTakenObserver(this);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onChanged(Change c) {

        while (c.next()) {
            if (c.wasAdded()) {
                int index = c.getFrom();
                List<Piece> pieces = c.getAddedSubList();

                for (Piece p : pieces) {
                    Image icon = new Image("file:src/chess/images/" + p.getImageName());

                    if (p.getColour() == ChessColour.WHITE) {
                        whiteTakenSquares[index].setGraphic(new ImageView(icon));
                    } 
                    
                    else {
                        blackTakenSquares[index].setGraphic(new ImageView(icon));
                    }
                }
            }
        }
    }

    class SquareEventHandler implements EventHandler<ActionEvent> {

        private ChessBoard board;
        private boolean firstClick;
        private Square firstSquare;
        private Square secondSquare;
        private Button firstButton;
        private Button secondButton;

        public SquareEventHandler(ChessBoard board) {
            this.board = board;
            firstClick = false;
            firstSquare = null;
            firstButton = null;
            secondSquare = null;
            secondButton = null;
        }

        @Override
        public void handle(ActionEvent event) {
            
            Button clickedSquare = (Button) event.getSource();

            if (firstClick == false) {
                firstButton = clickedSquare;
                Coordinate c = new Coordinate(firstButton.getText());
                firstSquare = board.getSquare(c);

                // Just for visual effects. Not part of the assignment
                // If a piece was clicked, it was turn the sqaure skyblue
                if (firstSquare.getPiece() != null) {
                    firstButton.setStyle("-fx-background-color: skyblue;");
                    movesIndicator(c);
                } 

                // If an empty peice was clicked, it will turn the sqaure red
                else {
                    firstButton.setStyle("-fx-background-color: red;");
                }

                secondButton = null;
                secondSquare = null;
                firstClick = true;
            } 
            
            else {
                removeEffect();
                secondButton = clickedSquare;
                Coordinate c = new Coordinate(secondButton.getText());
                secondSquare = board.getSquare(c);
                firstClick = false;

                // Moving the pieces if it s a valid move
                if (board.move(firstSquare.getCoordinate(), secondSquare.getCoordinate())) {
                    secondButton.setGraphic(firstButton.getGraphic());
                    firstButton.setGraphic(null);
                }
            }
        }

        // Just for visual effects. Not part of the assignment
        public void movesIndicator(Coordinate src) {
            DropShadow shadow = new DropShadow();
            for (int r = 7; r >= 0; r--) {
                for (int c = 0; c < 8; c++) {                    
                    Coordinate dest = new Coordinate(c, r);

                    if (board.possibleMoves(src, dest)) {
                        chessSquares[c][r].setStyle("-fx-border-color:black; -fx-background-color: green;");
                        chessSquares[c][r].setEffect(shadow);
                    }
                }
            }
        }

        // Just for visual effects. Not part of the assignment
        public void removeEffect() {
            for (int r = 7; r >= 0; r--) {
                for (int c = 0; c < 8; c++) {                    
                    chessSquares[c][r].setEffect(null);
                    
                    if (chessSquares[c][r].getId() == "white") {
                        chessSquares[c][r].setStyle("-fx-background-color: white;");
                    }
                    
                    else {
                        chessSquares[c][r].setStyle("-fx-background-color: grey;");
                    }
                }
            }
        }
    }
}
