package za.co.sacpo.grantportal.xCubeLib.adapter;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SCWeeklyReportObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SEditFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SRemoveFeedbackA;
import za.co.sacpo.grantportal.xCubeStudent.feedback.SReportDetailsA;


public class SCWeeklyReportAdapter extends RecyclerView.Adapter<SCWeeklyReportAdapter.LeavesHolder> {
    private List<SCWeeklyReportObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String is_upload_attendance,student_id;

    public SCWeeklyReportAdapter(List<SCWeeklyReportObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,String is_upload_attendance,String student_id) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.is_upload_attendance = is_upload_attendance;
        this.student_id = student_id;
    }

    public String getLabelFromDb(String inputLabel,int resId){
        String ValueLabel =baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if(res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
                    ValueLabel = res.getString(0);
                }
            }
            finally {
                if (res != null && !res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }

    @Override
    public LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_scweekly_report, parent, false);
        return new LeavesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
  
            holder.lblMonth.setTypeface(holder.lblMonth.getTypeface(), Typeface.BOLD);
            holder.lblMonth.setTextColor(res.getColor(R.color.white));

            holder.lblYear.setTypeface(holder.lblYear.getTypeface(), Typeface.BOLD);
            holder.lblYear.setTextColor(res.getColor(R.color.white));

            holder.lblSDepartment5.setTypeface(holder.lblSDepartment5.getTypeface(), Typeface.BOLD);
            holder.lblSDepartment5.setTextColor(res.getColor(R.color.white));

            holder.lblSTitle.setTypeface(holder.lblSTitle.getTypeface(), Typeface.BOLD);
            holder.lblSTitle.setTextColor(res.getColor(R.color.white));

            Labels =this.getLabelFromDb("b_174_edit",R.string.b_174_edit);
            holder.lblActionEdit.setText(Labels);
            holder.lblActionEdit.setTextColor(res.getColor(R.color.white));
            holder.lblActionEdit.setTypeface(holder.lblActionEdit.getTypeface(), Typeface.BOLD);
            holder.lblActionEdit.setVisibility(View.VISIBLE);
            holder.btnActionEdit.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("b_174_remove",R.string.b_174_remove);
            holder.lblActionRemove.setText(Labels);
            holder.lblActionRemove.setTextColor(res.getColor(R.color.white));
            holder.lblActionRemove.setTypeface(holder.lblActionRemove.getTypeface(), Typeface.BOLD);
            holder.lblActionRemove.setVisibility(View.VISIBLE);
            holder.btnActionRemove.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("b_174_details",R.string.b_174_details);
            holder.lblActionDetails.setText(Labels);
            holder.lblActionDetails.setTextColor(res.getColor(R.color.white));
            holder.lblActionDetails.setVisibility(View.VISIBLE);
            holder.btnActionDetails.setVisibility(View.GONE);

            holder.lblSTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).STitile7;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblSTitle,sToolTip,4);
                }
            });


        }
        else{

            holder.lblActionEdit.setVisibility(View.GONE);
            holder.btnActionEdit.setVisibility(View.VISIBLE);

            holder.lblActionRemove.setVisibility(View.GONE);
            holder.btnActionRemove.setVisibility(View.VISIBLE);

            holder.lblActionDetails.setVisibility(View.GONE);
            holder.btnActionDetails.setVisibility(View.VISIBLE);

            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);


            }
            else{
                BColor=res.getColor(R.color.row_odd);


            }

            holder.lblSTitle.setTextColor(res.getColor(R.color.row_link));
            holder.lblSTitle.setPaintFlags(holder.lblSTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblSTitle.getPaint().setUnderlineText(true);

            holder.lblSTitle.setTextColor(res.getColor(R.color.row_link));
            holder.lblSTitle.setPaintFlags(holder.lblSTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblSTitle.getPaint().setUnderlineText(true);

            holder.lblSTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();

                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "223");
                    String date_input = String.valueOf(holder.hItem.DateInput8);
                    inputUri.putString("month_year", date_input);

                    String grant_id = String.valueOf(holder.hItem.grant_id);
                    inputUri.putString("grant_id", grant_id);
                    inputUri.putString("is_upload_attendance", is_upload_attendance);
                    inputUri.putString("student_id", student_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SReportDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);

                }
            });
        }


      
        holder.lblMonth.setText(aObjList.get(holder.getAdapterPosition()).SMonth3);
        holder.lblMonth.setBackgroundColor(BColor);
       
        holder.lblYear.setText(aObjList.get(holder.getAdapterPosition()).SYear5);
        holder.lblYear.setBackgroundColor(BColor);

        holder.lblSDepartment5.setText(aObjList.get(holder.getAdapterPosition()).SDepartment6);
        holder.lblSDepartment5.setBackgroundColor(BColor);

        holder.lblSTitle.setText(aObjList.get(holder.getAdapterPosition()).STitile7);
        holder.lblSTitle.setBackgroundColor(BColor);

        holder.linearEdit.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("b_174_edit",R.string.b_174_edit);
        holder.btnActionEdit.setText(Labels);

        holder.linearRemove.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("b_174_remove",R.string.b_174_remove);
        holder.btnActionRemove.setText(Labels);

        holder.linearDetails.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("b_174_details",R.string.b_174_details);
        holder.btnActionDetails.setText(Labels);


        holder.lblMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).SMonth3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblMonth,sToolTip,4);
            }
        }); 

        holder.lblYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).SYear5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblYear,sToolTip,4);
            }
        });

           holder.lblSDepartment5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).SDepartment6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblSDepartment5,sToolTip,4);
            }
        });






        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();

                String s_w_r_id = String.valueOf(holder.hItem.aId2);
                String date_input = String.valueOf(holder.hItem.DateInput8);
                inputUri.putString("date", date_input);
                inputUri.putString("s_w_r_id", s_w_r_id);
                inputUri.putString("is_upload_attendance", is_upload_attendance);
                inputUri.putString("student_id", student_id);
                inputUri.putString("generator", "223");
                String grant_id = String.valueOf(holder.hItem.grant_id);
                inputUri.putString("grant_id", grant_id);
                Context context = v.getContext();
                Intent intent = new Intent(context, SEditFeedbackA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);

            }
        });
        holder.btnActionRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();

                 String s_w_r_id = String.valueOf(holder.hItem.aId2);
                 inputUri.putString("s_w_r_id", s_w_r_id);
                 String date = String.valueOf(holder.hItem.SMonth3);

                inputUri.putString("date", date);

                String feedback = String.valueOf(holder.hItem.STitile7);

                inputUri.putString("feedback", feedback);
                inputUri.putString("generator", "174");

                String date_input = String.valueOf(holder.hItem.DateInput8);
                inputUri.putString("date_input", date_input);
                Context context = v.getContext();
                Intent intent = new Intent(context, SRemoveFeedbackA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(aObjList!=null) {
            return aObjList.size();
        }
        return 0;
    }

    public class LeavesHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SCWeeklyReportObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblSTitle,lblMonth,lblYear,lblSDepartment5;
         LinearLayout linearEdit,linearRemove,linearDetails;
         TextView lblActionEdit,lblActionRemove,lblActionDetails;
         Button btnActionEdit,btnActionRemove,btnActionDetails;
        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRowFeedback);
            lblMonth= (TextView) itemView.findViewById(R.id.lblMonth);
            lblYear= (TextView) itemView.findViewById(R.id.lblYear);
            lblSDepartment5= (TextView) itemView.findViewById(R.id.lblSDepartment5);
            lblSTitle= (TextView) itemView.findViewById(R.id.lblSTitle);

            linearEdit= (LinearLayout) itemView.findViewById(R.id.linearEdit);
            linearRemove= (LinearLayout) itemView.findViewById(R.id.linearRemove);
            linearDetails= (LinearLayout) itemView.findViewById(R.id.linearDetails);

            lblActionEdit= (TextView) itemView.findViewById(R.id.lblActionEdit);
            lblActionRemove= (TextView) itemView.findViewById(R.id.lblActionRemove);
            lblActionDetails= (TextView) itemView.findViewById(R.id.lblActionDetails);

            btnActionEdit= (Button) itemView.findViewById(R.id.btnActionEdit);
            btnActionRemove= (Button) itemView.findViewById(R.id.btnActionRemove);
            btnActionDetails= (Button) itemView.findViewById(R.id.btnActionDetails);


        }
    }
}
