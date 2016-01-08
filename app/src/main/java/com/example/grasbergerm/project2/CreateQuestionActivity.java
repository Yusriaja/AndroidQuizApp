package com.example.grasbergerm.project2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CreateQuestionActivity extends ActionBarActivity {
    boolean result;
    MyDbHelper mDbHelper;
    SQLiteDatabase mDb;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        final String currentQuiz = getIntent().getExtras().getString("currentQuiz");
        String type = getIntent().getExtras().getString("type");
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(ll, llParam);
        ViewGroup.LayoutParams lpView = new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setText("Creating a " + "question for " + currentQuiz);
        tv.setLayoutParams(lpView);
        tv.setTextSize((float) 25.0);
        ll.addView(tv);
        et = new EditText(this);
        et.setHint("What is the question?");
        et.setLayoutParams(lpView);
        et.setTextSize((float) 20.0);
        ll.addView(et);
        if(type.equals("tf")){
            result = true;
            ToggleButton toggle = new ToggleButton(this);
            toggle.setTextOn("TRUE" ); toggle.setTextOff("FALSE");
            toggle.setChecked(true);
            toggle.setLayoutParams(lpView);
            AdapterView.OnClickListener ocl1;
            ocl1 = new AdapterView.OnClickListener() {
                public void onClick(View v) {
                    result = ((ToggleButton) v).isChecked();
                }
            };
            toggle.setOnClickListener(ocl1);
            ll.addView(toggle);
            Button button = new Button(this);
            button.setText("Submit Question");
            button.setLayoutParams(lpView);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(et.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Please enter a question!",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        mDbHelper = new MyDbHelper(CreateQuestionActivity.this);
                        mDb = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(mDbHelper.QUEST_COL_NAME, currentQuiz);
                        values.put(mDbHelper.QUEST_COL_QUEST, et.getText().toString());
                        values.put(mDbHelper.QUEST_COL_TYPE, "tf");
                        values.put(mDbHelper.QUEST_COL_ANS, result);
                        mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
                        finish();
                    }
                }
            });
            ll.addView(button);
        }
        else if(type.equals("fillIn")){
            final EditText et2 = new EditText(this);
            et2.setHint("What is the answer?");
            ll.addView(et2);
            Button button = new Button(this);
            button.setText("Submit Question");
            button.setLayoutParams(lpView);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(et2.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Please enter an answer!",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(et.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Please enter a question!",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        mDbHelper = new MyDbHelper(CreateQuestionActivity.this);
                        mDb = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(mDbHelper.QUEST_COL_NAME, currentQuiz);
                        values.put(mDbHelper.QUEST_COL_QUEST, et.getText().toString());
                        values.put(mDbHelper.QUEST_COL_TYPE, "fillIn");
                        values.put(mDbHelper.QUEST_COL_ANS, et2.getText().toString());
                        mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
                        finish();
                    }
                }
            });
            ll.addView(button);
        }
        else if(type.equals("mul")){
            final EditText et2 = new EditText(this);
            et2.setHint("What is the answer?");
            ll.addView(et2);
            final EditText choice1 = new EditText(this);
            choice1.setHint("Choice 1");
            ll.addView(choice1);
            final EditText choice2 = new EditText(this);
            choice2.setHint("Choice 2");
            ll.addView(choice2);
            final EditText choice3 = new EditText(this);
            choice3.setHint("Choice 3");
            ll.addView(choice3);
            final EditText choice4 = new EditText(this);
            choice4.setHint("Choice 4");
            ll.addView(choice4);
            Button button = new Button(this);
            button.setText("Submit Question");
            button.setLayoutParams(lpView);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (et2.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter an answer!",
                                Toast.LENGTH_LONG).show();
                    } else if (et.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter a question!",
                                Toast.LENGTH_LONG).show();
                    } else if(choice1.getText().toString().equals("") || choice2.getText().toString().equals("") || choice3.getText().toString().equals("") || choice4.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "Please fill out all the choices",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        mDbHelper = new MyDbHelper(CreateQuestionActivity.this);
                        mDb = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(mDbHelper.QUEST_COL_NAME, currentQuiz);
                        values.put(mDbHelper.QUEST_COL_QUEST, et.getText().toString());
                        values.put(mDbHelper.QUEST_COL_TYPE, "mul");
                        values.put(mDbHelper.QUEST_COL_ANSMUL, et2.getText().toString());
                        values.put(mDbHelper.QUEST_COL_ANS, choice1.getText().toString());
                        values.put(mDbHelper.QUEST_COL_ANS1, choice2.getText().toString());
                        values.put(mDbHelper.QUEST_COL_ANS2, choice3.getText().toString());
                        values.put(mDbHelper.QUEST_COL_ANS3, choice4.getText().toString());
                        mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
                        finish();
                    }
                }
            });
            ll.addView(button);
        }
    }

}
