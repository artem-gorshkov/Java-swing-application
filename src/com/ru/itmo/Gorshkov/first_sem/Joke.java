package com.ru.itmo.Gorshkov.first_sem;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.lang.String;

public class Joke extends Interaction {
    private String topic;
    private String text;
    private static String[][] Jokelist;
    static {
        Scanner in = null;
        try {
            in = new Scanner(Paths.get("C:\\Users\\HP\\Desktop\\prog\\lab3_prog\\Jokelist.txt"), "windows-1251");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Jokelist = new String[15][2];
        for (int i = 0; i < 15; i++) {
            String str = in.nextLine();
            Jokelist[i][0] = str.substring(0, str.indexOf(' ',2));
            str = str.substring( str.indexOf(' ',2));
            Jokelist[i][1] = str;
        }
    }
    public Joke(Human subject, Human target) {
        super(subject,target);
        Random random = new Random();
        int count = random.nextInt(15);
        this.topic = Jokelist[count][0];
        this.text = Jokelist[count][1];
        super.setName(this.topic);
        if (this.getTopic().equals("о маме") && this.getTarget().getName().equals("Малыш")) {
            System.out.println(this.getSubject().getName() + "  хотел пошутить " + this.getTopic() + " но " + this.getTarget().getName() + " остановил его");
        }
        else {
            System.out.println(this.getSubject().getName() + " рассказывает человеку " + this.getTarget().getName() + " шутку " + this.getTopic() + " : " + this.getText());
            this.getTarget().upCountCondition();
        }
    }
    public String getTopic() {
        return this.topic;
    }
    public String getText() {
        return this.text;
    }
    public boolean equals(Object otherObject)  {
        Joke other = (Joke) otherObject;
        return super.equals(other) && this.getTopic().equals(other.getTopic()) && this.getText().equals(other.getText());
    }
    public int hashCode()
    {
        return Objects.hash(this.getName(), this.getSubject(), this.getTarget(), text, topic);
    }
    public String toString() {
        return super.toString() + "[topic=" + this.getTopic() + ",text=" + this.getText() + "]";
    }
}
