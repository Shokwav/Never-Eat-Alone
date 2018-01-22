package com.nevereatalone.group.nevereatalone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.location.places.Place;
import java.util.ArrayList;
import java.util.List;

public class PlaceViewer extends AppCompatActivity {
    private class PlaceListAdapter extends ArrayAdapter<PlacesManager.PlaceInfo> {
        public PlaceListAdapter(){
            super(PlaceViewer.this, R.layout.place_view, myPlaces);
        }

        /* Called when a view is needed */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.place_view, parent, false);
            }

            /* The current place */
            PlacesManager.PlaceInfo currentPlace = myPlaces.get(position);

            /* The place description */
            TextView infoText = (TextView)(itemView.findViewById(R.id.txtPlaceName));
            infoText.setText("\"" + currentPlace.name + "\" (" + currentPlace.description + ")");

            /* Return the item view */
            return (itemView);
        }
    }

    private List<PlacesManager.PlaceInfo> myPlaces = new ArrayList<PlacesManager.PlaceInfo>();

    /* Called when the activity is first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_viewer);

        populatePlaceList();
        populateListView();

        /* Set click listener */
        Button btnConfirm = (Button)(findViewById(R.id.btnConfirmPlace));
        btnConfirm.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                /* Let MainActivity know we don't want the restaurant viewer */
                MainActivity.doViewer = false;
                finish();
            }
        });
    }

    /* Populate the place list */
    private void populatePlaceList(){
        ArrayList<PlacesManager.PlaceInfo> places = new ArrayList<PlacesManager.PlaceInfo>();
        for(PlacesManager.PlaceInfo place: PlacesManager.placeList){
            if(place.types.contains(Place.TYPE_RESTAURANT)){
                places.add(place);
            }
        }

        this.myPlaces = places;
    }

    /* Populate the list adapter */
    private void populateListView() {
        ArrayAdapter<PlacesManager.PlaceInfo> adapter = new PlaceViewer.PlaceListAdapter();
        ListView list = (ListView)(findViewById(R.id.placeListView));
        list.setAdapter(adapter);
    }
}
