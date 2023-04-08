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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        reportmanager = new ReportManager(this);

        reportmanager.open();

        reportList = reportmanager.getReports();

        setTitle(reportList.stream().count() + " interventions en cours");


        if(reportList.isEmpty()) {
            btnUpdateDatabase.setEnabled(false);
        } else {
            btnUpdateDatabase.setEnabled(true);
        }

        reportmanager.close();

        RecyclerView recyclerView = findViewById(R.id.MyrvData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReportListAdapter(this, reportList);

        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(dividerItemDecoration);

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

        reportList.clear();

        reportmanager.open();

        reportList.addAll(reportmanager.getReports());

        setTitle(reportList.stream().count() + " interventions en cours");

        if(reportList.isEmpty()) {
            btnUpdateDatabase.setEnabled(false);
        } else {
            btnUpdateDatabase.setEnabled(true);
        }

        reportmanager.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent modifyReport = new Intent(MainActivity.this, ModifyReport.class);
        modifyReport.putExtra("report_id",adapter.getItem(position).getId());
        startActivity(modifyReport);
    }

    public static String convertDate(String dateOrigine) {
        SimpleDateFormat sdfOrigine = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = sdfOrigine.parse(dateOrigine);

            SimpleDateFormat sdfCible = new SimpleDateFormat("yyyy/MM/dd");

            String dateCible = sdfCible.format(date);

            return dateCible;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void uploadInterventions() {
        btnUpdateDatabase.setEnabled(false);
        Toast loading = Toast.makeText(MainActivity.this, "Chargement", Toast.LENGTH_SHORT);
        loading.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONArray itemStorage = new JSONArray();
        JSONObject object = new JSONObject();
        int numberOfIntervention = (int) reportList.stream().count();
        try {
            int i;
            for(i = 0;i<reportList.stream().count();i++) {
                JSONObject item = new JSONObject();
                item.put("intervener",reportList.get(i).getIntervener());
                item.put("name",reportList.get(i).getName());
                item.put("surname",reportList.get(i).getSurname());
                item.put("address",reportList.get(i).getAddress());
                item.put("brand",reportList.get(i).getBrand());
                item.put("boiler",reportList.get(i).getBoiler());
                item.put("dateEntryService",convertDate(reportList.get(i).getDateEntryService()));
                item.put("dateIntervention",convertDate(reportList.get(i).getDateIntervention()));
                item.put("serialNumber",reportList.get(i).getSerialNumber());
                if(reportList.get(i).getDescription().isEmpty()){
                    item.put("description","Rien à signaler");
                } else {
                    item.put("description",reportList.get(i).getDescription());
                }
                item.put("duration",reportList.get(i).getDuration());
                System.out.println(item);
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
                        btnUpdateDatabase.setEnabled(true);

                        Intent restartActivity = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(restartActivity);

                        loading.cancel();
                        if(numberOfIntervention > 1) {
                            Toast.makeText(MainActivity.this, "Envoyées !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Envoyée !", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnUpdateDatabase.setEnabled(true);
                loading.cancel();
                Toast.makeText(MainActivity.this, "Connexion indisponible !", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        });
        requestQueue.add(jsonRequest);
    }

}