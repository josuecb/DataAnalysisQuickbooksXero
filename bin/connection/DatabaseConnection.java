package bin.connection;

import bin.DBTables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Josue on 8/11/2016.
 */
public class DatabaseConnection extends AbstractDatabaseConnection {
    public DatabaseConnection(String dbName) throws Exception {
        super(dbName);
    }


    public DatabaseConnection(String root, String password, String domain, String port) throws Exception {
        super(root, password, domain, port);
    }

    public ResultSet selectWhere(DBTables tbName, ArrayList<HashMap<String, String>> where) {
        try {
            return this.selectAllWhereQuery(tbName, where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet selectAll(DBTables tbName) {
        try {
            return this.selectAllQuery(tbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
