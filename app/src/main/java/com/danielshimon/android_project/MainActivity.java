package com.danielshimon.android_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danielshimon.android_project.model.entities.Driver;
import com.danielshimon.android_project.model.entities.Travel;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    private EditText name1;
    private Button btn;

    Travel travel = new Travel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        travel.setClientName(findViewById(R.id.name).toString());
        travel.setClientEmail(findViewById(R.id.mailClient).toString());
        travel.setClientNumber(findViewById(R.id.numberClient).toString());
        travel.setStratDrving(Time.valueOf(findViewById(R.id.startDriving).toString()));

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), name1.getText().toString(), Toast.LENGTH_LONG).show();


            }
        });

    }
}
