package bin.Font;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Josue on 8/20/2016.
 */
public class Fonts {
    private static ArrayList<Fonts> fontList = new ArrayList<>();

    private static String fontPath;

    public Fonts(String fontPath) {
        Fonts.fontPath = FontReference.FONT_LOCATION + fontPath;
        this.RegisterFont();
    }

    private void RegisterFont() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFont(Fonts fonts) {
        fontList.add(fonts);
//        System.out.println(fonts.toString());
    }
}
