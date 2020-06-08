package com.example.flag_1;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Random;

public class advance extends AppCompatActivity {

    //Variable declaration
    DBHelper dbHelper;
    Button nextButton, submitButton;
    TextView flag_1_result, flag_2_result, flag_3_result;
    EditText flag_1_edit, flag_2_edit, flag_3_edit;
    ImageView flag_1, flag_2, flag_3;

    //Mapping Flag ImageIds to Country names
    Hashtable<Integer, String> hashtable = new Hashtable<>();

    //Variables for keeping flag details
    String flag1Data, flag2Data, flag3Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        //Variable Initialization
        dbHelper = new DBHelper(this);
        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButtom);

        flag_1_result = findViewById(R.id.flag_1_result);
        flag_2_result = findViewById(R.id.flag_2_result);
        flag_3_result = findViewById(R.id.flag_3_result);

        flag_1_edit = findViewById(R.id.flag_1_edit);
        flag_2_edit = findViewById(R.id.flag_2_edit);
        flag_3_edit = findViewById(R.id.flag_3_edit);

        flag_1 = findViewById(R.id.flag_1);
        flag_2 = findViewById(R.id.flag_2);
        flag_3 = findViewById(R.id.flag_3);

        //Method call for Flag Fetching
        flagFetcher();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(submitButton.getText().equals("SUBMIT")) {
                    //Keeping edit texts's values to variables
                    String flag1text = String.valueOf(flag_1_edit.getText());
                    String flag2text = String.valueOf(flag_2_edit.getText());
                    String flag3text = String.valueOf(flag_3_edit.getText());

                    //comparing flag1 edit texts value to flag1 correct value in hashtable
                    if (flag1text.equals(hashtable.get(flag_1.getId()))) {
                        flag_1_edit.setTextColor(Color.GREEN);
                    } else {
                        flag_1_edit.setTextColor(Color.RED);
                        flag_1_result.setText("Correct Answer : " + hashtable.get(flag_1.getId()));
                    }

                    //comparing flag2 edit texts value to flag2 correct value in hashtable
                    if (flag2text.equals(hashtable.get(flag_2.getId()))) {
                        flag_2_edit.setTextColor(Color.GREEN);
                    } else {
                        flag_2_edit.setTextColor(Color.RED);
                        flag_2_result.setText("Correct Answer : " + hashtable.get(flag_2.getId()));
                    }

                    //comparing flag3 edit texts value to flag3 correct value in hashtable
                    if (flag3text.equals(hashtable.get(flag_3.getId()))) {
                        flag_3_edit.setTextColor(Color.GREEN);
                    } else {
                        flag_3_edit.setTextColor(Color.RED);
                        flag_3_result.setText("Correct Answer : " + hashtable.get(flag_3.getId()));
                    }

                    submitButton.setText("NEXT");

                }else {

                    //Random Flag Fetching
                    flagFetcher();

                    submitButton.setText("SUBMIT");
                }


            }
        });


    }

    //Random Flag Fetching
    public void flagFetcher() {

        //Random number generation
        Random r = new Random();
        int i = 0, j = 0, k = 0;

        //Generation of 3 random numbers
        while ((i == j) || (j == k) || (k == i)) {
            k = r.nextInt(MainActivity.images.length);
            i = r.nextInt(MainActivity.images.length);
            j = r.nextInt(MainActivity.images.length);
        }

        Log.i("TAG", i + ": " + j + ":" + k);

        //Getting Flag1 details from Database
        Cursor selectedFlagData1 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[i]);
        selectedFlagData1.moveToFirst();
        flag1Data = selectedFlagData1.getString(selectedFlagData1.getColumnIndex("country"));
        //set flag1 to imageView
        flag_1.setImageResource(MainActivity.images[i]);

        //Getting Flag2 details from Database
        Cursor selectedFlagData2 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[j]);
        selectedFlagData2.moveToFirst();
        //set flag2 to imageView
        flag_2.setImageResource(MainActivity.images[j]);
        flag2Data = selectedFlagData2.getString(selectedFlagData2.getColumnIndex("country"));

        //Getting Flag3 details from Database
        Cursor selectedFlagData3 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[k]);
        selectedFlagData3.moveToFirst();
        //set flag3 to imageView
        flag_3.setImageResource(MainActivity.images[k]);
        flag3Data = selectedFlagData3.getString(selectedFlagData3.getColumnIndex("country"));


        //Putting Flag Details to HashTable
        hashtable.put(flag_1.getId(), flag1Data);
        hashtable.put(flag_2.getId(), flag2Data);
        hashtable.put(flag_3.getId(), flag3Data);

    }
}
