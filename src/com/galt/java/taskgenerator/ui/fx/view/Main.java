package com.galt.java.taskgenerator.ui.fx.view;

import com.galt.java.taskgenerator.App;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private Canvas canvas;
    @FXML
    private TextField input;
    @FXML
    private TextFlow textConditions;
    @FXML
    private CheckBox cbStudentTicket;
    @FXML
    private CheckBox cbNumberOfGroup;
    @FXML
    private TextField tfNumberOfGroup;
    @FXML
    private TextField tfVariant;
    @FXML
    private Button btnOkGroup;
    @FXML
    private Button btnOkStudentTicket;

    App app;

    public void setApp(App mainApp) {
        this.app = mainApp;
        // Add observable list data to the table
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        Generator generator = new Generator(new Random());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Floor floor = generator.generateFloor(70, 40);
        canvas.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
        canvas.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
        floor.render(gc);
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
    }

    @FXML
    private void onOkClick() {
        long number = -1;
        if(cbStudentTicket.isSelected()) {
             number = Long.parseLong(input.getText());
        } else {
             String numberOfGroup = tfNumberOfGroup.getText();
             String variant = tfVariant.getText();
             number = Long.parseLong(numberOfGroup + variant);
        }
        Generator generator = new Generator(new Random(number));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Floor floor = generator.generateFloor(70, 40);
        canvas.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
        canvas.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
        floor.render(gc);
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

    @FXML
    private void onCbStudentTicketClick() {
        input.setDisable(false);
        cbNumberOfGroup.setSelected(false);
        cbStudentTicket.setSelected(true);
        tfNumberOfGroup.setDisable(true);
        tfVariant.setDisable(true);
        btnOkStudentTicket.setDisable(false);
        btnOkGroup.setDisable(true);
    }

    @FXML
    private void onCbNumberOfGroupClick() {
        input.setDisable(true);
        cbNumberOfGroup.setSelected(true);
        cbStudentTicket.setSelected(false);
        tfNumberOfGroup.setDisable(false);
        tfVariant.setDisable(false);
        btnOkGroup.setDisable(false);
        btnOkStudentTicket.setDisable(true);
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
        for(Subdivision subdivision : taskConditions.getOrganization().getSubdivisions()) {
            builder.append(subdivision.getName())
                    .append(": ")
                    .append(subdivision.getPlaces()).append(" РМ");
            for(Device device : subdivision.getDevices()) {
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
        for(String resource : taskConditions.getOrganization().getResources()) {
            resources.append(resource).append(", ");
        }
        Text informationResourcesItems = new Text(resources.toString() + "\n");
        informationResourcesItems.setFont(Font.font(family, size - 1));
        //Внешняя сеть
        Text externalLsn = new Text("Внешняя сеть: ");
        externalLsn.setFont(Font.font(family, FontWeight.BOLD, size));
        Text lan  = new Text(taskConditions.getExternalLan());
        lan.setFont(Font.font(family, size));
        textFlow.getChildren().addAll(organization, subdivisions, subdivisionsItems, summary, informatioResources, informationResourcesItems, externalLsn, lan);
    }


}
