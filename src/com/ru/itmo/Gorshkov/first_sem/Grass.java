package com.ru.itmo.Gorshkov.first_sem;

import java.util.ArrayList;
import java.util.List;

public class Grass {
    private int numberOfFertilizer;
    private List<Flower> flowers;
    public Grass(int numb) {
        this.numberOfFertilizer=numb;
        flowers = new ArrayList<>();
    }
    public void addFlower(Flower flower) {
        flowers.add(flower);
    }
    public List<Flower> getFlowers() {
        return flowers;
    }
    public void DelAllFlowers() {
        flowers.clear();
        System.out.println("на лужайке больше нет цветов");
    }
    public class Flower {
        private boolean grow;
        String name;
        public Flower(String name) {
            this.name = name;
            if(numberOfFertilizer>0) grow=true;
            else grow=false;
        }
        public void grow() throws NotGrowExeption {
            if(!grow) {
                throw new NotGrowExeption();
            }
        }
    }
}
