package com.example.progetto5cia_pagnucco;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityChallengeChoice extends AppCompatActivity {
    private static final String LOG = "Progetto5C";
    private String type="";
    private boolean numero=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_choice);
        Intent t = getIntent();
        EditText time = (EditText) findViewById(R.id.editTextTimer);
        TextView TextViewTime = (TextView) findViewById(R.id.textViewTempo);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if(t.hasExtra("type"))
            type = t.getStringExtra("type");

        if(type.equals("rush")) {
            time.setVisibility(View.VISIBLE);
            TextViewTime.setVisibility(View.VISIBLE);
        }

        if(ActivityOptions.GetLanguage().equals("Italiano")) {
            TextView txtTitolo = (TextView) findViewById(R.id.textViewTitoloChoice);
            txtTitolo.setText("SCEGLI UN'IMMAGINE:");
            Button btnGo = (Button) findViewById(R.id.buttonGo);
            btnGo.setText("VAI");
            Spinner spinEng = (Spinner) findViewById(R.id.spinnerImageEng);
            spinEng.setVisibility(View.INVISIBLE);
            Spinner spinIta = (Spinner) findViewById(R.id.spinnerImageIta);
            spinIta.setVisibility(View.VISIBLE);
            if(type.equals("rush")) {
                TextView txtTime = (TextView) findViewById(R.id.textViewTempo);
                txtTime.setText("Tempo (secondi):");
                time.setHint("5 minuti di default");
            }
        }

        if(ActivityOptions.GetLanguage().equals("English")) {
            TextView txtTitolo = (TextView) findViewById(R.id.textViewTitoloChoice);
            txtTitolo.setText("CHOOSE AN IMAGE:");
            Button btnGo = (Button) findViewById(R.id.buttonGo);
            btnGo.setText("GO");
            Spinner spinIta = (Spinner) findViewById(R.id.spinnerImageIta);
            spinIta.setVisibility(View.INVISIBLE);
            Spinner spinEng = (Spinner) findViewById(R.id.spinnerImageEng);
            spinEng.setVisibility(View.VISIBLE);
            if(type.equals("rush")) {
                TextView txtTime = (TextView) findViewById(R.id.textViewTempo);
                txtTime.setText("Time (seconds):");
                time.setHint("Default is 5 minutes");
            }
        }

        Button btnClickGo = (Button) findViewById(R.id.buttonGo);
        btnClickGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(ActivityChallengeChoice.this, ActivityDisegnoChallenge.class);
                Spinner spinner = null;
                if(ActivityOptions.GetLanguage().equals("English"))
                    spinner = (Spinner) findViewById(R.id.spinnerImageEng);
                else if (ActivityOptions.GetLanguage().equals("Italiano"))
                    spinner = (Spinner) findViewById(R.id.spinnerImageIta);
                t.putExtra("image",spinner.getSelectedItem().toString());

                if (type.equals("rush")) {
                    try {
                        Integer.parseInt(time.getText().toString());
                    }
                    catch (Exception e) {
                        if(time.getText().toString().isEmpty()) {
                            t.putExtra("time", "300");
                            startActivity(t);
                        }

                        else {
                            if(ActivityOptions.GetLanguage().equals("English"))
                                Toast.makeText(ActivityChallengeChoice.this, "Insert a number.", Toast.LENGTH_LONG).show();
                            else if(ActivityOptions.GetLanguage().equals("Italiano"))
                                Toast.makeText(ActivityChallengeChoice.this, "Inserisci un numero.", Toast.LENGTH_LONG).show();
                        }

                        numero = false;
                    }

                    if(numero==true) {
                        if(ActivityOptions.GetLanguage().equals("English")) {
                            if (Integer.parseInt(time.getText().toString()) < 3600 && Integer.parseInt(time.getText().toString()) >= 0) {

                                if (Integer.parseInt(time.getText().toString()) < 60) {

                                    if (Integer.parseInt(time.getText().toString()) == 0)
                                        Toast.makeText(ActivityChallengeChoice.this,"We feeling like Sonic today aren't we?",Toast.LENGTH_LONG).show();
                                    else {
                                        AlertDialog.Builder Warning = new AlertDialog.Builder(ActivityChallengeChoice.this);
                                        Warning.setTitle("Really? Less than a minute?");

                                        Warning.setPositiveButton("'Hard' is my second name", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                t.putExtra("time", time.getText().toString());
                                                startActivity(t);
                                            }
                                        });

                                        Warning.setNegativeButton("Looks like I made a slip", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        Warning.show();
                                    }
                                } else {
                                    t.putExtra("time", time.getText().toString());
                                    startActivity(t);
                                }
                            } else
                                Toast.makeText(ActivityChallengeChoice.this, "Hours and negative values are not supported.", Toast.LENGTH_LONG).show();
                        }
                        else if(ActivityOptions.GetLanguage().equals("Italiano")) {
                            if (Integer.parseInt(time.getText().toString()) < 3600 && Integer.parseInt(time.getText().toString()) >= 0) {

                                if (Integer.parseInt(time.getText().toString()) < 60) {

                                    if (Integer.parseInt(time.getText().toString()) == 0)
                                        Toast.makeText(ActivityChallengeChoice.this,"Seeeeeeh, vabbè, neanche un secondo?",Toast.LENGTH_LONG).show();
                                    else {
                                        AlertDialog.Builder Warning = new AlertDialog.Builder(ActivityChallengeChoice.this);
                                        Warning.setTitle("Davvero? Meno di un minuto?");

                                        Warning.setPositiveButton("'Tosto' è il mio secondo nome", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                t.putExtra("time", time.getText().toString());
                                                startActivity(t);
                                            }
                                        });

                                        Warning.setNegativeButton("Ho commesso una scivolata", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        Warning.show();
                                    }
                                } else {
                                    t.putExtra("time", time.getText().toString());
                                    startActivity(t);
                                }
                            } else
                                Toast.makeText(ActivityChallengeChoice.this, "Ore e valori negativi non sono supportati.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        numero=true;
                }
                else
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
