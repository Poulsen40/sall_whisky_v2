package gui;

import application.controller.Controller;
import application.model.*;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    private static Button btnFiltrerFade, btnFiltrerWhiskey, btnVisHistorie, btnAfbryd, btnFadType, btnFadtypeVælg,
            btnTræsort, btnFadtypeLuk, btnTræsortLuk, btnTræsortVælg, btnLeverandørVælg, btnLeverandørLuk, btnLeverandør,
            btnTilføjLeverandør, btnFiltrerMedValg, btnFjernLeverandør, btnRydvalgFad;
    private static Slider sliderMinAlder, sliderMaxAlder, sliderMinFadstørelse, sliderMaxFadstørelse, sliderMinGangeBrugt,
            sliderMaxGangeBrugt;
    private static CheckBox cbExBourbon, cbExOlorososherry, cbTræsortEgetræ, cbFyldt;

    private static List<String> valgteLeverandører = new ArrayList<>();

    // Buttons til filter af Whisky
    private static Slider sliderMinAlkopct, sliderMaxalkopct, sliderMinStørrelse, slidermaxStørrelse, sliderManxantalflasker, sliderminAntalFlasker, sliderminAlderForWhiskeyserien, slidermaxAlderForWhiskySerien;
    private static Button Btnwhiskyetyper, BtnLukWhisky, BtnvælgWhisky, BtnVis, btnRydValgWhiskey;
    private static Label LblWhiskytype;
    private static CheckBox cbSinglecask, cbsinglemalt, cbcaststrenght, cbmaltstrengt;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        //Filter sidepanel
        VBox Sidebar = new VBox();
        Sidebar.setPrefWidth(350);
        Sidebar.setVisible(false);
        pane.add(Sidebar, 2, 1, 2, 6);
        Sidebar.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        Sidebar.setTranslateX(1000);
        Sidebar.setSpacing(10);

        //Whiksy Sidepanel
        VBox Whiskysidebar = new VBox();
        Whiskysidebar.setPrefWidth(350);
        Whiskysidebar.setVisible(false);
        pane.add(Whiskysidebar, 2, 1, 2, 6);
        Whiskysidebar.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        Whiskysidebar.setTranslateX(1000);
        Whiskysidebar.setSpacing(10);


        //labels og sliders til Fad sidepanel
        sliderMinAlder = new Slider(0, 50, 0);
        sliderMinAlder.setShowTickLabels(true);
        sliderMinAlder.setShowTickMarks(true);
        sliderMinAlder.setMajorTickUnit(5);
        sliderMinAlder.setMinorTickCount(4);
        sliderMinAlder.setSnapToTicks(true);
        sliderMinAlder.setBlockIncrement(1);
        Label lblMinAlder = new Label("Min destillat alder: " + (int) sliderMinAlder.getValue());
        sliderMinAlder.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMinAlder.setText("Min destillat alder: " + newValue.intValue());
        });

        sliderMaxAlder = new Slider(0, 50, 50);
        sliderMaxAlder.setShowTickLabels(true);
        sliderMaxAlder.setMajorTickUnit(5);
        sliderMaxAlder.setMinorTickCount(4);
        sliderMaxAlder.setSnapToTicks(true);
        sliderMaxAlder.setBlockIncrement(1);
        Label lblMaxAlder = new Label("Max destillat alder: " + (int) sliderMaxAlder.getValue());
        sliderMaxAlder.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMaxAlder.setText("Max destillat alder: " + newValue.intValue());
        });

        sliderMinFadstørelse = new Slider(0, 650, 0);
        sliderMinFadstørelse.setShowTickLabels(true);
        sliderMinFadstørelse.setMajorTickUnit(50);
        sliderMinFadstørelse.setMinorTickCount(0);
        sliderMinFadstørelse.setSnapToTicks(true);
        sliderMinFadstørelse.setBlockIncrement(50);
        Label lblMinFadStørelse = new Label("Min fadstørelse: " + (int) sliderMinFadstørelse.getValue());
        sliderMinFadstørelse.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMinFadStørelse.setText("Min fadstørelse: " + newValue.intValue());
        });

        sliderMaxFadstørelse = new Slider(0, 650, 650);
        sliderMaxFadstørelse.setShowTickLabels(true);
        sliderMaxFadstørelse.setMajorTickUnit(50);
        sliderMaxFadstørelse.setMinorTickCount(0);
        sliderMaxFadstørelse.setSnapToTicks(true);
        sliderMaxFadstørelse.setBlockIncrement(50);
        Label lblMaxFadstørelse = new Label("Max fadstørelse: " + (int) sliderMaxFadstørelse.getValue());
        sliderMaxFadstørelse.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMaxFadstørelse.setText("Max fadstørelse: " + newValue.intValue());
        });

        sliderMinGangeBrugt = new Slider(0, 3, 0);
        sliderMinGangeBrugt.setShowTickLabels(true);
        sliderMinGangeBrugt.setMajorTickUnit(1);
        sliderMinGangeBrugt.setMinorTickCount(0);
        sliderMinGangeBrugt.setSnapToTicks(true);
        sliderMinGangeBrugt.setBlockIncrement(1);
        Label lblMinGangeBrugt = new Label("Min gange brugt: " + (int) sliderMinGangeBrugt.getValue());
        sliderMinGangeBrugt.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMinGangeBrugt.setText("Min gange brugt: " + newValue.intValue());
        });


        sliderMaxGangeBrugt = new Slider(0, 3, 3);
        sliderMaxGangeBrugt.setShowTickLabels(true);
        sliderMaxGangeBrugt.setMajorTickUnit(1);
        sliderMaxGangeBrugt.setMinorTickCount(0);
        sliderMaxGangeBrugt.setSnapToTicks(true);
        sliderMaxGangeBrugt.setBlockIncrement(1);
        Label lblMaxGangeBrugt = new Label("Max gange brugt: " + (int) sliderMaxGangeBrugt.getValue());
        sliderMaxGangeBrugt.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMaxGangeBrugt.setText("Max gange brugt: " + newValue.intValue());
        });

        // Labes i WhiskySidepanelet
        sliderMinAlkopct = new Slider(0, 100, 0);
        sliderMinAlkopct.setShowTickLabels(true);
        sliderMinAlkopct.setShowTickMarks(true);
        sliderMinAlkopct.setMajorTickUnit(5);
        sliderMinAlkopct.setMinorTickCount(4);
        sliderMinAlkopct.setSnapToTicks(true);
        sliderMinAlkopct.setBlockIncrement(1);
        Label lblMinalkoholpct = new Label("Min Alkohol pct: " + (int) sliderMinAlkopct.getValue());
        sliderMinAlkopct.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblMinalkoholpct.setText("Min Alkohol pct: " + newValue.intValue());
        });

        sliderMaxalkopct = new Slider(0, 100, 100);
        sliderMaxalkopct.setShowTickLabels(true);
        sliderMaxalkopct.setShowTickMarks(true);
        sliderMaxalkopct.setMajorTickUnit(5);
        sliderMaxalkopct.setMinorTickCount(4);
        sliderMaxalkopct.setSnapToTicks(true);
        sliderMaxalkopct.setBlockIncrement(1);
        Label LblMaxalkoholpct = new Label("Max Alkohol pct: " + (int) sliderMaxalkopct.getValue());
        sliderMaxalkopct.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblMaxalkoholpct.setText("Max Alkohol pct: " + newValue.intValue());
        });

        sliderMinStørrelse = new Slider(0, 100, 0);
        sliderMinStørrelse.setShowTickLabels(true);
        sliderMinStørrelse.setShowTickMarks(true);
        sliderMinStørrelse.setMajorTickUnit(5);
        sliderMinStørrelse.setMinorTickCount(4);
        sliderMinStørrelse.setSnapToTicks(true);
        sliderMinStørrelse.setBlockIncrement(1);
        Label Lblminstørrelse = new Label("Min størrelse/mængden: " + (int) sliderMinStørrelse.getValue());
        sliderMinStørrelse.valueProperty().addListener((observable, oldValue, newValue) -> {
            Lblminstørrelse.setText("Min størrelse/mængden: " + newValue.intValue());
        });

        slidermaxStørrelse = new Slider(0, 100, 100);
        slidermaxStørrelse.setShowTickLabels(true);
        slidermaxStørrelse.setShowTickMarks(true);
        slidermaxStørrelse.setMajorTickUnit(10);
        slidermaxStørrelse.setMinorTickCount(9);
        slidermaxStørrelse.setSnapToTicks(true);
        slidermaxStørrelse.setBlockIncrement(1);
        Label LblMaxStørrelse = new Label("Max størrelse/mængden: " + (int) slidermaxStørrelse.getValue());
        slidermaxStørrelse.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblMaxStørrelse.setText("Max størrelse/mængden: " + newValue.intValue());
        });

        sliderminAntalFlasker = new Slider(0, 2000, 0);
        sliderminAntalFlasker.setShowTickLabels(true);
        sliderminAntalFlasker.setShowTickMarks(true);
        sliderminAntalFlasker.setMajorTickUnit(100);
        sliderminAntalFlasker.setMinorTickCount(50);
        sliderminAntalFlasker.setSnapToTicks(true);
        sliderminAntalFlasker.setBlockIncrement(1);
        Label LblminAntalFlasker = new Label("Min Antal flasker: " + (int) sliderminAntalFlasker.getValue());
        sliderminAntalFlasker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblminAntalFlasker.setText("Min Antal flasker: " + newValue.intValue());
        });

        sliderManxantalflasker = new Slider(0, 2000, 2000);
        sliderManxantalflasker.setShowTickLabels(true);
        sliderManxantalflasker.setShowTickMarks(true);
        sliderManxantalflasker.setMajorTickUnit(100);
        sliderManxantalflasker.setMinorTickCount(50);
        sliderManxantalflasker.setSnapToTicks(true);
        sliderManxantalflasker.setBlockIncrement(1);
        Label LblmaxAntalFlasker = new Label("Max Antal flakser: " + (int) sliderManxantalflasker.getValue());
        sliderManxantalflasker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblmaxAntalFlasker.setText("Max Antal flakser: " + newValue.intValue());
        });

        sliderminAlderForWhiskeyserien = new Slider(0, 20, 0);
        sliderminAlderForWhiskeyserien.setShowTickLabels(true);
        // sliderminAlderForWhiskeyserien.setShowTickMarks(true);
        sliderminAlderForWhiskeyserien.setMajorTickUnit(1);
        sliderminAlderForWhiskeyserien.setMinorTickCount(0);
        sliderminAlderForWhiskeyserien.setSnapToTicks(true);
        sliderminAlderForWhiskeyserien.setBlockIncrement(1);
        Label LblMinårForWhiskyserien = new Label("Min År på Whiskyeserien: " + (int) sliderminAlderForWhiskeyserien.getValue());
        sliderminAlderForWhiskeyserien.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblMinårForWhiskyserien.setText("Min År på Whiskyeserien: " + newValue.intValue());
        });

        slidermaxAlderForWhiskySerien = new Slider(0, 20, 20);
        slidermaxAlderForWhiskySerien.setShowTickLabels(true);
        // slidermaxAlderForWhiskySerien.setShowTickMarks(true);
        slidermaxAlderForWhiskySerien.setMajorTickUnit(1);
        slidermaxAlderForWhiskySerien.setMinorTickCount(0);
        slidermaxAlderForWhiskySerien.setSnapToTicks(true);
        slidermaxAlderForWhiskySerien.setBlockIncrement(1);
        Label LblMaxÅrForWhiskySerien = new Label("Max År på Whiskyeserien: " + (int) slidermaxAlderForWhiskySerien.getValue());
        slidermaxAlderForWhiskySerien.valueProperty().addListener((observable, oldValue, newValue) -> {
            LblMaxÅrForWhiskySerien.setText("Max År på Whiskyeserien: " + newValue.intValue());
        });


        Label lblSkalVæreFyldt = new Label("Skal fadet være fyldt?");

        cbFyldt = new CheckBox("Ja");
        pane.add(cbFyldt, 1, 3);
        GridPane.setHalignment(cbFyldt, HPos.LEFT);
        cbFyldt.setFocusTraversable(false);

        HBox hBoxskalværefyldt = new HBox();
        hBoxskalværefyldt.getChildren().setAll(lblSkalVæreFyldt, cbFyldt);
        hBoxskalværefyldt.setSpacing(10);

        btnFadType = new Button("Fadtype");
        btnTræsort = new Button("Træsort");
        btnLeverandør = new Button("Leverandør");
        btnFiltrerMedValg = new Button("Vis");
        btnRydvalgFad = new Button("Ryd");


        Btnwhiskyetyper = new Button("WhiskyTyper");
        BtnVis = new Button("Vis");
        btnRydValgWhiskey = new Button("Ryd");

        VBox.setMargin(btnFiltrerMedValg, new Insets(100, 0, 0, 0));

        HBox sidebarRydVis = new HBox(btnRydvalgFad, btnFiltrerMedValg);
        HBox.setMargin(btnRydvalgFad, new Insets(100, 0, 0, 0));
        HBox.setMargin(btnFiltrerMedValg, new Insets(100, 0, 0, 0));
        sidebarRydVis.setSpacing(260);
        VBox.setMargin(sidebarRydVis, new Insets(65, 0, 0, 0));

        HBox sidebarRydVisWhiskey = new HBox(btnRydValgWhiskey, BtnVis);
        HBox.setMargin(btnRydValgWhiskey, new Insets(100, 0, 0, 0));
        HBox.setMargin(BtnVis, new Insets(100, 0, 0, 0));
        sidebarRydVisWhiskey.setSpacing(260);


        Sidebar.getChildren().addAll(lblMinAlder, sliderMinAlder, lblMaxAlder, sliderMaxAlder, lblMinFadStørelse, sliderMinFadstørelse, lblMaxFadstørelse,
                sliderMaxFadstørelse, lblMinGangeBrugt, sliderMinGangeBrugt, lblMaxGangeBrugt, sliderMaxGangeBrugt, hBoxskalværefyldt, btnFadType, btnTræsort, btnLeverandør, sidebarRydVis);

        Whiskysidebar.getChildren().addAll(lblMinalkoholpct, sliderMinAlkopct, LblMaxalkoholpct, sliderMaxalkopct, Lblminstørrelse, sliderMinStørrelse, LblMaxStørrelse, slidermaxStørrelse
                , LblminAntalFlasker, sliderminAntalFlasker, LblmaxAntalFlasker, sliderManxantalflasker,
                LblMinårForWhiskyserien, sliderminAlderForWhiskeyserien, LblMaxÅrForWhiskySerien, slidermaxAlderForWhiskySerien, Btnwhiskyetyper, sidebarRydVisWhiskey);


        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
        //WhiskyTyper sidepanel
        VBox sidebarWhiskyTyper = new VBox();
        sidebarWhiskyTyper.setPrefWidth(350);
        sidebarWhiskyTyper.setVisible(false);
        pane.add(sidebarWhiskyTyper, 2, 1, 2, 6);
        sidebarWhiskyTyper.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarWhiskyTyper.setTranslateX(1000);
        sidebarWhiskyTyper.setSpacing(20);

        cbSinglecask = new CheckBox("SINGLE CASK");
        cbsinglemalt = new CheckBox("SINGLE MALT");
        cbmaltstrengt = new CheckBox("MALT STRENGTH");
        cbcaststrenght = new CheckBox("CAST STRENGHT");

        cbSinglecask.setSelected(false);
        cbsinglemalt.setSelected(false);
        cbmaltstrengt.setSelected(false);
        cbcaststrenght.setSelected(false);

        LblWhiskytype = new Label("Whiskytyper");

        BtnLukWhisky = new Button("Luk");

        BtnLukWhisky.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarWhiskyTyper);
            if (sidebarWhiskyTyper.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarWhiskyTyper.setVisible(false));
                transitionUd.play();
            }
            cbSinglecask.setSelected(false);
            cbsinglemalt.setSelected(false);
            cbmaltstrengt.setSelected(false);
            cbcaststrenght.setSelected(false);
        });

        BtnvælgWhisky = new Button("Vælg");

        BtnvælgWhisky = new Button("Vælg");
        BtnvælgWhisky.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarWhiskyTyper);
            if (sidebarWhiskyTyper.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarWhiskyTyper.setVisible(false));
                transitionUd.play();
            }
        });

        HBox Whiskylukvælg = new HBox();
        Whiskylukvælg.setSpacing(250);
        Whiskylukvælg.getChildren().setAll(BtnLukWhisky, BtnvælgWhisky);

        sidebarWhiskyTyper.getChildren().addAll(LblWhiskytype, cbSinglecask, cbsinglemalt, cbmaltstrengt, cbcaststrenght, Whiskylukvælg);


        //Fadtype sidepanel
        VBox sidebarFadtype = new VBox();
        sidebarFadtype.setPrefWidth(350);
        sidebarFadtype.setVisible(false);
        pane.add(sidebarFadtype, 2, 1, 2, 6);
        sidebarFadtype.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarFadtype.setTranslateX(1000);
        sidebarFadtype.setSpacing(20);


        cbExBourbon = new CheckBox("Ex bourbon");
        cbExOlorososherry = new CheckBox("Ex Oloroso sherry");


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
            cbExOlorososherry.setSelected(false);
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

        sidebarFadtype.getChildren().setAll(label, cbExBourbon, cbExOlorososherry, FadtypeLukVælg);

        //Sidebar træsort
        VBox sidebarTræsort = new VBox();
        sidebarTræsort.setPrefWidth(350);
        sidebarTræsort.setVisible(false);
        pane.add(sidebarTræsort, 2, 1, 2, 6);
        sidebarTræsort.setStyle("-fx-background-color: #CCCCCC; -fx-padding: 10;");
        sidebarTræsort.setTranslateX(1000);
        sidebarTræsort.setSpacing(20);

        cbTræsortEgetræ = new CheckBox("Egetræ");

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

        Label lblLeverandør = new Label("Leverandør");

        TextField txfLeverandør = new TextField();

        btnLeverandørLuk = new Button("luk");
        btnLeverandørLuk.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
            if (sidebarLeverandør.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarLeverandør.setVisible(false));
                transitionUd.play();
                lwlValgteLeverandører.getItems().clear();
                valgteLeverandører.clear();
                txfLeverandør.clear();


            }
        });

        btnLeverandørVælg = new Button("Vælg");
        btnLeverandørVælg.setOnAction(Event -> {
            TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
            if (sidebarLeverandør.isVisible()) {
                transitionUd.setToX(1000);
                transitionUd.setOnFinished(e -> sidebarLeverandør.setVisible(false));
                transitionUd.play();
                txfLeverandør.clear();
            }
        });

        HBox leverandørLukVælg = new HBox();
        leverandørLukVælg.setSpacing(250);
        leverandørLukVælg.getChildren().setAll(btnLeverandørLuk, btnLeverandørVælg);
        lwlValgteLeverandører = new ListView<>();
        btnTilføjLeverandør = new Button("Tilføj");
        Label lblTilføjLeverandør = new Label("Tilføj leverandør");
        Label lblValgteLeverandører = new Label("Valgte Leverandører");
        HBox udfyldLeverandør = new HBox();
        btnTilføjLeverandør.setOnAction(Event -> {
            valgteLeverandører.add(txfLeverandør.getText());
            lwlValgteLeverandører.getItems().setAll(valgteLeverandører);
        });

        VBox vBoxValgteLeverandører = new VBox();
        vBoxValgteLeverandører.getChildren().addAll(lblValgteLeverandører, lwlValgteLeverandører);

        udfyldLeverandør.setSpacing(10);
        udfyldLeverandør.getChildren().setAll(lblTilføjLeverandør, txfLeverandør, btnTilføjLeverandør);

        btnFjernLeverandør = new Button("Fjern Leverandør");
        btnFjernLeverandør.setOnAction(Event -> {
            valgteLeverandører.remove(lwlValgteLeverandører.getSelectionModel().getSelectedItem());
            lwlValgteLeverandører.getItems().setAll(valgteLeverandører);
        });

        sidebarLeverandør.getChildren().addAll(lblLeverandør, udfyldLeverandør, vBoxValgteLeverandører, btnFjernLeverandør, leverandørLukVælg);

        //Tilføjer ting til pane
        Label lblFade = new Label("Fade");
        pane.add(lblFade, 0, 0);

        lwlFade = new ListView<>();
        pane.add(lwlFade, 0, 1);
        lwlFade.getItems().setAll(Controller.getFade());
        lwlFade.setMaxHeight(200);
        lwlFade.setMinWidth(400);
        setLwlFadeTekstLayout();


        Label lblWhiskeyserier = new Label("Whiskeyserier");
        pane.add(lblWhiskeyserier, 0, 2);

        lwlWhiskeyserier = new ListView<>();
        pane.add(lwlWhiskeyserier, 0, 3);
        lwlWhiskeyserier.getItems().setAll(Controller.getWhiskyserie());
        lwlWhiskeyserier.setMaxHeight(200);
        setLwlwhiskeyserierTekstLayout();

        Label lblBatches = new Label("Batches");
        pane.add(lblBatches, 0, 4);

        lwlBatches = new ListView<>();
        pane.add(lwlBatches, 0, 5);
        lwlBatches.getItems().setAll(Controller.getBatches());
        lwlBatches.setMaxHeight(200);
        setLwlBatchesTekstLayout();

        btnFiltrerFade = new Button("Filtrer");
        pane.add(btnFiltrerFade, 1, 1);
        btnFiltrerFade.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), Sidebar);
                    TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), Sidebar);
                    if (!Sidebar.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        Sidebar.setVisible(true);

                    } else {
                        transitionUd.setToX(800);
                        transitionUd.setOnFinished(e ->
                                Sidebar.setVisible(false));
                        transitionUd.play();
                    }
                }
        );

        //Knapper
        btnFiltrerWhiskey = new Button("wFiltrer");
        btnFiltrerWhiskey.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), Whiskysidebar);
                    TranslateTransition transitionUd = new TranslateTransition(Duration.millis(600), Whiskysidebar);
                    if (!Whiskysidebar.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        Whiskysidebar.setVisible(true);

                    } else {
                        transitionUd.setToX(800);
                        transitionUd.setOnFinished(e ->
                                Whiskysidebar.setVisible(false));
                        transitionUd.play();
                    }
                }
        );

        btnVisHistorie = new Button("Vis historie");
        btnVisHistorie.setOnAction(Event -> visHistorie());

        VBox VboxWhiskey = new VBox(10);
        VboxWhiskey.getChildren().addAll(btnFiltrerWhiskey, btnVisHistorie);
        pane.add(VboxWhiskey, 1, 3);

        btnAfbryd = new Button("Afbryd");
        pane.add(btnAfbryd, 0, 6);
        btnAfbryd.setOnAction(Event -> close());

        //Knapper til at åbne sidepanel fadtype og træsort i sidepanel
        btnFadType.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarFadtype);
                    if (!sidebarFadtype.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        sidebarFadtype.setVisible(true);
                    }
                }
        );

        btnTræsort.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarTræsort);
                    if (!sidebarTræsort.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        sidebarTræsort.setVisible(true);
                    }
                }
        );

        btnLeverandør.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarLeverandør);
                    if (!sidebarLeverandør.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        sidebarLeverandør.setVisible(true);
                    }
                }
        );

        Btnwhiskyetyper.setOnAction(Event -> {
                    TranslateTransition transision = new TranslateTransition(Duration.millis(600), sidebarWhiskyTyper);
                    if (!sidebarWhiskyTyper.isVisible()) {
                        transision.setToX(10);
                        transision.play();
                        sidebarWhiskyTyper.setVisible(true);
                    }
                }
        );

        btnRydvalgFad.setOnAction(Event -> resetFadSidebar()
        );


        btnFiltrerMedValg.setOnAction(Event -> filtrerFade());

        BtnVis.setOnAction(Event -> filtrerWhiskey());

        btnRydValgWhiskey.setOnAction(Event -> resetWhiskeySidebar());
    }

    //------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------
    // WHISKY OVERSIGT/FILTRERING
    //------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------


    public void afbryd() {

    }

    public void filtrerFade() {
        double minFadStørelse = sliderMinFadstørelse.getValue();
        double maxFadStørelse = sliderMaxFadstørelse.getValue();
        int minAlder = (int) sliderMinAlder.getValue();
        int maxAlder = (int) sliderMaxAlder.getValue();
        int minBrugt = (int) sliderMinGangeBrugt.getValue();
        int MaxBrugt = (int) sliderMaxGangeBrugt.getValue();
        Boolean skalVæreFyldt = false;
        List<Fadtype> valgteFadTyper = new ArrayList<>();
        List<Træsort> valgeTræsorter = new ArrayList<>();
        List<String> valgteLeverandører = lwlValgteLeverandører.getItems();

        if (cbFyldt.isSelected()) {
            skalVæreFyldt = true;
        }

        if (cbExBourbon.isSelected()) {
            valgteFadTyper.add(Fadtype.EXBOURBON);
        }
        if (cbExOlorososherry.isSelected()) {
            valgteFadTyper.add(Fadtype.EXOLOROSOSHERRY);
        }
        if (cbTræsortEgetræ.isSelected()) {
            valgeTræsorter.add(Træsort.EGETRÆ);
        }


        lwlFade.getItems().setAll(Controller.fadsøgning(minFadStørelse, maxFadStørelse, minAlder, maxAlder, minBrugt, MaxBrugt,
                valgteFadTyper, valgteLeverandører, valgeTræsorter, skalVæreFyldt));

    }

    public void filtrerWhiskey() {
        double minAlkoholpct = sliderMinAlkopct.getValue();
        double maxAlkoholpct = sliderMaxalkopct.getValue();
        double minMægnde = sliderMinStørrelse.getValue();
        double maxMægnde = slidermaxStørrelse.getValue();
        int minAntalflasker = (int) sliderminAntalFlasker.getValue();
        int maxAntalFlaser = (int) sliderManxantalflasker.getValue();
        int minAlder = (int) sliderminAlderForWhiskeyserien.getValue();
        int maxAlder = (int) slidermaxAlderForWhiskySerien.getValue();
        List<WhiskyType> valgteWhiskyTyper = new ArrayList<>();

        if (cbcaststrenght.isSelected()) {
            valgteWhiskyTyper.add(WhiskyType.CASKSTRENGTH);
        }
        if (cbmaltstrengt.isSelected()) {
            valgteWhiskyTyper.add(WhiskyType.MALTSTRENGTH);
        }
        if (cbsinglemalt.isSelected()) {
            valgteWhiskyTyper.add(WhiskyType.SINGLEMALT);
        }
        if (cbSinglecask.isSelected()) {
            valgteWhiskyTyper.add(WhiskyType.SINGLECASK);
        }

        lwlWhiskeyserier.getItems().setAll(Controller.whiskeySøgning(minAlkoholpct, maxAlkoholpct, minMægnde, maxMægnde, minAntalflasker, maxAntalFlaser, minAlder, maxAlder, valgteWhiskyTyper));


    }

    public void visHistorie() {
        if (lwlWhiskeyserier.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Vælg først en whisky serie du vil se historien på");
            alert.showAndWait();
        } else {
            VisHistorieVindue dia = new VisHistorieVindue("Hej", lwlWhiskeyserier.getSelectionModel().getSelectedItem());
            dia.show();
        }
    }

    public void setLwlFadeTekstLayout() {
        lwlFade.setCellFactory(lw -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);

                if (empty || fad == null) {
                    setText(null);
                } else if (Controller.getDestillat(fad) == null) {
                    setText("Fad Information:\n" + "Fad nr: " + Controller.getFadNr(fad) + "    Fadstørelse: " + Controller.getFadStørrelse(fad) + "    levarandør: " + Controller.getLeverandør(fad)
                            + "    Er Brugt: " + Controller.isErBrugt(fad) + "\nFadtype: " + Controller.getFadtype(fad) + "    Træsort: " +
                            Controller.getTræsort(fad) + "\nAntalGangeBrugt: " + Controller.getAntalGangeBrugt(fad) + "    Liter på fyldt: " + Controller.getLiterPåfyldt(fad) + "\nLager: " +
                            Controller.getLagerNavn(fad)
                            + "\nInformation om destillat på fadet:\n" + "Fadet har intet Destilat");
                } else {
                    Destillat destillat = Controller.getDestillat(fad);
                    setText("Fad Information\n" + "Fad nr: " + Controller.getFadNr(fad) + "    Fadstørelse: " + Controller.getFadStørrelse(fad) + "    levarandør: " + Controller.getLeverandør(fad)
                            + "    Er Brugt: " + Controller.isErBrugt(fad) + "\nFadtype: " + Controller.getFadtype(fad) + "    Træsort: " +
                            Controller.getTræsort(fad) + "\nAntalGangeBrugt: " + Controller.getAntalGangeBrugt(fad) + "    LiterPåfyldt: " + Controller.getLiterPåfyldt(fad) + "\nLager: " +
                            Controller.getLagerNavn(fad)
                            + "\nInformation om destillat på fadet\n" + "Alder: " +
                            ChronoUnit.YEARS.between(Controller.getDatoForPåfyldning(destillat).toLocalDate(), LocalDate.now()) + " År" +
                            "    Alkholprocent: " + Controller.getAlkoholprocent(destillat));
                }
            }
        });
    }

    public void setLwlwhiskeyserierTekstLayout() {
        lwlWhiskeyserier.setCellFactory(lw -> new ListCell<Whiskyserie>() {
            @Override
            protected void updateItem(Whiskyserie whiskyserie, boolean empty) {
                super.updateItem(whiskyserie, empty);

                if (empty || whiskyserie == null) {
                    setText(null);
                } else {
                    setText("WhiskeySerie infromation:\nSerienavn: " + Controller.getSerieNavn(whiskyserie) + "    Alkoholprocent: " + String.format("%.2f%%", Controller.getAlkoholPct(whiskyserie)) +
                            "    Mængde: " + Controller.getStørrelse(whiskyserie) + "L" + "\nAlder: " + ChronoUnit.YEARS.between(Controller.getDato(whiskyserie), LocalDate.now()) + " År" + "    Antal flasker: " +
                            +Controller.getAntalFlasker(whiskyserie) + "    Whiskeytype: " + Controller.getWhiskyType(whiskyserie));
                }
            }
        });
    }

    //Skal slut dato med bliver den nogensinde sat og brugt?
    public void setLwlBatchesTekstLayout() {
        lwlBatches.setCellFactory(lw -> new ListCell<Batch>() {
            @Override
            protected void updateItem(Batch batch, boolean empty) {
                super.updateItem(batch, empty);

                if (empty || batch == null) {
                    setText(null);
                } else {
                    setText("Batch information:\nBatch Id: " + Controller.getBatchID(batch) + "    Alkoholprocent: " + String.format("%.2f%%", Controller.getAlkoholPct(batch)) +
                            "    Mængde: " + Controller.getMængdeVæske(batch) + "L" + "\nMark: " + Controller.getMark(batch) + " Kornsort: " + Controller.getKornSort(batch) + "    Maltbatch: " +
                            Controller.getMaltBach(batch) + "\nRygemateriale: " + Controller.getRygemateriale(batch) + "    Startdato: "
                            + Controller.getStartDato(batch) + "    Slutdato: " + Controller.getSlutDato(batch));
                }
            }
        });
    }

    public void resetFadSidebar() {

        sliderMinAlder.setValue(0);
        sliderMaxAlder.setValue(sliderMaxAlder.getMax());
        sliderMinFadstørelse.setValue(0);
        sliderMaxFadstørelse.setValue(sliderMaxFadstørelse.getMax());
        sliderMinGangeBrugt.setValue(0);
        sliderMaxGangeBrugt.setValue(sliderMaxGangeBrugt.getMax());
        cbFyldt.setSelected(false);
        cbExBourbon.setSelected(false);
        cbExOlorososherry.setSelected(false);
        cbTræsortEgetræ.setSelected(false);
        lwlValgteLeverandører.getItems().clear();
        valgteLeverandører.clear();
        lwlFade.getItems().setAll(Controller.getFade());
    }

    public void resetWhiskeySidebar() {
        sliderMinAlkopct.setValue(0);
        sliderMaxalkopct.setValue(sliderMaxalkopct.getMax());
        sliderMinStørrelse.setValue(0);
        slidermaxStørrelse.setValue(slidermaxStørrelse.getMax());
        sliderminAntalFlasker.setValue(0);
        sliderManxantalflasker.setValue(sliderManxantalflasker.getMax());
        sliderminAlderForWhiskeyserien.setValue(0);
        slidermaxAlderForWhiskySerien.setValue(slidermaxAlderForWhiskySerien.getMax());
        cbSinglecask.setSelected(false);
        cbsinglemalt.setSelected(false);
        cbcaststrenght.setSelected(false);
        cbmaltstrengt.setSelected(false);
        lwlWhiskeyserier.getItems().setAll(Controller.getWhiskyserie());
    }



    // Bruger denne metode til og resette min lwlWhiskeyserier når jeg trykker afbryd i min visHistorieVindue

    public static void resetwhikskylistview(){
        lwlWhiskeyserier.getSelectionModel().clearSelection();
    }
}
