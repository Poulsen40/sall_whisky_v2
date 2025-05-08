package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Fad;
import application.model.Whiskyserie;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
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

import java.util.ArrayList;
import java.util.List;

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
    private ListView<String> lwlValgteLeverandører;

    private static Button btnFiltrerFade;
    private static Button btnFiltrerWhiskey;
    private static Button btnVisHistorie;
    private static Button btnAfbryd;
    private static Button btnFadType;
    private static Button btnFadtypeVælg;
    private static Button btnFadtypeLuk;
    private static Button btnTræsort;
    private static Button btnTræsortLuk;
    private static Button btnTræsortVælg;
    private static Button btnLeverandørVælg;
    private static Button btnLeverandørLuk;
    private static Button btnLeverandør;
    private static Button btnTilføjLeverandør;
    private static Button btnFiltrerMedValg;
    private static CheckBox fyldtJa = new CheckBox("Ja");
    private static CheckBox fyldtNej = new CheckBox("Nej");


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        VBox Sidebar = new VBox();
        Sidebar.setPrefWidth(350);
        Sidebar.setVisible(false);
        pane.add(Sidebar, 2, 1, 2, 6);
        Sidebar.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        Sidebar.setTranslateX(1000);
        Sidebar.setSpacing(10);

        Label lblMinAlder = new Label("Min alder");
        Slider sliderMinAlder = new Slider(0, 50, 0);
        sliderMinAlder.setShowTickLabels(true);
//        sliderMinAlder.setShowTickMarks(true);
        sliderMinAlder.setMajorTickUnit(5);
        sliderMinAlder.setMinorTickCount(4);
        sliderMinAlder.setSnapToTicks(true);
        sliderMinAlder.setBlockIncrement(1);

        Label lblMaxAlder = new Label("Max alder");
        Slider sliderMaxAlder = new Slider(0, 50, 0);
        sliderMaxAlder.setShowTickLabels(true);
//        sliderMaxAlder.setShowTickMarks(true);
        sliderMaxAlder.setMajorTickUnit(5);
        sliderMaxAlder.setMinorTickCount(4);
        sliderMaxAlder.setSnapToTicks(true);
        sliderMaxAlder.setBlockIncrement(1);

        Label lblMinFadStørelse = new Label("Min fadstørelse");
        Slider sliderMinFadstørelse = new Slider(0, 650, 0);

        sliderMinFadstørelse.setShowTickLabels(true);
        sliderMinFadstørelse.setMajorTickUnit(50);
        sliderMinFadstørelse.setMinorTickCount(0);
        sliderMinFadstørelse.setSnapToTicks(true);
        sliderMinFadstørelse.setBlockIncrement(50);

        Label lblMaxFadstørelse = new Label("Max fadstørelse");
        Slider sliderMaxFadstørelse = new Slider(0, 650, 0);
        sliderMaxFadstørelse.setShowTickLabels(true);
