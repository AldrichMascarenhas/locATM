package com.nerdcutlet.atmfinder;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nerdcutlet.atmfinder.model.AtmModelData;
import com.nerdcutlet.atmfinder.model.AtmStatusModelData;

import java.text.DateFormat;
import java.util.Date;

public class AtmStatusUpdate extends AppCompatActivity {

    private String EXTRA_ATM_PLACEID;
    private RadioGroup radioGroup;
    String status = "DISPENSING";
    public static final String LOG_TAG = "AtmStatusUpdate";
    private DatabaseReference AtmIDReference;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_status_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get post key from intent
        EXTRA_ATM_PLACEID = getIntent().getStringExtra("EXTRA_ATM_PLACEID");
        if (EXTRA_ATM_PLACEID == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_PLACEID");
        }

        AtmIDReference = FirebaseDatabase.getInstance().getReference().child("atm_status").child(EXTRA_ATM_PLACEID);

        Log.d(LOG_TAG, "status BEFORE : " + status);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    status = rb.getText().toString();
                    Log.d(LOG_TAG, "status : " + status);
                }

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check if status is null


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Sending Data. Be patient.", Snackbar.LENGTH_LONG);

                snackbar.show();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                AtmStatusModelData atmModelData = new AtmStatusModelData(status, currentDateTimeString);


                AtmIDReference.setValue(atmModelData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Successfully sent!", Snackbar.LENGTH_LONG);

                        snackbar.show();

                        // Execute some code after 5 seconds have passed
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                finish();
                            }
                        }, 1000);
                    }
                });

            }
        });
    }

}
