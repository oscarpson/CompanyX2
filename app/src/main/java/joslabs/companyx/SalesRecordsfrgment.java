package joslabs.companyx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import joslabs.companyx.productdistribution.BuyingProduct;

import static android.content.SharedPreferences.Editor;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SalesRecordsfrgment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SalesRecordsfrgment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesRecordsfrgment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner sname,snumber,spnmode;
    List<String> spinneritem = new ArrayList<String>();//spn item
    List<String> spnnumber = new ArrayList<String>();
    List<String>pmode= new ArrayList<String>();//time
    String itemName,number,mode,Paid,userKey;

    final  static  String SALES_URL="http://joslabs.co.ke/josmotos/intosales.php";
    final  static  String Funga_URL="http://joslabs.co.ke/josmotos/josregister.php";
   // final  static  String Fungua_URL="http://joslabs.co.ke/josmotos/josregister.php";
    ArrayAdapter<String> adapter ;
    ArrayAdapter<String> bb ;
    ArrayAdapter<String> modes ;

DatabaseReference dbref;

    EditText cost,discout,clientname;
    Button btnsubmit;
    CheckBox chkbpaid,chkbnotpaid;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SalesRecordsfrgment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesRecordsfrgment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesRecordsfrgment newInstance(String param1, String param2) {
        SalesRecordsfrgment fragment = new SalesRecordsfrgment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sales_recordsfrgment, container, false);
        /*sname= (Spinner) view.findViewById(R.id.spnitemname);
        snumber= (Spinner) view.findViewById(R.id.spnnumber);
        spnmode= (Spinner) view.findViewById(R.id.spnmodepayment);

        cost= (EditText) view.findViewById(R.id.edtprices);
        discout= (EditText) view.findViewById(R.id.edtdisc);
        clientname= (EditText) view.findViewById(R.id.edtclientname);

        dbref= FirebaseDatabase.getInstance().getReference();
        chkbpaid= (CheckBox) view.findViewById(R.id.chkpaid);
        chkbnotpaid= (CheckBox) view.findViewById(R.id.chknotpaid);
        if (itemName!=null) {

            if (itemName.matches("product A")) {
                int kk = Integer.parseInt(number) * 2000;
                cost.setText(kk);
            }
            if (itemName.matches("product B")) {
                int kk = Integer.parseInt(number) * 3000;
                cost.setText(kk);
            }
            if (itemName.matches("product C")) {
                int kk = Integer.parseInt(number) * 4000;
                cost.setText(kk);
            }
            if (itemName.matches("product D")) {
                int kk = Integer.parseInt(number) * 7000;
                cost.setText(kk);
            }
        }
        chkbpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (chkbpaid.isChecked())
               {
                   chkbnotpaid.setChecked(false);
                   Paid="1";
               }
            }
        });
        chkbnotpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkbnotpaid.isChecked())
                {
                    chkbpaid.setChecked(false);
                    Paid="0";
                }
            }
        });

        btnsubmit= (Button) view.findViewById(R.id.btnsubmmit);



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String costs=cost.getText().toString();
                final String discouts=discout.getText().toString();
                final String clientnames=clientname.getText().toString();


               userKey= dbref.push().getKey();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                Date today = Calendar.getInstance().getTime();
                final String reportDate = df.format(today);
                BuyingProduct buyingProduct=new BuyingProduct(clientnames,itemName,number,costs,discouts,mode,Paid,reportDate,"receipt");
                dbref.child("Productsell").child(userKey).setValue(buyingProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "product added successfully", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });


        adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinneritem);
        bb = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spnnumber);
        modes = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, pmode);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        modes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       spinneritem.add("product A");
        spinneritem.add("product B");
        spinneritem.add("product C");
        spinneritem.add("product D");
        sname.setAdapter(adapter);

        spnnumber.add("1");
        spnnumber.add("2");
        spnnumber.add("3");
        spnnumber.add("4");
        spnnumber.add("5");

        snumber.setAdapter(bb);

        pmode.add("Mpesa");
        pmode.add("Cash");
        pmode.add("Bank");

        spnmode.setAdapter(modes);

        sname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemName=sname.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnmode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mode=spnmode.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        snumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                number=snumber.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
        fungaApp();
        return  view;
    }

    private void fungaApp() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Funga_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("orderSuccess", response);
                try {

                    if (response.toString().equals("1")) {
                        // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));

                        SharedPreferences pref=getActivity().getSharedPreferences("funga",0);
                        SharedPreferences.Editor edit=pref.edit();
                        edit.putString("funga","yes");
                        edit.apply();
                    }
                    if (response.toString().equals("0")||response.toString().equals("")) {
                        // dbHelper.addUser(new User( jnames,jmodels,"true",null,null,null, jtypes,null));

                        SharedPreferences pref=getActivity().getSharedPreferences("funga",0);
                        SharedPreferences.Editor edit=pref.edit().clear();

                        edit.apply();
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
              //  Log.e("verrorx",error.getMessage());
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
        Volley.newRequestQueue(getContext()).

                add(stringRequest);

    }

    private void showSuceessDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setTitle("success");
        dialog.setMessage("Your sales info have been updated");
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();


            }
        });
        dialog.setIcon(R.drawable.ic_tick);
        dialog.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
