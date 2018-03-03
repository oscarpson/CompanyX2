package joslabs.companyx.viewsperson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import joslabs.companyx.R;
import joslabs.companyx.analysis.AnalysisActivity;
import joslabs.companyx.users;

public class Viewsperson extends AppCompatActivity {
RecyclerView rcv;
    List<users>userses;
    DatabaseReference dbref;
    FirebaseRecyclerAdapter<users,ViewspersonViewHolder> firebaseRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsperson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        dbref= FirebaseDatabase.getInstance().getReference();
        rcv= (RecyclerView) findViewById(R.id.rcvsalespersonview);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(null);
        userses=new ArrayList<>();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<users, ViewspersonViewHolder>(users.class,R.layout.viewperson_activity,ViewspersonViewHolder.class,dbref.child("salesrep")) {
            @Override
            protected void populateViewHolder(ViewspersonViewHolder viewHolder, users model, int position) {

                viewHolder.username.setText(model.getFname());
                viewHolder.region.setText("Region-"+model.getLname());
                viewHolder.lats.setText(model.getLats());
                viewHolder.longts.setText(model.getLongs());
                viewHolder.key.setText(model.getSpkey());
                Picasso.with(getApplicationContext())
                        .load(model.getSalespersonKey())
                        .into(viewHolder.profile);
                viewHolder.customers.setText(  dbref.child("salesrep") .getKey());


            }
        };
        rcv.setAdapter(firebaseRecyclerAdapter);


    }

}
