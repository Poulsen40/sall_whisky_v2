package application.model;

import java.time.LocalDate;

public class Batch {
    private LocalDate startDato;
    private LocalDate slutDato;
    private String maltBach;
    private String kornSort;
    private String mark;
    private double mængdeVæske;
    private double alkoholPct;
    private String kommentar;


    public Batch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar) {
        this.maltBach = maltBach;
        this.kornSort = kornSort;
        this.mark = mark;
        this.mængdeVæske = mængdeVæske;
        this.alkoholPct = alkoholPct;
        this.kommentar = kommentar;
        startDato = LocalDate.now();
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
}
