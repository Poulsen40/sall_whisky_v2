package application.model;

public class Whiskyprodukt {
    private static int nr;
    private Whiskyserie whiskyserie;

    public Whiskyprodukt(Whiskyserie whiskyserie) {
        nr++;
        this.whiskyserie = whiskyserie;
    }
}
