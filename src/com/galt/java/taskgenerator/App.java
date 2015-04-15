package com.galt.java.taskgenerator;


import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.Floor;
import com.galt.java.taskgenerator.core.model.Hall;
import com.galt.java.taskgenerator.ui.Canvas;
import com.galt.java.taskgenerator.ui.FloorScreen;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Генератор курсовых");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 400, 150);
        primaryStage.setScene(scene);
        Text scenetitle = new Text("Введите номер зачетки");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Номер:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Button btn = new Button("Ок");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                GridPane root = new GridPane();
                root.setAlignment(Pos.CENTER);
                root.add(new ProgressIndicator(), 1, 1);
                Scene scene2 = new Scene(root, 700, 500);

                MenuBar menuBar = new MenuBar();

                // --- Menu File
                Menu menuFile = new Menu("File");

                menuBar.getMenus().addAll(menuFile);

                primaryStage.setScene(scene2);
            }
        });

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 2, 1);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Canvas canvas = new Canvas(800, 800);
        final Generator g = new Generator(new Random());
        final Floor floor = g.generateFloor(70, 40);
        final FloorScreen floorScreen = new FloorScreen(floor, canvas);
        floorScreen.display();
        floorScreen.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_F5:
                        System.out.println("Key down");
                        floorScreen.changeFloor(g.generateFloor(70, 40));
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        System.out.println(floor);
        /*
        App.launch(args);*/
    }

}
