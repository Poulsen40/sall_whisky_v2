package gui;

import application.controller.Controller;
import application.model.Whiskyserie;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VisHistorieVindue extends Stage {


    private Whiskyserie whiskyserie;

    public VisHistorieVindue(String title, Whiskyserie whiskyserie) {

        this.whiskyserie = whiskyserie;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private static TextArea Txainfo;
    private static Button Btnafbryd, BtnPrint;
    private static HBox Hbocbtnafbrydbtnprint;


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Txainfo = new TextArea(Controller.info(whiskyserie).toString());
        pane.add(Txainfo, 0, 0);
        Txainfo.setMinWidth(800);
        Txainfo.setMinHeight(400);
        Txainfo.setEditable(false);

        //Knapper

        Btnafbryd = new Button("Afbryd");
        Btnafbryd.setPrefSize(80, 30);
        Btnafbryd.setOnAction(Event -> afbrydKnap());

        BtnPrint = new Button("Print");
        BtnPrint.setPrefSize(80, 30);

        BtnPrint.setOnAction(Event -> printKnap());

        Hbocbtnafbrydbtnprint = new HBox();
        Hbocbtnafbrydbtnprint.getChildren().setAll(Btnafbryd, BtnPrint);
        Hbocbtnafbrydbtnprint.setSpacing(700);
        pane.add(Hbocbtnafbrydbtnprint, 0, 1);


    }

    private void afbrydKnap() {
        close();
        whiskyserie = null;
        OpretOversigtWindow.resetwhikskylistview();
    }

    private void printKnap() {
        Controller.printTilFil(whiskyserie);
    }

}
