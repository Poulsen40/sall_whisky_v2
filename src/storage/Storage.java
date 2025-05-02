package storage;

import application.model.Batch;
import application.model.Fad;
import application.model.Lager;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private List<Batch> batches = new ArrayList<>();
    private List<Fad> fade = new ArrayList<>();
    private List<Lager> lagere = new ArrayList<>();

    //-----------------------------------------------------------------------------

    public List<Batch> getBatches() {
        return new ArrayList<Batch>(batches);
    }
    public void addBatch(Batch batch) {
        batches.add(batch);
    }
    public void removeBatch(Batch batch) {
        batches.remove(batch);
    }

    //-----------------------------------------------------------------------------

    public List<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }
    public void addFad(Fad fad) {
        fade.add(fad);
    }
    public void removeFad(Fad fad) {
        fade.remove(fad);
    }

    //------------------------------------------------------------------------------

    public List<Lager> getLager() {
        return new ArrayList<>(lagere);
    }
    public void addLager(Lager lager){
        lagere.add(lager);

    }
    public void removeLager(Lager lager) {
        lagere.remove(lager);
    }

    //----------------------------------------------------------------------------------



}
