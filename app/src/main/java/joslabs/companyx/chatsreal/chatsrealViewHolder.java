package joslabs.companyx.chatsreal;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import joslabs.companyx.R;


/**
 * Created by OSCAR on 8/4/2017.
 */

public class chatsrealViewHolder extends ViewHolder {
    public  TextView username;
    public TextView time;
    public TextView chattext;
    public ImageView profilepic,chatimg;
    public chatsrealViewHolder(View itemView) {
        super(itemView);

        username= (TextView) itemView.findViewById(R.id.txtuser);
        time= (TextView) itemView.findViewById(R.id.txttime);
        chattext= (TextView) itemView.findViewById(R.id.txtmyquiz);
        profilepic= (ImageView) itemView.findViewById(R.id.imgchatme);
        chatimg= (ImageView) itemView.findViewById(R.id.imgchatquiz);
    }
}
