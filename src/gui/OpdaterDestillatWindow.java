package gui;

import application.controller.Controller;
import application.model.Destillat;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpdaterDestillatWindow extends Stage {

    private static Button btnOpdater, btnAfbryd;

    private static TextField txfSvind, txfAlkoholprocent;

    private Destillat destillat;

    public OpdaterDestillatWindow(String title, Destillat destillat) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);


        GridPane pane = new GridPane();
        this.initContent(pane);
        pane.setMinWidth(200);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.destillat = destillat;
    }


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblSvind = new Label("Svind");
        pane.add(lblSvind, 0, 0);

        txfSvind = new TextField();
        pane.add(txfSvind, 0, 1);

        Label lblAlkoholprocent = new Label("Alkoholprocent");
        pane.add(lblAlkoholprocent, 1, 0);

        txfAlkoholprocent = new TextField();
        pane.add(txfAlkoholprocent, 1, 1);

        btnOpdater = new Button("Opdater");
        pane.add(btnOpdater, 1, 3);
        GridPane.setHalignment(btnOpdater, HPos.RIGHT);
        btnOpdater.setOnAction(Event -> opdater());

        btnAfbryd = new Button("Afbryd");
        pane.add(btnAfbryd, 0, 3);
        btnAfbryd.setOnAction(Event -> afbryd());

    }

    public void opdater() {
        try {
            if (!txfSvind.getText().isEmpty()) {
                double Svind = Double.parseDouble(txfSvind.getText());
                Controller.registerSvind(destillat, Svind);
            }
            if (!txfAlkoholprocent.getText().isEmpty()) {
                double alkoholprocent = Double.parseDouble(txfAlkoholprocent.getText());
                Controller.setMåltAlkoholprocent(destillat, alkoholprocent);
            }
            close();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Du skal skrive et tal");
            alert.showAndWait();
        }
        catch (RuntimeException e) {
            if(e.getMessage() != null && e.getMessage().contains("Tallet er negativt")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tallet skal være positivet");
                alert.showAndWait();
            }
            else if(e.getMessage() != null && e.getMessage().contains("Tallet er over 100")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Alkoholprocenten kan ikke være over 100");
                alert.showAndWait();
            }
            else if(e.getMessage() != null && e.getMessage().contains("Svindet er støre ind mængden")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Svindet kan ikke være mere ind mængden på destillatet");
                alert.showAndWait();
            }
            else if(e.getMessage() != null && e.getMessage().contains("Der er ikke mere af det valgte destillat")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Der er ikke mere af det valgte destillat");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Noget gik galt: ");
                alert.showAndWait();
            }
        }

    }

    public void afbryd() {
        txfAlkoholprocent.clear();
        txfSvind.clear();
        close();
    }
}
