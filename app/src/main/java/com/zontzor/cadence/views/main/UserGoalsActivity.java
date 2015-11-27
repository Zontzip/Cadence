package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.controller.DBManager;
import com.zontzor.cadence.views.sub.GoalUpdateActivity;

/**
 * Displays the users goal and distance completed so far
 */

public class UserGoalsActivity extends Activity {
    TextView txtGoal;
    TextView txtDist;
    Button btnUpdate;
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);

        txtDist = (TextView) findViewById(R.id.text_kmweek);
        txtGoal = (TextView) findViewById(R.id.text_goal);
        btnUpdate = (Button) findViewById(R.id.btn_goals_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateGoal = new Intent(UserGoalsActivity.this, GoalUpdateActivity.class);
                startActivity(updateGoal);
            }
        });

        updateData();
    }

    /** Update the text fields containing use data from database */
    public void updateData() {
        try {
            Cursor crsDistance;
            Cursor crsGoals;

            db.open();
            crsDistance = db.getRidesSum();
            crsGoals = db.getGoals();
            db.close();

            // Users distance so far
            int usrDist = crsDistance.getInt(crsDistance.getColumnIndex("total"));
            txtDist.setText(crsDistance.getString(crsDistance.getColumnIndex("total")));

            // Users goal distance
            int usrGoal =  crsGoals.getInt(crsGoals.getColumnIndex("goalvalue"));
            txtGoal.setText(crsGoals.getString(crsGoals.getColumnIndex("goalvalue")));

            updateGoal(usrDist, usrGoal);
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /** Updates the users goal to completed if total distance of rides are more than the goal */
    public void updateGoal(int usrDist, int usrGoal) {
        if (usrDist > usrGoal) {
            try {
                db.open();
                db.updateGoalComp(1, 1);
                db.close();

                Toast toast = Toast.makeText(getApplicationContext(), "Goal completed!",
                        Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            try {
                db.open();
                db.updateGoalComp(0, 1);
                db.close();

                Toast toast = Toast.makeText(getApplicationContext(), "Goal not completed",
                        Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onResume() {
        super.onResume();
        updateData();;
    }
}
