package storage;

import application.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Storage implements Serializable {

    private ArrayList<Batch> batches = new ArrayList<>();
    private ArrayList<Fad> fade = new ArrayList<>();
    private ArrayList<Lager> lagere = new ArrayList<>();
    private ArrayList<Destillat> destillater = new ArrayList<>();
    private ArrayList<Whiskyprodukt> whiskyprodukter = new ArrayList<>();
    private ArrayList<Whiskyserie> whiskyserier = new ArrayList<>();
    private int[] idArray = new int[] {1,1,1};




    //-----------------------------------------------------------------------------

    public ArrayList<Batch> getBatches() {
        return new ArrayList<Batch>(batches);
    }
    public void addBatch(Batch batch) {
        batches.add(batch);
    }
    public void removeBatch(Batch batch) {
        batches.remove(batch);
    }

    //-----------------------------------------------------------------------------

    public ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }
    public void addFad(Fad fad) {
        fade.add(fad);
    }
    public void removeFad(Fad fad) {
        fade.remove(fad);
    }

    //------------------------------------------------------------------------------

    public ArrayList<Lager> getLager() {
        return new ArrayList<>(lagere);
    }
    public void addLager(Lager lager){
        lagere.add(lager);

    }
    public void removeLager(Lager lager) {
        lagere.remove(lager);
    }

    //----------------------------------------------------------------------------------
    public ArrayList<Destillat> getDestillater() {
        return new ArrayList<Destillat>(destillater);
    }
    public void addDestillat(Destillat destillat) {
        destillater.add(destillat);
    }
    public void removeDestillat(Destillat destillat) {
        destillater.remove(destillat);
    }
    //----------------------------------------------------------------------------------
    public ArrayList<Whiskyprodukt> getWhiskyprodukter() {
        return new ArrayList<Whiskyprodukt>(whiskyprodukter);
    }
    public void addWhiskyprodukt(Whiskyprodukt whiskyprodukt) {
        whiskyprodukter.add(whiskyprodukt);
    }
    public void removeWhiskyprodukt(Whiskyprodukt whiskyprodukt) {
        whiskyprodukter.remove(whiskyprodukt);
    }

    //-----------------------------------------------------------------------------

    public ArrayList<Whiskyserie> getWhiskyserier() {
        return new ArrayList<Whiskyserie>(whiskyserier);
    }
    public void addWhiskyserie(Whiskyserie whiskyserie) {
        whiskyserier.add(whiskyserie);
    }
    public void removeWhiskyserie(Whiskyserie whiskyserie) {
        whiskyserier.remove(whiskyserie);
    }


    //-----------------------------------------------------------------------------

    public int batchId() {
        int id = idArray[0];
        idArray[0]++;
        return id;
    }

    public int fadId(){
        int id = idArray[1];
        idArray[1]++;
        return id;
    }

    public int whiskeyproduktId(){
        int id = idArray[2];
        idArray[2]++;
        return id;
    }

}
