package com.example.aplikasiservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class WallpaperChangeService extends Service implements Runnable {

    /* Refrensi gambar wallpaper disimpan dalam sebuah array, android1 dan
    android2 adalah nama file png atau jpeg anda*/
    private int wallpaperId[] = {R.drawable.android1, R.drawable.android2};

    /* Deklarasi variable yang digunakan untuk menyampaikan durasi yang dipilih user*/
    private int waktu;

    /* Deklarasi variable flag untuk menge check gambar mana yang akan ditampilkan berikutnya.*/
    private int FLAG=0;
    private Thread t;

    /* Deklarasi 2 buah variable Bitmap untuk menyimpan gambar wallpaper.*/
    private Bitmap gambar1;
    private Bitmap gambar2;

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {
        super.onStartCommand(intent, flag, startId);

        /* Peroleh bundle yang dikirim melalui intent dari MainAcitivity.*/
        Bundle bundle=intent.getExtras();

        /* Baca nilai yang tersimpan dengan kunci durasi.*/
        waktu = bundle.getInt("durasi");

        /* Inisialisasi obyek Bitmap dengan gambar wallpaper.*/
        gambar1=BitmapFactory.decodeResource(getResources(),wallpaperId[0]);
        gambar2=BitmapFactory.decodeResource(getResources(), wallpaperId[1]);

        /* Memulai sebuah thread agar service tetap berjalan di latar belakang.*/
        t = new Thread(WallpaperChangeService.this);
        t.start();
        return  START_STICKY_COMPATIBILITY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
    /* Method run() yang berisi kode yang dieksekusi oleh thread yang baru saja dibuat*/
    @Override
    public void run(){
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try {
            while (true) {
                if (FLAG==0) {
                    myWallpaper.setBitmap(gambar1);
                    FLAG++;
                }
                else {
                    myWallpaper.setBitmap(gambar2);
                    FLAG--;
                }
                Thread.sleep(100*waktu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
