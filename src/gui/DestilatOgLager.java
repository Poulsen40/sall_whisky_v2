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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.text.BreakIterator;
import java.time.LocalDateTime;
import java.util.Locale;

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
        lwlFade.getItems().setAll(Controller.getFade());
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
        lwlBatch.setPrefWidth(200);
        lwlBatch.setPrefHeight(150);
        lwlBatch.getItems().setAll(Controller.getBatch());
        lwlBatch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        VBox vBoxBatch = new VBox();
        pane.add(vBoxBatch, 1, 0);
        vBoxBatch.getChildren().add(lblStep2);
        vBoxBatch.getChildren().add(lwlBatch);

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

        vælgFad = new Button("Vælg fad");
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
        Controller.setValgtFad(selectedFad);
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

        }
    }

    public void vælgBatch() {

        if (selectedFad == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(400);
            alert.setWidth(400);
            alert.setContentText("Husk at udføre step 1 først");
            alert.showAndWait();

        }
        //Får info fra batch (hvilket batch og hvor meget der ønskes tappes fra brugergrænsefladen)
        Batch selectedBatch = lwlBatch.getSelectionModel().getSelectedItem();
        String mængdeValgt = txfBatchMængdeValgt.getText().trim();

        if (selectedBatch == null && mængdeValgt.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(400);
            alert.setWidth(400);
            alert.setContentText("Du skal huske at vælge den batch du vil tappe fra og hvor mange liter du vil tappe");
            alert.showAndWait();
        }
        if (selectedBatch == null && !mængdeValgt.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(400);
            alert.setWidth(400);
            alert.setContentText(" Du skal huske at vælge den batch du vil tappe fra");
            alert.showAndWait();
        }
        if (selectedBatch != null && mængdeValgt.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(400);
            alert.setWidth(400);
            alert.setContentText("Du skal huske at skrive hvor mange liter du vil tappe");
            alert.showAndWait();
        }

        if (selectedBatch != null && !mængdeValgt.isEmpty()) {
            if (!mængdeValgt.matches("\\d+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(400);
                alert.setWidth(400);
                alert.setContentText("Mængde væske ønsket tappes skal være et tal");
                alert.showAndWait();
                return;
            }
            double mængdeFraBatch = Double.parseDouble(txfBatchMængdeValgt.getText().trim());
            double væskeTilbagePåfad = Double.parseDouble(txfFadMængdeTilbage.getText().trim());

            if (mængdeFraBatch > selectedBatch.getMængdeVæske()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(400);
                alert.setWidth(400);
                alert.setContentText("Du kan ikke tappe flere liter end der er på batchet");
                alert.showAndWait();
            }
            if (mængdeFraBatch > væskeTilbagePåfad) {
                System.out.println(mængdeFraBatch);
                System.out.println(selectedFad.getFadStørrelse());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeight(400);
                alert.setWidth(400);
                alert.setContentText("Du kan ikke tappe flere liter end der er plads til på fadet");
                alert.showAndWait();
            }
            if (mængdeFraBatch <= selectedBatch.getMængdeVæske() && mængdeFraBatch <= væskeTilbagePåfad) {
                //Batchmængden bliver oprettet efter overstående info
                BatchMængde batchMængde = Controller.createBatchMængde(mængdeFraBatch, destillat, selectedBatch);
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

        }


    }

    public void opretDestillat() {
        try {
            if (destillat.getSamletMængde() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Du kan IKKE oprette en distillat uden og tilføje noget væske");
                alert.showAndWait();
            } else {
                FadPåLagerWindow dia = new FadPåLagerWindow("Registere distilat og lager", destillat);
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
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("HUSK og udføre step 1 og step 2");
            alert.showAndWait();
        }

    }

}
