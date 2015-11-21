package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.views.adapters.MainMenuGridAdapter;
import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

public class MainActivity extends Activity {
    GridView gridView;
    DBManager db = new DBManager(this);

    static final String[] options = new String[] {
            "Profile", "Rides","Goals" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testDB();
        setupGrid();
    }

    public void setupGrid() {
        gridView = (GridView) findViewById(R.id.menu_grid);
        gridView.setAdapter(new MainMenuGridAdapter(this, options));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent myNewActivity = null;

                TextView textView = (TextView) v.findViewById(R.id.grid_item_label);
                String selection = (String) textView.getText();

                switch (selection) {
                    case "Profile":
                        myNewActivity = new Intent(MainActivity.this, UserProfileActivity.class);
                        break;
                    case "Rides":
                        myNewActivity = new Intent(MainActivity.this, UserRidesActivity.class);
                        break;
                    case "Goals":
                        myNewActivity = new Intent(MainActivity.this, UserGoalsActivity.class);
                        break;
                }

                startActivity(myNewActivity);
            }
        });
    }

    public void testDB() {
        try {
            db.open();
            Cursor rides = db.getRides();
            String str;

            if (rides.moveToFirst()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Database exists",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Creating database",
                        Toast.LENGTH_SHORT);
                toast.show();

                db.insertUser("Zont", "1234", "Alex Kiernan");
                db.insertUser("Dollah", "6789", "Abdul Hakim");

                db.insertBicycle("Racer", "Road Bike", "Bianchi", "Bike has 11 speed groupset", 1);
                db.insertBicycle("Commuter", "Fixed Gear", "Dolan", "46 x 17 gear ratio", 1);
                db.insertBicycle("Commutter", "City Bike", "Dublin Bike", "Three speed", 2);

                db.insertRide("Lunch Ride", 3, 1, 1);
                db.insertRide("Morning Commute", 1, 1, 2);
                db.insertRide("Test Ride 1", 3, 1, 1);
                db.insertRide("Test Commute 1", 1, 1, 2);
                db.insertRide("Test Ride 2", 3, 1, 1);
                db.insertRide("Test Commute 2", 1, 1, 2);
            }

            db.close();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error inserting data",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
