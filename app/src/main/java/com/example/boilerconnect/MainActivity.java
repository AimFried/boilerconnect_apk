package com.example.boilerconnect;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.boilerconnect.Model.DAO.ReportManager;
import com.example.boilerconnect.Model.DataAdapter.ReportListAdapter;
import com.example.boilerconnect.Model.Entity.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ReportListAdapter.ItemClickListener {

    private Button btnAddReport;

    private Button btnUpdateDatabase;

    private ArrayList<Report> reportList;

    ReportListAdapter adapter;
    private ReportManager reportmanager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnUpdateDatabase = (Button) findViewById(R.id._btn_update_database);

        // On créer un ContactManager pour pouvoir récupérer la liste de contact
        reportmanager = new ReportManager(this);

        // on se connecte à la base de données
        reportmanager.open();

        // On récupère la liste de contacts contenus dans la table Contact
        reportList = reportmanager.getReports();

        setTitle(reportList.stream().count() + " interventions en cours");


        if(reportList.isEmpty()) {
            btnUpdateDatabase.setEnabled(false);
        } else {
            btnUpdateDatabase.setEnabled(true);
        }

        // on ferme la connexion
        reportmanager.close();

        RecyclerView recyclerView = findViewById(R.id.MyrvData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReportListAdapter(this, reportList);

        // On ajoute la gestion des evenements sur le clic
        adapter.setClickListener(this);

        // Puis on lie notre adapteur au RecyclerView
        recyclerView.setAdapter(adapter);

        // On ajoute un trait de séparation entre les éléments (pour faciliter la lecture et le clic
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //on lie les elements d'interface aux objets correspondants
        btnAddReport = (Button) findViewById(R.id._btn_update_report);

        btnAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addReport = new Intent(MainActivity.this, AddReport.class);
                startActivity(addReport);
            }
        });

        btnUpdateDatabase = (Button) findViewById(R.id._btn_update_database);

        btnUpdateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInterventions();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //On vide la liste
        reportList.clear();

        reportmanager.open();

        //Puis on réajoute tout le contenu de la liste de la base
        reportList.addAll(reportmanager.getReports());

        setTitle(reportList.stream().count() + " interventions en cours");

        if(reportList.isEmpty()) {
            btnUpdateDatabase.setEnabled(false);
        } else {
            btnUpdateDatabase.setEnabled(true);
        }

        reportmanager.close();

        // indique a l'adapter que les données ont été mise à jour,
        // et que le contenu de recyclerView doit être réaffiché.
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent modifyReport = new Intent(MainActivity.this, ModifyReport.class);
        modifyReport.putExtra("report_id",adapter.getItem(position).getId());
        startActivity(modifyReport);
    }

    public void uploadInterventions() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONArray itemStorage = new JSONArray();
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            int i;
            for(i = 0;i<reportList.stream().count();i++) {
                JSONObject item = new JSONObject();
                item.put("name",reportList.get(i).getName());
                item.put("surname",reportList.get(i).getSurname());
                item.put("address",reportList.get(i).getAddress());
                item.put("brand",reportList.get(i).getBrand());
                item.put("boiler",reportList.get(i).getBoiler());
                item.put("dateEntryService",reportList.get(i).getDateEntryService());
                item.put("dateIntervention",reportList.get(i).getDateIntervention());
                item.put("serialNumber",reportList.get(i).getSerialNumber());
                item.put("description",reportList.get(i).getDescription());
                item.put("duration",reportList.get(i).getDuration());
                itemStorage.put(item);
            }
            object.put("interventions",itemStorage);
            System.out.println(object);
        } catch (JSONException e) {

        }

        String url = getResources().getString(R.string.url_api);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        reportmanager.open();
                        reportmanager.removeAllContact();
                        reportList.clear();
                        reportmanager.close();

                        Intent restartActivity = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(restartActivity);

                        Toast.makeText(MainActivity.this, "Intervention(s) envoyée(s) !", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Connexion indisponible !", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        });
        requestQueue.add(jsonRequest);
    }

}