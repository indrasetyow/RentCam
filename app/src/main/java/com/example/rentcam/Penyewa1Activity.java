package com.example.rentcam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Penyewa1Activity extends AppCompatActivity {

    String[] daftar;//mendeklarasikan daftar
    ListView ls2;//mendeklarasikan ListView
    Menu menu;
    protected Cursor cursor;//menginisialisasikan cursor
    DataHelper dbcenter;//mendeklarasikan database
    public static Penyewa1Activity ma;//menginisalisasikan activity penyewa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa1);//memanggil layout activity penyewa

        getSupportActionBar().setTitle("Data Penyewa");//memeberi jdudl pada activity data penyewa

        ImageButton fab = findViewById(R.id.btnA2);//memanggil floatingbutton pada layout
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kj = new Intent(Penyewa1Activity.this, TambahPenyewaActivity.class);//memanggil activitty penyewa
                startActivity(kj);//memulai activity penyewaactivity
            }
        });

        ma = this;
        dbcenter = new DataHelper(this);//menginisialiasikan database
        RefreshList1();//mengambil fungsi dari refreshlist
    }
//membuat fungsi refresh list
    public void RefreshList1() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();//membaca database
        cursor = db.rawQuery("SELECT * FROM penyewa", null);//membaca data dengan memasukkan query
        daftar = new String[cursor.getCount()];//mengembalikan nilai
        cursor.moveToFirst();//mengembalikan set kosong

        //mengembalikan nilai
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(0).toString()+" - "+cursor.getString(1).toString();
        }
        ls2 = findViewById(R.id.ls2);//memanggil id layout
        ls2.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ls2.setSelected(true);
        ls2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final String id = selection.substring(0,5);//mengekstrak substring
                final CharSequence[] dialogitem = {"Lihat Data Penyewa", "Ubah Data Penyewa", "Hapus Data Penyewa"};//memberi pesan pada dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Penyewa1Activity.this);
                builder.setTitle("Pilihan");//memberi judul pada halaman activity
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), LihatPenyewaActivity.class);//memanggil lihat penyewa activity
                                i.putExtra("nama", id); //memanggil database tabel penyewa kolom nama
                                startActivity(i);//memulai activity
                                break;
                            case 1:
                                Intent b = new Intent(getApplicationContext(), UbahPenyewaActivity.class);// memanggil ubah penyewa activity
                                b.putExtra("nama", id);// memanggil database tabel penyewa kolom nama
                                startActivity(b);// memulai activity
                                break;

                                //membuat fungsi delete
                            case 2:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();//menulis data ke database
                                db.execSQL("DELETE FROM penyewa WHERE id = '" + id + "'");//memberi query delete pada tabel penyewa
                                RefreshList1();//memanggil function refreshlist
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ls2.getAdapter()).notifyDataSetInvalidated();
    }

    //fungsi menu belum tersedia
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}