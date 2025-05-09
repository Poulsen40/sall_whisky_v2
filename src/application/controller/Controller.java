package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
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
        Storage.addWhiskyserie(whiskyserie);
        return whiskyserie;
    }

    public static DestillatMængde createDestillatMængde(double mængde, Whiskyserie whiskyserie, Destillat destillat) {
        DestillatMængde destillatMængde = destillat.createDestillatMængde(mængde, whiskyserie);
        return destillatMængde;
    }

    public static Whiskyprodukt createWhiskyprodukt(Whiskyserie whiskyserie, double flaskeStr) {
        Whiskyprodukt whiskyprodukt = whiskyserie.createWhiskyprodukt(flaskeStr);
        Storage.addWhiskyprodukt(whiskyprodukt);
        return whiskyprodukt;
    }

    public static void removeDestilatMængderFraWhiskyserie(Whiskyserie whiskyserie, ArrayList<DestillatMængde> destillatMængder){
        for (DestillatMængde d : destillatMængder) {
            whiskyserie.removeDestillatMængde(d);
        }
    }


    public static void fjernDestillat(Destillat destillat) {
        Storage.removeDestillat(destillat);
    }

    public static void fjernWhiskyserie(Whiskyserie whiskyserie) {
        Storage.removeWhiskyserie(whiskyserie);
    }


    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }

    public static ArrayList<Lager> getlagere() {
        return Storage.getLager();
    }

    public static ArrayList<Batch> getBatches() {
        return Storage.getBatches();
    }

    public static ArrayList<Destillat> getDestillater() {
        return Storage.getDestillater();
    }

    public static ArrayList<Whiskyserie> getWhiskyserie(){return Storage.getWhiskyserier();}

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

    public static String addFadTilLager(Fad fad, Lager lager) {
        String placering = fad.tilføjTilLager(lager);
        lager.setAntalledigepladser(lager.getAntalledigepladser() - 1);

        tælAntalGangeBrugt(fad);
        return placering;
    }

    public static Batch getbatch(BatchMængde batchMængde) {
        return batchMængde.getBatch();
    }

    public static Destillat getDestillat(DestillatMængde destillatMængde) {
        return destillatMængde.getDestillat();
    }

    public static double getMængdeVæske(BatchMængde batchMængde) {
        return batchMængde.getMængde();

    }

    public static double getMængdeVæske(Batch batch) {
        return batch.getMængdeVæske();
    }

    public static double getMængdeVæskePåDestillatMængde(DestillatMængde destillatMængde) {
        return destillatMængde.getMængde();
    }


    public static void setDestillatFad(Fad fad, Destillat destillat) {
        System.out.println("antal gange før opdatering: " + fad.getAntalGangeBrugt());
        fad.setDestillat(destillat);
        System.out.println("antal gange før opdatering: " + fad.getAntalGangeBrugt());
    }

    public static void removeDestillatMængdeFraDestillat(Destillat destillat, DestillatMængde destillatMængde){
        destillat.removeDestillatMængde(destillatMængde);
    }



    public static ArrayList<BatchMængde> getBatchMængder(Destillat destillat) {
        return destillat.getBatchMængder();
    }

    public static ArrayList<DestillatMængde> getDestillatmængder(Whiskyserie whiskyserie) {
        return whiskyserie.getDestillatMængder();
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

    public static double samletMængdeWhiskySerie(Whiskyserie whiskyserie, double vandmængde) {
        double samletMængde = 0;
        for (DestillatMængde d : whiskyserie.getDestillatMængder()) {
            samletMængde += d.getMængde();
        }
        return samletMængde + vandmængde;
    }

    public static double antalForventetFlakser(Whiskyserie whiskyserie, double mængdeVæske) {
        double flaskeStørrelse = 0.70;
        double forventetAntal = mængdeVæske / flaskeStørrelse;
        return Math.floor(forventetAntal);
    }

    public static void tælAntalGangeBrugt(Fad fad) {
        if (fad != null) {
            System.out.println("antal gange før opdatering: " + fad.getAntalGangeBrugt());
            fad.setAntalGangeBrugt(fad.getAntalGangeBrugt() + 1);
            System.out.println("antal gange før opdatering: " + fad.getAntalGangeBrugt());

        }
    }
    public static void nulstilAntalgangeBrugt(Fad fad, int oprindeligVærdi) {
        if (fad != null) {
            fad.setAntalGangeBrugt(oprindeligVærdi);
        }
    }

    public static ArrayList<Whiskyprodukt> getWhiskyprodukter(){
        return Storage.getWhiskyprodukter();


    }

    public static String toStringFadOgDestillat(Destillat destillat) {
        StringBuilder h = new StringBuilder();
        Fad fad = destillat.getFad();

        h.append("Fad nr: " + fad.getFadNr() + ", fadstørrelse: " + fad.getFadStørrelse() + ", fadtype: " + fad.getFadtype() + ", leverandør: " + fad.getLevarandør() + ", gange brugt: " + fad.getAntalGangeBrugt());
        h.append("\nDestilat alkoholpct: " + fad.getDestillat().beregnalkoholprocent() + ", samlet mængde væske: " + Controller.getSamletMængde(fad.getDestillat()));
        h.append("\nBatches brugt i destillat:");
        for (BatchMængde batchMængde : destillat.getBatchMængder()) {
            h.append(" id: " + batchMængde.getBatch().getBatchID() + " mængde: " + batchMængde.getMængde());
        }
        return h.toString();
    }



    public static List<Fad> fadsøgning(double minfadstørrelse, double maxfadstørrelse, int minAlder, int maxAlder, int minBrugt, int maxBrugt,
                                       List<Fadtype> fadTyper, List<String> leverandør,
                                       List<Træsort> træsortList, boolean skalVæreFyldt) {
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
                // Filtrerer efter antal gange brugt
                .filter(f -> f.getAntalGangeBrugt() >= minBrugt && f.getAntalGangeBrugt() <= maxBrugt)
                // Filtrerer efter om fadet skal være fyldt
                .filter(f -> !skalVæreFyldt || f.getDestillat() != null)
                .collect(Collectors.toList());
    }

   public static ArrayList<Fad> frieFadeTilDestillat(ArrayList<Fad> fade){
       ArrayList<Fad> frieFade = new ArrayList<>();
       for (Fad f : Controller.getFade()) {
           if (Controller.getDestillat(f) == null && f.getAntalGangeBrugt() < 3) {
               frieFade.add(f);
           }
       }
       return frieFade;
   }

    public static ArrayList<Batch> batchKlarTilDestillat(ArrayList<Batch> batches){
        ArrayList<Batch> batchesMedVæske = new ArrayList<>();
        for (Batch b : Controller.getBatches()) {
            if (Controller.getMængdeVæske(b) > 0) {
                batchesMedVæske.add(b);
            }
        }
        return batchesMedVæske;
    }

    public static void setWhiskyInfo(ArrayList<DestillatMængde> destillatMængder, Whiskyserie whiskyserie, double vandmængde, double antalFlasker){
        whiskyserie.setAlkoholPct(Controller.beregnAlkoholProcentPåWhiskyserie(destillatMængder,vandmængde));
        whiskyserie.setStørrelse(Controller.samletMængdeWhiskySerie(whiskyserie,vandmængde));
        whiskyserie.setVandMængde(vandmængde);
        whiskyserie.setAntalFlasker(antalFlasker);

        int antalFad = 0;
        List<WhiskyType> whiskyTypes = new ArrayList<>();
        for (DestillatMængde destillatMængde : destillatMængder){
            if (destillatMængde.getDestillat().getFad() != null){
                antalFad++;
            }
        }

        if (vandmængde == 0 && antalFad > 1) {
            whiskyserie.setWhiskyType(WhiskyType.MALTSTRENGTH);
        }
        if (vandmængde > 0 && antalFad > 1){
            whiskyserie.setWhiskyType(WhiskyType.SINGLEMALT);
        }
        if (vandmængde == 0 && antalFad ==1){
            whiskyserie.setWhiskyType(WhiskyType.CASKSTRENGTH);
        }
        if (vandmængde > 0 && antalFad == 1){
            whiskyserie.setWhiskyType(WhiskyType.SINGLECASK);
        }
        System.out.println(whiskyserie.getWhiskyType());
    }

    public static String toStringInfoBoxWhiskyserie(ArrayList<DestillatMængde> destillatMængder, Whiskyserie whiskyserie, double vandmængde) {
        StringBuilder h = new StringBuilder();

        h.append("Whisky serie navn: " + whiskyserie.getSerieNavn() + "\nDato oprettet: " + whiskyserie.getDato() + "\nSamlet mængde væske: " + Controller.samletMængdeWhiskySerie(whiskyserie, vandmængde)
                + "\nForventet antal flasker: " + Controller.antalForventetFlakser(whiskyserie, Controller.samletMængdeWhiskySerie(whiskyserie, vandmængde)));

        HashSet<Integer> fadIds = new HashSet<>();
        h.append("\nFad info: \n");
        for (DestillatMængde destillatMængde : destillatMængder){
            Destillat destillat = destillatMængde.getDestillat();
            Fad fad = destillat.getFad();
            int fadId = fad.getFadNr();
            if (fadIds.add(fadId)){
                h.append("id: " + fadId + ", type: " + fad.getFadtype() + ", størrelse: " + fad.getFadStørrelse() + "\n");
            }
        }

        HashSet<Integer> batchIds = new HashSet<>();
        h.append("Batch info: \n");
        for (DestillatMængde destillatMængde : destillatMængder) {
            Destillat destillat = destillatMængde.getDestillat();
            for (BatchMængde batchMængde : destillat.getBatchMængder()) {
                int batchId = batchMængde.getBatch().getBatchID();
                if (batchIds.add(batchId)) {
                    //Bruger append så den ikke overskriver ID'et der stod før
                    h.append("id: " + batchId+ " ");
                }
            }

        }

        return h.toString();
    }

    public static double beregnAlkoholProcentPåWhiskyserie(ArrayList<DestillatMængde> destillatMængder, double mængdeVand) {
        double samletrentalkoholprocent = 0;
        double alkoholPct = 0;
        double mængdeAlkoholVæske = 0;

        for (DestillatMængde destillatMængde : destillatMængder) {
            Destillat destillat = destillatMængde.getDestillat();
            alkoholPct = destillat.beregnalkoholprocent();
            mængdeAlkoholVæske += destillatMængde.getMængde();
            samletrentalkoholprocent += destillatMængde.getMængde() * alkoholPct / 100;

        }
        mængdeAlkoholVæske += mængdeVand;
        return (samletrentalkoholprocent / mængdeAlkoholVæske) * 100;
    }

    public static ArrayList<Destillat> destilatWhiskySerieUdenFilter(ArrayList<Destillat> destillater) {
        ArrayList<Destillat> alleklarDestillater = new ArrayList<>();

        for (Destillat d : Controller.getDestillater()) {
            if (Controller.getDestillat(d.getFad()) != null && ChronoUnit.YEARS.between(d.getDatoForPåfyldning(), LocalDateTime.now()) >= 3 && d.getSamletMængde() > 0) {
                alleklarDestillater.add(d);
            }
        }

        return alleklarDestillater;
    }

    public static ArrayList<Destillat> destilatWhiskySerieFilter(ArrayList<Destillat> destillater, double år) {
        ArrayList<Destillat> alleklarDestillater = new ArrayList<>();

        for (Destillat d : Controller.getDestillater()) {
            if (Controller.getDestillat(d.getFad()) != null && ChronoUnit.YEARS.between(d.getDatoForPåfyldning(), LocalDateTime.now()) >= 3 && d.getSamletMængde() > 0) {
                alleklarDestillater.add(d);
            }
        }
        ArrayList<Destillat> destillaterEfterFiltrering = new ArrayList<>();
        for (Destillat destillat1 : alleklarDestillater) {
            if (ChronoUnit.YEARS.between(destillat1.getDatoForPåfyldning(), LocalDateTime.now()) >= år) {
                destillaterEfterFiltrering.add(destillat1);

            }
        }

        return destillaterEfterFiltrering;
    }

    public static List<Whiskyserie> whiskeySøgning(double minAlkoholProcent, double maxAlkoholProcent,
                                                   double minMængde, double maxMængde,
                                                   int minAntalFlakser, int maxAntalFlasker, int minAlder, int maxAlder,
                                                   list<whiskyType> whiskeyTyper ) {

        List<Whiskyserie> whiskyserier = Controller.getWhiskyserie();
        LocalDateTime nu = LocalDateTime.now();

        return whiskyserier.stream()
                .filter(w -> w.getAlkoholPct() >= minAlkoholProcent && w.getAlkoholPct() <= maxAlkoholProcent)
                .filter(w -> w.getStørrelse() >= minMængde && w.getStørrelse() <= maxMængde)
                .filter(w -> w.getAntalFlasker() >= minAntalFlakser && w.getAntalFlasker() <= maxAntalFlasker)
                .filter(w -> {
                    long alder = ChronoUnit.YEARS.between(w.getDato(),nu);
                    return alder >= minAlder && alder <= maxAlder; })
                .filter(w -> whiskeyTyper == null || whiskeyTyper.isEmpty || whiskeyTyper.contains(w.getWhiskeytype))
                .collect(Collectors.toList());

    }
}

