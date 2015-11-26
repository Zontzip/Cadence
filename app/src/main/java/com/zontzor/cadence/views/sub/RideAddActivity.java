package com.zontzor.cadence.views.sub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.controller.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Insert new ride into database
 */
public class RideAddActivity extends Activity {
    DBManager db = new DBManager(this);
    EditText rideName;
    EditText rideDuration;
    EditText rideDistance;
    Button btnAddRide;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_add);

        rideName = (EditText) findViewById(R.id.editText_ride_name);
        rideDuration = (EditText) findViewById(R.id.editText_ride_duration);
        rideDistance = (EditText) findViewById(R.id.editText_ride_distance);
        btnAddRide = (Button) findViewById(R.id.button_add_ride);

        final String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        btnAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                    db.insertRide(rideName.getText().toString(),
                            date,
                            Integer.parseInt(rideDuration.getText().toString()),
                            Integer.parseInt(rideDistance.getText().toString()),
                            1,
                            1);
                    db.close();

                    Toast toast = Toast.makeText(getApplicationContext(), "Ride added",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    rideName.setText("");
                    rideDuration.setText("");
                    rideDistance.setText("");
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
