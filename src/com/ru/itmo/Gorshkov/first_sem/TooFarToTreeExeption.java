package com.ru.itmo.Gorshkov.first_sem;

public class TooFarToTreeExeption extends Exception {
    public TooFarToTreeExeption() {}
    public TooFarToTreeExeption(String string) {
        super(string);
    }
}
