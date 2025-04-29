package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.management.OperatingSystemMXBean;

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
        pane.add(opretLager, 0, 0);

        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 0, 1);
        opretFad.setOnAction(event -> opretFad());


        Button opretBatch = new Button("Opret batch");
        pane.add(opretBatch, 0, 2);
        opretBatch.setOnAction(event -> opretBatch());


    }

    public void opretBatch(){


    }

    public void opretFad(){

    }


}
