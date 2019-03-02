package com.ru.itmo.Gorshkov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Human {
    private String name;
    private Condition condition;
    private byte countCondition = 0;
    private ArrayList<Property> allProperty;
    private double cordX;
    private double cordY;
    private boolean sit;
    public Human(String name) {
        this.name = name;
        condition = Condition.CALM;
        allProperty = new ArrayList<Property>();
        allProperty.ensureCapacity(5);
        System.out.println("Создан человек " + this.getName());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCondition (Condition condition) {
        this.condition = condition;
        System.out.println(this.getName() + " изменил состояние на " + condition.toString());
    }
    public Condition getCondition() {
        return condition;
    }
    public void addProperty(Property property) {
        this.allProperty.add(property);
        System.out.println("У человека " + this.getName() + " появилось новое имущество: " + property.getName());
    }
    public Property[] getAllProperty() {
        Property[] propertyArray = new Property[allProperty.size()];
        for (int i = 0;i < allProperty.size(); i++) {
            propertyArray[i] = allProperty.get(i);
        }
        return propertyArray;
    }
    public void delProperty(Property property) {
        this.allProperty.remove(property);
        System.out.println("У человека " + this.getName() + " пропало имущество: " + property.getName());
    }
    public void setCordX(double cordX) {
        this.cordX = cordX;
        System.out.println(this.getName() + " изменил координату X на " + cordX);
    }
    public void setCordY(double cordY) {
        this.cordY = cordY;
        System.out.println(this.getName() + " изменил координату Y на " + cordY);
    }
    public void setCords(double cordX, double cordY) {
        this.cordY = cordY;
        this.cordX = cordX;
        System.out.println(this.getName() + " изменил координаты на " + cordX + " " + cordY);
    }
    public double[] getCords() {
        return new double[] {cordX, cordY};
    }
    public void upCountCondition() {
        this.countCondition++;
        if (countCondition > 1) {
            switch (this.condition) {
                case CALM:
                    this.condition = Condition.ALARM;
                    this.countCondition = 0;
                    System.out.println(this.getName() + " изменил состояние на " + Condition.ALARM.toString());
                    break;
                case ALARM:
                    this.condition = Condition.GIVEUP;
                    this.countCondition = 0;
                    System.out.println(this.getName() + " изменил состояние на " + Condition.GIVEUP.toString());
                    break;
                case GIVEUP:
                    this.condition = Condition.CRY;
                    this.countCondition = 0;
                    System.out.println(this.getName() + " изменил состояние на " + Condition.CRY.toString());
                    break;
                case SCARED:
                    this.condition = Condition.VERYSCARED;
                    this.countCondition = 0;
                    System.out.println(this.getName() + " изменил состояние на " + Condition.VERYSCARED.toString());
                    break;
                case VERYSCARED:
                    this.condition = Condition.VERYVERYSCARED;
                    this.countCondition = 0;
                    System.out.println(this.getName() + " изменил состояние на " + Condition.VERYVERYSCARED.toString());
                    break;
            }
        }
    }
    public boolean equals(Object otherObject)  {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Human other = (Human) otherObject;
        return this.getName().equals(other.getName()) && this.getCondition().equals(other.getCondition()) && Arrays.equals(this.getCords(), other.getCords()) && Arrays.equals(this.getAllProperty(), other.getAllProperty());
    }
    public int hashCode()
    {
        return Objects.hash(name, condition, allProperty, cordX, cordY, countCondition);
    }
    public String toString()
    {
        return getClass().getName() + "[name=" + this.getName() + ",condition=" + this.getCondition().toString() + ",allProperty=" + this.getAllProperty().toString() + ",Cords=" + this.getCords().toString() + "]";
    }
    public TypeOfTree lookAt(Tree tree, MakeLaugh makeLaugh) throws TooFarToTreeExeption {
        if(tree instanceof LittleTree) {
            if(sit) {
                makeLaugh.makeLaugh();
                return tree.getType();
            }
            else throw new TooFarToTreeExeption();
        }
        else {
            return tree.getType();
        }
    }
    public class Legs {
        public Legs() {}
        public void walk(Grass grass, CanSoftly softly) {
            if (softly.canSoftly()) {
                System.out.println(" идет по мягкой траве с цветочками");
                condition=Condition.AMAZED;
            }
        }
        public void sit() {
            sit = true;
        }
        public void standUp() {
            sit = false;
        }
    }
}
