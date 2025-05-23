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
        Batch b1 = Controller.createBatch("Malt1", "Byg", "John's mark", 85, 65, "ingen", Rygemateriale.GLØD, LocalDate.of(2019,11,29));
        Batch b2 = Controller.createBatch("Malt2", "Havre", "Mark 1", 100, 70, "ingen", Rygemateriale.GLØD, LocalDate.of(2021,9,28));
        Batch b3 = Controller.createBatch("Malt3", "Byg", "Mark 4", 25, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2017,11,10));
        Batch b4 = Controller.createBatch("Malt4", "Havre", "Mark 4", 45, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2022,8,21));

        Batch b5 = Controller.createBatch("Malt2", "Havre", "Mark 1", 100, 73, "ingen", Rygemateriale.GLØD, LocalDate.of(2021,9,28));
        Batch b6 = Controller.createBatch("Malt3", "Byg", "Mark 4", 25, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2017,11,10));
        Batch b7 = Controller.createBatch("Malt4", "Havre", "Mark 4", 45, 63, "ingen", Rygemateriale.GLØD, LocalDate.of(2022,8,21));

        //Fad
        Fad f1 = Controller.createFad(65, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f2 = Controller.createFad(30, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f3 = Controller.createFad(12, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f4 = Controller.createFad(100, "Spanien", false, Fadtype.EXBOURBON, Træsort.EGETRÆ, 0);
        Fad f5 = Controller.createFad(78, "USA", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);

        Fad f6 = Controller.createFad(600, "Spanien", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);
        Fad f7 = Controller.createFad(228, "Spanien", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);
        Fad f8 = Controller.createFad(110, "Spanien", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);
        Fad f9 = Controller.createFad(650, "Spanien", true, Fadtype.EXOLOROSOSHERRY, Træsort.EGETRÆ, 1);


        //tilføjer fad til lager
        Controller.addFadTilLager(f1, lager);
        Controller.addFadTilLager(f2, lager);
        Controller.addFadTilLager(f3, lager);
        Controller.addFadTilLager(f4,lager);

        Destillat d1 = Controller.createDestilat(LocalDateTime.of(2022, 1, 1, 2, 2), f1);
        BatchMængde bb = b1.createBatchMængde(200, d1);
        BatchMængde bbb = b2.createBatchMængde(200, d1);
        BatchMængde bbbb = b3.createBatchMængde(200, d1);

        Destillat d2 = Controller.createDestilat(LocalDateTime.of(2020,12,12,12,12,12),f2);
        BatchMængde b8 = Controller.createBatchMængde(50,d2,b5);
        BatchMængde b9 = Controller.createBatchMængde(25,d2,b6);
        BatchMængde b10 = Controller.createBatchMængde(10,d2,b7);

        Destillat d3 = Controller.createDestilat(LocalDateTime.of(2020, 1, 1, 2, 2), f3);
        BatchMængde bbbbb = b1.createBatchMængde(200, d3);

        Destillat d4 = Controller.createDestilat(LocalDateTime.of(2022, 1, 1, 2, 2), f4);
        BatchMængde bbbbbb = b5.createBatchMængde(200, d4);

        Whiskyserie whiskyserie = Controller.createWhiskyserie("SALL 1.0", LocalDate.now());
        DestillatMængde destillatMængde = d1.createDestillatMængde(100,whiskyserie);
        DestillatMængde destillatMængde1 = d2.createDestillatMængde(70,whiskyserie);

        Controller.tidpåhverfad(whiskyserie);

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



