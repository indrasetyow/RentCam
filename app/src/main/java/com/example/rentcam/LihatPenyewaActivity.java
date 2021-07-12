package com.example.rentcam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LihatPenyewaActivity extends AppCompatActivity {

    protected Cursor cursor;//menginisialisasi cursor
    com.example.rentcam.DataHelper dbHelper;//menginisialisasi database
    Button btn2;//mendeklarasikan button
    TextView txt1, txt2, txt3, txt4;//mendeklarasikan textview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_penyewa);//memanggil layout lihat penyewa



        getSupportActionBar().setTitle("Lihat Data Penyewa");//memberi judul pada halaman activity

        dbHelper = new com.example.rentcam.DataHelper(this);//deklarasi memanggil database
        txt1 = findViewById(R.id.txt1);//memanggil id layout text
        txt2 = findViewById(R.id.txt2);//memanggil id layout text
        txt3 = findViewById(R.id.txt3);//memanggil id layout text
        txt4 = findViewById(R.id.txt4);//memanggil id layout text

        //memanggil database
        SQLiteDatabase db = dbHelper.getReadableDatabase();//membaca database
        cursor = db.rawQuery("SELECT * FROM penyewa WHERE id = '" + getIntent().getStringExtra("nama") +"'", null);//membaca query database tabel penyewa dan membaca kolom nama

        cursor.moveToFirst();
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);//mengurutkan sesuai dengan nomor
            txt1.setText(cursor.getString(0).toString());
            txt2.setText(cursor.getString(1).toString());
            txt3.setText(cursor.getString(2).toString());
            txt4.setText(cursor.getString(3).toString());
        }

        //membuat fungsi button kembali
        btn2 = findViewById(R.id.btn1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}