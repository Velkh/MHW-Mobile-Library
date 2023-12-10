package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mhwlibrary.MnsterDetail.BruteWyvern.Brachydios;
import com.example.mhwlibrary.MnsterDetail.ElderDragon.Fatalis;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Monsters extends AppCompatActivity {

    int itemIDBottomWeapon = R.id.bottom_weapons;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monsters);

        Button btnBruteWyvern = (Button) findViewById(R.id.btnBruteWyvern);
        btnBruteWyvern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Monsters.this, BruteWyvern.class);
                startActivity(switchActivityIntent);

            }
        });

        Button btnFlyingWyvern = (Button) findViewById(R.id.btnFlyingWyvern);
        btnFlyingWyvern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Monsters.this, FlyingWyvern.class);
                startActivity(switchActivityIntent);

            }
        });

        TextView fatalis = findViewById(R.id.fatalis);

        fatalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here
                Toast.makeText(getApplicationContext(), "Fatalis", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Monsters.this, Fatalis.class);
                startActivity(intent);

                // You can perform any other actions you want here
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_monsters);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            if(item.getItemId() == R.id.bottom_monsters)
            {
                return true;
            }
            else if(item.getItemId() == R.id.bottom_weapons)
            {
                startActivity(new Intent(getApplicationContext(), Weapon.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            else if(item.getItemId() == R.id.bottom_maps)
            {
                startActivity(new Intent(getApplicationContext(),Location.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            else
            {
                return false;
            }
//            switch (item.getItemId()){
//                case itemIDBottomMonster:
//                    return true;
//                case R.id.bottom_weapons:
//
////                case R.id.bottom_maps:
////                    startActivity(new Intent(getApplicationContext(),Weapon.class));
////                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
////                    finish();
////                    return true;
//            }
//        return false;
        });
    }
}