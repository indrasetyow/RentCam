package com.example.rentcam;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.isEmpty;

public class TambahPenyewaActivity extends AppCompatActivity {

    DataHelper dbHelper;//mendeklarasikan database
    Button btn1, btn2;//mendeklarasikan button
    EditText txt1, txt2, txt3, txt4;//mendeklarasikan edittext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penyewa);//memanggil layout activity tambah penyewa

        getSupportActionBar().setTitle("Tambah Data Penyewa");//memberi judul pada halaman activity

        dbHelper = new DataHelper(this);//memanggil database

        txt1 = findViewById(R.id.et1);//memanggil layout id edittext
        txt2 = findViewById(R.id.et2);//memanggil layout id edittext
        txt3 = findViewById(R.id.et3);//memanggil layout id edittext
        txt4 = findViewById(R.id.et4);//memanggil layout id edittext


        btn1 = findViewById(R.id.btnA);//memanggil layout id button
        btn2 = findViewById(R.id.btnB);//memanggil layout id button

        //membuat fungsi btn1
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            //apabila data yang diisi tidak sesuai dan belum terisi semua
            {
                if (isEmpty(txt2.getText().toString())|| isEmpty(txt3.getText().toString())|| isEmpty(txt4.getText().toString())) {
                    Toast.makeText(TambahPenyewaActivity.this, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();//apabila berhasil maka berhasil menambahdata
                } else if (txt1.length() < 5 )//apabila angka melebihi 5 angka
                    {
                    Toast.makeText(TambahPenyewaActivity.this, "ID Penyewa harus diisi 5 Karakter Angka!", Toast.LENGTH_SHORT).show();//memberi pesan menggunakan toast
                }else{  //apabila data yang dimasukan sesuai dan terisi semua
                    SQLiteDatabase db = dbHelper.getWritableDatabase();//menulis ke database
                    db.execSQL("INSERT INTO penyewa(id,nama, alamat, no_hp) VALUES ('" + //mengeksekusi query insert
                            txt1.getText().toString() + "','" +
                            txt2.getText().toString() + "','" +
                            txt3.getText().toString() + "','" +
                            txt4.getText().toString() + "')");
                    Toast.makeText(getApplicationContext(), "Data Berhasil dimasukan", Toast.LENGTH_SHORT).show();//memberi pesan toast
                    Penyewa1Activity.ma.RefreshList1();//mengembalikan ke penyewaactivity
                    finish();
                }
            }
        });

        //membuat fungsi kembali
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