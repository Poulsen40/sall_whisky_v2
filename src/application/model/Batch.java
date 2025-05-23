package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Batch implements Serializable {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltBach;
    private String kornSort;
    private String mark;
    private double mængdeVæske;
    private double alkoholPct;
    private String kommentar;
    private Rygemateriale rygemateriale;
    private int batchID;
    private static int batchNr = 1;

    //Linkattribut
    private List<BatchMængde> batchMængder = new ArrayList<>();

    public Batch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar, Rygemateriale rygemateriale, LocalDate slutDato) {
        this.maltBach = maltBach;
        this.kornSort = kornSort;
        this.mark = mark;
        this.mængdeVæske = mængdeVæske;
        this.alkoholPct = alkoholPct;
        this.kommentar = kommentar;
        this.rygemateriale = rygemateriale;
        startDato = LocalDate.now();
        this.slutDato = slutDato;
        batchID = batchNr++;
    }

    public ArrayList<BatchMængde> getBatchMængder(){
        return new ArrayList<>(batchMængder);
    }

    public BatchMængde createBatchMængde(double mængde, Destillat destillat){
        BatchMængde batchMængde = new BatchMængde(mængde,destillat,this);
        batchMængder.add(batchMængde);
        return batchMængde;
    }

    public void removeBatchMængde(BatchMængde batchMængde){
        if (batchMængder.contains(batchMængde)){
            batchMængder.remove(batchMængde);
        }
    }

    //Get og set

    public Rygemateriale getRygemateriale() {
        return rygemateriale;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public String getMaltBach() {
        return maltBach;
    }

    public String getKornSort() {
        return kornSort;
    }

    public String getMark() {
        return mark;
    }

    public double getMængdeVæske() {
        return mængdeVæske;
    }

    public void setMængdeVæske(double mængdeVæske) {
        this.mængdeVæske = mængdeVæske;
    }

    public double getAlkoholPct() {
        return alkoholPct;
    }

    public int getBatchID() {
        return batchID;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "rygemateriale=" + rygemateriale +
                ", alkoholPct=" + alkoholPct +
                ", mark='" + mark + '\'' +
                ", maltBach='" + maltBach + '\'' +
                ", kornSort='" + kornSort + '\'' +
                ", mængdeVæske=" + mængdeVæske +
                ", kommentar='" + kommentar + ", batchID= " + batchID+
                '}';
    }
}
