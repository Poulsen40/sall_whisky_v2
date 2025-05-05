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
        fad.setDestillat(this);
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

    public double getSamletMængde(){
        double mængde = 0;
        for (BatchMængde batchMængde : batchMængder) {
            mængde += batchMængde.getMængde();
        }
        return mængde;
    }

    public Fad getFad() {
        return fad;
    }

    private StringBuilder udskrivBatches(){
        StringBuilder h  = new StringBuilder();
        for (BatchMængde batchMængde : batchMængder) {
            h.append(batchMængde.getBatch().getMaltBach() + " " + batchMængde.getBatch().getKornSort() +" " + batchMængde.getBatch().getMark() + "\n");

        }
        return h;
    }


    @Override
    public String toString() {
        return  "Antal lLiter " + getSamletMængde() + "\nSamlet alkoholprocenten i Distillat " + beregnalkoholprocent() + "\nInfo omkirng indkluderet batches " + udskrivBatches();
    }
}
