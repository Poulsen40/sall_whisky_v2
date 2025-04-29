package storage;

import application.model.Batch;
import application.model.Fad;

import java.util.ArrayList;
import java.util.Base64;

public class Storage {

    private static ArrayList<Batch> batches = new ArrayList<>();
    private static ArrayList<Fad> fade = new ArrayList<>();

    //-----------------------------------------------------------------------------
    public static ArrayList<Batch> getBatches() {
        return new ArrayList<Batch>(batches);
    }

    public static void addBatch(Batch batch) {
        batches.add(batch);
    }
    public static void removeBatch(Batch batch) {
        batches.remove(batch);
    }
    //-----------------------------------------------------------------------------
    public static ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }

    public static void addFad(Fad fad) {
        fade.add(fad);
    }
    public static void removeFad(Fad fad) {
        fade.remove(fad);
    }

}
