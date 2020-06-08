package com.example.flag_1;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class guess_the_flag extends AppCompatActivity {

    DBHelper dbHelper;
    Button nextButton,submitButton;
    TextView resultText,answerText,guessThisCountry2,statusText;
    RadioGroup radioGroup;
    RadioButton flag_1_radio,flag_2_radio,flag_3_radio;

    Hashtable<Integer,String> hashtable = new Hashtable<>();
    Hashtable<Integer,Integer> tempHashTable = new Hashtable<>();

    String flag1Data,flag2Data,flag3Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);

        dbHelper = new DBHelper(this);


        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButtom);

        resultText = findViewById(R.id.resultText);
        answerText = findViewById(R.id.answerText);
        radioGroup = findViewById(R.id.radioGrouo);
        flag_1_radio = findViewById(R.id.flag_1_radio);
        flag_2_radio = findViewById(R.id.flag_2_radio);
        flag_3_radio = findViewById(R.id.flag_3_radio);
        guessThisCountry2 = findViewById(R.id.guessThisCountry2);
        statusText = findViewById(R.id.statusText);

        flagFetcher();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagFetcher();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                statusText.setText("You have Selected "+ tempHashTable.get(checkedId) +" flag");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!submitButton.getText().equals("NEXT")){
                    String selectedCountry = hashtable.get(radioGroup.getCheckedRadioButtonId());
                    String  givenCountry = (String) guessThisCountry2.getText();

                    if(selectedCountry.equals(givenCountry)){
                        resultText.setTextColor(Color.GREEN);
                        resultText.setText("Correct !!");
                        submitButton.setText("NEXT");
                    }else{
                        resultText.setTextColor(Color.RED);
                        resultText.setText("Wrong !!" + "Correct Answer : "+givenCountry);
                        submitButton.setText("NEXT");
                    }
                }else{
                    flagFetcher();
                    resultText.setText("");
                    submitButton.setText("SUBMIT");

                }

            }
        });
    }


    public void flagFetcher(){
        Random r = new Random();
        int i=0,j=0,k=0;

        while((i==j) || (j==k) || (k==i)){
            k = r.nextInt(MainActivity.images.length);
            i = r.nextInt(MainActivity.images.length);
            j = r.nextInt(MainActivity.images.length);
        }

        Log.i("TAG",i + ": "+ j +":" +k);


        flag_1_radio.setBackgroundResource(MainActivity.images[i]);

        Cursor selectedFlagData1 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[i]);
        selectedFlagData1.moveToFirst();
        flag1Data = selectedFlagData1.getString(selectedFlagData1.getColumnIndex("country"));

        Cursor selectedFlagData2 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[j]);
        selectedFlagData2.moveToFirst();
        flag_2_radio.setBackgroundResource(MainActivity.images[j]);
        flag2Data = selectedFlagData2.getString(selectedFlagData2.getColumnIndex("country"));

        Cursor selectedFlagData3 = dbHelper.getData("select * from flag where imageId = " + MainActivity.images[k]);
        selectedFlagData3.moveToFirst();
        flag_3_radio.setBackgroundResource(MainActivity.images[k]);
        flag3Data = selectedFlagData3.getString(selectedFlagData3.getColumnIndex("country"));


        //Mapping flag radio buttons to country
        hashtable.put(flag_1_radio.getId(),flag1Data);
        hashtable.put(flag_2_radio.getId(),flag2Data);
        hashtable.put(flag_3_radio.getId(),flag3Data);

        //Mapping Flag radio buttons to typical counting numbers
        tempHashTable.put(flag_1_radio.getId(),1);
        tempHashTable.put(flag_2_radio.getId(),2);
        tempHashTable.put(flag_3_radio.getId(),3);

        //Storing country names
        String [] country_3 = {flag1Data,flag2Data,flag3Data};

        //Generating Random country
        int giveSelectedFlag = r.nextInt(3);
        guessThisCountry2.setText(country_3[giveSelectedFlag]);
    }
}
