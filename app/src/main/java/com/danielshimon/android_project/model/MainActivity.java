package com.danielshimon.android_project.model;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.danielshimon.android_project.R;
import com.danielshimon.android_project.model.entities.Travel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    //region init variable
    double price;
    String longDrive;
    Location locationTarget=new Location("Location");
    Location locationCurrent=null;
    private EditText editText;
    private Button orderBtn;
    private Button ourrecommendation;
    private EditText chooseTime;
    private TextView locationTextView;
    private TextView startDrivingRequest;
    private TextView destDrivingRequest;
    private FusedLocationProviderClient client;
    Travel travel = new Travel();
    LocationManager locationManager;
    LocationListener locationListener;
    //endregion
    void getInstance() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("key-1", "value 1 ");
        hashMap.put("key-2", "value 2 ");
        hashMap.put("key-3", "value 3 ");
        myRef.setValue(hashMap);
    }
    public String getPlace(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                return cityName;
            }
            return "אין מיקום זמין כרגע \n"  ;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return "IOException ...";
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.ACCESS_FINE_LOCATION},1);
}
    public void openDialog(){
        PriceDialog priceDialog= new PriceDialog();
        priceDialog.show(getSupportFragmentManager(),"בדיקה");
    }
    private boolean findLocationFromAdress(String destDrivingRequest) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressesTarget=null;
            addressesTarget = geocoder.getFromLocationName(destDrivingRequest.toString(), 1);
            double a=addressesTarget.get(0).getLongitude();
            locationTarget.setLongitude(a);
            a=addressesTarget.get(0).getLatitude();
            locationTarget.setLatitude(a);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    private void calcTravel(){
        //need to complex the calac
        try{
            float distanceInMeters = locationCurrent.distanceTo(locationTarget);
            int distanceInTime=(int)distanceInMeters/60;
            price=0.75*distanceInMeters;
        }
        catch(Exception e)
        {
            price=0;
        }

    }
    @Override
    public void onClick(View v) {
        //  TODO request to DB
        destDrivingRequest=(TextView) findViewById(R.id.destinationDrivingRequest);
        if(findLocationFromAdress(destDrivingRequest.getText().toString())) {
            calcTravel();
            openDialog();
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null){
                    locationCurrent=location;
                    TextView myLocation=findViewById(R.id.MyLocation);
                    myLocation.setText(getPlace(location));
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //getInstance()
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
        client = LocationServices.getFusedLocationProviderClient(this);
        startDrivingRequest = (EditText) findViewById(R.id.startDrivingRequest);
        startDrivingRequest.setOnFocusChangeListener(this);
        orderBtn=(Button) findViewById(R.id.orderbtn);
        orderBtn.setOnClickListener(this);
        requestPermission();
    }
}
