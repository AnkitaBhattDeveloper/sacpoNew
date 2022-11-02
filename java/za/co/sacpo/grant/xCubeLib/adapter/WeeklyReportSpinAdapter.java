package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.sacpo.grant.xCubeLib.model.WeeklyRepport;

public class WeeklyReportSpinAdapter extends ArrayAdapter<WeeklyRepport> {

    private ArrayList<WeeklyRepport> myarrayList;

    public WeeklyReportSpinAdapter(Context context, int textViewResourceId, ArrayList<WeeklyRepport> modelArrayList) {
        super(context, textViewResourceId, modelArrayList);
        this.myarrayList = modelArrayList;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Nullable
    @Override
    public WeeklyRepport getItem(int position) {
        return myarrayList.get(position);
    }

    @Override
    public int getCount() {
        int count = myarrayList.size();
        //return count > 0 ? count - 1 : count;
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        WeeklyRepport model = getItem(position);

        View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

        TextView label = spinnerRow.findViewById(android.R.id.text1);
        label.setText(String.format("%s", model != null ? model.getName() : ""));

        return spinnerRow;
    }
}