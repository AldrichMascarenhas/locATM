package com.nerdcutlet.atmfinder;

import java.text.DateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nerdcutlet.atmfinder.model.AtmModelData;

import java.util.Date;

public class ATMFormACTIVITY extends AppCompatActivity {

    public static final String LOG_TAG = "ATMFormACTIVITY";
    private CoordinatorLayout coordinatorLayout;
    private String EXTRA_ATM_PLACEID;

    EditText et_100, et_2000, et_final_amount;
    EditText et_100_value, et_2000_value;

    int result_value_100, result_value_2000;

    private DatabaseReference AtmIDReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmform_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Get post key from intent
        EXTRA_ATM_PLACEID = getIntent().getStringExtra("EXTRA_ATM_PLACEID");
        if (EXTRA_ATM_PLACEID == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_PLACEID");
        }


        AtmIDReference = FirebaseDatabase.getInstance().getReference().child("atm_data").child(EXTRA_ATM_PLACEID);


        result_value_100 = 0;
        result_value_2000 = 0;

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        et_100 = (EditText) findViewById(R.id.et_100);
        et_2000 = (EditText) findViewById(R.id.et_2000);

        et_final_amount = (EditText) findViewById(R.id.et_final_amount);
        et_100_value = (EditText) findViewById(R.id.et_100_value);
        et_2000_value = (EditText) findViewById(R.id.et_2000_value);

        et_100.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_2000.setInputType(InputType.TYPE_CLASS_NUMBER);


        et_100.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(LOG_TAG, " value : " + editable.toString());

                if (!editable.toString().isEmpty()) {


                    int result = Integer.parseInt(editable.toString());
                    result_value_100 = result * 100;
                    String numberAsString = Integer.toString(result_value_100);

                    et_100_value.setText(numberAsString);
                    Log.d(LOG_TAG, editable.toString());

                    String finale = Integer.toString(result_value_2000 + result_value_100);
                    et_final_amount.setText(finale);
                } else {
                    et_final_amount.setText("");
                    et_100_value.setText("");

                }

            }


        });

        et_2000.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().isEmpty()) {
                    int result = Integer.parseInt(editable.toString());
                    result_value_2000 = result * 2000;
                    String numberAsString = Integer.toString(result_value_2000);

                    et_2000_value.setText(numberAsString);
                    Log.d(LOG_TAG, editable.toString());


                    String finale = Integer.toString(result_value_2000 + result_value_100);
                    et_final_amount.setText(finale);
                } else {
                    et_final_amount.setText("");
                    et_2000_value.setText("");
                }

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String total = Integer.toString(result_value_2000 + result_value_100);
                String number100 = et_100.getText().toString();
                String number2000 = et_2000.getText().toString();

                if (number100 == "" || number2000 == "" || number100.isEmpty() || number2000.isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Please Enter ALL missing data", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Sending Data. Be patient.", Snackbar.LENGTH_LONG);

                    snackbar.show();


                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                    Log.d(LOG_TAG, "t : " + total + " n100 : " + number100 + " n2000 : " + number2000 + " datetime : " + currentDateTimeString);

                    AtmModelData atmModelData = new AtmModelData(total, number100, number2000, currentDateTimeString);

                    //TODO: progress dialog.

                    final String key = AtmIDReference.push().getKey();


                    AtmIDReference.child(key).setValue(atmModelData, new DatabaseReference.CompletionListener() {
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

            }
        });
    }

}
