package com.galt.java.taskgenerator;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*Canvas canvas = new Canvas(800, 800);
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
        System.out.println(floor);*/
        App.launch(args);
    }

}
