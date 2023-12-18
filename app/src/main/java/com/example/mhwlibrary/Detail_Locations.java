package com.example.mhwlibrary;

import static com.example.mhwlibrary.Update_Locations.EXTRA_LOCATION;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mhwlibrary.Model.Model_Locations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Detail_Locations extends AppCompatActivity {

    private TextView txtLName, txtLImage, txtLDesc;
    private ImageView imgDetailImage;
    private Model_Locations locations;
    private String loc_id;
    private Button btnEdit;
    private ImageView delBtn;
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_locations);

        ImageButton BackMap = (ImageButton) findViewById(R.id.BackMap);

        BackMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(Detail_Locations.this, Location.class);
                startActivity(switchActivityIntent);
                }
            }
        );

        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtLName = findViewById(R.id.txtDetailLocName);
        imgDetailImage = findViewById(R.id.detailLocimage);
        txtLDesc = findViewById(R.id.txtDetailLocDesc);
        btnEdit = findViewById(R.id.btn_edit);
        delBtn = findViewById(R.id.DelMap);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(ALERT_DIALOG_DELETE);
            }
        });

        locations = getIntent().getParcelableExtra(EXTRA_LOCATION);

        if (locations != null) {
            loc_id = locations.getMaps_id();
        } else {
            locations = new Model_Locations();
        }

        if (locations != null) {
            txtLName.setText(locations.getMaps_name());
            loadImageFromUrl(locations.getMaps_image());
            txtLDesc.setText(locations.getMaps_desc());
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Tambahkan listener untuk tombol "Edit"
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode untuk membuka UpdateActivity
                openUpdateActivity();
            }
        });
    }

    private void loadImageFromUrl(String imageUrl) {
        Picasso.get().load(imageUrl).error(R.drawable.user).into(imgDetailImage, new Callback() {
            @Override
            public void onSuccess() {
                // Gambar berhasil dimuat
            }

            @Override
            public void onError(Exception e) {
                // Terjadi kesalahan saat memuat gambar
                // Tampilkan gambar default dari drawable
                imgDetailImage.setImageResource(R.drawable.user);
            }
        });
    }

    // Metode untuk membuka UpdateActivity
    private void openUpdateActivity() {
        // Pindah ke UpdateActivity dengan membawa data Talent
        Intent intent = new Intent(Detail_Locations.this, Update_Locations.class);
        intent.putExtra(Update_Locations.EXTRA_LOCATION, locations);
        startActivity(intent);
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
            // Handle home action
            showAlertDialog(ALERT_DIALOG_CLOSE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin kembali";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose) {
                            Intent intent = new Intent(Detail_Locations.this, Location.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // hapus data
                            DatabaseReference dbTalent =
                                    mDatabase.child("locations").child(loc_id);

                            dbTalent.removeValue();

                            Toast.makeText(Detail_Locations.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Detail_Locations.this, Location.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

