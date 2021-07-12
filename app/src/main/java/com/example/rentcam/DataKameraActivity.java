package com.example.rentcam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class DataKameraActivity extends AppCompatActivity {

    String[] daftar;//mendeklarasikan array
    ListView ls1;//mendeklarasikan listview
    Menu menu;
    protected Cursor cursor;//inisialisasi cursor
    DataHelper dbcenter;//memanggil class database
    public static DataKameraActivity ma;//inisialisasi activity data kamera

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kamera);//memanggil layout data kamera

        getSupportActionBar().setTitle("Data Kamera");//memberi judul pada activity

        ma = this;
        dbcenter = new DataHelper(this);//membuat deklarasi membaca database dari class datahelper
        RefreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);   //membuat fungsi menu(belum tersedia)
        return true;
    }

    //membuat parameter refreshlist

    private void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();//membaca data dari database
        cursor = db.rawQuery("SELECT * FROM kamera WHERE status = 'y'", null);//memasukkan query
        daftar = new String[cursor.getCount()];//menghitung data yang di list
        cursor.moveToFirst();
        //melakukan perulangan data
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(1);
        }
        ls1 = findViewById(R.id.Isi);//memanggil layout id isi
        ls1.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ls1.setSelected(true);
        ls1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                Intent i = new Intent(DataKameraActivity.this, DetailKameraActivity.class);//memanggil activity data kamera
                i.putExtra("namo", selection);//memanggil kolom nama kamera
                startActivity(i);
            }
        });

        ((ArrayAdapter) ls1.getAdapter()).notifyDataSetInvalidated();
    }
}