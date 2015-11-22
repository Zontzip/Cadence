package com.zontzor.cadence.views.sub;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

public class BikeAddActivity extends Activity {
    DBManager db = new DBManager(this);
    EditText bikeName;
    EditText bikeBrand;
    Button btnAddBike;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_add);

        bikeName = (EditText) findViewById(R.id.editText_bike_name);
        bikeBrand = (EditText) findViewById(R.id.editText_bike_brand);
        btnAddBike = (Button) findViewById(R.id.button_add_bike);
        spinner = (Spinner) findViewById(R.id.spinner_bike_type);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bikes_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        btnAddBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String bikeType = spinner.getSelectedItem().toString();
                    db.open();
                    db.insertBicycle(bikeName.getText().toString(), bikeType,
                            bikeBrand.getText().toString(), "This is a bike", 1);
                    db.close();

                    Toast toast = Toast.makeText(getApplicationContext(), "Bike added",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    bikeName.setText("");
                    bikeBrand.setText("");
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
