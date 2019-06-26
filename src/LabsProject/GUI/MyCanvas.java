package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;

import java.awt.*;
import java.util.List;


public class MyCanvas extends Canvas {
    private List<Human> humans;

    public MyCanvas(List<Human> humans) {
        super();
        this.humans = humans;
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setBounds(0, 0, 600, 400);
    }

    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //drawMan(g, 50, 50, 50, Color.GREEN);
        //drawMan(g, 150, 150, 50, Color.GREEN);
        if (humans != null && !humans.isEmpty()) {
            humans.forEach(human -> {
                drawMan(g, (int) human.getCordX(), (int) human.getCordY(), 100, new Color(human.getColor()), human.getCondition());
            });
        }
    }

    private void drawMan(Graphics g, int x, int y, int size, Color color, Condition cond) {
        String emo = null;
        switch (cond) {
            case ALARM:
                emo = "\uD83D\uDE12";
                break;
            case SCARED:
                emo = "\uD83D\uDE1E";
                break;
            case CALM:
                emo = "\uD83D\uDE10";
                break;
        }
        char[] emoji = emo.toCharArray();


        Color previous = g.getColor();
        int diameter = size / 4;

        Point head = new Point(x + size / 8, y - size);
        Point neck = new Point(x + size / 4, y - size * 3 / 4);

        Point leftHand = new Point(x, y - size * 3 / 8);
        Point rightHand = new Point(x + size / 2, y - size * 3 / 8);

        Point torso = new Point(x + size / 4, y - size * 3 / 8);
        Point leftFoot = new Point(x, y);
        Point rightFoot = new Point(x + size / 2, y);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));

        g2.setColor(color);
        g2.drawLine(neck.x, neck.y, leftHand.x, leftHand.y); // left hand
        g2.drawLine(neck.x, neck.y, rightHand.x, rightHand.y); // right hand
        g2.drawLine(neck.x, neck.y, torso.x, torso.y); // torso
        g2.drawLine(torso.x, torso.y, leftFoot.x, leftFoot.y); // left foot
        g2.drawLine(torso.x, torso.y, rightFoot.x, rightFoot.y); // right foot
        //g.fillOval(head.x, head.y, diameter, diameter); // head
        g2.setFont(new Font("Serif", Font.PLAIN, size/2));
        g2.drawChars(emoji, 0, 2, head.x-(3*(size/50)), head.y+(12*(size/50)));
        g2.setColor(previous);
    }

}
