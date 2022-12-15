package lab.com.sa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lab.com.sa.R;

public class HomeAdapter extends BaseAdapter {

    Context context;
    List<String> lab;

    public HomeAdapter(Context context, List<String> lab) {
        this.context = context;
        this.lab = lab;
    }

    @Override
    public int getCount() {
        return lab.size();
    }

    @Override
    public Object getItem(int i) {
        return lab.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String labName =lab.get(i);
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.cart_sample,null);
        }

        TextView textView = view.findViewById(R.id.cartTextid);
        textView.setText(labName);


        return view;
    }
}
