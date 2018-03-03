package joslabs.companyx;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppManager extends AppCompatActivity {
    Button btnwarn;
    SharedPreferences pref;
    final  static  String Fungua_URL="http://joslabs.co.ke/josmotos/josregister.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("funga",0);

        btnwarn= (Button) findViewById(R.id.btnfunga);
        btnwarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest=new StringRequest(Request.Method.POST, Fungua_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("orderSuccess", response);
                        try {

                            if (response.toString().equals("1")) {
                                // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));

                                SharedPreferences pref=getApplicationContext().getSharedPreferences("funga",0);
                                SharedPreferences.Editor edit=pref.edit();
                                edit.putString("funga","yes");
                                edit.apply();

                                ErrorPersist();
                            }
                            if (response.toString().equals("0")||response.toString().equals("")) {
                                // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));

                                SharedPreferences pref=getApplicationContext().getSharedPreferences("funga",0);
                                SharedPreferences.Editor edit=pref.edit().clear();

                                edit.apply();
                                ErrorRemoved();
                            }
                            // Toast.makeText(getContext(),"Success1",Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("responceserver",response);






                        } catch (JSONException e) {
                            Log.e("exception ","Exception encoutered ");
                            //  Toast.makeText(getContext(),"Exception encoutered ",Toast.LENGTH_LONG);
                            e.printStackTrace();

                        }
                        //JsonArrayRequest jsonArrayRequest=
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("verrorx",error.getMessage());
                        if (error instanceof NoConnectionError) {
                            // Toast.makeText(getContext(),"No Internet",Toast.LENGTH_LONG).show();

                        }




                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        // params.put(USERNAME,vname1);
                        //  params.put(PASSWORD,pass1);
                        System.out.print("was here");

                        Log.e("paramx",params.toString());
                        return params;
                    }
                };

                int x = 2;// retry count
                stringRequest.setRetryPolicy(new

                        DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                        x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).

                        add(stringRequest);
            }
        });

    }

    private void ErrorRemoved() {
        final AlertDialog dialog = new AlertDialog.Builder(AppManager.this).create();
        dialog.setTitle("Success");
        dialog.setMessage("System error fixed ...we are greatful for using our services and we appreciate when you continue enjoying ourthem");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();

                Intent intent=new Intent(AppManager.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        dialog.setIcon(R.drawable.ic_success);
        dialog.show();

    }

    private void ErrorPersist() {
        final AlertDialog dialog = new AlertDialog.Builder(AppManager.this).create();
        dialog.setTitle("Error");
        dialog.setMessage("System App Manager encountered some error contact App manager joslabs.co.ke or joslabs2012@gmail.com if error persist");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();



            }
        });
        dialog.setIcon(R.drawable.ic_error);
        dialog.show();

    }

}
