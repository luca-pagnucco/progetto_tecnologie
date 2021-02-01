package com.example.progetto5cia_pagnucco;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ActivityDisegnoChallenge extends AppCompatActivity {
    private static final String LOG = "Progetto5C";
    private static PaintView paintView;
    private static ProgressBar PBTime = null;
    private static String time = null;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView image = null;
    private Boolean galleria = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disegno_challenge);
        paintView = (PaintView) findViewById(R.id.paintViewChallenge);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        paintView.enable();
        image = (ImageView) findViewById(R.id.Immagine);
        PBTime = (ProgressBar) findViewById(R.id.progressBarTime);
        Intent t =getIntent();
        String immagine=null;
        time = null;
        galleria = false;
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            Button btnSave = (Button) findViewById(R.id.buttonSaveChallenge);
            btnSave.setText("SALVA IN GALLERIA");
        }

        if(ActivityOptions.GetLanguage().equals("English")) {
            Button btnSave = (Button) findViewById(R.id.buttonSaveChallenge);
            btnSave.setText("SAVE TO GALLERY");
        }

        if(t.hasExtra("image"))
            immagine = t.getStringExtra("image");
        if(t.hasExtra("time"))
            time = t.getStringExtra("time");

        if(time!=null) {
            PBTime.setVisibility(View.VISIBLE);
            PBTime.setMin(0);
            PBTime.setMax(Integer.parseInt(time));
            MyService.Restart();
            startService(new Intent(ActivityDisegnoChallenge.this, MyService.class));
        }

        if(immagine.equals("Random") || immagine.equals("Casuale")) {
            String[] images = getResources().getStringArray(R.array.images_eng);
            int n = 0;
            while(n==0 || n==images.length-1)
                n = GeneraNumero(images.length);
            immagine = images[n];
        }

        if(immagine.equals("Upload from gallery") || immagine.equals("Carica da galleria")) {
            if(MainActivity.StorageAccess) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                galleria = true;
            }
            else {
                if(ActivityOptions.GetLanguage().equals("Italiano"))
                    Toast.makeText(ActivityDisegnoChallenge.this, "Permesso non accettato", Toast.LENGTH_LONG).show();
                else if(ActivityOptions.GetLanguage().equals("English"))
                    Toast.makeText(ActivityDisegnoChallenge.this, "Permission not granted", Toast.LENGTH_LONG).show();
                finish();
            }

        }

        switch(immagine) {
            case "App icon":
            case "Icona app":
                image.setImageResource(R.drawable.icon);
                break;
            case "Gabibbo":
                image.setImageResource(R.drawable.gabibbo);
                break;
            case "Giuseppe Conte":
                image.setImageResource(R.drawable.il_mirabolante_uomo);
                break;
            case "Frank Horrigan":
                image.setImageResource(R.drawable.franco_origami);
                break;
            case "Android Studio":
                image.setImageResource(R.drawable.dolore);
                break;
            default:
                break;
        }

        Button btnClickSave = (Button) findViewById(R.id.buttonSaveChallenge);
        btnClickSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityOptions.GetLanguage().equals("English")) {
                    if (MainActivity.StorageAccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisegnoChallenge.this);
                        builder.setTitle("Name your masterpiece (no extension needed):");
                        // Set up the input
                        final EditText input = new EditText(ActivityDisegnoChallenge.this);
                        // Specify the type of input expected; this, for example
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setHint("Name");
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("Perfection", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!input.getText().toString().isEmpty() || input.getText().toString().equalsIgnoreCase("null"))
                                    FileUtils.saveImage(input.getText().toString() + ".png", "progetto5C", ActivityDisegnoChallenge.this, paintView.getBitmap());

                                else {
                                    AlertDialog.Builder Warning = new AlertDialog.Builder(ActivityDisegnoChallenge.this);
                                    Warning.setTitle("Do you really want to name this piece of art just 'null'?");

                                    Warning.setPositiveButton("That is my very wish", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FileUtils.saveImage("null.png", "progetto5C", ActivityDisegnoChallenge.this, paintView.getBitmap());
                                        }
                                    });

                                    Warning.setNegativeButton("Actually, no", new DialogInterface.OnClickListener() {    //l'intenzione qui era di richiamare builder.create() per richiedere il nome; ma purtoppo non si può :(
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    Warning.show();
                                }
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else
                        Toast.makeText(ActivityDisegnoChallenge.this, "Permission not granted", Toast.LENGTH_LONG).show();
                }

                else if(ActivityOptions.GetLanguage().equals("Italiano")) {
                    if (MainActivity.StorageAccess) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisegnoChallenge.this);
                        builder.setTitle("Nomina il tuo capolavoro (estensione non richiesta):");
                        // Set up the input
                        final EditText input = new EditText(ActivityDisegnoChallenge.this);
                        // Specify the type of input expected; this, for example
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setHint("Nome");
                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("Perfezione", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!input.getText().toString().isEmpty() || input.getText().toString().equalsIgnoreCase("null"))
                                    FileUtils.saveImage(input.getText().toString() + ".png", "anDRAWoid", ActivityDisegnoChallenge.this, paintView.getBitmap());

                                else {
                                    AlertDialog.Builder Warning = new AlertDialog.Builder(ActivityDisegnoChallenge.this);
                                    Warning.setTitle("Vuoi davvero chiamare questo pezzo di arte solo 'null'?");

                                    Warning.setPositiveButton("Questo è il mio volere", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FileUtils.saveImage("null.png", "anDRAWoid", ActivityDisegnoChallenge.this, paintView.getBitmap());
                                        }
                                    });

                                    Warning.setNegativeButton("In realtà, no", new DialogInterface.OnClickListener() {    //l'intenzione qui era di richiamare builder.create() per richiedere il nome; ma purtoppo non si può :(
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    Warning.show();
                                }
                            }
                        });

                        builder.setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else
                        Toast.makeText(ActivityDisegnoChallenge.this, "Permesso non accettato", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private int GeneraNumero(int bound) {
        Random r = new Random();
        int n = r.nextInt(bound);
        return n;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(ActivityOptions.GetLanguage().equals("Italiano"))
            menuInflater.inflate(R.menu.menu_ita, menu);
        else if (ActivityOptions.GetLanguage().equals("English"))
            menuInflater.inflate(R.menu.menu_engl, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        if(galleria)
            ActivityOptions.ResumeSong(this);
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.fill:
                paintView.fill();
                return true;
            case R.id.black:
                paintView.black();
                return true;
            case R.id.darkGray:
                paintView.darkGray();
                return true;
            case R.id.gray:
                paintView.gray();
                return true;
            case R.id.lightGray:
                paintView.lightGray();
                return true;
            case R.id.brown:
                paintView.brown();
                return true;
            case R.id.red:
                paintView.red();
                return true;
            case R.id.orange:
                paintView.orange();
                return true;
            case R.id.yellow:
                paintView.yellow();
                return true;
            case R.id.blue:
                paintView.blue();
                return true;
            case R.id.green:
                paintView.green();
                return true;
            case R.id.magenta:
                paintView.magenta();
                return true;
            case R.id.pinkskin:
                paintView.pinkSkin();
                return true;
            case R.id.cyan:
                paintView.cyan();
                return true;
            case R.id.rubber:
                paintView.rubber();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String GetTime() {
        return time;
    }

    public static void DisabilitaPaintView() {
        paintView.disable();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void AvanzaBarra(int progress) {
        PBTime.setProgress(progress, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }

    @Override
    public void onBackPressed() {
        if (ActivityOptions.GetLanguage().equals("Italiano")) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Chiusura Finestra");
            builder.setMessage("Vuoi davvero chiudere la finestra?");
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
        } else if (ActivityOptions.GetLanguage().equals("English")) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Close the Window");
            builder.setMessage("Do you really want to close the window?");
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
}
