package application.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lager {

    private String navn;
    private int antalledigepladser;
    private Fad[][][] obevaringsplads;
    private int næsteLedigePlads = 0;
    private int aktuelleHylde = 0;
    private int aktuelleReol = 0;
    private boolean erFyldt;


    public Lager(int rækker, int hylder, int plads, String navn) {
        this.obevaringsplads = new Fad[rækker][hylder][plads];
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

    public int getNæsteLedigePlads() {
        return næsteLedigePlads;
    }

    public void setNæsteLedigePlads(int næsteLedigePlads) {
        this.næsteLedigePlads = næsteLedigePlads;
    }

    public int getAktuelleHylde() {
        return aktuelleHylde;
    }

    public void setAktuelleHylde(int aktuelleHylde) {
        this.aktuelleHylde = aktuelleHylde;
    }

    public int getAktuelleReol() {
        return aktuelleReol;
    }

    public void setAktuelleReol(int aktuelleReol) {
        this.aktuelleReol = aktuelleReol;
    }

    public boolean getErFyldt() {
        return erFyldt;
    }

    public void setErFyldt(boolean erFyldt) {
        this.erFyldt = erFyldt;
    }

    /**
     * pre: lagert må ikke være fyldt og fad må ikke være null
     *
     * @param fad
     * @return String med placeringen af fadet på lageret
     */
    public String tilføjFadTilobevaringsplads(Fad fad) {
        obevaringsplads[aktuelleReol][aktuelleHylde][næsteLedigePlads] = fad;
        String placering = "Reol: " + aktuelleReol + " Hylde: " + aktuelleHylde + " Plads: " + næsteLedigePlads;
        næsteLedigePlads++;
        if (næsteLedigePlads == obevaringsplads[aktuelleReol][aktuelleHylde].length) {
            aktuelleHylde++;
            if (aktuelleHylde == obevaringsplads[aktuelleReol].length) {
                aktuelleReol++;
                if (aktuelleReol == obevaringsplads.length) {
                    erFyldt = true;
                }
                aktuelleHylde = 0;
            }
            næsteLedigePlads = 0;
        }

        return placering;
    }

    public void fjernFadFraObevaringspldas(Fad fad){

        if(fad.getLager().equals(this)){
            for (int i = 0; i < obevaringsplads.length; i++) {
                for (int j = 0; j < obevaringsplads[i].length; j++) {
                    for (int k = 0; k < obevaringsplads[i][j].length; k++) {
                        if(obevaringsplads[i][j][k].equals(fad)){
                            obevaringsplads[i][j][k] = null;
                            if(næsteLedigePlads == 0 && aktuelleHylde == 0){
                                aktuelleHylde = obevaringsplads[i].length;
                                næsteLedigePlads = obevaringsplads[i][j].length;
                            }
                            antalledigepladser++;
                        }
                    }
                }
            }

        }
    }

    @Override
    public String toString() {
        return navn + "Ledige pladser = " + antalledigepladser;
    }
}
