package com.ru.itmo.Gorshkov;

public class NotGrowExeption extends RuntimeException {
    public NotGrowExeption() {}
    public NotGrowExeption(String string) {
        super(string);
    }
}
