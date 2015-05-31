package com.example.milaronix.milkeo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class DetalleDispositivo extends Activity{
    View rootView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_dispositivo);


        Bundle extras = getIntent().getExtras();
        String nombret = extras.getString("nombre");
        int posicion = extras.getInt("posicion");
        int imagen_e = extras.getInt("imagen");

        ImageView imagen = (ImageView) findViewById(R.id.imagen);
        imagen.setImageResource(imagen_e);

        TextView nombre = (TextView) findViewById(R.id.nombre_l);
        nombre.setText(nombret+" :"+posicion+" : "+imagen_e);
    }

    private class conexion_http extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... accion) {
            String resp = null;

            //Crea conector http y autorizacion
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://devicecloud.digi.com/ws/sci");
            String basicAuth = "Basic " + Base64.encodeToString("milaronix:Gatocagado1.".getBytes(), Base64.NO_WRAP);
            httppost.setHeader("Authorization", basicAuth);

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

                // Guarda respuesta
                resp = EntityUtils.toString(resEntity);


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
