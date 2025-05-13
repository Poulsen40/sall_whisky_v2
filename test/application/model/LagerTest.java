package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import static org.junit.jupiter.api.Assertions.*;


class LagerTest {
    Lager lager1 = new Lager(2, 2, 2, "Lager1");
    Fad f1;
    Fad f2;
    Fad f3;
    Fad f4;
    Fad f5;
    Fad f6;
    Fad f7;
    Fad f8;


    @BeforeEach
    void setUp() {
        Storage storage = new Storage();
        Controller.setStorage(storage);
        f1 = Controller.createFad(65, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f2 = Controller.createFad(30, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f3 = Controller.createFad(12, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f4 = Controller.createFad(100, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f5 = Controller.createFad(68, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f6 = Controller.createFad(32, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f7 = Controller.createFad(10, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        f8 = Controller.createFad(160, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
    }

    @Test
    void TC3tilføjFadTilobevaringsplads() {
        lager1.tilføjFadTilobevaringsplads(f1);
        String plcaring = lager1.tilføjFadTilobevaringsplads(f2);

        assertEquals("Reol: 0 Hylde: 0 Plads: 1", plcaring);
    }

    @Test
    void TC4tilføjFadTilobevaringsplads() {
        lager1.tilføjFadTilobevaringsplads(f1);
        lager1.tilføjFadTilobevaringsplads(f2);
        String placering = lager1.tilføjFadTilobevaringsplads(f3);

        assertEquals("Reol: 0 Hylde: 1 Plads: 0",placering);

    }

    @Test
    void TC5tilføjFadTilobevaringsplads() {
        lager1.tilføjFadTilobevaringsplads(f1);
        lager1.tilføjFadTilobevaringsplads(f2);
        lager1.tilføjFadTilobevaringsplads(f3);
        String placering = lager1.tilføjFadTilobevaringsplads(f4);

        assertEquals("Reol: 0 Hylde: 1 Plads: 1",placering);

    }

    @Test
    void TC6tilføjFadTilobevaringsplads() {
        lager1.tilføjFadTilobevaringsplads(f1);
        lager1.tilføjFadTilobevaringsplads(f2);
        lager1.tilføjFadTilobevaringsplads(f3);
        lager1.tilføjFadTilobevaringsplads(f4);
        String placering = lager1.tilføjFadTilobevaringsplads(f5);

        assertEquals("Reol: 1 Hylde: 0 Plads: 0",placering);

    }

    @Test
    void TC7tilføjFadTilobevaringsplads() {
        lager1.tilføjFadTilobevaringsplads(f1);
        lager1.tilføjFadTilobevaringsplads(f2);
        lager1.tilføjFadTilobevaringsplads(f3);
        lager1.tilføjFadTilobevaringsplads(f4);
        lager1.tilføjFadTilobevaringsplads(f5);
        lager1.tilføjFadTilobevaringsplads(f6);
        lager1.tilføjFadTilobevaringsplads(f7);
        String placering = lager1.tilføjFadTilobevaringsplads(f8);

        assertEquals("Reol: 1 Hylde: 1 Plads: 1",placering);
        assertEquals(true,lager1.getErFyldt());
    }
}