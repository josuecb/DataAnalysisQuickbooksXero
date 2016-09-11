
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import app.*;
import app.List.ItemList;
import app.actionBar.ActionBarPanels;
import app.actionBar.JActionBarFrame;
import app.behavior.AppPartType;
import app.behavior.Behavior;
import app.behavior.InvoiceReport;
import bin.Font.FontType;
import bin.Font.Fonts;
import com.sun.awt.AWTUtilities;

/**
 * Created by Josue on 8/19/2016.
 */
public class App extends JActionBarFrame implements MouseListener {
    private JButton connectButton;
    private JPanel panel;
    private JPanel CTfitstContext;
    private JPanel contextBottom;
    private JList timeSessionList;
    private JLabel titleApp;
    private JPanel titleContainer;
    private JPanel contentContainer;
    private JPanel innerContainer;
    private JPanel dateContainer;
    private JPanel userIdContainer;
    private JButton dateButton;
    private JButton userIdButton;
    private JList userIdList;
    private JPanel panelContainer;
    private JPanel contextLayout;
    private JLabel selectedSessionText;
    private JPanel CBfirstContext;

    private JPanel CBsecondContext;
    private JPanel div2_panel2_child;
    private JPanel div2_panel2_child1;
    private JPanel div2_panel2_child2;
    private JButton analyzeButton;
    private JPanel contextTop;
    private JPanel CTsecondContext;
    private JPanel reportHeader;
    private JLabel div1_child1_title;
    private JPanel div1_child1_options;
    private JComboBox selectOption;
    private JPanel report_inner_body;
    private JList reportEntityList;
    private JPanel analysis_inner_body;
    private JPanel reportBody;
    private JList analysisMinReportList;
    private JList analysisMaxReportList;
    private JPanel welcomeLayout;
    private JPanel welcomeMessage;
    private JPanel welcomeOptions;
    private JPanel welcomeTitleContainer;
    private JPanel welcomeAdditionalContentContainer;
    private JButton buildDatabaseButton;
    private JButton connectionButton;
    private JLabel selectOptionMessage;
    private JPanel optionMessageContainer;
    private JLabel welcomeTitleLabel;
    private JPanel welcomeTitleLabelWraper;
    private JLabel acTextArea;
    private HintTextField username, password, server, port, dbName;
    private JPanel additionalContentBody;
    private JPanel additionalContentFooter;
    private JPanel acTextAreaContainer;
    private JPanel acInputContainer;
    private JPanel usernameContainer;
    private JPanel passwordContainer;
    private JPanel selectOptionMessageContainer;
    private JPanel builButtonContainer;
    private JPanel soMessageContainer;
    private JPanel actionBar;
    private JPanel opButtonsContainer;
    private JPanel toolButtonContainer;
    private JPanel minButtonContainer;
    private JPanel maxButtonContainer;
    private JPanel closeButtonContainer;
    private JPanel opExtraContainer;
    private JPanel actionBarTab;
    private JPanel actionBarUtils;
    private JPanel bodyContainer;
    private JPanel leftUtils;
    private JPanel centerUtils;
    private JPanel rightUtils;
    private JPanel backButton1;
    private JPanel forwardButton1;
    private JPanel luBackForButton;
    private JPanel luExtraSpace;
    private JPanel appTitlleCenter;
    private JLabel appTitleLabel;
    private JPanel appTitleLeft;
    private JPanel appTitleRight;
    private JPanel serverContainer;
    private JPanel portContainer;
    private JScrollPane minListContainer;
    private JScrollPane maxListContainer;
    private JPanel dbNameContainer;
    private JPanel dateRowContainer;
    private boolean isConnected = false;
    private Behavior behavior;
    private App app = this;
    private int pX, pY;

    public App() {

        setUpBehavior();
        behavior.setAppContext(app);
        behavior.onAnalyzePressed(reportEntityList, analyzeButton, CTfitstContext, CTsecondContext);
        behavior.onOptionDropMenu(selectOption, analyzeButton);
    }

