package com.example.milaronix.milkeo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milaronix on 1/06/15.
 */
public class ControlBDDispositivos extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "milkeo_db";

    // Contacts table name
    private static final String TABLE_DISPOSITIVOS = "dispositivos";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_IMAGEN = "imagen";
    private static final String KEY_IMG_ESTADO = "img_estado";
    private static final String KEY_ESTADO = "estado";
    private static final String KEY_PIN = "pin";

    private SQLiteDatabase bd;

    public ControlBDDispositivos(Context context) {
        super(context, DATABASE_NAME+".db", null, DATABASE_VERSION);
        //bd = context.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA_DISPOSITIVOS = "CREATE TABLE " + TABLE_DISPOSITIVOS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NOMBRE + " TEXT,"
                + KEY_ESTADO + " TEXT," + KEY_PIN + " INTEGER,"
                +KEY_IMAGEN + " TEXT," + KEY_IMG_ESTADO + " TEXT" + ")";
        db.execSQL(CREAR_TABLA_DISPOSITIVOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPOSITIVOS);

        // Create tables again
        onCreate(db);
    }

    public void insertarDispositivo(Dispositivo dispositivo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, dispositivo.getNombre());
        values.put(KEY_ESTADO, dispositivo.getEstado());
        values.put(KEY_PIN, dispositivo.getPin());
        values.put(KEY_IMAGEN, dispositivo.getImagen());
        values.put(KEY_IMG_ESTADO, dispositivo.getImg_estado());

        // Inserting Row
        db.insert(TABLE_DISPOSITIVOS, null, values);
        db.close(); // Closing database connection
        Log.d("Insert DB" , "Base de datos cerrada y query insertado");

    }

    public Dispositivo getDispositivo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DISPOSITIVOS, new String[] { KEY_ID, KEY_IMAGEN, KEY_NOMBRE,
                        KEY_PIN, KEY_ESTADO, KEY_IMG_ESTADO}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Dispositivo dispositivo = new Dispositivo(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)));

        return dispositivo;
    }

    public List<Dispositivo> getAllDispositivos() {
        List<Dispositivo> dispositivosList = new ArrayList<Dispositivo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DISPOSITIVOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String CREAR_TABLA_DISPOSITIVOS = "CREATE TABLE" + TABLE_DISPOSITIVOS + "("
                + KEY_ID + "INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT,"
                + KEY_ESTADO + " TEXT," + KEY_PIN + " INTEGER,"
                +KEY_IMAGEN + " TEXT," + KEY_IMG_ESTADO + " INTEGER" + ")";

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dispositivo contact = new Dispositivo();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNombre(cursor.getString(1));
                contact.setEstado(cursor.getString(2));
                contact.setPin(cursor.getString(3));
                contact.setImagen(cursor.getString(4));
                contact.setImg_estado(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                dispositivosList .add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return dispositivosList;
    }

    public int totalDeDispositivos() {
        String countQuery = "SELECT  * FROM " + TABLE_DISPOSITIVOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateDispositivo(Dispositivo dispositivo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, dispositivo.getNombre());
        values.put(KEY_ESTADO, dispositivo.getEstado());
        values.put(KEY_PIN, dispositivo.getPin());
        values.put(KEY_IMAGEN, dispositivo.getImagen());
        values.put(KEY_IMG_ESTADO, dispositivo.getImg_estado());

        // updating row
        return db.update(TABLE_DISPOSITIVOS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dispositivo.getId()) });
    }

    public void borrarDispositivo (Dispositivo dispositivo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPOSITIVOS, KEY_ID + " = ?",
                new String[] { String.valueOf(dispositivo.getId()) });
        db.close();
    }
}
