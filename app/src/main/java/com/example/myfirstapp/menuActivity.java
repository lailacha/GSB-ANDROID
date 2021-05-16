package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class menuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button showVisiteurs = (Button) findViewById(R.id.show_famMed_btn);
        Button showMedicaments = (Button) findViewById(R.id.show_medicaments_btn);


        showVisiteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), listActivity.class);
                startActivity(otherActivity);
            }
        });

        showMedicaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), menuMedActivity.class);
                startActivity(otherActivity);

            }
        });
    }
}