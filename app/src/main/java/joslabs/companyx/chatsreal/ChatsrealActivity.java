package joslabs.companyx.chatsreal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import joslabs.companyx.R;


public class ChatsrealActivity extends AppCompatActivity {
RecyclerView recyclerView;
    List<ChatsReal>chatsReals;
    FirebaseRecyclerAdapter<ChatsReal,chatsrealViewHolder> firebaseRecyclerAdapter;

    DatabaseReference dbref;
    Button sendchat,btncameraSend;
    EditText edtchat;
    String sedtchat,userId,usernames,photourl,userKey;
    SharedPreferences pref;
    StorageReference storageRef;
    ImageView imgquiz;
    Bitmap bitmap;
    private static final int SELECT_PICTURE = 100;
    private View mProgressView;
    TextView txtprogress;
    static boolean calledAlready=false;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsreal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(!calledAlready)
        {
            try {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                calledAlready = true;
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Exception occured", Toast.LENGTH_SHORT).show();
                Log.e("exfire",e.getMessage().toString());
            }
        }

        Firebase.setAndroidContext(this);

      //DatabaseReference dbrefc=FirebaseDatabase.getInstance().getReference("chats");
       // dbrefc.keepSynced(true);
        mProgressView = findViewById(R.id.simpleProgressBar);
        txtprogress= (TextView) findViewById(R.id.txtprogress);


        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mProgressView.animate().setDuration(shortAnimTime);

        mProgressView.setVisibility(View.VISIBLE);
        txtprogress.setVisibility(View.VISIBLE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://brucecompany-dca0f.appspot.com/");
        pref=getApplicationContext().getSharedPreferences("regd",0);
        usernames=pref.getString("username",null);
        userKey=pref.getString("ukey",null);
 recyclerView= (RecyclerView) findViewById(R.id.chatsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(null);
        imgquiz= (ImageView) findViewById(R.id.imgquiz);
        dbref=FirebaseDatabase.getInstance().getReference();
        chatsReals=new ArrayList<>();
        edtchat= (EditText) findViewById(R.id.edtFeed);
        sendchat= (Button) findViewById(R.id.btnfeedSend);
     //   bitmap=null;
        btncameraSend= (Button) findViewById(R.id.btncameraSend);
        btncameraSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );
            }
        });
        sendchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtchat.getText().length()==0||edtchat.getText().toString().equals(""))
                {
edtchat.setError("please enter message to send");
                    edtchat.requestFocus();
                }
                else {
                    sedtchat = edtchat.getText().toString();
                    edtchat.setText("");
                    //  imgquiz.setVisibility(View.GONE);

                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                    if (imgquiz.getDrawable() != null) {
                        Random rnd = new Random();
                        final int randoms = 100000 + rnd.nextInt(900000);
                        StorageReference myfileRef = storageRef.child(randoms + "quizimg.jpg");
                        imgquiz.setDrawingCacheEnabled(true);
                        imgquiz.buildDrawingCache();
                        bitmap = imgquiz.getDrawingCache();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        imgquiz.setImageDrawable(null);
                        imgquiz.setBackground(null);
                        imgquiz.destroyDrawingCache();
                        UploadTask uploadTask = myfileRef.putBytes(data);

uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        Toast.makeText(ChatsrealActivity.this,  taskSnapshot.getBytesTransferred()+"", Toast.LENGTH_SHORT).show();

    }
});
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(ChatsrealActivity.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                                Log.i("errorx", exception.getMessage());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(ChatsrealActivity.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String DOWNLOAD_URL = downloadUrl.getPath();
                                Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                                Log.d("Downx", DOWNLOAD_URL + "\n" + downloadUrl);
                                photourl = downloadUrl.toString();
                                Toast.makeText(ChatsrealActivity.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                                userId = dbref.push().getKey();
                                Date today = Calendar.getInstance().getTime();
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                                final String reportDate = df.format(today);
                                ChatsReal chatsReal = new ChatsReal(userKey, photourl, reportDate, sedtchat);
                                dbref.child("chats").child(userId).setValue(chatsReal);
                            }
                        });
                    } else {
                        userId = dbref.push().getKey();
                        Date today = Calendar.getInstance().getTime();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                        final String reportDate = df.format(today);
                        ChatsReal chatsReal = new ChatsReal(userKey, "", reportDate, sedtchat);
                        dbref.child("chats").child(userId).setValue(chatsReal).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatsrealActivity.this, "Chats added succesfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });//.child("chats")

     /*   DatabaseReference cons=FirebaseDatabase.getInstance().getReference(".info/connected");
        cons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected=dataSnapshot.getValue(Boolean.class);
                if (connected)
                {
                    Toast.makeText(ChatsrealActivity.this,"connected",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ChatsrealActivity.this,"Notconnected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ChatsReal, chatsrealViewHolder>(ChatsReal.class, R.layout.chatsreal_activity,chatsrealViewHolder.class,dbref.child("chats")) {
    @Override
    protected void populateViewHolder(final chatsrealViewHolder viewHolder, final ChatsReal model, int position) {
        mProgressView.setVisibility(  View.GONE);
        txtprogress.setVisibility(View.GONE);
        viewHolder.chattext.setText(model.getChattext());
        viewHolder.time.setText(model.getTime());
        viewHolder.username.setText("@bruce");
if(model.getUsername()!=null) {

   /* dbref.child("userdatas").child(model.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Googleregister googleregister = dataSnapshot.getValue(Googleregister.class);
            viewHolder.username.setText(googleregister.getUsername());

            if (googleregister.getPhoto().equals("") || googleregister.getPhoto().length() == 0) {
                Picasso.with(getApplicationContext())

                        .load(R.drawable.fit)
                        .into(viewHolder.profilepic);
            } else {
                Picasso.with(getApplicationContext())

                        .load(googleregister.getPhoto())
                        .into(viewHolder.profilepic);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });*/
}
if (model.getChatimg().equals("")||model.getChatimg().length()==0) {
    viewHolder.chatimg.setVisibility(View.GONE);

}
else {
    Picasso.with(getApplicationContext())

            .load(model.getChatimg())
            .placeholder(R.drawable.logo_googleg_color_18dp)
            .into(viewHolder.chatimg);
}

        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {


            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);


                int friendlyMessageCount =firebaseRecyclerAdapter.getItemCount();
                int lastVisiblePosition =
                       new LinearLayoutManager(getApplicationContext()).findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        Log.e("chatx",model.getChatimg()+"\n"+model.getChattext());
        dbref.child("chats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("datas",dataSnapshot.toString()+"\n"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
};
recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imgquiz.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
