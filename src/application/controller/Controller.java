package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDateTime;
import java.util.List;

public class Controller {

    private static Storage storage;


    public static void setStorage(Storage storage){
        Controller.storage = storage;
    }

    public static Batch createBatch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar, Rygemateriale rygemateriale) {
        Batch batch = new Batch(maltBach, kornSort, mark, mængdeVæske, alkoholPct, kommentar, rygemateriale);
        storage.addBatch(batch);
        return batch;
    }

    public static Fad createFad(double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt) {
        Fad fad = new Fad(fadStørrelse, levarandør, erBrugt, fadtype, træsort, antalGangeBrugt);
        storage.addFad(fad);
        return fad;
    }

    public static BatchMængde createBatchMængde(double mængde, Destillat destillat, Batch batch) {
        BatchMængde batchMængde = batch.createBatchMængde(mængde, destillat);
        return batchMængde;
    }

    public static Destillat createDestilat(LocalDateTime datoForPåfyldning, Fad fad) {
        Destillat destillat =  new Destillat(datoForPåfyldning, fad);
        return destillat;
    }

    public static List<Fad> getFade() {
        return storage.getFade();
    }

    public static List<Batch> getBatch() {
        return storage.getBatches();
    }

    public static ArrayList<Lager> getlagere(){
        return Storage.getLager();
    }



    /**
     * Præbetingelse
     * rækker >= 0
     * hylder >= 0
     * plads >= 0
     */

    public static Lager createLager(int rækker, int hylder, int plads, String navn){
        Lager lager = new Lager(rækker, hylder, plads, navn);
        storage.addLager(lager);
        return lager;
    }
}
