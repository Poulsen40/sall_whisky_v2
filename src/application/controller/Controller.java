package application.controller;

import application.model.Batch;
import application.model.Fad;
import application.model.Fadtype;
import application.model.Træsort;
import storage.Storage;

public class Controller {

    public static Batch createBatch(String maltBach, String kornSort, String mark, double mængdeVæske, double alkoholPct, String kommentar) {
        Batch batch = new Batch(maltBach, kornSort, mark, mængdeVæske, alkoholPct, kommentar);
        Storage.addBatch(batch);
        return batch;
    }

    public static Fad createFad(int fadNr, double fadStørrelse, String levarandør, boolean erBrugt, Fadtype fadtype, Træsort træsort, int antalGangeBrugt, double literPåfyld) {
        Fad fad = new Fad(fadNr, fadStørrelse, levarandør, erBrugt, fadtype, træsort, antalGangeBrugt, literPåfyld);
        Storage.addFad(fad);
        return fad;
    }
}
