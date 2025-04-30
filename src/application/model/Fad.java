package application.model;

import java.util.ArrayList;

public class Fad {
    private static int fadNr;
    private double fadStørrelse;
    private String levarandør;
    private boolean erBrugt;
    private Fadtype fadtype;
    private Træsort træsort;
    private int antalGangeBrugt;
    // linkattribut til Batch
    private ArrayList<Batch> batches = new ArrayList<>();


    public Fad(double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt) {
        fadNr++;
        this.fadStørrelse = fadStørrelse;
        this.levarandør = levarandør;
        this.erBrugt = erBrugt;
        this.fadtype = fadtype;
        this.træsort = træsort;
        this.antalGangeBrugt = antalGangeBrugt;
    }

    //Metoder til batches
    public  ArrayList<Batch> getBatches(){
        return new ArrayList<>(batches);
    }
    public void addBatch(Batch batch){
        if (!batches.contains(batch)){
            batches.add(batch);
        }
    }

    public void remodeBatch(Batch batch){
        if (batches.contains(batch)){
            batches.remove(batch);
        }
    }

    //get og set metoder


    public int getFadNr() {
        return fadNr;
    }

    public void setFadNr(int fadNr) {
        this.fadNr = fadNr;
    }

    public double getFadStørrelse() {
        return fadStørrelse;
    }

    public void setFadStørrelse(double fadStørrelse) {
        this.fadStørrelse = fadStørrelse;
    }

    public String getLevarandør() {
        return levarandør;
    }

    public void setLevarandør(String levarandør) {
        this.levarandør = levarandør;
    }

    public boolean isErBrugt() {
        return erBrugt;
    }

    public void setErBrugt(boolean erBrugt) {
        this.erBrugt = erBrugt;
    }

    public Fadtype getFadtype() {
        return fadtype;
    }

    public void setFadtype(Fadtype fadtype) {
        this.fadtype = fadtype;
    }

    public Træsort getTræsort() {
        return træsort;
    }

    public void setTræsort(Træsort træsort) {
        this.træsort = træsort;
    }

    public int getAntalGangeBrugt() {
        return antalGangeBrugt;
    }

    public void setAntalGangeBrugt(int antalGangeBrugt) {
        this.antalGangeBrugt = antalGangeBrugt;
    }



    @Override
    public String toString() {
        return "Fad{" +
                "fadStørrelse=" + fadStørrelse +
                ", levarandør='" + levarandør + '\'' +
                ", erBrugt=" + erBrugt +
                ", fadtype=" + fadtype +
                ", antalGangeBrugt=" + antalGangeBrugt +
                ", træsort=" + træsort +
                '}';
    }
}
