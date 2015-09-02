package asy;

/**
 * Created by ericbasendra on 23/07/15.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.oxilo.scash.Doc_profile;
import com.oxilo.scash.Doctor_list;
import com.oxilo.scash.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginAsynTask extends AsyncTask<String, String, String> {

    private ProgressDialog mProgressDialog;
    private Context context;

    private boolean isLoaded = false;

    String url;
    HashMap<String,String> hm;
    String response = "";
    public LoginAsynTask(Context context, String url, ProgressDialog mProgressDialog, HashMap<String,String> hm) {
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
            response = fetch_data.makePostRequest(hm,url);
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getInt("status")==1)
           {
                isLoaded = true;
                JSONArray jsonArray = jsonObject.getJSONArray("DocList");
               for (int i = 0; i < jsonArray.length(); i++) {
                   JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                   prepareHasMap(jsonObject1);
               }
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
            Intent intent = new Intent(context, Doc_profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(context.getResources().getString(R.string.constant_hasmap), hm);
            context.startActivity(intent);
            return;
        }
        else{
            Toast.makeText(context, "Please fill the right Username and Password... ", Toast.LENGTH_SHORT).show();
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


    private HashMap prepareHasMap(JSONObject jsonObject){
        try {
            hm.put("name",jsonObject.getString("name").toString());
            hm.put("email",jsonObject.getString("email").toString());
            hm.put("address",jsonObject.getString("address").toString());
            hm.put("start_date",jsonObject.getString("start_date").toString());
            hm.put("end_date",jsonObject.getString("end_date").toString());
            hm.put("start_time",jsonObject.getString("start_time").toString());
            hm.put("end_time",jsonObject.getString("end_time").toString());
            hm.put("specification",jsonObject.getString("specification").toString());
            hm.put("hospital",jsonObject.getString("hospital").toString());
            hm.put("tariff",jsonObject.getString("tariff").toString());
            hm.put("holiday",jsonObject.getString("holiday").toString());
            hm.put("image",jsonObject.getString("image").toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return hm;
    }


}
