package bin.DataSession;

import bin.Invoice.InvAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Josue on 8/19/2016.
 */
public interface DataSessionInterface {
    public void printSessions();

    public HashMap<String, String> getAttributes();

    public String getValueFromKey(SessionAttributes key);
}
