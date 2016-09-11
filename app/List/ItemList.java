package app.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Josue on 8/23/2016.
 */
public class ItemList implements ListCellRenderer {
    private Border noFocusBorder = new EmptyBorder(16, 64, 16, 64);

    //    protected static TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(),
//            "title");
    public ItemList() {
        super();
    }

    public ItemList(int top, int left, int bottom, int right) {
        super();
        noFocusBorder = new EmptyBorder(top, left, bottom, right);
    }

    public ItemList(Border border) {
        super();
        noFocusBorder = border;
    }

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        renderer.setBorder(noFocusBorder);
        return renderer;
    }
}
