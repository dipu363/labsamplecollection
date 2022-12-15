package lab.com.sa.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import lab.com.sa.R;
import lab.com.sa.data.model.TestSampleModel;
import lab.com.sa.util.AppData;
import lab.com.sa.util.SQLiteDB;

public class AddTestSampleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText pName,pId,pPhone,pAddress,testName;
    Button btnSave;
    ImageButton btnScan,btnMap;

    AppData appData;
    SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_sample);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lab Test Info");

        pId = findViewById(R.id.edit_patient_id);
        pName = findViewById(R.id.edit_pname_id);
        pPhone = findViewById(R.id.edit_phone_id);
        pAddress = findViewById(R.id.edit_address_id);
        testName = findViewById(R.id.edit_test_name_id);
        btnSave = findViewById(R.id.btn_save_test_sample);
        btnScan = findViewById(R.id.btn_scan_info);
        btnMap = findViewById(R.id.btn_map_info);
        btnSave.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        appData = new AppData(this);
        sqLiteDB  = new SQLiteDB(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_save_test_sample:
                checkValidity();

                break;
                case R.id.btn_scan_info:
                    scanBarcode();
                break;

                case R.id.btn_map_info:
                    String addrs = pAddress.getText().toString().trim();
                    if (TextUtils.isEmpty(addrs)) {
                        pAddress.setError("Please Type Patient Address ");
                        pAddress.requestFocus();
                    }else{
                        Intent intent = new Intent(this,MapsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("address",addrs);
                        startActivity(intent);
                    }


                break;
        }

    }

    private void checkValidity() {
        String id = pId.getText().toString().trim();
        String name = pName.getText().toString().trim();
        String phone = pPhone.getText().toString().trim();
        String address = pAddress.getText().toString().trim();
        String tname = testName.getText().toString().trim();

        if (TextUtils.isEmpty(id)) {
            pId.setError("Please Type Patient Id ");
            pId.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            pName.setError("Please Type Patient Name ");
            pName.requestFocus();
        }else if (TextUtils.isEmpty(phone)) {
            pPhone.setError("Please Type Patient Phone No ");
            pPhone.requestFocus();
        }else if (TextUtils.isEmpty(address)) {
            pAddress.setError("Please Type Patient Address ");
            pAddress.requestFocus();
        }else if (TextUtils.isEmpty(tname)) {
            testName.setError("Please Type Patient Address ");
            testName.requestFocus();
        }else {
            saveLabTestInfo(id,name,phone,address,tname);
        }
    }

    private void saveLabTestInfo(String id,String name,String phone,String address,String test) {
        int labid = appData.getLabInfo("labId");
        TestSampleModel sampleModel = new TestSampleModel(labid,id,name,phone,address,test,"Processing");
        sqLiteDB.insertTestSample(sampleModel);
        Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
        clear();
        Intent intent = new Intent(this,DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void scanBarcode() {
        //need a bar or qr code capture class for capture bar code or qr code that is extends CaptureActiviy class;
        //capture class declared as an activity in manifest file
        // implementation 'com.journeyapps:zxing-android-embedded:3.4.0'//for barcode gen and scan
        // Override onActivityResult method and intentintegrator.parseActivityResult and set text in any where

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureBarcode.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {

            if (intentResult.getContents() != null) {
                pId.setText(intentResult.getContents());
            } else {
                Toast.makeText(this, "Patient Id Not Found", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void clear(){
        pId.setText("");
        pName.setText("");
        pPhone.setText("");
        pAddress.setText("");
        testName.setText("");

    }
}