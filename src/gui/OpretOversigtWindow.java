package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Fad;
import application.model.Whiskyserie;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class OpretOversigtWindow extends Stage {


    public OpretOversigtWindow(String title) {

        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle(title);


        GridPane pane = new GridPane();
        this.initContent(pane);
        pane.setMinWidth(800);

        Scene scene = new Scene(pane);
        this.setScene(scene);

    }

    private static ListView<Fad> lwlFade;
    private static ListView<Whiskyserie> lwlWhiskeyserier;
    private static ListView<Batch> lwlBatches;

    private static Button btnFiltrerFade;
    private static Button btnFiltrerWhiskey;
    private static Button btnVisHistorie;
    private static Button btnAfbryd;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        VBox Sidebar = new VBox();
        Sidebar.setPrefWidth(400);
        Sidebar.setVisible(false);
        pane.add(Sidebar,2,1);
        Label lbb = new Label("kat");
        Sidebar.getChildren().addAll(lbb);
        Sidebar.setStyle("-fx-background-color: #00FFFF; -fx-padding: 10;");
        Sidebar.setTranslateX(1000);

        Label lblFade = new Label("Fade");
        pane.add(lblFade, 0, 0);

        lwlFade = new ListView<>();
        pane.add(lwlFade, 0, 1);
        lwlFade.getItems().setAll(Controller.getFade());
        lwlFade.setMaxHeight(200);

        Label lblWhiskeyserier = new Label("Whiskeyserier");
        pane.add(lblWhiskeyserier, 0, 2);

        lwlWhiskeyserier = new ListView<>();
        pane.add(lwlWhiskeyserier, 0, 3);
//        lwlWhiskeyserier.getItems().setAll(Controller.getWhiskeyserier());
        lwlWhiskeyserier.setMaxHeight(200);

        Label lblBatches = new Label("Batches");
        pane.add(lblBatches, 0, 4);

        lwlBatches = new ListView<>();
        pane.add(lwlBatches, 0, 5);
        lwlBatches.getItems().setAll(Controller.getBatch());
        lwlBatches.setMaxHeight(200);

        btnFiltrerFade = new Button("Filtrer");
        pane.add(btnFiltrerFade,1,1);
        btnFiltrerFade.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600),Sidebar);
                    TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), Sidebar);

                    if(!Sidebar.isVisible()) {
                        transision.setToX(150);
                        transision.play();
                        Sidebar.setVisible(true);
                    }
                    else {
                        transitionUd.setToX(800);
                        transitionUd.setOnFinished(e -> Sidebar.setVisible(false));
                        transitionUd.play();
                    }


                }
        );

        btnFiltrerWhiskey = new Button("Filtrer");
        btnFiltrerWhiskey.setOnAction(Event -> filtrerWhiskey());

        btnVisHistorie = new Button("Vis historie");
        btnVisHistorie.setOnAction(Event -> visHistorie());

        VBox VboxWhiskey = new VBox(10);
        VboxWhiskey.getChildren().addAll(btnFiltrerWhiskey,btnVisHistorie);
        pane.add(VboxWhiskey,1,3);

        btnAfbryd = new Button("Afbryd");
        pane.add(btnAfbryd,0,6);
        btnAfbryd.setOnAction(Event -> afbryd());

    }

    public void afbryd() {

    }

    public void filtrerFade() {

    }

    public void filtrerWhiskey() {

    }

    public void visHistorie() {

    }
}
