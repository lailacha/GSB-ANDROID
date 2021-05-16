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

public class medicamentListActivity extends AppCompatActivity implements Serializable{


   private static final String TAG_depotLegalMed = "MED_DEPOTLEGAL";
    private static final String TAG_nomMed = "MED_NOMCOMMERCIAL";
    private static final String TAG_compoMed = "MED_COMPOSITION";
    private static final String TAG_contreIndicMed = "MED_CONTREINDIC";
    private static final String TAG_effetMed = "MED_EFFETS";
    //private static final String TAG_prixMed = "MED_PRIXECHANTILLON";
    private static final String TAG_codeFamMed = "FAM_CODE";

    ArrayList<medicamentListActivity.Medicament> listMed = new ArrayList<medicamentListActivity.Medicament>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicament_list);


        Button btn12 = (Button) findViewById(R.id.chargement_btn);

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new medicamentListActivity.JSONAsynchrone().execute();
            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    public class JSONAsynchrone extends AsyncTask<String, String, String> implements Serializable {

        private ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://10.0.2.2/API-GSB/TousMedicamentsSwissJson.php").build();
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

            pDialog = new ProgressDialog(medicamentListActivity.this);
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

                    String depotLegalMed = c.getString(TAG_depotLegalMed);
                    String nomMed = c.getString(TAG_nomMed);
                    String compoMed = c.getString(TAG_compoMed);
                    String effetMed = c.getString(TAG_effetMed);
                    String contreIndicMed = c.getString(TAG_contreIndicMed);
                    //Double prixMed = c.getDouble(TAG_prixMed);
                    String codeFamMed = c.getString(TAG_codeFamMed);
                    Medicament med = new Medicament(depotLegalMed, nomMed, compoMed, effetMed, contreIndicMed, codeFamMed);
                    listMed.add(med);
                }


                ListView listDesMedicaments = (ListView) findViewById(R.id.list_medicaments);
                final ArrayAdapter<Medicament> adapter = new
                        ArrayAdapter<Medicament>(medicamentListActivity.this, R.layout.support_simple_spinner_dropdown_item, listMed);
                listDesMedicaments.setAdapter(adapter);
                Button btn12 = (Button) findViewById(R.id.chargement_btn);
               btn12.setVisibility(View.GONE);

                // fin du for

                listDesMedicaments.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        Toast.makeText(medicamentListActivity.this, "Vous avez cliqué le medicament " + listMed.get(+position).nomMed,
                                Toast.LENGTH_SHORT).show();

                        Intent newO = new Intent(getApplicationContext(), showMedDetailsActivity.class);
                        newO.putExtra("medicament", listMed.get(+position));
                        startActivity(newO);


                    }});
            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.i("e",e.toString());
                Log.w("eeee", "YA UN SOUCIS");


            }

// textViewDonnees.setText(listMed.get(i).toString());

        }




    }


    public class Medicament implements Serializable {
        String depotLegalMed;
        String nomMed;
        String compoMed;
        String effetMed;
        String contreIndicMed;
       // Double prixMed;
        String codeFamMed;

        public Medicament(String depotLegalMed, String nomMed, String compoMed, String effetMed, String contreIndicMed,  String codeFamMed) {
            this.depotLegalMed = depotLegalMed;
            this.nomMed = nomMed;
            this.compoMed = compoMed;
            this.effetMed = effetMed;
            this.contreIndicMed = contreIndicMed;
            //this.prixMed = prixMed;
            this.codeFamMed = codeFamMed;
        }




        public String getDepotLegalMed() {
            return depotLegalMed;
        }

        public String getNomMed() {
            return nomMed;
        }

        public String getCompoMed() {
            return compoMed;
        }

        public String getEffetMed() {
            return effetMed;
        }

        public String getContreIndicMed() {
            return contreIndicMed;
        }

//        public Double getPrixMed() {
//            return prixMed;
//        }

        public String getCodeFamMed() {
            return codeFamMed;
        }


        @Override
        public String toString() {
            return  nomMed + " " + depotLegalMed; }

    }

}