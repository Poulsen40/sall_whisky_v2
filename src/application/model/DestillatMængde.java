package application.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DestillatMængde implements Serializable {
    private double mængde;
    private Destillat destillat;
    private LocalDateTime slutDato;

    DestillatMængde(double mængde, Whiskyserie whiskyserie, Destillat destillat) {
        this.mængde = mængde;
        this.destillat = destillat;
        whiskyserie.addDestillatMængde(this);
        setSlutdato(LocalDateTime.now());
    }

    public void setSlutdato(LocalDateTime slutdato) {
        this.slutDato = slutdato;
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
