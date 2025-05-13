package application.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Destillat {
    private LocalDateTime datoForPåfyldning;
    private double alkoholPct;

    //Linkattribut
    private List<BatchMængde> batchMængder = new ArrayList<>();
    private Fad fad;

    private ArrayList<DestillatMængde> destillatMængder = new ArrayList<>();


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

    public ArrayList<DestillatMængde> getDestillatMængder(){
        return new ArrayList<>(destillatMængder);
    }

    public DestillatMængde createDestillatMængde(double mængde,Whiskyserie whiskyserie){
        DestillatMængde destillatMængde = new DestillatMængde(mængde,whiskyserie,this);
        destillatMængder.add(destillatMængde);
        return destillatMængde;
    }

    public void removeDestillatMængde(DestillatMængde destillatMængde){
        if (destillatMængder.contains(destillatMængde)){
            destillatMængder.remove(destillatMængde);
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
        for (DestillatMængde destillatMængde : destillatMængder) {
            mængde -= destillatMængde.getMængde();

        }
        return mængde;
    }


    public LocalDateTime getDatoForPåfyldning() {
        return datoForPåfyldning;
    }

    public Fad getFad() {
        return fad;
    }

    private StringBuilder udskrivBatches(){
        StringBuilder sb  = new StringBuilder();
        for (BatchMængde batchMængde : batchMængder) {
            sb.append("\nBatch id: " + batchMængde.getBatch().getBatchID() + ", maltbatch: " + batchMængde.getBatch().getMaltBach() + ", kornsort: " + batchMængde.getBatch().getKornSort() + ", mark: " + batchMængde.getBatch().getMark());

        }
        return sb;
    }


    @Override
    public String toString() {
        return  "Antal liter " + getSamletMængde() + "\nSamlet alkoholprocenten i Distillat " + beregnalkoholprocent() + "\nInfo omkirng indkluderet batches " + udskrivBatches();
    }



}
