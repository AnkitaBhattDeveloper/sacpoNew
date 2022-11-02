package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import za.co.sacpo.grant.xCubeLib.dataObj.SpinnerObj;

public class SpinAdapter extends ArrayAdapter<SpinnerObj> {

    private Context context;
    private SpinnerObj[] values;
    public SpinAdapter(Context context, int textViewResourceId, SpinnerObj[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }
    public int getCount(){
        return values.length;
    }
    public SpinnerObj getItem(int position){
        return values[position];
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.parseColor("#00487F"));
        label.setPadding(5,5,5,5);
        label.setText(values[position].getName());
        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.parseColor("#00487F"));
        label.setPadding(5,5,5,5);
        label.setText(values[position].getName());
        return label;
    }


}