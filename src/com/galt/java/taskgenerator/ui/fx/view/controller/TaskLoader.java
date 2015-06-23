package com.galt.java.taskgenerator.ui.fx.view.controller;

import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;
import com.galt.java.taskgenerator.core.utils.CryptoUtils;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.io.File;
import java.util.Random;

/**
 * Created by Grishechko on 24.05.2015.
 */
public class TaskLoader {
    @FXML
    TextField password;
    @FXML
    TextArea textArea;
    @FXML
    Button ok;
    @FXML
    Button loadJson;
    @FXML
    Text jsonLoadStatus;

    @FXML
    private void initialize() {
        //Nothing
    }

    boolean isIncorrectPassword = false;

    @FXML
    private void onOkClick() throws CryptoUtils.CryptoException {
        String password = CryptoUtils.getPassword();
        String hash = CryptoUtils.getSHA(this.password.getText());
        if(password.equals(hash)) {
            loadJson.setDisable(false);
            textArea.setDisable(false);
        } else {
            loadJson.setDisable(true);
            textArea.setDisable(true);
            isIncorrectPassword = true;
            jsonLoadStatus.setVisible(false);
            this.password.clear();
            this.password.setPromptText("Неверный пароль!");
        }
    }

    @FXML
    private void onPasswordTyped() {
        if(isIncorrectPassword) {
            isIncorrectPassword = false;
            this.password.setPromptText("Введите пароль");
        }
    }

    @FXML
    private void onLoadJsonClick() throws CryptoUtils.CryptoException {
        Generator generator = new Generator(new Random());
        TaskConditions taskConditions = null;
        try {
            taskConditions = generator.generateTaskConditions(textArea.getText());
            if(!isJsonValid(textArea.getText()) || taskConditions == null) {
                jsonLoadStatus.setVisible(true);
                jsonLoadStatus.setText("Неправильный JSON!");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonLoadStatus.setVisible(true);
            jsonLoadStatus.setText("Неправильный JSON!");
            return;
        }
        CryptoUtils.encrypt(textArea.getText(), new File("net-task-generator-setting.dat"));
        jsonLoadStatus.setVisible(true);
        jsonLoadStatus.setText("JSON успешно загружен");
    }

    private boolean isJsonValid(String json) {
        try {
            new Gson().fromJson(json, TaskData.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
