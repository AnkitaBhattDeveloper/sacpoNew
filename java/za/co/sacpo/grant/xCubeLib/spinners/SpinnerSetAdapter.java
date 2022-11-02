package za.co.sacpo.grant.xCubeLib.spinners;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by vin on 29/8/16.
 */
public class SpinnerSetAdapter extends ArrayAdapter<SpinnerSet> {
    private Context context;
    private SpinnerSet[] values;
    public SpinnerSetAdapter(Context context, int textViewResourceId, SpinnerSet[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }
    public int getCount(){
        return values.length;
    }
    public SpinnerSet getItem(int position){
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