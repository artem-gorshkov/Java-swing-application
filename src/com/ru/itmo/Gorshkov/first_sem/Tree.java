package com.ru.itmo.Gorshkov.first_sem;

abstract class Tree {
    private TypeOfTree type;
    static int num;
    public Tree(TypeOfTree type) {
        this.type = type;
        num++;
        System.out.println("создано дерево " + num);
    }
    public TypeOfTree getType() {
        return type;
    }
}
