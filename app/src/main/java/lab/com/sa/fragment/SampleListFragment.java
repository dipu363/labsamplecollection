package lab.com.sa.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.com.sa.R;
import lab.com.sa.adapter.TestSampleAdapter;
import lab.com.sa.data.model.TestSampleModel;
import lab.com.sa.ui.TestSampleDetailsActivity;
import lab.com.sa.util.AppData;
import lab.com.sa.util.SQLiteDB;

public class SampleListFragment extends Fragment {

    ListView listView;
    SQLiteDB sqLiteDB;
    AppData appData;
    TestSampleAdapter testSampleAdapter;
    List<TestSampleModel> sampleModelListl;

    public SampleListFragment() {
        // Required empty public constructor
    }
    public static SampleListFragment newInstance(String param1, String param2) {
        SampleListFragment fragment = new SampleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteDB = new SQLiteDB(getContext());
        appData = new AppData(getContext());
        sampleModelListl = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample_list, container, false);

        listView = view.findViewById(R.id.testsamplelistviewid);
        getAllTestSample();

        return view;

    }

    private void getAllTestSample() {
        int labid =appData.getLabInfo("labId");
        Cursor cursor =  sqLiteDB.getAllTestSample(labid);

        if(cursor.moveToFirst()){
            do{

                TestSampleModel ts = new TestSampleModel();
                ts.setSample_id(cursor.getInt(0));
                ts.setLab_id(cursor.getInt(1));
                ts.setPat_id(cursor.getString(2));
                ts.setPat_name(cursor.getString(3));
                ts.setPat_phone(cursor.getString(4));
                ts.setPat_address(cursor.getString(5));
                ts.setTest_name(cursor.getString(6));
                ts.setTest_result(cursor.getString(7));
                ts.setTest_status(cursor.getString(8));
                sampleModelListl.add(ts);
            }while (cursor.moveToNext());
        }

        testSampleAdapter = new TestSampleAdapter(getContext(),sampleModelListl);
        listView.setAdapter(testSampleAdapter);

    }

}