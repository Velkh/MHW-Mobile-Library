package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mhwlibrary.Adapter.AdapterMonsters;
import com.example.mhwlibrary.Model.Model_Monsters;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Monsters extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    FirebaseAuth auth;
    Button loButton;
    FirebaseUser user;

    private ListView listView;
    private Button btnAdd;
    private AdapterMonsters adapter;
    private ArrayList<Model_Monsters> monstersList;
    private DatabaseReference dbMonsters;

    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monsters);

        monstersList = new ArrayList<>();

        edtSearch = findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the talent list based on the search query
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not used
            }
        });

        listView = findViewById(R.id.mons_list);
        btnAdd = findViewById(R.id.btnAddMonster);
        btnAdd.setOnClickListener(this);

        adapter = new AdapterMonsters(this, monstersList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Model_Monsters selectedMonster;

            if (adapter instanceof AdapterMonsters) {
                selectedMonster = (Model_Monsters) ((AdapterMonsters) adapter).getItem(i);
            } else {
                selectedMonster = monstersList.get(i);
            }

            Intent intent = new Intent(Monsters.this, Detail_Monsters.class);
            intent.putExtra(Update_Monsters.EXTRA_MONSTER, selectedMonster);
            startActivity(intent);
        });

        dbMonsters = FirebaseDatabase.getInstance().getReference("monsters");

        auth = FirebaseAuth.getInstance();
        loButton = findViewById(R.id.btnLogout);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        loButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Bottom Navbar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_monsters);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_monsters) {
                return true;
            } else if (item.getItemId() == R.id.bottom_quest) {
                startActivity(new Intent(getApplicationContext(), Quest.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_maps) {
                startActivity(new Intent(getApplicationContext(), Location.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMonsters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                monstersList.clear();

                for (DataSnapshot talentSnapshot : dataSnapshot.getChildren()) {
                    Model_Monsters monsters = talentSnapshot.getValue(Model_Monsters.class);
                    monstersList.add(monsters);
                }

                adapter.setMonstersList(monstersList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Monsters.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent =  new Intent(getApplicationContext(),Create_Monsters.class);
        startActivity(intent);
        finish();// Handle button click or any other view click events
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Monsters.this, Detail_Monsters.class);
        intent.putExtra("mons_id", monstersList.get(i).getMons_id());
        startActivity(intent);
    }
}
