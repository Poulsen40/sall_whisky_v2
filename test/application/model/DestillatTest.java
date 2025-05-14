package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


class DestillatTest {


    private Destillat d;
    private Fad f;
    private Batch b1, b2, b3;
    private BatchMængde bm1, bm2, bm3;

    @BeforeEach
    void setUp() {
        f = new Fad(100, "TestLeverandør", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 1);
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        b2 = new Batch("Malt2", "Sort2", "Mark2", 100, 20.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        b3 = new Batch("Malt3", "Sort3", "Mark3", 100, 50.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));

        bm1 = new BatchMængde(100, d, b1);
        bm2 = new BatchMængde(100, d, b2);
        bm3 = new BatchMængde(100, d, b3);
    }

    @Test
    void testBeregnAlkoholProcent_TC39() {
        //Batchmængde 1: 100, 40%, Batchmængde 2: 100, 20%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);

        double result = d.beregnalkoholprocent();
        assertEquals(30.0, result, "Alkoholprocenten skal være 30%");
        System.out.println("TC39: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC40() {
        //Batchmængde 1: 200, 50%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        bm1 = new BatchMængde(200, d, b3); //opdaterer bm1 ved at sige new Batchmængde
        d.addBatchMængde(bm1);

        double result = d.beregnalkoholprocent();
        assertEquals(50.0, result, "Alkoholprocenten skal være 50%");
        System.out.println("TC40: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC41() {
        //Batchmængde 1: 100, 50%, Batchmængde 2: 200, 33%, Batchmængde 3: 50, 80%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        b1 = new Batch("Malt1", "Sort1", "Mark1", 50, 80.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        b2 = new Batch("Malt2", "Sort2", "Mark2", 200, 33.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        b3 = new Batch("Malt3", "Sort3", "Mark3", 100, 50.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));

        bm1 = new BatchMængde(100, d, b3); //bm1 AlcPct 50%
        bm2 = new BatchMængde(200, d, b2); //bm2 AlcPct 33%
        bm3 = new BatchMængde(50, d, b1); //bm3 AlcPct 80%

        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);
        d.addBatchMængde(bm3);
        double result = d.beregnalkoholprocent();
        assertEquals(44.57, result, 0.1, "Alkoholprocenten skal være 44.57%");
        System.out.println("TC41: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC42() {
        //Batchmængde 1: 120, 43%, Batchmængde 2: 43, 20%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        b1 = new Batch("Malt1", "Sort1", "Mark1", 120, 43.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        b2 = new Batch("Malt2", "Sort2", "Mark2", 43, 20.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));

        BatchMængde bigB = new BatchMængde(120, d, b1);
        BatchMængde lilB = new BatchMængde(43, d, b2);

        double result = d.beregnalkoholprocent();
        assertEquals(36.93, result, 0.01, "Alkohol pct. skal være 36.93%");
        System.out.println("TC42: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC43() {
        //ingen batchmængder tilføjes til destillat
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        double result = d.beregnalkoholprocent();
        assertTrue(Double.isNaN(result), "Alkohol pct. skal være NaN for en tom liste");
        System.out.println("TC43: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC44() {
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        //Batch 1: 100, 0%
        b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 0.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        bm1 = new BatchMængde(100, d, b1);

        d.addBatchMængde(bm1);
        double result = d.beregnalkoholprocent();
        assertEquals(0.0, result, "Alkohol pct. skal være 0%, da batchmængden ikke indeholder noget alkohol");
        System.out.println("TC44 PASSED: " + result);
    }
//---------------------------------------------------------------------
    @Test
    void addBatchMængde_TC47() {
        Destillat d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 100, 40, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(100, d, b);

        //Tilføjer bm1 til destillat
        d.addBatchMængde(bm);
        assertEquals(1, d.getBatchMængder().size(), "Batchmængderne skal være 1 efter tilføjelse.");
        assertTrue(d.getBatchMængder().contains(bm), "Batchmængden skal være i listen.");
        System.out.println("TC47 PASSED: Batchmængde tilføjet korrekt.");
    }

    @Test
    void addBatchMængde_TC48() {
        Destillat d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2),f );
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 0, 20, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(0, d, b);

        d.addBatchMængde(bm);
        assertEquals(1, d.getBatchMængder().size(), "Batchmængderne skal være 1 efter tilføjelse.");
        assertTrue(d.getBatchMængder().contains(bm), "Batchmængden skal være i listen.");
        System.out.println("TC49 PASSED: Batchmængde tilføjet korrekt.");

    }

    @Test
    void addBatchMængde_TC49() {
        Destillat d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);
        Batch b = new Batch("Malt1", "Sort1", "Mark1", -100, -40, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(-100, d, b);

        d.addBatchMængde(bm);
        assertEquals(1, d.getBatchMængder().size(), "Batchmængderne skal være 1 efter tilføjelse.");
        assertTrue(d.getBatchMængder().contains(bm), "Batchmængden skal være i listen.");
        System.out.println("TC49 PASSED: Batchmængde tilføjet korrekt.");
    }

    //------------------------------------------------
    @Test
    void getBatchMængder_TC50() {
        //Destillat uden batches
        Destillat d = new Destillat(LocalDateTime.now(), f);

        List<BatchMængde> batchMængder = d.getBatchMængder();
        System.out.println("Batchmængder i destillat (forventet tom liste): " + batchMængder);

        assertNotNull(batchMængder, "Listen må ikke være null.");
        assertEquals(0, batchMængder.size(), "Forventet tom liste, men listen var ikke tom.");
        System.out.println("TC50 PASSED: returnerer tom liste .");


    }
    @Test
    void getBatchMængder_TC51() {
        Destillat destillat = new Destillat(LocalDateTime.now(), f);
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(100, destillat, b);


        //Tilføjer batchmængden til destillatet
        destillat.addBatchMængde(bm);
        List<BatchMængde> batchMængder = destillat.getBatchMængder();

        System.out.println("Batchmængder i destillat: " + batchMængder);
        assertNotNull(batchMængder, "Listen må ikke være null.");
        assertEquals(1, batchMængder.size(), "Forventet én batchmængde, men listen indeholder ikke præcis én.");
        assertTrue(batchMængder.contains(bm), "Batchmængden skal være i listen.");
        System.out.println("TC51 PASSED: Returnerer batchmængde i liste");
    }
    @Test
    void getBatchMængder_TC52() {
        Destillat d = new Destillat(LocalDateTime.now(), f);
        Batch b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        Batch b2 = new Batch("Malt2", "Sort2", "Mark2", 200, 20.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm1 = new BatchMængde(100, d, b1);
        BatchMængde bm2 = new BatchMængde(200, d, b2);

        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);

        List<BatchMængde> batchMængder = d.getBatchMængder();

        System.out.println("Batchmængder i destillat: " + batchMængder);
        assertNotNull(batchMængder, "Listen må ikke være null.");
        //Tester at listen indeholder præcis to batchmængder
        assertEquals(2, batchMængder.size(), "Forventet to batchmængder, men listen indeholder ikke præcis to.");

        //Tester at batchmængderne findes i listen
        assertTrue(batchMængder.contains(bm1), "Batchmængde1 skal være i listen.");
        assertTrue(batchMængder.contains(bm2), "Batchmængde2 skal være i listen.");
        System.out.println("TC52 PASSED: Returnerer batchmængde i liste");

    }

    //--------------------------------------------------
    @Test
    void RemoveBatchMængde_TC53() {
        Destillat d = new Destillat(LocalDateTime.now(), f);
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(100, d, b);

        d.addBatchMængde(bm);
        System.out.println("Batchmængder før fjernelse: " + d.getBatchMængder());
        d.removeBatchMængde(bm);
        System.out.println("Batchmængder efter fjernelse: " + d.getBatchMængder());

        assertFalse(d.getBatchMængder().contains(bm), "Batchmængden skal være fjernet fra listen.");
        assertEquals(0, d.getBatchMængder().size(), "Listen skal være tom efter fjernelse af batchmængden.");
        System.out.println("TC52 PASSED: Returnerer batchmængde");

    }
    @Test
    public void RemoveBatchMængde_TC54() {
        Destillat d = new Destillat(LocalDateTime.now(), f);
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        BatchMængde bm = new BatchMængde(100, d, b);

        d.removeBatchMængde(bm);
        assertFalse(d.getBatchMængder().contains(bm), "Batchmængden skal ikke være i listen.");
        System.out.println("TC54: ingen batchmængder ");
    }
    @Test
    public void RemoveBatchMængde_TC55() {
        Destillat d = new Destillat(LocalDateTime.now(), f);

        List<BatchMængde> batchMængder = d.getBatchMængder();
        System.out.println("batchMængder " + batchMængder);
        assertTrue(batchMængder.isEmpty(), "Batchmængder listen skal være tom.");
        assertFalse(batchMængder.contains(null), "Listen må ikke indeholde null-værdier.");
        System.out.println("TC55: ingen batchmængder ");
    }
//_________________________________
    @Test
    public void getDestillatMængder_TC56() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);
        DestillatMængde dm = d.createDestillatMængde(150, w);

        List<DestillatMængde> destillatMængder = d.getDestillatMængder();
        System.out.println("DestillatMængder i destillat: " + destillatMængder);

        //Tester at listen ikke er null
        assertNotNull(destillatMængder, "Listen må ikke være null.");
        assertEquals(1, destillatMængder.size(), "Der skal være én destillatmængde.");
        assertTrue(destillatMængder.contains(dm), "DestillatMængde skal findes i destillatets liste.");
        System.out.println("TC56: Returnerer korrekte destillatmængde");

    }
    @Test
    void getDestillatMængder_TC57() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);

        DestillatMængde dm1 = d.createDestillatMængde(150, w);
        DestillatMængde dm2 = d.createDestillatMængde(200, w);

        List<DestillatMængde> destillatMængder = d.getDestillatMængder();
        System.out.println("DestillatMængder i destillat: " + destillatMængder);
        assertNotNull(destillatMængder, "Listen må ikke være null.");
        assertEquals(2, destillatMængder.size(), "Der skal være to destillatmængder.");
        assertEquals(150, destillatMængder.get(0).getMængde(), 0.01);
        assertEquals(200, destillatMængder.get(1).getMængde(), 0.01);
        System.out.println("TC57: Returnerer batchmængder ");

    }
    @Test
    void getDestillatMængder_TC58() {
        Destillat d = new Destillat(LocalDateTime.now(), f);

        List<DestillatMængde> destillatMængder = d.getDestillatMængder();
        System.out.println("liste " + destillatMængder);
        assertNotNull(destillatMængder, "Listen må ikke være null.");
        assertEquals(0, destillatMængder.size(), "Der forventedes 0 destillatMængder, men listen var ikke tom.");
        System.out.println("TC58: Returner tom liste");
    }