    public void setUpBehavior() {
        HashMap<AppPartType, JComponent> components = new HashMap<>();

        components.put(AppPartType.BD_WELCOME_LAY, this.welcomeLayout);
        components.put(AppPartType.BD_CONTEXT_LAY, this.contextLayout);
        components.put(AppPartType.DB_CONTEXT_TOP, this.contextTop);
        components.put(AppPartType.DB_CONTEXT_BOTTOM, this.contextBottom);
        components.put(AppPartType.RP_INNER_BODY, this.report_inner_body);
        components.put(AppPartType.ANLYS_INNER_BODY, this.analysis_inner_body);
        components.put(AppPartType.RP_INNER_BODY_LIST, this.reportEntityList);
        components.put(AppPartType.ANLYS_INNER_BODY_MIN_LIST, this.analysisMinReportList);
        components.put(AppPartType.ANLYS_INNER_BODY_MAX_LIST, this.analysisMaxReportList);
        components.put(AppPartType.WC_CONNECT, this.connectionButton);
        components.put(AppPartType.WC_BUILD_DB, this.buildDatabaseButton);
        components.put(AppPartType.CT_FIRST_CONTEXT, this.CTfitstContext);
        components.put(AppPartType.CT_SECOND_CONTEXT, this.CTsecondContext);

        this.behavior = new Behavior(components);
    }


    private void setTextFields() {
        HashMap<AppPartType, JComponent> components = new HashMap<>();

        components.put(AppPartType.DB_USERNAME, this.username);
        components.put(AppPartType.DB_PASSWORD, this.password);
        components.put(AppPartType.DB_DOMAIN, this.server);
        components.put(AppPartType.DB_PORT, this.port);
        components.put(AppPartType.DB_NAME, this.dbName);
        this.behavior.setTextFields(components);
    }

