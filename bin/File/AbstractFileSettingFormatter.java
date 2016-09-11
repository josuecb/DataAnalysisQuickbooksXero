package bin.File;

import bin.Resources.SettingListeners;
import bin.Resources.SettingReference;
import bin.Resources.Settings;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Josue on 8/24/2016.
 */
public abstract class AbstractFileSettingFormatter implements SettingListeners {
    private String documentPath;
    private String folderAppName;
    private String fileName;

    AbstractFileSettingFormatter() throws Exception {
        this.folderAppName = SettingReference.folderAppName;
        this.documentPath = SettingReference.documentPath;
        this.fileName = SettingReference.fileName;

        File dir = new File(documentPath + this.folderAppName);
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (created) {
                File jsonFile = new File(documentPath + this.folderAppName + File.separator + this.fileName);
                if (!jsonFile.exists()) {
                    this.createFile();
                    this.writeToFile(createJsonConfigString());
                }
            }
        }
    }

    protected boolean createFile() {
        PrintWriter fileCreator = null;
        try {
            fileCreator = new PrintWriter(documentPath + this.folderAppName + File.separator + this.fileName, "UTF-8");
            fileCreator.close();
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected boolean writeToFile(String json) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(documentPath + this.folderAppName + File.separator + this.fileName);

            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(json);
            out.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    private String createJsonConfigString() {
        return createJsonConfigString("root", "", "ANL", "localhost", "3306", "0");
    }

    protected String createJsonConfigString(String user, String password, String dbName, String domain, String port, String storeCredentials) {
        JSONObject dataSet = new JSONObject();

        HashMap<String, String> directorySet = new HashMap<>();
        HashMap<String, String> configSet = new HashMap<>();

        directorySet.put(Settings.path.toString(), SettingReference.documentPath + SettingReference.folderAppName);
        directorySet.put(Settings.config_file.toString(), SettingReference.fileName);

        configSet.put(Settings.user.toString(), user);
        configSet.put(Settings.password.toString(), password);
        configSet.put(Settings.dbname.toString(), dbName);
        configSet.put(Settings.domain.toString(), domain);
        configSet.put(Settings.port.toString(), port);
        configSet.put(Settings.store.toString(), storeCredentials);

        dataSet.put(Settings.directory_setting.toString(), directorySet);
        dataSet.put(Settings.config_settings.toString(), configSet);
        return dataSet.toString();
    }

    public void decodeJsonConfig() throws IOException {
        Object object = JSONValue.parse(this.getSettingContent());
        JSONObject jsonObject = (JSONObject) object;

        Object configJsonOb = jsonObject.get(Settings.config_settings.toString());
        Object folderJsonOb = jsonObject.get(Settings.directory_setting.toString());

        onConfigurationParsing(configJsonOb);
        onFileListener(folderJsonOb);
    }

    protected String getSettingContent() throws IOException {
        BufferedReader br = null;
        String jsonContent = "";

        String sCurrentLine;

        br = new BufferedReader(new FileReader(documentPath + this.folderAppName + File.separator + this.fileName));
        while ((sCurrentLine = br.readLine()) != null) {
            jsonContent += sCurrentLine;
//                System.out.println(sCurrentLine);
        }
        if (br != null) br.close();

        if (jsonContent.isEmpty()) {
            this.writeToFile(createJsonConfigString());
            return this.getSettingContent();
        }

        return jsonContent;
    }

}
