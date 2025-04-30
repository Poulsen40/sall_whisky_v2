package application.controller;

import application.model.*;
import storage.Storage;

public class Controller {

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

    public static Lager createLager(int rækker, int hylder, int plads, String navn){
        Lager lager = new Lager(rækker, hylder, plads, navn);
        Storage.addLager(lager);
        return lager;
    }
}