//        sliderMaxFadstørelse.setShowTickMarks(false);
        sliderMaxFadstørelse.setMajorTickUnit(50);
        sliderMaxFadstørelse.setMinorTickCount(0);
        sliderMaxFadstørelse.setSnapToTicks(true);
        sliderMaxFadstørelse.setBlockIncrement(50);

        Label lblMinGangeBrugt = new Label("Min gange brugt");
        Slider sliderMinGangeBrugt = new Slider(0, 3, 0);
        sliderMinGangeBrugt.setShowTickLabels(true);
        sliderMinGangeBrugt.setMajorTickUnit(1);
        sliderMinGangeBrugt.setMinorTickCount(0);
        sliderMinGangeBrugt.setSnapToTicks(true);
        sliderMinGangeBrugt.setBlockIncrement(1);

        Label lblMaxGangeBrugt = new Label("Max gange brugt");
        Slider sliderMaxGangeBrugt = new Slider(0, 3, 0);
        sliderMaxGangeBrugt.setShowTickLabels(true);
        sliderMaxGangeBrugt.setMajorTickUnit(1);
        sliderMaxGangeBrugt.setMinorTickCount(0);
        sliderMaxGangeBrugt.setSnapToTicks(true);
        sliderMaxGangeBrugt.setBlockIncrement(1);

        Label lblSkalVæreFyldt = new Label("Skal fadet være fyldt?");

        fyldtJa = new CheckBox("Ja");
        pane.add(fyldtJa, 1, 3);
        GridPane.setHalignment(fyldtJa, HPos.LEFT);
        fyldtJa.setFocusTraversable(false);

        fyldtJa.setOnMouseClicked(event -> {
            fyldtNej.setDisable(fyldtJa.isSelected());
        });

        fyldtNej = new CheckBox("nej");
        pane.add(fyldtNej, 1, 3);
        GridPane.setHalignment(fyldtNej, HPos.CENTER);
        fyldtNej.setFocusTraversable(false);
        fyldtNej.setOnMouseClicked(event -> {
            fyldtJa.setDisable(fyldtNej.isSelected());
        });

        HBox hBoxskalværefyldt = new HBox();
        hBoxskalværefyldt.getChildren().setAll(lblSkalVæreFyldt, fyldtJa, fyldtNej);
        hBoxskalværefyldt.setSpacing(10);

        btnFadType = new Button("Fadtype");
        btnTræsort = new Button("Træsort");
        btnLeverandør = new Button("Leverandør");
        btnFiltrerMedValg = new Button("Filtrer");

        VBox.setMargin(btnFiltrerMedValg,new Insets(100,0,0,0));

        Sidebar.getChildren().addAll(lblMinAlder, sliderMinAlder, lblMaxAlder, sliderMaxAlder, lblMinFadStørelse, sliderMinFadstørelse, lblMaxFadstørelse,
                sliderMaxFadstørelse, lblMinGangeBrugt, sliderMinGangeBrugt, lblMaxGangeBrugt, sliderMaxGangeBrugt, hBoxskalværefyldt, btnFadType, btnTræsort, btnLeverandør,btnFiltrerMedValg);

        //Fadtype sidepanel
        VBox sidebarFadtype = new VBox();
        sidebarFadtype.setPrefWidth(350);
        sidebarFadtype.setVisible(false);
        pane.add(sidebarFadtype, 2, 1, 2, 6);
        sidebarFadtype.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarFadtype.setTranslateX(1000);
        sidebarFadtype.setSpacing(20);

        CheckBox cbExBourbon = new CheckBox("EXBOURBON");
        CheckBox cbExLorososherry = new CheckBox("EXOLOROSOSHERRY");

        Label label = new Label("Fadtype");

        btnFadtypeLuk = new Button("luk");
        btnFadtypeLuk.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarFadtype);
            if (sidebarFadtype.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarFadtype.setVisible(false));
                transitionUd.play();
            }
            cbExBourbon.setSelected(false);
            cbExLorososherry.setSelected(false);
        });

        btnFadtypeVælg = new Button("Vælg");
        btnFadtypeVælg.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarFadtype);
            if (sidebarFadtype.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarFadtype.setVisible(false));
                transitionUd.play();
            }
        });

        HBox FadtypeLukVælg = new HBox();
        FadtypeLukVælg.setSpacing(250);
        FadtypeLukVælg.getChildren().setAll(btnFadtypeLuk, btnFadtypeVælg);

        sidebarFadtype.getChildren().setAll(label, cbExBourbon, cbExLorososherry, FadtypeLukVælg);

        //Sidebar træsort
        VBox sidebarTræsort = new VBox();
        sidebarTræsort.setPrefWidth(350);
        sidebarTræsort.setVisible(false);
        pane.add(sidebarTræsort, 2, 1, 2, 6);
        sidebarTræsort.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarTræsort.setTranslateX(1000);
        sidebarTræsort.setSpacing(20);

        CheckBox cbTræsortEgetræ = new CheckBox("Egetræ");

        Label lblTræSort = new Label("Træsort");
        btnTræsortLuk = new Button("luk");
        btnTræsortLuk.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarTræsort);
            if (sidebarTræsort.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarTræsort.setVisible(false));
                transitionUd.play();
            }
            cbTræsortEgetræ.setSelected(false);
        });

        btnTræsortVælg = new Button("Vælg");
        btnTræsortVælg.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarTræsort);
            if (sidebarTræsort.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarTræsort.setVisible(false));
                transitionUd.play();
            }
        });

        HBox træsortLukVælg = new HBox();
        træsortLukVælg.setSpacing(250);
        træsortLukVælg.getChildren().setAll(btnTræsortLuk, btnTræsortVælg);

        sidebarTræsort.getChildren().addAll(lblTræSort, cbTræsortEgetræ, træsortLukVælg);

        //Sidebar leverandør
        VBox sidebarLeverandør = new VBox();
        sidebarLeverandør.setPrefWidth(350);
        sidebarLeverandør.setVisible(false);
        pane.add(sidebarLeverandør, 2, 1, 2, 6);
        sidebarLeverandør.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarLeverandør.setTranslateX(1000);
        sidebarLeverandør.setSpacing(20);

        List<String> valgteLeverandører = new ArrayList<>();

        Label lblLeverandør = new Label("Leverandør");
        btnLeverandørLuk = new Button("luk");
        btnLeverandørLuk.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
            if (sidebarLeverandør.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarLeverandør.setVisible(false));
                transitionUd.play();
                lwlValgteLeverandører.getItems().clear();
                valgteLeverandører.clear();


            }
        });

        btnLeverandørVælg = new Button("Vælg");
        btnLeverandørVælg.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
            if (sidebarLeverandør.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarLeverandør.setVisible(false));
                transitionUd.play();
            }
        });

        HBox leverandørLukVælg = new HBox();
        leverandørLukVælg.setSpacing(250);
        leverandørLukVælg.getChildren().setAll(btnLeverandørLuk, btnLeverandørVælg);
        lwlValgteLeverandører = new ListView<>();
        btnTilføjLeverandør = new Button("Tilføj");
        Label lblTilføjLeverandør = new Label("Tilføj leverandør");
        Label lblValgteLeverandører = new Label("Valgte Leverandører");
        TextField txfLeverandør = new TextField();
        HBox udfyldLeverandør = new HBox();
        btnTilføjLeverandør.setOnAction(Event -> {
            valgteLeverandører.add(txfLeverandør.getText());
            lwlValgteLeverandører.getItems().setAll(valgteLeverandører);
            System.out.println(valgteLeverandører.getFirst());
        });

        VBox vBoxValgteLeverandører = new VBox();
        vBoxValgteLeverandører.getChildren().addAll(lblValgteLeverandører, lwlValgteLeverandører);

        udfyldLeverandør.setSpacing(10);
        udfyldLeverandør.getChildren().setAll(lblTilføjLeverandør, txfLeverandør, btnTilføjLeverandør);

        sidebarLeverandør.getChildren().addAll(lblLeverandør, udfyldLeverandør, vBoxValgteLeverandører, leverandørLukVælg);

        //Tilføjer ting til pane
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
        pane.add(btnFiltrerFade, 1, 1);
        btnFiltrerFade.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), Sidebar);
                    TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), Sidebar);
                    if (!Sidebar.isVisible()) {
                        transision.setToX(100);
                        transision.play();
                        Sidebar.setVisible(true);
                    } else {
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
        VboxWhiskey.getChildren().addAll(btnFiltrerWhiskey, btnVisHistorie);
        pane.add(VboxWhiskey, 1, 3);

        btnAfbryd = new Button("Afbryd");
        pane.add(btnAfbryd, 0, 6);
        btnAfbryd.setOnAction(Event -> afbryd());

        //Knapper til at åbne sidepanel fadtype og træsort i sidepanel
        btnFadType.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarFadtype);
                    if (!sidebarFadtype.isVisible()) {
                        transision.setToX(100);
                        transision.play();
                        sidebarFadtype.setVisible(true);
                    }
                }
        );

        btnTræsort.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarTræsort);
                    if (!sidebarTræsort.isVisible()) {
                        transision.setToX(100);
                        transision.play();
                        sidebarTræsort.setVisible(true);
                    }
                }
        );

        btnLeverandør.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
                    if (!sidebarLeverandør.isVisible()) {
                        transision.setToX(100);
                        transision.play();
                        sidebarLeverandør.setVisible(true);
                    }
                }
        );

        btnFiltrerMedValg.setOnAction(Event -> filtrerFade());
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
