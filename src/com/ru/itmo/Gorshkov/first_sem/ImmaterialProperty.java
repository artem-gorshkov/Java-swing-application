package com.ru.itmo.Gorshkov.first_sem;

public class ImmaterialProperty extends Property {
    public ImmaterialProperty(String name) {
        super(name);
    }
    public ImmaterialProperty(String name, Human owner) {
        super(name, owner);
    }
    public void setOwner(Human owner) {
        System.out.print("You can't change owner!");
    }
}