package com.zontzor.cadence.views.main;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.network.DBManager;

public class UserGoalsActivity extends Activity {
    TextView goal;
    TextView dist;
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);

        goal = (TextView) findViewById(R.id.text_goal);
        dist = (TextView) findViewById(R.id.text_kmweek);

        try {
            Cursor goals;
            Cursor sumRides;

            db.open();
            goals = db.getGoals();
            sumRides = db.getRidesSum();
            db.close();

            // User distance so far
            String sum;
            sum = sumRides.getString(sumRides.getColumnIndex("total"));
            dist.setText(sum);

            // User goal distance
            String distance;
            distance = goals.getString(goals.getColumnIndex("goalvalue"));
            goal.setText(distance);

            updateGoal(sum, distance);
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void updateGoal(String sum, String distance) {
        Cursor updateGoal;

        int sumI = Integer.parseInt(sum);
        int distanceI = Integer.parseInt(distance);

        if (sumI > distanceI) {
            try {
                Cursor goals;

                db.open();
                goals = db.updateGoalComp();
                db.close();
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
