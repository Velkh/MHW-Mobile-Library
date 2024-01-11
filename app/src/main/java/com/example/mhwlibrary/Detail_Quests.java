package com.example.mhwlibrary;

import static com.example.mhwlibrary.Update_Quests.EXTRA_QUESTS;

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

import com.example.mhwlibrary.Model.Model_Quests;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
public class Detail_Quests extends AppCompatActivity {

    private TextView txtqName, txtqType, txtqImage, txtqDesc;
    private ImageView imgDetailImage;
    private Model_Quests quests;
    private String quests_id;
    private Button btnEdit;
    private ImageButton delBtn;
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_quest);

        ImageButton BackQuest = (ImageButton) findViewById(R.id.BackQuest);

        BackQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivityIntent = new Intent(Detail_Quests.this, Quest.class);
                startActivity(switchActivityIntent);
                }
            }
        );

        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtqName = findViewById(R.id.txtDetailQuestName);
        txtqType = findViewById(R.id.txtDetailQuestType);
        imgDetailImage = findViewById(R.id.detailQuestimages);
        txtqDesc = findViewById(R.id.detailQuestDesc);
        btnEdit = findViewById(R.id.btn_edit);
        delBtn = findViewById(R.id.DelQuest);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(ALERT_DIALOG_DELETE);
            }
        });

        quests = getIntent().getParcelableExtra(EXTRA_QUESTS);

        if (quests != null) {
            quests_id = quests.getQuest_id();
        } else {
            quests = new Model_Quests();
        }

        if (quests != null) {
            txtqName.setText(quests.getQuest_name());
            txtqType.setText(quests.getQuest_type());
            loadImageFromUrl(quests.getQuest_image());
            txtqDesc.setText(quests.getQuest_desc());
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
        Intent intent = new Intent(Detail_Quests.this, Update_Quests.class);
        intent.putExtra(Update_Quests.EXTRA_QUESTS, quests);
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
                            Intent intent = new Intent(Detail_Quests.this, Quest.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // hapus data
                            DatabaseReference dbTalent =
                                    mDatabase.child("quests").child(quests_id);

                            dbTalent.removeValue();

                            Toast.makeText(Detail_Quests.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Detail_Quests.this, Quest.class);
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

