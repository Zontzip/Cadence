package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.controller.DBManager;

import java.io.ByteArrayOutputStream;

/**
 * Displays information related to the user, user can tap on profile to take new picture
 */
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
                dispatchTakePictureIntent();
            }
        });
    }

    /** Retrieves and displays info about the user from database */
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
            cursor = db.getRides(1);
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

            // Set the users profile picture
            try {
                cursor = db.getUser();
                byte[] photo = cursor.getBlob(cursor.getColumnIndex("profilepic"));
                Bitmap theImage= BitmapFactory.decodeByteArray(photo, 0, photo.length);
                imgProfile.setImageBitmap(theImage);
            } catch (Exception ep) {
                Toast toast = Toast.makeText(getApplicationContext(), "No profile image exists",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /** Start camera activity */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    /** Call back method for camera activity; stores the image taken in database */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();

            try {
                db.open();
                db.insertProfilePic(bArray);
                db.close();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            imgProfile.setImageBitmap(imageBitmap);
        }
    }
}
