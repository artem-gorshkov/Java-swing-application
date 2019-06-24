package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Human;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ResourceBundle;

public class MyModelTable extends AbstractTableModel {
    private List<Human> humans;

    ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");
    public MyModelTable(List<Human> h) {
        humans = h;

    }

    public int getColumnCount() {
        return humans.size();
    }

    public int getRowCount() {
        return 4;
    }

    public Object getValueAt(int row, int col) {
        Human human = humans.get(row);
        switch (col) {
            case 0:
                return row + 1;
            case 1:
                return human.getName();
            case 2:
                return human.getBirthday();
            case 3:
                return human.getCondition();
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
