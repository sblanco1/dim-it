package com.att.a56withrice.dimit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mr088w on 4/4/2017.
 */

public class LightFixture {

    private String name;
    private int lightValue;
    private boolean lightStatus;
    private boolean smartStatus;

    private boolean enabled = false;

    public LightFixture() {
        this.enabled = false;
    }

    public LightFixture(String name, int lightValue, boolean lightStatus, boolean smartStatus) {
        this.name = name;
        this.lightValue = lightValue;
        this.lightStatus = lightStatus;
        this.smartStatus = smartStatus;
        this.enabled = false;
    }

    public void sendNewValue(int value) {
        if(this.enabled) {
            new UpdateUserPreferences().execute(value);
        }

    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getLightStatus() {
        return this.lightStatus;
    }

    public void setLightStatus(boolean lightStatus) {

        this.lightStatus = lightStatus;

        if(lightStatus == false) {
            sendNewValue(0);
        }
        else {
            sendNewValue(this.lightValue);
        }

    }

    public boolean getSmartStatus() {
        return this.smartStatus;
    }

    public void setSmartStatus(boolean smartStatus) {

        this.smartStatus = smartStatus;

        if(lightStatus == false) {
            return;
        }
        else if(smartStatus == false ) {
            sendNewValue(100);
        }
        else {
            sendNewValue(this.lightValue);
        }
    }

    public int getLightValue() {
        return this.lightValue;
    }

    public void setLightValue(int lightValue) {

        this.lightValue = lightValue;

        if(lightStatus == true) {
            sendNewValue(lightValue);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }



    private class UpdateUserPreferences extends AsyncTask<Integer, Integer, Integer> {

        protected Integer doInBackground(Integer... value) {
            Log.d("LightFixture", "sendNewValue() " + value[0].intValue() + " Called.");

            String json = "{\"value\": "+ value[0].intValue() + " }";
            Log.d("LightFixture", "JSON: " + json);

            HttpURLConnection connection = null;
            try {
                int timeout = 5000;
                URL u = new URL("https://api-m2x.att.com/v2/devices/36d8536fd3f2a6fbaf4faae107659495/streams/UserLightPreference/value");

                connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod("PUT");

                //set the sending type and receiving type to json
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("X-M2X-KEY", "980e0d657bfda9278656abd29dfdc912");

                connection.setAllowUserInteraction(false);
                connection.setConnectTimeout(timeout);
                connection.setReadTimeout(timeout);

                if (json != null) {
                    System.out.println(json);

                    //set the content length of the body
                    connection.setRequestProperty("Content-length", json.getBytes().length + "");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    //send the json as body of the request
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(json.getBytes("UTF-8"));
                    outputStream.close();
                }

                //Connect to the server
                connection.connect();

                int status = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                Log.d("LightFixture", "HTTP status code : " + status);
                Log.d("LightFixture", "HTTP Response message: " + responseMessage);

                switch (status) {
                    case 200:
                    case 201:
                    case 202:
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        bufferedReader.close();
                        Log.d("LightFixture", "Received String : " + sb.toString());
                }

            } catch (MalformedURLException ex) {
                Log.d("LightFixture", "Error in http connection" + ex.toString());
            } catch (IOException ex) {
                Log.d("LightFixture", "Error in http connection" + ex.toString());
            } catch (Exception ex) {
                System.out.println("Error in http connection" + ex.toString());
                Log.d("LightFixture", "Error in http connection" + ex.toString());
            } finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    } catch (Exception ex) {
                        Log.d("LightFixture", "Error in http connection" + ex.toString());
                    }
                }
            }
            return value[0];
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Long result) {

        }
    }


}
