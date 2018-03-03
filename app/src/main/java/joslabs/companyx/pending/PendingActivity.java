package joslabs.companyx.pending;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import joslabs.companyx.R;
import joslabs.companyx.productdistribution.BuyingProduct;

public class PendingActivity extends AppCompatActivity {
//TextView username,amount,modeofpayment,products,region;
    DatabaseReference dbref;
    List<BuyingProduct>buyingProductList;
    RecyclerView rcv;
    TextView tcash,tpending;
    int pending,cash;
    FirebaseRecyclerAdapter<BuyingProduct,PendingViewHolder>recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        dbref = FirebaseDatabase.getInstance().getReference();
        cash=0;
        pending=0;
tcash= (TextView) findViewById(R.id.txtcash);
       tpending= (TextView) findViewById(R.id.txtpending);
        rcv = (RecyclerView) findViewById(R.id.rcvpending);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv.setAdapter(null);

        buyingProductList = new ArrayList<>();
        recyclerAdapter = new FirebaseRecyclerAdapter<BuyingProduct, PendingViewHolder>(BuyingProduct.class, R.layout.pending_activity, PendingViewHolder.class, dbref.child("Productsell")) {
            @Override
            protected void populateViewHolder(PendingViewHolder viewHolder, BuyingProduct model, int position) {



                if (model.getPaid().matches("1"))
                {
                    cash=cash+Integer.parseInt(model.getAmount())* Integer.parseInt(model.getPnumber());
                    Toast.makeText(PendingActivity.this, "total"+cash, Toast.LENGTH_SHORT).show();
                    tcash.setText("Ksh paid: \t"+cash);
                    viewHolder.region.setText("(paid)");


                }
                if (model.getPaid().matches("0"))
                {
                    pending=pending+Integer.parseInt(model.getAmount())* Integer.parseInt(model.getPnumber());
                    //Toast.makeText(PendingActivity.this, "total"+pending, Toast.LENGTH_SHORT).show();
                    tpending.setText("To Ksh pay: \t"+pending);
                   viewHolder.region.setText("");


                }
                viewHolder.username.setText(model.getClientkey());
                viewHolder.amount.setText(" Ksh" + Integer.parseInt(model.getAmount())*Integer.parseInt(model.getPnumber()) + "\t");
                viewHolder.modePayment.setText("\t via \t" + model.getPaymentmode());
                viewHolder.productsb.setText(model.getPtype()+"\t\tX" +model.getPnumber());
                //tcash.setText(cash);
/*dbref.child("Productsell").orderByChild("paid").equalTo("1").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        cash=cash+Integer.parseInt(dataSnapshot.child("amount").getValue().toString()) * Integer.parseInt(dataSnapshot.child("pnumber").getValue().toString());
        tcash.setText(cash+"");
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});*/
            }



        };

dbref.child("userkeys").child("12345").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("dataxz",dataSnapshot.getValue().toString());

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
      //  tcash.setText(cash);
        rcv.setAdapter(recyclerAdapter);
    }


}
