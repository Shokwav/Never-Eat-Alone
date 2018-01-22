package com.nevereatalone.group.nevereatalone;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

/* Populates the restaurant ListView with icon and text
   Icon images taken from iconset:world-flag-icons at https://www.iconfinder.com
 */

public class FoodPreferences extends AppCompatActivity {
    /* ListAdapter for FoodPreferences */
    private class RestarauntListAdapter extends ArrayAdapter<Restaurant> {
        public RestarauntListAdapter(){
            super(FoodPreferences.this, R.layout.item_view, myRestaurants);
        }

        /* Called when the View needs to be retrieved */
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            /* Get current restaurant */
            Restaurant currentRestaurant = myRestaurants.get(position);

            /* Set image resource to current restaurant */
            ImageView imageView = (ImageView) (itemView.findViewById(R.id.item_icon));
            imageView.setImageResource(currentRestaurant.iconID);

            /* Set text to current restaurant */
            TextView typeText = (TextView) (itemView.findViewById(R.id.item_txtType));
            typeText.setText(currentRestaurant.type);

            /* Return item view */
            return (itemView);
        }
    }

    /* list of restaurants */
    private List<Restaurant> myRestaurants = new ArrayList<Restaurant>();

    /* Called when this activity is started */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set content view to food preferences */
        setContentView(R.layout.activity_food_preferences);

        /* Populate food list & adapter */
        populateFoodList();
        populateListView();

        /* Set click listener */
        Button btnConfirm = (Button)(findViewById(R.id.btnConfirm));
        btnConfirm.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                /* Let MainActivity know we want the restaurant viewer */
                MainActivity.doViewer = true;
                finish();
            }
        });
    }

    /* Populate the list of restaurants */
    private void populateFoodList(){
        myRestaurants.add(new Restaurant("American", R.drawable.usa_icon));
        myRestaurants.add(new Restaurant("Jamaican", R.drawable.jamaica_icon));
        myRestaurants.add(new Restaurant("Mexican", R.drawable.mexico_icon));
        myRestaurants.add(new Restaurant("Italian", R.drawable.italy_icon));
        myRestaurants.add(new Restaurant("Greek", R.drawable.greece_icon));
        myRestaurants.add(new Restaurant("Chinese", R.drawable.china_icon));
        myRestaurants.add(new Restaurant("Japanese", R.drawable.japan_icon));
        myRestaurants.add(new Restaurant("Thai", R.drawable.thailand_icon));
        myRestaurants.add(new Restaurant("Vietnamese", R.drawable.vietnam_icon));
    }

    /* Populate list adapter */
    private void populateListView() {
        ArrayAdapter<Restaurant> adapter = new RestarauntListAdapter();
        ListView list = (ListView)(findViewById(R.id.restaurantListView));
        list.setAdapter(adapter);
    }
}
