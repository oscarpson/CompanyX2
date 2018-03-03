package joslabs.companyx.viewsperson;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import joslabs.companyx.R;
import joslabs.companyx.analysis.AnalysisActivity;

/**
 * Created by OSCAR on 9/2/2017.
 */

public class ViewspersonViewHolder extends RecyclerView.ViewHolder {
    ImageView profile;
    TextView username,region,customers,pendingcash,cash,analysis,moredata,location,lats,longts,key;
    public ViewspersonViewHolder(final View itemView) {
        super(itemView);
        username= (TextView) itemView.findViewById(R.id.txtusername);
        region= (TextView) itemView.findViewById(R.id.txtregion);
        customers= (TextView) itemView.findViewById(R.id.txtcustomers);
        pendingcash= (TextView) itemView.findViewById(R.id.txtpendingcash);
        cash= (TextView) itemView.findViewById(R.id.txtcash);
        analysis= (TextView) itemView.findViewById(R.id.txtanalysis);
        moredata= (TextView) itemView.findViewById(R.id.txtanalysisy);
        location= (TextView) itemView.findViewById(R.id.viewloca);
        profile= (ImageView) itemView.findViewById(R.id.imguser);
        lats= (TextView) itemView.findViewById(R.id.txtlats);
        key= (TextView) itemView.findViewById(R.id.txtkey);
        longts= (TextView) itemView.findViewById(R.id.txtlongts);

        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(itemView.getContext(), AnalysisActivity.class);
                itemView.getContext().startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(itemView.getContext(),Viewsalesmap.class);
                intent.putExtra("lats",lats.getText().toString());
                intent.putExtra("longts",longts.getText().toString());
                intent.putExtra("username",username.getText().toString());
                itemView.getContext().startActivity(intent);
            }
        });
    }

}


