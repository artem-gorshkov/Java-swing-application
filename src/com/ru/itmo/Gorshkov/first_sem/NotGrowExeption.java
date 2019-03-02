package com.ru.itmo.Gorshkov.first_sem;

public class NotGrowExeption extends RuntimeException {
    public NotGrowExeption() {}
    public NotGrowExeption(String string) {
        super(string);
    }
}
