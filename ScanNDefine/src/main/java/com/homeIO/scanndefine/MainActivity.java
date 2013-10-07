package com.homeIO.scanndefine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


public class MainActivity extends Activity {

    public final static String MainExtras = "com.homeIO.scanndefine.MESSAGE";
    public final static String UPCCODE = "com.homeIO.scanndefine.BARCODE";
    public final static String showITEMS = "com.homeIO.scanndefine.ITEMMSG";


    public static final String PREFS_NAME = "MyPrefsFile";



    int HaveBarcode = 0;
    String strUPC = "";
    String slice = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Setting the policy enables this application in Android Versions > 3.0
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        // Extra things to do after basic setup
        new Asyncer().execute("some string");

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("silentMode", false);
        //ToastMSG(Boolean.toString(silent));

    }

    @Override
    public void onBackPressed() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", true);
        // Commit the edits!
        editor.commit();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("silentMode", true);

        // Commit the edits!
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void openLIST(View view) {

        // Show intent to open List Activity and GO!
        Intent intent_ITEMS = new Intent(this, ItemList.class);
        String strList = "message";
        intent_ITEMS.putExtra(showITEMS, strList);
        startActivity(intent_ITEMS);

    }

    public void appEXIT(View view) {
        Toast.makeText(MainActivity.this,"NOT IMPLEMENTED YET",Toast.LENGTH_SHORT).show();
    }

    // END OF ONCREATE

    class Asyncer extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {


            while (HaveBarcode != 1) {
                try {
                    Intent intent_UPC = getIntent();
                    HaveBarcode = intent_UPC.getIntExtra(CameraTestActivity.BarcodeMSG, 0);
                } catch (Exception e) {

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent_UPC = getIntent();
            strUPC = intent_UPC.getStringExtra(CameraTestActivity.UPCpass);
            slice = strUPC.substring(strUPC.indexOf("0") + 1);
            //Toast.makeText(MainActivity.this, slice, Toast.LENGTH_SHORT).show();
            ShowDescription();
        }
    }

    public void ToastMSG(String TOASTY){

            Toast.makeText(MainActivity.this, TOASTY, Toast.LENGTH_SHORT).show();
    }

    public void ShowDescription(){
        // Create new intent to send data to another activity
        Intent intent_SHOW = new Intent(this, HttpExample.class);
        intent_SHOW.putExtra(UPCCODE, slice);
        startActivity(intent_SHOW);
        // end intent snippet
    }

    public void lookupUPC(View view){

        // Create new intent to send data to another activity
        Intent intent = new Intent(this, CameraTestActivity.class);
        String message = "MESSAGE!";
        intent.putExtra(MainExtras, message);
        startActivity(intent);
        // end intent snippet


    }




}
