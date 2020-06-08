package com.example.flag_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Variable Declaration
    Button guessTheContryButton,guessTheFlagButton,guessTheHintsButton,advancedButton;

    //SQLLite DB Helper class
    DBHelper dbHelper;

    //Array for keeping the country flags locally.
    final static public int images [] = {R.drawable.north_korea_flag_small,R.drawable.saudi_arabia_flag_small,
            R.drawable.united_kingdom_flag_small,R.drawable.yemen_flag_small};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inserting Flags to the Database for the first time of app installation
        boolean isFirstRun=getSharedPreferences("PREFERENCES",MODE_PRIVATE).getBoolean("isFirstRun",true);
        if(isFirstRun){

            getSharedPreferences("PREFERENCES",MODE_PRIVATE).edit().putBoolean("isFirstRun",false).commit();
            dbHelper = new DBHelper(this);

            //Insert Flags in to Database.Give Country and Flag drawable here.
            dbHelper.insertFlag("North korea",R.drawable.north_korea_flag_small);
            dbHelper.insertFlag("Saudi Arabia",R.drawable.saudi_arabia_flag_small);
            dbHelper.insertFlag("United Kingdom",R.drawable.united_kingdom_flag_small);
            dbHelper.insertFlag("Yeman",R.drawable.yemen_flag_small);

        }else{
        }

        //Component Declaration
        guessTheContryButton = findViewById(R.id.guessTheContryButton);
        guessTheFlagButton = findViewById(R.id.guessTheFlagButton);
        guessTheHintsButton = findViewById(R.id.guessTheHintsButton);
        advancedButton = findViewById(R.id.advancedButton);

        //Start Activity for Guess the country
        guessTheContryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,guess_the_country.class);
                startActivity(i);
            }
        });

        //Start Activity for Guess the Flag
        guessTheFlagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,guess_the_flag.class);
                startActivity(i);
            }
        });

        //Start Activity for Guess the Advanced Level
        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,advance.class);
                startActivity(i);
            }
        });

        //Start Activity for Guess the Hint
        guessTheHintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("T","dsd");
                Intent i = new Intent(MainActivity.this,guess_the_hint.class);
                startActivity(i);
            }
        });


    }
}
