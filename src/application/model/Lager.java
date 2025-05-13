package application.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lager implements Serializable {

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

    public void fjernFadFraObevaringsplads(Fad fad) {
        if (!fad.getLager().equals(this)) {
            return;
        }

        int sidsteI = -1, sidsteJ = -1, sidsteK = -1;
        int fadI = -1, fadJ = -1, fadK = -1;

        // Gennemgå hele arrayet og find både:
        // - hvor fadet står
        // - og hvor det sidste fad står (sidst i brug)
        for (int i = 0; i < obevaringsplads.length; i++) {
            for (int j = 0; j < obevaringsplads[i].length; j++) {
                for (int k = 0; k < obevaringsplads[i][j].length; k++) {
                    if (obevaringsplads[i][j][k] != null) {
                        sidsteI = i;
                        sidsteJ = j;
                        sidsteK = k;
                    }
                    if (obevaringsplads[i][j][k] != null && obevaringsplads[i][j][k].equals(fad)) {
                        fadI = i;
                        fadJ = j;
                        fadK = k;
                    }
                }
            }
        }

        // Hvis fadet blev fundet – fjern det
        if (fadI != -1) {
            obevaringsplads[fadI][fadJ][fadK] = null;
            antalledigepladser++;

            // Kun hvis det var det sidste fad, skal vi opdatere næste ledige plads
            if (fadI == sidsteI && fadJ == sidsteJ && fadK == sidsteK) {
                // Find den nye sidste plads
                boolean found = false;
                for (int i = obevaringsplads.length - 1; i >= 0 && !found; i--) {
                    for (int j = obevaringsplads[i].length - 1; j >= 0 && !found; j--) {
                        for (int k = obevaringsplads[i][j].length - 1; k >= 0 && !found; k--) {
                            if (obevaringsplads[i][j][k] != null) {
                                aktuelleHylde = j;
                                næsteLedigePlads = k + 1;
                                found = true;
                            }
                        }
                    }
                }
                if (!found) {
                    aktuelleHylde = 0;
                    næsteLedigePlads = 0;
                }
            }
        }
    }


    @Override
    public String toString() {
        return navn + "Ledige pladser = " + antalledigepladser;
    }
}
