package lab.com.sa.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import lab.com.sa.R;
import lab.com.sa.data.model.TestSampleModel;
import lab.com.sa.util.AppData;
import lab.com.sa.util.SQLiteDB;

public class TestSampleDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    TextView pId,labid,name,phone,address,testName,status ,result;
    EditText testResult;
    Button btnDone;
    ImageButton btnMap,btnCall;

    AppData appData;
    SQLiteDB sqLiteDB;
    TestSampleModel ts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sample_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Sample Details");
        appData = new AppData(this);
        sqLiteDB = new SQLiteDB(this);

        pId = findViewById(R.id.text_p_id);
        labid = findViewById(R.id.text_lab_id);
        name = findViewById(R.id.text_p_name_id);
        phone = findViewById(R.id.text_phone_id);
        address = findViewById(R.id.text_addrees_id);
        testName = findViewById(R.id.text_test_name_id);
        status = findViewById(R.id.text_status_id);
        testResult = findViewById(R.id.edit_testresult_id);
        result = findViewById(R.id.text_result_id);
        btnMap = findViewById(R.id.btn_map_id);
        btnCall = findViewById(R.id.btn_phone_call);
        btnDone = findViewById(R.id.btn_sample_collect_done);

        btnCall.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        setPatientInfo();

    }


    public void setPatientInfo(){
        int id = appData.getLabInfo("sampleId");
        Cursor cursor =  sqLiteDB.getTestSampleById(id);
        if(cursor.moveToFirst()){
            do{
                ts = new TestSampleModel();
                ts.setSample_id(cursor.getInt(0));
                ts.setLab_id(cursor.getInt(1));
                ts.setPat_id(cursor.getString(2));
                ts.setPat_name(cursor.getString(3));
                ts.setPat_phone(cursor.getString(4));
                ts.setPat_address(cursor.getString(5));
                ts.setTest_name(cursor.getString(6));
                ts.setTest_result(cursor.getString(7));
                ts.setTest_status(cursor.getString(8));
            }while (cursor.moveToNext());


        }

        pId.setText(ts.getPat_id());
        labid.setText(String.valueOf(ts.getLab_id()));
        name.setText(ts.getPat_name());
        phone.setText(ts.getPat_phone());
        address.setText(ts.getPat_address());
        testName.setText(ts.getTest_name());
        if (ts.getTest_result() == null){
            testResult.setVisibility(View.VISIBLE);
            result.setVisibility(View.INVISIBLE);
        }else {
            testResult.setVisibility(View.INVISIBLE);
            result.setVisibility(View.VISIBLE);
            result.setText(ts.getTest_result());
        }
        if (ts.getTest_status().equals("Done")){
            status.setText("Done");
            btnDone.setVisibility(View.INVISIBLE);
        }else {
            status.setText("Processing");
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_map_id:
                String address = ts.getPat_address();
                Intent intent = new Intent(this,MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("address",address);
                startActivity(intent);
                break;
            case R.id.btn_phone_call:
                String number = phone.getText().toString();
                Intent call = new Intent("android.intent.action.DIAL", Uri.parse("tel:"+number));
                startActivity(call);
                break;
            case R.id.btn_sample_collect_done:
                String status = "Done";
                String result = testResult.getText().toString();
                if (TextUtils.isEmpty(result)){
                    testResult.setError("Please Type Test Result");
                    testResult.requestFocus();
                }else{
                    sqLiteDB.updateTestSample(status,result,ts.getSample_id());
                    Toast.makeText(this, "Result Submit Successful", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(this,DashboardActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    finish();
                }

                break;
        }

    }
}