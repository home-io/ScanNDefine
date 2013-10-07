package com.homeIO.scanndefine;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 *  Created by Justin Gross on 9/29/13 for homeIO Technologies
 */
public class HttpExample extends Activity {

    TextView httpStuff;
    String strUPC;
    public final static String Item4Add = "com.homeIO.scanndefine.ITEMMSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the policy enables this application in Android Versions > 3.0

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.httpex);
        httpStuff = (TextView) findViewById(R.id.tvHttp);
        doLookup();
    }


    public void doLookup() {

        Intent intent_UPC = getIntent();
        strUPC = intent_UPC.getStringExtra(MainActivity.UPCCODE);

                //--- Where the Magic Happens!
                GetMethodEx test = new GetMethodEx();

                test.setUPC(strUPC);


                String returned = null;
                try {
                    returned = test.getInternetData();
                     //returned = "Description:...................Test Case Scenario..................Weight";
                    String slice = returned.substring(returned.indexOf("Description") + 20, returned.lastIndexOf("Issuing") - 4);

                    String dice = slice.replaceAll("(?i)<t.[^>]*>", "").replaceAll("\\s+", " ").trim();

                    String splice = dice.replaceAll("(?i)</t.[^>]*>", "").replaceAll("\\s+", " ").trim();

                    String strife = splice.replaceAll("Weight", "Weight ").replaceAll("\\s+", " ").trim();

                    String description = strife.replaceAll("&amp;", "").replaceAll("\\s+", " ").trim();

                    httpStuff.setText(description);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

    public void openWEB(View v) {

        String WebPage = "http://www.upcdatabase.com/item/" + strUPC;
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri u = Uri.parse(WebPage);

        try {
            // Start the activity
            i.setData(u);
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Raise on activity not found
            Toast.makeText(this, "Browser not found.", Toast.LENGTH_SHORT).show();
        }
    }


    public void add2List(View v) {
        // Show intent to open List Activity and GO!
        Intent intent_LIST = new Intent(this, ItemList.class);
        String strList = "message";
        intent_LIST.putExtra(Item4Add, strList);
        startActivity(intent_LIST);
    }
}
