package app;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Josue on 8/19/2016.
 */
public class RoundedBorder implements Border {

    private int radius;
    private Color color;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius+2);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (this.color != null)
            g.setColor(this.color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
