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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhwlibrary.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mhwlibrary.Model.Model_Quests;
public class Update_Quests extends AppCompatActivity implements View.OnClickListener  {

    private EditText edtqName, edtqImage, edtqDesc;
    private Button btnUpdate;
    private Spinner spinnerBranch;
    public static final String EXTRA_QUESTS = "extra_quests";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    private Model_Quests quests;
    private String quest_id;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_quest);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtqName = findViewById(R.id.edit_qName);
        spinnerBranch = findViewById(R.id.edt_qType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Quest_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBranch.setAdapter(adapter);
        edtqImage = findViewById(R.id.edit_qPhoto);
        edtqDesc = findViewById(R.id.edit_qDesc);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        quests = getIntent().getParcelableExtra(EXTRA_QUESTS);

        if (quests != null) {
            quest_id = quests.getQuest_id();
        } else {
            quests = new Model_Quests();
        }

        if (quests != null) {
            edtqName.setText(quests.getQuest_name());
            spinnerBranch.setSelection(adapter.getPosition(quests.getQuest_type()));
            edtqImage.setText(quests.getQuest_image());
            edtqDesc.setText(quests.getQuest_desc());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateQuests();
        }
    }

    public void updateQuests() {
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
            Toast.makeText(Update_Quests.this, "Updating Data...", Toast.LENGTH_SHORT).show();

            quests.setQuest_name(name);
            quests.setQuest_type(type);
            quests.setQuest_image(image);
            quests.setQuest_desc(desc);

            DatabaseReference dbTalent = mDatabase.child("quests");

            // Update data
            dbTalent.child(quest_id).setValue(quests);
            Toast.makeText(Update_Quests.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Update_Quests.this, Quest.class);
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
                    DatabaseReference dbTalent = mDatabase.child("quests").child(quest_id);
                    dbTalent.removeValue();

                    Toast.makeText(Update_Quests.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Update_Quests.this, Quest.class);
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

