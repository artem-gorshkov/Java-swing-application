package com.ru.itmo.Gorshkov.first_sem;

import java.util.Arrays;
import java.util.Objects;

public class FlyableHuman extends Human implements Flyable {
    private double cordZ;

    public FlyableHuman(String name) {
        super(name);
        System.out.println(this.getName() + " - летающий человек");
    }

    @Override
    public void setCordZ(double cordZ) {
        this.cordZ = cordZ;
        System.out.println(this.getName() + " изменил координату Z на " + cordZ);
    }

    public void setCords(double cordX, double cordY, double cordZ) {
        super.setCords(cordX, cordY);
        this.cordZ = cordZ;
        System.out.println(this.getName() + " изменил координаты на " + cordX + " " + cordY + " " + cordZ);
    }

    @Override
    public double[] getCords() {
        double[] arr1 = new double[3];
        double[] arr2 = new double[2];
        arr2 = super.getCords();
        arr1[0] = arr2[0];
        arr1[1] = arr2[1];
        arr1[2] = cordZ;
        return arr1;
    }

    public boolean equals(Object otherObject) {
        Robber other = (Robber) otherObject;
        return super.equals(other) && Arrays.equals(this.getCords(), other.getCords());
    }
    public int hashCode()
    {
        return Objects.hash(this.getName(), this.getCondition(), this.getAllProperty(), this.getCords());
    }
    public String toString()
    {
        return getClass().getName() + "[name=" + this.getName() + ",condition=" + this.getCondition().toString() + ",allProperty=" + this.getAllProperty().toString() + ",Cords=" + this.getCords().toString() + "]";
    }
}