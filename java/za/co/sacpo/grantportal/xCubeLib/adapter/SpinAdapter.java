package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SpinnerObj;

public class SpinAdapter extends ArrayAdapter<SpinnerObj> {

    private Context context;
    private SpinnerObj[] values;
    BaseAPC baseAPC = new BaseAPC() {
        @Override
        protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        }

        @Override
        protected void initializeViews() {

        }

        @Override
        protected void initializeListeners() {

        }

        @Override
        protected void initializeInputs() {

        }

        @Override
        protected void initializeLabels() {

        }

        @Override
        protected void setLayoutXml() {

        }

        @Override
        protected void verifyVersion() {

        }

        @Override
        protected void fetchVersionData() {

        }

        @Override
        protected void internetChangeBroadCast() {

        }
    };
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
       label.setTextColor(context.getResources().getColor(baseAPC.getTextcolorResourceId("spinnertext")));
        label.setPadding(20,5,5,5);
        label.setText(values[position].getName());
        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(context.getResources().getColor(baseAPC.getTextcolorResourceId("spinnertext")));
        // label.setTextColor(Color.parseColor("#00487F"));
        label.setPadding(15,5,5,5);
        label.setText(values[position].getName());
        return label;
    }


}