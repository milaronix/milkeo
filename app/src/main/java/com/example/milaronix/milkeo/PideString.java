package com.example.milaronix.milkeo;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by milaronix on 31/05/15.
 */
public class PideString extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
    JSONArray items = null;
    JSONObject currentValues = null;
    // JSON Node names
    private static final String TAG_ITEMS = "items";
    private static final String TAG_CURRENTVALUE = "currentValue";
    private static final String TAG_DATA = "data";
    private static final String TAG_DESCRIPTION = "description";
    ArrayList<HashMap<String, String>> pafuera = new ArrayList<HashMap<String, String>>();

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
        //Crea conector http y autorizacion
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("https://devicecloud.digi.com/ws/DataStream/00000000-00000000-00409DFF-FF5E0CE5/DIO"+params[0]+".json");
        String basicAuth = "Basic " + Base64.encodeToString("milaronix:Gatocagado1.".getBytes(), Base64.NO_WRAP);
        httpget.setHeader("Authorization", basicAuth);

        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpget);
            HttpEntity resEntity = httpresponse.getEntity();
            String jsonStr = EntityUtils.toString(resEntity);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Obtiene todos los items (es array porque esta entre corchetes)
                    items = jsonObj.getJSONArray(TAG_ITEMS);


                    // Recorre todos los items
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);
                        //Obtiene el objeto donde esta el tag de data (es object por esta entre llaves)
                        currentValues = c.getJSONObject(TAG_CURRENTVALUE);

                        //Guarda los datos a variables
                        String id = (String) c.get(TAG_DESCRIPTION);
                        String data = (String) currentValues.get(TAG_DATA);
                        Log.d("******TAG DESCRIPTION: ", "-" + id);
                        Log.d("******TAG DATA: ", "-"+data);

                        // tmp hashmap for single contact
                        HashMap<String, String> pines = new HashMap<String, String>();
                        pines.put("id",id);
                        pines.put(TAG_DATA,data);


                        pafuera.add(pines);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pafuera;
    }
}
