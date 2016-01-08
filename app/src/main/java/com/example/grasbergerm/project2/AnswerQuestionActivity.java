package com.example.grasbergerm.project2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AnswerQuestionActivity extends ActionBarActivity {
    MyDbHelper mDbHelper;
    SQLiteDatabase mDb;
    String result;
    Cursor c;
    ViewGroup.LayoutParams lpView;
    LinearLayout ll;
    boolean resultQ;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        result = "Not Answered";
        mDbHelper = new MyDbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        String[] columns = {"_id", mDbHelper.QUEST_COL_TYPE, mDbHelper.QUEST_COL_QUEST, mDbHelper.QUEST_COL_ANS, mDbHelper.QUEST_COL_ANS1, mDbHelper.QUEST_COL_ANS2, mDbHelper.QUEST_COL_ANS3, mDbHelper.QUEST_COL_ANSMUL };
        String selection =   "_id=?";
        c = mDb.query(mDbHelper.QUEST_TABLE_NAME, columns, selection, new String[] {getIntent().getExtras().getString("QuestionID")}, null, null,
                null);
        if (c.moveToFirst()) {
            if(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_TYPE)).equals("fillIn")) {
                lpView = new
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(this);
                textView.setTextSize((float) 25.0);
                textView.setLayoutParams(lpView);
                textView.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_QUEST)));
                ll = new LinearLayout(this);
                ll.addView(textView);
                ll.setOrientation(LinearLayout.VERTICAL);
                setContentView(ll);
                EditText et = new EditText(this);
                et.setHint("Enter your answer");
                et.setLayoutParams(lpView);
                et.setId(0);
                ll.addView(et);
                button = new Button(this);
                button.setText("Check Answer");
                button.setLayoutParams(lpView);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        EditText et = (EditText) ll.findViewById(0);
                        if (et.getText().toString().equals(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS)))) {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextColor(Color.BLACK);
                            textView2.setTextSize((float) 25.0);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Correct!");
                            result = "Correct";
                            ll.addView(textView2);
                            button.setClickable(false);
                        }
                        else if(et.getText().toString().equals("")){

                        }
                        else {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextSize((float) 25.0);
                            textView2.setTextColor(Color.BLACK);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Incorrect! The answer is " + c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS)));
                            result = "Incorrect";
                            ll.addView(textView2);
                            button.setClickable(false);
                        }
                    }
                });
                ll.addView(button);
            }
            else if(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_TYPE)).equals("tf")){
                lpView = new
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(this);
                textView.setTextSize((float) 25.0);
                textView.setLayoutParams(lpView);
                textView.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_QUEST)));
                ll = new LinearLayout(this);
                ll.addView(textView);
                ll.setOrientation(LinearLayout.VERTICAL);
                setContentView(ll);
                resultQ = true;
                ToggleButton toggle = new ToggleButton(this);
                toggle.setTextOn("TRUE"); toggle.setTextOff("FALSE");
                toggle.setChecked(true);
                toggle.setLayoutParams(lpView);
                AdapterView.OnClickListener ocl1;
                ocl1 = new AdapterView.OnClickListener() {
                    public void onClick(View v) {
                        resultQ = ((ToggleButton) v).isChecked();
                    }
                };
                toggle.setOnClickListener(ocl1);
                ll.addView(toggle);
                button = new Button(this);
                button.setText("Check Answer");
                button.setLayoutParams(lpView);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        boolean ans;
                        if (c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS)).equals("1")) {
                            ans = true;
                        } else ans = false;
                        if (resultQ == ans) {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextColor(Color.BLACK);
                            textView2.setTextSize((float) 25.0);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Correct!");
                            result = "Correct";
                            ll.addView(textView2);
                        } else {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextSize((float) 25.0);
                            textView2.setTextColor(Color.BLACK);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Incorrect!");
                            result = "Incorrect";
                            ll.addView(textView2);
                        }
                        button.setClickable(false);
                    }
                });
                ll.addView(button);
            }
            else if(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_TYPE)).equals("mul")){
                lpView = new
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(this);
                textView.setTextSize((float) 25.0);
                textView.setLayoutParams(lpView);
                textView.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_QUEST)));
                ll = new LinearLayout(this);
                ll.addView(textView);
                ll.setOrientation(LinearLayout.VERTICAL);
                setContentView(ll);
                final RadioGroup radioGroup = new RadioGroup(this);
                RadioButton ans1 = new RadioButton(this);
                ans1.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS)));
                radioGroup.addView(ans1);
                RadioButton ans2 = new RadioButton(this);
                ans2.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS1)));
                radioGroup.addView(ans2);
                RadioButton ans3 = new RadioButton(this);
                ans3.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS2)));
                radioGroup.addView(ans3);
                RadioButton ans4 = new RadioButton(this);
                ans4.setText(c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANS3)));
                radioGroup.addView(ans4);
                ll.addView(radioGroup);
                button = new Button(this);
                button.setText("Check Answer");
                button.setLayoutParams(lpView);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        boolean ans;
                        RadioButton rdBut = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        if (rdBut.equals(null)) {
                            Toast.makeText(getApplicationContext(), "Please select an answer!",
                                    Toast.LENGTH_LONG).show();
                        } else if (c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANSMUL)).equals(rdBut.getText().toString())) {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextColor(Color.BLACK);
                            textView2.setTextSize((float) 25.0);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Correct!");
                            result = "Correct";
                            ll.addView(textView2);
                            button.setClickable(false);
                        } else {
                            TextView textView2 = new TextView(getApplicationContext());
                            textView2.setTextSize((float) 25.0);
                            textView2.setTextColor(Color.BLACK);
                            textView2.setLayoutParams(lpView);
                            textView2.setText("Incorrect! The answer is " + c.getString(c.getColumnIndex(mDbHelper.QUEST_COL_ANSMUL)));
                            result = "Incorrect";
                            ll.addView(textView2);
                            button.setClickable(false);
                        }
                    }
                });
                ll.addView(button);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        returnIntent.putExtra("position",getIntent().getExtras().getInt("position"));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
