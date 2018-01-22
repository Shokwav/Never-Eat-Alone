package com.nevereatalone.group.nevereatalone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    /* Whether or not to display the restaurant viewer */
    static boolean doViewer = false;

    /* The places manager */
    PlacesManager placesManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        /* Set toolbar */
        Toolbar toolbar = (Toolbar)(findViewById(R.id.toolbar));
        super.setSupportActionBar(toolbar);

        /* Get the drawer */
        DrawerLayout drawer = (DrawerLayout)(findViewById(R.id.drawer_layout));

        /* Create toggle button & set it as a listener for the drawer */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* Get the NavigationView */
        NavigationView navigationView = (NavigationView)(findViewById(R.id.nav_view));
        navigationView.setNavigationItemSelectedListener(this);

        /* Create Google Places manager */
        placesManager = new PlacesManager(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        /* Start the Activity w/ Intent */
        if(doViewer){
            startActivity(new Intent(getApplicationContext(), PlaceViewer.class));
            finish();
        }
    }

    /* When the back button is pressed, close the drawer (if open) */
    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)(findViewById(R.id.drawer_layout));
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        /* Inflate the menu; this adds items to the action bar if it is present */
        getMenuInflater().inflate(R.menu.main, menu);

        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if(id == R.id.action_settings){
            return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        //Handle navigation view item clicks here
        switch(item.getItemId()){
            case (R.id.nav_camera): break;
            case (R.id.nav_send): break;
            default: break;
        }

        DrawerLayout drawer = (DrawerLayout)(findViewById(R.id.drawer_layout));
        drawer.closeDrawer(GravityCompat.START);
        return (true);
    }

    public void onRequestPermissionsResult(int request, String permissions[], int[] results){
        /* If the results array is null, the request has been cancelled */
        if(results != null && results.length > 0){
            if(request == PlacesManager.PERMISSION_REQUEST_LOCATION){
                if(results[0] == PackageManager.PERMISSION_GRANTED){
                    new Thread(){
                        public void run(){
                            placesManager.update();
                        }
                    }.run();
                }else{
                    throw new RuntimeException("Permission denied");
                }
            }
        }
    }
}
