package com.example.rentcam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CetakSewaActivity extends AppCompatActivity {

    TextView idpenyewa, nama, notelp, alamat, idmotor, namo, lama, promo, total, tgl, harga, prtotal;//mendeklarasikan textview
    Button cetak;//mendeklarasikan button
    String sTot, sTot1, sHarga,sHarga1, sPromo, sLama, sprTot;//mendeklarasikan String
    int iLama, iHarga,  iSub, iPromo;//menginisialisasikan nilai integer
    double dPromo, dTotal, dsubP, dLama, dHarga;//menginisialiasikan double
    protected Cursor cursor, cursor1;//menginisialisasikan cursor
    DataHelper dbHelper;//mendeklarasikan database

    Bitmap bitmap, scaleBitmap;//mendeklasrasikan bitmap
    int pageWidth = 1200;//mendeklarasikann nilai page
    Date dateTime;//mendeklarasik tanggal
    DateFormat dateFormat;//mendeklarasikan format tanggal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_sewa);//memanggil activity cetak sewa

        idpenyewa = findViewById(R.id.tvidPenyewa);//memanggil id layout
        nama = findViewById(R.id.tvnmPenyewa);//memanggil id layout
        notelp = findViewById(R.id.tvnoPenyewa);//memanggil id layout
        alamat = findViewById(R.id.tvalPenyewa);//memanggil id layout
        idmotor = findViewById(R.id.tvidMotor);//memanggil id layout
        namo = findViewById(R.id.tvMotor);//memanggil id layout
        lama = findViewById(R.id.tvLama);//memanggil id layout
        promo = findViewById(R.id.tvPromo);//memanggil id layout
        prtotal = findViewById(R.id.tvPrTotal);//memanggil id layout
        total = findViewById(R.id.tvTotal);//memanggil id layout
        tgl = findViewById(R.id.tvTgl);//memanggil id layout
        harga = findViewById(R.id.tvHarga);//memanggil id layout
        cetak = findViewById(R.id.btnPrint);//memanggil id layout

        tgl.setText(getIntent().getStringExtra("tgl"));//mendeklarasikan id tanggal
        idpenyewa.setText(getIntent().getStringExtra("idp"));//mendeklarasikan id penyewa
        idmotor.setText(getIntent().getStringExtra("idm"));//mendekalarasikan id kamera
        lama.setText(getIntent().getStringExtra("m")+" Hari");//mendeklarasikan lama hari
        sPromo = getIntent().getStringExtra("po");//mendeklarasikan promo

        getSupportActionBar().setTitle("Cetak Nota Pembayaran");//memberi judul halaman activity

        //mengambil data dari tabel kamera
        dbHelper = new DataHelper(this);//memanggil database
        SQLiteDatabase db = dbHelper.getReadableDatabase();//membaca database
        cursor = db.rawQuery("SELECT * FROM kamera WHERE id_kamera = '" + getIntent().getStringExtra("idm") +"'", null);//mengeksekusu tabel kamera dan membaca id kamera
        cursor.moveToFirst();
        //mengembalikan nilai
        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            namo.setText(cursor.getString(1).toString());
            sHarga = cursor.getString(2).toString();
            sHarga1 = formatRupiah(Double.parseDouble(sHarga));//memanggil function formatrupiah
            harga.setText(sHarga1);

        }

        //mengambil data dari tabel penyewa
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();//deklarasi membaca database
        cursor1 = db1.rawQuery("SELECT * FROM penyewa WHERE id = '" + getIntent().getStringExtra("idp") +"'", null);//mengeksekusi tabel penyewa dan id
        cursor1.moveToFirst();
        //mengembalikan nilai
        if (cursor1.getCount()>0){
            cursor1.moveToPosition(0);
            nama.setText(cursor1.getString(1).toString());
            alamat.setText(cursor1.getString(2).toString());
            notelp.setText(cursor1.getString(3).toString());
        }

        //mengakumulasi harga, lama sewa, dan harga promo
        iHarga =Integer.parseInt(sHarga.replaceAll("[\\D]", ""));
        dHarga = Double.parseDouble(sHarga.replaceAll("[\\D]", ""));
        sLama = lama.getText().toString();
        iLama = Integer.parseInt(sLama.replaceAll("[\\D]", ""));
        dLama = Double.parseDouble(sLama.replaceAll("[\\D]", ""));
        iSub = iHarga * iLama;
        dPromo = Double.parseDouble(sPromo);
        dsubP = dPromo * dHarga *dLama;
        dTotal = iSub - dsubP;
        sprTot = formatRupiah(dsubP);
        sTot1 = String.valueOf(dTotal);
        sTot = formatRupiah(dTotal);
        prtotal.setText(sprTot);
        total.setText(sTot);
        iPromo =  (int) (dPromo * 100);
        sPromo = String.valueOf(iPromo);
        promo.setText(sPromo+"%");


        //cover header
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_tks);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);

        //membuat permission
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();
    }

    private void createPDF() {
        cetak.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)//membuat minimum API versi Android KITKAT
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {

                dateTime = new Date();//mendeklarasikan tanggal

                //mendapatkan input
                if (idmotor.getText().toString().length() == 0 ||
                        idpenyewa.getText().toString().length() == 0 ||
                        lama.getText().toString().length() == 0 ||
                        promo.getText().toString().length() == 0) {
                    Toast.makeText(CetakSewaActivity.this, "Data tidak boleh kosong!", Toast.LENGTH_LONG).show();
                } else {
                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();

                    //membuat fungsi data tersimpan dalam PDF
                    PdfDocument.PageInfo pageInfo
                            = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();
                    canvas.drawBitmap(scaleBitmap, 0, 0, paint);

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    titlePaint.setColor(Color.WHITE);
                    titlePaint.setTextSize(70);
                    canvas.drawText("Nota Pembayaran", pageWidth / 2, 500, titlePaint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(35f);
                    canvas.drawText("Nama Penyewa: " + nama.getText(), 20, 590, paint);
                    canvas.drawText("Nomor Tlp: " + notelp.getText(), 20, 640, paint);

                    dateFormat = new SimpleDateFormat("yyMMddHHmm");
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("No. Pesanan: " + dateFormat.format(dateTime), pageWidth - 20, 590, paint);

                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    canvas.drawText("Tanggal: " + dateFormat.format(dateTime), pageWidth - 20, 640, paint);

                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    canvas.drawText("Pukul: " + dateFormat.format(dateTime), pageWidth - 20, 690, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    canvas.drawRect(20, 780, pageWidth - 20, 860, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawText("ID Motor.", 40, 830, paint);
                    canvas.drawText("Motor", 200, 830, paint);
                    canvas.drawText("Harga", 700, 830, paint);
                    canvas.drawText("Lama", 900, 830, paint);
                    canvas.drawText("Total", 1050, 830, paint);

                    canvas.drawLine(180, 790, 180, 840, paint);
                    canvas.drawLine(680, 790, 680, 840, paint);
                    canvas.drawLine(880, 790, 880, 840, paint);
                    canvas.drawLine(1030, 790, 1030, 840, paint);


                    canvas.drawText("" + idmotor.getText(), 40, 950, paint);
                    canvas.drawText("" + namo.getText(), 200, 950, paint);
                    canvas.drawText(String.valueOf(harga.getText()), 700, 950, paint);
                    canvas.drawText(String.valueOf(iLama), 900, 950, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(formatRupiah(Double.parseDouble(String.valueOf(iSub))), pageWidth - 40, 950, paint);
                    paint.setTextAlign(Paint.Align.LEFT);

                    canvas.drawLine(400, 1200, pageWidth - 20, 1200, paint);
                    canvas.drawText("Sub Total", 700, 1250, paint);
                    canvas.drawText(":", 900, 1250, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(formatRupiah(Double.parseDouble(String.valueOf(iSub))), pageWidth - 40, 1250, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("PPN (" + sPromo + "%)", 700, 1300, paint);
                    canvas.drawText(":", 900, 1300, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(""+prtotal.getText().toString(), pageWidth - 40, 1300, paint);
                    paint.setTextAlign(Paint.Align.LEFT);

                    paint.setColor(Color.rgb(247, 147, 30));
                    canvas.drawRect(680, 1350, pageWidth - 20, 1450, paint);

                    paint.setColor(Color.BLACK);
                    paint.setTextSize(50f);
                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Total", 700, 1415, paint);
                    paint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(sTot), pageWidth - 40, 1415, paint);

                    pdfDocument.finishPage(page);

                    dateFormat = new SimpleDateFormat("yyMMddHHmm");//mengatur format tanggal
                    File file = new File(Environment.getExternalStorageDirectory(), "/"+ dateFormat.format(dateTime)+" Rental Kamera.pdf");//mengatur format file
                    try {
                        pdfDocument.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    pdfDocument.close();
                    SQLiteDatabase dbH = dbHelper.getWritableDatabase();//menulis data kedalam database
                    dbH.execSQL("INSERT INTO sewa (id_sewa, tgl, id, id_kamera, promo, lama, total) VALUES ('" +//mengeksekusi insert kedalam tabel sewa
                            dateFormat.format(dateTime) + "','" +
                            tgl.getText().toString() + "','" +
                            getIntent().getStringExtra("idp") + "','" +
                            getIntent().getStringExtra("idm") + "','" +
                            sPromo + "','" +
                            iLama + "','" +
                            sTot + "');");
                    dbH.execSQL("UPDATE kamera SET status = 'n' WHERE id_kamera ='"+getIntent().getStringExtra("idm")+"';");//mengeksekusi query update tabel kamera
                    RentalActivity.mi.RefreshList2();//memanggil activity rental
                    Toast.makeText(getApplicationContext(), "Penyewaan kamera Berhasil, dan Nota Pembayaran sudah dibuat", Toast.LENGTH_LONG).show();//memberikan pesan toast
                    startActivity(new Intent(CetakSewaActivity.this, MainActivity.class));//memulai activity
                }
            }
        });

    }

    //membuat function format rupiah
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}

