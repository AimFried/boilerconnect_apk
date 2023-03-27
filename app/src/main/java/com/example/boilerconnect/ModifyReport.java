package com.example.boilerconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.boilerconnect.Model.DAO.ReportManager;
import com.example.boilerconnect.Model.Entity.Report;

public class ModifyReport extends AppCompatActivity {

    private Button btnUpdateReport;
    private Button btnDeleteReport;

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
        setContentView(R.layout.activity_modify_report);

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



        btnUpdateReport = (Button) findViewById(R.id._btn_update_report);
        btnDeleteReport = (Button) findViewById(R.id._btn_delete_report);
        name = (EditText) findViewById(R.id._name);
        surname = (EditText) findViewById(R.id._surname);
        serialNumber = (EditText) findViewById(R.id._serialNumber);
        dateIntervention = (EditText) findViewById(R.id.dateIntervention);
        dateEntryService = (EditText) findViewById(R.id.dateEntryService);
        address = (EditText) findViewById(R.id._address);
        duration = (EditText) findViewById(R.id._duration);
        description = (EditText) findViewById(R.id._description);

        int id = getIntent().getIntExtra("report_id",-1);

        reportmanager = new ReportManager(this);

        reportmanager.open();

        Report report = reportmanager.getReport(id);

        setTitle("Modification de l'intervention");

        name.setText(report.getName());
        surname.setText(report.getSurname());
        serialNumber.setText(report.getSerialNumber());
        dateIntervention.setText(report.getDateIntervention());
        dateEntryService.setText(report.getDateEntryService());
        address.setText(report.getAddress());
        duration.setText(report.getDuration());
        description.setText(report.getDescription());
        spinnerMarque.setSelection(adapterMarque.getPosition(report.getBrand()));
        spinnerModele.setSelection(adapterModele.getPosition(report.getBoiler()));

        reportmanager.close();

        btnUpdateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reportmanager.open();
                Report updateReport = reportmanager.getReport(id);

                updateReport.setName(name.getText().toString());
                updateReport.setSurname(surname.getText().toString());
                updateReport.setAddress(address.getText().toString());
                updateReport.setBrand(spinnerMarque.getSelectedItem().toString());
                updateReport.setBoiler(spinnerModele.getSelectedItem().toString());
                updateReport.setDateEntryService(dateEntryService.getText().toString());
                updateReport.setDateIntervention(dateIntervention.getText().toString());
                updateReport.setSerialNumber(serialNumber.getText().toString());
                updateReport.setDescription(description.getText().toString());
                updateReport.setDuration(duration.getText().toString());

                reportmanager.updateReport(updateReport);

                reportmanager.close();
                Toast.makeText(ModifyReport.this, "Intervention modifiée", Toast.LENGTH_SHORT).show();
                ModifyReport.this.finish();
            }
        });

        btnDeleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportmanager.open();

                reportmanager.removeContact(reportmanager.getReport(id));

                reportmanager.close();

                Toast.makeText(ModifyReport.this, "Intervention supprimée !", Toast.LENGTH_SHORT).show();

                ModifyReport.this.finish();




                //BottomSheetDialog bottomSheet = new BottomSheetDialog();
                //bottomSheet.show(getSupportFragmentManager(),"");
            }
        });


    }
}