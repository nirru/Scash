package com.oxilo.scash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.logging.Log;

import java.util.HashMap;

import asy.LoginAsynTask;
import asy.PateintAsynTask;


public class LoginActivity extends Activity {

 EditText email, password;
    Button login;
    TextView text_forgotpwd, textSignUp;
    String url = "http://jarvisme.com/doc/login_doc.php";
    String url1 = "http://www.jarvisme.com/patient/login_patient.php";
    RadioGroup radioGroup;
    int id1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        email = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btn_login);
        textSignUp = (TextView)findViewById(R.id.sign_up);
        login.setOnClickListener(l);
        textSignUp.setOnClickListener(l);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                if (checkedId == R.id.radioButton1) {
                    id1 = 1;
                } else if (checkedId == R.id.radioButton2) {
                    id1 = 2;
                }
            }
        });
    }

    private void submit(){
        if (email.getText().toString().equals(""))
        {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Please fill Right email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().toString().equals(""))
        {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Please fill password", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            if (id1 == 1 || id1 == 0){
                new LoginAsynTask(LoginActivity.this,url,progressDialog,prepareHasMap()).execute("");
            }
            else{
                new PateintAsynTask(LoginActivity.this,url1,progressDialog,prepareHasMap()).execute("");
            }

        }
    }

    private HashMap prepareHasMap(){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("email",this.email.getText().toString());
        hm.put("password",this.password.getText().toString());
        return hm;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    submit();
                    break;
                case R.id.sign_up:
                    signUp();
                    break;
            }
        }
    };

    private void signUp(){
        Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return;
    }
}
