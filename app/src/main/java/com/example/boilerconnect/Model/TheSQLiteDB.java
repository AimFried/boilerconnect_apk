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
            // Création de la base de données
            // on exécute ici les requêtes de création des tables
            sqLiteDatabase.execSQL(ReportManager.CREATE_TABLE_CONTACT); // création table
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            // Mise à jour de la base de données
            // méthode appelée sur incrémentation de DATABASE_VERSION
            // on peut faire ce qu'on veut ici, comme recréer la base :
            onCreate(sqLiteDatabase);
        }

    } // class MySQLite
