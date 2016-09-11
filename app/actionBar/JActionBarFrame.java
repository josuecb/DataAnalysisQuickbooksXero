package app.actionBar;

import app.Helpers;
import bin.Font.FontType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Created by Josue on 8/21/2016.
 */
public class JActionBarFrame extends JFrame implements MouseListener {
    private JPanel actionBarContainer;
    private JPanel opButtonContainer, toolButtonContainer;
    private JPanel minP, maxP, xP, exP;
    private Dimension windowsDimension;
    private Dimension acTabDimensions, acUtilsDimension;
    private Dimension tabLeftDividerDimensions, tabRightDividerDimensions;
    private JPanel acTabP, acUtilsP;
    private JLabel xIcon, minIcon, maxIcon;
    private Icon xImg, xImgActive, minImg, minImgActive, maxImg, maxImgActive;
    private JLabel bIcon, fIcon;
    private Icon bImg, bImgActive, bImgDisabled, fImg, fImgActive, fImgDisabled;
    private JActionBarFrame app;
    private boolean isMaximized = false;
    private Point previousAppLocation;
    private JPanel backButton, forwardButton;
    private JPanel leftUtilP, centerUtilP, rightUtilP;
    private boolean backIconActivated = false, forwardIconActivated = false;
    private JLabel appTitle;

