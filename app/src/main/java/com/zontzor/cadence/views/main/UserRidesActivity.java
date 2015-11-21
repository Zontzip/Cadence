package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;
import com.zontzor.cadence.views.sub.RideAddActivity;
import com.zontzor.cadence.views.adapters.UserRidesCursorAdapter;

public class UserRidesActivity extends Activity {
    DBManager db = new DBManager(this);
    ListView listRides;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rides);

        listRides = (ListView) findViewById(R.id.list_rides);
        Button btnAddRide = (Button) findViewById(R.id.button_rides_add);
        registerForContextMenu(listRides);

        getRides();

        btnAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRideActivity = new Intent(UserRidesActivity.this, RideAddActivity.class);
                startActivity(addRideActivity);
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_ride, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item_edit_ride:
                return true;
            case R.id.item_delete_ride:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void getRides() {
        try {
            db.open();

            Cursor rides = db.getRides();
            UserRidesCursorAdapter cursorAdapter = new UserRidesCursorAdapter
                    (UserRidesActivity.this, rides);
            listRides.setAdapter(cursorAdapter);

            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
