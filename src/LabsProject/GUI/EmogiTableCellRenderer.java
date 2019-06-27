package LabsProject.GUI;

import LabsProject.Nature.Homosapiens.Condition;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class EmogiTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setFont(new Font("Serif", Font.BOLD, 20));
        setHorizontalAlignment(HORIZONTAL);
        setText(MyModelTable.getSmile((Condition) value));
        return this;
    }
}
