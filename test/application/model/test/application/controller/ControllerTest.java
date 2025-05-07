package application.model.test.application.controller;


import application.controller.Controller;
import application.model.Lager;
import org.junit.jupiter.api.Test;
import storage.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {

    @Test
    void createLagerTC1() {
        Lager lager = Controller.createLager(10,5,2,"ContatinerLager");
        Lager lager1 = null;
        for (Lager l1 : Storage.getLager() ) {
            if(lager.equals(l1)){
                lager1 = l1;
            }
        }
        assertEquals(lager,lager1);
    }
    @Test
    void createLagerTC2() {
        Lager lager = new Lager(10,5,2,"ContatinerLager");
        assertEquals(lager.getNavn(),"ContatinerLager");

        assertEquals(lager.getObevaringsplads().length,10);
        assertEquals(lager.getObevaringsplads()[0].length,5);
        assertEquals(lager.getObevaringsplads()[0][0].length,2);
    }
}