package com.example.milaronix.milkeo.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaronix.milkeo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by milaronix on 7/05/15.
 */
public class Boton extends Fragment{

    View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_boton, container, false);

        Button encender = (Button) rootView.findViewById(R.id.b_encender);
        encender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast para validar en pantalla la accion del boton
                Toast toast = Toast.makeText(rootView.getContext(), "Encender Dispositivo", Toast.LENGTH_SHORT);
                toast.show();

                String respuesta = null;
                try {
                    respuesta = new conexion_http().execute("high").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                EditText respuesta2 = (EditText) rootView.findViewById(R.id.respuesta2);
                respuesta2.setText(respuesta);

            }
        });

        Button apagar = (Button) rootView.findViewById(R.id.b_apagar);
        apagar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast para validar en pantalla la accion del boton


                String respuesta = null;
                try {
                    respuesta = new conexion_http().execute("low").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                EditText respuesta2 = (EditText) rootView.findViewById(R.id.respuesta2);
                respuesta2.setText(respuesta);
            }
        });

        return rootView;
    }

    private class conexion_http extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... accion) {
            String resp = null;
            //Crea conector http y autorizacion
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://devicecloud.digi.com/ws/sci");
            String basicAuth = "Basic " + Base64.encodeToString("milaronix:Gatocagado1.".getBytes(), Base64.NO_WRAP);
            httppost.setHeader("Authorization", basicAuth);

            Log.d("esto es un mensaje ", accion[0]);

            try {
                StringEntity se = new StringEntity( "<sci_request version=\"1.0\"> \n" +
                        "  <send_message cache=\"false\"> \n" +
                        "    <targets>\n" +
                        "      <device id=\"00000000-00000000-00409DFF-FF5E0CE5\"/>\n" +
                        "    </targets> \n" +
                        "    <rci_request version=\"1.1\"> \n" +
                        "      <set_setting>\n" +
                        "        <InputOutput>\n" +
                        "          <D9>"+accion[0]+"</D9>\n" +
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

}
