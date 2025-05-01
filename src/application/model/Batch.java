package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Batch {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltBach;
    private String kornSort;
    private String mark;
    private double mængdeVæske;
    private double alkoholPct;
    private String kommentar;
    private Rygemateriale rygemateriale;

    //Linkattribut
    private ArrayList<BatchMængde> batchMængder = new ArrayList<>();


    public Batch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar, Rygemateriale rygemateriale) {
        this.maltBach = maltBach;
        this.kornSort = kornSort;
        this.mark = mark;
        this.mængdeVæske = mængdeVæske;
        this.alkoholPct = alkoholPct;
        this.kommentar = kommentar;
        this.rygemateriale = rygemateriale;
        startDato = LocalDate.now();
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
    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public String getMaltBach() {
        return maltBach;
    }

    public void setMaltBach(String maltBach) {
        this.maltBach = maltBach;
    }

    public String getKornSort() {
        return kornSort;
    }

    public void setKornSort(String kornSort) {
        this.kornSort = kornSort;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public void setAlkoholPct(double alkoholPct) {
        this.alkoholPct = alkoholPct;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
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
                ", kommentar='" + kommentar + '\'' +
                '}';
    }
}
