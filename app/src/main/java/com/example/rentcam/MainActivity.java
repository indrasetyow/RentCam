package com.example.rentcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageButton tvMotor, tvSewa, tvPenyewa, tvRental;//mendeklarasikan imagebutton
    TextView tvini;//mendeklarasikan textview
    String hariIni;//mendeklarasikan pada tanggal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//memanggil layout activity main
//membaca atribut pada layout
        tvMotor = findViewById(R.id.btnMotor);//memanggil id layout buttonkamera
        tvSewa = findViewById(R.id.btnSewa);//memanggil id layout buttonsewa
        tvPenyewa = findViewById(R.id.btnPenyewa);//memanggil id layout buttonpenyewa
        tvRental = findViewById(R.id.btnRental);//memanggil id layout buttonrental
        tvini = findViewById(R.id.tvDate);//memanggil id layout textview tanggal
//fungsi tanggal
        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);
        if (hariIni.equalsIgnoreCase("sunday")) {
            hariIni = "Minggu";
        } else if (hariIni.equalsIgnoreCase("monday")) {
            hariIni = "Senin";
        } else if (hariIni.equalsIgnoreCase("tuesday")) {
            hariIni = "Selasa";
        } else if (hariIni.equalsIgnoreCase("wednesday")) {
            hariIni = "Rabu";
        } else if (hariIni.equalsIgnoreCase("thursday")) {
            hariIni = "Kamis";
        } else if (hariIni.equalsIgnoreCase("friday")) {
            hariIni = "Jumat";
        } else if (hariIni.equalsIgnoreCase("saturday")) {
            hariIni = "Sabtu";
        }

        getToday();
//memanggil bundle dari data kamera
        tvMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DataKameraActivity.class);
                startActivity(i);
            }
        });
//memanggil bundle dari sewa kamera
        tvSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, SewaKameraActivity.class);
                startActivity(j);
            }
        });
//memanggil bundle dari Data Penyewa
        tvPenyewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Penyewa1Activity.class);
                startActivity(k);
            }
        });
//memanggil bundle dari rental kamera
        tvRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(MainActivity.this, RentalActivity.class);
                startActivity(l);
            }
        });
    }
//membuuat parameter untuk fungsi Date
    private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d", date);//membuat fungsi tangggal hari
        String monthNumber = (String) DateFormat.format("M", date);//membuat fungsi tangggal bulan
        String year = (String) DateFormat.format("yyyy", date);//membuat fungsi tangggal tahun

        int month = Integer.parseInt(monthNumber);
        String bulan = null;
        if (month == 1) {
            bulan = "Januari";
        } else if (month == 2) {
            bulan = "Februari";
        } else if (month == 3) {
            bulan = "Maret";
        } else if (month == 4) {
            bulan = "April";
        } else if (month == 5) {
            bulan = "Mei";
        } else if (month == 6) {
            bulan = "Juni";
        } else if (month == 7) {
            bulan = "Juli";
        } else if (month == 8) {
            bulan = "Agustus";
        } else if (month == 9) {
            bulan = "September";
        } else if (month == 10) {
            bulan = "Oktober";
        } else if (month == 11) {
            bulan = "November";
        } else if (month == 12) {
            bulan = "Desember";
        }
        String formatFix = hariIni + ", " + tanggal + " " + bulan + " " + year;
        tvini.setText(formatFix);
    }
}