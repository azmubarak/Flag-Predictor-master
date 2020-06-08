package com.example.flag_1;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class guess_the_country extends AppCompatActivity {


    ImageView imageView;
    Spinner flagList;
    DBHelper dbHelper;
    Button nextButton;
    Button submitButton;
    TextView resultText,answerText;

    int selectedFlagImageId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_country);

        dbHelper = new DBHelper(this);


        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButtom);
        imageView = findViewById(R.id.imageView);
        flagList = findViewById(R.id.flagList);
        resultText = findViewById(R.id.resultText);
        answerText = findViewById(R.id.answerText);


        loadcountriesToSpinner();


        imageView.setImageResource(MainActivity.images[0]);
        selectedFlagImageId = MainActivity.images[0];
        Cursor data = dbHelper.getData("select * from flag where imageId = " + R.drawable.north_korea_flag_small);
        data.moveToFirst();
        Log.i("TAG", data.getString(data.getColumnIndex("country")));


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitButton.getText().equals("SUBMIT")){
                    String selectedItem = flagList.getSelectedItem().toString();
                    Log.i("TAG",selectedItem);
                    Log.i("TAG", String.valueOf(selectedFlagImageId));
                    Cursor selectedFlagData = dbHelper.getData("select * from flag where imageId = " + selectedFlagImageId);
                    // String selectedFlagCountry = selectedFlagData.getString(selectedFlagData.getColumnIndex("country"));
                    selectedFlagData.moveToFirst();

                    if(selectedItem.equals(selectedFlagData.getString(selectedFlagData.getColumnIndex("country")))){
                        resultText.setTextColor(Color.GREEN);
                        resultText.setText("Correct !!");
                        answerText.setText("");
                    }else{
                        resultText.setTextColor(Color.RED);
                        resultText.setText("Wrong !!");
                        answerText.setText("Correct Answer: " + selectedFlagData.getString(selectedFlagData.getColumnIndex("country")));
                    }
                    submitButton.setText("NEXT");

                }else{

                    loadRandom();
                    submitButton.setText("SUBMIT");
                }

            }
        });
    }


    public void loadcountriesToSpinner(){

        Cursor cursor = dbHelper.getData("select * from flag");


        ArrayList<String> countries = new ArrayList<String>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            countries.add(cursor.getString(cursor.getColumnIndex("country"))); //add the item
            cursor.moveToNext();
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countries);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        flagList.setAdapter(dataAdapter);

    }


    public void loadRandom(){
        Random r = new Random();
        int i = r.nextInt(4);
        Log.i("TAG","Random Number" + i);
        imageView.setImageResource(MainActivity.images[i]);
        selectedFlagImageId = MainActivity.images[i];
    }
}
