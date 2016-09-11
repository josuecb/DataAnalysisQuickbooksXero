package bin.Entity;

import bin.DataSession.SessionAttributes;
import bin.Invoice.InvAttributes;
import bin.Payment.PaymentAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Josue on 8/16/2016.
 */
abstract public class EntityBase {
    protected HashMap<String, String> entityAttributes = new HashMap<>();
    private ArrayList<String> attributesValues = new ArrayList<>();
    private ResultSet result;
    private String type;

    public EntityBase(ResultSet nextResult, String type) {
        this.result = nextResult;
        this.type = type;
        this.setEntityAttributes();

    }

    private void setEntityAttributes() {
        this.getAttributes();

        for (String attr : this.attributesValues) {
            String value = null;
            try {
                value = this.result.getString(attr);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.entityAttributes.put(attr, value);
        }
    }

    private void getAttributes() {
        switch (this.type) {
            case "payment":
                for (PaymentAttributes attr : PaymentAttributes.values()) {
                    this.attributesValues.add(String.valueOf(attr));
                }
                break;
            case "invoice":
                for (InvAttributes attr : InvAttributes.values()) {
                    this.attributesValues.add(String.valueOf(attr));
                }
                break;
            case "session":
                for (SessionAttributes attr : SessionAttributes.values()) {
                    this.attributesValues.add(String.valueOf(attr));
                }
                break;

        }
    }

    protected String getValue(String key) {
        if (this.entityAttributes.size() > 0) {
            return this.entityAttributes.get(key);
        }
        return null;
    }
}
