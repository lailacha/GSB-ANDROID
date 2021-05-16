package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class consultationMedicamentByFamilleActivity extends AppCompatActivity {

    private static final String TAG_FAMILLE = "FAM_LIBELLE";
    ArrayList<consultationMedicamentByFamilleActivity.Famille> listMed = new ArrayList<consultationMedicamentByFamilleActivity.Famille>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_medicament_by_famille);

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
            Request request = new Request.Builder().url("http://10.0.2.2/API-GSB/MedicamentFamilleJson.php").build();
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

            pDialog = new ProgressDialog(consultationMedicamentByFamilleActivity.this);
            pDialog.setMessage("Je charge les données ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); }


        @Override
        protected void onPostExecute(String json) {
            Log.w("eeee", "Jentre dans le post");
            pDialog.dismiss();

            //  textViewDonnees.setText(json.toString());
            JSONObject c= null;
            try {
                // Getting JSON Array from URL
                JSONArray android = new JSONArray(json);
                for (int i = 0; i < android.length(); i++) {
                    c = android.getJSONObject(i);

                    String codeFamMed = c.getString(TAG_FAMILLE);
                    consultationMedicamentByFamilleActivity.Famille med = new consultationMedicamentByFamilleActivity.Famille(codeFamMed);
                    listMed.add(med);
                }


                ListView listDesFamilles = (ListView) findViewById(R.id.list_famille);
                final ArrayAdapter<consultationMedicamentByFamilleActivity.Famille> adapter = new
                        ArrayAdapter<consultationMedicamentByFamilleActivity.Famille>(consultationMedicamentByFamilleActivity.this, R.layout.support_simple_spinner_dropdown_item, listMed);
                listDesFamilles.setAdapter(adapter);
                Log.w("eeee", "YA PAS DE SOUCIS");

                // fin du for

                listDesFamilles.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(consultationMedicamentByFamilleActivity.this, "Vous avez cliqué le medicament " + listMed.get(+position).codeFamMed,
                                Toast.LENGTH_SHORT).show();

                        Intent newO = new Intent(getApplicationContext(), showMedDetailsActivity.class);
                        newO.putExtra("medicament", listMed.get(+position));
                        startActivity(newO);


                    }});
            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.i("e",e.toString());
            }
        }
    }


    public class Famille implements Serializable {

        // Double prixMed;
        String codeFamMed;

        public Famille(String codeFamMed) {

            this.codeFamMed = codeFamMed;
        }



        public String getCodeFamMed() {
            return codeFamMed;
        }


        @Override
        public String toString() {
            return  codeFamMed; }

    }
}