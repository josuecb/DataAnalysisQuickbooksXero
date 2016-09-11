package app;

import app.behavior.InvoiceReport;
import bin.Analysis.ReportType;

/**
 * Created by Josue on 8/20/2016.
 */
public class ComboItem {
    private InvoiceReport key;

    public ComboItem(InvoiceReport key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }

    public InvoiceReport getKey() {
        return this.key;
    }
}
