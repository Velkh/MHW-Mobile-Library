package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mhwlibrary.Model.Model_Monsters;

public class Create_Monsters extends AppCompatActivity implements View.OnClickListener {

    private EditText edtmName, edtmImage, edtmDesc, edtmWeakImage;
    private Spinner spinnerBranch;
    private Button btnSave;
    private Model_Monsters monsters;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_monsters);

        ImageButton BackCreateMonster = (ImageButton) findViewById(R.id.BackCreateMonster);

        BackCreateMonster.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent switchActivityIntent = new Intent(Create_Monsters.this, Monsters.class);
                                                 startActivity(switchActivityIntent);
                                             }
                                         }
        );

//
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtmName = findViewById(R.id.edt_monsName);
        spinnerBranch = findViewById(R.id.edt_mType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Monsters_Type,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        edtmImage = findViewById(R.id.edt_monsPhoto);
        edtmDesc = findViewById(R.id.edt_monsDesc);
        edtmWeakImage = findViewById(R.id.monsWeakPhoto);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        monsters = new Model_Monsters(); // Instantiation of the model class
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            saveMonster();
        }
    }

    private void saveMonster() {
        String name = edtmName.getText().toString().trim();
        String type = spinnerBranch.getSelectedItem().toString().trim();
        String image = edtmImage.getText().toString().trim();
        String desc = edtmDesc.getText().toString().trim();
        String Wimage = edtmWeakImage.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(name)) {
            isEmptyFields = true;
            edtmName.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(image)) {
            isEmptyFields = true;
            edtmImage.setError("Field ini tidak boleh kosong");
        }

        if (TextUtils.isEmpty(desc)) {
            isEmptyFields = true;
            edtmDesc.setError("Field ini tidak boleh kosong");
        }

        if (!isEmptyFields) {
            Toast.makeText(Create_Monsters.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMonsters = mDatabase.child("monsters");

            String id = dbMonsters.push().getKey();
            monsters.setMons_id(id);
            monsters.setMons_name(name);
            monsters.setMons_type(type);
            monsters.setMons_image(image);
            monsters.setMons_desc(desc);
            monsters.setMons_weak(Wimage);

            dbMonsters.child(id).setValue(monsters);
            Intent intent = new Intent(Create_Monsters.this, Monsters.class);
            startActivity(intent);
            finish();
        }
    }
}
