package com.example.milaronix.milkeo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by milaronix on 2/06/15.
 */
public class SetAlarma extends ActionBarActivity {
    int dhora = 0;
    int dminuto =  0;
    int danio = 0;
    int dmes = 0;
    int ddia = 0;
    long diferenciaHora = 0;
    long diferenciaFecha = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarma);


        final List<String> list = new ArrayList<String>();
        final ControlBDDispositivos bd = new ControlBDDispositivos(getApplicationContext());
        List<Dispositivo> dispositivos = bd.getAllDispositivos();
        final HashMap<String,Integer> llave = new HashMap<String, Integer>();

        for(int i = 0; i < dispositivos.size(); i++){
            list.add(dispositivos.get(i).getNombre());
            llave.put(dispositivos.get(i).getNombre(),dispositivos.get(i).getId());
        }

        final Spinner dispositivoAsociado = (Spinner) findViewById(R.id.dispositivo);
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dispositivoAsociado.setAdapter(adp1);

        final ArrayList<String> estados = new ArrayList<String>();
        estados.add("Encender");
        estados.add("Apagar");
        final Spinner estadoAsociado = (Spinner) findViewById(R.id.estado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoAsociado.setAdapter(adapter);

        final Button hora = (Button) findViewById(R.id.hora);
        final Button fecha = (Button) findViewById(R.id.fecha);

        SetFechaHora(hora,false,true);
        SetFechaHora(fecha,true,false);

        //desde_fecha.setVisibility(View.GONE);

        hora.setOnClickListener(EscogeHora(hora));
        fecha.setOnClickListener(EscogeFecha(fecha));

        Button cancelar = (Button) findViewById(R.id.b_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button guardar = (Button) findViewById(R.id.b_guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = llave.get(dispositivoAsociado.getSelectedItem().toString());
                Dispositivo dispositivo = bd.getDispositivo(a);
                Toast.makeText(getApplicationContext(),""+dispositivo.getNombre()+a,Toast.LENGTH_SHORT).show();

                Log.d("<*<*<*<*<* Estado Seleccionado: ",estadoAsociado.getSelectedItem().toString());
                String accion = null;
                if(estadoAsociado.getSelectedItem().toString().equals("Encender")){
                    accion = "high";
                }else if(estadoAsociado.getSelectedItem().toString().equals("Apagar")){
                    accion = "low";
                }

                Calendar actual = Calendar.getInstance();
                Calendar futuro = Calendar.getInstance();
                futuro.set(Calendar.DAY_OF_MONTH, ddia);
                futuro.set(Calendar.MONTH, dmes);
                futuro.set(Calendar.YEAR, danio);
                futuro.set(Calendar.HOUR_OF_DAY, dhora);
                futuro.set(Calendar.MINUTE, dminuto);
                //actual.set(Calendar.MONTH,actual.get(Calendar.MONTH)+1);
                Log.d("*****actual:",""+actual.getTime());
                Log.d("*****futuro:",""+futuro.getTime());
                Long diferencia =futuro.getTimeInMillis() - actual.getTimeInMillis();

                setAlarma(SetAlarma.this,actual.getTimeInMillis()+ diferencia,0,dispositivo.getId(),accion);
            }
        });
    }

    private void SetFechaHora (Button boton, boolean fecha, boolean hora){
        Calendar calendario = Calendar.getInstance();
        int hour = calendario.get(Calendar.HOUR_OF_DAY);
        int minute =  calendario.get(Calendar.MINUTE);
        int mYear = calendario.get(Calendar.YEAR);
        int mMonth = calendario.get(Calendar.MONTH)+1;
        int mDay = calendario.get(Calendar.DAY_OF_MONTH);
        if(fecha){
            boton.setText("" + mDay + "/" + mMonth + "/" + mYear);
        }
        if (hora){
            String AM_PM ;
            if(hour < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            boton.setText( hour + ":" + minute + " " + AM_PM);
        }
    }

    private View.OnClickListener EscogeHora(final Button hora){
        View.OnClickListener listenerhora = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetAlarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        dhora = selectedHour;
                        dminuto = selectedMinute;
                        hora.setText( selectedHour + ":" + selectedMinute + " " + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione Hora");
                mTimePicker.show();
            }
        };

        return listenerhora;
    }

    private View.OnClickListener EscogeFecha(final Button fecha){
        View.OnClickListener listenerfecha = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(SetAlarma.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        ddia = selectedday;
                        dmes = selectedmonth;
                        selectedmonth = selectedmonth + 1;
                        danio= selectedyear;
                        fecha.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Seleccione Fecha");
                mDatePicker.show();
            }
        };
        return listenerfecha;
    }

    private void setAlarma(Context context, Long time, int requestCode, int id, String estado){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dispositvos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar:
                Toast.makeText(getApplicationContext(), "Agregar", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
