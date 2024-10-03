package com.adamas.androidcourse;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    private Thread thread;

    private int counter;

    private MyBinder binder = new MyBinder();

    public MyService() {
        counter = 0;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        counter++;
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
    }

    public class MyBinder extends Binder {

        public void startCounting() {
            MyService.this.startCounting();
        }

        public int getCounter() {
            return MyService.this.getCounter();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICE", "Starting the service");
        return super.onStartCommand(intent, flags, startId);
    }

    public void startCounting() {
        thread.start();
    }

    public int getCounter() {
        return counter;
    }
}