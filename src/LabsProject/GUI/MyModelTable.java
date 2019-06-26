package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

public class MyModelTable extends AbstractTableModel {

    private List<Human> humans;
    ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");

    public MyModelTable(List<Human> h) {
        humans = h;
    }

    public int getColumnCount() {
        return 5;
    }

    public int getRowCount() {
        if(humans!=null && !humans.isEmpty()) {
            return humans.size();
        }
        return 0;
    }

    public Object getValueAt(int row, int col) {
        Human human = humans.get(row);
        switch (col) {
            case 0:
                return row + 1;
            case 1:
                return human.getName();
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                return human.getBirthday().format(formatter);
            case 3:
                return human.getBirthday().getZone();
            case 4:
                switch (human.getCondition()) {
                    case ALARM:
                        return "\uD83D\uDE12";
                    case SCARED:
                        return "\uD83D\uDE1E";
                    case CALM:
                        return "\uD83D\uDE10";
                }
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return resource.getString("c1");
            case 1:
                return resource.getString("c2");
            case 2:
                return resource.getString("c3");
            case 3:
                return resource.getString("c4");
            case 4 :
                return resource.getString("c5");
            default:
                return null;
        }
    }

    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> humans) {
        this.humans = humans;
    }
}
