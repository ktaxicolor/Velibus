package klaou.perso.velibus.json;

/**
 * Created by Klaou on 02/11/2016.
*/
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.annotation.*;


public class JSONVelibParser {

    private ObjectMapper objectMapper = null;
    private File jsonOutputFile;
    private File jsonFile;
    private JsonNode jsonTree;

    public JSONVelibParser() {
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

    public int getVelibDocks() {
        JsonNode velibDocksNode = jsonTree.path("available_bike_stands");
        return velibDocksNode.asInt();
    }

    public int getVelibBikes() {
        JsonNode velibBikesNode = jsonTree.path("available_bikes");
        return velibBikesNode.asInt();
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
            //urlConnection.setInstanceFollowRedirects(false);
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
        jsonFile = new File(jsonOutputFile, "velibData.json");
    }
}



