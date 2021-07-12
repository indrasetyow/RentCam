package com.example.rentcam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

public class UbahPenyewaActivity extends AppCompatActivity {

    protected Cursor cursor;//menginisialisasi curosr
    DataHelper dbHelper;//mendeklarasikan database
    Button btn1, btn2;//mendeklarasikan button
    EditText txt1, txt2, txt3, txt4;//mendeklarasikan edittext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_penyewa);//memanggil layout ubah penyewa

        getSupportActionBar().setTitle("Ubah Data Penyewa");//memberi judul pada halaman activity

        dbHelper =new DataHelper(this);//memanggil database
        txt1 = findViewById(R.id.etA);//memanggil layout edittext
        txt2 = findViewById(R.id.etB);//memanggil layout edittext
        txt3 = findViewById(R.id.etC);//memanggil layout edittext
        txt4 = findViewById(R.id.etD);//memanggil layout edittext

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM penyewa WHERE id = '" +//mengeksekusi query table penyewa
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        //mengembalikan nilai
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            txt1.setText(cursor.getString(0).toString());
            txt2.setText(cursor.getString(1).toString());
            txt3.setText(cursor.getString(2).toString());
            txt4.setText(cursor.getString(3).toString());
        }
        btn1 = findViewById(R.id.btnA1);//memanggil button ubah
        btn2 = findViewById(R.id.btnA2);//memanggil button kembali

        //membuat fungsi button ubah data
        btn1.setOnClickListener(new View.OnClickListener() {
            //apabila data yang dimasukkan tidak sesuai
            @Override
            public void onClick(View v) {
                if (isEmpty(txt2.getText().toString())|| isEmpty(txt3.getText().toString())|| isEmpty(txt4.getText().toString())) {//memberi fungsi data tidak boleh kosong
                    Toast.makeText(UbahPenyewaActivity.this, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();//apabila data diisi kosong akan muncul pesan toast
                } else if (txt1.length() < 5 ){//hanya dapat memasukkan angka 5 karakter
                    Toast.makeText(UbahPenyewaActivity.this, "ID Penyewa harus diisi 5 Karakter Angka!", Toast.LENGTH_SHORT).show();//apabila melebihi 5 angka akan muncul pesan toast
                }else
                    //apabila data yang dimasukkan sesuai
                    {
                SQLiteDatabase db = dbHelper.getWritableDatabase();//menulis data kedalam database
                db.execSQL("UPDATE penyewa SET nama ='"+//mengeksekusi query update ke tabel penyewa
                        txt2.getText().toString() + "', alamat='" +//mengeksekusi kolom alamat
                        txt3.getText().toString() + "', no_hp='" +//mengeksekusi  kolom no hp
                        txt4.getText().toString() + "' WHERE  id='" +//mengeksekusi kolom id
                        txt1.getText().toString() + "'");
                Toast.makeText(getApplicationContext(),"Data Berhasil diubah", Toast.LENGTH_SHORT).show();//memberi pesan toast
                Penyewa1Activity.ma.RefreshList1();//memanggil activity penyewa dan refreshlist
                finish();
            }
        }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}