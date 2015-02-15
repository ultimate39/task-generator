package com.galt.java.taskgenerator;

import com.galt.java.taskgenerator.core.Floor;
import com.galt.java.taskgenerator.core.Hall;
import com.galt.java.taskgenerator.ui.Canvas;
import com.galt.java.taskgenerator.ui.FloorScreen;
import com.galt.java.taskgenerator.ui.Screen;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Canvas canvas = new Canvas(1000, 1000);
        Floor floor = new Floor(80, 30);
        Hall hall = new Hall(1, 1, 2, 10, Hall.HORIZONTAL);
        floor.getHalls().add(hall);
        FloorScreen screen = new FloorScreen(floor, canvas);
        screen.display();
    }

}
