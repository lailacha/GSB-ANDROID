package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class showVisiteur extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_visiteur);

        this.textView = (TextView) findViewById(R.id.visitor_name);

       listActivity.Visiteur visiteur = (listActivity.Visiteur) getIntent().getSerializableExtra("visiteur");
        this.textView.setText(visiteur.getNomVis());



    }
}