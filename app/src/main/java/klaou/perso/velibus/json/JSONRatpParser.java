package klaou.perso.velibus.json;

import android.os.Environment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Klaou on 04/11/2016.
 */

public class JSONRatpParser {

    private static final String ERROR_IN_JSON_PARSER = "ERROR IN JSON_PARSER";
    private ObjectMapper objectMapper = null;
    private File jsonOutputFile;
    private File jsonFile;
    private JsonNode jsonTree;

    public JSONRatpParser() {
        objectMapper = new ObjectMapper();
    }

    public boolean init(String urlData) {
        try {
            downloadJsonFile(urlData);
            jsonTree = objectMapper.readTree(jsonFile);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getMinutesToBus(int busPosition) {
        JsonNode busSchedulesNode = jsonTree.path("response").path("schedules");
        if(busSchedulesNode.size() > busPosition)
        {
            return busSchedulesNode.get(busPosition).path("message").asText();
        }
        return ERROR_IN_JSON_PARSER;
    }


    private void downloadJsonFile(String urlData) throws IOException{

        createFileAndDirectory();
        URL url = new URL(urlData);
        HttpURLConnection urlConnection;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(false);
        urlConnection.connect();
        FileOutputStream fileOutput = new FileOutputStream(jsonFile);
        InputStream inputStream = urlConnection.getInputStream();
        byte[] buffer = new byte[1024];
        int bufferLength;
        while ((bufferLength = inputStream.read(buffer)) > 0) {
            fileOutput.write(buffer, 0, bufferLength);
        }
        fileOutput.close();

    }

    private void createFileAndDirectory() throws FileNotFoundException {
        final String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        final String temp_path = extStorageDirectory + "/velibus/temp";
        jsonOutputFile = new File(temp_path, "/");
        if (!jsonOutputFile.exists())
            jsonOutputFile.mkdirs();
        jsonFile = new File(jsonOutputFile, "busData.json");
    }
}



