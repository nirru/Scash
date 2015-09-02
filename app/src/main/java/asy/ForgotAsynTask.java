package asy;

/**
 * Created by ericbasendra on 23/07/15.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;


public class ForgotAsynTask extends AsyncTask<String, String, String> {

    private ProgressDialog mProgressDialog;
    private Context context;
    private SQLiteDatabase database;
    ArrayList<String> myStrings;

    private boolean isLoaded = false;

    String url;
    SharedPreferences.Editor editor;
    String doc_email;
    public ForgotAsynTask(Context context, String url, ProgressDialog mProgressDialog) {
        this.context = context;
        this.url = url;
        this.mProgressDialog = mProgressDialog;
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
                    ((Fetch_data) fetch_data).executeHttpGet(this.url))
                    .getString("status").equals("1")){
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
            Toast.makeText(context, "Your password has been sent on your email account ..", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Please try Again... ", Toast.LENGTH_SHORT).show();
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