    @Override
    public void setContentPane(Container contentPane) {
        setWelcomeLayout();
        super.setContentPane(contentPane);
        // main window 50% of full screen
        Dimension mainLayoutDimension = Helpers.percentageFromParent(Helpers.getFullScreenSize(), 50, 50);
        panel.setPreferredSize(mainLayoutDimension);

        // 50 % of main window
        Dimension division1Dim = Helpers.percentageFromParent(mainLayoutDimension, 100, 50);
        contextTop.setPreferredSize(division1Dim);
        contextBottom.setPreferredSize(division1Dim);

        titleApp.setFont(Helpers.fontSize(32));
        titleApp.setBorder(BorderFactory.createCompoundBorder(
                titleApp.getBorder(),
                BorderFactory.createEmptyBorder(16, 8, 16, 8)));

        // 50% of mainWindow
        Dimension buttonDimension = Helpers.percentageFromParent(mainLayoutDimension, 20, 5);

        connectButton.setName("connectBtn2");
        connectButton.addMouseListener(this);

        analyzeButton.setName("anaBtn");
        analyzeButton.addMouseListener(this);

        Helpers.plainButton(connectButton, new Dimension((int) buttonDimension.getWidth(), 75));
        Helpers.plainButton(analyzeButton, new Dimension((int) buttonDimension.getWidth(), 75));

        selectedSessionText.setFont(Helpers.fontSize(32));

        Helpers.plainButton(dateButton, new Dimension((int) Helpers.percentageFromParent(division1Dim, 50, 100).getWidth(), 50));
        dateButton.setName("dateBtn");
        dateButton.addMouseListener(this);
        Helpers.plainButton(userIdButton, new Dimension((int) Helpers.percentageFromParent(division1Dim, 50, 100).getWidth(), 50));
        userIdButton.setName("userIdBtn");
        userIdButton.addMouseListener(this);

        timeSessionList.setFont(Helpers.fontSize(21));
        userIdList.setFont(Helpers.fontSize(21));

        contentContainer.setPreferredSize(Helpers.percentageFromParent(division1Dim, 100, 100));
        innerContainer.setPreferredSize(Helpers.percentageFromParent(division1Dim, 100, 100));
        innerContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 100)));

        dateContainer.setPreferredSize(Helpers.percentageFromParent(division1Dim, 50, 100));
        dateContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0, 0, 0, 100)));

        userIdContainer.setPreferredSize(Helpers.percentageFromParent(division1Dim, 50, 100));

        timeSessionList.setPreferredSize(Helpers.percentageFromParent(division1Dim, 50, 100));
        contextTop.setVisible(false);

        CTsecondContext.setPreferredSize(Helpers.percentageFromParent(division1Dim, 100, 100));

        titleContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100, 100)));

        reportHeader.setPreferredSize(Helpers.percentageFromParent(division1Dim, 100, 30));
        reportHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100, 100)));

        Dimension reportBodyDimensions = Helpers.percentageFromParent(division1Dim, 100, 70);
        reportBody.setPreferredSize(reportBodyDimensions);
        report_inner_body.setPreferredSize(reportBodyDimensions);
        analysis_inner_body.setPreferredSize(reportBodyDimensions);

        analysisMinReportList.setCellRenderer(new ItemList());
        analysisMinReportList.setFont(Helpers.fontSize(24, FontType.REGULAR));
        analysisMinReportList.setSelectionForeground(new Color(250, 250, 250));
        analysisMinReportList.setSelectionBackground(new Color(7, 100, 210));

        analysisMaxReportList.setCellRenderer(new ItemList());
        analysisMaxReportList.setFont(Helpers.fontSize(24, FontType.REGULAR));
        analysisMaxReportList.setSelectionForeground(new Color(250, 250, 250));
        analysisMaxReportList.setSelectionBackground(new Color(7, 100, 210));

        reportEntityList.setPreferredSize(Helpers.percentageFromParent(division1Dim, 100, 90));

        div1_child1_title.setPreferredSize(Helpers.percentageFromParent(mainLayoutDimension, 100, 50));
        div1_child1_title.setFont(Helpers.fontSize(32));
        div1_child1_title.setBorder(BorderFactory.createCompoundBorder(
                div1_child1_title.getBorder(),
                BorderFactory.createEmptyBorder(8, 32, 8, 32)));

//        selectOption.setPreferredSize();
        selectOption.setBorder(BorderFactory.createCompoundBorder(
                selectOption.getBorder(),
                BorderFactory.createEmptyBorder(8, 32, 8, 32)));

        selectOption.addItem(new ComboItem(InvoiceReport.INVOICE_REPORT));
        selectOption.addItem(new ComboItem(InvoiceReport.PARTIAL_PAID_MIN_MAX_DAYS_REPORT));
        selectOption.addItem(new ComboItem(InvoiceReport.PAID_MIN_MAX_DAYS_REPORT));

        selectOption.setFont(Helpers.fontSize(24, FontType.BOLD));
