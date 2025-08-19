/*
 * CSC210 Fall 2024
 * Project 7 -- Garden User GUI
 * Ethan Alter
 */
package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import javafx.scene.control.Label;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    // variables that will be read in from file

    private static int cols=5;
    private static int rows=5;

    // constants for the program
    private final static int TEXT_SIZE = 120;
    private final static int RECT_SIZE = 20;

    // temporary constants for starter code
    private final static int PLOT_LENGTH = 80;
    private static final int SMALL_SQUARES = 5; // Number of small squares per plot (5x5)
    private static final int GAP = 10;           // Gap between each small square
    private static final int PLOT_GAP = 15;
    // mega list to house (x,y) coord of each square on garden
    private static String[][][][] garden;
    // changes square size (to fit) if other variables change
    private static final int SQUARE_SIZE = (PLOT_LENGTH / SMALL_SQUARES) - GAP;
    private static Garden gardenRef;
    private static GraphicsContext gc;
    private static TextArea commandText;

    public static void setGardenRef(Garden mainGarden) {
        gardenRef=mainGarden;
    }
    // sets up garden with # of rows/cols and adds buttons for continual use
    @Override
    public void start(Stage stage) throws IOException {
        setGardenRef(new Garden(cols,rows));
        stage.setTitle("Ethan's Garden");
        // we will be using a panel
        BorderPane myPanel = new BorderPane();
        garden = new String[cols][rows][5][5];
        int length=(PLOT_LENGTH+PLOT_GAP)*cols-PLOT_GAP;
        int height=(PLOT_LENGTH+PLOT_GAP)*rows-PLOT_GAP;
        Canvas myCanvas = new Canvas(length, height);
        this.gc = myCanvas.getGraphicsContext2D();
        // set up text area
        this.commandText = new TextArea();
        commandText.setPrefHeight(150);
        commandText.setEditable(false);
        GridPane myGrid = new GridPane();
        myGrid.setPadding(new Insets(15, 15, 15, 15));
        myGrid.setHgap(10);
        // place canvas and text area in the panel
        myPanel.setCenter(myCanvas);
        myPanel.setTop(myGrid);
        myPanel.setBottom(commandText);
        Label entry = new Label("Enter Command: ");
        myGrid.getChildren().add(entry);
        TextField userEntry = new TextField();
        GridPane.setConstraints(userEntry,1,0);
        myGrid.getChildren().add(userEntry);
        setButton(myGrid, userEntry);
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                String[][] plot = drawPlot(gc, col * (PLOT_LENGTH+PLOT_GAP), 
                row * (PLOT_LENGTH+PLOT_GAP));
                garden[col][row]=plot;
            }
        }
        scene = new Scene(myPanel, length+30, height+200);
        stage.setScene(scene);
        stage.show();
    }
    // sets up execute button
    private void setButton(GridPane myGrid, TextField userEntry) {
        Button execute = new Button("Execute");
        GridPane.setConstraints(execute, 2, 0);
        myGrid.getChildren().add(execute);
        execute.setOnAction((actionEvent) -> {
            String command = userEntry.getText();
            String[] splitLine = command.split(" ");
            ArrayList<String> cmdLine = new ArrayList<>
                (Arrays.asList(splitLine));
            userEntry.clear();
            simulateGarden(gc, cmdLine);
        });
    }
    // draws garden plot based on # of rows and cols
    private String[][] drawPlot(GraphicsContext gc, int startX, int startY) {
        String[][] plot = new String[5][5];
        // Draws grid of plants/dirt within singular plot
        for (int col = 0; col < SMALL_SQUARES; col++) {
            for (int row = 0; row < SMALL_SQUARES; row++) {
                // accounts for gap between squares within plot
                int x = startX + col * (SQUARE_SIZE + GAP);
                int y = startY + row * (SQUARE_SIZE + GAP);
                String location = String.valueOf(x) + "," + String.valueOf(y);
                plot[col][row] = location;
    
                // Set color to brown dirt
                Color c = Color.web("964B00");
                gc.setFill(c);
                gc.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        return plot;
    }
    // creates visual plant mechanism for garden
    public static void add(int gardX, int gardY, Integer plotX, Integer plotY, String type) {
        String[] coords=garden[gardX][gardY][plotX][plotY].split(",");
        int x=Integer.valueOf(coords[0]);
        int y=Integer.valueOf(coords[1]);
        Color c = Color.web("964B00");
        // tree=light green, flower=pink, vegetable=orange, vine=dark green
        if (type.equals("tree")) {
            c = Color.web("39FF14");
        } else if (type.equals("flower")) {
            c = Color.web("FFC0CB");
        } else if (type.equals("vegetable")) {
            c = Color.web("FFA500");
        } else {
            c = Color.web("06402B");
        }
        gc.setFill(c);
        gc.fillRect(x, y, SQUARE_SIZE+2, SQUARE_SIZE+2);
    }
    // removes plant after command (sets plot back to dirt)
    public static void remove(int gardX, int gardY) {
        String[][] plot=garden[gardX][gardY];
        Color n = Color.web("964B00");
        gc.setFill(n);
        for (String[] col: plot) {
            for (String square : col) {
                // clears whole plot then replaces dirt tiles
                String[] remCoords=square.split(",");
                int remX=Integer.valueOf(remCoords[0]);
                int remY=Integer.valueOf(remCoords[1]);
                gc.clearRect(remX,remY,SQUARE_SIZE+2,SQUARE_SIZE+2);
                gc.fillRect(remX, remY, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
    // initiates user entered commands
    private void simulateGarden(GraphicsContext gc,ArrayList<String> commands) {
        String cmdText=RunGarden.initiateCommands(gardenRef, commands);
        commandText.appendText(cmdText +"\n");
    }
    // adds errors to text area
    public static void printError(String error) {
        commandText.appendText(error);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //cols=Integer.valueOf(args[0]);
        //rows=Integer.valueOf(args[1]);
        launch();
    }

}