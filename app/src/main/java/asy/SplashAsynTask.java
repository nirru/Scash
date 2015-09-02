package asy;

/**
 * Created by ericbasendra on 23/07/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SplashAsynTask extends AsyncTask<String, String, String> {

    private ProgressDialog mProgressDialog;
    private Context context;
    private SQLiteDatabase database;
    ArrayList<String> myStrings;

    private boolean isLoaded = false;

    String url;
    HashMap<String,String> hm;
    public SplashAsynTask(Context context, String url,ProgressDialog mProgressDialog, HashMap<String,String> hm) {
        this.context = context;
        this.url = url;
        this.mProgressDialog = mProgressDialog;
         this.hm = hm;
        Log.e("MAY AYA", "" + url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showDialog();
    }

    @Override
    protected String doInBackground(String... aurl) {

        Log.e("MAY AYA", "" + "do in background");
        Fetch_data fetch_data = new Fetch_data();
        try {
            if (new JSONObject(
                    ((Fetch_data) fetch_data).makePostRequest(hm,this.url))
                    .getInt("status") == 1){
                isLoaded = true;
            }
            else {
                isLoaded = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    @Override
    protected void onPostExecute(String unused) {
        // mProgressDialog.dismiss();
        closeDialog();
        if(isLoaded){
            Toast.makeText(context, "registered successfully", Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();
        }
        else{
            Toast.makeText(context, "Sorry try again later..", Toast.LENGTH_SHORT).show();
        }

    }



    void showDialog() {
        mProgressDialog.setMessage("Please Wait...updating form.");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    void closeDialog() {
        // dismiss pDialog
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
