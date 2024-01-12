package com.example.mhwlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mhwlibrary.Model.Model_Quests;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_Quests extends AppCompatActivity implements View.OnClickListener {

    private EditText edtqName, edtqImage, edtqDesc;
    private Spinner spinnerBranch;
    private Button btnSave;
    private Model_Quests quests;
    DatabaseReference mDatabase;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_quests);

        ImageButton BackCreateQuest = (ImageButton) findViewById(R.id.BackCreateQuests);

        BackCreateQuest.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent switchActivityIntent = new Intent(Create_Quests.this, Quest.class);
                                                 startActivity(switchActivityIntent);
                                             }
                                         }
        );


        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtqName = findViewById(R.id.edt_qName);
        spinnerBranch = findViewById(R.id.edt_qType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Quest_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        edtqImage = findViewById(R.id.edt_qImage);
        edtqDesc = findViewById(R.id.edt_qDesc);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        quests = new Model_Quests(); // Instantiation of the model class
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            saveQuests();
        }
    }

    private void saveQuests() {
        String name = edtqName.getText().toString().trim();
        String type = spinnerBranch.getSelectedItem().toString().trim();
        String image = edtqImage.getText().toString().trim();
        String desc = edtqDesc.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(name)) {
            isEmptyFields = true;
            edtqName.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(image)) {
            isEmptyFields = true;
            edtqImage.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(desc)) {
            isEmptyFields = true;
            edtqDesc.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields) {
            Toast.makeText(Create_Quests.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbQuests = mDatabase.child("quests");

            String id = dbQuests.push().getKey();
            quests.setQuest_id(id);
            quests.setQuest_name(name);
            quests.setQuest_type(type);
            quests.setQuest_image(image);
            quests.setQuest_desc(desc);

            dbQuests.child(id).setValue(quests);
            Intent intent = new Intent(Create_Quests.this, Quest.class);
            startActivity(intent);
            finish();
        }
    }
}
