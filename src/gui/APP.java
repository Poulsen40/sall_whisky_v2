package gui;

import application.controller.Controller;
import application.model.*;
import javafx.application.Application;

import javax.naming.ldap.Control;


public class APP {
    public static void main(String[] args) {

        initStorage();

        Application.launch(GUI.class);

    }

    public static void initStorage() {

        //Batch
        Batch b1 = Controller.createBatch("Malt2","Sort","Sorte mark",85,63,"ingen", Rygemateriale.GLØD);

        //Fad
        Fad f1 = Controller.createFad(65,"Spanien",false, Fadtype.EXBOURBON, Træsort.EGETRÆ,0,0);


    }

}
