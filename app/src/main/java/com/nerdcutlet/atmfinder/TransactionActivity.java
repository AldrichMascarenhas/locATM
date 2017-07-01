package com.nerdcutlet.atmfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nerdcutlet.atmfinder.model.AtmModelData;
import com.nerdcutlet.atmfinder.viewholders.PostViewHolder;

public class TransactionActivity extends AppCompatActivity {
    private static final String LOG_TAG = "TransactionActivity";

    String EXTRA_ATM_PLACEID;


    private FirebaseRecyclerAdapter<AtmModelData, PostViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    private DatabaseReference mATMDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Get post key from intent
        EXTRA_ATM_PLACEID = getIntent().getStringExtra("EXTRA_ATM_PLACEID");
        if (EXTRA_ATM_PLACEID == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ATM_PLACEID");
        }

        mATMDataRef = FirebaseDatabase.getInstance().getReference().child("atm_data").child(EXTRA_ATM_PLACEID);

        mRecycler = (RecyclerView) findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query postsQuery = mATMDataRef.limitToFirst(10);

        mAdapter = new FirebaseRecyclerAdapter<AtmModelData, PostViewHolder>(AtmModelData.class, R.layout.item_post,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final AtmModelData model, final int position) {

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model);

            }
        };
        mRecycler.setAdapter(mAdapter);

    }
}
