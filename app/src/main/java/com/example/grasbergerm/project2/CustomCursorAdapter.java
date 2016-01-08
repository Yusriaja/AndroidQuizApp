package com.example.grasbergerm.project2;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by grasbergerm on 11/8/15.
 */
public class CustomCursorAdapter extends SimpleCursorAdapter{

    public CustomCursorAdapter(Context context, int layout, Cursor c,
                              String[] from, int[] to) {
        super(context, layout, c, from, to);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        int temp =cursor.getPosition()+1;
        LinearLayout rl = (LinearLayout) view;
        TextView tv = (TextView) rl.findViewById(R.id.QuestionID);
        tv.setText(Integer.toString(temp));
        super.bindView(view, context, cursor);
    }
}
