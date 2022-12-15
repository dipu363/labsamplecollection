package lab.com.sa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lab.com.sa.R;
import lab.com.sa.data.model.TestSampleModel;
import lab.com.sa.ui.TestSampleDetailsActivity;
import lab.com.sa.util.AppData;
import lab.com.sa.util.SQLiteDB;

public class SampleCollectionDoneAdapter extends BaseAdapter {


    Context context;
    List<TestSampleModel> sampleModelList;
    SQLiteDB sqLiteDB;
    AppData appData;

    public SampleCollectionDoneAdapter(Context context, List<TestSampleModel> sampleModelList) {
        this.context = context;
        this.sampleModelList = sampleModelList;
        sqLiteDB = new SQLiteDB(context);
        appData = new AppData(context);
    }

    @Override
    public int getCount() {
        return sampleModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return sampleModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TestSampleModel sampleModel = sampleModelList.get(i);
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.test_list_sample_view, null);
        }
        TextView id = view.findViewById(R.id.patient_id);
        TextView name = view.findViewById(R.id.patient_name);
        TextView status = view.findViewById(R.id.test_status);
        ImageButton btnDelete = view.findViewById(R.id.btn_delete_Id);
        Button btnDetail = view.findViewById(R.id.btn_details_id);

        id.setText(sampleModel.getPat_id());
        name.setText(sampleModel.getPat_name());
        status.setText(sampleModel.getTest_status());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemAlertDialog(sampleModel.getSample_id());
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appData.saveLabInfo("sampleId" ,sampleModel.getSample_id());
                Intent intent = new Intent( context, TestSampleDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }


    public void  deleteItemAlertDialog(int id){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permanent Delete")
                .setMessage("Are you sure item is deleted?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqLiteDB.deleteSingleSample(id);
                        sampleModelList.remove(i); // remove the item
                        notifyDataSetChanged();
                        Toast.makeText(context, "Delete has been successful", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
