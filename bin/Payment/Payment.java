package bin.Payment;

import bin.Entity.EntityBase;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by Josue on 8/16/2016.
 */
public class Payment extends EntityBase implements PaymentInterface {

    public Payment(ResultSet nextResult, String type) {
        super(nextResult, type);
    }

    @Override
    public void printPayments() {

    }

    @Override
    public HashMap<String, String> getAttributes() {
        return this.entityAttributes;
    }

    @Override
    public String getValueFromKey(PaymentAttributes key) {
        return this.getValue(key.toString());
    }
}
