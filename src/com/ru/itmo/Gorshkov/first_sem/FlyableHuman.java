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
}