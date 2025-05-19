package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Destillat implements Serializable {
    private LocalDateTime datoForPåfyldning;
    private double svind = 0;
    private double måltAlkoholprocent = -1;

    private LocalDateTime slutDato;

    //omhældning
    private LocalDateTime datoForOmhældning;

    private HashSet<Fad> tidligereFade = new HashSet<>();


    //Linkattribut
    private List<BatchMængde> batchMængder = new ArrayList<>();
    private Fad fad;

    private List<DestillatMængde> destillatMængder = new ArrayList<>();
    private List<Destillat> destillater = new ArrayList<>();



    public Destillat(LocalDateTime datoForPåfyldning, Fad fad) {
        this.datoForPåfyldning = datoForPåfyldning;
        this.fad = fad;
        fad.setDestillat(this);
    }

    public Destillat(LocalDateTime datoForOmhældning, LocalDateTime datoForPåfyldning, Fad fad, List<Destillat> destillater){
        this.datoForOmhældning = datoForOmhældning;
        this.fad = fad;
        this.destillater = destillater;
        this.datoForPåfyldning = datoForPåfyldning;
        for (Destillat d : destillater) {
            tidligereFade.addAll(d.getTidligereFade());
            tidligereFade.add(d.getFad());
        }
        this.slutDato = datoForOmhældning;
    }

    public LocalDateTime getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDateTime slutDato) {
        this.slutDato = datoForOmhældning;
    }

    public ArrayList<BatchMængde> getBatchMængder() {
        return new ArrayList<>(batchMængder);
    }

    public HashSet<Fad> getTidligereFade() {
//        return new HashSet<>(tidligereFade)
    return tidligereFade;
    }

    public ArrayList<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    public void addDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }

    public void addBatchMængde(BatchMængde batchMængde) {
        if (!batchMængder.contains(batchMængde)) {
            batchMængder.add(batchMængde);
        }
    }


    public void removeBatchMængde(BatchMængde batchMængde) {
        if (batchMængder.contains(batchMængde)) {
            batchMængder.remove(batchMængde);
        }
    }

    public double getSvind() {
        return svind;
    }

    public void setSvind(double svind) {
        this.svind = svind;
    }

    public double getMåltAlkoholProcent() {
        return måltAlkoholprocent;
    }

    public void setMåltAlkoholProcent(double måltAlkoholProcent) {
        this.måltAlkoholprocent = måltAlkoholProcent;
    }

    public ArrayList<DestillatMængde> getDestillatMængder(){
        return new ArrayList<>(destillatMængder);
    }

    public DestillatMængde createDestillatMængde(double mængde,Whiskyserie whiskyserie){
        DestillatMængde destillatMængde = new DestillatMængde(mængde,whiskyserie,this);
        destillatMængder.add(destillatMængde);
        return destillatMængde;
    }

    public void removeDestillatMængde(DestillatMængde destillatMængde){
        if (destillatMængder.contains(destillatMængde)){
            destillatMængder.remove(destillatMængde);
        }
    }

    public double beregnalkoholprocent() {

        if(måltAlkoholprocent == -1) {

            double samletrentalkoholprocent = 0;
            double samletmængde = 0;

            for (BatchMængde batchMængde : batchMængder) {
                double alkopct = batchMængde.getBatch().getAlkoholPct();
                double mængde = batchMængde.getMængde();
                samletrentalkoholprocent += mængde * alkopct / 100;
                samletmængde += mængde;
            }
            return samletrentalkoholprocent / samletmængde * 100;
        }
        return måltAlkoholprocent;
    }

    public double getSamletMængde(){
        double mængde = 0;
        for (BatchMængde batchMængde : batchMængder) {
            mængde += batchMængde.getMængde();
        }
        for (DestillatMængde destillatMængde : destillatMængder) {
            mængde -= destillatMængde.getMængde();

        }
        for (Destillat destillat: destillater){
            mængde += destillat.getSamletMængde();
        }
        return mængde - svind;
    }


    public LocalDateTime getDatoForPåfyldning() {
        return datoForPåfyldning;
    }

    public Fad getFad() {
        return fad;
    }

    public LocalDateTime getDatoForOmhældning() {
        return datoForOmhældning;
    }

    private StringBuilder udskrivBatches(){
        StringBuilder sb  = new StringBuilder();
        for (BatchMængde batchMængde : batchMængder) {
            sb.append("\nBatch id: " + batchMængde.getBatch().getBatchID() + ", maltbatch: " + batchMængde.getBatch().getMaltBach() + ", kornsort: " + batchMængde.getBatch().getKornSort() + ", mark: " + batchMængde.getBatch().getMark());

        }
        return sb;
    }

    public StringBuilder udskrivFad(){
        StringBuilder sb  = new StringBuilder();
        for (Fad fad1 : tidligereFade) {
            sb.append("Tidligere fade: ");
            sb.append("fadnr: "+ fad1.getFadNr() + ", fadtype: " + fad1.getFadtype() + "\n");

        }
        return sb;
    }

    public Set<Fad> hentAlleFade(){
        Set<Fad> alleFade = new HashSet<>();
        alleFade.add(fad);
        for(Destillat destillat : destillater){
            alleFade.addAll(destillat.hentAlleFade());
        }
        return alleFade;
    }
    public Set<Destillat> hentalleDestillater(){
        Set<Destillat> alledestillater = new HashSet<>();

        alledestillater.add(this);

        for(Destillat destillat : destillater) {
            alledestillater.addAll(destillat.hentalleDestillater());
        }
        return alledestillater;
    }

    public Set<Batch> hentAlleBatch(){
        Set<Batch> alleBatch = new HashSet<>();
        for(BatchMængde batchMængde : batchMængder){
            alleBatch.add(batchMængde.getBatch());
        }
        for(Destillat destillat : destillater) {
            alleBatch.addAll(destillat.hentAlleBatch());
        }
        return alleBatch;
    }
    public Period getTidPåNuværendeFad() {
        LocalDateTime startdato = datoForOmhældning != null ? datoForOmhældning : datoForPåfyldning;
        LocalDateTime slutdato = this.slutDato != null ? this.slutDato : LocalDateTime.now();
        return Period.between(startdato.toLocalDate(), slutdato.toLocalDate());
    }




    @Override
    public String toString() {
        return  "Antal liter " + getSamletMængde() + "\nSamlet alkoholprocenten i destillat indtilvidere " + beregnalkoholprocent() + "\nInfo omkirng indkluderet batches " + udskrivBatches() + "INFOOO FAD TJEJ" + udskrivFad();
    }


}
