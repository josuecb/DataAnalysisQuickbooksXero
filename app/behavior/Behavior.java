package app.behavior;

import app.ComboItem;
import app.Helpers;
import app.HintTextField;
import app.List.ItemList;
import bin.Analysis.DataAnalysis;
import bin.DataSession.DataSession;
import bin.DataSession.SessionAttributes;
import bin.Font.FontType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * Created by Josue on 8/19/2016.
 */
public class Behavior extends AbstractBehavior implements MouseListener {
    private boolean startUp = true;
    private JPanel welcomeLayout, contextLayout;
    private JButton connectButton, buildDButon;
    private JPanel contextTop, contextBottom;
    private DataSession dataSession;
    private InvoiceReport type = InvoiceReport.INVOICE_REPORT;
    private JPanel reportInnerBody, analysisReportInnerBody;
    private JList reportList, analysisMinList, analysisMaxList;
    private JPanel ctFirstContext, ctSecondContext;
    private JFrame app;
    private boolean storing = false;
    private HintTextField username, password, dbName, domain, port;

    public Behavior(HashMap<AppPartType, JComponent> components) {
        super();
        this.welcomeLayout = (JPanel) components.get(AppPartType.BD_WELCOME_LAY);
        this.contextLayout = (JPanel) components.get(AppPartType.BD_CONTEXT_LAY);
        this.contextTop = (JPanel) components.get(AppPartType.DB_CONTEXT_TOP);
        this.contextBottom = (JPanel) components.get(AppPartType.DB_CONTEXT_BOTTOM);

        this.reportInnerBody = (JPanel) components.get(AppPartType.RP_INNER_BODY);
        this.analysisReportInnerBody = (JPanel) components.get(AppPartType.ANLYS_INNER_BODY);

        this.reportList = (JList) components.get(AppPartType.RP_INNER_BODY_LIST);
        this.analysisMinList = (JList) components.get(AppPartType.ANLYS_INNER_BODY_MIN_LIST);
        this.analysisMaxList = (JList) components.get(AppPartType.ANLYS_INNER_BODY_MAX_LIST);

        this.connectButton = (JButton) components.get(AppPartType.WC_CONNECT);
        this.buildDButon = (JButton) components.get(AppPartType.WC_BUILD_DB);

        this.ctFirstContext = (JPanel) components.get(AppPartType.CT_FIRST_CONTEXT);
        this.ctSecondContext = (JPanel) components.get(AppPartType.CT_SECOND_CONTEXT);

        setNames();
        setListeners();
    }

    public void setTextFields(HashMap<AppPartType, JComponent> components) {
        this.username = (HintTextField) components.get(AppPartType.DB_USERNAME);
        this.password = (HintTextField) components.get(AppPartType.DB_PASSWORD);
        this.domain = (HintTextField) components.get(AppPartType.DB_DOMAIN);
        this.port = (HintTextField) components.get(AppPartType.DB_PORT);
        this.dbName = (HintTextField) components.get(AppPartType.DB_NAME);
    }

    public void setAppContext(JFrame app) {
        this.app = app;
    }

    private void setNames() {
        this.connectButton.setName("btnConnect");
        this.buildDButon.setName("btnBuildDB");

    }

    private void setListeners() {
        this.connectButton.addMouseListener(this);
        this.buildDButon.addMouseListener(this);
    }

    public void populate(JList dateList, JList userIdList, JButton connectButton, JLabel selectedLabel) {
        dateList.setCellRenderer(new ItemList());
        dateList.setFont(Helpers.fontSize(24, FontType.REGULAR));
        dateList.setSelectionForeground(new Color(250, 250, 250));

        userIdList.setCellRenderer(new ItemList());
        userIdList.setFont(Helpers.fontSize(24, FontType.REGULAR));
        userIdList.setSelectionForeground(new Color(250, 250, 250));


        this.populateList(dateList, userIdList, connectButton, selectedLabel);
    }


    @Override
    protected void onItemSelected(int index, JButton button, JLabel selectedLabel) {
        super.onItemSelected(index, button, selectedLabel);
        button.setText("Analyze");
        System.out.println(this.sessions.get(index).getValueFromKey(SessionAttributes.userid));
        selectedLabel.setText("Analyze User With Id: " + this.sessions.get(index).getValueFromKey(SessionAttributes.userid));
        dataSession = this.sessions.get(index);
    }

    public void onAnalyzePressed(JList entityList, JButton analyzeButton, JPanel panel1, JPanel panel2) {
        this.onAnalyzeButton(entityList, analyzeButton, panel1, panel2);
    }

    @Override
    protected void onAnalyzeButtonPressed(JList list, JPanel panel1, JPanel panel2, String type) {
        panel1.setVisible(false);
        panel2.setVisible(true);
        this.populateEntityList(list, dataSession);

        if (type.equals("Go")) {
            this.reportInnerBody.setVisible(false);
            this.reportList.setVisible(false);

            this.analysisReportInnerBody.setVisible(true);
            this.runAnalysis(this.analysisMinList, this.analysisMaxList, this.type);
        }

    }

    public void onOptionDropMenu(JComboBox box, JButton button) {
        this.onOptionSelection(box, button);
    }


    @Override
    protected void onOptionSelected(JComboBox box, JButton button) {
        ComboItem combo = (ComboItem) box.getSelectedItem();
        this.type = combo.getKey();
        this.reportInnerBody.setVisible(false);

        if (this.type.equals(InvoiceReport.INVOICE_REPORT)) {
            if (!startUp) {
                button.setText("Analyze");
                button.setEnabled(false);
            } else {
                button.setText("Go");
                button.setEnabled(true);
            }
            startUp = false;
        } else {
            button.setText("Go");
            button.setEnabled(true);
        }
        System.out.println(this.type.toString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JComponent component = (JComponent) e.getSource();
        switch (component.getName()) {
            case "btnConnect":
                Helpers.activeButton((JButton) component);
                connect(username.getText(), password.getPasswordValue(), domain.getText(),
                        port.getText(), dbName.getText());
                analysis = new DataAnalysis(this.connection);
                sessions = analysis.getDataSessions();
                break;
            case "btnBuildDB":
                Helpers.activeButton((JButton) component);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JComponent component = (JComponent) e.getSource();
        switch (component.getName()) {
            case "btnConnect":
                Helpers.inactiveColor((JButton) component);
                dismissWelcomeLayout();
                System.out.println("connecting");
                break;
            case "btnBuildDB":
                Helpers.inactiveColor((JButton) component);
                System.out.println("building");
                break;
        }
    }

    private void dismissWelcomeLayout() {
        this.welcomeLayout.show(false);
        this.contextLayout.show(true);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        app.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        app.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}

