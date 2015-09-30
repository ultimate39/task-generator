package com.galt.java.taskgenerator.ui.fx.view.controller;

import com.galt.java.taskgenerator.App;
import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.floor.Chunk;
import com.galt.java.taskgenerator.core.model.floor.Floor;
import com.galt.java.taskgenerator.core.model.pojo.Device;
import com.galt.java.taskgenerator.core.model.pojo.Subdivision;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;
import com.galt.java.taskgenerator.core.utils.ImageUtils;
import com.galt.java.taskgenerator.core.utils.Logger;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.util.ArrayList;
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
    private TextField tfCountBuildings;
    @FXML
    private MenuItem itemLoadTask;
    @FXML
    private MenuItem itemExportData;
    @FXML
    private ScrollPane taskPane;
    @FXML
    private MenuBar menuBar;

    private App app;

    boolean isGenerated;

    private ContextMenu saveMenuContext;
    private ArrayList<Canvas> mAdditionBuildings;

    @FXML
    private void initialize() {
        saveMenuContext = new ContextMenu();
        MenuItem saveImage = new MenuItem("Сохранить изображение как...");
        saveMenuContext.getItems().addAll(saveImage);
        saveImage.setOnAction(event -> {
            File file = app.showFileChooserDialog();
            if (file != null) {
                Canvas canvas = (Canvas) ((AnchorPane) tabs.getSelectionModel().getSelectedItem().getContent()).getChildren().get(0);
                ImageUtils.saveImage(file.getAbsolutePath(), canvas);
            }
        });
    }

    public void setApp(App app) {
        this.app = app;
        app.getStage().getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                taskPane.setPrefHeight(taskPane.getHeight() + (newValue.intValue() - oldValue.intValue()));
            }
        });
        app.getStage().getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tabs.setPrefWidth(tabs.getWidth() + (newValue.intValue() - oldValue.intValue()));
                taskPane.setPrefWidth(taskPane.getWidth() + (newValue.intValue() - oldValue.intValue()));
                menuBar.setPrefWidth(menuBar.getWidth() + (newValue.intValue() - oldValue.intValue()));
            }
        });
    }

    @FXML
    private void onOkClick() throws Exception {
        long number;
        if (tabs.getTabs().size() > 2) {
            while (tabs.getTabs().size() != 2) {
                tabs.getTabs().remove(tabs.getTabs().size() - 1);
            }
        }
        String numberOfGroup = tfNumberOfGroup.getText();
        String variant = tfVariant.getText();
        number = Long.parseLong(numberOfGroup + variant);
        Generator generator = new Generator(new Random(number));

        //Generate additional floors
        try {
            Long count = Long.parseLong(tfCountBuildings.getText());
            if (count > 2) {
                count -= 2;
                for (int i = 0; i < count; i++) {
                    AnchorPane anchorPane = new AnchorPane();
                    Canvas building = new Canvas(560, 320);
                    anchorPane.getChildren().add(building);

                    GraphicsContext gc = building.getGraphicsContext2D();
                    gc.clearRect(0, 0, building.getWidth(), building.getHeight());
                    Floor floor = generator.generateFloor(70, 40);
                    building.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
                    building.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
                    floor.render(gc);
                    building.setOnMousePressed(event -> {
                        if (event.isSecondaryButtonDown()) {
                            showSaveImageMenu(building, event);
                        }
                    });
                    tabs.getTabs().add(new Tab(String.format("Здание %d", i + 3), anchorPane));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        isGenerated = true;
        tabs.getSelectionModel().select(0);
        generateFormattedText(textConditions, generator.generateTaskConditions());

        floorOne.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                showSaveImageMenu(floorOne, event);
            }
        });

        floorTwo.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) {
                showSaveImageMenu(floorTwo, event);
            }
        });
    }

    private void showSaveImageMenu(Node node, MouseEvent mouseEvent) {
        saveMenuContext.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
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
        int size = 15;
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
        //Размер этажа
        Text sizeOfFloor = new Text("Размер этажа:");
        sizeOfFloor.setFont(Font.font(family, FontWeight.BOLD, size));
        Text sizes = new Text(String.format("%dx%d метров \n", taskConditions.getWidth(), taskConditions.getHeight()));
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
                sizeOfFloor, sizes,
                floors, floorsCounts,
                buildings, buildingsCount);
        textFlow.getAccessibleText();
    }

    @FXML
    private void onExportImagesClick() {
        if (isGenerated) {
            File file = app.showDirectoryChooserDialog();
            if(file != null) {
                if (tabs.getTabs().size() == 2) {
                    ImageUtils.saveImage(file.getAbsolutePath() + "/building1.png", floorOne);
                    if (!tabTwo.isDisabled()) {
                        ImageUtils.saveImage(file.getAbsolutePath() + "/building2.png", floorTwo);
                    }
                } else {
                    for (int tabPos = 0; tabPos < tabs.getTabs().size(); tabPos++) {
                        Canvas canvas = (Canvas) ((AnchorPane) tabs.getTabs().get(tabPos).getContent()).getChildren().get(0);
                        ImageUtils.saveImage(String.format("%s/building%d.png", file.getAbsolutePath(), tabPos + 1), canvas);
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Экспорт картинки");
                alert.setHeaderText(null);
                alert.setContentText("Экспорт произведен успешно!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка экспорта");
            alert.setHeaderText(null);
            alert.setContentText("Сгененируйте сначала план здания");
            alert.showAndWait();
        }
    }

    @FXML
    private void onLoadJsonClick() {
        app.showLoadTaskDataDialog();
    }
}
