package joslabs.companyx.todaycash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import joslabs.companyx.R;
import joslabs.companyx.pending.PendingViewHolder;
import joslabs.companyx.productdistribution.BuyingProduct;

public class todaypayments extends AppCompatActivity {
RecyclerView rcvcash;
    List<BuyingProduct>buyingProductList;
    FirebaseRecyclerAdapter<BuyingProduct,PendingViewHolder>firebaseRecyclerAdapter;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaypayments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        dbref= FirebaseDatabase.getInstance().getReference();
        rcvcash= (RecyclerView) findViewById(R.id.rcvcash);
        rcvcash.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcvcash.setAdapter(null);

        buyingProductList=new ArrayList<>();

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<BuyingProduct, PendingViewHolder>(BuyingProduct.class, R.layout.pending_activity,PendingViewHolder.class,dbref.child("Productsell")) {
            @Override
            protected void populateViewHolder(PendingViewHolder viewHolder, BuyingProduct model, int position) {
                Log.e("datax", model.getClass().getMethods().toString());
                viewHolder.username.setText(model.getClientkey());
                viewHolder.amount.setText(model.getAmount());
                viewHolder.modePayment.setText(model.getPaymentmode());
                viewHolder.productsb.setText(model.getPtype());

            }
        };
        rcvcash.setAdapter(firebaseRecyclerAdapter);
    }

}
