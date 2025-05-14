package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.management.OperatingSystemMXBean;
import java.util.Objects;

public class GUI extends Application {
    public void start(Stage stage) {
        stage.setTitle("Sall Whisky Destillery");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(100);
        pane.setVgap(20);

        HBox hBoxButtons = new HBox(40);
        pane.add(hBoxButtons,0,1,3,1);
        hBoxButtons.setAlignment(Pos.TOP_CENTER);


        Button opretLager = new Button("Opret lager");
        hBoxButtons.getChildren().add(opretLager);
        opretLager.setOnAction(event -> opretLager());

        Button opretFad = new Button("Opret fad");
        hBoxButtons.getChildren().add(opretFad);
        opretFad.setOnAction(event -> opretFad());
        GridPane.setHalignment(opretFad, HPos.CENTER);

        Button opretBatch = new Button("Opret batch");
        hBoxButtons.getChildren().add(opretBatch);
        opretBatch.setOnAction(event -> opretBatch());
        GridPane.setHalignment(opretBatch, HPos.RIGHT);

        Button destilatOgLager = new Button("Registrer destillat på fad");
        hBoxButtons.getChildren().add(destilatOgLager);
        destilatOgLager.setOnAction(event -> opretDestillat());

        Button whiskeyserie = new Button("Opret whisky serie");
        hBoxButtons.getChildren().add(whiskeyserie);
        whiskeyserie.setOnAction(event -> opretWhiskyserie());

        Button oversigt = new Button("Oversigt");
        hBoxButtons.getChildren().add(oversigt);
        oversigt.setOnAction(Event -> opretOversigt());

        Button omhæld = new Button("Omhældning");
        hBoxButtons.getChildren().add(omhæld);
        omhæld.setOnAction(Event -> omhældning());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gui/billeder/img.png")));
        ImageView imageView = new ImageView(image);
        pane.add(imageView,0,0);
        GridPane.setColumnSpan(imageView,3);
        imageView.setFitHeight(450);
        imageView.setFitWidth(800);

        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY);
        pane.setBackground(new Background(backgroundFill));


    }

    public void opretBatch(){
        OpretBatchWindow dia = new OpretBatchWindow("Opret nyt batch");
        dia.showAndWait();
    }

    public void opretFad(){
        OpretFadWindow dia = new OpretFadWindow("Opret nyt fad");
        dia.showAndWait();
    }

    public void opretLager(){
        OpretLagerWindow dia = new OpretLagerWindow("Opret nyt lager");
        dia.showAndWait();
    }


    public void opretDestillat(){
        DestilatOgLager dia = new DestilatOgLager("Registere distilat og lager");
        dia.showAndWait();

    }

    public void opretWhiskyserie(){
        OpretWhiskyserieWindow dia = new OpretWhiskyserieWindow("Opret whiskyserie");
        dia.showAndWait();

    }

    public void opretOversigt(){
        OpretOversigtWindow dia = new OpretOversigtWindow("Oversigt");
        dia.showAndWait();
    }

    public void omhældning(){
        OpretOmhældningWindow dia = new OpretOmhældningWindow("Omhældning");
        dia.showAndWait();
    }



}
