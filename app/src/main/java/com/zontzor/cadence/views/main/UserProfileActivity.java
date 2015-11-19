package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;
import com.zontzor.cadence.views.adapters.UserRidesCursorAdapter;

import org.w3c.dom.Text;

public class UserProfileActivity extends Activity {
    DBManager db = new DBManager(this);
    Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView txtName = (TextView) findViewById(R.id.text_profile_name);
        TextView txtRides = (TextView) findViewById(R.id.text_profile_rides);

        try {
            db.open();
            // Get users fullname
            cursor = db.getUser();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            txtName.setText(name);
            // Get users number of rides
            cursor = db.getRides();
            String rideCount = cursor.getString(cursor.getCount());
            txtRides.setText(rideCount);

            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
