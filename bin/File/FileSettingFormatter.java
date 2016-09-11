package bin.File;

import bin.Resources.Settings;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by Josue on 8/24/2016.
 */
public class FileSettingFormatter extends AbstractFileSettingFormatter {
    private String username, password, domain, port, dbName, storing;

    public FileSettingFormatter() throws Exception {
        super();
        this.createFile();
    }

    public void writeJson(String json) {
        this.writeToFile(json);
    }

    @Override
    public void onConfigurationParsing(Object configSettings) {
        Object jsonOb = JSONValue.parse(configSettings.toString());
        JSONObject jsonObject = (JSONObject) jsonOb;

        this.username = (String) jsonObject.get(Settings.user.toString());
        this.password = (String) jsonObject.get(Settings.password.toString());
        this.dbName = (String) jsonObject.get(Settings.dbname.toString());
        this.domain = (String) jsonObject.get(Settings.domain.toString());
        this.port = (String) jsonObject.get(Settings.port.toString());
        this.storing = (String) jsonObject.get(Settings.store.toString());

        System.out.println(configSettings.toString());
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDomain() {
        return this.domain;
    }

    public String getPort() {
        return this.port;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getStoring() {
        return this.storing;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setStoreCredentials(String storing) {
        this.storing = storing;
    }


    public void storeCredentials() {
        this.writeJson(createJsonConfigString(this.username, this.password, this.dbName, this.domain,
                this.port, this.storing));
    }

    @Override
    public void onFileListener(Object directoryListener) {
        System.out.println(directoryListener.toString());
    }
}
