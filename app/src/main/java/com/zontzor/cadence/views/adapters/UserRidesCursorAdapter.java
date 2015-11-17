package com.zontzor.cadence.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.zontzor.cadence.R;

public class UserRidesCursorAdapter extends CursorAdapter {
    public UserRidesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_user_rides, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView rideName = (TextView) view.findViewById(R.id.text_ride_name);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("ridename"));

        rideName.setText(name);
    }
}
