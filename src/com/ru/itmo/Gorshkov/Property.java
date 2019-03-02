package com.ru.itmo.Gorshkov;

import java.util.Objects;

abstract class Property {
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
        return this.getName().equals(other.getName()) && this.getOwner().equals(other.getOwner());
    }
    public int hashCode()
    {
        return Objects.hash(name, owner);
    }
    public String toString()
    {
        return getClass().getName() + "[name=" + this.getName() + ",owner=" + this.getOwner().toString() + "]";
    }
}
