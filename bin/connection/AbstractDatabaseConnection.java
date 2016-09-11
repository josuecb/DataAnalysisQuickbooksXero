package bin.connection;

import bin.DBTables;
import bin.File.FileSettingFormatter;
import bin.Helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Josue on 8/11/2016.
 */
abstract class AbstractDatabaseConnection {
    private String dbName = null;
    private String username = "root";
    private String password = "";
    private Connection connection;

    AbstractDatabaseConnection(String dbName) throws Exception {
        this.setDatabaseName(dbName);
    }

    AbstractDatabaseConnection(String root, String password, String domain, String port) throws Exception {

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + domain + ":" + port + "/";

        String credentials = "?user=" + root + "&password=" + password;

        Class.forName(driver).newInstance();

        this.connection = DriverManager.getConnection(url + credentials);
    }

    private void doConnectionWithDatabase(String dbName) {
        try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/";

            Class.forName(driver).newInstance();

            this.connection = DriverManager.getConnection(url + dbName, this.username, this.password);
            System.out.println("****************Connection Successful****************\n");
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    public int createDatabase(String dbName) throws SQLException {
        Statement s = this.connection.createStatement();
        return s.executeUpdate("CREATE DATABASE " + dbName);
    }

    public void setDatabaseName(String dbName) throws Exception {
        if (!dbName.isEmpty()) {
            this.connection.close();
            this.dbName = dbName;
            this.doConnectionWithDatabase(this.dbName);
        } else
            throw new Exception("Null database Name");
    }

    public Connection getConnection() {
        return this.connection;
    }

    protected ResultSet selectAllQuery(DBTables tbName) throws SQLException {
        if (this.connection != null) {
            Statement st = this.connection.createStatement();
            return st.executeQuery("SELECT * FROM  " + tbName);
        }
        return null;
    }

    protected ResultSet selectAllWhereQuery(DBTables tbName, ArrayList<HashMap<String, String>> where) throws SQLException {
        if (this.connection != null) {
            Statement st = this.connection.createStatement();
//            System.out.println("SELECT * FROM  " + tbName + " WHERE " + this.whereStatement(where, "AND"));
            return st.executeQuery("SELECT * FROM  " + tbName + " WHERE " + this.whereStatement(where, "AND"));
        }
        return null;
    }

    public void test(ArrayList<HashMap<String, String>> whereList) {

        this.whereStatement(whereList, "AND");
    }

    private String whereStatement(ArrayList<HashMap<String, String>> whereList, String conjunction) {
        String whereSt = "";

        int count = 0;
        for (HashMap<String, String> where : whereList) {
            String key = Helpers.parseHashMapKey(where);
//            System.out.println(whereList.size());
            if (count == (whereList.size() - 1))
                whereSt += key + "=" + where.get(key);
            else
                whereSt += key + "=" + where.get(key) + " " + conjunction + " ";

            count++;
        }

//        System.out.println(whereSt);
        return whereSt;
    }

    public boolean close() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
