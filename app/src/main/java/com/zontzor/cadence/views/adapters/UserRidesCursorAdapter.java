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

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_user_rides, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView rideName = (TextView) view.findViewById(R.id.text_ride_name);
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow("ridename"));

        // Populate fields with extracted properties
        rideName.setText(name);
    }
}
