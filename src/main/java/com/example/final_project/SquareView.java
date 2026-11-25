package com.example.final_project;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SquareView extends StackPane 
{
    public static final int SIZE = 70;
    private static final String LIGHT = "#f0d9b5";
    private static final String DARK = "#b58863";

    private final int row;   
    private final int col; 
    private final Text pieceLabel = new Text();

    public SquareView(int row, int col) 
    {
        this.row = row;
        this.col = col;
        setPrefSize(SIZE, SIZE);
        setStyle("-fx-background-color: " + (isLight() ? LIGHT:DARK) + ";");
        getChildren().add(pieceLabel);
    }

    public boolean isLight()
    {
        return (row + col) % 2 == 0;
    }

    public int getRow() 
    { 
        return row; 
    }

    public int getCol()
    { 
        return col;
    }

    public void setPiece(String glyph)
    { 
        pieceLabel.setText(glyph);
    }

    public void clearPiece() 
    { 
        pieceLabel.setText("");
    }

    // next -> piece size, font, and color implementation
}