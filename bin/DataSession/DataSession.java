package bin.DataSession;

import bin.Entity.EntityBase;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Josue on 8/19/2016.
 */
public class DataSession extends EntityBase implements DataSessionInterface {

    public DataSession(ResultSet nextResult, String type) {
        super(nextResult, type);
    }

    @Override
    public void printSessions() {

    }

    @Override
    public HashMap<String, String> getAttributes() {
        return this.entityAttributes;
    }

    @Override
    public String getValueFromKey(SessionAttributes key) {
        return this.getValue(key.toString());
    }
}
