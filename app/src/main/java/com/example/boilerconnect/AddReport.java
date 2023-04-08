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

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class AddReport extends AppCompatActivity {

    private Button btnSaveReport;
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

        Spinner spinnerIntervener = (Spinner) findViewById(R.id._spinner_intervener);
        String[] optionsIntervener = {"Technicien","Merlin Soucy", "Didier Rocher", "Jean Gladu", "Guillaume Brasseur" , "Lucas Lamontagne"};

        ArrayAdapter<String> adapterMarque = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionsMarque);
        adapterMarque.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarque.setAdapter(adapterMarque);

        ArrayAdapter<String> adapterModele = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionsModele);
        adapterModele.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModele.setAdapter(adapterModele);

        ArrayAdapter<String> adapterIntervener = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, optionsIntervener);
        adapterIntervener.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIntervener.setAdapter(adapterIntervener);

        setTitle("Nouvelle intervention");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(System.currentTimeMillis());
        dateIntervention.setText(date);
        dateEntryService.setText(date);

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
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        dateEntryService.addTextChangedListener(new TextWatcher() {
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
                        dateEntryService.setText(dateEntryService.getText().toString() + "/");
                        dateEntryService.setSelection(dateEntryService.getText().length());
                    }
                    if(s.length() == 5) {
                        dateEntryService.setText(dateEntryService.getText().toString() + "/");
                        dateEntryService.setSelection(dateEntryService.getText().length());
                    }
                    if(s.length() == 10) {
                        dateEntryService.setText(dateEntryService.getText());
                        dateEntryService.setSelection(dateEntryService.getText().length());
                    }
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


        btnSaveReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validSpinnerInput() && validTextInput() && validDateInput() && validTimeInput()) {
                    reportmanager.open();

                    Report newReport = new Report(0,"");
                    newReport.setIntervener(spinnerIntervener.getSelectedItem().toString());
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

                    reportmanager.addReport(newReport);

                    reportmanager.close();

                    Toast.makeText(AddReport.this, "Rapport enregistré", Toast.LENGTH_SHORT).show();
                    AddReport.this.finish();
                } else {
                    Toast.makeText(AddReport.this, "Votre saisie est incorrect !", Toast.LENGTH_SHORT).show();
                }
            }

            private boolean validTimeInput() {
                if(duration.getText().toString() != "") {
                    return true;
                } else {
                    return false;
                }
            }

            private boolean validDateInput() {
                String DATE_PATTERN = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
                Pattern pattern = Pattern.compile(DATE_PATTERN);
               if(pattern.matcher(dateIntervention.getText().toString()).matches()
                       && pattern.matcher(dateEntryService.getText().toString()).matches()
                        && dateIntervention.getText().toString() != "" && dateEntryService.getText().toString() != "") {
                   return true;
               } else {
                   return false;
               }
            }

            private boolean validTextInput() {
                if(name.getText().toString() != "" && surname.getText().toString() != "" && address.getText().toString() != "" && serialNumber.getText().toString() != "" && description.getText().toString() != "") {
                    System.out.println("oui");
                    return true;
                }  else {
                    return false;
                }
            }

            private boolean validSpinnerInput() {
                if(spinnerIntervener.getSelectedItem().toString() != "Technicien" && spinnerMarque.getSelectedItem().toString() != "Marque" && spinnerModele.getSelectedItem().toString() != "Modèle") {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}