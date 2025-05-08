package application.model;

import java.security.spec.RSAOtherPrimeInfo;

public class Whiskyprodukt {
    private static int nr;
    private Whiskyserie whiskyserie;
    private double flaskeStr = 0.70;

    Whiskyprodukt(Whiskyserie whiskyserie, double flaskeStr) {
        nr++;
        this.whiskyserie = whiskyserie;
        this.flaskeStr = flaskeStr;
    }

}
