package com.example.grasbergerm.project2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class TakeQuizActivity extends ActionBarActivity {
    CustomCursorAdapter dbAdapter;
    MyDbHelper mDbHelper;
    SQLiteDatabase mDb;
    String currentQuiz;
    MatrixCursor mMatrixCursor;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        score = 0;
        currentQuiz = getIntent().getExtras().getString("currentQuiz");
        TextView textViewQuiz = (TextView) findViewById(R.id.textViewQuiz);
        TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewQuiz.setText("Quiz for " + currentQuiz);
        mDbHelper = new MyDbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        String[] columns = {"_id", mDbHelper.QUEST_COL_QUEST, mDbHelper.QUEST_COL_ANS };
        String selection =  mDbHelper.QUEST_COL_NAME + "=?";
        Cursor c = mDb.query(mDbHelper.QUEST_TABLE_NAME, columns, selection, new String[] {currentQuiz}, null, null,
                null);
        dbAdapter = new CustomCursorAdapter(getBaseContext(),
                R.layout.list_layout,
                c,
                columns,
                new int[]{});
        ListView listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(dbAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                String name = Long.toString(id);
                Intent intent = new Intent(getApplicationContext(), AnswerQuestionActivity.class);
                intent.putExtra("QuestionID", name);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            }
        });
        textViewScore.setText("Score: " + score + " / " + c.getCount());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                int position = data.getIntExtra("position",-1);
                ListView listView = (ListView) findViewById(R.id.mylist);
                View v = listView.getChildAt(position);
                TextView textView = (TextView) v.findViewById(R.id.Answered);
                textView.setText(result);
                if(!result.equals("Not Answered")){
                    textView.setClickable(true);
                    v.setClickable(true);
                }
                if(result.equals("Correct")){
                    score++;
                    TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
                    mDbHelper = new MyDbHelper(this);
                    mDb = mDbHelper.getWritableDatabase();
                    String[] columns = {"_id", mDbHelper.QUEST_COL_QUEST, mDbHelper.QUEST_COL_ANS };
                    String selection =  mDbHelper.QUEST_COL_NAME + "=?";
                    Cursor c = mDb.query(mDbHelper.QUEST_TABLE_NAME, columns, selection, new String[] {currentQuiz}, null, null,
                            null);
                    textViewScore.setText("Score: " + score + " / " + c.getCount());
                }
                //listView.getItemAtPosition();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}
