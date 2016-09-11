package app.behavior;

import bin.Analysis.DataAnalysis;
import bin.Analysis.ReportType;
import bin.DataSession.DataSession;
import bin.DataSession.SessionAttributes;
import bin.File.FileSettingFormatter;
import bin.connection.DatabaseConnection;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Josue on 8/19/2016.
 */
public abstract class AbstractBehavior {
    ArrayList<DataSession> sessions = new ArrayList<>();
    DatabaseConnection connection;
    DataAnalysis analysis;
    FileSettingFormatter fileSettingFormatter;
    String userName, password, domain, port, dbName;

    boolean storing = false;


    public AbstractBehavior() {
        try {
            fileSettingFormatter = new FileSettingFormatter();
            fileSettingFormatter.decodeJsonConfig();

            if (fileSettingFormatter.getStoring().equals("1")) {
                storing = true;
                this.connect(fileSettingFormatter.getUsername(),
                        fileSettingFormatter.getPassword(), fileSettingFormatter.getDomain(),
                        fileSettingFormatter.getPort(), fileSettingFormatter.getDbName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String userName, String password, String domain, String port, String dbName) {
        this.userName = userName;
        this.password = password;
        this.domain = domain;
        this.port = port;
        this.dbName = dbName;

        try {
            this.connection = new DatabaseConnection(userName, password, domain, port);
            this.connection.setDatabaseName(dbName);

            fileSettingFormatter.setUsername(userName);
            fileSettingFormatter.setPassword(password);
            fileSettingFormatter.setDbName(dbName);
            fileSettingFormatter.setDomain(domain);
            fileSettingFormatter.setPort(port);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void storeCredentials() {
        if (this.storing) {
            fileSettingFormatter.setStoreCredentials("1");
            fileSettingFormatter.storeCredentials();
        }
    }

    protected void populateList(JList dateList, JList userIdList, JButton button, JLabel label) {
        DefaultListModel modelDateList = new DefaultListModel();
        DefaultListModel modelIdList = new DefaultListModel();

        System.out.println(this.sessions.size());
        for (DataSession s : this.sessions) {
            String date = new Date(Long.parseLong(s.getValueFromKey(SessionAttributes.timestamp) + "000")).toString();
            System.out.println(date);
            modelDateList.addElement(date);
            modelIdList.addElement(String.valueOf(s.getValueFromKey(SessionAttributes.userid)));
        }

        dateList.setModel(modelDateList);
        userIdList.setModel(modelIdList);

        dateList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    onItemSelected(dateList.getSelectedIndex(), button, label);
                }
            }
        });


        userIdList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    onItemSelected(userIdList.getSelectedIndex(), button, label);
                }
            }
        });
    }

    protected void onItemSelected(int index, JButton button, JLabel label) {

    }


    protected void onAnalyzeButton(JList list, JButton analyzeButton, JPanel panel1, JPanel panel2) {
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAnalyzeButtonPressed(list, panel1, panel2, analyzeButton.getText());
                analyzeButton.setEnabled(false);
            }
        });
    }

    protected void onAnalyzeButtonPressed(JList list, JPanel panel1, JPanel panel2, String type) {

    }

    protected void onOptionSelection(JComboBox box, JButton button) {
        box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOptionSelected(box, button);
            }
        });
    }

    protected void onOptionSelected(JComboBox box, JButton button) {

    }

    protected void runAnalysis(JList minList, JList maxList, InvoiceReport type) {
        minList.setVisible(true);
        maxList.setVisible(true);
        if (type.equals(InvoiceReport.PAID_MIN_MAX_DAYS_REPORT)) {
            this.analysis.populateMinMaxLists(minList, maxList, this.analysis.getPaidInvoices(), ReportType.PAID);
        } else {
            this.analysis.populateMinMaxLists(minList, maxList, this.analysis.getPartialPaidInvoices(), ReportType.PARTIAL_PAID);
        }
    }

    protected void populateEntityList(JList list, DataSession session) {
        this.connect(this.userName, this.password, this.domain,
                this.port, this.dbName);

        this.analysis = new DataAnalysis(this.connection, session);
        this.analysis.setJlist(list);
        this.analysis.popuplateInvoiceReport();
    }
}
