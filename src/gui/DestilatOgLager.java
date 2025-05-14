package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.BatchMængde;
import application.model.Destillat;
import application.model.Fad;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private static Button vælgFad;
    private static Button afbryd;
    private boolean isVælgFadButtonPressed; //Skal den være static?

    private static int originalAntalgangeBrugt;
    private static Fad selectedFad;
    private static Destillat destillat;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        lwlFade = new ListView<>();
        lwlFade.setPrefWidth(200);
        lwlFade.setPrefHeight(150);
        lwlFade.getItems().setAll(Controller.frieFadeTilDestillat(Controller.getFade()));
        lwlFade.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        GridPane.setValignment(lwlFade, VPos.BOTTOM);


        Label lblStep1 = new Label("Step 1 : vælg et fad");
        lblStep1.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        VBox vBoxFade = new VBox();
        pane.add(vBoxFade, 0, 0);
        vBoxFade.getChildren().add(lblStep1);
        vBoxFade.getChildren().add(lwlFade);

        Label lblStep2 = new Label("Step 2: vælg en batch");
        lblStep2.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");


        Label lblValgtFad = new Label("Størrelse på det valgte fad");
        pane.add(lblValgtFad, 0, 1);

        txfValgtFad = new TextField();
        pane.add(txfValgtFad, 0, 2);
        txfValgtFad.setEditable(false);

        Label lblMængdeTilbagePåFad = new Label("Plads tilbage på fad");
        pane.add(lblMængdeTilbagePåFad, 0, 3);

        txfFadMængdeTilbage = new TextField();
        pane.add(txfFadMængdeTilbage, 0, 4);
        txfFadMængdeTilbage.setEditable(false);

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
        lwlBatch.setPrefWidth(200);
        lwlBatch.setPrefHeight(150);
        lwlBatch.getItems().setAll(Controller.batchKlarTilDestillat(Controller.getBatches()));
        lwlBatch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        VBox vBoxBatch = new VBox();
        pane.add(vBoxBatch, 1, 0);
        vBoxBatch.getChildren().add(lblStep2);
        vBoxBatch.getChildren().add(lwlBatch);

        Label lblBatchInfo = new Label("Destileret væske tilbage på valgt batch");
        pane.add(lblBatchInfo, 1, 1);

        txfBatchInfo = new TextField();
        pane.add(txfBatchInfo, 1, 2);
        txfBatchInfo.setEditable(false);


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

        vælgFad = new Button("Vælg fad");
        pane.add(vælgFad, 0, 2);
        vælgFad.setOnAction(event -> vælgFad());
        GridPane.setHalignment(vælgFad, HPos.RIGHT);


        Label lblBaycgMængde = new Label("Ønsket mængde væske tappet fra valgt batch");
        pane.add(lblBaycgMængde, 1, 3);

        txfBatchMængdeValgt = new TextField();
        pane.add(txfBatchMængdeValgt, 1, 4);

        Button opretDestillat = new Button("Opret destillat på fad");
        opretDestillat.setOnAction(event -> opretDestillat());
        afbryd = new Button("Afbryd");
        afbryd.setOnAction(Event -> afbryd());
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(afbryd,opretDestillat);
        pane.add(hBox,0,5);

    }

    private void ShowAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setHeight(400);
        alert.setWidth(400);
        alert.showAndWait();
    }

    public void vælgFad() {
        selectedFad = lwlFade.getSelectionModel().getSelectedItem();
        if (selectedFad == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Du skal trykke på det fad du vil vælge");
            alert.showAndWait();
        } else {
            //Opretter destillat
            destillat = Controller.createDestilat(LocalDateTime.now(), selectedFad);

            lwlFade.setDisable(true);
            txfValgtFad.setDisable(true);
            vælgFad.setDisable(true);
            vælgFad.setFocusTraversable(false);

            isVælgFadButtonPressed = true;

        }
    }

    public void vælgBatch() {

        if (selectedFad == null) {
            ShowAlert("Husk at udføre step 1 først");
        }
        //Får info fra batch (hvilket batch og hvor meget der ønskes tappes fra brugergrænsefladen)
        Batch selectedBatch = lwlBatch.getSelectionModel().getSelectedItem();
        String mængdeValgt = txfBatchMængdeValgt.getText().trim();

        if (selectedBatch == null && mængdeValgt.isEmpty()) {
            ShowAlert("Du skal huske at vælge den batch du vil tappe fra og hvor mange liter du vil tappe");
        }
        if (selectedBatch == null && !mængdeValgt.isEmpty()) {
            ShowAlert(" Du skal huske at vælge den batch du vil tappe fra");
        }
        if (selectedBatch != null && mængdeValgt.isEmpty()) {
            ShowAlert("Du skal huske at skrive hvor mange liter du vil tappe");
        }

        if (selectedBatch != null && !mængdeValgt.isEmpty()) {
            if (!mængdeValgt.matches("\\d+")) {
                ShowAlert("Mængde væske ønsket tappes skal være et tal");
                return;
            }
            double mængdeFraBatch = Double.parseDouble(txfBatchMængdeValgt.getText().trim());
            double væskeTilbagePåfad = Double.parseDouble(txfFadMængdeTilbage.getText().trim());

            if (mængdeFraBatch > Controller.getMængdeVæske(selectedBatch)) {
                ShowAlert("Du kan ikke tappe flere liter end der er på batchet");
            }
            if (mængdeFraBatch > væskeTilbagePåfad) {
                System.out.println(mængdeFraBatch);
                System.out.println(Controller.getFadStørrelse(selectedFad));
                ShowAlert("Du kan ikke tappe flere liter end der er plads til på fadet");
            }
            if (mængdeFraBatch <= Controller.getMængdeVæske(selectedBatch) && mængdeFraBatch <= væskeTilbagePåfad && isVælgFadButtonPressed) {
                //Batchmængden bliver oprettet efter overstående info
                BatchMængde batchMængde = Controller.createBatchMængde(mængdeFraBatch, destillat, selectedBatch);
                //Batchmængden bliver added til det oprettede destillat
                Controller.addBatchMængde(batchMængde,destillat);


                //Udregning der ændrer tal på grænsefladen - mængdetilbage på det fad der er valgt
                double mængdeTilbageFørTapningFraBatch = Double.parseDouble(txfFadMængdeTilbage.getText().trim());
                double mængdeValgtTappetFraBatch = Controller.getMængdeVæske(batchMængde);
                double nyMængde = mængdeTilbageFørTapningFraBatch - mængdeValgtTappetFraBatch;
                //Den nye mængde tilbage på faddet settes
                txfFadMængdeTilbage.setText(String.valueOf(nyMængde));
                txfBatchMængdeValgt.clear();

                //Mængden på batchen reguleres alt efter hvor meget der tappes via en batchmængde og settes
                double nyBatchInfo = Controller.getMængdeVæske(selectedBatch) - Controller.getMængdeVæske(batchMængde);
                Controller.setMængdeVæske(selectedBatch,nyBatchInfo);
                txfBatchInfo.setText(String.valueOf(nyBatchInfo));
                lwlBatch.refresh();

                System.out.println(Controller.getBatchMængder(destillat));
            }

        }


    }

    public void opretDestillat() {
        try {
            if (Controller.getSamletMængde(destillat) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Du kan IKKE oprette en distillat uden og tilføje noget væske");
                alert.showAndWait();
            } else {
                FadPåLagerWindow dia = new FadPåLagerWindow("Registere distilat og lager", destillat, selectedFad);
                dia.showAndWait();

                txfBatchInfo.clear();
                txfBatchMængdeValgt.clear();
                txfFadMængdeTilbage.clear();
                txfValgtFad.clear();
                txfValgtFad.setDisable(false);
                lwlBatch.getSelectionModel().clearSelection();
                lwlFade.getSelectionModel().clearSelection();
                lwlBatch.setDisable(false);
                lwlFade.setDisable(false);
                vælgFad.setDisable(false);
                isVælgFadButtonPressed = false;
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("HUSK og udføre step 1 og step 2");
            alert.showAndWait();
        }
        lwlBatch.refresh();

        if (Controller.getDestillat(selectedFad) != null) {
            lwlFade.getItems().remove(selectedFad);
        }
        for (Batch b : Controller.getBatches()){
            if(Controller.getMængdeVæske(b) <= 0){
                lwlBatch.getItems().remove(b);
            }
        }

    }

    public void afbryd(){
        if(!isVælgFadButtonPressed) {
            close();
        }
        else {
            afbryd.setDisable(true);
            close();

            Fad fad = lwlFade.getSelectionModel().getSelectedItem();
            ArrayList<BatchMængde> batchMængdes = new ArrayList<>(Controller.getBatchMængder(destillat));

            for (BatchMængde b : batchMængdes) {
                Batch bb = Controller.getbatch(b);
                Controller.setMængdeVæske(bb,Controller.getMængdeVæske(bb) + Controller.getMængdeVæske(b));
            }

            Controller.setDestillatFad(fad, null);
            Controller.fjernDestillat(destillat);

            lwlFade.getSelectionModel().clearSelection();

        }
    }

}
