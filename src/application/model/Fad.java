package application.model;

import java.io.Serializable;

public class Fad implements Serializable {
    private int fadNr;
    private static int fadID = 1;
    private double fadStørrelse;
    private String levarandør;
    private boolean erBrugt;
    private Fadtype fadtype;
    private Træsort træsort;
    private int antalGangeBrugt;
    private String placeringPåLager;
    // linkattribut til destilat
    private Destillat destillat;
    //linkattribut til lager
    private Lager lager;


    public Fad(double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt) {
        this.fadStørrelse = fadStørrelse;
        this.levarandør = levarandør;
        this.erBrugt = erBrugt;
        this.fadtype = fadtype;
        this.træsort = træsort;
        this.antalGangeBrugt = antalGangeBrugt;
        fadNr = fadID++;
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

    //Metoder til Lager

    public void setLager(Lager lager) {
        if (this.lager != lager) {
            this.lager = lager;
        }
    }

    public void fjernFraLager() {
        if(this.getLager() != null) {
            if (destillat == null || destillat.getSamletMængde() == 0) {
                lager.fjernFadFraObevaringsplads(this);
                placeringPåLager = "";
                lager = null;
            } else {
                throw new RuntimeException("Fadet kan ikke fjernes fra lageret da der stadig er noget destilliatmængde på den");
            }
        }
    }

    public String tilføjTilLager(Lager lager) {
        if (lager.getErFyldt()) {
            throw new IllegalStateException("Lageret er fyldt "); // skal den være i lagers metode?(tilføjFadTilobevaringsplads)
        }
        String placering;
        setLager(lager);
        placering = lager.tilføjFadTilobevaringsplads(this);
        placeringPåLager = placering;
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

    public String getLevarandør() {
        return levarandør;
    }

    public boolean isErBrugt() {
        return erBrugt;
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

    public int getAntalGangeBrugt() {
        return antalGangeBrugt;
    }

    public void setAntalGangeBrugt(int antalGangeBrugt) {
        this.antalGangeBrugt = antalGangeBrugt;
    }

    public double getLiterPåfyldt() {
        if(destillat != null) {
            return destillat.getSamletMængde();
        }
        return 0;
    }

    public Lager getLager() {
        return lager;
    }

    public String getPlaceringPåLager() {
        return placeringPåLager;
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
