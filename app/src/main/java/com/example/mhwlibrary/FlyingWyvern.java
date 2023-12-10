package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FlyingWyvern extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flying_wyvern);

        Button ED = (Button) findViewById(R.id.ED);
        ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(FlyingWyvern.this, Monsters.class);
                startActivity(switchActivityIntent);

            }
        });

        Button BW = (Button) findViewById(R.id.BW);
        BW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(FlyingWyvern.this, BruteWyvern.class);
                startActivity(switchActivityIntent);

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

        });

    }
}