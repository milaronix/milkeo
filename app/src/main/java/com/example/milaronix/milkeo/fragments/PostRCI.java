package com.example.milaronix.milkeo.fragments;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by milaronix on 31/05/15.
 */
public class PostRCI extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... accion) {
        String resp = null;
        //Crea conector http y autorizacion
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://devicecloud.digi.com/ws/sci");
        String basicAuth = "Basic " + Base64.encodeToString("milaronix:Gatocagado1.".getBytes(), Base64.NO_WRAP);
        httppost.setHeader("Authorization", basicAuth);

        Log.d("Pin a modificar", accion[0]);
        Log.d("Nuevo estado", accion[1]);

        try {
            StringEntity se = new StringEntity( "<sci_request version=\"1.0\"> \n" +
                    "  <send_message cache=\"false\"> \n" +
                    "    <targets>\n" +
                    "      <device id=\"00000000-00000000-00409DFF-FF5E0CE5\"/>\n" +
                    "    </targets> \n" +
                    "    <rci_request version=\"1.1\"> \n" +
                    "      <set_setting>\n" +
                    "        <InputOutput>\n" +
                    "          <"+accion[0]+">"+accion[1]+"</"+accion[0]+">\n" +
                    "        </InputOutput>\n" +
                    "      </set_setting>\n" +
                    "    </rci_request>\n" +
                    "  </send_message>\n" +
                    "</sci_request>", HTTP.UTF_8);
            se.setContentType("text/xml");

            // realiza el POST http
            httppost.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity resEntity = httpresponse.getEntity();
            httpclient.getConnectionManager().shutdown();
            httppost.abort();

            // Pone repuesta en pantalla para verificar
            resp = EntityUtils.toString(resEntity);
                /*EditText respuesta2 = (EditText) rootView.findViewById(R.id.respuesta2);
                respuesta2.setText(resp);*/

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resp;
    }
}
