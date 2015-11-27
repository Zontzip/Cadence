package com.zontzor.cadence.views.sub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zontzor.cadence.R;
import com.zontzor.cadence.controller.DBManager;

/**
 * Updates user goal to inputted value
 */
public class GoalUpdateActivity extends Activity {
    DBManager db = new DBManager(this);

    EditText editTextUpdateGoal;
    Button btnUpdateGoal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_update);

        editTextUpdateGoal = (EditText) findViewById(R.id.edittext_goal_update);
        btnUpdateGoal = (Button) findViewById(R.id.btn_goal_update);

        btnUpdateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int value = Integer.parseInt(editTextUpdateGoal.getText().toString());
                    db.open();
                    db.updateGoal(value, 1);
                    db.close();
                    editTextUpdateGoal.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "Goal updated",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
                } catch (Exception ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error opening database",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
