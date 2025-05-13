package application.model;

import java.io.Serializable;

public class DestillatMængde implements Serializable {
    private double mængde;
    private Destillat destillat;

    DestillatMængde(double mængde, Whiskyserie whiskyserie, Destillat destillat) {
        this.mængde = mængde;
        this.destillat = destillat;
        whiskyserie.addDestillatMængde(this);
    }

    public double getMængde() {
        return mængde;
    }

    public Destillat getDestillat() {
        return destillat;
    }

    @Override
    public String toString() {
        return mængde + "";
    }


}
