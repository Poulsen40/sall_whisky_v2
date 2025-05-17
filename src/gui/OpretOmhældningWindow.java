package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;
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
import java.util.ArrayList;
import java.util.HashSet;

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


    private static ListView<Destillat> lwlDestillat;
    private static ListView<Fad> lwlFrieFad;
    private static LocalDateTime mindsteDato;
    private static ListView<Lager> lwlLager;
    private static Button btnAfbryd;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblFadMedDestillat = new Label("Fade med destillater");


        lwlDestillat = new ListView<>();
        pane.add(lwlDestillat, 0, 0);
        lwlDestillat.setPrefWidth(400);
        lwlDestillat.setPrefHeight(400);
        lwlDestillat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lwlDestillat.getItems().setAll(Controller.destillaterPåLager(Controller.getDestillater()));
        lwlDestillat.setCellFactory(listView -> new ListCell<Destillat>() {
            @Override
            protected void updateItem(Destillat destillat, boolean empty) {
                super.updateItem(destillat, empty);
                if (empty || destillat == null) {
                    setText(null);
                } else {
                    long år = ChronoUnit.YEARS.between(Controller.getDatoForPåfyldning(destillat), LocalDateTime.now());
                    long måneder = ChronoUnit.MONTHS.between(Controller.getDatoForPåfyldning(destillat).plusYears(år), LocalDateTime.now());
                    long dage = ChronoUnit.DAYS.between(Controller.getDatoForPåfyldning(destillat).plusYears(år).plusMonths(måneder), LocalDateTime.now());
                    setText("Alder på destillat: " + år + " år " + måneder + " måneder " + dage + " dage" +
                            "\nDestillat mængde: " + destillat.getSamletMængde() + "\n" + destillat.udskrivFad() + "\nFad nr: " + destillat.getFad().getFadNr() + ", fadstørrelse " + destillat.getFad().getFadStørrelse() +
                            ", fadtype: " + destillat.getFad().getFadtype() + ",\nantal gange brugt:" + destillat.getFad().getAntalGangeBrugt() + ", lager: " + destillat.getFad().getLager());
                }
            }
        });

        VBox destillatPåLager = new VBox();
        destillatPåLager.getChildren().add(lblFadMedDestillat);
        destillatPåLager.getChildren().add(lwlDestillat);
        pane.add(destillatPåLager, 1, 0);


        lwlFrieFad = new ListView<>();
        lwlFrieFad.setPrefWidth(400);
        lwlFrieFad.setPrefHeight(400);
        lwlFrieFad.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lwlFrieFad.getItems().setAll(Controller.frieFadeTilDestillat(Controller.getFade()));
        lwlFrieFad.setCellFactory(listView -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad nr: " + fad.getFadNr() + ", fadtype:" + fad.getFadtype() + ", fadstørrelse: " + fad.getFadStørrelse() + ", antal gange brugt: " + fad.getAntalGangeBrugt() + ", lager: " + fad.getLager() + "\nDestillat på fad: " + fad.getDestillat());
                }
            }
        });

        Label lblFrieFade = new Label("Frie fade");

        VBox frieFde = new VBox();
        frieFde.getChildren().add(lblFrieFade);
        frieFde.getChildren().add(lwlFrieFad);
        pane.add(frieFde, 0, 0);

        Label lblLager = new Label("Lagere");

        lwlLager = new ListView<>();
        lwlLager.setPrefWidth(300);
        lwlLager.setPrefHeight(400);
        lwlLager.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lwlLager.getItems().setAll(Controller.lagerMedPlads(Controller.getlagere()));

        VBox lager = new VBox();
        lager.getChildren().add(lblLager);
        lager.getChildren().add(lwlLager);
        pane.add(lager, 2, 0);

        Button btnOmhæld = new Button("Omhæld");
        btnOmhæld.setOnAction(event -> omhæld());
        pane.add(btnOmhæld, 3, 0);

        btnAfbryd = new Button("Afbryd");
        pane.add(btnAfbryd,0,1);
        btnAfbryd.setOnAction(Event -> close());

    }

    public void omhæld() {
        int samletmængde = 0;
        Fad fad = lwlFrieFad.getSelectionModel().getSelectedItem();
        ArrayList<Destillat> destillater = new ArrayList<>(lwlDestillat.getSelectionModel().getSelectedItems());
        Lager lager = lwlLager.getSelectionModel().getSelectedItem();

        if (fad == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Du skal vælge et fad at omhælde på");
            alert.showAndWait();
            return;
        }
        if (lager == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Du skal vælge et lager at ligge det nye fad på");
            alert.showAndWait();
            return;
        }
        if (destillater.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Du skal vælge minimun et destillater at omhælde");
            alert.showAndWait();
            return;
        }

        for (Destillat destillat1 : destillater) {
            samletmængde += Controller.getSamletMængde(destillat1);
        }
        if (samletmængde > fad.getFadStørrelse()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Mængden af væske er for stor til det valgte fad");
            alert.showAndWait();
            return;

        } else {
            LocalDateTime mindsteDato = destillater.get(0).getDatoForPåfyldning();
            for (Destillat destillat1 : destillater) {
                if (destillat1.getDatoForPåfyldning().isAfter(mindsteDato)) {
                    mindsteDato = destillat1.getDatoForPåfyldning();
                }
            }

            Destillat omhældtDestilat = Controller.createOmhældtDestilat(LocalDateTime.now(), mindsteDato, fad, destillater);
            Controller.setDestillatFad(fad, omhældtDestilat);
            System.out.println("Fad tjek" + omhældtDestilat.getFad());
            Controller.addFadTilLager(fad, lager);
            System.out.println("Fad tjek 2 " + omhældtDestilat.getFad().getLager());

            for (Destillat d : destillater) {
                Fad fad1 = d.getFad();
                Controller.setDestillatFad(fad1,null);
                Controller.fjernDestillat(d);
                Controller.fjernFadFraLager(fad1);
                System.out.println("Fadtjek på lager:" + fad1.getLager());
            }

            lwlDestillat.getItems().setAll(Controller.destillaterPåLager(Controller.getDestillater()));
            lwlFrieFad.getItems().setAll(Controller.frieFadeTilDestillat(Controller.getFade()));
            lwlLager.getItems().setAll(Controller.lagerMedPlads(Controller.getlagere()));
        }

    }
}
