package bin.Invoice;

/**
 * Created by Josue on 8/11/2016.
 */

import bin.Entity.EntityBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class is based on the database
 * The database is related to the data got by quickbooks and xero
 * Make sure to have the same structure
 */

public class Invoice extends EntityBase implements InvoiceInterface {
    public Invoice(ResultSet nextResult, String type) {
        super(nextResult, type);
    }

    @Override
    public void printInvoices() {

    }

    @Override
    public HashMap<String, String> getAttributes() {
        return this.entityAttributes;
    }

    @Override
    public String getValueFromKey(InvAttributes key) {
        return this.getValue(key.toString());
    }
}
