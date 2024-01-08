package com.example.tk_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button beef, fish, pig, sweets, chicken, others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beef = findViewById(R.id.beef);
        fish = findViewById(R.id.fish);
        pig = findViewById(R.id.pig);
        sweets = findViewById(R.id.sweets);
        chicken = findViewById(R.id.chicken);
        others = findViewById(R.id.others);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, beef_recipe.class);
                startActivity(intent);
                finish();
            }

        };
        beef.setOnClickListener(onClickListener);

        pig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, pig_recipe.class);
                startActivity(intent);
                finish();
            }
        });

    }
}