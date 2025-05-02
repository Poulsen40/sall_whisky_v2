package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;
import javax.naming.ldap.Control;
import java.time.LocalDateTime;


public class APP {
    public static void main(String[] args) {

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

}
