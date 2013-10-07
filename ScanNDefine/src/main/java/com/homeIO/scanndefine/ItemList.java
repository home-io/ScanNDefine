package com.homeIO.scanndefine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by justin on 10/3/13 for homeIO Technologies!
 */
public class ItemList extends Activity {

    private List<List_Item> itList = new ArrayList<List_Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shop_list);

        populateShopList();
        populateListView();
    }


    private void populateShopList() {

        itList.add(new List_Item("Carrot", R.drawable.carrot));
        itList.add(new List_Item("Apple", R.drawable.apple));
        itList.add(new List_Item("Toilet Paper", R.drawable.tp));


    }
    private void populateListView() {

        ArrayAdapter<List_Item> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.shopListView);
        list.setAdapter(adapter);


    }

    private class MyListAdapter extends ArrayAdapter<List_Item> {
        private MyListAdapter() {
            super(ItemList.this, R.layout.item_view, itList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            // find item to work with
            List_Item currentItem = itList.get(position);

            // fill the view with Icon
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentItem.getIconID());

            // fill Description
            TextView descText = (TextView) itemView.findViewById(R.id.item_txtDesc);
            descText.setText(currentItem.getDesc());

            // fill quantity
            TextView qText = (TextView) itemView.findViewById(R.id.item_txtQuantity);
            qText.setText("Quantity: 1");

            return itemView;
            //return super.getView(position, convertView, parent);
        }
    }

}