//____________________________--
    @Test
    void testCreateDestillatMængde_TC59() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);
        DestillatMængde dm = d.createDestillatMængde(150, w);

        System.out.println("Oprettet destillatmængde: " + dm.getMængde() + "L i whiskyserien: " + w.getSerieNavn());
        System.out.println("Antal destillatmængder i destillat: " + d.getDestillatMængder().size());

        assertNotNull(dm);
        assertEquals(150, dm.getMængde());
        System.out.println("TC59: Opretter destillatmægnde");
    }
    @Test
    void testCreateDestillatMængde_TC60() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);
        DestillatMængde dm = d.createDestillatMængde(0, w);

        System.out.println("Oprettet destillatmængde med 0L i whiskyserien: " + w.getSerieNavn());
        System.out.println("Antal destillatmængder i destillat: " + d.getDestillatMængder().size());

        assertNotNull(dm);
        assertEquals(0, dm.getMængde());
    }
    @Test
    void testCreateDestillatMængde_TC61() {
        // Testen forventer en NullPointerException, da whiskyserie er null
        assertThrows(NullPointerException.class, () -> {
            Destillat destillat = new Destillat(LocalDateTime.now(), f);

            destillat.createDestillatMængde(-100, null);
        });

        //tjekker om testen fejler
        System.out.println("TC61: NullPointerException kastes.");
    }
    //-------------------------------------------------------------------------------------------
    @Test
    void testRemoveDestillatMængde_TC62() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);
        DestillatMængde dm = new DestillatMængde(200, w, d);

        System.out.println("Forsøger at fjerne destillatmængde, som aldrig blev tilføjet: " + dm.getMængde() + "L");
        d.removeDestillatMængde(dm);
        System.out.println("Antal destillatmængder efter fjernelse: " + d.getDestillatMængder().size());

        assertFalse(d.getDestillatMængder().contains(dm));
        System.out.println("TC62: Fjerner destillatmængde");
    }
    @Test
    void testRemoveDestillatMængde_TC63() {
        Destillat d = new Destillat(LocalDateTime.now(), f);

        System.out.println("Forsøger at fjerne null fra destillatmængder.");
        d.removeDestillatMængde(null);
        System.out.println("Antal destillatmængder efter fjernelse: " + d.getDestillatMængder().size());

        assertTrue(d.getDestillatMængder().isEmpty());
        System.out.println("TC63: Fjerner destillatmængde");

    }
    @Test
    void testRemoveDestillatMængde_TC64() {
        Whiskyserie w = new Whiskyserie("ws", LocalDate.now());
        Destillat d = new Destillat(LocalDateTime.now(), f);
        DestillatMængde dm = d.createDestillatMængde(150, w);

        System.out.println("Fjerner destillatmængde: " + dm.getMængde() + "L fra whiskyserien: " + w.getSerieNavn());
        d.removeDestillatMængde(dm);
        System.out.println("Antal destillatmængder efter fjernelse: " + d.getDestillatMængder().size());

        assertFalse(d.getDestillatMængder().contains(dm));
        System.out.println("TC64: Fjerner destillatmængde");
    }
    //----------------------------------
    @Test
    void testGetSamletMængde_TC65() {
        Destillat d = new Destillat(LocalDateTime.now(), f);

        System.out.println("Samlet mængde uden batchmængder: " + d.getSamletMængde());
        assertEquals(0, d.getSamletMængde(), "Forventet samlet mængde: 0L");
        System.out.println("TC65: Får korrekte samlet mængde");


    }
    @Test
    void testGetSamletMængde_TC66() {
        Batch b = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD,LocalDate.of(2018,10,10));
        Destillat d = new Destillat(LocalDateTime.now(), f);
        BatchMængde bm = new BatchMængde(100, d, b);
        d.addBatchMængde(bm);

        System.out.println("Samlet mængde efter tilføjelse af batchmængde: " + d.getSamletMængde());
        assertEquals(100, d.getSamletMængde(), "Forventet samlet mængde: 100L");
        System.out.println("TC66: Får korrekte samlet mængde");
    }
    @Test
    void testGetSamletMængde_TC67() {
        Whiskyserie w = new Whiskyserie("Serie1", LocalDate.now());
        Batch b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        Batch b2 = new Batch("Malt2", "Sort2", "Mark2", 200, 20.0, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        Destillat d = new Destillat(LocalDateTime.now(), f);

        BatchMængde bm1 = new BatchMængde(100, d, b1);
        BatchMængde bm2 = new BatchMængde(200, d, b2);
        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);

        //Opretter destillatmængde og tilføjer den til destillatet
        DestillatMængde destillatMængde = d.createDestillatMængde(50, w);
        System.out.println("Samlet mængde efter tilføjelse af to batchmængder: " + d.getSamletMængde());
        assertEquals(250, d.getSamletMængde(), "Forventet samlet mængde: 300L");
        System.out.println("TC67: Får korrekte samlet mængde");
    }
    //----------------------------------
    @Test
    void testGetDatoForPåfyldning_TC68() {
        LocalDateTime dato = LocalDateTime.of(2023, 5, 12, 14, 30);
        Destillat d = new Destillat(dato, f);

        System.out.println("Dato for påfyldning: " + d.getDatoForPåfyldning());
        assertEquals(dato, d.getDatoForPåfyldning());
        System.out.println("TC68: Får korrekte dato");

    }
    @Test
    void testGetDatoForPåfyldning_TC69() {
        Destillat destillat = new Destillat(null, f);

        System.out.println("Dato for påfyldning er null.");
        assertNull(destillat.getDatoForPåfyldning());
        System.out.println("TC69: Returnerer null");
    }
    //--------------------------------------------
    @Test
    void testGetFad_TC70() {
        Fad f = new Fad(100, "TestLeverandør", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 1);
        Destillat d = new Destillat(LocalDateTime.now(), f);

        System.out.println("Fad tilknyttet destillat: " + d.getFad());
        assertEquals(f, d.getFad());
        System.out.println("TC70: Returnerer det korrekte fad");


    }
    @Test
    void testGetFad_TC71() {

        assertThrows(NullPointerException.class, () -> {
            Destillat d = new Destillat(LocalDateTime.now(), null);

            // Kalder getFad(), hvilket forventes at kaste en NullPointerException
            d.getFad();
        });

        // Hvis exceptionen ikke kastes, fejler testen automatisk
        System.out.println("TC71: NullPointerException kastes.");
    }
//------------------------------------------------------
    @Test
    void testToString_TC72() {
        LocalDateTime dato = LocalDateTime.of(2023, 5, 12, 14, 30);
        Destillat d = new Destillat(dato, f);
        String result = d.toString();

        System.out.println("Destillat toString output:\n" + result);
        assertNotNull(result);
        assertTrue(result.contains("Antal liter"));
        System.out.println("TC72: Returnerer info om destillat");
    }

    @Test
    void testToString_TC73() {
        assertThrows(NullPointerException.class, () -> { //forventer nullpointer
            Destillat d = new Destillat(null, null);
            d.toString();
        });
        System.out.println("TC73: NullPointerException kastes.");
    }
}
