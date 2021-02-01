package com.example.progetto5cia_pagnucco;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityChallengeMenu extends AppCompatActivity {
    private static final String LOG = "Progetto5C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_menu);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            TextView txtTitolo = (TextView) findViewById(R.id.textViewTitolo);
            txtTitolo.setText("MODALITÀ SFIDA");
            Button btnFreeMode = (Button) findViewById(R.id.buttonFreeMode);
            btnFreeMode.setText("MODALITÀ LIBERA");
            Button btnRushMode = (Button) findViewById(R.id.buttonRushMode);
            btnRushMode.setText("MODALITÀ CORSA");
        }

        if(ActivityOptions.GetLanguage().equals("English")) {
            TextView txtTitolo = (TextView) findViewById(R.id.textViewTitolo);
            txtTitolo.setText("CHALLENGE MODE");
            Button btnFreeMode = (Button) findViewById(R.id.buttonFreeMode);
            btnFreeMode.setText("FREE MODE");
            Button btnRushMode = (Button) findViewById(R.id.buttonRushMode);
            btnRushMode.setText("RUSH MODE");
        }

        Button btnClickFreeMode = (Button) findViewById(R.id.buttonFreeMode);
        btnClickFreeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ActivityChallengeMenu.this, ActivityChallengeChoice.class);
                t.putExtra("type","free");
                startActivity(t);
            }
        });

        Button btnClickRushMode = (Button) findViewById(R.id.buttonRushMode);
        btnClickRushMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ActivityChallengeMenu.this, ActivityChallengeChoice.class);
                t.putExtra("type","rush");
                startActivity(t);
            }
        });
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
