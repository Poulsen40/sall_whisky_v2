package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.DestillatMængde;
import application.model.Fad;
import application.model.Whiskyserie;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;

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

    private static String serieNavn;

    private static DestillatMængde destillatMængde;

    private static LocalDate dato;
    private static ListView<Fad> lwlFade;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblStep1 = new Label("Step 1 : vælg et navn og dato for whiskyserien");
        lblStep1.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label lblNavn = new Label("Navn på whiskyserie");

        txfNavn = new TextField();

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Vælg datoen for oprettelsen af whiskyserien");
        datePicker.setOnAction(event -> {
            dato = datePicker.getValue();
        });


        Button btnOpretWhiskySerieObjekt = new Button("Opret whisky serie");
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

        lwlFade = new ListView<>();
        lwlFade.setPrefWidth(200);
        lwlFade.setPrefHeight(150);
        ArrayList<Fad> frieFade = new ArrayList<>();
        for (Fad f : Controller.getFade()) {
            if (Controller.getDestillat(f) != null) {
                frieFade.add(f);
            }
        }
        lwlFade.getItems().setAll(frieFade);
        lwlFade.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        GridPane.setValignment(lwlFade, VPos.BOTTOM);

        Label lblStep2 = new Label("Step 2 : vælg det fad du vil tappe destilat fra og mængden. Gentag hvis ønskes");
        lblStep2.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label lblTap = new Label("Fade med destillat klar til tapning");

        VBox step2 = new VBox();
        step2.setSpacing(15);
        step2.getChildren().add(lblStep2);
        step2.getChildren().add(lblTap);
        step2.getChildren().add(lwlFade);

        txfTapMængde = new TextField();

        Label lblTapMængde = new Label("Mængden du vil tappe");

        Button btnTap = new Button("Tap");
        btnTap.setOnAction(event -> {
            tapMængdeFraDestilat();
            setInfoBox();
        });

        VBox step2_2 = new VBox();
        step2_2.setSpacing(15);
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

        Label lblVand = new Label("Fortynd eventuelt med vand (liter)");

        txfVand = new TextField();

        Button btnFortynd = new Button("Fortynd");
        GridPane.setHalignment(btnFortynd, HPos.RIGHT);

        VBox step3 = new VBox();
        step3.setSpacing(15);
        step3.getChildren().addAll(lblStep3, lblVand, txfVand, btnFortynd);

        txaInfo = new TextArea("Whisky serie information: \n");
        txaInfo.setPrefWidth(450);

        Button btnTapPåFlaske = new Button("Tap flasker");


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


    }

    public void opretWhiskySerieObjekt() {
        serieNavn = txfNavn.getText().trim();

        whiskyserie = Controller.createWhiskyserie(serieNavn, dato);

        System.out.println(whiskyserie);
    }

    public void tapMængdeFraDestilat() {
        Fad selectedFad = lwlFade.getSelectionModel().getSelectedItem();
        Destillat valgtDestillat = selectedFad.getDestillat();
        double mængde = Double.parseDouble(txfTapMængde.getText().trim());

        destillatMængde = Controller.createDestillatMængde(mængde, whiskyserie, valgtDestillat);
        Controller.addDestillatMængde(destillatMængde, whiskyserie);
        System.out.println("Deestilat mængder på whiskeyserien" + whiskyserie.getDestillatMængder());
    }

    public void setInfoBox() {
        txaInfo.setText("Whisky serie navn: " + serieNavn + "\nDato oprettet: " + dato + "\nSamlet mængde væske: " + Controller.samletMængdeWhiskySerie(whiskyserie)
                + "\nForventet antal flasker: " + Controller.antalForventetFlakser(whiskyserie, Controller.samletMængdeWhiskySerie(whiskyserie)));


    }

}
