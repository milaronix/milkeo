package com.example.milaronix.milkeo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by milaronix on 2/06/15.
 */
public class Alarma extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("<*<*<*<*<si llego: ","");
        Bundle extras = intent.getExtras();
        int id = extras.getInt("id");
        String estado = extras.getString("estado");

        ControlBDDispositivos bd = new ControlBDDispositivos(context);
        Dispositivo dispositivo = bd.getDispositivo(id);
        bd.close();

        String retorno = null;
        try {
            retorno = new PostRCI().execute("D"+dispositivo.getPin(),estado).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("auto id: ",""+id);
        Log.d("auto estado: ",""+estado);
        Log.d("auto respuesta: ",""+retorno);

    }

    public void setAlarma(Context context, Long time, int requestCode, int id, String estado){
        Intent i = new Intent(context, Alarma.class);
        i.putExtra("id", id);
        i.putExtra("estado", estado);
        AlarmManager am =( AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);

        Log.d("set Time: ", "" + time);
        Log.d("set requestcode: ",""+requestCode);
        Log.d("set id: ",""+id);
        Log.d("set estado: ",""+estado);
    }

    public void CancelAlarm(Context context, int requestCode)
    {
        Intent intent = new Intent(context, Alarma.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
