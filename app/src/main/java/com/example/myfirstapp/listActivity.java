package com.example.myfirstapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class listActivity extends AppCompatActivity implements Serializable{


    private static final String TAG_matricule = "VIS_MATRICULE";

    private static final String TAG_nom = "VIS_NOM";
    private static final String TAG_prenom = "VIS_PRENOM";
    private static final String TAG_adresse = "VIS_ADRESSE";
    private static final String TAG_ville = "VIS_VILLE";
    private static final String TAG_cp = "VIS_CP";
    ArrayList<Visiteur> oslist = new ArrayList<Visiteur>();


    /**
     * Cette méthode s'active dès l'instanciation de l'activité
     * Elle va executer une requète asyncrhone au clique sur le bouton de chargement
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.w("myApp", "no network");
        Button btn1;
        Button btn = btn1 = (Button) findViewById(R.id.chargement_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONAsynchrone().execute();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class JSONAsynchrone extends AsyncTask<String, String, String> implements Serializable {

        private ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://10.0.2.2/API-GSB/TousVisiteursSwissJson.php").build();
            Response response = null;
            try {
                response = client.newCall(request).execute(); return response.body().string();
            } catch (IOException e) {
                e.printStackTrace(); }
            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(listActivity.this);
            pDialog.setMessage("Je charge les données ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); }


        @Override
        protected void onPostExecute(String json) {
            Log.w("eeee", "Jentre dans le post");
            pDialog.dismiss();

          //  textViewDonnees.setText(json.toString());
            JSONObject c = null;
            try {
                // Getting JSON Array from URL
                JSONArray android = new JSONArray(json);
                for (int i = 0; i < android.length(); i++) {
                    c = android.getJSONObject(i);
//VIS_MATRICULE, VIS_NOM, VIS_PRENOM, VIS_ADRESSE, VIS_VILLE, VIS_CP
                    String id = c.getString(TAG_matricule);
                    String nom = c.getString(TAG_nom);
                    String prenom = c.getString(TAG_prenom);
                    String adresse = c.getString(TAG_adresse);
                    String ville = c.getString(TAG_ville);
                    String cp = c.getString(TAG_cp);
                    Visiteur vis = new Visiteur(id, nom, prenom, adresse, ville, cp);
                    oslist.add(vis);
                }


                ListView listDesVisiteurs = (ListView) findViewById(R.id.listView1);
                final ArrayAdapter<Visiteur> adapter = new
                       ArrayAdapter<Visiteur>(listActivity.this, R.layout.support_simple_spinner_dropdown_item, oslist);
                listDesVisiteurs.setAdapter(adapter);
                Log.w("eeee", "YA PAS DE SOUCIS");

                // fin du for

                listDesVisiteurs.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Toast.makeText(listActivity.this, "Vous avez cliqué le visiteur " + oslist.get(+position).nomVis,
                                Toast.LENGTH_SHORT).show();

                        Intent newO = new Intent(getApplicationContext(), showVisiteur.class);
                        Log.d("visiteur",oslist.get(+position).toString());
                        newO.putExtra("visiteur", oslist.get(+position));
                        startActivity(newO);

                        finish();
                    }});
                }
            catch (JSONException e) {
                e.printStackTrace();
                Log.i("e",e.toString());
                Log.w("eeee", "YA UN SOUCIS");


            }

// textViewDonnees.setText(oslist.get(i).toString());

    }




    }

    public class Visiteur implements Serializable {
        String matriculeVis;
        String nomVis; String prenomVis;
        String adresseVis; String villeVis;
        String cpVis;
        public Visiteur(String matricule, String nom, String prenom, String adresse, String ville,String cp)
        {
            matriculeVis = matricule; nomVis =nom; prenomVis=prenom;
            adresseVis = adresse; villeVis =ville;
            cpVis = cp;
        }
        public String getMatriculeVis(){
            return matriculeVis; }
        public String getNomVis() {
            return nomVis; }
        public String getPrenomVis() {
            return prenomVis; }
        public String getAdresseVis() {
            return adresseVis; }
        public String getVilleVis() {
            return villeVis; }
        public String getCpVis() {
            return cpVis;
        }
        @Override
        public String toString() {
            return  nomVis + " " + prenomVis; }

    }

}

