import bin.Analysis.DataAnalysis;
import bin.Analysis.ReportType;
import bin.DataSession.DataSession;
import bin.DataSession.SessionAttributes;
import bin.Helpers;
import bin.connection.DatabaseConnection;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Josue on 8/11/2016.
 */


public class Test {
    public static void run(String[] args) {
        DatabaseConnection connection = null;
        try {
            connection = new DatabaseConnection("tf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataAnalysis sessions = new DataAnalysis(connection);

        ArrayList<DataSession> allSessions = new ArrayList<>();
        allSessions = sessions.getDataSessions();

        sessions.DisplaySessionReport();

        Scanner reader = new Scanner(System.in);
        System.out.print("Enter choice: ");
        int index = reader.nextInt();

        try {
            connection = new DatabaseConnection("tf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataAnalysis analysis = new DataAnalysis(connection, allSessions.get(index - 1));
        analysis.DisplaySessionReport();

        System.out.println("INVOICE REPORT REPORT user No: " + allSessions.get(index - 1).getValueFromKey(SessionAttributes.userid));
        analysis.DisplayInvoiceReport();

        analysis.DisplayReport(analysis.getPartialPaidInvoices(), ReportType.PARTIAL_PAID);
    }
}
