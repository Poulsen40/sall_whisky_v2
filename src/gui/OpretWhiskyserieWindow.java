package gui;

import application.controller.Controller;
import application.model.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.jfr.TransitionFrom;

import java.nio.channels.Pipe;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class OpretWhiskyserieWindow extends Stage {
    public OpretWhiskyserieWindow(String title) {


        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private static TextField txfNavn;
    private static TextField txfTapMængde;
    private static TextField txfVand;
    private static Whiskyserie whiskyserie;
    private static TextArea txaInfo;
    private static TextArea txaDestilatInfo;
    private static DatePicker datePicker;
    private static Button btnOpretWhiskySerieObjekt;
    private static Button btnTap;
    private static double mængdeVand;

    private static Destillat selectedDestillat;

    private static String serieNavn;
    private static double selectedButtonAlder;

    private static DestillatMængde destillatMængde;

    private static LocalDate dato;
    private static ListView<Destillat> lwlDestillat;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblStep1 = new Label("Step 1 : vælg et navn og dato for whiskyserien");
        lblStep1.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label lblNavn = new Label("Navn på whiskyserie");

        txfNavn = new TextField();

        datePicker = new DatePicker();
        datePicker.setPromptText("Vælg datoen for oprettelsen af whiskyserien");
        datePicker.setEditable(false);  // Forhindrer manuel indtastning
        datePicker.setOnAction(event -> {
            dato = datePicker.getValue();
        });


        btnOpretWhiskySerieObjekt = new Button("Opret whisky serie");
        btnOpretWhiskySerieObjekt.setOnAction(event -> opretWhiskySerieObjekt());

        VBox step1 = new VBox();
        step1.setSpacing(15);
        step1.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-padding: 10");
        step1.getChildren().add(lblStep1);
        step1.getChildren().add(lblNavn);
        step1.getChildren().add(txfNavn);
        step1.getChildren().add(datePicker);
        step1.getChildren().add(btnOpretWhiskySerieObjekt);
        pane.add(step1, 0, 0);

        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("3 år");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("4 år");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("5+ år");
        rb3.setToggleGroup(group);
        HBox hboxButtons = new HBox(10, rb1, rb2, rb3);

        lwlDestillat = new ListView<>();
        lwlDestillat.setPrefWidth(200);
        lwlDestillat.setPrefHeight(150);
        lwlDestillat.getItems().setAll();
        lwlDestillat.getItems().setAll(Controller.destilatWhiskySerieUdenFilter(Controller.getDestillater()));

        group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                RadioButton selectedButton = (RadioButton) group.getSelectedToggle();
                String færdigString = selectedButton.getText().substring(0, 1);
                selectedButtonAlder = Double.parseDouble(færdigString);
                lwlDestillat.getItems().setAll(Controller.destilatWhiskySerieFilter(Controller.getDestillater(), selectedButtonAlder));
            }
        });


        //Bruges til at lave destillater i listview mere overskuelige at læse
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
                    setText("Dato for påfyldning: " + Controller.getDatoForPåfyldning(destillat).toLocalDate() + "\nAlder på destillat: " + år + " år " + måneder + " måneder " + dage + " dage");
                }
            }
        });
        lwlDestillat.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        GridPane.setValignment(lwlDestillat, VPos.BOTTOM);

        txaDestilatInfo = new TextArea();
        txaDestilatInfo.setPrefHeight(100);
        txaDestilatInfo.setPrefWidth(100);

        txaDestilatInfo.setEditable(false);

        lwlDestillat.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                txaDestilatInfo.setText("");
            } else {
                txaDestilatInfo.setText(Controller.toStringFadOgDestillat(lwlDestillat.getSelectionModel().getSelectedItem()));


            }
        });


        Label lblStep2 = new Label("Step 2 : vælg det fad du vil tappe destilat fra og mængden. Gentag hvis ønskes");
        lblStep2.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label lblTap = new Label("Fade med destillat klar til tapning");


        VBox step2 = new VBox();
        step2.setSpacing(15);
        step2.getChildren().add(lblStep2);
        step2.getChildren().add(lblTap);
        step2.getChildren().add(lwlDestillat);
        step2.getChildren().add(txaDestilatInfo);


        txfTapMængde = new TextField();
        txfTapMængde.setPromptText("Måles i liter (L)");

        Label lblTapMængde = new Label("Mængden du vil tappe");

        Label lblFilter = new Label("Filtrer efter alder på fad");


        btnTap = new Button("Tap");
        btnTap.setOnAction(event -> {
            tapMængdeFraDestilat();
        });

        Region tom = new Region();
        tom.setMinHeight(20);
        Region tom1 = new Region();


        VBox step2_2 = new VBox();
        step2_2.setSpacing(15);
        step2_2.getChildren().add(tom1);
        step2_2.getChildren().add(lblFilter);
        step2_2.getChildren().add(hboxButtons);
        step2_2.getChildren().add(tom);
        step2_2.getChildren().add(lblTapMængde);
        step2_2.getChildren().add(txfTapMængde);
        step2_2.getChildren().add(btnTap);
        step2_2.setAlignment(Pos.CENTER);


        HBox hBoxStep2 = new HBox();
        hBoxStep2.setSpacing(15);
        hBoxStep2.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-padding: 10");
        hBoxStep2.getChildren().addAll(step2, step2_2);
        pane.add(hBoxStep2, 1, 0);

        Label lblStep3 = new Label("Step 3 : fortynd eventuel din whiskey serie. \nTap på flasker når endelig produkt er opnået");
        lblStep3.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label lblVand = new Label("Fortynd eventuelt med vand");

        txfVand = new TextField();
        txfVand.setPromptText("Måles i liter (L)");

        Button btnFortynd = new Button("Fortynd");
        GridPane.setHalignment(btnFortynd, HPos.RIGHT);
        btnFortynd.setOnAction(event -> fortynd());

        VBox step3 = new VBox();
        step3.setSpacing(15);
        step3.getChildren().addAll(lblStep3, lblVand, txfVand, btnFortynd);

        txaInfo = new TextArea("Whisky serie information: \n");
        txaInfo.setPrefWidth(450);
        txaInfo.setPrefHeight(200);
        txaInfo.setEditable(false);

        Button btnTapPåFlaske = new Button("Tap flasker");
        btnTapPåFlaske.setOnAction(event -> tapPåFlaske());


        HBox step3_2 = new HBox();
        step3_2.setSpacing(48);
        step3_2.getChildren().addAll(txaInfo, btnTapPåFlaske);
        step3_2.setAlignment(Pos.CENTER);

        HBox hBoxStep3_3 = new HBox();
        hBoxStep3_3.setSpacing(40);
        hBoxStep3_3.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-padding: 10");
        hBoxStep3_3.getChildren().addAll(step3, step3_2);
        pane.add(hBoxStep3_3, 0, 1);
        GridPane.setColumnSpan(hBoxStep3_3, 2);

        Button btnAfbryd = new Button("Afbryd");
        btnAfbryd.setOnAction(event -> afbryd());

        HBox hboxAfbryd = new HBox();
        hboxAfbryd.getChildren().add(btnAfbryd);
        pane.add(hboxAfbryd, 0, 2);


    }

    private void ShowAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void opretWhiskySerieObjekt() {
        serieNavn = txfNavn.getText().trim();

        if (serieNavn.isEmpty() || dato == null) {
            ShowAlert("Du skal udfylde både navn og dato for whiskyserien");
        } else {
            whiskyserie = Controller.createWhiskyserie(serieNavn, dato);
            txfNavn.setDisable(true);
            datePicker.setDisable(true);
            btnOpretWhiskySerieObjekt.setDisable(true);
            System.out.println(whiskyserie);
        }

    }

    public void tapMængdeFraDestilat() {
        selectedDestillat = lwlDestillat.getSelectionModel().getSelectedItem();
        if (whiskyserie == null) {
            ShowAlert("Du skal udføre step 1 først");
        } else if (selectedDestillat == null) {
            ShowAlert("Du skal vælge et destillat før du kan tappe");
        } else if (txfTapMængde.getText().trim().isEmpty()) {
            ShowAlert("Du skal indtaste mængden af væske du vil tappe");
        } else if (!txfTapMængde.getText().trim().matches("\\d+(\\.\\d+)?")) {
            ShowAlert("Tapmængden skal være et tal");
        } else {
            if (whiskyserie != null && selectedDestillat != null && !txfTapMængde.getText().isEmpty() && txfTapMængde.getText().trim().matches("\\d+(\\.\\d+)?")) {
                double mængde = Double.parseDouble(txfTapMængde.getText().trim());

                if (mængde < 1) {
                    ShowAlert("Tapmængden skal være mininum 1");
                } else if (mængde > selectedDestillat.getSamletMængde()) {
                    ShowAlert("Du kan ikke tappe mere væske end der er tilbage på destillatet");
                } else {
                    destillatMængde = Controller.createDestillatMængde(mængde, whiskyserie, selectedDestillat);
                    //Controller.addDestillatMængde(destillatMængde, whiskyserie);
                    txaDestilatInfo.setText(Controller.toStringFadOgDestillat(selectedDestillat));

                    if (Controller.getSamletMængde(selectedDestillat) == 0) {
                        lwlDestillat.getItems().remove(selectedDestillat);
                    }

                    txfTapMængde.clear();

                    setInfoBox();

                }
            }
        }
    }

    public void fortynd() {
        if (whiskyserie == null) {
            ShowAlert("Du skal udføre step 1 først");
        }
        else if(whiskyserie.getDestillatMængder().isEmpty()) {
            ShowAlert("Du skal vælge et fad, indtaste en mængde og trykke 'Tap', før du kan fortynde.");
        }
        else if(!txfVand.getText().trim().isEmpty() && whiskyserie != null) {
            mængdeVand += Double.parseDouble(txfVand.getText().trim());
            txfVand.clear();
            setInfoBox();
        }
    }

    public void tapPåFlaske() {
        if (whiskyserie == null) {
            ShowAlert("Du skal udføre step 1 først");
        } else if (whiskyserie.getDestillatMængder().isEmpty()) {
            ShowAlert("Du skal udføre step 2 først");
        }else{
            //Beregner samlet mængde whisky med vand inkluderet
            double samletMængdeWhisky = Controller.samletMængdeWhiskySerie(whiskyserie, mængdeVand);

            //beregning af antalflasker der skal tappes
            int antalFlasker = (int) Controller.antalForventetFlakser(whiskyserie, samletMængdeWhisky);

            double mængdePrFlaske = 0.7;

            //Laver antal flasker
            for (int i = 0; i < antalFlasker; i++) {
                Controller.createWhiskyprodukt(whiskyserie, 70);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Whisky tappet på " + antalFlasker + " flasker af " + mængdePrFlaske + " cl pr flaske");
            alert.showAndWait();
            close();

            if (Controller.getSamletMængde(selectedDestillat) == 0) {
                Controller.fjernFadFraLager(selectedDestillat.getFad());
                Controller.fjernDestillat(selectedDestillat);
                lwlDestillat.getItems().remove(selectedDestillat);

            }

            Controller.setWhiskyInfo(whiskyserie.getDestillatMængder(), whiskyserie, mængdeVand, antalFlasker);

            whiskyserie = null;
            mængdeVand = 0;


        }
    }


    public void setInfoBox() {
        txaInfo.setText(Controller.toStringInfoBoxWhiskyserie(whiskyserie.getDestillatMængder(), whiskyserie, mængdeVand));
        txaInfo.appendText("\nMængde vand tilføjer whiskyserien " + mængdeVand);
        txaInfo.appendText("\nAlkohol procent: " + Controller.beregnAlkoholProcentPåWhiskyserie(whiskyserie.getDestillatMængder(), mængdeVand));
    }

    public void afbryd() {
        Alert yseOrNo = new Alert(Alert.AlertType.CONFIRMATION);
        yseOrNo.setTitle("Bekræft afbrydelse");
        yseOrNo.setHeaderText("Er du sikker på, at du vil afbryde?");
        yseOrNo.setContentText("Alle ikke gemte ændringer går tabt.");

        Optional<ButtonType> result = yseOrNo.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            if (whiskyserie != null) {
                ArrayList<DestillatMængde> destillatMængder = new ArrayList<>(Controller.getDestillatmængder(whiskyserie));
                System.out.println("alle destilalnænger på whiskyerie" + destillatMængder);


                Controller.removeDestilatMængderFraWhiskyserie(whiskyserie, whiskyserie.getDestillatMængder());

                for (DestillatMængde d : destillatMængder) {
                    System.out.println("destillat tjek" + d);
                    Destillat destillat = d.getDestillat();
                    Controller.removeDestillatMængdeFraDestillat(destillat, d);
                }


                Controller.fjernWhiskyserie(whiskyserie);
                lwlDestillat.getItems().setAll(Controller.destilatWhiskySerieUdenFilter(Controller.getDestillater()));
            }

            close();

        } else {
            yseOrNo.close();

        }

    }
}
