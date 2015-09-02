package com.oxilo.scash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class AppointmentActivity extends Activity implements  com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener{

    TextView doc_start_date, doc_end_date, doc_start_time, doc_end_time,doc_holiday;
    TextView pat_booking_date, pat_booking_time, pat_fee_to_pay;
    Button book;
    String doctor= "";


    HashMap<String, String> hm = null;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    int id = 0;
    private static final String TIME_PATTERN = "HH:mm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        init();

        if (getIntent() !=null){
            hm = (HashMap<String, String>)getIntent().getSerializableExtra(AppointmentActivity.this.getResources().getString(R.string.constant_hasmap));
            doctor = hm.get("name");
        }

        filledText();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment, menu);
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
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
        doc_start_date = (TextView)findViewById(R.id.doc_start_date);
        doc_end_date = (TextView)findViewById(R.id.doc_end_date);
        doc_start_time = (TextView)findViewById(R.id.doc_start_time);
        doc_end_time = (TextView)findViewById(R.id.doc_end_time);
        doc_holiday = (TextView)findViewById(R.id.holiday);

        pat_booking_date = (TextView)findViewById(R.id.booking_date);
        pat_booking_time = (TextView)findViewById(R.id.booking_time);
        pat_fee_to_pay = (TextView)findViewById(R.id.consultaion_fee);
        book = (Button)findViewById(R.id.book);

        pat_booking_date.setOnClickListener(clickListener);
        pat_booking_time.setOnClickListener(clickListener);
        book.setOnClickListener(clickListener);

    }

    private void doValidationAndAction(){
        if (pat_booking_date.getText().toString().equals("")){
            showOKAleart(AppointmentActivity.this,"Error","Kindly enter the date on which you want the booking");
            return;
        }
        else if(pat_booking_time.getText().toString().equals("")){
            showOKAleart(AppointmentActivity.this, "Error", "Kindly enter the time on which you want to visit the doctor");
            return;
        }
        else{
            Toast.makeText(AppointmentActivity.this,"You appoitment is sent to " + doctor + ". You will shortly recieve a confirmation mail from " + doctor,Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void filledText(){
        if (hm != null){
            doc_start_date.setText("Opening Date \n" + hm.get("start_date"));
            doc_end_date.setText("Closing Date \n" + hm.get("end_date"));
            doc_start_time.setText("Opening Time \n" + hm.get("start_time"));
            doc_end_time.setText("Closing Time \n" + hm.get("end_time"));
            if (hm.get("holiday") !=null || !(hm.get("holiday").equals("null")))
            doc_holiday.setText("Holiday \n" + hm.get("holiday"));

            pat_fee_to_pay.setText("Consultation Fee \n" + hm.get("consulation"));
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }

    private void update() {
        switch (id){
            case 1:
                pat_booking_date.setText(dateFormat.format(calendar.getTime()));
                break;
            case 2:
                pat_booking_time.setText(timeFormat.format(calendar.getTime()));
                break;

        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.booking_date:
                    id = 1;
                    DatePickerDialog.newInstance(AppointmentActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                    break;
                case R.id.booking_time:
                    id = 2;
                    TimePickerDialog.newInstance(AppointmentActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    break;
                case R.id.book:
                    doValidationAndAction();
                    break;
            }
        }
    };

    public  void showOKAleart(Context context, String title, String message) {
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle("Message");
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
