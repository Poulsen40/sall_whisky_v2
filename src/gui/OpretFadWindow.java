package gui;

import application.controller.Controller;
import application.model.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretFadWindow extends Stage {
    public OpretFadWindow(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);


    }

    private static TextField txfFadStørrelse;
    private static TextField txfLeverandør;
    private static String fadtype;
    private static String træsort;
    private static TextField txfantalGangeBrugt;
    private static CheckBox brugtJa = new CheckBox("Ja");
    private static CheckBox brugtNej = new CheckBox("Nej");
    private static int antalGange;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblInformationer = new Label("Fad informationer");
        pane.add(lblInformationer, 0, 0);

        Label lblFadStørrelse = new Label("Fad størrelse");
        pane.add(lblFadStørrelse, 0, 1);

        Label lblLeverandør = new Label("Leverandør");
        pane.add(lblLeverandør, 0, 2);

        Label lblBrugtFør = new Label("Har fadet været brugt før?");
        pane.add(lblBrugtFør, 0, 3);

        Label lblAntalGangeBrugt = new Label("Antal gange brugt");
        pane.add(lblAntalGangeBrugt, 0, 4);

        Label lblTræsort = new Label("Træsort");
        pane.add(lblTræsort, 0, 7);


        txfFadStørrelse = new TextField();
        pane.add(txfFadStørrelse, 1, 1);

        txfLeverandør = new TextField();
        pane.add(txfLeverandør, 1, 2);

        txfantalGangeBrugt = new TextField();
        pane.add(txfantalGangeBrugt, 1, 4);

        //Checkboxes
        brugtJa = new CheckBox("Ja");
        pane.add(brugtJa, 1, 3);
        GridPane.setHalignment(brugtJa, HPos.LEFT);
        brugtJa.setFocusTraversable(false);
        brugtJa.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
            brugtNej.setDisable(true);
            if (!newSelection) {
                brugtNej.setDisable(false);
            }
        });

        brugtNej = new CheckBox("nej");
        pane.add(brugtNej, 1, 3);
        GridPane.setHalignment(brugtNej, HPos.CENTER);
        brugtNej.setFocusTraversable(false);
        brugtNej.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
            brugtJa.setDisable(brugtNej.isSelected());
            txfantalGangeBrugt.setDisable(true);
            if (!newSelection) {
                brugtJa.setDisable(false);
                txfantalGangeBrugt.setDisable(false);
            }
        });


        //Drop down menu
        Label lblFadtype = new Label("Fadtype");
        pane.add(lblFadtype, 0, 6);
        ComboBox<String> comboBoxFadtype = new ComboBox<>();
        comboBoxFadtype.getItems().add("EXBOURBON");
        comboBoxFadtype.getItems().add("EXOLOROSOSHERRY");
        comboBoxFadtype.setValue("Vælg fadtype");
        pane.add(comboBoxFadtype, 1, 6);
        comboBoxFadtype.setOnAction(event -> {
            fadtype = comboBoxFadtype.getValue();
        });


        ComboBox<String> comboBoxTræsort = new ComboBox<>();
        comboBoxTræsort.getItems().add("EGETRÆ");
        comboBoxTræsort.setValue("Vælg træsort");
        pane.add(comboBoxTræsort, 1, 7);
        comboBoxTræsort.setOnAction(event -> {
            træsort = comboBoxTræsort.getValue();
        });

        //Knapper
        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 1, 8);
        opretFad.setOnAction(event -> opretFad());
        GridPane.setHalignment(opretFad, HPos.RIGHT);

        Button afbrudFad = new Button("Afbryd");
        pane.add(afbrudFad, 0, 8);afbrudFad.setOnAction(event ->
        {
            Stage stage = (Stage) afbrudFad.getScene().getWindow();
            stage.close(); // lukker dialogen
        });

    }

    private void ShowAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void opretFad() {

        String fadstørrelse = txfFadStørrelse.getText().trim();
        String leverandør = txfLeverandør.getText().trim();

        if (fadstørrelse.isEmpty() || leverandør.isEmpty()
                || txfantalGangeBrugt == null) {
            ShowAlert("Alle felter skal udfyldles");
            return;
        }
        if (!brugtNej.isSelected() && !brugtJa.isSelected()) {
            ShowAlert("Du skal vælge om fadet har været brugt før");
            return;
        }
        if (!txfFadStørrelse.getText().trim().matches("\\d+(\\.\\d+)?") || Integer.parseInt(fadstørrelse) <= 0) {
            ShowAlert("Fadstørrelse skal være et tal over 0");
            return;
        }

        antalGange = 0;
        int fadStørrelse = Integer.parseInt(txfFadStørrelse.getText().trim());


        if (brugtJa.isSelected()) {
            if (txfantalGangeBrugt.getText() != null && !txfantalGangeBrugt.getText().trim().matches("\\d+(\\.\\d+)?")) {
                ShowAlert("Antal gange brugt skal være et tal");
                return;
            }
            if (Integer.parseInt(txfantalGangeBrugt.getText().trim()) == 0) {
                ShowAlert("Et fad skal minimun have været brugt 1 gang før");
                return;
            }
            if (Integer.parseInt(txfantalGangeBrugt.getText().trim()) > 2) {
                ShowAlert("Et fad kan højest være brugt 2 gange");
                return;
            } else {
                antalGange = Integer.parseInt(txfantalGangeBrugt.getText().trim());
            }
        }

        if (!leverandør.isEmpty() && leverandør.matches("\\d+")) {
            ShowAlert("Leverandør skal skrives med bogstaver");
            return;
        }
        if (fadtype == null || træsort == null) {
            ShowAlert("Fadtype og træsort skal være valgt");
        } else {
            boolean brugt = false;
            if (brugtNej.isSelected()) {
                brugt = false;
            }
            if (brugtJa.isSelected()) {
                brugt = true;
            }
            //Opret fad
            Fad fad = Controller.createFad(fadStørrelse, leverandør, brugt, Fadtype.valueOf(fadtype.toUpperCase()), Træsort.valueOf(træsort.toUpperCase()), antalGange);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Fad er oprettet");
            alert.showAndWait();
            hide();
            System.out.println(fad);
        }
    }
}

