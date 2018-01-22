package com.nevereatalone.group.nevereatalone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.*;
import android.support.v7.app.AlertDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import java.util.ArrayList;
import java.util.List;

public class PlacesManager implements GoogleApiClient.OnConnectionFailedListener {
    public class PlaceInfo {
        String name = null;
        String description = null;
        List<Integer> types = null;

        public PlaceInfo(){}

        public PlaceInfo(String name, String desc, List<Integer> types){
            this.name = name;
            this.description = desc;
            this.types = types;
        }
    };

    public static final int PERMISSION_REQUEST_LOCATION = 1;

    /* The Activity the API is associated with */
    private FragmentActivity activity = null;

    /* The Google Places API client */
    private GoogleApiClient apiClient = null;

    /* List of place data */
    public static ArrayList<PlaceInfo> placeList = new ArrayList<PlaceInfo>();

    public PlacesManager(FragmentActivity activity){
        this.activity = activity;
        this.apiClient = new GoogleApiClient.Builder(activity)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(activity, this)
                .build();
        this.apiClient.connect();

        ActivityCompat.requestPermissions(activity, new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_REQUEST_LOCATION);
    }

    public void update(){
        PendingResult<PlaceLikelihoodBuffer> places =
                Places.PlaceDetectionApi.getCurrentPlace(apiClient, null);

        places.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>(){
            public void onResult(PlaceLikelihoodBuffer likelyPlaces){
                placeList.clear();

                for(final PlaceLikelihood current: likelyPlaces){
                    final Place place = current.getPlace();
                    placeList.add(new PlaceInfo(place.getName().toString(),
                            place.getAddress().toString(), place.getPlaceTypes()));
                }

                likelyPlaces.release();

                /* Display success dialog */
                AlertDialog successAlert = new AlertDialog.Builder(activity).create();

                /* Inform the user how many restaraunts are nearby */
                successAlert.setMessage("Finished scanning nearby locations. " +
                        "There are " + placeList.size() + " eligible place(s) nearby. ");

                /* Load food preferences after dialog's dismissal */
                successAlert.setOnDismissListener(
                    new AlertDialog.OnDismissListener(){
                        public void onDismiss(final DialogInterface dialog){
                            /* Start the food preferences activity */
                            activity.startActivity(new Intent(activity.getApplicationContext(),
                                    FoodPreferences.class));
                        }
                    }
                );

                successAlert.show();
            }
        });
    }

    /* Throw an exception if the connection to the Google API failed */
    @Override
    public void onConnectionFailed(ConnectionResult res){
        throw new RuntimeException(res.getErrorMessage());
    }
}
