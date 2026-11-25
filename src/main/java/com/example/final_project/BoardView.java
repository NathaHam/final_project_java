package com.example.final_project;

import javafx.scene.layout.GridPane;

public class BoardView extends GridPane 
{
    private final SquareView[][] squares = new SquareView[8][8];

    public BoardView() 
    {
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                squares[row][col] = new SquareView(row, col);
                add(squares[row][col], col, row);
            }
        }
    }

    public SquareView getSquare(int row, int col) 
    {
        return squares[row][col];
    }

    public void clearAll() 
    {
        for (SquareView[] rank : squares) 
        {
            for (SquareView square : rank) 
            {
                square.clearPiece();
            }
        }
    }

    public void showFen(String fen) 
    {
        clearAll();
        // next -> read FEN when chesslib added, and set pieces on squares accordingly
    }
}