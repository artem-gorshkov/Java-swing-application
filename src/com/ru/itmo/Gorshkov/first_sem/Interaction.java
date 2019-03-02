package com.ru.itmo.Gorshkov.first_sem;

import java.util.Objects;

public class Interaction {
    private String name;
    private Human subject;
    private Human target;
    private boolean negative;
    public Interaction(Human subject, Human target) {
        this.subject = subject;
        this.target = target;
        if(subject.getClass() == FlyableHuman.class && target.getClass() == Robber.class) {
            ((Robber) this.target).fearOfFlyableHuman((FlyableHuman) subject);
        }
    }
    public Interaction(String name, Human subject, Human target, boolean negative) {
        this.name = name;
        this.subject = subject;
        this.target = target;
        this.negative = negative;
        if(this.negative = true) {
            target.upCountCondition();
        }
        System.out.println(subject.getName() + " применяет " + name + " к человеку " + target.getName());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Human getSubject() {
        return subject;
    }
    public void setSubject(Human subject) {
        this.subject = subject;
    }
    public Human getTarget() {
        return target;
    }
    public void setTarget(Human target) {
        this.target = target;
    }
    public boolean equals(Object otherObject)  {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Interaction other = (Interaction) otherObject;
        return this.getName().equals(other.getName()) && this.getTarget().equals(other.getTarget()) && this.getSubject().equals(other.getSubject());
    }
    public int hashCode()
    {
        return Objects.hash(name, subject, target);
    }
    public String toString()
    {
        return getClass().getName() + "[name=" + this.getName() + ",target=" + this.getTarget().toString() + ",subject=" + this.getSubject().toString() + "]";
    }
}