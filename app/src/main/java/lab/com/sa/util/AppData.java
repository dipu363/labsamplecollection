package lab.com.sa.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppData {


    Context context;

    public AppData(Context context) {
        this.context = context;
    }


    public void  saveLabInfo(String key ,int integer_value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LabInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, integer_value);
        editor.apply();

    }

    public int getLabInfo(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LabInfo",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }

}
