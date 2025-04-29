package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Rygemateriale;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.sound.midi.Soundbank;

public class OpretBatchWindow extends Stage {
    public OpretBatchWindow(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);


    }

    private static TextField txfMaltBatch;
    private static TextField txfMængdeVæske;
    private static TextField txfAlkoholPct;
    private static TextField txfKommentar;
    private static TextField txfMark;
    private static TextField txfKornsort;
    private static String rygemateriale;



    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblInformationer = new Label("Fad informationer");
        pane.add(lblInformationer, 0, 0);

        Label lblName = new Label("MaltBatch");
        pane.add(lblName, 0, 1);

        Label lblMængdeVæske = new Label("MængdeVæske");
        pane.add(lblMængdeVæske, 0, 2);

        Label lblAlkoholpct = new Label("Alkohol procent");
        pane.add(lblAlkoholpct, 0, 3);

        Label lblKommentar = new Label("Eventuel kommentar");
        pane.add(lblKommentar, 0, 4);

        Label lblMark = new Label("Mark");
        pane.add(lblMark, 0, 5);

        Label lblKornSort = new Label("Kornsort");
        pane.add(lblKornSort, 0, 6);

        txfMaltBatch = new TextField();
        pane.add(txfMaltBatch, 1, 1);

        txfMængdeVæske = new TextField();
        pane.add(txfMængdeVæske, 1, 2);

        txfAlkoholPct = new TextField();
        pane.add(txfAlkoholPct, 1, 3);

        txfKommentar = new TextField();
        pane.add(txfKommentar, 1, 4);

        txfMark = new TextField();
        pane.add(txfMark, 1, 5);

        txfKornsort = new TextField();
        pane.add(txfKornsort, 1, 6);

        //Drop down menu
        Label lblRygemateriale = new Label("Ryge materiale");
        pane.add(lblRygemateriale, 0, 7);
        ComboBox<String> comboBoxRygeMaterialle = new ComboBox<>();
        comboBoxRygeMaterialle.getItems().add("MULD");
        comboBoxRygeMaterialle.getItems().add("TØRV");
        comboBoxRygeMaterialle.getItems().add("GLØD");
        comboBoxRygeMaterialle.setValue("Vælg ryge materialle");
        pane.add(comboBoxRygeMaterialle, 1, 7);
        comboBoxRygeMaterialle.setOnAction(event -> {
            rygemateriale = comboBoxRygeMaterialle.getValue();
        });

        Button opretBatch = new Button("Opret batch");
        pane.add(opretBatch, 0, 8);
        opretBatch.setOnAction(event -> opretBatch());

    }

    public void opretBatch() {
        String maltBatch =  txfMaltBatch.getText().trim();
        int mængdeVæske = Integer.parseInt(txfMængdeVæske.getText().trim());
        int alkoholPct = Integer.parseInt(txfAlkoholPct.getText().trim());
        String kommentar = txfKommentar.getText().trim();
        String kornSort = txfKornsort.getText().trim();
        String mark = txfMark.getText().trim();

        //Opret batch
        Batch b1 = Controller.createBatch(maltBatch,kornSort,mark,mængdeVæske,alkoholPct,kommentar,Rygemateriale.valueOf(rygemateriale.toUpperCase()));
        System.out.println(b1);
        hide();

    }

}
