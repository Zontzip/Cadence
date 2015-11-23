package com.zontzor.cadence.views.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
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
        ImageView bikePic = (ImageView) view.findViewById(R.id.img_bike_pic);
        TextView bikeName = (TextView) view.findViewById(R.id.text_bike_name);
        TextView bikeNote = (TextView) view.findViewById(R.id.text_bike_note);

        String name = cursor.getString(cursor.getColumnIndex("bicyclename"));
        String note = cursor.getString(cursor.getColumnIndex("bicyclenotes"));
        String type = cursor.getString(cursor.getColumnIndex("bicycletype"));

        bikeName.setText(name);
        bikeNote.setText(note);

        switch (type) {
            case "City Bike":
                bikePic.setImageResource(R.mipmap.ic_rides_city);
                break;
            case "Fixed Gear":
                bikePic.setImageResource(R.mipmap.ic_rides_fixie);
                break;
            case "Mountain Bike":
                bikePic.setImageResource(R.mipmap.ic_rides_mtb);
                break;
            case "Road Bike":
                bikePic.setImageResource(R.mipmap.ic_rides_roadie);
                break;
        }
    }
}

