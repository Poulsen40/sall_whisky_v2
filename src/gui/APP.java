package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import javax.naming.ldap.Control;
import java.io.*;
import java.time.LocalDateTime;


public class APP {
    public static void main(String[] args) {
//        Storage LS = loadStorage();
//		if (LS == null){
//			LS = new ListStorage();
//			System.out.println("Empty ListStorage created");
//		}
//		Controller.setStorage((ListStorage) LS);
//
//		if (Controller.getCompanies().isEmpty() && Controller.getEmployees().isEmpty()){
//			initStorage();
//			System.out.println("Storage initialized");
//		}
//		Application.launch(StartWindow.class);
//		saveStorage(LS);


        initStorage();

        Application.launch(GUI.class);

    }

    public static void initStorage() {
        //Batch
        Batch b1 = Controller.createBatch("Malt2","Sort","Sorte mark",85,10,"ingen", Rygemateriale.GLØD);
        Batch b2 = Controller.createBatch("Malt2","Sort","Sorte mark",100,20,"ingen", Rygemateriale.GLØD);
        Batch b3 = Controller.createBatch("Malt2","Sort","Sorte mark",25,63,"ingen", Rygemateriale.GLØD);
        Batch b4 = Controller.createBatch("Malt2","Sort","Sorte mark",45,63,"ingen", Rygemateriale.GLØD);

        //Fad
        Fad f1 = Controller.createFad(65,"Spanien",false, Fadtype.EXBOURBON, Træsort.EGETRÆ,0);
        Fad f2 = Controller.createFad(30,"Spanien",false, Fadtype.EXBOURBON, Træsort.EGETRÆ,0);
        Fad f3 = Controller.createFad(12,"Spanien",false, Fadtype.EXBOURBON, Træsort.EGETRÆ,0);
        Fad f4 = Controller.createFad(100,"Spanien",false, Fadtype.EXBOURBON, Træsort.EGETRÆ,0);

        //
        Lager lager = Controller.createLager(10,3,2,"Container lager");
        Lager lager1 = Controller.createLager(12,3,10,"Lars landmand");

        Destillat destillat = Controller.createDestilat(LocalDateTime.now(),f1);

        b1.createBatchMængde(44,destillat);
        b1.createBatchMængde(33,destillat);
        b2.createBatchMængde(33,destillat);
    }


    //loader objekter i storage
//    public static Storage loadStorage() {
//        String fileName = "storage.ser";
//        try (FileInputStream fileIn = new FileInputStream(fileName);
//             ObjectInputStream objIn = new ObjectInputStream(fileIn)
//        ) {
//            Object obj = objIn.readObject();
//            Storage storage = (ListStorage) obj;
//            System.out.println("Storage loaded from file " + fileName);
//            return storage;
//        } catch (IOException | ClassNotFoundException ex) {
//            System.out.println("Error deserializing storage");
//            System.out.println(ex);
//            return null;
//        }
//    }


    //gemmer objekter i storage
//    public static void saveStorage(Storage storage) {
//        String fileName = "storage.ser";
//        try (FileOutputStream fileOut = new FileOutputStream(fileName);
//             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
//        ) {
//            objOut.writeObject(storage);
//            System.out.println("Storage saved in file " + fileName);
//        } catch (IOException ex) {
//            System.out.println("Error serializing storage");
//            System.out.println(ex);
//            throw new RuntimeException();
//        }
//    }

}
