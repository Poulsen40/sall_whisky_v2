package application.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Destillat {
    private LocalDateTime datoForPåfyldning;
    private double samletMængde;
    private double alkoholPct;

    //Linkattribut
    private ArrayList<BatchMængde> batchMængder = new ArrayList<>();
    private Fad fad;

    public Destillat(LocalDateTime datoForPåfyldning, Fad fad) {
        this.datoForPåfyldning = datoForPåfyldning;
        this.fad = fad;
    }

    public ArrayList<BatchMængde> getBatchMængder() {
        return new ArrayList<>(batchMængder);
    }

    public void addBatchMængde(BatchMængde batchMængde) {
        if (!batchMængder.contains(batchMængde)) {
            batchMængder.add(batchMængde);
        }
    }

    public void removeBatchMængde(BatchMængde batchMængde) {
        if (batchMængder.contains(batchMængde)) {
            batchMængder.remove(batchMængde);
        }
    }

    public double beregnalkoholprocent() {
        double samletrentalkoholprocent = 0;
        double samletmængde = 0;

        for (BatchMængde batchMængde : batchMængder) {
            double alkopct = batchMængde.getBatch().getAlkoholPct();
            double mængde = batchMængde.getMængde();
            samletrentalkoholprocent += mængde * alkopct / 100;
            samletmængde += mængde;
        }
        return samletrentalkoholprocent/ samletmængde * 100;
    }
}
