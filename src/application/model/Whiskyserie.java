package application.model;

import com.sun.source.tree.NewClassTree;

import java.time.LocalDate;
import java.util.ArrayList;

public class Whiskyserie {
    private String serieNavn;
    private double alkoholPct;
    private double størrelse;
    private double vandMængde;
    private Fad Fad;
    private LocalDate dato;



    private ArrayList<Whiskyprodukt> whiskyprodukter = new ArrayList<>();

    private ArrayList<DestillatMængde> destillatMængder = new ArrayList<>();



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

    public Whiskyprodukt createWhiskyprodukt(){
        Whiskyprodukt whiskyprodukt = new Whiskyprodukt(this);
        whiskyprodukter.add(whiskyprodukt);
        return whiskyprodukt;
    }

    public void removeWhiskyprodukt(Whiskyprodukt whiskyprodukt){
        if (whiskyprodukter.contains(whiskyprodukt)){
            whiskyprodukter.remove(whiskyprodukt);
        }
    }


}
