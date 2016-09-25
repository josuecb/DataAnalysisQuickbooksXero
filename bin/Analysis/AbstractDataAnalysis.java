package bin.Analysis;

import bin.DBTables;
import bin.DataSession.DataSession;
import bin.DataSession.SessionAttributes;
import bin.Entity.EntityBase;
import bin.Helpers;
import bin.Invoice.InvAttributes;
import bin.Invoice.Invoice;
import bin.Payment.Payment;
import bin.Payment.PaymentAttributes;
import bin.connection.DatabaseConnection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Josue on 8/11/2016.
 */
abstract class AbstractDataAnalysis {
    private ArrayList<HashMap<String, String>> user = new ArrayList<>();
    private String time = null;
    private DatabaseConnection connection;
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();


    private ArrayList<Invoice> paidInvoices = new ArrayList<>();
    private ArrayList<Invoice> partialPaidInvoices = new ArrayList<>();
    private ArrayList<Invoice> unpaidInvoices = new ArrayList<>();
    private ArrayList<DataSession> dataSessions = new ArrayList<>();

    AbstractDataAnalysis(DatabaseConnection connection) {
        this.connection = connection;

        ResultSet dataSessionResult = this.getSessionsFromDatabase();
        this.parseEntity(dataSessionResult, "session");

        this.connection.close();
    }

    AbstractDataAnalysis(DatabaseConnection connection, DataSession session) {
        HashMap<String, String> user = Helpers.createDictionary("userid", session.getValueFromKey(SessionAttributes.userid));
        this.connection = connection;
        this.setUser(user);
        this.time = session.getValueFromKey(SessionAttributes.timestamp);

        ResultSet invoiceResult = this.getInvoiceFromDataBase();
        this.parseEntity(invoiceResult, "invoice");

        ResultSet paymentResult = this.getPaymentsFromDatabase();
        this.parseEntity(paymentResult, "payment");

        // Close connection
        this.connection.close();
    }

    private void parseEntity(ResultSet result, String type) {
        try {
            while (result.next()) {
                EntityBase ent;
                switch (type) {
                    case "invoice":
                        ent = new Invoice(result, type);
                        this.invoices.add((Invoice) ent);
                        break;
                    case "payment":
                        ent = new Payment(result, type);
                        this.payments.add((Payment) ent);
                        break;
                    default:
                        ent = new DataSession(result, type);
                        this.dataSessions.add((DataSession) ent);
                        break;
                }
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Invoice> getInvoices() {
        return this.invoices;
    }

    private void setUser(HashMap<String, String> whereUser) {
        this.user.add(whereUser);
    }

    protected void unpaidInvoices() {

    }

    protected void parseInvoiceTypes(JList list) {
        long countUnpaid = 0;
        long countPartialPaid = 0;
        long paid = 0;

        for (Invoice invoice : this.invoices) {
            if ((invoice.getValueFromKey(InvAttributes.amount_paid).equals("0.00") || invoice.getValueFromKey(InvAttributes.amount_paid).equals("0")) &&
                    !invoice.getValueFromKey(InvAttributes.txn_status).toLowerCase().equals("paid")) {
                countUnpaid++;
                this.unpaidInvoices.add(invoice);
            } else if (invoice.getValueFromKey(InvAttributes.amount_paid).equals(invoice.getValueFromKey(InvAttributes.total)) ||
                    (invoice.getValueFromKey(InvAttributes.amount_due).equals("0") || invoice.getValueFromKey(InvAttributes.amount_due).equals("0.00"))) {
                paid++;
                this.paidInvoices.add(invoice);
            } else {
                this.partialPaidInvoices.add(invoice);
                countPartialPaid++;
            }
        }

        if (list == null)
            this.printInvoiceTypeReport(countUnpaid, paid, countPartialPaid);
        else
            this.populateInvoiceTypeReport(list, countUnpaid, paid, countPartialPaid);
    }

    protected void parseDateSession() {
        int count = 1;
        for (DataSession session : this.dataSessions) {
            this.printDateSessions(session, count);
            count++;
        }
    }

    protected void populateInvoiceTypeReport(JList list, long countUnpaid, long paid, long countPartialPaid) {

    }

    protected void printDateSessions(DataSession session, int count) {

    }

    protected void printInvoiceTypeReport(long countUnpaid, long paid, long countPartialPaid) {

    }

    public ArrayList<DataSession> getDataSessions() {
        return this.dataSessions;
    }

    public ArrayList<Invoice> getPaidInvoices() {
        return this.paidInvoices;
    }

    public ArrayList<Invoice> getPartialPaidInvoices() {
        return this.partialPaidInvoices;
    }

    public ArrayList<Invoice> getUnpaidInvoices() {
        return this.unpaidInvoices;
    }

    protected void calculateAverageDatePerInvoice(JList minList, JList maxList, ArrayList<Invoice> invoiceList) {
        HashMap<Invoice, ArrayList<Integer>> diffDaysPerInvoice = this.getPaymentDifferenceDatesPerInvoice(invoiceList);

        Iterator it = diffDaysPerInvoice.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Integer> a = (ArrayList<Integer>) pair.getValue();

            a.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });

            if (minList == null)
                printReport((Invoice) pair.getKey(), a);
            else {
                printReport((Invoice) pair.getKey(), a);
                populateMinMaxReport(minList, maxList, (Invoice) pair.getKey(), a);
            }

            it.remove();
        }
    }

