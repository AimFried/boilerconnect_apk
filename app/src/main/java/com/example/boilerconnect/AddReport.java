package com.example.boilerconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boilerconnect.Model.DAO.ReportManager;
import com.example.boilerconnect.Model.Entity.Report;

public class AddReport extends AppCompatActivity {

    private Button btnSaveReport;
    private Button btnCancelReport;
    private ReportManager reportmanager;
    private EditText serialNumber;
    private EditText dateIntervention;

    private EditText dateEntryService;
    private EditText name;

    private EditText surname;

    private EditText address;

    private EditText duration;

    private EditText description;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        btnSaveReport = (Button) findViewById(R.id._btn_save_report);
        name = (EditText) findViewById(R.id._name);
        surname = (EditText) findViewById(R.id._surname);
        serialNumber = (EditText) findViewById(R.id._serialNumber);
        dateIntervention = (EditText) findViewById(R.id.dateIntervention);
        dateEntryService = (EditText) findViewById(R.id.dateEntryService);
        address = (EditText) findViewById(R.id._address);
        duration = (EditText) findViewById(R.id._duration);
        description = (EditText) findViewById(R.id._description);

        Spinner spinnerMarque = (Spinner) findViewById(R.id._spinner_marque);
        String[] optionsMarque = {"Marque", "Atlantic", "Elm leblanc","Viessmann"};

        Spinner spinnerModele = (Spinner) findViewById(R.id._spinner_modele);
        String[] optionsModele = {"Modèle", "Gaz classique", "Gaz basse température", "Gaz à condensation", "Gaz hybride"};

        ArrayAdapter<String> adapterMarque = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionsMarque);
        adapterMarque.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarque.setAdapter(adapterMarque);

        ArrayAdapter<String> adapterModele = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionsModele);
        adapterModele.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModele.setAdapter(adapterModele);

        setTitle("Nouvelle intervention");

            reportmanager = new ReportManager(this);

        dateIntervention.addTextChangedListener(new TextWatcher() {
            int countOldValue;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setCountOldValue(s.length());
                System.out.println(s.length());
            }

            public void setCountOldValue(int countOldValue) {
                this.countOldValue = countOldValue;
            }

            public int getCountOldValue() {
                return countOldValue;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == getCountOldValue() + 1) {
                    if(s.length() == 2) {
                        dateIntervention.setText(dateIntervention.getText().toString() + "/");
                        dateIntervention.setSelection(dateIntervention.getText().length());
                    }
                    if(s.length() == 5) {
                        dateIntervention.setText(dateIntervention.getText().toString() + "/");
                        dateIntervention.setSelection(dateIntervention.getText().length());
                    }
                    if(s.length() == 10) {
                        dateIntervention.setText(dateIntervention.getText());
                        dateIntervention.setSelection(dateIntervention.getText().length());
                    }
                } else {

                }
                System.out.println(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Called after the text is changed
                // You can perform your action here, such as updating a list or calling a method
            }
        });


        btnSaveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Ouvre l'acces a la base de données
                reportmanager.open();

                //Crée un nouveau contact et peuple l'objet avec les valeurs des champs de l'activité
                Report newReport = new Report(0,"");
                newReport.setName(name.getText().toString());
                newReport.setSurname(surname.getText().toString());
                newReport.setAddress(address.getText().toString());
                newReport.setBrand(spinnerMarque.getSelectedItem().toString());
                newReport.setBoiler(spinnerModele.getSelectedItem().toString());
                newReport.setDateEntryService(dateEntryService.getText().toString());
                newReport.setDateIntervention(dateIntervention.getText().toString());
                newReport.setSerialNumber(serialNumber.getText().toString());
                newReport.setDescription(description.getText().toString());
                newReport.setDuration(duration.getText().toString());

                //Ajoute le contact à la base de données
                reportmanager.addReport(newReport);

                // Ferme l'acces a la base
                reportmanager.close();

                // Ferme l'activité une fois l'ajout terminé.
                Toast.makeText(AddReport.this, "Intervention enregistrée", Toast.LENGTH_SHORT).show();
                AddReport.this.finish();
            }
        });
        }
    }