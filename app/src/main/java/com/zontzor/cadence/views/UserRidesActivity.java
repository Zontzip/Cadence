package com.zontzor.cadence.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;
import com.zontzor.cadence.views.adapters.UserRidesCursorAdapter;

public class UserRidesActivity extends Activity {
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rides);

        ListView listRides = (ListView) findViewById(R.id.list_rides);
        Button btnAddRide = (Button) findViewById(R.id.button_rides_add);

        try {
            db.open();
            Cursor rides = db.getRides();
            String str = rides.getString(rides.getColumnIndex("ridename"));
            //UserRidesCursorAdapter cursorAdapter = new UserRidesCursorAdapter(UserRidesActivity.this, rides);
            //listRides.setAdapter(cursorAdapter);
            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database", Toast.LENGTH_LONG);
            toast.show();
        }

        btnAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRideActivity = new Intent(UserRidesActivity.this, RideAddActivity.class);
                startActivity(addRideActivity);
            }
        });
    }
}
