package com.example.rentcam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailKameraActivity extends AppCompatActivity {

    protected Cursor cursor;
    String sMotor, sHarga, sGambar, sNopol;
    DataHelper dbHelper;
    TextView tMotor, tHarga, tNopol;
    ImageView iGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kamera);

        getSupportActionBar().setTitle("Detail Kamera");

        Bundle terima = getIntent().getExtras();
        String motor = terima.getString("namo");

        dbHelper = new DataHelper(this);
        tMotor = findViewById(R.id.Tmotor);
        tHarga = findViewById(R.id.Tharga);
        tNopol = findViewById(R.id.Tnopol);
        iGambar = findViewById(R.id.Imotor);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from kamera where namo = '" + motor + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            sMotor = cursor.getString(1);
            sHarga = cursor.getString(2);
            sNopol = cursor.getString(3);
        }

        if (sMotor.equals("Canon EOS 5D Mark IV")) {
            sGambar = "canoneos5d";
        } else if (sMotor.equals("Nikon D330")) {
            sGambar = "nikond330";
        } else if (sMotor.equals("Nikon D3400")) {
            sGambar = "nikond340";
        } else if (sMotor.equals("Canon EOS 750D")) {
            sGambar = "canon_750d";
        } else if (sMotor.equals("Nikon D5200")) {
            sGambar = "nikon_d5200";
        }else if (sMotor.equals("Canon EOS 5D Mark III")) {
            sGambar = "canoneos5d";
        }else if (sMotor.equals("Canon EOS 80D")) {
            sGambar = "canon_80d";
        }else if (sMotor.equals("Canon EOS 5DS R")) {
            sGambar = "canon5ds";
        }else if (sMotor.equals("Canon EOS Rebel SL 2")) {
            sGambar = "canon_rebel";
        }else if (sMotor.equals("Canon EOS 4000D")) {
            sGambar = "canon400d";
        }

        tMotor.setText(sMotor);
        tNopol.setText(sNopol);
        iGambar.setImageResource(getResources().getIdentifier(sGambar, "drawable", getPackageName()));
        tHarga.setText("Rp. " + sHarga);

    }
}