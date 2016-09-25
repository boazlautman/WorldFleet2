package com.edt.worldfleet2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by boaz on 9/19/2016.
 */
public class Services {

    public void RegisterPost() throws UnsupportedEncodingException {

        String url = "http://qa.worldfleetlog.com/m2/services/PublicServices.svc/PG_Login";
        try
        {
            JSONObject newUser =  new JSONObject();
            newUser.put("username", "administrator");
            newUser.put("pass", "edt123");
            newUser.put("company", "edt5");
            newUser.put("selDeviceId", "11658");
            newUser.put("version", "3.10.6");


            String response = postData(url, newUser);
            Log.d("response", response);

            if(response!="")
            {
                try {
                    JSONObject user = new JSONObject(response);
                   // User returnUser = new User( user.getInt("id"),user.getString("name"),user.getString("email"),user.getString("password"),user.getString("phone"),user.getString("nic"),user.getString("userType"), user.getString("street"), user.getString("city"), user.getString("country"), Double.parseDouble(user.getString("lat")), Double.parseDouble(user.getString("lng")),user.getInt("is_login"),user.getInt("is_vehicle_added"), user.getString("reg_id"), user.getInt("isError"), user.getString("errorMessage"), user.getString("image"));
                    //return returnUser;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //return  null;
    }

    private String postData(String urlpath, JSONObject json)
    {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            Log.d("write data",json.toString());
            streamWriter.write(json.toString());
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("HTTP_OK response", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.e("else response", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

    }
}