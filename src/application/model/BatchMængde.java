package application.model;

import java.io.Serializable;

public class BatchMængde implements Serializable {
    private double mængde;
    private Batch batch;

    BatchMængde(double mængde, Destillat destillat, Batch batch) {
        this.mængde = mængde;
        destillat.addBatchMængde(this);
        this.batch = batch;
    }

    public Batch getBatch(){
        return batch;
    }

    public double getMængde() {
        return mængde;
    }

    @Override
    public String toString() {
        return "BatchMængde{" +
                "batch=" + batch +
                '}';
    }
}
