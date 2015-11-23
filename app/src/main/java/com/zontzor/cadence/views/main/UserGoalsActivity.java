package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

public class UserGoalsActivity extends Activity {
    TextView txtGoal;
    TextView txtDist;
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);

        txtGoal = (TextView) findViewById(R.id.text_goal);
        txtDist = (TextView) findViewById(R.id.text_kmweek);

        try {
            Cursor csrGoals;
            Cursor crsDistance;

            db.open();
            csrGoals = db.getGoals();
            crsDistance = db.getRidesSum();
            db.close();

            // Users distance so far
            int usrDist = csrGoals.getColumnIndex("goalvalue");
            txtGoal.setText(csrGoals.getString(csrGoals.getColumnIndex("goalvalue")));

            // Users goal distance
            int usrGoal = crsDistance.getColumnIndex("total");
            txtDist.setText(crsDistance.getString(crsDistance.getColumnIndex("total")));

            updateGoal(usrDist, usrGoal);
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void updateGoal(int usrDist, int usrGoal) {
        if (usrDist > usrGoal) {
            try {
                db.open();
                db.updateGoalComp();
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
            Toast toast = Toast.makeText(getApplicationContext(), "Goal not completed",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
