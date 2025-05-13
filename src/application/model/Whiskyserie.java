package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Whiskyserie {
    private String serieNavn;
    private double alkoholPct;
    private double størrelse;
    private double vandMængde;
    private LocalDate dato;
    private double antalFlasker;
    private WhiskyType whiskyType;

    private List<Whiskyprodukt> whiskyprodukter = new ArrayList<>();

    private List<DestillatMængde> destillatMængder = new ArrayList<>();


    public Whiskyserie(String serieNavn, LocalDate dato) {
        this.serieNavn = serieNavn;
        this.dato = dato;
    }

    public ArrayList<DestillatMængde> getDestillatMængder() {
        return new ArrayList<>(destillatMængder);
    }

    public void addDestillatMængde(DestillatMængde destillatMængde){
        if (!destillatMængder.contains(destillatMængde)){
            destillatMængder.add(destillatMængde);
        }
    }

    public void removeDestillatMængde(DestillatMængde destillatMængde){
        if (destillatMængder.contains(destillatMængde)){
            destillatMængder.remove(destillatMængde);
        }
    }


    public ArrayList<Whiskyprodukt> getWhiskyprodukter(){
        return new ArrayList<>(whiskyprodukter);
    }

    public Whiskyprodukt createWhiskyprodukt(double flaskeStr){
        Whiskyprodukt whiskyprodukt = new Whiskyprodukt(this,flaskeStr);
        whiskyprodukter.add(whiskyprodukt);
        return whiskyprodukt;
    }

    public void removeWhiskyprodukt(Whiskyprodukt whiskyprodukt){
        if (whiskyprodukter.contains(whiskyprodukt)){
            whiskyprodukter.remove(whiskyprodukt);
        }
    }

    public String getSerieNavn() {
        return serieNavn;
    }

    public double getAlkoholPct() {
        return alkoholPct;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public void setAntalFlasker(double antalFlasker) {
        this.antalFlasker = antalFlasker;
    }


    public void setSerieNavn(String serieNavn) {
        this.serieNavn = serieNavn;
    }

    public void setAlkoholPct(double alkoholPct) {
        this.alkoholPct = alkoholPct;
    }

    public void setStørrelse(double størrelse) {
        this.størrelse = størrelse;
    }

    public void setVandMængde(double vandMængde) {
        this.vandMængde = vandMængde;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    public LocalDate getDato() {
        return dato;
    }

    public WhiskyType getWhiskyType() {
        return whiskyType;
    }

    public void setWhiskyType(WhiskyType whiskyType) {
        this.whiskyType = whiskyType;
    }

    public double getAntalFlasker() { return antalFlasker; }

    @Override
    public String toString() {
        return "Whiskyserie{" +
                "serieNavn='" + serieNavn + '\'' +
                ", alkoholPct=" + alkoholPct + ", vandmængde= " + vandMængde +
                ", størrelse=" + størrelse +
                ", dato=" + dato + ", antal flasker=" + antalFlasker + ", whiskytype= " + whiskyType +
                '}';
    }
}
