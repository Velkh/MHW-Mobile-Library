package com.example.mhwlibrary;

import static com.example.mhwlibrary.Update_Monsters.EXTRA_MONSTER;

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

import com.example.mhwlibrary.Model.Model_Monsters;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Detail_Monsters extends AppCompatActivity {

    private TextView txtmName, txtmType, txtmDesc;
    private ImageView  imgWeakImages, imgDetailImage;
    private Model_Monsters monsters;
    private String mons_id;
    private Button btnEdit;

    private ImageButton delBtn;
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_monsters);

        ImageButton BackMonster = (ImageButton) findViewById(R.id.BackMonster);

        BackMonster.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Intent switchActivityIntent = new Intent(Detail_Monsters.this, Monsters.class);
                startActivity(switchActivityIntent);
                }
            }
        );

        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtmName = findViewById(R.id.txtDetailMonsterName);
        txtmType = findViewById(R.id.txtDetailMonsterType);
        imgDetailImage = findViewById(R.id.DetailMonsterimage);
        imgWeakImages = findViewById(R.id.monsterWeakness);
        txtmDesc = findViewById(R.id.txtDetailMonsterDesc);
        btnEdit = findViewById(R.id.btn_edit);
        delBtn = findViewById(R.id.DelMonster);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(ALERT_DIALOG_DELETE);
            }
        });
        monsters = getIntent().getParcelableExtra(EXTRA_MONSTER);

        if (monsters != null) {
            mons_id = monsters.getMons_id();
        } else {
            monsters = new Model_Monsters();
        }

        if (monsters != null && monsters.getMons_image() != null && monsters.getMons_weak() != null) {
            txtmName.setText(monsters.getMons_name());
            txtmType.setText(monsters.getMons_type());
            loadImageFromUrl(monsters.getMons_image(), imgDetailImage);
            txtmDesc.setText(monsters.getMons_desc());
            loadImageFromUrl(monsters.getMons_weak(), imgWeakImages);
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

    private void loadImageFromUrl(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).error(R.drawable.user).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                // Gambar berhasil dimuat
            }

            @Override
            public void onError(Exception e) {
                // Terjadi kesalahan saat memuat gambar
                // Tampilkan gambar default dari drawable
                imageView.setImageResource(R.drawable.user);
            }
        });
    }

    // Metode untuk membuka UpdateActivity
    private void openUpdateActivity() {
        // Pindah ke UpdateActivity dengan membawa data Talent
        Intent intent = new Intent(Detail_Monsters.this, Update_Monsters.class);
        intent.putExtra(Update_Monsters.EXTRA_MONSTER, monsters);
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
            finish();
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
                            Intent intent = new Intent(Detail_Monsters.this, Monsters.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // hapus data
                            DatabaseReference dbTalent =
                                    mDatabase.child("monsters").child(mons_id);

                            dbTalent.removeValue();

                            Toast.makeText(Detail_Monsters.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Detail_Monsters.this, Monsters.class);
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
