package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

import junit.framework.TestCase;

public class UserProfileActivity extends Activity {
    DBManager db = new DBManager(this);
    Cursor cursor;

    ImageView imgProfile;
    TextView txtName;
    TextView txtRides;
    TextView txtBikes;
    TextView txtGoals;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imgProfile = (ImageView) findViewById(R.id.img_profile);

        getValues();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "You clicked the picture!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
