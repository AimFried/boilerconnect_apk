package com.example.boilerconnect.Model.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.boilerconnect.Model.Entity.Report;
import com.example.boilerconnect.Model.TheSQLiteDB;


public class ReportManager {
    private static final String TABLE_NAME = "reports";
    public static final String KEY_ID_CONTACT="id";
    public static final String KEY_NAME_CONTACT="name";
    public static final String KEY_SURNAME_CONTACT="surname";
    public static final String KEY_ADDRESS_CONTACT="address";
    public static final String KEY_BRAND_CONTACT="brand";
    public static final String KEY_BOILER_CONTACT="boiler";
    public static final String KEY_DATEENTRYSERVICE_CONTACT="dateEntryService";
    public static final String KEY_DATEINTEVENTION_CONTACT="dateIntervention";
    public static final String KEY_SEERIALNUMBER_CONTACT="serialNumber";
    public static final String KEY_DESCRIPTION_CONTACT="description";
    public static final String KEY_DURATION_CONTACT="duration";
    public static final String CREATE_TABLE_CONTACT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_CONTACT+" INTEGER primary key," +
            " "+KEY_NAME_CONTACT+" TEXT," +
            " "+KEY_SURNAME_CONTACT+" TEXT," +
            " "+KEY_ADDRESS_CONTACT+" TEXT," +
            " "+KEY_BRAND_CONTACT+" TEXT," +
            " "+KEY_BOILER_CONTACT+" TEXT," +
            " "+KEY_DATEENTRYSERVICE_CONTACT+" TEXT," +
            " "+KEY_DATEINTEVENTION_CONTACT+" TEXT," +
            " "+KEY_SEERIALNUMBER_CONTACT+" TEXT," +
            " "+KEY_DESCRIPTION_CONTACT+" TEXT," +
            " "+KEY_DURATION_CONTACT+" TEXT" +
            ");";
    private TheSQLiteDB maBase; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ReportManager(Context context)
    {
        maBase = TheSQLiteDB.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBase.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addReport(Report report) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_CONTACT, report.getName());
        values.put(KEY_SURNAME_CONTACT, report.getSurname());
        values.put(KEY_ADDRESS_CONTACT, report.getAddress());
        values.put(KEY_BRAND_CONTACT, report.getBrand());
        values.put(KEY_BOILER_CONTACT, report.getBoiler());
        values.put(KEY_DATEENTRYSERVICE_CONTACT, report.getDateEntryService());
        values.put(KEY_DATEINTEVENTION_CONTACT, report.getDateIntervention());
        values.put(KEY_SEERIALNUMBER_CONTACT, report.getSerialNumber());
        values.put(KEY_DESCRIPTION_CONTACT, report.getDescription());
        values.put(KEY_DURATION_CONTACT, report.getDuration());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateReport(Report report) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_CONTACT, report.getName());
        values.put(KEY_SURNAME_CONTACT, report.getSurname());
        values.put(KEY_ADDRESS_CONTACT, report.getAddress());
        values.put(KEY_BRAND_CONTACT, report.getBrand());
        values.put(KEY_BOILER_CONTACT, report.getBoiler());
        values.put(KEY_DATEENTRYSERVICE_CONTACT, report.getDateEntryService());
        values.put(KEY_DATEINTEVENTION_CONTACT, report.getDateIntervention());
        values.put(KEY_SEERIALNUMBER_CONTACT, report.getSerialNumber());
        values.put(KEY_DESCRIPTION_CONTACT, report.getDescription());
        values.put(KEY_DURATION_CONTACT, report.getDuration());

        String where = KEY_ID_CONTACT+" = ?";
        String[] whereArgs = {report.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int removeContact(Report report) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_CONTACT+" = ?";
        String[] whereArgs = {report.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public int removeAllContact() {
        return db.delete(TABLE_NAME,null,null);
    }

    @SuppressLint("Range")
    public Report getReport(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Report a=new Report(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CONTACT+"="+id, null);

        if (c.moveToFirst()) {
            a.setId(c.getInt(c.getColumnIndex(KEY_ID_CONTACT)));
            a.setName(c.getString(c.getColumnIndex(KEY_NAME_CONTACT)));
            a.setSurname(c.getString(c.getColumnIndex(KEY_SURNAME_CONTACT)));
            a.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS_CONTACT)));
            a.setBrand(c.getString(c.getColumnIndex(KEY_BRAND_CONTACT)));
            a.setBoiler(c.getString(c.getColumnIndex(KEY_BOILER_CONTACT)));
            a.setDateEntryService(c.getString(c.getColumnIndex(KEY_DATEENTRYSERVICE_CONTACT)));
            a.setDateIntervention(c.getString(c.getColumnIndex(KEY_DATEINTEVENTION_CONTACT)));
            a.setSerialNumber(c.getString(c.getColumnIndex(KEY_SEERIALNUMBER_CONTACT)));
            a.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION_CONTACT)));
            a.setDuration(c.getString(c.getColumnIndex(KEY_DURATION_CONTACT)));
            c.close();
        }
        return a;
    }

    @SuppressLint("Range")
    public ArrayList<Report> getReports() {

        ArrayList<Report> reportList = new ArrayList<Report>();

        //récupère dans un curseur le résultat du select sur la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (c.moveToFirst()) {
            //parcourt le curseur obtenu, jusqu'a la fin, et créer pour chaque enregistrement un objet Contact
            do {
                Report a = new Report(0, "");
                a.setId(c.getInt(c.getColumnIndex(KEY_ID_CONTACT)));
                a.setName(c.getString(c.getColumnIndex(KEY_NAME_CONTACT)));
                a.setSurname(c.getString(c.getColumnIndex(KEY_SURNAME_CONTACT)));
                a.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS_CONTACT)));
                a.setBrand(c.getString(c.getColumnIndex(KEY_BRAND_CONTACT)));
                a.setBoiler(c.getString(c.getColumnIndex(KEY_BOILER_CONTACT)));
                a.setDateEntryService(c.getString(c.getColumnIndex(KEY_DATEENTRYSERVICE_CONTACT)));
                a.setDateIntervention(c.getString(c.getColumnIndex(KEY_DATEINTEVENTION_CONTACT)));
                a.setSerialNumber(c.getString(c.getColumnIndex(KEY_SEERIALNUMBER_CONTACT)));
                a.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION_CONTACT)));
                a.setDuration(c.getString(c.getColumnIndex(KEY_DURATION_CONTACT)));

                // ajoute l'objet créé à la ArrayList de Contact qui sera renvoyée.
                reportList.add(a);
            }
            while (c.moveToNext());
        }
        c.close();

        return reportList;
    }


}
