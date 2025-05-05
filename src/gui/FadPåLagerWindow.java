package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import storage.Storage;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class FadPåLagerWindow extends Stage {

    private Destillat destillat;

    public FadPåLagerWindow(String title, Destillat destillat) {

        this.destillat = destillat;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.setOnCloseRequest(event -> {
            event.consume(); // Forhindrer vinduet i at lukke
        });
    }


    private static ListView<Lager> lwlager;
//    private static ListView<Fad> lwFad;
    private static TextArea txadestillat;
    private static Button btnCancel;
    private static Button btnAdd;





    public void initContent(GridPane pane) {

        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label info = new Label("Info");

        lwlager = new ListView<>();
        lwlager.setPrefWidth(200);
        lwlager.setPrefHeight(150);
        lwlager.getItems().setAll(Controller.getlagere());
        lwlager.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        VBox vBox = new VBox();
        pane.add(vBox, 0, 0);
        vBox.getChildren().add(info);
        vBox.getChildren().add(lwlager);

        txadestillat = new TextArea();
        pane.add(txadestillat, 0, 1);
        txadestillat.setText(destillat.toString());
        txadestillat.setEditable(false);


        btnCancel = new Button("Afbryd");
        pane.add(btnCancel, 0, 3);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close(); // lukker dialogen
            genstartDestilatPåFad();
        });

//        lwFad.getSelectionModel().selectedItemProperty().addListener((obsFad, oldFadSelection, newFadSelection) -> {
//
//        });
        lwlager.getSelectionModel().selectedItemProperty().addListener((obsLager, oldLagerSelection, newLagerSelection) -> {

        });

        btnAdd = new Button("Tilføj fad til lager");
        pane.add(btnAdd, 0, 3);
        GridPane.setHalignment(btnAdd, HPos.RIGHT);
        btnAdd.setOnAction(event ->{
            Lager selectedLager = lwlager.getSelectionModel().getSelectedItem();

            if (selectedLager != null){

                if (Controller.getValgtFad() != null){
                    String placering = Controller.addFadTilLager(Controller.getValgtFad(), selectedLager);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Fad tilføjet");
                    alert.setHeaderText("Fadet er nu placeret på lageret!");
                    alert.setContentText(placering);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingen fad tilgængelige");
                    alert.showAndWait();
                    alert.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Du skal vælge et lager");
                alert.showAndWait();
                alert.close();
            }



        });
    }

    public void genstartDestilatPåFad() {
        ArrayList<BatchMængde> batchMængdes = new ArrayList<>(destillat.getBatchMængder());
        for (BatchMængde b : batchMængdes) {
            Batch bb = b.getBatch();
            bb.setMængdeVæske(bb.getMængdeVæske() + b.getMængde());

        }
        Controller.fjernDestillat(destillat);

    }
}

