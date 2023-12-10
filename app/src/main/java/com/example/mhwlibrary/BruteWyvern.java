package com.example.mhwlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhwlibrary.MnsterDetail.BruteWyvern.Brachydios;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BruteWyvern extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brute_wyvern);


        Button ElderDragon = (Button) findViewById(R.id.ElderDragon);
        ElderDragon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(BruteWyvern.this, Monsters.class);
                startActivity(switchActivityIntent);

            }
        });


        Button FlyingWyvern = (Button) findViewById(R.id.FlyingWyvern);
        FlyingWyvern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(BruteWyvern.this, FlyingWyvern.class);
                startActivity(switchActivityIntent);

            }
        });

        TextView brachydios = findViewById(R.id.brachydios);

        brachydios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here
                Toast.makeText(getApplicationContext(), "Brachydios", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BruteWyvern.this, Brachydios.class);
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

        });

    }
}