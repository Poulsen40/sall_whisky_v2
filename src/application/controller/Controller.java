package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public static void setDestillatFad(Fad fad, Destillat destillat){
        fad.setDestillat(destillat);
    }

    public static ArrayList<BatchMængde> getBatchMængder(Destillat destillat) {
        return destillat.getBatchMængder();
    }

    public static Destillat getDestillat(Fad fad) { return fad.getDestillat(); }

    public static double getFadStørrelse(Fad fad) { return fad.getFadStørrelse(); }

    public static void addBatchMængde(BatchMængde batchMængde, Destillat destillat) {destillat.addBatchMængde(batchMængde);}

    public static void setMængdeVæske(Batch batch,double nyBatchInfo) { batch.setMængdeVæske(nyBatchInfo);}

    public static double getSamletMængde(Destillat destillat) { return destillat.getSamletMængde();}

    public static String destillatToString(Destillat destillat) { return destillat.toString();}


}

