package com.example.progetto5cia_pagnucco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG = "Progetto5C";
    private final int MY_PERMISSIONS_REQUEST_ACCESS_EXTERNAL_STORAGE = 101;
    public static boolean StorageAccess=false;
    private final String explain_eng = "This app needs to access files in the storage if you want to do the challenge mode with a custom image and to save your fantastic masterpieces.";
    private final String explain_ita = "Questa app necessita il permesso di accesso ai file in memoria se vuoi usare foto custom nella modalità sfida o salvare i tuoi capolavori.";
    private int numRifiuti=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        ActivityOptions.SetLanguage(sharedPref.getString("language","English"));
        ActivityOptions.SetSong(sharedPref.getString("song",null));
        ActivityOptions.ResumeSong(MainActivity.this);

        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            Button btnNormalMode = (Button) findViewById(R.id.buttonNormalMode);
            btnNormalMode.setText("MODALITÀ NORMALE");
            Button btnChallengeMode = (Button) findViewById(R.id.buttonChallengeMode);
            btnChallengeMode.setText("MODALITÀ SFIDA");
        }

        if(ActivityOptions.GetLanguage().equals("English")) {
            Button btnNormalMode = (Button) findViewById(R.id.buttonNormalMode);
            btnNormalMode.setText("NORMAL MODE");
            Button btnChallengeMode = (Button) findViewById(R.id.buttonChallengeMode);
            btnChallengeMode.setText("CHALLENGE MODE");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_ACCESS_EXTERNAL_STORAGE);
        else
           StorageAccess=true;

        Button btnClickNormalMode = (Button) findViewById(R.id.buttonNormalMode);
        btnClickNormalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, ActivityDisegno.class);
                startActivity(t);
            }
        });

        Button btnClickChallengeMode = (Button) findViewById(R.id.buttonChallengeMode);
        btnClickChallengeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, ActivityChallengeMenu.class);
                startActivity(t);
            }
        });

        Button btnClickOptions = (Button) findViewById(R.id.buttonOptions);
        btnClickOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, ActivityOptions.class);
                startActivity(t);
            }
        });
    }

    @Override
    protected void onRestart() {  //se non fai questo, quando torna dalla activity opzioni non cambia la lingua siccome non evoce il metodo onCreate
        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            Button btnNormalMode = (Button) findViewById(R.id.buttonNormalMode);
            btnNormalMode.setText("MODALITÀ NORMALE");
            Button btnChallengeMode = (Button) findViewById(R.id.buttonChallengeMode);
            btnChallengeMode.setText("MODALITÀ SFIDA");
        }

        if(ActivityOptions.GetLanguage().equals("English")) {
            Button btnNormalMode = (Button) findViewById(R.id.buttonNormalMode);
            btnNormalMode.setText("NORMAL MODE");
            Button btnChallengeMode = (Button) findViewById(R.id.buttonChallengeMode);
            btnChallengeMode.setText("CHALLENGE MODE");
        }

        super.onRestart();
    }

    public void checkPermission(final String myPermission, final int MY_PERMISSIONS_REQUEST, final String explanation) {

        if (ContextCompat.checkSelfPermission(this, myPermission) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, myPermission)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                if(ActivityOptions.GetLanguage().equals("English")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Why permit access to external storage?");
                    builder.setMessage(explanation);
                    builder.setNeutralButton("Close", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    // Request permission
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{myPermission}, MY_PERMISSIONS_REQUEST);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if (ActivityOptions.GetLanguage().equals("Italiano")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Perché permettere l'accesso alla memoria?");
                    builder.setMessage(explanation);
                    builder.setNeutralButton("Chiudi", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    // Request permission
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{myPermission}, MY_PERMISSIONS_REQUEST);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }
        else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this, new String[]{myPermission}, MY_PERMISSIONS_REQUEST);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if(ActivityOptions.GetLanguage().equals("English")) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        Toast myToast = Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT);
                        myToast.show();
                        StorageAccess = true;

                    } else if (numRifiuti == 0) {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                        Toast myToast = Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT);
                        myToast.show();
                        numRifiuti++;
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_ACCESS_EXTERNAL_STORAGE, explain_eng);
                    }
                    return;
                }
                else if (ActivityOptions.GetLanguage().equals("Italiano")) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        Toast myToast = Toast.makeText(this, "Permesso Accettato!", Toast.LENGTH_SHORT);
                        myToast.show();
                        StorageAccess = true;

                    } else if (numRifiuti == 0) {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                        Toast myToast = Toast.makeText(this, "Permesso rifiutato!", Toast.LENGTH_SHORT);
                        myToast.show();
                        numRifiuti++;
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_ACCESS_EXTERNAL_STORAGE, explain_ita);
                    }
                    return;
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onBackPressed() {

        if (ActivityOptions.GetLanguage().equals("Italiano")) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Chiusura Applicazione");
            builder.setMessage("Vuoi davvero chiudere l'app?");
            builder.setCancelable(true);
            builder.setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss(); // quit AlertDialog
                }
            });
            builder.setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss(); // quit AlertDialog
                    finish(); // quit Activity
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        }
        else if (ActivityOptions.GetLanguage().equals("English")) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Close the App");
            builder.setMessage("Do you really want to close the app?");
            builder.setCancelable(true);
            builder.setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss(); // quit AlertDialog
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss(); // quit AlertDialog
                    finish(); // quit Activity
                }
            });
            android.app.AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    protected void onDestroy() {  //purtoppo se si preme il tasto sx dei sipositivi Samsung (quello che visualizza le finestre aperte) e si chiude l'app da li non salva i dati ma solo se si preme la freccia
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("language", ActivityOptions.GetLanguage());
        editor.putString("song", ActivityOptions.GetSong());
        editor.commit();
        super.onDestroy();
    }
}