package LabsProject.Nature.Homosapiens;

import LabsProject.Nature.Homosapiens.Propetyies.ImmaterialProperty;
import LabsProject.Nature.Homosapiens.Propetyies.Property;

import java.util.Arrays;

public class Robber extends Human implements FearOfFlyableHuman, Steal {
    private Human victim;
    public Robber(String name) {
        super(name);
        System.out.println(this.getName() + " - грабитель");
    }
    public Human getVictim() {
        return victim;
    }
    public void setVictim (Human victim) {
        this.victim = victim;
        System.out.println("Грабитель " + this.getName() + " хочет ограбить человека " + victim.getName());
    }

    @Override
    public boolean steal(Human victim, Property property) {
        if (property instanceof ImmaterialProperty) {
            System.out.println(this.getName() + " хотел украсть " + property.getName() + " ,но нельзя украсть нематериальное имущество");
            return false;
        }
        else {

            if (Arrays.asList(victim.getAllProperty()).contains(property)) {
                System.out.println(this.getName() + " украл у " + victim.getName() + " имущество: " + property.getName());
                victim.delProperty(property);
                this.addProperty(property);
                return true;
            }
            else {
                System.out.println(this.getName() + " не может украсть " + property.getName() + ", у " + victim.getName() + " нету этого имущества");
                return false;
            }
        }
    }
    public void giveback() {
        for (Property property : this.getAllProperty()) {
            System.out.println(this.getName() + " вернул имущество " + property.getName() + " хозяину " + property.getOwner().getName());
            this.delProperty(property);
            property.getOwner().addProperty(property);
        }
    }
    @Override
    public void fearOfFlyableHuman(FlyableHuman flyableHuman) {
        switch (this.getCondition()) {
            case CALM:
                System.out.println("Грабитель " + this.getName() + " увидел " + flyableHuman.getName() + " и испугался");
                this.setCondition(Condition.SCARED);
            case SCARED:
                    this.upCountCondition();
                    System.out.println("Грабитель " + this.getName() + " увидел " + flyableHuman.getName() + " опять и испугался еще сильнее");
            case VERYSCARED:
                this.setCondition(Condition.VERYVERYSCARED);
                System.out.println("Грабитель " + this.getName() + " очень испугался и решил вернуть все украденное");
                this.giveback();
            case VERYVERYSCARED:
                break;
        }
    }
    public boolean equals(Object otherObject)  {
        Robber other = (Robber) otherObject;
        return super.equals(other) && this.getVictim().equals(other.getVictim());
    }
    public String toString() {
        return super.toString() + "[victim=" + this.getVictim().toString() + "]";
    }
}
