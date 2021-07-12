package com.example.rentcam;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class RentalActivity extends AppCompatActivity {

    String[] daftar;//menginisialisasikan daftar
    ListView ls3;//mendeklaarasikan listview
    Menu menu;
    protected Cursor cursor;//menginisialisasikan curosr
    DataHelper dbcenter;//mendeklarasikan database
    public static RentalActivity mi;//menginisialisasikan rentalactivity

    //membuat method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);//memanggil activity rental

        getSupportActionBar().setTitle("Data Rental");//member judul halaman activity

        mi = this;
        dbcenter = new DataHelper(this);//memanggil database
        RefreshList2();//memanggil function refreshlist

    }

    //membuat method format rupiah
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");//memakai format rupiah indonesia
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);//mengembalikan format nilai
    }

    //membuat fungsi refreshlist
    public void RefreshList2() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();//membaca database
        cursor = db.rawQuery("SELECT kamera.id_kamera, sewa.id_sewa, sewa.tgl, penyewa.nama, kamera.namo, sewa.promo, sewa.lama, sewa.total FROM sewa, penyewa, kamera WHERE sewa.id = penyewa.id AND sewa.id_kamera = kamera.id_kamera ORDER BY id_sewa DESC", null);//membuat query semua database
        daftar = new String[cursor.getCount()];//mengembalikan nilai
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            //menerima input nilai
            daftar[cc] = "Kode Sewa :\t"+cursor.getString(0).toString() +""+cursor.getString(1).toString() + "\nTanggal\t:\t" + cursor.getString(2).toString()+ "\n" + cursor.getString(3).toString()+
                    " Menyewa Kamera "+cursor.getString(4).toString() + "\nselama " + cursor.getString(6).toString()+ " hari, dengan potongan Harga sebanyak " + cursor.getString(5).toString()+ "%\nTotal Pembayaran\t:\t" + cursor.getString(7).toString()+"\n";

            ls3 = findViewById(R.id.ls3);//memanggil id layout
            ls3.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));//mengkonversikan data menjadi array
            ls3.setSelected(true);
            ls3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                    final String selection = daftar[arg2];
                    final String id = selection.substring(17,27);
                    final String idm = selection.substring(12,17);
                    final CharSequence[] dialogitem = {"Kamera kembali ", "Hapus Data"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentalActivity.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                //membuat fungsi mengembalikan data
                                case 0:
                                SQLiteDatabase dba = dbcenter.getWritableDatabase();//menulis kembali ke database
                                dba.execSQL("UPDATE kamera SET status = 'y' WHERE id_kamera = '"+idm+"'");//membuat query eksekusi tabel kamera
                                RefreshList2();//memanggil refreshlist
                                Toast.makeText(getApplicationContext(), "Motor Telah Dikembalikan", Toast.LENGTH_SHORT).show();//memberi pesan dengan toast
                                break;

                                case 1:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();//menulis data untuk menghapus
                                db.execSQL("DELETE FROM sewa WHERE id_sewa = '" + id + "'");//membuat query mengeksekusi tabel sewa
                                RefreshList2();//memanggil refreshlist
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();//memberi pesan dengan toast
                                break;
                            }
                        }
                    });
                    builder.create().show();
                }
            });
            ((ArrayAdapter) ls3.getAdapter()).notifyDataSetInvalidated();
        }
    }
}