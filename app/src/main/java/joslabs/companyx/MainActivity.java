package joslabs.companyx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import joslabs.companyx.analysis.AnalysisActivity;
import joslabs.companyx.chats.ChatsActivity;
import joslabs.companyx.chatsreal.ChatsrealActivity;
import joslabs.companyx.clientlist.ClientlistActivity;
import joslabs.companyx.salesrecords.SalesrecordsActivity;
import joslabs.companyx.viewsperson.Viewsperson;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TodaysSalesfragment.OnFragmentInteractionListener,SalesRecordsfrgment.OnFragmentInteractionListener{

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("admin",0);

        if (savedInstanceState == null) {
            //Fragment fragment = null;
            // savedInstanceState.putString("l","lo");
            Fragment fragment=null;
            Class fragmentClass = null;
            fragmentClass = TabFragment.class;

            //  fragment.setArguments(bundle);
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }


            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
      /*  switch (item.getItemId()){
            case R.id.sign_out_menu:
                //signout
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Handle the camera action
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
         /*     } else if (id == R.id.nav_share) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", "oscar@gmail.com", null));
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Doctor Fitness");
                    String sAux = "Lets get kickassbodies.. Install the app using this link http://joslabs.co.ke.com/josmotos/doctorfitness.apk ";
                    // sAux = sAux + "\nDownload the app from  playstore using this link https://play.google.com/store/search?q=BeTheChangeKe&hl=en";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Project beach-body,  lets go!"));


                } catch (Exception e) {

                }*/
            } else if (id == R.id.nav_register) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_analysis) {
                Intent intent = new Intent(getApplicationContext(), AnalysisActivity.class);
                startActivity(intent);
           /* } else if (id == R.id.nav_clientlist) {
                Intent intent = new Intent(getApplicationContext(), ClientlistActivity.class);
                startActivity(intent);*/
            } else if (id == R.id.nav_checkrep) {
                Intent intent = new Intent(getApplicationContext(),Viewsperson.class);
                startActivity(intent);
            } else if (id == R.id.nav_salesrecord) {
                Intent intent = new Intent(getApplicationContext(), SalesrecordsActivity.class);
                startActivity(intent);


            } else if (id == R.id.nav_chat) {
                Intent intent = new Intent(getApplicationContext(), ChatsrealActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_about) {

               // Intent intent = new Intent(getApplicationContext(), mapsActivity.class);
              //  startActivity(intent);
            }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
