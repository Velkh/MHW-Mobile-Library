package com.example.mhwlibrary;

import androidx.annotation.NonNull;
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


import com.example.mhwlibrary.Adapter.AdapterQuests;
import com.example.mhwlibrary.Model.Model_Quests;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quest extends  AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user;
    Button loButton;

    private ListView listView;
    private Button btnAdd;
    private AdapterQuests adapter;
    private ArrayList<Model_Quests> questsList;
    private DatabaseReference dbQuests;

    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        questsList = new ArrayList<>();

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

        listView = findViewById(R.id.quest_list);
        btnAdd = findViewById(R.id.btnAddQuest);
        btnAdd.setOnClickListener(this);

        adapter = new AdapterQuests(this, questsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Model_Quests selectedQuest;

            if (adapter instanceof AdapterQuests) {
                selectedQuest = (Model_Quests) ((AdapterQuests) adapter).getItem(i);
            } else {
                selectedQuest = questsList.get(i);
            }

            Intent intent = new Intent(Quest.this, Detail_Quests.class);
            intent.putExtra(Update_Quests.EXTRA_QUESTS, selectedQuest);
            startActivity(intent);
        });

        dbQuests = FirebaseDatabase.getInstance().getReference("quests");

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
        bottomNavigationView.setSelectedItemId(R.id.bottom_quest);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_quest) {
                return true;
            } else if (item.getItemId() == R.id.bottom_monsters) {
                startActivity(new Intent(getApplicationContext(), Monsters.class));
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

        dbQuests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questsList.clear();

                for (DataSnapshot talentSnapshot : dataSnapshot.getChildren()) {
                    Model_Quests quests = talentSnapshot.getValue(Model_Quests.class);
                    questsList.add(quests);
                }

                adapter.setQuestsList(questsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Quest.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent =  new Intent(getApplicationContext(),Create_Quests.class);
        startActivity(intent);
        finish();// Handle button click or any other view click events
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Quest.this, Detail_Quests.class);
        intent.putExtra("quest_id", questsList.get(i).getQuest_id());
        startActivity(intent);
    }
}