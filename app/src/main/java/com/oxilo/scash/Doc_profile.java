package com.oxilo.scash;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class Doc_profile extends Activity {

    EditText firstName, email, password, mobile, address, specality_ed, consultancy_ed,hospital_ed, doc_holiday;
    TextView start_date_ed, end_date_ed,start_time_ed,end_time_ed;
    NetworkImageView doc_pic;
    ImageLoader mImageLoader;
    HashMap<String, String> hm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        init();
        if (getIntent() !=null){
            hm = (HashMap<String, String>)getIntent().getSerializableExtra(Doc_profile.this.getResources().getString(R.string.constant_hasmap));
        }

        filledText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doc_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void  init() {
        mImageLoader = CustomVolleyRequestQueue.getInstance(Doc_profile.this.getApplicationContext())
                .getImageLoader();
        doc_pic = (NetworkImageView) findViewById(R.id.doc_pic);
        firstName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.adress);
        hospital_ed = (EditText) findViewById(R.id.doc_hos);
        specality_ed = (EditText) findViewById(R.id.doc_specify);
        start_date_ed = (TextView) findViewById(R.id.doc_date_st);
        end_date_ed = (TextView) findViewById(R.id.doc_date_en);
        start_time_ed = (TextView) findViewById(R.id.doc_time_st);
        end_time_ed = (TextView) findViewById(R.id.doc_time_end);
        doc_holiday = (EditText) findViewById(R.id.doc_holiday);
        consultancy_ed = (EditText) findViewById(R.id.doc_tariff);
        password = (EditText) findViewById(R.id.password);
    }

    private void filledText(){
        if (hm != null){
            doc_pic.setImageUrl(hm.get("image"),mImageLoader);
            firstName.setText(hm.get("name"));
            email.setText(hm.get("email"));
            specality_ed.setText(hm.get("specification"));
            hospital_ed.setText(hm.get("hospital"));
            consultancy_ed.setText(hm.get("tariff"));
            address.setText(hm.get("address"));
            start_date_ed.setText(hm.get("start_date"));
            end_date_ed.setText(hm.get("end_date"));
            start_time_ed.setText(hm.get("start_time"));
            end_time_ed.setText(hm.get("end_time"));
            if (hm.get("holiday") !=null || !(hm.get("holiday").equals("null")))
                doc_holiday.setText(hm.get("holiday"));
            else
                doc_holiday.setVisibility(View.GONE);

        }
    }

}
