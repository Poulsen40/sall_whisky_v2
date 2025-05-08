package application.model;

import java.security.spec.RSAOtherPrimeInfo;

public class Whiskyprodukt {
    private static int nr = 1;
    private int nrID;
    private Whiskyserie whiskyserie;
    private double flaskeStr = 0.70;

    Whiskyprodukt(Whiskyserie whiskyserie, double flaskeStr) {
        nrID = nr++;
        this.whiskyserie = whiskyserie;
        this.flaskeStr = flaskeStr;
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
