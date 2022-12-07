package com.vitefinetechapp.vitefinetech.Aeps2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_BANK = "banklist";
    public static final String TABLE_AEPS1 = "banklistaeps";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "aeps2.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String tablebank = "create table banklist(bank_name text,bank_code text)";
        String tableaeps1 = "create table banklistaeps(bank_name text, code text)";

        db.execSQL(tablebank);
        db.execSQL(tableaeps1);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }


    public void insertBanklist(String bank_name,String bank_code) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bank_name", bank_name);
        values.put("bank_code", bank_code);
        sqLiteDatabase.insert("banklist", null, values);

    }

    public void insertaeps1Banklist(String bank_name,String code) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bank_name", bank_name);
        values.put("code", code);
        sqLiteDatabase.insert("banklistaeps", null, values);

    }

    public void deletebanklistRecord() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("delete from " + TABLE_BANK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletebanklistaeps1Record() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("delete from " + TABLE_AEPS1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


