package com.zontzor.cadence.views.sub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

public class GoalUpdateActivity extends Activity {
    DBManager db = new DBManager(this);

    EditText newGoal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_update);

        newGoal = (EditText) findViewById(R.id.edittext_goal_new);

        newGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                    db.updateGoal(goal);
                    db.close();
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
