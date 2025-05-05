package application.model;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class Fad {
    private static int fadNr;
    private double fadStørrelse;
    private String levarandør;
    private boolean erBrugt;
    private Fadtype fadtype;
    private Træsort træsort;
    private int antalGangeBrugt;
    private double literPåfyldt;
    // linkattribut til destilat
    private Destillat destillat;
    //linkattribut til lager
    private Lager lager;


    public Fad(double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt) {
        fadNr++;
        this.fadStørrelse = fadStørrelse;
        this.levarandør = levarandør;
        this.erBrugt = erBrugt;
        this.fadtype = fadtype;
        this.træsort = træsort;
        this.antalGangeBrugt = antalGangeBrugt;
    }

    //Metoder til destillat

    public Destillat getDestillat() {
        return destillat;
    }

    /**
     * Sætter fadets destillat
     * Pre: Fadet skal være tomt
     */
    public void setDestillat(Destillat destillat) {
        this.destillat = destillat;
    }

        /*Der skal laves en metode til at tømme et fad når Use case med at lave wiskey laves.
        Det er nok noget med at destilatklassen har en metode som kader den der er her som så
        fjerner destilatet på fadet. André
         */


    //Metoder til Lager

    public void setLager(Lager lager) {
        if (this.lager != lager) {
            this.lager = lager;
        }
    }

    public void fjernFraLager() {
        //TODO
    }

    public String tilføjTilLager(Lager lager) {
        if(lager.getErFyldt()) {
            throw new IllegalStateException("Lageret er fyldt "); // skal den være i lagers metode?(tilføjFadTilobevaringsplads)
        }
        String placering;
        setLager(lager);
       placering = lager.tilføjFadTilobevaringsplads(this);
       return placering;
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

    public double getLiterPåfyldt() {
        return literPåfyldt;
    }

    public void setLiterPåfyldt(double literPåfyldt) {
        this.literPåfyldt = literPåfyldt;
    }

    public Lager getLager() {
        return lager;
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
