package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Weapon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        Button btnInsectGlaive = (Button) findViewById(R.id.btnInsectGlaive);
        btnInsectGlaive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Weapon.this, InsectGlaive.class);
                startActivity(switchActivityIntent);

            }
        });

        Button btnChargeBlade = (Button) findViewById(R.id.btnChargeBlade);
        btnChargeBlade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Weapon.this, ChargeBlade.class);
                startActivity(switchActivityIntent);

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_weapons);

        bottomNavigationView.setOnItemSelectedListener(item ->{
            if(item.getItemId() == R.id.bottom_weapons)
            {
                return true;
            }
            else if(item.getItemId() == R.id.bottom_monsters)
            {
                startActivity(new Intent(getApplicationContext(), Monsters.class));
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
//            return false;
        });
    }
}