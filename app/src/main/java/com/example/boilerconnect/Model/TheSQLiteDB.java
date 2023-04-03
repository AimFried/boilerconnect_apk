package com.example.boilerconnect.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.boilerconnect.Model.DAO.ReportManager;

public class TheSQLiteDB extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "db.sqlite";
        private static final int DATABASE_VERSION = 1;
        private static TheSQLiteDB sInstance;

        public static synchronized TheSQLiteDB getInstance(Context context) {
            if (sInstance == null) { sInstance = new TheSQLiteDB(context); }
            return sInstance;
        }

        private TheSQLiteDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(ReportManager.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            onCreate(sqLiteDatabase);
        }

    }
