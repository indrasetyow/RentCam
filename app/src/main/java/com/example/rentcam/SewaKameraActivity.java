package com.example.rentcam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SewaKameraActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView lama;//mendeklarasikan textview
    RadioGroup promo;//mendeklarasikan radiogroup promo
    RadioButton weekday, weekend;//mendeklarasikan radiobutton weekday dan weekend
    Button sewa, tambah, kurang;//mendeklarasikan button sewa, tambah, dan kurang

    String sPenyewa, sMotor, sLama, idmotor, idpenyewa, tanggal, sMot, sPromo;//membuat parameter

    private Spinner spinner, spinner1;//menginisialisasikan sipinner
    DataHelper dbHelper;//mendeklarasikan pemanggilan database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa_kamera);//memanggil layout sewa kamera

        getSupportActionBar().setTitle("Sewa Kamera");//memberi judul halaman activity

        dbHelper = new DataHelper(this);//memanggil database

        spinner = findViewById(R.id.spinner);//memanggil layout id spinner
        spinner1 = findViewById(R.id.spinner1);//memanggil layout id spinner1
        sewa = findViewById(R.id.btnSewa);//memanggil layout id btnsewa
        promo = findViewById(R.id.promoGroup);//memanggil layout id promogroup
        weekday = findViewById(R.id.rbWeekDay);//memanggil layout id radiobutton weekday
        weekend = findViewById(R.id.rbWeekEnd);//memanggil layout id radiobutton weekend
        lama = findViewById(R.id.lmsw);//memanggil layout id lamasewa
        tambah = findViewById(R.id.bttambah);//memanggil layout id tambah
        kurang = findViewById(R.id.btkurang);//memanggil layout id kurang
        tambah.setOnClickListener(new View.OnClickListener()
        //membuat fungsi button tambah
        {
            @Override
            public void onClick(View v) {
                int a = Integer.valueOf(lama.getText().toString());
                a++;
                lama.setText(String.valueOf(a));
            }
        });
        //membuat fungsi button kurang
        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b = Integer.valueOf(lama.getText().toString());
                b--;
                lama.setText(String.valueOf(b));
            }
        });

        Calendar c = Calendar.getInstance();//membuat paramnmeter calendar
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//mengatur format tanggal calendar
        tanggal = df.format(c.getTime());

        spinner.setOnItemSelectedListener(this);
        spinner1.setOnItemSelectedListener(this);

        loadSpinnerDataMotor();//memanggil function spinner kamera
        loadSpinnerDataPenyewa();//memanggil function spinnernpenyewa


        //membuat fungsi button sewa
        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.valueOf(lama.getText().toString()) < 1) { //apabila lama sewa kurang 1 hari
                    Toast.makeText(SewaKameraActivity.this, "Lama Sewa harus lebih dari 0", Toast.LENGTH_SHORT).show();//maka muncul pesan toast
                } else {
                    sLama = lama.getText().toString();//menginput data

                    if (weekday.isChecked()) {
                        sPromo = "0.25";//membuat nilai potongan harga 25%
                    } else if (weekend.isChecked()) {
                        sPromo = "0.1";//membuat nilai ptongan harga 10%
                    }

                    Intent sw = new Intent(getApplicationContext(), CetakSewaActivity.class);
                    sw.putExtra("idp", idpenyewa);
                    sw.putExtra("idm", idmotor);
                    sw.putExtra("tgl", tanggal);
                    sw.putExtra("po", sPromo);
                    sw.putExtra("m", sLama);
                    startActivity(sw);
                }
            }
        });
    }

    //membuat fungsii spinner kamera
    private void loadSpinnerDataMotor() {
        DataHelper db = new DataHelper(getApplicationContext());
        List<String> categories = db.semuaKamera();//memanggil parameter database

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);//mengubah data menjadi array
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sMotor = parent.getItemAtPosition(position).toString();
                sMot = sMotor.substring(8);

                idmotor = sMotor.substring(0, 6);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //membuat fungsi spinner penyewa
    private void loadSpinnerDataPenyewa() {
        DataHelper db = new DataHelper(getApplicationContext());//memanggil database
        List<String> categories = db.semuaPenyewa();//memanggil parameter database

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);//mengubah data menjadi array
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sPenyewa = parent.getItemAtPosition(position).toString();
                idpenyewa = sPenyewa.substring(0, 6);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}