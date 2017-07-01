package com.nerdcutlet.atmfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nerdcutlet.atmfinder.ATMFormACTIVITY;
import com.nerdcutlet.atmfinder.AtmStatusUpdate;
import com.nerdcutlet.atmfinder.R;
import com.nerdcutlet.atmfinder.model.AtmModelData;
import com.nerdcutlet.atmfinder.model.AtmStatusModelData;


public class AtmDetail extends AppCompatActivity {
    private static final String LOG_TAG = "AtmDetail";


    String EXTRA_ATM_NAME;
    String EXTRA_ATM_VICINITY;
    String EXTRA_ATM_PLACEID;

    FloatingActionMenu menuRed;

    FloatingActionButton fab1;
    FloatingActionButton fab2;

    TextView tv_name, tv_vicinity, tv_id, tv_status, tv_date;

    DatabaseReference AtmIDReference;
    String STATUS, DATE_TIME;

    Button button_trans;




    private DatabaseReference mATMDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_detail);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Get post key from intent
        EXTRA_ATM_NAME = getIntent().getStringExtra("EXTRA_ATM_NAME");
        if (EXTRA_ATM_NAME == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_NAME");
        }

        // Get post key from intent
        EXTRA_ATM_VICINITY = getIntent().getStringExtra("EXTRA_ATM_VICINITY");
        if (EXTRA_ATM_VICINITY == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_VICINITY");
        }

        // Get post key from intent
        EXTRA_ATM_PLACEID = getIntent().getStringExtra("EXTRA_ATM_PLACEID");
        if (EXTRA_ATM_PLACEID == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_PLACEID");
        }


        AtmIDReference = FirebaseDatabase.getInstance().getReference().child("atm_status").child(EXTRA_ATM_PLACEID);

        mATMDataRef = FirebaseDatabase.getInstance().getReference().child("atm_data").child(EXTRA_ATM_PLACEID);

        Log.d(LOG_TAG, "NAME : " + EXTRA_ATM_NAME + " VICINITY : " + EXTRA_ATM_VICINITY + " PLACEID : " + EXTRA_ATM_PLACEID);


        menuRed = (FloatingActionMenu) findViewById(R.id.menu_red);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_vicinity = (TextView) findViewById(R.id.tv_vicinity);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_date = (TextView) findViewById(R.id.tv_date);

        button_trans = (Button)findViewById(R.id.button_trans);

        tv_name.setText(EXTRA_ATM_NAME);
        tv_vicinity.setText(EXTRA_ATM_VICINITY);
        tv_id.setText(EXTRA_ATM_PLACEID);




        AtmIDReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    AtmStatusModelData digitsModel = dataSnapshot.getValue(AtmStatusModelData.class);
                    STATUS = digitsModel.getStatus();
                    DATE_TIME = digitsModel.getDatetime();


                    tv_status.setText(STATUS);
                    tv_date.setText(DATE_TIME);


                } else {
                    tv_status.setText("No Data Available");
                    tv_date.setText("NO last update time");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AtmStatusUpdate.class);

                intent.putExtra("EXTRA_ATM_PLACEID", EXTRA_ATM_PLACEID);

                startActivity(intent);
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ATMFormACTIVITY.class);

                intent.putExtra("EXTRA_ATM_PLACEID", EXTRA_ATM_PLACEID);

                startActivity(intent);
            }
        });

        button_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);

                intent.putExtra("EXTRA_ATM_PLACEID", EXTRA_ATM_PLACEID);

                startActivity(intent);
            }
        });




    }

}
