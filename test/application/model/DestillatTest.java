package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDateTime;

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

        b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 40.0, "ingen", Rygemateriale.GLØD);
        b2 = new Batch("Malt2", "Sort2", "Mark2", 100, 20.0, "ingen", Rygemateriale.GLØD);
        b3 = new Batch("Malt3", "Sort3", "Mark3", 100, 50.0, "ingen", Rygemateriale.GLØD);

        bm1 = new BatchMængde(100, d, b1);
        bm2 = new BatchMængde(100, d, b2);
        bm3 = new BatchMængde(100, d, b3);

    }

    @Test
    void testBeregnAlkoholProcent_TC1() {
        //Batchmængde 1: 100, 40%, Batchmængde 2: 100, 20%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);

        double result = d.beregnalkoholprocent();
        assertEquals(30.0, result, "Alkoholprocenten skal være 30%");
        System.out.println("TC1: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC2() {
        //Batchmængde 1: 200, 50%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        bm1 = new BatchMængde(200, d, b3); //opdaterer bm1 ved at sige new Batchmængde
        d.addBatchMængde(bm1);

        double result = d.beregnalkoholprocent();
        assertEquals(50.0, result, "Alkoholprocenten skal være 50%");
        System.out.println("TC2: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC3() {
        //Batchmængde 1: 100, 50%, Batchmængde 2: 200, 33%, Batchmængde 3: 50, 80%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        b1 = new Batch("Malt1", "Sort1", "Mark1", 50, 80.0, "ingen", Rygemateriale.GLØD);
        b2 = new Batch("Malt2", "Sort2", "Mark2", 200, 33.0, "ingen", Rygemateriale.GLØD);
        b3 = new Batch("Malt3", "Sort3", "Mark3", 100, 50.0, "ingen", Rygemateriale.GLØD);

        bm1 = new BatchMængde(100, d, b3); //bm1 AlcPct 50%
        bm2 = new BatchMængde(200, d, b2); //bm2 AlcPct 33%
        bm3 = new BatchMængde(50, d, b1); //bm3 AlcPct 80%

        d.addBatchMængde(bm1);
        d.addBatchMængde(bm2);
        d.addBatchMængde(bm3);
        double result = d.beregnalkoholprocent();
        assertEquals(44.57, result,0.1, "Alkoholprocenten skal være 44.57%");
        System.out.println("TC3: PASSED " + result);
    }

    @Test
    void testBeregnAlkoholProcent_TC4() {
        //Batchmængde 1: 120, 43%, Batchmængde 2: 43, 20%
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        b1 = new Batch("Malt1", "Sort1", "Mark1", 120, 43.0, "ingen", Rygemateriale.GLØD);
        b2 = new Batch("Malt2", "Sort2", "Mark2", 43, 20.0, "ingen", Rygemateriale.GLØD);

        BatchMængde bigB = new BatchMængde(120, d, b1);
        BatchMængde lilB = new BatchMængde(43, d, b2);

        double result = d.beregnalkoholprocent();
        assertEquals(36.93, result,0.01, "Alkohol pct. skal være 36.93%");
        System.out.println("TC4: PASSED " + result);
    }
    @Test
    void testBeregnAlkoholProcent_TC5() {
        //ingen batchmængder tilføjes til destillat
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        double result = d.beregnalkoholprocent();
        assertTrue(Double.isNaN(result), "Alkohol pct. skal være NaN for en tom liste");
        System.out.println("TC5: PASSED " + result);



    }

    @Test
    void testBeregnAlkoholProcent_TC6() {
        d = new Destillat(LocalDateTime.of(2022, 1, 1, 2, 2), f);

        //Batch 1: 100, 0%
        b1 = new Batch("Malt1", "Sort1", "Mark1", 100, 0.0, "ingen", Rygemateriale.GLØD);
        bm1 = new BatchMængde(100, d, b1);

        d.addBatchMængde(bm1);
        double result = d.beregnalkoholprocent();
        assertEquals(0.0, result, "Alkohol pct. skal være 0%, da batchmængden ikke indeholder noget alkohol");
        System.out.println("TC6 PASSED: " + result);



    }

    @Test
    void getBatchMængder() {
    }

    @Test
    void addBatchMængde() {
    }

    @Test
    void removeBatchMængde() {
    }

    @Test
    void getDestillatMængder() {
    }

    @Test
    void createDestillatMængde() {
    }

    @Test
    void removeDestillatMængde() {
    }


    @Test
    void getSamletMængde() {
    }

    @Test
    void getDatoForPåfyldning() {
    }

    @Test
    void getFad() {
    }

    @Test
    void testToString() {
    }
}