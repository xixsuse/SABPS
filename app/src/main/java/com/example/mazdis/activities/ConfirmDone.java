package com.example.mazdis.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.mazdis.sabps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfirmDone extends BaseActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_done);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int) (height*0.3));

        TextView headerView = (TextView) findViewById(R.id.header_textview);
        headerView.setPadding(0,50,0,50);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    public void startMap(View view){

        completeBooking();
        startActivity(new Intent(this, MapsActivity.class));
        finish();
    }

    public void backToReservedMap(View view){
        startActivity((new Intent(this, ReservedMapsActivity.class)));
        finish();
    }

    public void completeBooking(){


        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String endTime = timeFormat.format(c.getTime());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String bookingTitle = prefs.getString("bookingTitle", "no id");

        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = mDatabase.child(user_id).child("bookings").child(bookingTitle);

        current_user_db.child("end time").setValue(endTime);
    }

}

