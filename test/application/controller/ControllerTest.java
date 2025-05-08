package application.controller;


import application.model.*;
//import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {


    List<Fad> fade = List.of(

            // Fyldte Fade
            Controller.createFad(50, "SPANIEN", true, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0),
            Controller.createFad(200, "USA", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1),
            Controller.createFad(650, "SPANIEN", true, Fadtype.EXBOURBON, Træsort.EGETRÆ, 3),
            Controller.createFad(200, "USA", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 2),
            Controller.createFad(100, "FRANKRIG", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 0),
            Controller.createFad(650, "USA", true, Fadtype.EXBOURBON, Træsort.EGETRÆ, 2),
            Controller.createFad(50, "USA", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 1),
            Controller.createFad(200, "SPANIEN", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 3),

            //Tomme Fade :)
            Controller.createFad(300, "USA", true, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0),
            Controller.createFad(400, "SPANIEN", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1),
            Controller.createFad(650, "USA", false, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 3)
    );
    Destillat d1 = new Destillat(LocalDateTime.now().minusYears(2), fade.getFirst());
    Destillat d2 = new Destillat(LocalDateTime.now().minusYears(5), fade.get(1));
    Destillat d3 = new Destillat(LocalDateTime.now().minusYears(8), fade.get(2));
    Destillat d4 = new Destillat(LocalDateTime.now().minusYears(10), fade.get(3));
    Destillat d5 = new Destillat(LocalDateTime.now().minusYears(3), fade.get(4));
    Destillat d6 = new Destillat(LocalDateTime.now().minusYears(0), fade.get(5));
    Destillat d7 = new Destillat(LocalDateTime.now().minusYears(3), fade.get(6));
    Destillat d8 = new Destillat(LocalDateTime.now().minusYears(4), fade.get(7));


    @Test
    void TC1CreateLager() {
        Lager lager = Controller.createLager(10, 5, 2, "ContatinerLager");
        Lager lager1 = null;
        for (Lager l1 : Storage.getLager()) {
            if (lager.equals(l1)) {
                lager1 = l1;
            }
        }
        assertEquals(lager, lager1);
    }

    @Test
    void TC2CreateLager() {
        Lager lager = new Lager(10, 5, 2, "ContatinerLager");
        assertEquals(lager.getNavn(), "ContatinerLager");

        assertEquals(lager.getObevaringsplads().length, 10);
        assertEquals(lager.getObevaringsplads()[0].length, 5);
        assertEquals(lager.getObevaringsplads()[0][0].length, 2);
    }

    @Test
    void TC8() {

        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, null, null, null,  true);
        assertEquals(8, fade.size()); // kun fyldte fade
    }

    @Test
    void TC9() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, null, null, null,  false);
        assertEquals(11, fade.size()); // alle fade (fyldte og tomme
    }

    @Test
    void TC10() {
        List<Fad> fade = Controller.fadsøgning(650, 650, 0, 50, 0, 3, null, null, null,  true);
        assertEquals(2, fade.size()); // Præcis fadstørrelse (650) - kun fyldte fade
    }
    @Test
    void TC11() {
        List<Fad> fade = Controller.fadsøgning(50, 50, 0, 50, 0, 3, null, null, null,  true);
        assertEquals(2, fade.size()); // Præcis mindste typiske fadstørrelse (50) - kun fyldte fade
    }

    @Test
    void TC12() {
        List<Fad> fade = Controller.fadsøgning(200, 200, 0, 50, 0, 3, null, null, null, true);
        assertEquals(3, fade.size()); // Præcis fadstørrelse (200) - kun fyldte fade
    }

    @Test
    void TC13() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 0, 0, 3, null, null, null,  true);
        assertEquals(1, fade.size()); // Helt nyt destillat (alder = 0) - kun fyldte fade
    }

    @Test
    void TC14() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 50, 50, 0, 3, null, null, null,  true);
        assertEquals(0, fade.size()); // Præcis alder (50) - kun fyldte fade
    }

    @Test
    void TC15() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 3, 4, 0, 3, null, null, null, true);
        assertEquals(3, fade.size()); // Standard whisky-alder (3-4 år) - kun fyldte fade
    }

    @Test
    void TC16() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 0, null, null, null,  true);
        assertEquals(2, fade.size()); // Kun nye fade - kun fyldte fade
    }
    @Test
    void TC17() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 1, 1, null, null, null,  true);
        assertEquals(2, fade.size()); // Præcis én gang brugt - kun fyldte fade
    }
    @Test
    void TC18() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 3, 3, null, null, null,  true);
        assertEquals(2, fade.size()); // Præcis mange gange brugt (3) - kun fyldte fade
    }
    @Test
    void TC19() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),  true);
        assertEquals(8, fade.size()); // Tomme lister - kun fyldte fade
    }

    @Test
    void TC20() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3,null, List.of("USA"), null,  true);
        assertEquals(4, fade.size()); // Liste med præcis én leverandør - kun fyldte fade
    }
    @Test
    void TC21() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, null, null, List.of(Træsort.EGETRÆ),  true);
        assertEquals(8, fade.size()); // Liste med præcis én træsort - kun fyldte fade
    }

    @Test
    void TC22() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, List.of(Fadtype.EXBOURBON), null, null,  true);
        assertEquals(5, fade.size()); // Liste med præcis én fadtype - kun fyldte fade
    }

    @Test
    void TC23() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, null, List.of("USA","SPANIEN","FRANKRIG"), null,  true);
        assertEquals(8, fade.size()); // Liste med alle mulige leverandører - kun fyldte fade
    }

    @Test
    void TC24() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, null, null, List.of(Træsort.EGETRÆ), true);
        assertEquals(8, fade.size()); // Liste med alle mulige træsorter - kun fyldte fade
    }

    @Test
    void TC25() {
        List<Fad> fade = Controller.fadsøgning(0, 650, 0, 50, 0, 3, Arrays.asList(Fadtype.EXBOURBON, Fadtype.EXOLOROSOSHERRY), null, null,  true);
        assertEquals(8, fade.size()); // Liste med alle mulige fadtyper - kun fyldte fade
    }

    @Test
    void TC26() {
        List<Fad> fade = Controller.fadsøgning(0, 0, 0, 0, 0, 0, null, null, null, true);
        assertEquals(0, fade.size()); // Minimale værdier overalt - kun fyldte fade
    }

    @Test
    void TC27() {
        List<Fad> fade = Controller.fadsøgning(650, 650, 50, 50, 3, 3, Arrays.asList(Fadtype.EXBOURBON, Fadtype.EXOLOROSOSHERRY),List.of("USA","SPANIEN","FRANKRIG"), List.of(Træsort.EGETRÆ), true);
        assertEquals(0, fade.size()); // Maksimale værdier overalt - kun fyldte fade
    }





}
