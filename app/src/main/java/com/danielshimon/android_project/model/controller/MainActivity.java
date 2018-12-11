package com.danielshimon.android_project.model.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danielshimon.android_project.R;
import com.danielshimon.android_project.model.model.backend.Backend;
import com.danielshimon.android_project.model.model.backend.BackendFactory;
import com.danielshimon.android_project.model.model.entities.Travel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    //region init variable
    private static int SPLASH_TIME_OUT = 3500;
    double price;
    String longDrive;
    Location locationTarget = new Location("Location");
    Location locationCurrent = null;
    private Button orderBtn;
    private Button ourRecommendation;
    private EditText chooseTime;
    private TextView locationTextView;
    private TextView startDrivingRequest;
    private TextView destDrivingRequest;
    private EditText emailInput;
    private FusedLocationProviderClient client;
    private PlaceAutocompleteFragment destLocation;
    Travel travel = new Travel();
    LocationManager locationManager;
    LocationListener locationListener;

    //endregion
    public String getPlace(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getAddressLine(0);
            }
            return "אין מיקום זמין כרגע \n";
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return "שגיאה במציאת המיקום";
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(this, "נא לאפשר גישה למיקום", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

//    public void openDialog() {
//        PriceDialog priceDialog = new PriceDialog();
//        priceDialog.show(getSupportFragmentManager(), "סיכום הזמנה");
//    }

    private boolean findLocationFromAdress(String destDrivingRequest) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressesTarget = null;
            addressesTarget = geocoder.getFromLocationName(destDrivingRequest.toString(), 1);
            double a = addressesTarget.get(0).getLongitude();
            locationTarget.setLongitude(a);
            a = addressesTarget.get(0).getLatitude();
            locationTarget.setLatitude(a);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean validEmail() {
        String emailValid = emailInput.getText().toString().trim();
        if (!emailValid.isEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(emailValid).matches()) {
                emailInput.setError("מייל לא תקין");
                emailInput.setTextColor(-65536);
                return false;
            }
        }
        emailInput.setError(null);
        emailInput.setTextColor(Color.parseColor("#000000"));
        return true;
    }

    private void calcTravel() {
        //need to complex the calac
        try {
            float distanceInMeters = locationCurrent.distanceTo(locationTarget);
            int distanceInTime = (int) distanceInMeters / 60;
            price = 0.75 * distanceInMeters;
        } catch (Exception e) {
            price = 0;
        }

    }

    @Override
    public void onClick(View v) {
        try {
            EditText name = findViewById(R.id.name);
            travel.setClientName(name.getText().toString());
            travel.setClientEmail(emailInput.getText().toString());
            EditText number = findViewById(R.id.numberClient);
            travel.setClientNumber(number.getText().toString());
            if(locationTarget!=null&&locationCurrent!=null) {
                calcTravel();
            }
            String checkName = name.getText().toString();
            String checkNumber = number.getText().toString();
            travel.setCurrent(locationCurrent);
            travel.setDestination(locationTarget);
            //current = findViewById(R.id.startDrivingRequest);
            //travel.setStratDrving((current.getText().toString()));
            if (validEmail() && !checkName.isEmpty() && !checkNumber.isEmpty()) {
                final Backend backend = BackendFactory.getBackend();
                new AsyncTask<Context, Void, Void>() {

                    @Override
                    protected Void doInBackground(Context... contexts) {
                        backend.addRequest(travel, contexts[0]);
                        //openDialog();
                        return null;

                    }
                }.execute(this);
            } else {
                Toast.makeText(getApplicationContext(), "נא למלא את הפרטים החסרים",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == emailInput) {
            validEmail();
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    locationCurrent = location;
                    TextView myLocation = findViewById(R.id.MyLocation);
                    myLocation.setText(getPlace(location));
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseTime = findViewById(R.id.startDrivingRequest);
        emailInput = findViewById(R.id.mailClient);
        EditText name = findViewById(R.id.name);
        destLocation = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        destLocation.setHint("נא להכניס יעד נסיעה");
        destLocation.getView().setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_edittext));



        destLocation.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationTarget.setAltitude(place.getLatLng().latitude);
                locationTarget.setLongitude(place.getLatLng().longitude);

                //TODO take the place
            }

            @Override
            public void onError(Status status) { }
        });

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
        client = LocationServices.getFusedLocationProviderClient(this);
        startDrivingRequest = (EditText) findViewById(R.id.startDrivingRequest);
        onFocusChange(emailInput, true);
        emailInput.setOnFocusChangeListener(this);
        orderBtn = (Button) findViewById(R.id.orderbtn);
        orderBtn.setOnClickListener(this);
        requestPermission();
        validEmail();
    }

}
