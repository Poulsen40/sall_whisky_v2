package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OpretOmhældningWindow extends Stage {
    public OpretOmhældningWindow(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }


    private static ListView<Fad> lwlFad;
    private static ListView<Fad> lwlFrieFad;



    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblFadMedDestillat = new Label("Fade med destillater");


        lwlFad = new ListView<>();
        pane.add(lwlFad,0,0);
        lwlFad.setPrefWidth(500);
        lwlFad.setPrefHeight(500);
        lwlFad.getItems().setAll();
        lwlFad.getItems().setAll(Controller.fyldteFadePåLager(Controller.getFade()));
        lwlFad.setCellFactory(listView -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad nr: " + fad.getFadNr()  +", fadtype:" + fad.getFadtype() + ", fadstørrelse: "+ fad.getFadStørrelse() + "\nDestillat på fad: " + fad.getDestillat());
                }
            }
        });

        VBox fadeMedDesillat = new VBox();
        fadeMedDesillat.getChildren().add(lblFadMedDestillat);
        fadeMedDesillat.getChildren().add(lwlFad);
        pane.add(fadeMedDesillat,0,0);



        lwlFrieFad = new ListView<>();
        pane.add(lwlFrieFad,1,0);
        lwlFrieFad.setPrefWidth(500);
        lwlFrieFad.setPrefHeight(500);
        lwlFrieFad.getItems().setAll();
        lwlFrieFad.getItems().setAll(Controller.frieFadeTilDestillat(Controller.getFade()));
        lwlFrieFad.setCellFactory(listView -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad nr: " + fad.getFadNr()  +", fadtype:" + fad.getFadtype() + ", fadstørrelse: "+ fad.getFadStørrelse() + "\nDestillat på fad: " + fad.getDestillat());
                }
            }
        });

        Label lblFrieFade = new Label("Frie fade");

        VBox frieFde = new VBox();
        frieFde.getChildren().add(lblFrieFade);
        frieFde.getChildren().add(lwlFrieFad);
        pane.add(frieFde,1,0);

        Button btnOmhæld = new Button("Omhæld");
        pane.add(btnOmhæld,2,0);




    }
}
