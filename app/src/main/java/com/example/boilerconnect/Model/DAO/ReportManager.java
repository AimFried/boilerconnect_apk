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
    public static final String KEY_ID="id";

    public static final String KEY_INTERVENER="intervener";
    public static final String KEY_NAME="name";
    public static final String KEY_SURNAME="surname";
    public static final String KEY_ADDRESS="address";
    public static final String KEY_BRAND="brand";
    public static final String KEY_BOILER="boiler";
    public static final String KEY_DATEENTRYSERVICE="dateEntryService";
    public static final String KEY_DATEINTEVENTION="dateIntervention";
    public static final String KEY_SEERIALNUMBER="serialNumber";
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_DURATION="duration";
    public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +
            " (" +
            " "+KEY_ID+" INTEGER primary key," +
            " "+KEY_INTERVENER+" TEXT," +
            " "+KEY_NAME+" TEXT," +
            " "+KEY_SURNAME+" TEXT," +
            " "+KEY_ADDRESS+" TEXT," +
            " "+KEY_BRAND+" TEXT," +
            " "+KEY_BOILER+" TEXT," +
            " "+KEY_DATEENTRYSERVICE+" TEXT," +
            " "+KEY_DATEINTEVENTION+" TEXT," +
            " "+KEY_SEERIALNUMBER+" TEXT," +
            " "+KEY_DESCRIPTION+" TEXT," +
            " "+KEY_DURATION+" TEXT" +
            ");";
    private TheSQLiteDB maBase;
    private SQLiteDatabase db;

    public ReportManager(Context context)
    {
        maBase = TheSQLiteDB.getInstance(context);
    }

    public void open()
    {
        db = maBase.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public long addReport(Report report) {

        ContentValues values = new ContentValues();
        values.put(KEY_INTERVENER, report.getIntervener());
        values.put(KEY_NAME, report.getName());
        values.put(KEY_SURNAME, report.getSurname());
        values.put(KEY_ADDRESS, report.getAddress());
        values.put(KEY_BRAND, report.getBrand());
        values.put(KEY_BOILER, report.getBoiler());
        values.put(KEY_DATEENTRYSERVICE, report.getDateEntryService());
        values.put(KEY_DATEINTEVENTION, report.getDateIntervention());
        values.put(KEY_SEERIALNUMBER, report.getSerialNumber());
        values.put(KEY_DESCRIPTION, report.getDescription());
        values.put(KEY_DURATION, report.getDuration());

        return db.insert(TABLE_NAME,null,values);
    }

    public int updateReport(Report report) {

        ContentValues values = new ContentValues();
        values.put(KEY_INTERVENER, report.getIntervener());
        values.put(KEY_NAME, report.getName());
        values.put(KEY_SURNAME, report.getSurname());
        values.put(KEY_ADDRESS, report.getAddress());
        values.put(KEY_BRAND, report.getBrand());
        values.put(KEY_BOILER, report.getBoiler());
        values.put(KEY_DATEENTRYSERVICE, report.getDateEntryService());
        values.put(KEY_DATEINTEVENTION, report.getDateIntervention());
        values.put(KEY_SEERIALNUMBER, report.getSerialNumber());
        values.put(KEY_DESCRIPTION, report.getDescription());
        values.put(KEY_DURATION, report.getDuration());

        String where = KEY_ID + " = ?";
        String[] whereArgs = {report.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int removeContact(Report report) {

        String where = KEY_ID+" = ?";
        String[] whereArgs = {report.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public int removeAllContact() {
        return db.delete(TABLE_NAME,null,null);
    }

    @SuppressLint("Range")
    public Report getReport(int id) {

        Report a=new Report(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+"="+id, null);

        if (c.moveToFirst()) {
            a.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            a.setIntervener(c.getString(c.getColumnIndex(KEY_INTERVENER)));
            a.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            a.setSurname(c.getString(c.getColumnIndex(KEY_SURNAME)));
            a.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
            a.setBrand(c.getString(c.getColumnIndex(KEY_BRAND)));
            a.setBoiler(c.getString(c.getColumnIndex(KEY_BOILER)));
            a.setDateEntryService(c.getString(c.getColumnIndex(KEY_DATEENTRYSERVICE)));
            a.setDateIntervention(c.getString(c.getColumnIndex(KEY_DATEINTEVENTION)));
            a.setSerialNumber(c.getString(c.getColumnIndex(KEY_SEERIALNUMBER)));
            a.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            a.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));
            c.close();
        }
        return a;
    }

    @SuppressLint("Range")
    public ArrayList<Report> getReports() {

        ArrayList<Report> reportList = new ArrayList<Report>();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Report a = new Report(0, "");
                a.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                a.setIntervener(c.getString(c.getColumnIndex(KEY_INTERVENER)));
                a.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                a.setSurname(c.getString(c.getColumnIndex(KEY_SURNAME)));
                a.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
                a.setBrand(c.getString(c.getColumnIndex(KEY_BRAND)));
                a.setBoiler(c.getString(c.getColumnIndex(KEY_BOILER)));
                a.setDateEntryService(c.getString(c.getColumnIndex(KEY_DATEENTRYSERVICE)));
                a.setDateIntervention(c.getString(c.getColumnIndex(KEY_DATEINTEVENTION)));
                a.setSerialNumber(c.getString(c.getColumnIndex(KEY_SEERIALNUMBER)));
                a.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                a.setDuration(c.getString(c.getColumnIndex(KEY_DURATION)));

                reportList.add(a);
            }
            while (c.moveToNext());
        }
        c.close();
        return reportList;
    }
}
