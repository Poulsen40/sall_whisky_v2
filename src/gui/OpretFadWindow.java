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

import java.awt.font.ImageGraphicAttribute;
import java.util.Locale;

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
    private static TextField txfErBrugt;
    private static String fadtype;
    private static String træsort;
    private static TextField txfantalGangeBrugt;
    private static TextField txfLiterPåfyldt;
    private static CheckBox brugtJa = new CheckBox("Ja");
    private static CheckBox brugtNej = new CheckBox("Nej");


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


        txfFadStørrelse = new TextField();
        pane.add(txfFadStørrelse, 1, 1);

        txfLeverandør = new TextField();
        pane.add(txfLeverandør, 1, 2);

        txfantalGangeBrugt = new TextField();
        pane.add(txfantalGangeBrugt, 1, 4);


        brugtJa = new CheckBox("Ja");
        pane.add(brugtJa, 1, 3);
        GridPane.setHalignment(brugtJa, HPos.LEFT);
        brugtJa.setFocusTraversable(false);

        brugtNej = new CheckBox("nej");
        pane.add(brugtNej, 1, 3);
        GridPane.setHalignment(brugtNej, HPos.CENTER);
        brugtNej.setFocusTraversable(false);

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

        Label lblTræsort = new Label("Træsort");
        pane.add(lblTræsort, 0, 7);
        ComboBox<String> comboBoxTræsort = new ComboBox<>();
        comboBoxTræsort.getItems().add("EGETRÆ");
        comboBoxTræsort.setValue("Vælg træsort");
        pane.add(comboBoxTræsort, 1, 7);
        comboBoxTræsort.setOnAction(event -> {
            træsort = comboBoxTræsort.getValue();
        });

        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 1, 8);
        opretFad.setOnAction(event -> opretFad());
        GridPane.setHalignment(opretFad,HPos.RIGHT);


        Button afbrudFad = new Button("Afbrud");
        pane.add(afbrudFad, 0, 8);

        afbrudFad.setOnAction(event -> {
            Stage stage = (Stage) afbrudFad.getScene().getWindow();
            stage.close(); // lukker dialogen
        });

    }

    public void opretFad() {
        try {
            if (txfFadStørrelse.getText().trim().isEmpty() || txfLeverandør.getText().trim().isEmpty()
                    || txfantalGangeBrugt.getText().trim().isEmpty() || træsort == null){
                throw new IllegalArgumentException("Alle felter skal udfyldes");
            }
            int fadStørrelse = Integer.parseInt(txfFadStørrelse.getText().trim());
            String leverandør = txfLeverandør.getText().trim();
            int antalGangeBrugt = Integer.parseInt(txfantalGangeBrugt.getText().trim());

            if (brugtNej.isSelected() && antalGangeBrugt != 0 || brugtJa.isSelected() && antalGangeBrugt < 1){
                throw new IllegalArgumentException("Antal gange brugt skal være 0, hvis fadet ikke har været brugt. Hvis det har, skal det være mindst 1.");
            }

            if (antalGangeBrugt > 3){
                throw new IllegalArgumentException("Et fad kan højst bruges 3 gange");
            }

            System.out.println("DEBUG: Fadtype = " + fadtype + ", Træsort = " + træsort);


            if (fadtype.equals("Vælg fadtype") || træsort.equals("Vælg træsort")) {
                throw new IllegalArgumentException("Fadtype og træsort skal være valgt.");
            }

            boolean brugt = false;
            if (brugtNej.isSelected()) {
                brugt = false;
            }
            if (brugtJa.isSelected()) {
                brugt = true;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Batch er oprettet");
            alert.showAndWait();

            //Opret fad
            Fad f1 = Controller.createFad(fadStørrelse, leverandør, brugt, Fadtype.valueOf(fadtype.toUpperCase()), Træsort.valueOf(træsort.toUpperCase()), antalGangeBrugt);
            System.out.println(f1);
            hide();

        } catch (IllegalArgumentException e) { //kan laves flere af med nye catches
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

