package application.controller;


import application.model.Lager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {

    @Test
    void createLager() {
        Lager lager = new Lager(10,5,2,"ContatinerLager");
        assertEquals(lager.getNavn(),"ContatinerLager");

        assertEquals(lager.getObevaringsplads().length,10);
        assertEquals(lager.getObevaringsplads()[0].length,5);
        assertEquals(lager.getObevaringsplads()[0][0].length,2);
    }
}