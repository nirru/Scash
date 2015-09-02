package com.oxilo.scash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import asy.SplashAsynTask;
import spinneradapter.NothingSelectedSpinnerAdapter;


public class RegistrationActivity extends Activity implements  com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener, com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener{

    EditText firstName, email, password, mobile, address, specality_ed, consultancy_ed,hospital_ed;
            TextView start_date_ed, end_date_ed,start_time_ed,end_time_ed;
    Button btn_signup;
    RadioGroup radioGroup;
    Spinner spinnerMF;
    String url =       "http://www.jarvisme.com/patient/register_patient.php";
    String doc_url =   "http://jarvisme.com/doc/register_doc.php";
    int id1 = 0;

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    int id = 0;

    static final String TAG_CAMERA = "Take Photo";
    static final String TAG_CHOOSE_FROM_LIBRARY = "Choose from Library";
    static final String TAG_CANCEL = "Cancel";
    static final String TAG_ADD_PHOTO = "Add photo";
    static final String TAG_SELECT_FILE = "Select File";
    static final int SELECT_FILE = 1;
    static final int REQUEST_CAMERA = 0;

    private static final String TIME_PATTERN = "HH:mm";
    String  base64ProfilePic = "sdf";
    Bitmap  bitmapProfileLogo;
    String picturePath = "";

    ImageView doc_pic;
    String holiday = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();

