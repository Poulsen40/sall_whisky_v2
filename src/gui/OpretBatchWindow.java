package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Lager;
import application.model.Rygemateriale;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.sound.midi.Soundbank;
import java.awt.font.ImageGraphicAttribute;
import java.time.LocalDate;

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
    private static DatePicker datePicker;
    private static LocalDate dato;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblInformationer = new Label("Batch informationer");
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

        Label lblDato = new Label("Dato");
        pane.add(lblDato, 0, 8);
        datePicker = new DatePicker();
        datePicker.setPromptText("Vælg dato for batch");
        pane.add(datePicker, 1, 8);
        datePicker.setEditable(false); // Forhindrer manuel indtastning
        datePicker.setOnAction(event -> {
            dato = datePicker.getValue();
        });

        Button opretBatch = new Button("Opret batch");
        pane.add(opretBatch, 1, 9);
        opretBatch.setOnAction(event -> opretBatch());
        GridPane.setHalignment(opretBatch, HPos.RIGHT);

        Button afbrudBatch = new Button("Afbryd");
        pane.add(afbrudBatch, 0, 9);
        afbrudBatch.setOnAction(event -> {
            Stage stage = (Stage) afbrudBatch.getScene().getWindow();
            stage.close(); // lukker dialogen
        });

    }

    private void ShowAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void opretBatch() {
        String mængdeVæske = txfMængdeVæske.getText().trim();
        String alkoholPct = txfAlkoholPct.getText().trim();
        String kommentar = txfKommentar.getText().trim();
        String kornSort = txfKornsort.getText().trim();
        String mark = txfMark.getText().trim();
        String maltBatch = txfMaltBatch.getText().trim();
        boolean isValid = true; //Bruges til at styre om en batch kan oprettes baseret på brugerens input

        if (maltBatch.isEmpty() || mark.isEmpty() || kornSort.isEmpty() || mængdeVæske.isEmpty() || alkoholPct.isEmpty()) {
            isValid = false;
            ShowAlert("Du mangler at udfylde noget information");
        }
        if (dato == null) {
            isValid = false;
            ShowAlert("Du skal vælge en dato");
        }
        if (!maltBatch.matches("[a-zA-ZæøåÆØÅ ]+")) {
            isValid = false;
            ShowAlert("Maltbatch må kun indeholde bogstaver");
        }
        if (!kornSort.matches("[a-zA-ZæøåÆØÅ ]+")) {
            isValid = false;
            ShowAlert("Kornsort må kun indeholde bogstaver");
        }
        if (!mark.matches("[a-zA-ZæøåÆØÅ ]+")) {
            isValid = false;
            ShowAlert("Mark må kun indeholde bogstaver");
        }
        if (!kommentar.matches("[a-zA-ZæøåÆØÅ ]*")) {
            isValid = false;
            ShowAlert("Kommentar må kun indeholde bogstaver");
        }
        if (!alkoholPct.matches("\\d+")) {
            isValid = false;
            ShowAlert("Alkoholprocent skal være et tal");
        }
        if (!mængdeVæske.matches("\\d+")) {
            isValid = false;
            ShowAlert("Mængdevæske skal være et tal");
        }
        if (isValid) {//Hvis alt brugerens input er gyldigt, fortsættes oprettelsen af batchen
            try {
                int mængdeVæske1 = Integer.parseInt(txfMængdeVæske.getText().trim());
                int alkoholPct1 = Integer.parseInt(txfAlkoholPct.getText().trim());
                if (rygemateriale == null) {
                    isValid = false;
                    ShowAlert("Du mangler at vælge et rygematerialle");

                } else if (mængdeVæske1 == 0 || alkoholPct1 == 0 || alkoholPct1 > 95) { //Hvis mængde og procent er 0
                    isValid = false; //markedes som false
                    if (mængdeVæske1 == 0) {
                        ShowAlert("Mængdevæske skal minimum være 1");
                    }
                    if (alkoholPct1 == 0) {
                        ShowAlert("Alkoholprocent må ikke være 0");
                    }
                    if (alkoholPct1 > 95) {
                        ShowAlert("Alkoholprocent må ikke være over 95");
                    }
                }
                if (isValid) { //Tjekker om brugerens input stadig er gyldigt
                    //Opret batch
                    Batch b1 = Controller.createBatch(maltBatch, kornSort, mark, mængdeVæske1, alkoholPct1, kommentar, Rygemateriale.valueOf(rygemateriale.toUpperCase()), dato);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Dit batch er nu oprettet");
                    alert.showAndWait();
                    hide();
                }
            } catch (NumberFormatException e) {
                if (!mængdeVæske.isEmpty() || !alkoholPct.isEmpty()) {
                    ShowAlert("Ingen bogstaver på feltet væskemængde eller alkoholprocent");
                }
            }
        }
    }

}






