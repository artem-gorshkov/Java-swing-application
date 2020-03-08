package LabsProject.Nature.Homosapiens;

import LabsProject.Nature.Homosapiens.Propetyies.Property;

import java.io.Serializable;
import java.util.*;
import java.time.ZonedDateTime;


public class Human implements Comparable<Human>, Serializable {
    private String name;
    private Condition condition;
    private byte countCondition = 0;
    private ArrayList<Property> allProperty;
    private double cordX;
    private double cordY;
    private boolean sit;
    private ZonedDateTime birthday;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Human(String name) {
        this.name = name;
        condition = Condition.CALM;
        allProperty = new ArrayList<>();
        allProperty.ensureCapacity(5);
        this.sit = false;
        //System.out.println("Создан человек " + this.getName());
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Human human) {
        return  Integer.compare(Objects.hash(name, condition, allProperty, cordX, cordY, birthday),
                Objects.hash(human.getName(), human.getCondition(), human.getAllProperty(),
                        human.getCordX(), human.getCordY(), human.getBirthday()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
        //System.out.println(this.getName() + " изменил состояние на " + condition.toString());
    }

    public Condition getCondition() {
        return condition;
    }

    public void addProperty(Property property) {
        this.allProperty.add(property);
        //System.out.println("У человека " + this.getName() + " появилось новое имущество: " + property.getName());
    }

    public List<Property> getAllProperty() {
        return allProperty;
    }

    public void delProperty(Property property) {
        this.allProperty.remove(property);
        //System.out.println("У человека " + this.getName() + " пропало имущество: " + property.getName());
    }

    public void setCordX(double cordX) {
        this.cordX = cordX;
    }

    public void setCordY(double cordY) {
        this.cordY = cordY;
    }

    public void setCords(double cordX, double cordY) {
        this.cordY = cordY;
        this.cordX = cordX;
        //System.out.println(this.getName() + " изменил координаты на " + cordX + " " + cordY);
    }

    public double getCordX() {
        return cordX;
    }

    public double getCordY() {
        return cordY;
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

    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Human other = (Human) otherObject;
        return this.getName().equals(other.getName()) && this.getCondition().equals(other.getCondition()) &&
                this.getCordY() == other.getCordY() && this.getCordX() == other.getCordX() &&
                Arrays.equals(this.getAllProperty().toArray(), other.getAllProperty().toArray()) && this.getBirthday().equals(other.getBirthday());
    }

    public int hashCode() {
        return Objects.hash(name, condition, allProperty, cordX, cordY, countCondition, birthday);
    }

    public String toString() {
        String str = "[";
        for (int i = 0; i < allProperty.size(); i++) {
            str = str + allProperty.get(i).toString() + ",";
        }
        str = str + "]";
        return getClass().getSimpleName() + "[name=" + this.getName() + ", condition=" + this.getCondition().toString() +
                ", allProperty=" + str + ", CordX=" + this.getCordX() + ", CordY=" + this.getCordY() +
                ", Birthday=" + this.getBirthday() + "]";
    }

    /*public TypeOfTree lookAt(Tree tree, MakeLaugh makeLaugh) throws TooFarToTreeExeption {
        if (tree instanceof LittleTree) {
            if (sit) {
                makeLaugh.makeLaugh();
                return tree.getType();
            } else throw new TooFarToTreeExeption();
        } else {
            return tree.getType();
        }
    }*/

}
