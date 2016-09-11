package bin.Analysis;

import bin.DataSession.DataSession;
import bin.DataSession.SessionAttributes;
import bin.Invoice.InvAttributes;
import bin.Invoice.Invoice;
import bin.connection.DatabaseConnection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Josue on 8/11/2016.
 */
public class DataAnalysis extends AbstractDataAnalysis {
    ReportType reportType = null;
    JList invoiceReportList = null;
    DefaultListModel minModelList;
    DefaultListModel maxModelList;

    public DataAnalysis(DatabaseConnection connection) {
        super(connection);
    }

    public DataAnalysis(DatabaseConnection connection, DataSession session) {
        super(connection, session);
    }


    @Override
    protected void calculateAverageDatePerInvoice(JList minList, JList maxList, ArrayList<Invoice> invoiceList) {
        super.calculateAverageDatePerInvoice(minList, maxList, invoiceList);
    }

    public void DisplayReport(ArrayList<Invoice> invoices, ReportType reportType) {
        this.reportType = reportType;
        this.calculateAverageDatePerInvoice(null, null, invoices);
    }

    public void populateMinMaxLists(JList minList, JList maxList, ArrayList<Invoice> invoices, ReportType reportType) {
        this.reportType = reportType;
        this.minModelList = new DefaultListModel();
        this.maxModelList = new DefaultListModel();
        this.calculateAverageDatePerInvoice(minList, maxList, invoices);
    }

    @Override
    protected void populateMinMaxReport(JList minList, JList maxList, Invoice invoice, ArrayList<Integer> sortedList) {

        if (!sortedList.get(0).equals(sortedList.get(sortedList.size() - 1))) {
            this.minModelList.addElement(sortedList.get(0) + " days");
            this.maxModelList.addElement(sortedList.get(sortedList.size() - 1) + " days");
        } else {
            this.minModelList.addElement(sortedList.get(0) + " days");
            this.maxModelList.addElement(sortedList.get(sortedList.size() - 1) + " days");
        }

        minList.setModel(minModelList);
        maxList.setModel(maxModelList);
    }

    @Override
    protected void printInvoiceTypeReport(long countUnpaid, long paid, long countPartialPaid) {
        System.out.println("\t* Unpaid invoices: " + countUnpaid);
        System.out.println("\t* Paid invoices: " + paid);
        System.out.println("\t* Partial-Paid invoices: " + countPartialPaid);
    }

    @Override
    protected void printReport(Invoice invoice, ArrayList<Integer> sortedList) {
        System.out.println("\n\n\t" + this.reportType + " REPORT user No: " + invoice.getValueFromKey(InvAttributes.userid));
        System.out.println("[" + invoice.getValueFromKey(InvAttributes.company_name) + "]");
        System.out.println("\t- Average Amount Between Invoice date and Payment:");
        if (!sortedList.get(0).equals(sortedList.get(sortedList.size() - 1))) {
            System.out.println("\t\t* Minimum days: " + sortedList.get(0));
            System.out.println("\t\t* Maximum days: " + sortedList.get(sortedList.size() - 1));
        } else {
            System.out.println("\t\t* Minimum & Maxmimum days: " + sortedList.get(0));
        }

        System.out.println();
    }

    public void setJlist(JList list) {
        this.invoiceReportList = list;
    }

    public void DisplayInvoiceReport() {
        this.parseInvoiceTypes(this.invoiceReportList);
    }

    public void DisplaySessionReport() {
        this.parseDateSession();
    }

    public void popuplateInvoiceReport() {
        this.parseInvoiceTypes(this.invoiceReportList);
    }

    @Override
    protected void printDateSessions(DataSession session, int count) {
        long sessionDate = Long.parseLong(session.getValueFromKey(SessionAttributes.timestamp) + "000");
        System.out.println("SESSION REPORT");
        System.out.println("\t" + count + ") " + session.getValueFromKey(SessionAttributes.userid) + ": " + new Date(sessionDate).toString());
        System.out.println();
        System.out.println();
    }

    @Override
    protected void populateInvoiceTypeReport(JList list, long countUnpaid, long paid, long countPartialPaid) {
        DefaultListModel model = new DefaultListModel();

        model.addElement("Unpaid invoices: " + countUnpaid);
        model.addElement("Paid invoices: " + paid);
        model.addElement("Partial-Paid invoices: " + countPartialPaid);

        list.setModel(model);
    }

    public void populateDayList(JList list) {

    }
}
