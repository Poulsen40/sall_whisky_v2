package application.model;

public class DestillatMængde {
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
