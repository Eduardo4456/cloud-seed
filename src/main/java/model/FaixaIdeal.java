package model;

import java.io.Serializable;

public class FaixaIdeal implements Serializable {
    public double tempMin, tempMax;
    public int umidMin, umidMax;

    public FaixaIdeal(double tempMin, double tempMax, int umidMin, int umidMax) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.umidMin = umidMin;
        this.umidMax = umidMax;
    }
}
