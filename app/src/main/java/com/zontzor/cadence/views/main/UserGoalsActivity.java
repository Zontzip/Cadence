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
import com.zontzor.cadence.network.DBManager;
import com.zontzor.cadence.views.sub.GoalUpdateActivity;

public class UserGoalsActivity extends Activity {
    TextView txtGoal;
    TextView txtDist;
    Button btnUpdate;
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);

        txtGoal = (TextView) findViewById(R.id.text_goal);
        txtDist = (TextView) findViewById(R.id.text_kmweek);
        btnUpdate = (Button) findViewById(R.id.btn_goals_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateGoal = new Intent(UserGoalsActivity.this, GoalUpdateActivity.class);
                startActivity(updateGoal);
            }
        });

        try {
            Cursor crsDistance;
            Cursor csrGoals;

            db.open();
            crsDistance = db.getRidesSum();
            csrGoals = db.getGoals();
            db.close();

            // Users distance so far
            int usrDist = crsDistance.getColumnIndex("total");
            txtGoal.setText(crsDistance.getString(crsDistance.getColumnIndex("total")));

            // Users goal distance
            int usrGoal = csrGoals.getColumnIndex("goalvalue");
            txtDist.setText(csrGoals.getString(csrGoals.getColumnIndex("goalvalue")));

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
                Cursor cursor = db.updateGoalComp(1);
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
                Cursor cursor = db.updateGoalComp(0);
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
}
