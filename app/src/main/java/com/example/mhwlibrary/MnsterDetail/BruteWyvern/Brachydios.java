package com.example.mhwlibrary.MnsterDetail.BruteWyvern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mhwlibrary.BruteWyvern;
import com.example.mhwlibrary.MnsterDetail.ElderDragon.Fatalis;
import com.example.mhwlibrary.Monsters;
import com.example.mhwlibrary.R;

public class Brachydios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brachydios);

        ImageButton Back = (ImageButton) findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(Brachydios.this, BruteWyvern.class);
                startActivity(switchActivityIntent);

            }
        });
    }
}