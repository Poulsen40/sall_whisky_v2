package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
        pane.setVgap(100);

        Button opretLager = new Button("Opret lager");
        pane.add(opretLager, 0, 1);
        opretLager.setOnAction(event -> opretLager());

        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 1, 1);
        opretFad.setOnAction(event -> opretFad());
        GridPane.setHalignment(opretFad, HPos.CENTER);

        Button opretBatch = new Button("Opret batch");
        pane.add(opretBatch, 2, 1);
        opretBatch.setOnAction(event -> opretBatch());
        GridPane.setHalignment(opretBatch, HPos.RIGHT);

        Button destilatOgLager = new Button("Registrer destillat på fad");
        pane.add(destilatOgLager, 3, 1);
        destilatOgLager.setOnAction(event -> opretDestillat());


        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gui/billeder/img.png")));
        ImageView imageView = new ImageView(image);
        pane.add(imageView,0,0);
        GridPane.setColumnSpan(imageView,3);
        imageView.setFitHeight(300);
        imageView.setFitWidth(500);

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



}
