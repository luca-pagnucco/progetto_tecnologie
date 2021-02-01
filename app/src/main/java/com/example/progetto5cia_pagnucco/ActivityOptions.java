package com.example.progetto5cia_pagnucco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

//ricorda lo royalty free ukulele

public class ActivityOptions extends AppCompatActivity {
    private static String language = "English";     //purtoppo, se te chiudi l'applicazione non con la freccia (backPressed) non salva l'ultima modiifica
    private static String song = null;
    private static MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        RadioButton English = (RadioButton) findViewById(R.id.radioButtonEnglish);
        RadioButton Italiano = (RadioButton) findViewById(R.id.radioButtonItaliano);
        Button btnPlay = (Button) findViewById(R.id.buttonPlay);
        TextView TextViewLanguage = (TextView) findViewById(R.id.textViewLanguage);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (language.equals("English")) {
            English.setChecked(true);
            Italiano.setChecked(false);
            TextViewLanguage.setText("Language:");
            TextView TextViewTitoloOpzioni = (TextView) findViewById(R.id.textViewTitoloOpzioni);
            TextViewTitoloOpzioni.setText("SETTINGS");
            TextView txtSong = (TextView) findViewById(R.id.textViewSong);
            txtSong.setText("Song:");
            btnPlay.setText("PLAY");
            Spinner spinIta = (Spinner) findViewById(R.id.spinnerSongIta);
            spinIta.setVisibility(View.INVISIBLE);
            Spinner spinEng = (Spinner) findViewById(R.id.spinnerSongIng);
            spinEng.setVisibility(View.VISIBLE);
        } else if (language.equals("Italiano")) {
            Italiano.setChecked(true);
            English.setChecked(false);
            TextViewLanguage.setText("Lingua:");
            TextView TextViewTitoloOpzioni = (TextView) findViewById(R.id.textViewTitoloOpzioni);
            TextViewTitoloOpzioni.setText("IMPOSTAZIONI");
            TextView txtSong = (TextView) findViewById(R.id.textViewSong);
            txtSong.setText("Canzone:");
            btnPlay.setText("SUONA");
            Spinner spinEng = (Spinner) findViewById(R.id.spinnerSongIng);
            spinEng.setVisibility(View.INVISIBLE);
            Spinner spinIta = (Spinner) findViewById(R.id.spinnerSongIta);
            spinIta.setVisibility(View.VISIBLE);
        }

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                English.setChecked(true);
                Italiano.setChecked(false);
                language = "English";
                TextViewLanguage.setText("Language:");
                TextView txtSong = (TextView) findViewById(R.id.textViewSong);
                txtSong.setText("Song:");
                btnPlay.setText("PLAY");
                Spinner spinIta = (Spinner) findViewById(R.id.spinnerSongIta);
                spinIta.setVisibility(View.INVISIBLE);
                Spinner spinEng = (Spinner) findViewById(R.id.spinnerSongIng);
                spinEng.setVisibility(View.VISIBLE);
            }
        });

        Italiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Italiano.setChecked(true);
                English.setChecked(false);
                language = "Italiano";
                TextViewLanguage.setText("Lingua:");
                TextView txtSong = (TextView) findViewById(R.id.textViewSong);
                txtSong.setText("Canzone:");
                btnPlay.setText("SUONA");
                Spinner spinEng = (Spinner) findViewById(R.id.spinnerSongIng);
                spinEng.setVisibility(View.INVISIBLE);
                Spinner spinIta = (Spinner) findViewById(R.id.spinnerSongIta);
                spinIta.setVisibility(View.VISIBLE);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            Spinner songs = null;
            @Override
            public void onClick(View v) {
                if(language.equals("English"))
                    songs = (Spinner) findViewById(R.id.spinnerSongIng);
                else if (language.equals("Italiano"))
                    songs = (Spinner) findViewById(R.id.spinnerSongIta);
                song = songs.getSelectedItem().toString();
                if(!song.equals("Stop"))
                    mp.release();

                switch(song) {
                    case "Dancing Polish Cow (8-bit)":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.mucca_polacca_8_bit);
                        mp = EasterEgg(mp, ActivityOptions.this);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "#IoPensoPositivo OST":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.iopensonegativo);
                        mp = EasterEgg(mp, ActivityOptions.this);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "Marty Robbins - Big Iron":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.grandeferro);
                        mp = EasterEgg(mp, ActivityOptions.this);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "Deltarune OST - Trash Machine":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.macchina_spazzatura);
                        mp = EasterEgg(mp, ActivityOptions.this);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "The Chalkeaters - It Just Works":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.todd_godd);
                        mp = EasterEgg(mp, ActivityOptions.this);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "DONT! Please, just DO NOT!!":
                    case "NO! Ti prego, NON FARLO!!":
                        mp = MediaPlayer.create(ActivityOptions.this, R.raw.arco_dornante);
                        mp.setLooping(true);
                        mp.start();
                        break;
                    case "Stop":
                        if(mp.isPlaying())
                            mp.stop();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public static String GetLanguage() {
        return language;
    }

    public static void SetLanguage(String l) {
        language = l;
    }

    public static String GetSong() {
        return song;
    }

    public static void SetSong(String s) {
        song = s;
    }

    public static void ResumeSong(Context context) {
            switch (song) {
                case "Dancing Polish Cow (8-bit)":
                    mp = MediaPlayer.create(context, R.raw.mucca_polacca_8_bit);
                    mp.setLooping(true);
                    mp.start();
                    break;
                case "#IoPensoPositivo OST":
                    mp = MediaPlayer.create(context, R.raw.iopensonegativo);
                    mp.setLooping(true);
                    mp.start();
                    break;
                case "Marty Robbins - Big Iron":
                    mp = MediaPlayer.create(context, R.raw.grandeferro);
                    mp.setLooping(true);
                    mp.start();
                    break;
                case "Deltarune OST - Trash Machine":
                    mp = MediaPlayer.create(context, R.raw.macchina_spazzatura);
                    mp.setLooping(true);
                    mp.start();
                    break;
                case "The Chalkeaters - It Just Works":
                    mp = MediaPlayer.create(context, R.raw.todd_godd);
                    mp.setLooping(true);
                    mp.start();
                    break;
                default:
                    break;
            }
    }

    public MediaPlayer EasterEgg(MediaPlayer mp2, Context context) {
        Random r = new Random();
        int n = r.nextInt(10);
        if (n==1)
            mp2 = MediaPlayer.create(context, R.raw.spurs);
        return mp2;
    }

    @Override
    public void onBackPressed() {
        if (language.equals("Italiano")) {
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
        } else if (language.equals("English")) {
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



