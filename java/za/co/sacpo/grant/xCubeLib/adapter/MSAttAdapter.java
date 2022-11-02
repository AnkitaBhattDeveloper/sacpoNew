package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.MSAttObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.attendance.MAttSummaryA;
import za.co.sacpo.grant.xCubeMentor.attendance.MAttMonthlyA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MSAttAdapter extends RecyclerView.Adapter<MSAttAdapter.ViewHolder> {
    private List<MSAttObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String temp;
    MAttSummaryA baseActivity;
    public MSAttAdapter(List<MSAttObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MAttSummaryA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
    }

    public String getLabelFromDb(String inputLabel, int resId) {
        String ValueLabel = baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if (res.getCount() > 0) {
            try {

                while (res.moveToNext())

                {
                    ValueLabel = res.getString(0);
                }
            } finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }

        return ValueLabel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_studentattendance_row, parent, false);
        return new ViewHolder(itemView);
    }
    public void gotoAttendanceDetails(String month_year,String month,String year,Context context){
        Bundle inputUri = new Bundle();
        inputUri.putString("student_id", baseActivity.getStudentId());
        inputUri.putString("m_student_name",baseActivity.getStudentName());
        inputUri.putString("date_input",month_year);
        inputUri.putString("month",month);
        inputUri.putString("year",year);
        inputUri.putString("generator", "M405");
        Intent intent = new Intent(context, MAttMonthlyA.class);
        intent.putExtras(inputUri);
        context.startActivity(intent);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;


        if (position == 0) {
            BColor = res.getColor(R.color.row_head);
            holder.lbl_month.setTextColor(res.getColor(R.color.white));
            holder.lbl_month.setTypeface(holder.lbl_month.getTypeface(), Typeface.BOLD);

            holder.lbl_month.setText(aObjList.get(holder.getAdapterPosition()).month3);
            holder.lbl_month.setBackgroundColor(BColor);

            holder.lbl_month.setTextColor(res.getColor(R.color.white));
            holder.lbl_month.setTypeface(holder.lbl_month.getTypeface(), Typeface.BOLD);
            holder.lbl_year.setTextColor(res.getColor(R.color.white));
            holder.lbl_year.setTypeface(holder.lbl_year.getTypeface(), Typeface.BOLD);


            holder.lbl_days_worked.setTextColor(res.getColor(R.color.white));
            holder.lbl_days_worked.setTypeface(holder.lbl_days_worked.getTypeface(), Typeface.BOLD);
            holder.lbl_annual_leave.setTextColor(res.getColor(R.color.white));
            holder.lbl_annual_leave.setTypeface(holder.lbl_annual_leave.getTypeface(), Typeface.BOLD);
            holder.lbl_sick_leave.setTextColor(res.getColor(R.color.white));
            holder.lbl_sick_leave.setTypeface(holder.lbl_sick_leave.getTypeface(), Typeface.BOLD);
            holder.lbl_opdLeave.setTextColor(res.getColor(R.color.white));
            holder.lbl_opdLeave.setTypeface(holder.lbl_opdLeave.getTypeface(), Typeface.BOLD);
            holder.lbl_upLeave.setTextColor(res.getColor(R.color.white));
            holder.lbl_upLeave.setTypeface(holder.lbl_upLeave.getTypeface(), Typeface.BOLD);
        } else {


                holder.lbl_days_worked.setTextColor(res.getColor(R.color.row_link));
                holder.lbl_days_worked.setPaintFlags(holder.lbl_days_worked.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lbl_days_worked.getPaint().setUnderlineText(true);

                holder.lbl_annual_leave.setTextColor(res.getColor(R.color.row_link));
                holder.lbl_annual_leave.setPaintFlags(holder.lbl_annual_leave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lbl_annual_leave.getPaint().setUnderlineText(true);

                holder.lbl_opdLeave.setTextColor(res.getColor(R.color.row_link));
                holder.lbl_opdLeave.setPaintFlags(holder.lbl_opdLeave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lbl_opdLeave.getPaint().setUnderlineText(true);

                holder.lbl_upLeave.setTextColor(res.getColor(R.color.row_link));
                holder.lbl_upLeave.setPaintFlags(holder.lbl_upLeave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lbl_upLeave.getPaint().setUnderlineText(true);

                holder.lbl_upLeave.setTextColor(res.getColor(R.color.row_link));
                holder.lbl_upLeave.setPaintFlags(holder.lbl_upLeave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lbl_upLeave.getPaint().setUnderlineText(true);



            if ((position % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }
        }


        holder.lbl_month.setText(aObjList.get(holder.getAdapterPosition()).month3);
        holder.lbl_month.setBackgroundColor(BColor);

        holder.lbl_year.setText(aObjList.get(holder.getAdapterPosition()).year4);
        holder.lbl_year.setBackgroundColor(BColor);
        holder.lbl_days_worked.setText(aObjList.get(holder.getAdapterPosition()).daysWorked5);
        holder.lbl_days_worked.setBackgroundColor(BColor);
        holder.lbl_annual_leave.setText(aObjList.get(holder.getAdapterPosition()).annualLeave6);
        holder.lbl_annual_leave.setBackgroundColor(BColor);
        holder.lbl_sick_leave.setText(aObjList.get(holder.getAdapterPosition()).sickLeave7);
        holder.lbl_sick_leave.setBackgroundColor(BColor);
        holder.lbl_opdLeave.setText(aObjList.get(holder.getAdapterPosition()).oPL8);
        holder.lbl_opdLeave.setBackgroundColor(BColor);
        holder.lbl_upLeave.setText(aObjList.get(holder.getAdapterPosition()).uPL9);
        holder.lbl_upLeave.setBackgroundColor(BColor);

        holder.lbl_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });

        holder.lbl_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });
        holder.lbl_days_worked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });
        holder.lbl_annual_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });

        holder.lbl_sick_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });
        holder.lbl_opdLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month_y = aObjList.get(holder.getAdapterPosition()).yearMonth10;
                String month = aObjList.get(holder.getAdapterPosition()).month3;
                String year = aObjList.get(holder.getAdapterPosition()).year4;
                gotoAttendanceDetails(month_y,month,year,view.getContext());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (aObjList != null) {
            return aObjList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MSAttObj.Item hItem;
        public final TableRow tRow;
        public final TextView lbl_month, lbl_days_worked, lbl_annual_leave, lbl_sick_leave, lbl_opdLeave, lbl_upLeave,lbl_year;


        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (TableRow) itemView.findViewById(R.id.mStudentAttendance_Row);

            lbl_month = (TextView) itemView.findViewById(R.id.lbl_month);
            lbl_year = (TextView) itemView.findViewById(R.id.lbl_year);
            lbl_days_worked = (TextView) itemView.findViewById(R.id.lbl_days_worked);
            lbl_annual_leave = (TextView) itemView.findViewById(R.id.lbl_annual_leave);
            lbl_sick_leave = (TextView) itemView.findViewById(R.id.lbl_sick_leave);
            lbl_opdLeave = (TextView) itemView.findViewById(R.id.lbl_opdLeave);
            lbl_upLeave = (TextView) itemView.findViewById(R.id.lbl_upLeave);
        }
    }
}