//        selectOption.setForeground(new Color(250, 250, 250));
        selectOption.setRenderer(new ItemList(8, 16, 8, 16));

        reportEntityList.setCellRenderer(new ItemList());
        reportEntityList.setFont(Helpers.fontSize(24, FontType.REGULAR));
        reportEntityList.setSelectionForeground(new Color(250, 250, 250));
        reportEntityList.setSelectionBackground(new Color(7, 100, 210));
    }

    public void setWelcomeLayout() {
        Dimension mainLayoutDimension = Helpers.percentageFromParent(Helpers.getFullScreenSize(), 50, 50);

        // Setting up ActionBar
        this.setActionBar(Helpers.percentageFromParent(mainLayoutDimension, 100, 15));

        Dimension mainBodyLayoutDimension = Helpers.percentageFromParent(mainLayoutDimension, 100, 85);
        panelContainer.setPreferredSize(mainBodyLayoutDimension);
        panelContainer.setBackground(Helpers.RGBA(0, 0, 0, 0));

        welcomeLayout.setPreferredSize(mainBodyLayoutDimension);

        Dimension welcomeTitleContainerDimensions = Helpers.percentageFromParent(mainBodyLayoutDimension, 50, 100);
        welcomeMessage.setPreferredSize(welcomeTitleContainerDimensions);

        welcomeOptions.setPreferredSize(welcomeTitleContainerDimensions);
        welcomeOptions.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0, 0, 0, 100)));

        Dimension titleContainerDimensions = Helpers.percentageFromParent(welcomeTitleContainerDimensions, 100, 25);
        welcomeTitleContainer.setPreferredSize(titleContainerDimensions);

        Dimension additionalContainerDimension = Helpers.percentageFromParent(welcomeTitleContainerDimensions, 100, 75);
        welcomeAdditionalContentContainer.setPreferredSize(additionalContainerDimension);

        welcomeTitleLabelWraper.setBackground(Helpers.RGBA(1, 1, 1, 0));
        welcomeTitleLabel.setPreferredSize(Helpers.percentageFromParent(titleContainerDimensions, 50, 40));

        welcomeTitleLabel.setFont(Helpers.fontSize(42));
        welcomeOptions.setPreferredSize(welcomeTitleContainerDimensions);

        soMessageContainer.setPreferredSize(Helpers.percentageFromParent(welcomeTitleContainerDimensions, 100, 15));
        soMessageContainer.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0, 100)));
        selectOptionMessage.setFont(Helpers.fontSize(36));

        builButtonContainer.setPreferredSize(Helpers.percentageFromParent(welcomeTitleContainerDimensions, 100, 84));

        Helpers.plainButton(connectionButton, Helpers.percentageFromParent(additionalContainerDimension, 100, 10));
        Helpers.plainButton(buildDatabaseButton, Helpers.percentageFromParent(additionalContainerDimension, 100, 10));

        // Additional Content
        Dimension acBodyDimensions = Helpers.percentageFromParent(additionalContainerDimension, 100, 80);
        additionalContentBody.setPreferredSize(acBodyDimensions);

        acTextAreaContainer.setPreferredSize(Helpers.percentageFromParent(acBodyDimensions, 85, 20));

        acTextArea.setMaximumSize(Helpers.percentageFromParent(acBodyDimensions, 85, 20));
        acTextArea.setPreferredSize(Helpers.percentageFromParent(acBodyDimensions, 85, 20));
        acTextArea.setFont(Helpers.fontSize(28, FontType.REGULAR));
        String textAreaString = "Enter root user and password to use database MYSQL support Only";

        acTextArea.setText(Helpers.wrapperText(textAreaString));

        acTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Additional Inputs

        Dimension acInputContainerDimension = Helpers.percentageFromParent(acBodyDimensions, 100, 80);
        acInputContainer.setPreferredSize(acBodyDimensions);

        server = new HintTextField("Server (http://localhost.com)");
        port = new HintTextField("Port (3306)");
        username = new HintTextField("Username");
        password = new HintTextField("Password");
        dbName = new HintTextField("Database Name");
        password.simulatePasswordField();

        Helpers.customTextField(username, acInputContainerDimension);
        Helpers.customTextField(password, acInputContainerDimension);
        Helpers.customTextField(server, acInputContainerDimension);
        Helpers.customTextField(port, acInputContainerDimension);
        Helpers.customTextField(dbName, acInputContainerDimension);

        setTextFields();
        // Additional Footer
        additionalContentFooter.setPreferredSize(Helpers.percentageFromParent(additionalContainerDimension, 100, 20));

        usernameContainer.add(username);
        passwordContainer.add(password);
        serverContainer.add(server);
        portContainer.add(port);
        dbNameContainer.add(dbName);
    }

    public void setContentPane() {
        this.setTitle("Data Analysis App");
        setContentPane(this.panel);
        setUndecorated(true);
//        setLocationRelativeTo(null);
        setLocation(Helpers.centerApp(this));
        AWTUtilities.setWindowOpacity(this, 0.95f);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();
            }

            public void mouseDragged(MouseEvent me) {

                app.setLocation(app.getLocation().x + me.getX() - pX,
                        app.getLocation().y + me.getY() - pY);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {

                app.setLocation(app.getLocation().x + me.getX() - pX,
                        app.getLocation().y + me.getY() - pY);

            }
        });

        this.registerFont();

    }

    private void registerFont() {
        Fonts.addFont(new Fonts("Lato-Light.ttf"));
        Fonts.addFont(new Fonts("Lato-Thin.ttf"));
        Fonts.addFont(new Fonts("Lato-Regular.ttf"));
        Fonts.addFont(new Fonts("Lato-Bold.ttf"));
    }

    private void setActionBar(Dimension windowDimensions) {
        HashMap<ActionBarPanels, Component> panels = new HashMap<>();
        panels.put(ActionBarPanels.AC_CONTAINER, actionBar);

        panels.put(ActionBarPanels.AC_TOOL_CONTAINER, toolButtonContainer);
        panels.put(ActionBarPanels.AC_OP_CONTAINER, opButtonsContainer);
        panels.put(ActionBarPanels.AC_X_CONTAINER, closeButtonContainer);
        panels.put(ActionBarPanels.AC_MIN_CONTAINER, minButtonContainer);
        panels.put(ActionBarPanels.AC_MAX_CONTAINER, maxButtonContainer);
        panels.put(ActionBarPanels.AC_RIGHT_EXTRA, opExtraContainer);
        panels.put(ActionBarPanels.AC_TAB, actionBarTab);
        panels.put(ActionBarPanels.AC_UTILS, actionBarUtils);
        panels.put(ActionBarPanels.AC_BACK_BTN, backButton1);
        panels.put(ActionBarPanels.AC_FWD_BTN, forwardButton1);
        panels.put(ActionBarPanels.AC_LEFT_UTIL, leftUtils);
        panels.put(ActionBarPanels.AC_CENTER_UTIL, centerUtils);
        panels.put(ActionBarPanels.AC_RIGHT_UTIL, rightUtils);
        panels.put(ActionBarPanels.AC_APP_TITLE, appTitleLabel);

        initializeActionBar(panels, windowDimensions);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        JComponent component = (JComponent) e.getSource();
        switch (component.getName()) {
            case "dateBtn":
                Helpers.activeButton((JButton) component);
                break;
            case "userIdBtn":
                Helpers.activeButton((JButton) component);
                break;
            case "connectBtn2":
                Helpers.activeButton((JButton) component);
                break;
            case "anaBtn":
                Helpers.activeButton((JButton) component);
                break;
        }
    }

    private void secondConnectButtonPressed() {

        if (isConnected) {
            connectButton.setText("Connect");
            contextTop.show(false);
            CBfirstContext.show(false);

            isConnected = false;
        } else {
            connectButton.setText("Disconnect");
            CBsecondContext.show(true);

            behavior.populate(timeSessionList, userIdList, analyzeButton, selectedSessionText);

            contextTop.setVisible(true);
            isConnected = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        JComponent component = (JComponent) e.getSource();
        switch (component.getName()) {
            case "dateBtn":
                Helpers.inactiveColor((JButton) component);
                break;
            case "userIdBtn":
                Helpers.inactiveColor((JButton) component);
                break;
            case "connectBtn2":
                Helpers.inactiveColor((JButton) component);
                secondConnectButtonPressed();
                break;
            case "anaBtn":
                Helpers.inactiveColor((JButton) component);
                break;
        }
    }

}
