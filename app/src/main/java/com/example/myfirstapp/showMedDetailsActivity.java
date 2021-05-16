package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class showMedDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_med_details);

        TextView medCompo = (TextView) findViewById(R.id.med_compo_txt);
        TextView medContreindic = (TextView) findViewById(R.id.med_contreindic_text);
        TextView medEffet = (TextView) findViewById(R.id.med_effet_txt);
        TextView medNom = (TextView) findViewById(R.id.nom_med_txt);

        medicamentListActivity.Medicament med = (medicamentListActivity.Medicament) getIntent().getSerializableExtra("medicament");
        medCompo.setText(med.getCompoMed());
        medContreindic.setText(med.getContreIndicMed());
        medEffet.setText(med.getEffetMed());
        medNom.setText(med.getNomMed());

    }
}