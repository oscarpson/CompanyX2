package joslabs.companyx.pending;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import joslabs.companyx.R;

/**
 * Created by OSCAR on 8/24/2017.
 */

public class PendingViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public TextView amount;
    public TextView modePayment;
    public TextView productsb;
    TextView region;
    public PendingViewHolder(View itemView) {
        super(itemView);
        username= (TextView) itemView.findViewById(R.id.txtusername);
        amount= (TextView) itemView.findViewById(R.id.txtamount);
        modePayment= (TextView) itemView.findViewById(R.id.txtmodepayement);
        productsb= (TextView) itemView.findViewById(R.id.txtproducts);
        region= (TextView) itemView.findViewById(R.id.txtregion);




    }

}