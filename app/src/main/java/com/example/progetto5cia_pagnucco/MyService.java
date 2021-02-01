package com.example.progetto5cia_pagnucco;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.progetto5cia_pagnucco.MainActivity;
import com.example.progetto5cia_pagnucco.R;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MyService extends Service {
    private static final String TAG = "LukeExampleService";
    private static final int APP_PERMISSIONS = 0;
    // indica se il servizio e’ attivo
    private boolean isRunning  = false;
    private int time = 0;
    private int secondi = 0;
    private int minuti = 0;
    private static boolean stop=false;

    private int i;

    public static void Stop() {stop=true;}

    public static void Restart() {stop=false;}

    // costruttore
    public MyService() {
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    // collegamento con altre APP non necessario (ma il metodo onBind va implementato cmq)
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ActivityOptions.GetLanguage().equals("Italiano"))
            Toast.makeText(this, "Timer avviato.", Toast.LENGTH_LONG).show();
        else if(ActivityOptions.GetLanguage().equals("English"))
            Toast.makeText(this, "Timer started.", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Service onStartCommand");
        isRunning = true;
        // createMessageDialog().show();
        // Qui viene avviato il thread che implementa il servizio in background:
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Oggetto utilizzato per comunicare con il thread principale (UIThread)
                Handler handler = new Handler(Looper.getMainLooper());


                time = Integer.parseInt(ActivityDisegnoChallenge.GetTime());
                String fullstops = "";
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (i = 0; i < time; i++) {

                    if (stop == true)
                        break;

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    fullstops = fullstops + ".";
                    if(isRunning){
                        Log.i(TAG, "Service running"+fullstops);
                    }
                    // il metodo post serve a inviare un messaggio al UIThread
                    // (in questo caso viene inviato un oggetto eseguibile cioè Runnable)
                    handler.post(new Runnable() { // the runnable object
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            /*secondi=i;

                            if(secondi>=60) {
                                minuti=secondi/60;
                                secondi=secondi-(60*minuti);
                            }

                            String timer = ((minuti<10)?"0":"") + minuti + ":" +((secondi<10)?"0":"") + secondi;
                            ActivityDisegnoChallenge.setText(timer);*/
                            ActivityDisegnoChallenge.AvanzaBarra(i);
                        }
                    });
                }

                if(stop==false) {
                    handler.post(new Runnable() { // the runnable object
                        @Override
                        public void run() {
                            try { createMessageDialog().show(); }    //il try catch è perché sul telefono crasha e da un exception
                            catch (Exception e) {createToast();}
                            ActivityDisegnoChallenge.DisabilitaPaintView();
                        }
                    });
                }


                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();
        return Service.START_STICKY;
    }

    private void createToast() {
        if(ActivityOptions.GetLanguage().equals("Italiano"))
            Toast.makeText(this, "Tempo scaduto!", Toast.LENGTH_LONG).show();
        else if(ActivityOptions.GetLanguage().equals("English"))
            Toast.makeText(this, "Time's Up!", Toast.LENGTH_LONG).show();
    }

    private AlertDialog createMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            builder.setTitle("Well");
            builder.setMessage("Time's up!");
            builder.setCancelable(true);
            builder.setNeutralButton("Close", new
                    DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            dialog.dismiss();
                            //finish();  // quit app}
                        }
            });
        }
        else if(ActivityOptions.GetLanguage().equals("English")) {
            builder.setTitle("Oh Beh");
            builder.setMessage("Tempo scaduto!");
            builder.setCancelable(true);
            builder.setNeutralButton("Chiusi", new
                    DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            dialog.dismiss();
                            //finish();  // quit app
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        return alert;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
        // qui deve essere terminato il thread che implementa il servizio in background
        isRunning = false;
    }
}
