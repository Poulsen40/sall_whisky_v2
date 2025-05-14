package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import storage.Storage;

import javax.naming.ldap.Control;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class APP {
    public static void main(String[] args) {
        Storage storage = loadStorage();
        if (storage == null) {
            storage = new Storage();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);

        if (Controller.getBatches().isEmpty() && Controller.getFade().isEmpty() && Controller.getlagere().isEmpty()
                && Controller.getDestillater().isEmpty()) {
            initStorage();
            System.out.println("Storage initialized");
        }
        Application.launch(GUI.class);
        saveStorage(storage);

    }

    public static void initStorage() {
        //opretter lager
        Lager lager = Controller.createLager(10, 3, 2, "Container lager");
        Lager lager1 = Controller.createLager(12, 3, 10, "Lars landmand");
        Lager lilleLager = Controller.createLager(2, 1, 2, "Lille lager");

        //Batch
        Batch b1 = Controller.createBatch("Malt2", "Sort", "Sorte mark", 85, 10, "ingen", Rygemateriale.GLØD, LocalDate.of(2022,11,11));
        Batch b2 = Controller.createBatch("Malt2", "Sort", "Sorte mark", 100, 20, "ingen", Rygemateriale.GLØD, LocalDate.of(2022,12,12));
        Batch b3 = Controller.createBatch("Malt2", "Sort", "Sorte mark", 25, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));
        Batch b4 = Controller.createBatch("Malt2", "Sort", "Sorte mark", 45, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2018,10,10));

        //Fad
        Fad f1 = Controller.createFad(65, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f2 = Controller.createFad(30, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f3 = Controller.createFad(12, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f4 = Controller.createFad(100, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f5 = Controller.createFad(78, "Spanien", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);

        //test med småt lager
        Controller.addFadTilLager(f1, lilleLager);
        Controller.addFadTilLager(f2, lilleLager);


        //tilføjer fad til lager
//        Controller.addFadTilLager(f1, lager);
//        Controller.addFadTilLager(f2, lager);
        Controller.addFadTilLager(f3, lager);
        Controller.addFadTilLager(f4, lager1);
        Controller.addFadTilLager(f5, lager1);


        Destillat d1 = Controller.createDestilat(LocalDateTime.of(2022, 1, 1, 2, 2), f1);
        BatchMængde bb = b1.createBatchMængde(200, d1);
        BatchMængde bbb = b2.createBatchMængde(200, d1);
        BatchMængde bbbb = b3.createBatchMængde(200, d1);


        System.out.println("samlet mængde d1" + d1.getSamletMængde());
        System.out.println("efter tjek d1 " + d1.getSamletMængde());


        Destillat d2 = Controller.createDestilat(LocalDateTime.of(2020, 1, 1, 2, 2), f2);
        BatchMængde bbbbb = b1.createBatchMængde(200, d2);
        System.out.println("d2 alko" + d2.beregnalkoholprocent());

        Whiskyserie ws1 = Controller.createWhiskyserie("Hej", LocalDate.now());

        DestillatMængde dm = Controller.createDestillatMængde(40, ws1, d2);


        Destillat d3 = Controller.createDestilat(LocalDateTime.of(2018, 1, 1, 2, 2), f3);
        BatchMængde bhnbbb = b3.createBatchMængde(200, d3);


//        Whiskyserie whiskyserie = Controller.createWhiskyserie("Hej", LocalDate.now());
//        DestillatMængde destillatMængde = d1.createDestillatMængde(100,whiskyserie);
//        DestillatMængde destillatMængde1 = d2.createDestillatMængde(100,whiskyserie);
//        DestillatMængde destillatMængde2 = d3.createDestillatMængde(100,whiskyserie);
//        whiskyserie.addDestillatMængde(destillatMængde);
//        whiskyserie.addDestillatMængde(destillatMængde1);
//        whiskyserie.addDestillatMængde(destillatMængde1);


        //


    }

    //loader objekter i storage
    public static Storage loadStorage() {
        String fileName = "storage.ser";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            Object obj = objIn.readObject();
            Storage storage = (Storage) obj;
            System.out.println("Storage loaded from file " + fileName);
            return storage;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error deserializing storage");
            System.out.println(ex);
            return null;
        }
    }


    //gemmer objekter i storage
    public static void saveStorage(Storage storage) {
        String fileName = "storage.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
        ) {
            objOut.writeObject(storage);
            System.out.println("Storage saved in file " + fileName);
        } catch (IOException ex) {
            System.out.println("Error serializing storage");
            System.out.println(ex);
            throw new RuntimeException();
        }
    }
}



