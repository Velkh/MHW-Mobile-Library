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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mhwlibrary.Location;
import com.example.mhwlibrary.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mhwlibrary.Model.Model_Locations;
public class Update_Locations extends AppCompatActivity implements View.OnClickListener  {

    private EditText edtlName, edtlImage, edtlDesc;
    private Button btnUpdate;
    public static final String EXTRA_LOCATION = "extra_location";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    private Model_Locations locations;
    private String locations_id;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_locations);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtlName = findViewById(R.id.edt_lName);
        edtlImage = findViewById(R.id.edt_lPhoto);
        edtlDesc = findViewById(R.id.edt_lDesc);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        locations = getIntent().getParcelableExtra(EXTRA_LOCATION);

        if (locations != null) {
            locations_id = locations.getMaps_id();
        } else {
            locations = new Model_Locations();
        }

        if (locations != null) {
            edtlName.setText(locations.getMaps_name());
            edtlImage.setText(locations.getMaps_image());
            edtlDesc.setText(locations.getMaps_desc());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateLocation();
        }
    }

    public void updateLocation() {
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
            Toast.makeText(Update_Locations.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            locations.setMaps_name(name);
            locations.setMaps_image(image);
            locations.setMaps_desc(desc);

            DatabaseReference dbTalent = mDatabase.child("locations");

            // Update data
            dbTalent.child(locations_id).setValue(locations);
            Toast.makeText(Update_Locations.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Update_Locations.this, Location.class);
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
                    DatabaseReference dbTalent = mDatabase.child("locations").child(locations_id);
                    dbTalent.removeValue();

                    Toast.makeText(Update_Locations.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Update_Locations.this, Location.class);
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

