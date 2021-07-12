package com.example.rentcam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rentalkamera.db";// nama database
    private static final int DATABASE_VERSION = 1;
    private String KEY_NAME = "NAMA"; //menginisialisasi nama database

    //method untuk membaca database
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON"); //
        String sqlpenyewa = "create table penyewa (id integer primary key, nama text null, alamat text null, no_hp text null);";// membuat tabel penyewa
        Log.d("Data", "onCreate: "+ sqlpenyewa);
        db.execSQL(sqlpenyewa);//untuk mengeksekusi tabel penyewa

        String sqlkamera = "create table kamera(id_kamera integer primary key, namo text null, harga integer null, noseri text null , status text null);";//membuat tabel kamera
        Log.d("Data", "onCreate: "+ sqlkamera);
        db.execSQL(sqlkamera);//untuk mengeksekusi tabel kamera

        //membuat tabel sewa
        String sqlsewa = "create table sewa(id_sewa integer primary key, tgl text null, id integer null, id_kamera integer null, promo integer null, lama integer null , total double null, foreign key(id) references penyewa (id), foreign key(id_kamera) references kamera (id_kamera));";
        Log.d("Data", "onCreate: "+ sqlsewa);
        db.execSQL(sqlsewa);//untuk mengeksekusi tabel sewa

        // memasukkan data ke database tabel kamera
        db.execSQL("insert into kamera values (" +
                "'10001'," +
                "'Canon EOS 5D Mark IV'," +
                "100000," +
                "'5D Mark IV'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10002'," +
                "'Nikon D330'," +
                "90000," +
                "'D330'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10003'," +
                "'Nikon D3400'," +
                "80000," +
                "'D3400'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10004'," +
                "'Canon EOS 750D'," +
                "80000," +
                "'750D'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10005'," +
                "'Nikon D5200'," +
                "80000," +
                "'D5200'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10006'," +
                "'Canon EOS 5D Mark III'," +
                "90000," +
                "'5D Mark III'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10007'," +
                "'Canon EOS 80D'," +
                "120000," +
                "'80D'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10008'," +
                "'Canon EOS 5DS R'," +
                "100000," +
                "'5DS R'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10009'," +
                "'Canon EOS Rebel SL 2'," +
                "110000," +
                "'Rebel SL 2'," +
                "'y'" +
                ");" +
                "");
        db.execSQL("insert into kamera values (" +
                "'10010'," +
                "'Canon EOS 4000D'," +
                "100000," +
                "'4000D'," +
                "'y'" +
                ");" +
                "");

        //memasukkan data ke database tabel penyewa
        db.execSQL("insert into penyewa values (" +
                "'20001'," +
                "'Indra Setyo'," +
                "'Yogyakarta'," +
                "'08112321354'" +
                ");" +
                "");
    }

    //membuat parameter untuk membaca list kamera
    public List<String> semuaKamera() {
        List<String> motors = new ArrayList<String>();//merubah data menjadu arraylist
        String selectQuery = "select * from kamera where status = 'y'";//merubah data menjadu arraylist
        SQLiteDatabase db = this.getReadableDatabase();//membaca data dalam database
        Cursor cursor = db.rawQuery(selectQuery, null);//cursor untuk membaca data

        //perulangan data
        if (cursor.moveToFirst()) {
            do {
                motors.add(cursor.getString(0) +" - "+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();//menutup database apabila sudah tidak digunakan

        return motors;//mengembalikan nilai
    }

    //membuat parameter untuk membaca list penyewa
    public List<String> semuaPenyewa() {
        List<String> penyewas = new ArrayList<String>();//merubah data da;am arraylist
        String selectQuery = "select * from penyewa";//query memilih data dalam database tabel penyewa
        //membaca data dalam database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//cursor untuk membaca data

        //perulangan data
        if (cursor.moveToFirst()) {
            do {
                penyewas.add(cursor.getString(0) +" - "+ cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();//menutup database apabila sudah tidak digunakan

        return penyewas;//mengembalikan nilai
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
