package com.example.mhwlibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhwlibrary.Monsters;
import com.example.mhwlibrary.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mhwlibrary.Model.Model_Monsters;


public class Update_Monsters extends AppCompatActivity implements View.OnClickListener  {

    private EditText edtmName, edtmImage, edtmDesc, edtmImageW;
    private Button btnUpdate;
    private Spinner spinnerBranch;
    public static final String EXTRA_MONSTER = "extra_monster";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    private Model_Monsters monsters;
    private String mons_id;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_monsters);

        ImageButton BackUpdateMonster = (ImageButton) findViewById(R.id.BackUpdateMonster);

        BackUpdateMonster.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent switchActivityIntent = new Intent(Update_Monsters.this, Monsters.class);
                                                 startActivity(switchActivityIntent);
                                             }
                                         }
        );


        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtmName = findViewById(R.id.updatelName);
        spinnerBranch = findViewById(R.id.edt_mType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Monsters_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        edtmImage = findViewById(R.id.edtmPhoto);
        edtmDesc = findViewById(R.id.updatemDesc);
        edtmImageW = findViewById(R.id.edtmPhotoW);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        monsters = getIntent().getParcelableExtra(EXTRA_MONSTER);

        if (monsters != null) {
            mons_id = monsters.getMons_id();
        } else {
            monsters = new Model_Monsters();
        }

        if (monsters != null) {
            edtmName.setText(monsters.getMons_name());
            spinnerBranch.setSelection(adapter.getPosition(monsters.getMons_type()));
            edtmImage.setText(monsters.getMons_image());
            edtmDesc.setText(monsters.getMons_desc());
            edtmImageW.setText(monsters.getMons_weak());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateMonsters();
        }
    }

    public void updateMonsters() {
        String name = edtmName.getText().toString().trim();
        String type = spinnerBranch.getSelectedItem().toString().trim();
        String image = edtmImage.getText().toString().trim();
        String desc = edtmDesc.getText().toString().trim();

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
            Toast.makeText(Update_Monsters.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            monsters.setMons_name(name);
            monsters.setMons_type(type);
            monsters.setMons_image(image);
            monsters.setMons_desc(desc);

            DatabaseReference dbTalent = mDatabase.child("monsters");

            // Update data
            dbTalent.child(mons_id).setValue(monsters);
            Toast.makeText(Update_Monsters.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Update_Monsters.this, Monsters.class);
            startActivity(intent);
            finish();  // Optional: Close UpdateActivity to avoid going back to it when pressing back in MainActivity
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_delete) {
            // Handle delete action
            showAlertDialog(ALERT_DIALOG_DELETE);
            return true;
        } else if (itemId == android.R.id.home) {
            // Handle close action
            showAlertDialog(ALERT_DIALOG_CLOSE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Cancel Changes";
            dialogMessage = "Do you want to cancel the changes?";
        } else {
            dialogTitle = "Delete Data";
            dialogMessage = "Do you want to delete this data?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isDialogClose) {
                    finish();
                } else {
                    DatabaseReference dbTalent = mDatabase.child("monsters").child(mons_id);
                    dbTalent.removeValue();

                    Toast.makeText(Update_Monsters.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Update_Monsters.this, Monsters.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
