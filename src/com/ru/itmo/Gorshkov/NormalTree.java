package com.ru.itmo.Gorshkov;

public class NormalTree extends Tree {
    public NormalTree(TypeOfTree type) {
        super(type);
    }
    public void Ripe(CanFruit canFruit) {
        if (canFruit.fruit()) {
            new Acorn();
        }
    }
    public static class Acorn {
        public Acorn() {
            System.out.println("Новый желудь созрел и упал");
        }
    }
}
