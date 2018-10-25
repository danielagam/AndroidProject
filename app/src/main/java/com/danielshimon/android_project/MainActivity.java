package com.danielshimon.android_project;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danielshimon.android_project.model.entities.Travel;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button btn;
    private EditText chooseTime;

    Travel travel = new Travel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseTime = findViewById(R.id.startDrivingRequest);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        travel.setClientName(findViewById(R.id.name).toString());
        travel.setClientEmail(findViewById(R.id.mailClient).toString());
        travel.setClientNumber(findViewById(R.id.numberClient).toString());
        //travel.setStratDrving(Time.valueOf(findViewById(R.id.startDriving).toString()));

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), editText.getText().toString(), Toast.LENGTH_LONG).show();


            }
        });
    }
}
