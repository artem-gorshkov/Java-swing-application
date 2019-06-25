package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Human;

import java.awt.*;
import java.util.List;


public class MyCanvas extends Canvas {
    private List<Human> humans;

    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }
}
