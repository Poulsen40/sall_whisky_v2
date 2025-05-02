package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
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
    }


    private static ListView<Lager> lwlager;
    private static TextArea txadestillat;

    public void initContent(GridPane pane){

        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        lwlager = new ListView<>();
        pane.add(lwlager, 0, 0);
        lwlager.setPrefWidth(200);
        lwlager.setPrefHeight(150);
        lwlager.getItems().setAll(Controller.getlagere());
        lwlager.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txadestillat = new TextArea();
        pane.add(txadestillat,0,1);
        txadestillat.setText(destillat.toString());
    }
}
