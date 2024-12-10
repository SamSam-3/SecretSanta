package com.example.secretsanta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class fiche extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fiche);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button confirm = (Button) findViewById(R.id.confirm);
        EditText prenom = (EditText) findViewById(R.id.prenom);
        EditText nom = (EditText) findViewById(R.id.nom);
        EditText tel = (EditText) findViewById(R.id.tel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(fiche.this, MainActivity.class);
                i.putExtra("prenom",prenom.getText().toString());
                i.putExtra("nom",nom.getText().toString());
                i.putExtra("tel",tel.getText().toString());
                startActivity(i);
            }
        });
    }
}