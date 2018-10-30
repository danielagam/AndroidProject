package com.danielshimon.android_project.model;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.danielshimon.android_project.R;
import com.danielshimon.android_project.model.entities.Travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button btn;
    private EditText chooseTime;

    void getInstance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("key-1", "value 1 ");
        hashMap.put("key-2", "value 2 ");
        hashMap.put("key-3", "value 3 ");
        myRef.setValue(hashMap);
    }


    Travel travel = new Travel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getInstance();
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
