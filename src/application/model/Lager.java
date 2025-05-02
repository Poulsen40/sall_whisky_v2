package application.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lager {

    String navn;
    int antalledigepladser;
    Fad[][][] obevaringsplads;

    public Lager(int rækker, int hylder, int plads, String navn) {
        this.obevaringsplads =  new Fad[rækker][hylder][plads];
        this.navn = navn;
        this.antalledigepladser = rækker * hylder * plads;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getAntalledigepladser() {
        return antalledigepladser;
    }

    public void setAntalledigepladser(int antalledigepladser) {
        this.antalledigepladser = antalledigepladser;
    }

    public Fad[][][] getObevaringsplads() {
        return obevaringsplads;
    }

    public void setObevaringsplads(Fad[][][] obevaringsplads) {
        this.obevaringsplads = obevaringsplads;
    }

    @Override
    public String toString() {
        return navn  + "Ledige pladser = " + antalledigepladser;
    }
}
