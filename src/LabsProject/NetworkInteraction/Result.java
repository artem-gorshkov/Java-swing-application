package LabsProject.NetworkInteraction;

import LabsProject.Nature.Homosapiens.Human;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    public Result(String answer, List<Human> humans) {
        this.answer = answer;
        this.humans = humans;
    }
    public Result(List<Human> humans) {
        this.humans = humans;
    }
    public Result(String answer) {this.answer = answer;}
    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }

    private List<Human> humans;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;
}
