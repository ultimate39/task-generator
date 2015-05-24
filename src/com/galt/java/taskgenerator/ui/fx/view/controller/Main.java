package com.galt.java.taskgenerator.ui.fx.view.controller;
import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.floor.Chunk;
import com.galt.java.taskgenerator.core.model.floor.Floor;
import com.galt.java.taskgenerator.core.model.pojo.Device;
import com.galt.java.taskgenerator.core.model.pojo.Subdivision;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;
import com.galt.java.taskgenerator.core.uitls.Logger;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.util.Random;

/**
 * Created by vladislav on 4/16/15.
 */
public class Main {
    @FXML
    private Canvas floorOne;

    @FXML
    private Canvas floorTwo;

    @FXML
    private Tab tabTwo;

    @FXML
    private TabPane tabs;

    @FXML
    private TextFlow textConditions;

    @FXML
    private TextField tfNumberOfGroup;

    @FXML
    private TextField tfVariant;

    @FXML
    private void initialize() {
        //Nothing
    }

    @FXML
    private void onOkClick() throws Exception {
        long number;
        String numberOfGroup = tfNumberOfGroup.getText();
        String variant = tfVariant.getText();
        number = Long.parseLong(numberOfGroup + variant);
        Generator generator = new Generator(new Random(number));

        TaskConditions taskConditions = generator.generateTaskConditions();

        GraphicsContext gc = floorOne.getGraphicsContext2D();
        gc.clearRect(0, 0, floorOne.getWidth(), floorOne.getHeight());
        Floor floor = generator.generateFloor(70, 40);
        floorOne.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
        floorOne.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
        floor.render(gc);

        if (Integer.valueOf(taskConditions.getBuildings()) > 1) {
            tabTwo.setDisable(false);
            generator = new Generator(new Random(-number));
            gc = floorTwo.getGraphicsContext2D();
            gc.clearRect(0, 0, floorOne.getWidth(), floorOne.getHeight());
            floor = generator.generateFloor(70, 40);
            floorTwo.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
            floorTwo.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
            floor.render(gc);
        } else {
            tabTwo.setDisable(true);
        }
        tabs.getSelectionModel().select(0);
        generateFormattedText(textConditions, generator.generateTaskConditions());
    }

    private String readText() {
        File file = new File("assets/data.json");
        TaskData task = null;
        try {
            task = new Gson().fromJson(new FileReader(file), TaskData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return task.toString();
    }

    private void generateFormattedText(TextFlow textFlow, TaskConditions taskConditions) {
        textFlow.getChildren().clear();
        String family = "Helvetica";
        int size = 20;
        //Организация
        Text organization = new Text(taskConditions.getOrganization().getName() + "\n");
        organization.setFont(Font.font(family, FontWeight.BOLD, size));
        //Структурные подразделения
        Text subdivisions = new Text("Структурные подразделения: ");
        subdivisions.setFont(Font.font(family, size));
        StringBuilder builder = new StringBuilder();
        for (Subdivision subdivision : taskConditions.getOrganization().getSubdivisions()) {
            builder.append(subdivision.getName())
                    .append(": ")
                    .append(subdivision.getPlaces()).append(" РМ");
            for (Device device : subdivision.getDevices()) {
                builder.append(" + ")
                        .append(device.getCount())
                        .append(" ")
                        .append(device.getName());
            }
            //TODO remove last comma (',')
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), ".");
        Text subdivisionsItems = new Text(builder.toString() + "\n");
        subdivisionsItems.setFont(Font.font(family, size));
        //Итого
        Text summary = new Text("Итого: " + taskConditions.getPlaces() + " РМ" + " + " + taskConditions.getDevices() + " устр-в.\n");
        summary.setFont(Font.font(family, size));
        //Информационные ресурсы
        StringBuilder resources = new StringBuilder();
        Text informatioResources = new Text("Информационные ресуры: ");
        informatioResources.setFont(Font.font(family, FontWeight.BOLD, size));
        //TODO Remove last comma
        Logger.d(taskConditions.getOrganization().getResources().size());
        for (String resource : taskConditions.getOrganization().getResources()) {
            resources.append(resource).append(", ");
        }
        resources.replace(resources.length() - 2, resources.length(), ".");
        Text informationResourcesItems = new Text(resources.toString() + "\n");
        informationResourcesItems.setFont(Font.font(family, size - 1));
        //Внешняя сеть
        Text externalLsn = new Text("Внешняя сеть: ");
        externalLsn.setFont(Font.font(family, FontWeight.BOLD, size));
        Text lan = new Text(taskConditions.getExternalLan() + "\n");
        lan.setFont(Font.font(family, size));
        //Этажей
        Text floors = new Text("Этажей: ");
        floors.setFont(Font.font(family, FontWeight.BOLD, size));
        Text floorsCounts = new Text(taskConditions.getFloors() + "\n");
        floorsCounts.setFont(Font.font(family, size));
        //Зданий
        Text buildings = new Text("Зданий: ");
        buildings.setFont(Font.font(family, FontWeight.BOLD, size));
        Text buildingsCount = new Text(taskConditions.getBuildings());
        buildingsCount.setFont(Font.font(family, size));
        textFlow.getChildren().addAll(organization,
                subdivisions, subdivisionsItems,
                summary,
                informatioResources, informationResourcesItems,
                externalLsn, lan,
                floors, floorsCounts,
                buildings, buildingsCount);
    }
}
