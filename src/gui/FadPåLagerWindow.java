package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLOutput;

public class FadPåLagerWindow extends Stage {

    private Destillat destillat;

    public FadPåLagerWindow(String title, Destillat destillat) {

        this.destillat = destillat;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.setOnCloseRequest(event -> {
            event.consume(); // Forhindrer vinduet i at lukke
        });
    }


    private static ListView<Lager> lwlager;
    private static TextArea txadestillat;
    private static Button btnCancel;


    public void initContent(GridPane pane) {

        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblIntro = new Label("Info");

        lwlager = new ListView<>();
        lwlager.setPrefWidth(200);
        lwlager.setPrefHeight(150);
        lwlager.getItems().setAll(Controller.getlagere());
        lwlager.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox vBox = new VBox();
        pane.add(vBox, 0, 0);
        vBox.getChildren().add(lblIntro);
        vBox.getChildren().add(lwlager);


        txadestillat = new TextArea();
        pane.add(txadestillat, 0, 1);
        txadestillat.setText(destillat.toString());

        btnCancel = new Button("Afbryd");
        pane.add(btnCancel, 0, 3);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close(); // lukker dialogen
        });
        txadestillat.setEditable(false);


    }
}
