package app;

import bin.Font.FontType;
import bin.Resources.ImageReference;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.metal.MetalComboBoxButton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Josue on 8/19/2016.
 */

public class Helpers {
    public static Dimension getFullScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static Dimension percentageFromParent(Dimension parentLayout, int widthPercentage, int heightPercentage) {
        int width = (int) parentLayout.getWidth();
        int height = (int) parentLayout.getHeight();

        width = getPercentage(width, widthPercentage);
        height = getPercentage(height, heightPercentage);

        return new Dimension(width, height);
    }

    public static Color RGBA(float R, float G, float B, float A) {
        return new Color(R, G, B, A);
    }

    public static JLabel getImage(String imgName) {
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File(ImageReference.IMG_LOCATION + imgName));
            return new JLabel(new ImageIcon(myPicture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JLabel getImage(String imgName, Dimension dimension, int percentage) {
        return new JLabel(generateImage(imgName, dimension, percentage));
    }

    public static Point centerApp(JFrame app) {
        Dimension dimension = getFullScreenSize();
        Dimension appDim = app.getPreferredSize();

        Point middle = new Point((int) (dimension.getWidth() / 2), (int) (dimension.getHeight() / 2));
        Point midAppDim = new Point((int) (appDim.getWidth() / 2), (int) (appDim.getHeight() / 2));

        return new Point((int) (middle.getX() - midAppDim.getX()),
                (int) (middle.getY() - midAppDim.getY()));
    }

    public static Icon generateImage(String imgName, Dimension dimension, int percentage) {
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File(ImageReference.IMG_LOCATION + imgName));

            int highValue = getLowestValue(dimension);
            int ratio = getPercentage(highValue, percentage);
            return new ImageIcon(myPicture.getScaledInstance(ratio, ratio, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPaddingRatio(Dimension dimension, int percentage) {
        return (int) Helpers.percentageFromParent(dimension, percentage, percentage).getWidth();
    }

    public static int getLowestValue(Dimension dimension) {
        if (dimension.getHeight() == dimension.getWidth()) {
            return (int) dimension.getWidth();
        } else if (dimension.getWidth() < dimension.getHeight()) {
            return (int) dimension.getWidth();
        } else {
            return (int) dimension.getHeight();
        }
    }

    public static void centerAlign(JPanel panel) {
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    }

    private static int getPercentage(int total, int percentage) {
        return (total * percentage) / 100;
    }

    public static Font fontSize(int size) {
        return new Font("Lato Light", Font.PLAIN, size);
    }

    public static Font fontSize(int size, FontType type) {
        if (type.equals(FontType.THIN))
            return new Font("Lato Thin", Font.PLAIN, size);
        else if (type.equals(FontType.REGULAR))
            return new Font("Lato Regular", Font.PLAIN, size);
        else if (type.equals(FontType.BOLD))
            return new Font("Lato Bold", Font.PLAIN, size);
        else
            return new Font("Lato Light", Font.PLAIN, size);
    }

    public static String wrapperText(String textAreaString) {
        return "<html> <div style=\"text-align: center;\">" + textAreaString + "</div></html>";
    }

    public static void plainButton(JButton button, Dimension dimension) {
        if (dimension != null)
            button.setPreferredSize(dimension);
        button.setFont(Helpers.fontSize(24, FontType.BOLD));
        button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 50)));
        inactiveColor(button);
    }

    public static void activeButton(JButton component) {
        component.setContentAreaFilled(false);
        component.setBackground(new Color(7, 100, 210));
        component.setForeground(new Color(250, 250, 250));
        component.setOpaque(true);
    }

    public static void inactiveColor(JButton component) {
        component.setContentAreaFilled(false);
        component.setBackground(new Color(255, 255, 255));
        component.setForeground(new Color(0, 0, 0));
        component.setOpaque(true);
    }

    public static void dropDownMenu(JComboBox selectOption, Color color) {
        selectOption.getEditor().getEditorComponent().setBackground(color);

        Component[] comp = selectOption.getComponents();
        for (int i = 0; i < comp.length; i++) {// hack valid only for Metal L&F
            if (comp[i] instanceof MetalComboBoxButton) {
                MetalComboBoxButton coloredArrowsButton = (MetalComboBoxButton) comp[i];
                coloredArrowsButton.setBackground(null);
                break;
            }
        }
    }

    public static void customTextField(JComponent component, Dimension dimension) {
        component.setPreferredSize(Helpers.percentageFromParent(dimension, 65, 14));
        component.setFont(Helpers.fontSize(26, FontType.REGULAR));
        component.setBorder(BorderFactory.createCompoundBorder(
                component.getBorder(),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
    }
}
