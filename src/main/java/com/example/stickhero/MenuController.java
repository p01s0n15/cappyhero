package com.example.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;


public class MenuController {
    Parent root;
    Stage stage;
    Scene scene;


    @FXML
    public Text score;
    public void switchToScene2(ActionEvent event) throws IOException {
         root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scene1.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void loadScore()
    {
        int points = 0;

        try (FileInputStream fileInputStream = new FileInputStream("save.txt");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            // Read the points from the file
            points = objectInputStream.readInt();

        } catch (Exception e) {
            e.printStackTrace();
        }
        score.setText(String.valueOf(points));
    }

}
