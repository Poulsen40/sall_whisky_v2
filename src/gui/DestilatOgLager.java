package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.BatchMængde;
import application.model.Destillat;
import application.model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDateTime;

public class DestilatOgLager extends Stage {
    public DestilatOgLager(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private static ListView<Fad> lwlFade;
    private static ListView<Batch> lwlBatch;
    private static TextField txfValgtFad;
    private static TextField txfBatchInfo;
    private static TextField txfBatchMængdeValgt;
    private static TextField txfFadMængdeTilbage;
    private static TextArea txaInfo;
    private static Button opretLagerPlads;

    private static Fad selectedFad;
    private static Destillat destillat;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        lwlFade = new ListView<>();
        pane.add(lwlFade, 0, 0);
        lwlFade.setPrefWidth(200);
        lwlFade.setPrefHeight(150);
        lwlFade.getItems().setAll(Controller.getFade());
        lwlFade.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        Label lblValgtFad = new Label("Størrelse på det valgte fad");
        pane.add(lblValgtFad, 0, 1);

        txfValgtFad = new TextField();
        pane.add(txfValgtFad, 0, 2);

        Label lblMængdeTilbagePåFad = new Label("Plads tilbage på fad");
        pane.add(lblMængdeTilbagePåFad, 0, 3);

        txfFadMængdeTilbage = new TextField();
        pane.add(txfFadMængdeTilbage, 0, 4);

        lwlFade.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txfValgtFad.setText(String.valueOf(newSelection.getFadStørrelse()) + " liter");
                txfFadMængdeTilbage.setText(String.valueOf(newSelection.getFadStørrelse()));
            } else {
                txfValgtFad.setText("");
                txfFadMængdeTilbage.setText("");
            }
        });


        lwlBatch = new ListView<>();
        pane.add(lwlBatch, 1, 0);
        lwlBatch.setPrefWidth(200);
        lwlBatch.setPrefHeight(150);
        lwlBatch.getItems().setAll(Controller.getBatch());
        lwlBatch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Label lblBatchInfo = new Label("Destileret væske tilbage på valgt batch");
        pane.add(lblBatchInfo, 1, 1);

        txfBatchInfo = new TextField();
        pane.add(txfBatchInfo, 1, 2);


        lwlBatch.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txfBatchInfo.setText(String.valueOf(newSelection.getMængdeVæske()));
            } else {
                txfBatchInfo.setText("");
            }
        });

        Button vælgBatch = new Button("Tap fra batch");
        pane.add(vælgBatch, 1, 5);
        vælgBatch.setOnAction(event -> vælgBatch());

        Button vælgFad = new Button("Vælg fad");
        pane.add(vælgFad, 0, 2);
        vælgFad.setOnAction(event -> vælgFad());
        GridPane.setHalignment(vælgFad, HPos.RIGHT);


        Label lblBaycgMængde = new Label("Ønsket mængde væske tappet fra valgt batch");
        pane.add(lblBaycgMængde, 1, 3);

        txfBatchMængdeValgt = new TextField();
        pane.add(txfBatchMængdeValgt, 1, 4);

        Button opretDestillat = new Button("Opret destillat på fad");
        pane.add(opretDestillat, 0, 5);
        opretDestillat.setOnAction(event -> opretDestillat());

    }

    public void vælgFad() {
        selectedFad = lwlFade.getSelectionModel().getSelectedItem();
        //Opretter destillat
        destillat = Controller.createDestilat(LocalDateTime.now(),selectedFad);

        lwlFade.setDisable(true);
        txfValgtFad.setDisable(true);
    }

    public void vælgBatch() {
        //Får info fra batch (hvilket batch og hvor meget der ønskes tappes fra brugergrænsefladen)
        Batch selectedBatch = lwlBatch.getSelectionModel().getSelectedItem();
        int mængdeFraBatch = Integer.parseInt(txfBatchMængdeValgt.getText().trim());

        //Batchmængden bliver oprettet efter overstående info
        BatchMængde batchMængde = Controller.createBatchMængde(mængdeFraBatch,destillat,selectedBatch);
        //Batchmængden bliver added til det oprettede destillat
        destillat.addBatchMængde(batchMængde);

        //Udregning der ændrer tal på grænsefladen - mængdetilbage på det fad der er valgt
        double mængdeTilbageFørTapningFraBatch = Double.parseDouble(txfFadMængdeTilbage.getText().trim());
        double mængdeValgtTappetFraBatch = batchMængde.getMængde();
        double nyMængde = mængdeTilbageFørTapningFraBatch - mængdeValgtTappetFraBatch;
        //Den nye mængde tilbage på faddet settes
        txfFadMængdeTilbage.setText(String.valueOf(nyMængde));
        txfBatchMængdeValgt.clear();

        //Mængden på batchen reguleres alt efter hvor meget der tappes via en batchmængde og settes
        double nyBatchInfo = selectedBatch.getMængdeVæske() - batchMængde.getMængde();
        selectedBatch.setMængdeVæske(nyBatchInfo);
        txfBatchInfo.setText(String.valueOf(nyBatchInfo));
        lwlBatch.refresh();

        System.out.println(destillat.getBatchMængder());


    }

    public void opretDestillat() {

        //Nyt vindue skl åbnes

    }

}
