package com.example.grasbergerm.project2;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ActionBarActivity implements OnItemSelectedListener{
    MyDbHelper mDbHelper;
    SQLiteDatabase mDb;
    Cursor dbCursor;
    SimpleCursorAdapter dbAdapter;
    String currentQuiz;
    int i;

    String[] COLUMNS = new String[] {MyDbHelper.QUIZ_COL_NAME};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = 0;
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        mDbHelper = new MyDbHelper(this);
        mDb = mDbHelper.getWritableDatabase();
        String[] allColumns = new String[] {"_id", MyDbHelper.QUIZ_COL_NAME};
        dbCursor = mDb.query(MyDbHelper.QUIZ_TABLE_NAME, allColumns, null, null, null, null, null);
        if(dbCursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(mDbHelper.QUIZ_COL_NAME, "European Cars");
            mDb.insert(mDbHelper.QUIZ_TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(mDbHelper.QUEST_COL_NAME, "European Cars");
            values.put(mDbHelper.QUEST_COL_QUEST, "In what year was Porsche founded?");
            values.put(mDbHelper.QUEST_COL_TYPE, "fillIn");
            values.put(mDbHelper.QUEST_COL_ANS, "1931");
            mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(mDbHelper.QUEST_COL_NAME, "European Cars");
            values.put(mDbHelper.QUEST_COL_QUEST, "Lamborghini was founded in 1963.");
            values.put(mDbHelper.QUEST_COL_TYPE, "tf");
            values.put(mDbHelper.QUEST_COL_ANS, true);
            mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(mDbHelper.QUIZ_COL_NAME, "American Cars");
            mDb.insert(mDbHelper.QUIZ_TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(mDbHelper.QUEST_COL_NAME, "American Cars");
            values.put(mDbHelper.QUEST_COL_QUEST, "In what year was Ford founded?");
            values.put(mDbHelper.QUEST_COL_TYPE, "fillIn");
            values.put(mDbHelper.QUEST_COL_ANS, "1903");
            mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
            values = new ContentValues();
            values.put(mDbHelper.QUEST_COL_NAME, "American Cars");
            values.put(mDbHelper.QUEST_COL_QUEST, "Chevy was founded in 1901");
            values.put(mDbHelper.QUEST_COL_TYPE, "tf");
            values.put(mDbHelper.QUEST_COL_ANS, false);
            mDb.insert(mDbHelper.QUEST_TABLE_NAME, null, values);
        }
        List<String> labels = mDbHelper.getAllLabels();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        currentQuiz = parent.getItemAtPosition(position).toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    public void takeQuiz(View view){
        if(currentQuiz == null){
            Toast.makeText(getApplicationContext(), "Please select a quiz first",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), TakeQuizActivity.class);
            intent.putExtra("currentQuiz", currentQuiz);
            startActivity(intent);
        }
    }
    public void addNewQuiz(View view){
        mDb = mDbHelper.getWritableDatabase();
        EditText editText = (EditText) findViewById(R.id.editText);
        String selection = MyDbHelper.QUIZ_COL_NAME+" = ?";
        String[] args = new String[]{editText.getText().toString()};
        Cursor result = mDb.query(MyDbHelper.QUIZ_TABLE_NAME,
                COLUMNS,
                selection,args,null,null,null,null);
        if (result.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "A quiz with this name already exists!",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            ContentValues values = new ContentValues();
            values.put(mDbHelper.QUIZ_COL_NAME, editText.getText().toString());
            mDb.insert(mDbHelper.QUIZ_TABLE_NAME, null, values);
            List<String> labels = mDbHelper.getAllLabels();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, labels);
            dataAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            editText.setText("");
        }
    }
    public void deleteCurrentQuiz(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you sure you want to delete this quiz?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Spinner spinner = (Spinner) findViewById(R.id.spinner);
                        mDb = mDbHelper.getWritableDatabase();
                        mDb.delete(mDbHelper.QUIZ_TABLE_NAME, mDbHelper.QUIZ_COL_NAME + "=?", new String[]{currentQuiz});
                        mDb.delete(mDbHelper.QUEST_TABLE_NAME, mDbHelper.QUEST_COL_NAME + "=?", new String[]{currentQuiz});
                        List<String> labels = mDbHelper.getAllLabels();
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, labels);
                        dataAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                    }
                });
        builder.setNeutralButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void createQuestion(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("New Question");
        builder.setMessage("Select a Type of Question: ");
        builder.setPositiveButton("T/F",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
                        intent.putExtra("currentQuiz",currentQuiz);
                        intent.putExtra("type","tf");
                        startActivity(intent);
                    }
                });

        builder.setNeutralButton("One Word",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
                        intent.putExtra("currentQuiz",currentQuiz);
                        intent.putExtra("type","fillIn");
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton("Multiple Choice",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), CreateQuestionActivity.class);
                        intent.putExtra("currentQuiz",currentQuiz);
                        intent.putExtra("type","mul");
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
