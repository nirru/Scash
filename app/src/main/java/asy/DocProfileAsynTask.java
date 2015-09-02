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
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class DocProfileAsynTask extends AsyncTask<String, String, String> {

    private ProgressDialog mProgressDialog;
    private Context context;
    private SQLiteDatabase database;
    ArrayList<String> myStrings;

    private boolean isLoaded = false;

    String url;
    SharedPreferences.Editor editor;
    String doc_email;
    SharedPreferences share_pre;
    TextView localTextView0,localTextView1, localTextView2, localTextView3,
     localTextView4, localTextView5, localTextView6,
     localTextView7, localTextView8, localTextView9;

    String result = "";
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;

    public DocProfileAsynTask(Context context, String url, ProgressDialog mProgressDialog,SharedPreferences share_pre,TextView localTextView0,
                              TextView localTextView1,TextView localTextView2, TextView localTextView3,
                              TextView localTextView4,TextView localTextView5,TextView localTextView6,
                              TextView localTextView7,TextView localTextView8,TextView localTextView9) {
        this.context = context;
        this.url = url;
        this.mProgressDialog = mProgressDialog;
        this.localTextView0 = localTextView0;
        this.localTextView1 = localTextView1;
        this.localTextView2 = localTextView2;
        this.localTextView3 = localTextView3;
        this.localTextView4 = localTextView4;
        this.localTextView5 = localTextView5;
        this.localTextView6 = localTextView6;
        this.localTextView7 = localTextView7;
        this.localTextView8 = localTextView8;
        this.localTextView9 = localTextView9;
        this.share_pre = share_pre;

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
            result = new Fetch_data().executeHttpGet(url);
            jsonObject = new JSONObject(result);
            Log.e("JSONNN", "" + jsonObject.getString("status"));
            if (!(jsonObject.getString("status")).equals("1")) {
                isLoaded = false;
            }
            else {
                isLoaded = true;
            }


            jsonArray = jsonObject.getJSONArray("data");



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
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.e("IIIIII", "" + i);
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    Log.e("TAG BANE", "" + jsonObject2.getString("email"));
                    if (this.share_pre.getString("doc_email", null).equals(
                            jsonObject2.getString("email"))) {
                        Log.e("TAG BANE", "" + jsonObject2.getString("name"));
                        localTextView0.setText(jsonObject2.getString("name"));
                        localTextView1.setText(jsonObject2.getString("email"));
                        localTextView2.setText(jsonObject2
                                .getString("specification"));
                        localTextView3.setText(jsonObject2.getString("address"));
                        localTextView4.setText(jsonObject2.getString("hospital"));
                        localTextView5.setText(jsonObject2.getString("tariff"));
                        localTextView6.setText(jsonObject2.getString("start_date"));
                        localTextView7.setText(jsonObject2.getString("end_date"));
                        localTextView8.setText(jsonObject2.getString("start_time"));
                        localTextView9.setText(jsonObject2.getString("end_time"));
                    } else{
                        continue;
                    }

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
