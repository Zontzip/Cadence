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
import com.zontzor.cadence.controller.DBManager;
import com.zontzor.cadence.views.adapters.UserBikesCursorAdapter;
import com.zontzor.cadence.views.sub.BikeAddActivity;

/**
 * Creates list of users bikes contained in database, initialises context menu
 */
public class UserBikesActivity extends Activity {
    private DBManager db = new DBManager(this);
    private ListView listBikes;
    private UserBikesCursorAdapter cursorAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bikes);

        listBikes = (ListView) findViewById(R.id.list_bikes);
        Button btnAddRide = (Button) findViewById(R.id.button_bikes_add);
        registerForContextMenu(listBikes);

        try {
            db.open();

            Cursor bikes = db.getBikes();
            cursorAdapter = new UserBikesCursorAdapter(UserBikesActivity.this, bikes);
            listBikes.setAdapter(cursorAdapter);

            db.close();
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        btnAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addBikeActivity = new Intent(UserBikesActivity.this, BikeAddActivity.class);
                startActivity(addBikeActivity);
            }
        });
    }

    /** Handles context menu creation */
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_ride, menu);
    }

    /** Handles context menu click event */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int index = (int) info.id;

        switch (item.getItemId()) {
            case R.id.item_delete:
                try {
                    db.open();

                    db.deleteBike(index);
                    Cursor cursor = db.getBikes();

                    db.close();
                    cursorAdapter.changeCursor(cursor);
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
