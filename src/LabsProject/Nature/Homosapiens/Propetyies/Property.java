package LabsProject.Nature.Homosapiens.Propetyies;

import LabsProject.Nature.Homosapiens.Human;

import java.io.Serializable;
import java.util.Objects;

public abstract class Property implements Serializable {
    private String name;
    private Human owner;
    public Property(String name) {
        this.name = name;
    }
    public Property(String name, Human owner) {
        this.name = name;
        this.owner = owner;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setOwner(Human owner) {
        this.owner = owner;
    }
    public Human getOwner() {
        return this.owner;
    }
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Property other = (Property) otherObject;
        return this.getName().equals(other.getName()) && (this.getOwner() == other.getOwner());
    }
    public int hashCode()
    {
        return Objects.hash(name, owner);
    }
    public String toString()
    {
        if(this.owner!=null) {
            return getClass().getSimpleName() + "[name=" + this.getName() + ",owner=" + this.getOwner().toString() + "]";
        }
        else {
            return "Prop" + "[name=" + this.getName()  + "]";
        }
    }
}
