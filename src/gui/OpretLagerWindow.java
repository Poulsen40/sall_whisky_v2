package gui;

import application.controller.Controller;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

public class OpretLagerWindow extends Stage {

    public OpretLagerWindow(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private static TextField Txfrækker;
    private static TextField Txfhylder;
    private static TextField TxfPladser;
    private static TextField Txfnavn;
    private static Button btnOpretLager, btnCancel;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblrækker = new Label("Antal rækker");
        pane.add(lblrækker, 0, 0);

        Label lblhylder = new Label("Antal hylder i hver række");
        pane.add(lblhylder, 0, 2);

        Label lblpladser = new Label("Antal pladser på hver hylde");
        pane.add(lblpladser, 0, 4);

        Label lblnavn = new Label("Navnet");
        pane.add(lblnavn, 0, 6);

        Txfrækker = new TextField();
        pane.add(Txfrækker, 3, 0);

        Txfhylder = new TextField();
        pane.add(Txfhylder, 3, 2);

        TxfPladser = new TextField();
        pane.add(TxfPladser, 3, 4);

        Txfnavn = new TextField();
        pane.add(Txfnavn, 3, 6);

        btnOpretLager = new Button("Opret lager");
        pane.add(btnOpretLager, 3, 8);
        GridPane.setHalignment(btnOpretLager, HPos.RIGHT);
        btnOpretLager.setOnAction(event -> opretLagermetode());

        btnCancel = new Button("Afbrud");
        pane.add(btnCancel, 0, 8);

        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close(); // lukker dialogen
        });
    }

    public void opretLagermetode() {

        // Her tjekker jeg om alle felter er udfyldt, hvis de er forsætter jeg
        if (Txfnavn.getText().trim().isEmpty() || Txfrækker.getText().isEmpty() || TxfPladser.getText().isEmpty() || Txfnavn.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Husk og udfylde ALLE felter");
            alert.showAndWait();
        } else {

            try {
                int rækker = Integer.parseInt(Txfrækker.getText().trim());
                int hylder = Integer.parseInt(Txfhylder.getText().trim());
                int pladser = Integer.parseInt(TxfPladser.getText().trim());
                String navn = Txfnavn.getText().trim();

                // Her tjekker jeg
                if (rækker <= 0) {
                    Txfrækker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                } else if (hylder <= 0) {
                    Txfhylder.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                } else if (pladser <= 0) {
                    TxfPladser.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                }

                if (rækker > 0 && hylder > 0 && pladser > 0 && !Txfnavn.getText().isEmpty()) {
                    Lager lager1 = Controller.createLager(rækker, hylder, pladser, navn);
                    Txfrækker.setText("");
                    Txfhylder.setText("");
                    TxfPladser.setText("");
                    Txfnavn.setText("");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Lageret er nu oprettet");
                    alert.showAndWait();
                    System.out.println(lager1);
                    Txfrækker.setStyle(""); // nulstiller til standard
                    Txfhylder.setStyle(""); // nulstiller til standard
                    TxfPladser.setStyle(""); // nulstiller til standard
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ingen bogstaver er tilladt udover i navn textfeltet");
                alert.showAndWait();
            }
        }
    }
}

