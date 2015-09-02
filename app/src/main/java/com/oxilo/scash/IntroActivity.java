package com.oxilo.scash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class IntroActivity extends Activity {

    TextView mobile, health, help, faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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

    private void init(){
        mobile = (TextView)findViewById(R.id.mobile_cash);
        health = (TextView)findViewById(R.id.health);
        help = (TextView)findViewById(R.id.help);
        faq = (TextView)findViewById(R.id.faq);

        mobile.setOnClickListener(l);
        health.setOnClickListener(l);
        help.setOnClickListener(l);
        faq.setOnClickListener(l);
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.mobile_cash:
                    login();
                    break;
                case R.id.health:
                    login();
                    break;
                case R.id.help:
                    login();
                    break;
                case R.id.faq:
                    login();
                    break;
            }

        }
    };

    private void login(){
        Intent i = new Intent(IntroActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return;
    }


}
