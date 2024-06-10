package com.example.stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
    private Stage stage;
    private int points = 0;

    @FXML
    private ImageView stick;
    @FXML
    private ImageView cherry1;
    @FXML
    private ImageView cherry2;
    @FXML
    private ImageView capo;
    @FXML
    private Rectangle pillar1;

    @FXML
    private Text pointsLabel;

    @FXML
    private Rectangle pillar2;

    @FXML
    private Rectangle pillar3;

    private Scene scene;
    private Parent root;

    boolean mousePressed;
    public boolean inversionaallowed;

    public int inverted;


    public void switchToScene1(ActionEvent event) throws IOException {
        switchScenes();
    }

    private void switchScenes() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameOver.fxml")));
        stage = HelloApplication.getStage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int RandomWidth() {
        Random rand = new Random();
        final int MIN_SIZE = 50;
        final int MAX_SIZE = 120;

        int width = 0;
        while (width < MIN_SIZE)
            width = rand.nextInt(MAX_SIZE);

        return width;
    }

    public double Randomdistance(Rectangle pillarx) {
        Random rand = new Random();
        final double MIN_SIZE = pillarx.getWidth() + 50;
        final double MAX_SIZE = pillarx.getWidth() + 300;

        double width = 0;
        while (width < MIN_SIZE)
            width = rand.nextDouble(MAX_SIZE);

        return width;
    }

    public int pillarnum = 0;

    public void elongateRod(MouseEvent event) throws IOException, InterruptedException {
        if (inverted == 0) {
            mousePressed = true;
            Thread t1 = new Thread(() -> {
                while (mousePressed) {
                    System.out.println(stick.getLayoutY());
                    stick.setFitHeight(stick.getFitHeight() + 5);
                    System.out.println(stick.getFitHeight());
                    System.out.println(stick.getFitWidth());
                    stick.setLayoutY(stick.getLayoutY() - 5);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

            });
            t1.start();
        }
    }

    public void invertcapy(KeyEvent event) {

        if (event.getCode() == KeyCode.W && inverted == 0 && inversionaallowed) {
            Point3D point = new Point3D(1.0, 0, 0);
            capo.setRotationAxis(point);
            capo.setLayoutY(capo.getLayoutY() + 78);
            inverted = 1;
        } else if (event.getCode() == KeyCode.W && inverted == 1 && inversionaallowed) {
            Point3D point = new Point3D(0, 0, 0);
            capo.setRotationAxis(point);
            capo.setLayoutY(capo.getLayoutY() - 78);
            inverted = 0;
        }
    }

    ;

    public void setBooleanTrueForLimitedTime(long duration) {
        inversionaallowed = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                visibility();
                inversionaallowed = false;
                timer.cancel(); // Terminate the timer after the specified duration
            }
        }, duration);
    }

    public void visibility() {
        if (inverted == 1) {
            capo.setVisible(false);
        }
    }

    public void stopelongation(MouseEvent event) {
        mousePressed = false;
        stick.setRotate(90);
        stick.setLayoutY(stick.getLayoutY() + stick.getFitHeight() / 2);
        stick.setLayoutX(stick.getLayoutX() + stick.getFitHeight() / 2);
        TranslateTransition capy = new TranslateTransition();
        capy.setNode(capo);
        capy.setDuration(Duration.millis(2000));
        setBooleanTrueForLimitedTime(2000);
        int CollisionCheck;
        if (inverted == 1) {
            System.out.println("lmao ded XD");
            TranslateTransition capyGira = new TranslateTransition(Duration.millis(500), capo);
            capyGira.setByY(1000);
            capyGira.play();

            //change back to main menu
            capyGira.setOnFinished((event2) -> {
                try {
                    switchScenes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            CollisionCheck = 1;
        } else {
            CollisionCheck = 0;
        }
        capy.setByX(stick.getFitHeight());
        capy.play();
        capy.setOnFinished((event1) -> {
            resetStick();
            if (isCapyOut() == -1 || CollisionCheck == 1) {// dying state
                System.out.println("lmao ded XD");
                TranslateTransition capyGira = new TranslateTransition(Duration.millis(500), capo);
                capyGira.setByY(1000);
                capyGira.play();

                //change back to main menu
                capyGira.setOnFinished((event2) -> {
                    try {
                        switchScenes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (isCapyOut() == 3 && inverted == 0) {
                double move = (-pillar3.getLayoutX()) + 15;
                TranslateTransition capoTrans = new TranslateTransition(Duration.millis(1500), capo);
                TranslateTransition pillar1Trans = new TranslateTransition(Duration.millis(1500), pillar1);
                TranslateTransition pillar2Trans = new TranslateTransition(Duration.millis(1500), pillar2);
                TranslateTransition pillar3Trans = new TranslateTransition(Duration.millis(1500), pillar3);
                TranslateTransition cherry1Trans = new TranslateTransition(Duration.millis(1500), cherry1);
                TranslateTransition cherry2Trans = new TranslateTransition(Duration.millis(1500), cherry2);
                capoTrans.setByX(move);
                pillar1Trans.setByX(move);
                pillar2Trans.setByX(move);
                pillar3Trans.setByX(move);
                cherry1Trans.setByX(move);
                cherry2Trans.setByX(move);
                ParallelTransition animate = new ParallelTransition(capoTrans, pillar1Trans, pillar2Trans, pillar3Trans, cherry1Trans, cherry2Trans);
                animate.play();

                animate.setOnFinished((event69) -> {
                    capo.setLayoutX(capo.getLayoutX() + capo.getTranslateX());
                    capo.setTranslateX(0);
                    pillar1.setLayoutX(pillar1.getLayoutX() + pillar1.getTranslateX());
                    pillar1.setTranslateX(0);
                    pillar2.setLayoutX(pillar2.getLayoutX() + pillar2.getTranslateX());
                    pillar2.setTranslateX(0);
                    pillar3.setLayoutX(pillar3.getLayoutX() + pillar3.getTranslateX());
                    pillar3.setTranslateX(0);
                    cherry1.setLayoutX(cherry1.getLayoutX() + cherry1.getTranslateX());
                    cherry1.setTranslateX(0);
                    cherry2.setLayoutX(cherry2.getLayoutX() + cherry2.getTranslateX());
                    cherry2.setTranslateX(0);

                    stick.setLayoutX(capo.getLayoutX() + 47);
                    pillar1.setLayoutX(pillar3.getLayoutX());
                    pillar1.setWidth(pillar3.getWidth());
                    pillar2.setLayoutX(pillar1.getLayoutX() + Randomdistance(pillar2));
                    pillar3.setLayoutX(pillar2.getLayoutX() + Randomdistance(pillar3));
                    pillar3.setWidth(RandomWidth());
                    pillar2.setWidth(RandomWidth());

                    //drawing cherries
                    if (iscolliding() == 0) {
                        cherry1.setLayoutX(pillar1.getLayoutX() + (Randomdistance(pillar2) / 2));
                        cherry2.setLayoutX(pillar2.getLayoutX() + (Randomdistance(pillar3) / 2));
                    } else if (iscolliding() == 1) {
                        cherry2.setLayoutX(pillar2.getLayoutX() + (Randomdistance(pillar3) / 2));
                    } else if (iscolliding() == 2) {
                        cherry1.setLayoutX(pillar1.getLayoutX() + (Randomdistance(pillar2) / 2));
                    }
                    pillarnum = 3;
                });
            }

        });

        int pillarIndex = isCapyOut()-1;
        if (pillarIndex != -1) {
            points += 10; // Adjust points as needed
            updatePointsLabel();
        }
    }

    private void savePointsToFile(int points, String filePath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            // Write the points to the file
            objectOutputStream.writeInt(points);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        savePointsToFile(points, "save.txt");
    }

    private void updatePointsLabel() {
        pointsLabel.setText("Points: " + points);
    }

    private void resetStick() {
        capo.setLayoutX(capo.getLayoutX() + capo.getTranslateX());
        capo.setTranslateX(0);
        stick.setLayoutY(stick.getLayoutY() + stick.getFitHeight() / 2);
        stick.setLayoutX(stick.getLayoutX() + stick.getFitHeight() / 2);
        stick.setFitHeight(0.01);
        stick.setRotate(0);
    }

    private int isCapyOut() {
        // VERY, VERY IMPORTANT, MAKE A FUCKING ARRAYLIST OF THE PILLARSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS

        double coord = capo.getLayoutX() + (capo.getFitWidth() / 2);
        if ((coord > pillar1.getLayoutX()) && (coord < (pillar1.getLayoutX() + pillar1.getWidth()))) {
            pillarnum = 1;
        } else if ((coord > pillar2.getLayoutX()) && (coord < (pillar2.getLayoutX() + pillar2.getWidth()))) {
            pillarnum += 2;
        } else if ((coord > pillar3.getLayoutX()) && (coord < (pillar3.getLayoutX() + pillar3.getWidth()))) {
            pillarnum = 3;
        } else {
            pillarnum = -1;
        }
        return pillarnum;
    }

    private int iscolliding() {
        int collision = 0;//1 for cherry1,2 for cherry 2 ,3 for both
        int collision1 = 0;
        int collision2 = 0;
        double cherry = cherry1.getLayoutX() + (cherry1.getFitWidth() / 2);
        if ((cherry > pillar1.getLayoutX()) && (cherry < (pillar1.getLayoutX() + pillar1.getWidth()))) {
            cherry2.setOpacity(0);
            collision1 = 1;

        } else if ((cherry > pillar2.getLayoutX()) && (cherry < (pillar2.getLayoutX() + pillar2.getWidth()))) {
            cherry2.setOpacity(0);
            collision1 = 1;
        } else if ((cherry > pillar3.getLayoutX()) && (cherry < (pillar3.getLayoutX() + pillar3.getWidth()))) {
            cherry2.setOpacity(0);
            collision1 = 1;
        } else {
            cherry2.setOpacity(100);
            double cherryy = cherry2.getLayoutX() + (cherry2.getFitWidth() / 2);
            if ((cherryy > pillar1.getLayoutX()) && (cherryy < (pillar1.getLayoutX() + pillar1.getWidth()))) {
                cherry2.setOpacity(0);
                collision2 = 1;

            } else if ((cherryy > pillar2.getLayoutX()) && (cherryy < (pillar2.getLayoutX() + pillar2.getWidth()))) {
                cherry2.setOpacity(0);
                collision2 = 1;
            } else if ((cherryy > pillar3.getLayoutX()) && (cherryy < (pillar3.getLayoutX() + pillar3.getWidth()))) {
                cherry2.setOpacity(0);
                collision2 = 1;
            } else {
                cherry2.setOpacity(100);
            }

            if (collision1 == 1 & collision2 == 1) {
                return collision = 3;
            } else if (collision1 == 1) {
                return collision = 1;
            } else if (collision2 == 1) {
                return collision = 2;
            } else {
                return collision = 0;
            }
        }


        return collision;
    }
}