package application.model;

import java.io.Serializable;
import java.security.spec.RSAOtherPrimeInfo;

public class Whiskyprodukt implements Serializable {
    private static int nr = 1;
    private int nrID;
    private Whiskyserie whiskyserie;
    private double flaskeStr = 0.70;

    Whiskyprodukt(Whiskyserie whiskyserie, double flaskeStr) {
        nrID = nr++;
        this.whiskyserie = whiskyserie;
        this.flaskeStr = flaskeStr;
    }

    public void setNrID(int nrID) {
        this.nrID = nrID;
    }

    @Override
    public String toString() {
        return "Whiskyprodukt{" +
                "nrID=" + nrID +
                ", whiskyserie=" + whiskyserie +
                ", flaskeStr=" + flaskeStr +
                '}';
    }
}
