package com.example.secretsanta;

import static android.content.Context.*;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final ArrayList<String> prenom = new ArrayList<>();
    private static final ArrayList<String> nom = new ArrayList<>();
    private static final ArrayList<String> tel = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(SmsManager.getDefaultSmsSubscriptionId());
        //SmsManager smsManager = getSystemService(SmsManager.class).createForSubscriptionId(SmsManager.getDefaultSmsSubscriptionId());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prenom.add(extras.getString("prenom"));
            nom.add(extras.getString("nom"));
            tel.add(extras.getString("tel"));
        }

        Button add = findViewById(R.id.add1);
        Button send = findViewById(R.id.send1);

        LinearLayout liste = findViewById(R.id.liste1);

        // Liste de test

        //prenom.add("PRENOM1");
        //prenom.add("PRENOM2");

        //nom.add("NOM1");
        //nom.add("NOM2");

        //tel.add("0XXXXXXXXX");
        //tel.add("0XXXXXXXXX");

        for(int i=0;i<prenom.size();i++){

            Button item = new Button(this);
            item.setText(prenom.get(i));
            int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,prenom.get(finalI)+" "+nom.get(finalI)+" "+tel.get(finalI),Toast.LENGTH_LONG).show();
                }
            });
            liste.addView(item);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent formulaire = new Intent(MainActivity.this,fiche.class);
            startActivity(formulaire);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                String verif = "false";

                ArrayList<String> choisi = new ArrayList<>(prenom);
                ArrayList<String> estChoisi = new ArrayList<>(prenom);
                ArrayList<String> toSend = new ArrayList<>();
                while(verif.equals("false")) {


                    if( choisi.size() == 1 && choisi.get(0) == estChoisi.get(0)) {
                        choisi = new ArrayList<>(prenom);
                        estChoisi = new ArrayList<>(prenom);
                        toSend.clear();
                        System.out.println("Retrying ...\n");
                    }

                    if(choisi.size() == 1){
                        //System.out.println(choisi.get(0)+" Tu es le Secret Santa de "+estChoisi.get(0));

                        //permet de retrouver le bon numero et nom et prénom de la personne choisie
                        int n = prenom.indexOf(choisi.get(0));
                        String num = tel.get(n);
                        //System.out.println(prenom.get(n)+" "+nom.get(n)+" "+tel.get(n));
                        toSend.add(num);
                        toSend.add(estChoisi.get(0));

                        choisi.remove(0);
                        estChoisi.remove(0);
                        verif = "true";
                    } else {
                        int c,eC;

                        // Empêche le secret santa de recevoir lui meme son cadeau
                        do {
                            c = rand.nextInt(choisi.size());
                            eC = rand.nextInt(estChoisi.size());
                        } while (choisi.get(c) == estChoisi.get(eC));


                        //System.out.println(choisi.get(c) + " es le SS de " + estChoisi.get(eC));
                        int n = prenom.indexOf(choisi.get(c));
                        String num = tel.get(n);

                        //System.out.println(prenom.get(n)+" "+nom.get(n)+" "+tel.get(n));

                        toSend.add(num);
                        toSend.add(estChoisi.get(eC));
                        choisi.remove(c);
                        estChoisi.remove(eC);

                    }
                }

                for(int i=0;i<toSend.size();i+=2){
                    smsManager.sendTextMessage(toSend.get(i),null,"Tu es le Secret Santa de : "+toSend.get(i+1),null,null);
                    //System.out.println("addrss:"+toSend.get(i)+" |estChoisie:"+toSend.get(i+1));
                }

            }
        });
    }
}