    protected void populateMinMaxReport(JList minList, JList maxList, Invoice invoice, ArrayList<Integer> sortedList) {

    }

    protected void printReport(Invoice invoice, ArrayList<Integer> sortedList) {

    }


    private HashMap<Invoice, ArrayList<Integer>> getPaymentDifferenceDatesPerInvoice(ArrayList<Invoice> invoiceList) {
        HashMap<Invoice, ArrayList<Integer>> daysDifference = new HashMap<>();

        for (Invoice invoice : invoiceList) {
            String[] ids = this.getIds(invoice.getValueFromKey(InvAttributes.payment_ids));

            if (ids != null) {
                ArrayList<Integer> differenceDays = new ArrayList<>();

                boolean hasMultipleIds = false;
                if (ids.length > 1) {
                    hasMultipleIds = true;
                }

                for (String id : ids) {
                    Payment p = this.findPayment(id);
                    if (p != null) {
                        if (!hasMultipleIds) {
                            long start = Helpers.toMicro(invoice.getValueFromKey(InvAttributes.txn_date));
                            long end = Helpers.toMicro(p.getValueFromKey(PaymentAttributes.date));

                            int days = Helpers.differenceDays(start, end);
                            differenceDays.add(days);

//                        System.out.println(days + " days.");
                        }
                    }

                }
                daysDifference.put(invoice, differenceDays);
            }
//            System.out.println();
        }


        return daysDifference;
    }

    private String[] getIds(String json) {
        if (json == null)
            return null;

        json = json.replace("[", "").replace("]", "").replace("\"", "");
        return json.split(",");
    }

    protected void paidInvoices() {

    }


    protected void timeBetweenPaymentDate_InvoiceDate() {

    }

    private ResultSet getInvoiceFromDataBase() {
        return this.connection.selectWhere(DBTables.jc_accounting_invoices, this.user);
    }

    private ResultSet getPaymentsFromDatabase() {
        ArrayList<HashMap<String, String>> time = new ArrayList<>();
        time.add(Helpers.createDictionary("timestamp", this.time));

        return this.connection.selectWhere(DBTables.jc_payments, time);
    }

    private ResultSet getSessionsFromDatabase() {
        return this.connection.selectAll(DBTables.jc_data_requests);
    }


    private Payment findPayment(String paymentId) {
        for (Payment payment : this.payments) {
            if (payment.getValueFromKey(PaymentAttributes.paymentid).equals(paymentId))
                return payment;
        }

        return null;
    }
}