    public void initializeActionBar(HashMap<ActionBarPanels, Component> panels, Dimension winDimensions) {
        this.windowsDimension = winDimensions;
        this.app = this;
        this.acTabDimensions = Helpers.percentageFromParent(this.windowsDimension, 100, 35);
        this.acUtilsDimension = Helpers.percentageFromParent(this.windowsDimension, 100, 65);

        this.tabLeftDividerDimensions = Helpers.percentageFromParent(this.acTabDimensions, 80, 100);
        this.tabRightDividerDimensions = Helpers.percentageFromParent(this.acTabDimensions, 20, 100);

        this.actionBarContainer = (JPanel) panels.get(ActionBarPanels.AC_CONTAINER);

        this.opButtonContainer = (JPanel) panels.get(ActionBarPanels.AC_OP_CONTAINER);
        this.toolButtonContainer = (JPanel) panels.get(ActionBarPanels.AC_TOOL_CONTAINER);
        this.minP = (JPanel) panels.get(ActionBarPanels.AC_MIN_CONTAINER);
        this.maxP = (JPanel) panels.get(ActionBarPanels.AC_MAX_CONTAINER);
        this.xP = (JPanel) panels.get(ActionBarPanels.AC_X_CONTAINER);
        this.exP = (JPanel) panels.get(ActionBarPanels.AC_RIGHT_EXTRA);
        this.acTabP = (JPanel) panels.get(ActionBarPanels.AC_TAB);
        this.acUtilsP = (JPanel) panels.get(ActionBarPanels.AC_UTILS);
        this.backButton = (JPanel) panels.get(ActionBarPanels.AC_BACK_BTN);
        this.forwardButton = (JPanel) panels.get(ActionBarPanels.AC_FWD_BTN);
        this.leftUtilP = (JPanel) panels.get(ActionBarPanels.AC_LEFT_UTIL);
        this.centerUtilP = (JPanel) panels.get(ActionBarPanels.AC_CENTER_UTIL);
        this.rightUtilP = (JPanel) panels.get(ActionBarPanels.AC_RIGHT_UTIL);
        this.appTitle = (JLabel) panels.get(ActionBarPanels.AC_APP_TITLE);

        this.actionBarContainer.setPreferredSize(this.windowsDimension);


        actionBarContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100, 50)));
        this.appTitle.setText("Analysis App");
        this.appTitle.setFont(Helpers.fontSize(28, FontType.REGULAR));

        populateOpContainer();
        populateUtilsContainer();
    }


    private void populateOpContainer() {
        this.acTabP.setPreferredSize(this.acTabDimensions);
        this.acUtilsP.setPreferredSize(this.acUtilsDimension);

        this.toolButtonContainer.setPreferredSize(this.tabLeftDividerDimensions);
        this.toolButtonContainer.setBackground(new Color(0, 0, 0, 255));
        this.opButtonContainer.setPreferredSize(this.tabRightDividerDimensions);

        this.exP.setPreferredSize(Helpers.percentageFromParent(this.tabRightDividerDimensions, 40, 100));

        Dimension buttonDimensions = Helpers.percentageFromParent(this.tabRightDividerDimensions, 20, 100);

        setIcons(buttonDimensions);

        this.xP.setPreferredSize(buttonDimensions);

        this.xP.add(xIcon);
        int xpRatio = Helpers.getPaddingRatio(buttonDimensions, 20);
        this.xP.setBorder(new EmptyBorder(xpRatio, xpRatio, xpRatio, xpRatio));
        Helpers.centerAlign(this.xP);


        this.maxP.setPreferredSize(buttonDimensions);
        this.maxP.add(maxIcon);
        this.maxP.setBorder(new EmptyBorder(xpRatio, xpRatio, xpRatio, xpRatio));

        Helpers.centerAlign(this.maxP);


        this.minP.setPreferredSize(buttonDimensions);
        this.minP.add(minIcon);
        this.minP.setBorder(new EmptyBorder(xpRatio, xpRatio, xpRatio, xpRatio));

        Helpers.centerAlign(this.minP);
    }

    private void populateUtilsContainer() {
        this.leftUtilP.setPreferredSize(Helpers.percentageFromParent(this.acUtilsDimension, 20, 100));
        this.centerUtilP.setPreferredSize(Helpers.percentageFromParent(this.acUtilsDimension, 55, 100));
        this.rightUtilP.setPreferredSize(Helpers.percentageFromParent(this.acUtilsDimension, 20, 100));

        this.backButton.add(bIcon);
        this.backButton.setBorder(new EmptyBorder(0, 24, 0, 0));
        Helpers.centerAlign(this.backButton);

        this.forwardButton.add(fIcon);
        Helpers.centerAlign(this.forwardButton);
    }

    private void createIconImages(Dimension buttonDimensions) {
        Dimension tbDimensions = new Dimension(28, 28);
        Dimension bfDimensions = new Dimension(54, 54);

        xImg = Helpers.generateImage("close_button.png", tbDimensions, 100);
        xImgActive = Helpers.generateImage("close_button_active.png", tbDimensions, 100);

        minImg = Helpers.generateImage("min_button.png", tbDimensions, 100);
        minImgActive = Helpers.generateImage("min_button_active.png", tbDimensions, 100);

        maxImg = Helpers.generateImage("max_button.png", tbDimensions, 100);
        maxImgActive = Helpers.generateImage("max_button_active.png", tbDimensions, 100);


        bImg = Helpers.generateImage("back_button.png", bfDimensions, 100);
        bImgActive = Helpers.generateImage("back_button_active.png", bfDimensions, 100);
        bImgDisabled = Helpers.generateImage("back_button_disabled.png", bfDimensions, 100);

        fImg = Helpers.generateImage("forward_button.png", bfDimensions, 100);
        fImgActive = Helpers.generateImage("forward_button_active.png", bfDimensions, 100);
        fImgDisabled = Helpers.generateImage("forward_button_disabled.png", bfDimensions, 100);
    }

    private void setIcons(Dimension buttonDimensions) {
        createIconImages(buttonDimensions);

        this.xIcon = new JLabel();
        this.xIcon.setName("xIcon");
        this.xIcon.setIcon(xImg);
        this.xIcon.addMouseListener(this);

        this.minIcon = new JLabel();
        this.minIcon.setName("minIcon");
        this.minIcon.setIcon(minImg);
        this.minIcon.addMouseListener(this);

        this.maxIcon = new JLabel();
        this.maxIcon.setName("maxIcon");
        this.maxIcon.setIcon(maxImg);
        this.maxIcon.addMouseListener(this);

        this.bIcon = new JLabel();
        this.bIcon.setName("bIcon");
        this.bIcon.setIcon(bImgDisabled);
        this.bIcon.addMouseListener(this);

        this.fIcon = new JLabel();
        this.fIcon.setName("fIcon");
        this.fIcon.setIcon(fImgDisabled);
        this.fIcon.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            JComponent label = (JLabel) e.getSource();
            switch (label.getName()) {
                case "bIcon":
                    bIcon.setIcon(bImgActive);
                    break;
                case "fIcon":
                    fIcon.setIcon(fImgActive);
                    break;
            }
        } catch (Exception ex) {

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            JLabel label = (JLabel) e.getSource();
            switch (label.getName()) {
                case "xIcon":
                    this.app.dispose();
                    break;
                case "minIcon":
                    this.app.setState(JFrame.ICONIFIED);
                    break;
                case "maxIcon":
                    if (isMaximized) {
                        this.app.setSize(Helpers.percentageFromParent(this.app.getPreferredSize(), 100, 100));
                        this.app.setLocation(previousAppLocation);
                        isMaximized = false;
                    } else {
                        this.app.setSize(Helpers.percentageFromParent(this.app.getPreferredSize(), 200, 200));
                        this.previousAppLocation = this.app.getLocation();
                        this.app.setLocation(0, 0);
                        isMaximized = true;
                    }
                    break;
                case "bIcon":
                    if (backIconActivated)
                        bIcon.setIcon(bImg);
                    else
                        bIcon.setIcon(bImgDisabled);
                    break;
                case "fIcon":
                    if (forwardIconActivated)
                        fIcon.setIcon(fImg);
                    else
                        fIcon.setIcon(fImgDisabled);
                    break;
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            JLabel l = (JLabel) e.getSource();
            switch (l.getName()) {
                case "xIcon":
                    xIcon.setIcon(xImgActive);
                    break;
                case "minIcon":
                    minIcon.setIcon(minImgActive);
                    break;
                case "maxIcon":
                    maxIcon.setIcon(maxImgActive);
                    break;
            }
        } catch (Exception ex) {

        }

        app.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            JLabel label = (JLabel) e.getSource();
            switch (label.getName()) {
                case "xIcon":
                    xIcon.setIcon(xImg);
                    break;
                case "minIcon":
                    minIcon.setIcon(minImg);
                    break;
                case "maxIcon":
                    maxIcon.setIcon(maxImg);
                    break;
            }
        } catch (Exception ex) {

        }

        app.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
