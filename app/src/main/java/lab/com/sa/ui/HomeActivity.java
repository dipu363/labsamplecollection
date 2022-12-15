package lab.com.sa.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lab.com.sa.R;
import lab.com.sa.adapter.HomeAdapter;
import lab.com.sa.util.AppData;

public class HomeActivity extends AppCompatActivity {


    GridView gridView ;
    AppData appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gridView = findViewById(R.id.labName_grid_view_id);
        appData = new AppData(this);

        List<String> lablist = new ArrayList<>();
        lablist.add("Virology PCR");
        lablist.add("Parasitology");
        lablist.add("Lab-AKU");
        lablist.add("Biochemistry");
        lablist.add("Hematology Ped&Mat");
        lablist.add("Cytogenetics");
        lablist.add("Haematololgy");
        lablist.add("Microbiology Bench");

        HomeAdapter labAdapter = new HomeAdapter(this,lablist);
        gridView.setAdapter(labAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView parent, View v,
                                       int position, long id) {
               appData.saveLabInfo("labId",position+1);
               Intent intent = new Intent(HomeActivity.this,DashboardActivity.class);
               startActivity(intent);


            }
       });

    }
}