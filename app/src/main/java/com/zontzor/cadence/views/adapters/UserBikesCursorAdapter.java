package com.zontzor.cadence.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.zontzor.cadence.R;

public class UserBikesCursorAdapter extends CursorAdapter {
    public UserBikesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_user_bikes, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView bikeName = (TextView) view.findViewById(R.id.text_bike_name);
        TextView bikeNote = (TextView) view.findViewById(R.id.text_bike_note);

        String name = cursor.getString(cursor.getColumnIndex("bicyclename"));
        String note = cursor.getString(cursor.getColumnIndex("bicyclenotes"));

        bikeName.setText(name);
        bikeNote.setText(note);
    }
}

