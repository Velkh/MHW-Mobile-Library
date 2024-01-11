package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mhwlibrary.Model.Model_Locations;
public class Create_Locations extends AppCompatActivity implements View.OnClickListener {

    private EditText edtlName, edtlImage, edtlDesc;
    private Button btnSave;
    private Model_Locations locations;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_locations);

        ImageButton BackCreateMap = (ImageButton) findViewById(R.id.BackCreateMap);

        BackCreateMap.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               Intent switchActivityIntent = new Intent(Create_Locations.this, Location.class);
                                               startActivity(switchActivityIntent);
                                           }
                                       }
        );

        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtlName = findViewById(R.id.edt_locName);
        edtlImage = findViewById(R.id.edt_locImage);
        edtlDesc = findViewById(R.id.edt_locDesc);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        locations = new Model_Locations(); // Instantiation of the model class
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            saveMonster();
        }
    }

    private void saveMonster() {
        String name = edtlName.getText().toString().trim();
        String image = edtlImage.getText().toString().trim();
        String desc = edtlDesc.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(name)) {
            isEmptyFields = true;
            edtlName.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(image)) {
            isEmptyFields = true;
            edtlImage.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(desc)) {
            isEmptyFields = true;
            edtlDesc.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields) {
            Toast.makeText(Create_Locations.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbLocations = mDatabase.child("locations");

            String id = dbLocations.push().getKey();
            locations.setMaps_id(id);
            locations.setMaps_name(name);
            locations.setMaps_image(image);
            locations.setMaps_desc(desc);

            dbLocations.child(id).setValue(locations);
            Intent intent = new Intent(Create_Locations.this, Location.class);
            startActivity(intent);
            finish();
        }
    }
}

