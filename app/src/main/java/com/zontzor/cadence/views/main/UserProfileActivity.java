package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

import junit.framework.TestCase;

public class UserProfileActivity extends Activity {
    DBManager db = new DBManager(this);
    Cursor cursor;

    TextView txtName;
    TextView txtRides;
    TextView txtBikes;
    TextView txtGoals;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getValues();
    }

    public void getValues() {
        txtName = (TextView) findViewById(R.id.text_profile_name);
        txtRides = (TextView) findViewById(R.id.text_profile_rides);
        txtBikes = (TextView) findViewById(R.id.text_profile_bikes);
        txtGoals = (TextView) findViewById(R.id.text_profile_goals);

        try {
            db.open();
            // Get users fullname
            cursor = db.getUser();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            txtName.setText(name);
            // Get users number of rides
            cursor = db.getRides();
            int rideCount = cursor.getCount();
            String rideCounts = Integer.toString(rideCount);
            txtRides.setText(rideCounts);
            // Get users total number of bikes
            cursor = db.getBikes();
            int bikeCount = cursor.getCount();
            String bikeCounts = Integer.toString(bikeCount);
            txtBikes.setText(bikeCounts);
            // Check if user reached goal
            cursor = db.getGoals();
            String userGoal = cursor.getString(cursor.getColumnIndex("completed"));
            String foo;

            if (userGoal.equals("1")) {
                foo = "Completed";
            } else {
                foo = "Not Completed";
            }
            txtGoals.setText(foo);

            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
