package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuMedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_med);



        Button showFamMed = (Button) findViewById(R.id.show_famMed_btn);
        Button showAllMed = (Button) findViewById(R.id.show_medicaments_btn);


        showFamMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(menuMedActivity.this, consultationMedicamentByFamilleActivity.class);
                startActivity(otherActivity);
            }
        });

        showAllMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), medicamentListActivity.class);
                startActivity(otherActivity);

            }
        });
    }
}