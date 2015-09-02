package com.oxilo.scash;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ManageEventAdapter;
import asy.Fetch_data;
import holder.ChildItem;
import holder.GroupItem;

public class Doctor_list extends FragmentActivity {
	private GroupItem groupItem;
	private ListView listView;
	private ManageEventAdapter adpapter;

	String url = "http://www.jarvisme.com/doc/view_doc.php";

	/* Error */
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.doc_list_xml);
		init();

	}

	private void init(){
		ProgressDialog progressDialog = new ProgressDialog(Doctor_list.this);
		progressDialog.setMessage("Loading...");
		progressDialog.setCanceledOnTouchOutside(false);
        groupItem = new GroupItem();
		listView = (ListView)findViewById(R.id.listView);

		new SplashAsynTask(Doctor_list.this,url,progressDialog).execute("");
	}



	private void setListviewToAdapter() {
		if (groupItem != null) {
			if (groupItem.items.size()==0){
				Toast.makeText(Doctor_list.this, "No Event Found", Toast.LENGTH_LONG).show();
			}
			else{
				adpapter = new ManageEventAdapter(Doctor_list.this, groupItem);
				listView.setAdapter(adpapter);
			}

		} else {
			Toast.makeText(Doctor_list.this, "No Event Found", Toast.LENGTH_LONG).show();
		}
	}


	public class SplashAsynTask extends AsyncTask<String, String, String> {

		private ProgressDialog mProgressDialog;
		private Context context;
		private SQLiteDatabase database;
		ArrayList<String> myStrings;

		private boolean isLoaded = false;
		String response = "";
		String url;
		HashMap<String,String> hm;
		public SplashAsynTask(Context context, String url,ProgressDialog mProgressDialog) {
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
				response = fetch_data.makePostRequest1(url);
				JSONObject jsonObject = new JSONObject(response);
				if (jsonObject.getInt("status")==1)
				{
					isLoaded = true;
					JSONArray jsonArray = jsonObject.getJSONArray("docList");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);
						 ChildItem childItem = new ChildItem();
						childItem.mDocName = jsonObject1.getString("name");
						childItem.mDocEmailId = jsonObject1.getString("email");
						childItem.mDocAdd = jsonObject1.getString("address");
						childItem.mDocSchStartDate = jsonObject1.getString("start_date");
						childItem.mDocSchEndDate = jsonObject1.getString("end_date");
						childItem.mDocSchStartTime = jsonObject1.getString("start_time");
						childItem.mDocSchEndTime = jsonObject1.getString("end_time");
						childItem.mDocHospital = jsonObject1.getString("hospital");
						childItem.mDocSpecification = jsonObject1.getString("specification");
						childItem.mDocTerrif = jsonObject1.getString("tariff");
						childItem.mDocHoliday = jsonObject1.getString("holiday");
						childItem.mDocImageUrl = jsonObject1.getString("image");
						groupItem.items.add(childItem);
					}
				}
				else {
					isLoaded = false;
				}
			}catch (Exception e){
				e.printStackTrace();
			}

			return  null;

		}

		@Override
		protected void onPostExecute(String unused) {
			// mProgressDialog.dismiss();
			closeDialog();
			if(isLoaded){
				setListviewToAdapter();
			}
			else{
				Toast.makeText(context, "Sorry try again later..", 1)
						.show();
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


//    private void saveDoctor(){
//        Event event = new Event();
//        event.name = editText01.getText().toString();
//        event.email = editText02.getText().toString();
//        event.address = editText03.getText().toString();;
//        event.start_date  = editText04.getText().toString();
//        event.end_date = editText05.getText().toString();
//        event.start_time = editText06.getText().toString();
//        event.end_time = editText07.getText().toString();
//        event.specification = editText08.getText().toString();
//        event.hospital = editText09.getText().toString();
//        event.terrif = editText10.getText().toString();
//
//        event.save();
//    }

	}


}
