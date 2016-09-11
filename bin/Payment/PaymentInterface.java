package bin.Payment;

import java.util.HashMap;

/**
 * Created by Josue on 8/16/2016.
 */
public interface PaymentInterface {
    public void printPayments();

    public HashMap<String, String> getAttributes();

    public String getValueFromKey(PaymentAttributes key);

}
