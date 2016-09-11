package bin.Invoice;

import java.util.HashMap;

/**
 * Created by Josue on 8/11/2016.
 */
public interface InvoiceInterface {
    public void printInvoices();

    public HashMap<String, String> getAttributes();

    public String getValueFromKey(InvAttributes key);
}
