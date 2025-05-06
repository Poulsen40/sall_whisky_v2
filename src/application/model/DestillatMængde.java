package application.model;

public class DestillatMængde {
    private double mængde;
    private Destillat destillat;

    DestillatMængde(double mængde, Whiskyserie whiskyserie, Destillat destillat) {
        this.mængde = mængde;
        this.destillat = destillat;
        whiskyserie.addDestillatMængde(this);
    }
}
