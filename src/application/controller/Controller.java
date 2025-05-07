package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {


    //skal bruges til load og save
//    private static Storage storage;
//
//
//    public static void setStorage(Storage storage){
//        Controller.storage = storage;
//    }


    public static Batch createBatch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar, Rygemateriale rygemateriale) {
        Batch batch = new Batch(maltBach, kornSort, mark, mængdeVæske, alkoholPct, kommentar, rygemateriale);
        Storage.addBatch(batch);
        return batch;
    }

    public static Fad createFad(double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt) {
        Fad fad = new Fad(fadStørrelse, levarandør, erBrugt, fadtype, træsort, antalGangeBrugt);
        Storage.addFad(fad);
        return fad;
    }

    public static BatchMængde createBatchMængde(double mængde, Destillat destillat, Batch batch) {
        BatchMængde batchMængde = batch.createBatchMængde(mængde, destillat);
        return batchMængde;
    }

    public static Destillat createDestilat(LocalDateTime datoForPåfyldning, Fad fad) {
        Destillat destillat = new Destillat(datoForPåfyldning, fad);
        Storage.addDestillat(destillat);
        return destillat;
    }

    public static Whiskyserie createWhiskyserie(String serieNavn, LocalDate dato) {
        Whiskyserie whiskyserie = new Whiskyserie(serieNavn, dato);
        return whiskyserie;
    }

    public static DestillatMængde createDestillatMængde(double mængde, Whiskyserie whiskyserie, Destillat destillat) {
        DestillatMængde destillatMængde = destillat.createDestillatMængde(mængde, whiskyserie);
        return destillatMængde;
    }

    public static Whiskyprodukt createWhiskyprodukt(Whiskyserie whiskyserie) {
        Whiskyprodukt whiskyprodukt = whiskyserie.createWhiskyprodukt();
        return whiskyprodukt;
    }


    public static void fjernDestillat(Destillat destillat) {
        Storage.removeDestillat(destillat);
    }

    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }

    public static ArrayList<Lager> getlagere() {
        return Storage.getLager();
    }

    public static ArrayList<Batch> getBatch() {
        return Storage.getBatches();
    }

    public static ArrayList<Destillat> getDestillater() {
        return Storage.getDestillater();
    }

    /**
     * Præbetingelse
     * rækker >= 0
     * hylder >= 0
     * plads >= 0
     */

    public static Lager createLager(int rækker, int hylder, int plads, String navn) {
        Lager lager = new Lager(rækker, hylder, plads, navn);
        Storage.addLager(lager);
        return lager;
    }

    //addfadtillager skal vide hvilket lager og fad brugeren har valgt. Dvs i DesitllatOgLager,
    //skal det fastholdes hvilket fad der klikkes på i listviewet i vælgFad().
    //Tilsvarende skal det fastholdes hvilket lager brugeren vælger i listviewet.
    //Metoden addFadTilLager skal lave relationen mellem fad og lager, og det kan kun gøres hvis den kender det valgte fad og lager.
    //Jeg kan ikke se hvor der bliver lavet en setSelection i viewet(måske er det noget java gør for en).
    public static String addFadTilLager(Fad fad, Lager lager) {
        String placering = fad.tilføjTilLager(lager);
        lager.setAntalledigepladser(lager.getAntalledigepladser() - 1);

        return placering;
    }

    public static Batch getbatch(BatchMængde batchMængde) {
        return batchMængde.getBatch();
    }

    public static double getMængdeVæske(BatchMængde batchMængde) {
        return batchMængde.getMængde();

    }

    public static double getMængdeVæske(Batch batch) {
        return batch.getMængdeVæske();
    }

    public static void setDestillatFad(Fad fad, Destillat destillat) {
        fad.setDestillat(destillat);
    }

    public static ArrayList<BatchMængde> getBatchMængder(Destillat destillat) {
        return destillat.getBatchMængder();
    }

    public static Destillat getDestillat(Fad fad) {
        return fad.getDestillat();
    }

    public static double getFadStørrelse(Fad fad) {
        return fad.getFadStørrelse();
    }

    public static void addBatchMængde(BatchMængde batchMængde, Destillat destillat) {
        destillat.addBatchMængde(batchMængde);
    }

    public static void setMængdeVæske(Batch batch, double nyBatchInfo) {
        batch.setMængdeVæske(nyBatchInfo);
    }

    public static double getSamletMængde(Destillat destillat) {
        return destillat.getSamletMængde();
    }

    public static String destillatToString(Destillat destillat) {
        return destillat.toString();
    }

    public static void addDestillatMængde(DestillatMængde destillatMængde, Whiskyserie whiskyserie) {
        whiskyserie.addDestillatMængde(destillatMængde);
    }

    public static double samletMængdeWhiskySerie(Whiskyserie whiskyserie) {
        double samletMængde = 0;
        for (DestillatMængde d : whiskyserie.getDestillatMængder()) {
            samletMængde += d.getMængde();
        }
        return samletMængde;
    }

    public static double antalForventetFlakser(Whiskyserie whiskyserie, double mængdeVæske) {
        double flaskeStørrelse = 0.70;
        double forventetAntal = mængdeVæske / flaskeStørrelse;
        return Math.floor(forventetAntal);
    }

    public static void tælAntalGangeBrugt(Fad fad){
        if (fad != null){
            fad.setAntalGangeBrugt(fad.getAntalGangeBrugt() + 1);
        }
    }

    public static void nulstilAntalgangeBrugt(Fad fad, int oprindeligVærdi){
        if (fad != null){
            fad.setAntalGangeBrugt(oprindeligVærdi);
        }
    }



    public static String toStringFadOgDestillat(Fad fad) {
        StringBuilder h = new StringBuilder();

        h.append("Fad nr: " + fad.getFadNr() + ", fadstørrelse: " + fad.getFadStørrelse() + ", fadtype: " + fad.getFadtype() + ", leverandør: " + fad.getLevarandør() + ", gange brugt: " + fad.getAntalGangeBrugt());
        h.append("\nDestilat alkoholpct: " + fad.getDestillat().beregnalkoholprocent() + ", samlet mængde væske: " + Controller.getSamletMængde(fad.getDestillat()));
        h.append("\nBatches brugt i destillat:");
        for (BatchMængde batchMængde : fad.getDestillat().getBatchMængder()) {
            h.append(" id: " + batchMængde.getBatch().getBatchID() + " mængde: " + batchMængde.getMængde());
        }
        return h.toString();
    }



    public static List<Fad> fadsøgning(double minfadstørrelse, double maxfadstørrelse, int minAlder, int maxAlder,
                                       List<Fadtype> fadTyper, List<String> leverandør, int minBrugt, int maxBrugt,
                                       List<Træsort> træsortList, List<Lager> lagerList, boolean skalVæreFyldt) {
        List<Fad> fade = Storage.getFade();

        // Den her bruger jeg senere til og tjekke for alderen på væsken på fadet
        LocalDateTime nu = LocalDateTime.now();

        return fade.stream()
                // Filtrerer efter fadtype, hvis angivet
                .filter(f -> fadTyper == null || fadTyper.isEmpty() || fadTyper.contains(f.getFadtype()))
                // Filtrerer efter fadstørrelse
                .filter(f -> f.getFadStørrelse() >= minfadstørrelse && f.getFadStørrelse() <= maxfadstørrelse)
                // Filtrerer efter alder, men tjekker først om der er destillat på fadet
                .filter(f -> {
                    // Hvis vi ikke kræver fyldte fade og fadet er tomt, så skal det inkluderes uanset alder
                    if (!skalVæreFyldt && f.getDestillat() == null) {
                        return true;
                    }
                    // Hvis fadet har destillat, tjek alderen
                    if (f.getDestillat() != null) {
                        long alder = ChronoUnit.YEARS.between(f.getDestillat().getDatoForPåfyldning(), nu);
                        return alder >= minAlder && alder <= maxAlder;
                    }
                    // Hvis vi kræver fyldte fade og fadet er tomt, så skal det ikke inkluderes
                    return false;
                })
                // Filtrerer efter leverandør, hvis angivet
                .filter(f -> leverandør == null || leverandør.isEmpty() || leverandør.contains(f.getLevarandør()))
                // Filtrerer efter træsort, hvis angivet
                .filter(f -> træsortList == null || træsortList.isEmpty() || træsortList.contains(f.getTræsort()))
                // Filtrerer efter lager, hvis angivet
                .filter(f -> lagerList == null || lagerList.isEmpty() || lagerList.contains(f.getLager()))
                // Filtrerer efter antal gange brugt
                .filter(f -> f.getAntalGangeBrugt() >= minBrugt && f.getAntalGangeBrugt() <= maxBrugt)
                // Filtrerer efter om fadet skal være fyldt
                .filter(f -> !skalVæreFyldt || f.getDestillat() != null)
                .collect(Collectors.toList());
    }
}

