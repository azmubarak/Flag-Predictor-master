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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class guess_the_hint extends AppCompatActivity {

    //Declare Variables
    private DBHelper dbHelper;
    private String flag_name;
    private ImageView flag;
    private EditText guessL;
    private Button submit;
    private TextView iscorrct,correctAnswer;

    //Variable for keeping selected Image Id
    private int selectedFlagImageId;

    //Variable for keeping incorrectGuessCount
    private int inCorrectGuesscount=1;
    private int correctGuessCount=0;

    String guessFlag="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_hint);

        //DB Helper Initialization
        dbHelper = new DBHelper(this);

        //Component Initialization
        flag = findViewById(R.id.flag);
        guessL = findViewById(R.id.editText2);
        iscorrct = findViewById(R.id.iscorrect);
        correctAnswer = findViewById(R.id.correctAnswer);

        //Method execution for loading flags
        loadFlag();
        Log.i("TAGG",flag_name);
        //Method execution for generating  letters
        generateLetters();


        submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(submit.getText().equals("NEXT")){

                    Log.i("TAGG","went to else");
                    iscorrct.setText("");
                    correctAnswer.setText("");
                    inCorrectGuesscount=1;
                    submit.setText("SUBMIT");

                    loadFlag();
                    generateLetters();
                }else{

                    if (inCorrectGuesscount != 3) {

                        String inputLetter = String.valueOf(guessL.getText()).toLowerCase();


                        String string = flag_name.toLowerCase();
                        List<Character> chars = new ArrayList<>();
                        for (char ch : string.toCharArray()) {
                            chars.add(ch);
                        }
                        System.out.println(string.charAt(2));
                        System.out.println(inputLetter);
                        boolean iscorrectletter = false;
                        for (int i = 0; i < chars.size(); i++) {
                            if (inputLetter.toString().equals(Character.toString(string.charAt(i)))) {
                                iscorrectletter = true;
                                iscorrct.setText("");
                                EditText letter = findViewById(i);
                                correctGuessCount+=1;
                                letter.setText(Character.toString(string.charAt(i)));
                            }
                        }
                        if (!iscorrectletter) {
                            inCorrectGuesscount += 1;
                            iscorrct.setTextColor(Color.RED);
                            iscorrct.setText("Wrong Guess Try Again !!");
                        }
                        String current_flag_name="";
                        for(int k=0; k<flag_name.length();k++){
                            EditText let = findViewById(k);
                            current_flag_name+=let.getText();

                        }
                        if(current_flag_name.equals(flag_name.toLowerCase())){
                            iscorrct.setText("Correct !!");
                            submit.setText("NEXT");
                        }
                        Log.i("TAGG",current_flag_name+flag_name.toLowerCase());


                    } else {
                        iscorrct.setTextColor(Color.RED);
                        iscorrct.setText("Wrong !!");
                        correctAnswer.setText("Correct Answer is : " + flag_name);
                        iscorrct.setText("");
                        submit.setText("NEXT");
                    }

                }

            }
        });

    }


    //Generating empty spaces for randomly selected flag name
    public void generateLetters(){

        //Getting the flag name length
        int flag_name_len = flag_name.length();


        //Getting the Linear layout as an object
        LinearLayout myxml = findViewById(R.id.guess);

        //Clear Child in the Linear Layout
        if((myxml).getChildCount() > 0)
            (myxml).removeAllViews();

        for(int i=0; i<flag_name_len;i++){
            EditText letter = new EditText(this);
            letter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            letter.setText("");
            letter.setId(i);
            letter.setTextSize(16);
            letter.setEnabled(false);
            myxml.addView(letter);
        }
    }

    //Loading flags randomly and put them into image views
    public void loadFlag(){

        //Random integer generates
        Random r = new Random();
        int i = r.nextInt(MainActivity.images.length);

        //Set Flag to image view
        flag.setImageResource(MainActivity.images[i]);

        //Assign the imageId of randomly selected Image to variable
        selectedFlagImageId = MainActivity.images[i];

        //Execute Raw query to get image details from Database
        Cursor selectedFlagData = dbHelper.getData("select * from flag where imageId = " + selectedFlagImageId);
        selectedFlagData.moveToFirst();

        //Assign Flag name to variable
        flag_name = selectedFlagData.getString(selectedFlagData.getColumnIndex("country"));
    }
}