        addItemToSpinner();
        addListenerToSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    private void  init(){
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        doc_pic = (ImageView) findViewById(R.id.doc_pic);
        firstName = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.mobile);
        address = (EditText)findViewById(R.id.adress);
        hospital_ed = (EditText)findViewById(R.id.doc_hos);
        specality_ed = (EditText) findViewById(R.id.doc_specify);
        start_date_ed = (TextView) findViewById(R.id.doc_date_st);
        end_date_ed = (TextView) findViewById(R.id.doc_date_en);
        start_time_ed = (TextView) findViewById(R.id.doc_time_st);
        end_time_ed = (TextView) findViewById(R.id.doc_time_end);
        spinnerMF = (Spinner) findViewById(R.id.gender);
        consultancy_ed = (EditText) findViewById(R.id.doc_tariff);
        password = (EditText)findViewById(R.id.password);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        spinnerMF = (Spinner)findViewById(R.id.gender);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id1 == 1 || id1 == 0) {
                    validationForDoctor();
                } else if (id1 == 2) {
                   validationForPateint();
                }


            }
        });

        doc_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    id1 = 1;
                    clearData();
                    showView();
                    mobile.setVisibility(View.GONE);
                    address.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioButton2) {
                    id1 = 2;
                    clearData();
                    hideView();
                    mobile.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                }
            }
        });

        start_date_ed.setOnClickListener(clickListener);
        end_date_ed.setOnClickListener(clickListener);
        start_time_ed.setOnClickListener(clickListener);
        end_time_ed.setOnClickListener(clickListener);
    }

    private HashMap prepareHasMapforDoctor(){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("name",firstName.getText().toString());
        hm.put("email",email.getText().toString());
        hm.put("password",password.getText().toString());
        hm.put("address",address.getText().toString());
        hm.put("start_date", start_date_ed.getText().toString());
        hm.put("end_date", end_date_ed.getText().toString());
        hm.put("start_time", start_time_ed.getText().toString());
        hm.put("end_time", end_time_ed.getText().toString());
        hm.put("specification", specality_ed.getText().toString());
        hm.put("hospital", hospital_ed.getText().toString());
        hm.put("tariff", consultancy_ed.getText().toString());
        hm.put("image", base64ProfilePic);
        hm.put("holiday", holiday.toString().trim());
        return hm;
    }

    private HashMap prepareHasMapforPatient(){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("name", firstName.getText().toString());
        hm.put("lastName", firstName.getText().toString());
        hm.put("userName", firstName.getText().toString());
        hm.put("email", email.getText().toString());
        hm.put("password", password.getText().toString());
        hm.put("address", address.getText().toString());
        hm.put("mobile", mobile.getText().toString());
        return hm;
    }


    private void hideView(){
        doc_pic.setVisibility(View.GONE);
        specality_ed.setVisibility(View.GONE);
        hospital_ed.setVisibility(View.GONE);
        consultancy_ed.setVisibility(View.GONE);
        spinnerMF.setVisibility(View.GONE);
        start_date_ed.setVisibility(View.GONE);
        end_date_ed.setVisibility(View.GONE);
        start_time_ed.setVisibility(View.GONE);
        end_time_ed.setVisibility(View.GONE);
    }

    private void showView(){
        doc_pic.setVisibility(View.VISIBLE);
        specality_ed.setVisibility(View.VISIBLE);
        hospital_ed.setVisibility(View.VISIBLE);
        consultancy_ed.setVisibility(View.VISIBLE);
        spinnerMF.setVisibility(View.VISIBLE);
        start_date_ed.setVisibility(View.VISIBLE);
        end_date_ed.setVisibility(View.VISIBLE);
        start_time_ed.setVisibility(View.VISIBLE);
        end_time_ed.setVisibility(View.VISIBLE);
    }

    private void clearData(){
        firstName.getText().clear();
        email.getText().clear();
        password.getText().clear();
        specality_ed.getText().clear();
        hospital_ed.getText().clear();
        consultancy_ed.getText().clear();
        hospital_ed.getText().clear();
        start_date_ed.clearComposingText();
        end_date_ed.clearComposingText();
        start_time_ed.clearComposingText();
        end_time_ed.clearComposingText();
        mobile.clearComposingText();

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
                start_date_ed.setText(dateFormat.format(calendar.getTime()));
                break;
            case 2:
                end_date_ed.setText(dateFormat.format(calendar.getTime()));
                break;
            case 3:
                start_time_ed.setText(timeFormat.format(calendar.getTime()));
                break;
            case 4:
                end_time_ed.setText(timeFormat.format(calendar.getTime()));
                break;
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.doc_date_st:
                    id = 1;
                    DatePickerDialog.newInstance(RegistrationActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                    break;
                case R.id.doc_date_en:
                    id = 2;
                    DatePickerDialog.newInstance(RegistrationActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                    break;
                case R.id.doc_time_st:
                    id = 3;
                    TimePickerDialog.newInstance(RegistrationActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    break;
                case R.id.doc_time_end:
                    id = 4;
                    TimePickerDialog.newInstance(RegistrationActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    break;


            }
        }
    };

    private void validationForDoctor() {

        if (firstName.getText().toString().equals("")){
            firstName.setError("Please enter your name");
            return;
        }
        else if (email.getText().toString().equals("")){
            email.setError("Please enter your Email");
            return;
        }
        else if(!checkEmailPattern()){
            return;
        }
        else if(password.getText().toString().equals("")){
            password.setError("Please enter your password");
            return;
        }
        else if(specality_ed.getText().toString().equals("")){
            specality_ed.setError("Please enter your specality");
            return;
        }
        else if(hospital_ed.getText().toString().equals("")){
            hospital_ed.setError("Please enter hospital name");
            return;
        }
        else if(consultancy_ed.getText().toString().equals("")){
            consultancy_ed.setError("Please enter your consultancy fee");
            return;
        }
        else if(start_date_ed.getText().toString().equals("")){
            start_date_ed.setError("Please enter your start date");
            return;
        }
        else if(end_date_ed.getText().toString().equals("")){
            end_date_ed.setError("Please enter your end date");
            return;
        }
        else if(start_time_ed.getText().toString().equals("")){
            end_date_ed.setError("Please enter your schdule start time");
            return;
        }
        else if(end_time_ed.getText().toString().equals("")){
            end_time_ed.setError("Please enter your schdule close time");
            return;
        }
        else{
            ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            new SplashAsynTask(RegistrationActivity.this, doc_url, progressDialog, prepareHasMapforDoctor()).execute("");
        }

    }

    private void validationForPateint(){
        if (firstName.getText().toString().equals("")){
            firstName.setError("Please enter your name");
            return;
        }
        else if (email.getText().toString().equals("")){
            email.setError("Please enter your Email");
            return;
        }
        else if(!checkEmailPattern()){
            return;
        }
        else if(password.getText().toString().equals("")){
            password.setError("Please enter your password");
            return;
        }
        else if(address.getText().toString().equals("")){
            address.setError("Please enter your address");
            return;
        }
        else if(mobile.getText().toString().equals("")){
            mobile.setError("Please enter your mobile number");
            return;
        }else{
            ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            new SplashAsynTask(RegistrationActivity.this,url,progressDialog,prepareHasMapforPatient()).execute("");
        }

    }

    public boolean checkEmailPattern() {
        Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}"
                + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" + ")+");
        if (!EMAIL_PATTERN.matcher(email.getText().toString())
                .matches()) {
            email.setError("Please enter the correct email");
            return false;
        }
        return true;
    }

    private void selectImage() {
        final CharSequence[] items = {TAG_CAMERA, TAG_CHOOSE_FROM_LIBRARY,
                TAG_CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(
                RegistrationActivity.this);
        builder.setTitle(TAG_ADD_PHOTO);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(TAG_CAMERA)) {
                    takePictureFromCamera();
                } else if (items[item].equals(TAG_CHOOSE_FROM_LIBRARY)) {
                    takePictuureFromGallery();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void takePictuureFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
//        intent.putExtra("imageId", imageID);
        startActivityForResult(Intent.createChooser(intent, TAG_SELECT_FILE),
                SELECT_FILE);
    }

    private void takePictureFromCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra("imageId", imageID);
        startActivityForResult(
                Intent.createChooser(cameraIntent, "TAG_SELECT_FILE"),
                REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.e("tag","" + imageid);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
//				Log.e("IMAGE NUMBER", "" + i);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                saveImageToImageView(photo);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                getImageFromgallery(selectedImageUri);
            }
        }
    }

    private void saveImageToImageView(Bitmap bitMapPhoto) {
        bitmapProfileLogo = bitMapPhoto;
        doc_pic.setImageBitmap(bitmapProfileLogo);
        ConvertBitmapToBAse64String(bitmapProfileLogo);

    }

    private void getImageFromgallery(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        Log.e("PICTURE PATH", "" + picturePath);
        cursor.close();

        bitmapProfileLogo = RegistrationActivity
                .decodeSampledBitmapFromResource(picturePath, 100, 100);
        doc_pic.setImageBitmap(bitmapProfileLogo);
        ConvertBitmapToBAse64String(bitmapProfileLogo);

        if (bitmapProfileLogo != null && !bitmapProfileLogo.isRecycled()) {
            // bitmapBussinessLogo.recycle();
            bitmapProfileLogo = null;
        }
    }



	/*----------- Android convert image to Base64 String ---------*/

    private void ConvertBitmapToBAse64String(Bitmap photo) {
        if (photo != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (picturePath.contains(".png")){
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            }
            else {
                photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            }

            byte[] byteArray = byteArrayOutputStream .toByteArray();
            base64ProfilePic = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            base64ProfilePic = "vfg";
        }

        Log.e("BASE64","" + base64ProfilePic);
    }

	/* reduced Imaage without lossing its quality */

    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }



    /**
     * An inner class that will that handle
     * Spinner Selection event
     *
     */

    public class SystemSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerMF.setSelection(position);
            switch (position) {
                case 0:
                    holiday = "Monday";
                    break;
                case 1:
                    holiday = "Tuesday";
                    break;
                case 2:
                    holiday = "Wednesday";
                    break;
                case 3:
                    holiday = "Thrusday";
                    break;
                case 4:
                    holiday = "Friday";
                    break;
                case 5:
                    holiday = "Saturday";
                    break;
                case 6:
                    holiday = "Sunday";
                    break;
                default:
                    break;
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void addItemToSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_aray, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMF.setPrompt(getResources().getString(R.string.spinner_prompt));

        spinnerMF.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected, this));
    }

    private void addListenerToSpinner(){
        spinnerMF.setOnItemSelectedListener(new SystemSpinner());
    }
}
