package LabsProject.GUI;

import LabsProject.Commands.SendAndChange;
import LabsProject.Nature.Homosapiens.Condition;
import LabsProject.Nature.Homosapiens.Human;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

import static LabsProject.Nature.Homosapiens.Condition.*;

public class MyModelTable extends AbstractTableModel {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    private List<Human> humans;
    private MyJFrame frame;
    ResourceBundle resource = ResourceBundle.getBundle("GuiLabels");

    public MyModelTable(List<Human> h, MyJFrame frame) {
        humans = h;
        this.frame = frame;
    }

    public int getColumnCount() {
        return 5;
    }

    public int getRowCount() {
        if (humans != null && !humans.isEmpty()) {
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
                return human.getBirthday().format(formatter);
            case 3:
                return human.getBirthday().getZone();
            case 4:
                return human.getCondition();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int r, int c) {
        Human human = humans.get(r);
        String str = (String) value;
        switch (c) {
            case 2:
                ZonedDateTime zonedDateTime = human.getBirthday();
                LocalDateTime time = LocalDateTime.parse(str, formatter);
                ZonedDateTime newtime = ZonedDateTime.ofLocal(time, human.getBirthday().getZone(), human.getBirthday().getOffset());
                if (!zonedDateTime.equals(newtime)) {
                    human.setBirthday(newtime);
                    frame.updateColl(frame.getConn().sendAndGetAnswer(new SendAndChange(null, human)).getHumans());
                }
                break;
            case 3:
                try {
                    ZonedDateTime zonedDateTime1 = human.getBirthday();
                    ZonedDateTime newtime1 = ZonedDateTime.ofInstant(human.getBirthday().toInstant(), ZoneId.of(str));
                    if (!zonedDateTime1.equals(newtime1)) {
                        human.setBirthday(newtime1);
                        frame.updateColl(frame.getConn().sendAndGetAnswer(new SendAndChange(null, human)).getHumans());
                    }
                } catch (Throwable e) {
                }
                break;
            case 4:
                Condition cond = human.getCondition();
                Condition newcond = getCond(str);
                if (!cond.equals(newcond)) {
                    human.setCondition(newcond);
                    frame.updateColl(frame.getConn().sendAndGetAnswer(new SendAndChange(null, human)).getHumans());
                }
                break;
            default:
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
            case 4:
                return resource.getString("c5");
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return (humans.get(r).getColor() == frame.getConfig().getColor()) && c != 0 && c != 1;
    }

    public Class getColumnClass(int columnindex) {
            switch (columnindex) {
                case 0:
                    Integer is = 5;
                    return is.getClass();
                case 1:
                case 2:
                case 3:
                    String s = "s";
                    return s.getClass();
                case 4:
                    Condition condition = ALARM;
                    return condition.getClass();
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

    public static String getSmile(Condition cond) {
        switch (cond) {
            case ALARM:
                return "\uD83D\uDE12";
            case SCARED:
                return "\uD83D\uDE1E";
            case CALM:
                return "\uD83D\uDE10";
            case AMAZED:
                return "\uD83D\uDE00";
            default:
                return null;
        }
    }

    public static Condition getCond(String str) {
        switch (str) {
            case "\uD83D\uDE12":
                return ALARM;
            case "\uD83D\uDE1E":
                return SCARED;
            case "\uD83D\uDE10":
                return CALM;
            case "\uD83D\uDE00":
                return AMAZED;
            default:
                return null;
        }
    }
}
