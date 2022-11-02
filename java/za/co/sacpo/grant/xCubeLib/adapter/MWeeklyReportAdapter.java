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
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.MWeeklyReportObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.feedback.MStudentReportsDetailsA;


public class MWeeklyReportAdapter extends RecyclerView.Adapter<MWeeklyReportAdapter.ReportHolder> {
    private List<MWeeklyReportObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,stipend_id;

    public MWeeklyReportAdapter(List<MWeeklyReportObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,String stipend_id) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.stipend_id = stipend_id;
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
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_m_weekly_report, parent, false);
        return new ReportHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ReportHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head);
  
            holder.lblWeekEnding.setTypeface(holder.lblWeekEnding.getTypeface(), Typeface.BOLD);
            holder.lblWeekEnding.setTextColor(res.getColor(R.color.white));

            holder.lblNameOfDepartment.setTypeface(holder.lblNameOfDepartment.getTypeface(), Typeface.BOLD);
            holder.lblNameOfDepartment.setTextColor(res.getColor(R.color.white));
            
            holder.lblTitle.setTypeface(holder.lblTitle.getTypeface(), Typeface.BOLD);
            holder.lblTitle.setTextColor(res.getColor(R.color.white));




            holder.lblTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).MTitle3;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblTitle,sToolTip,4);
                }
            });


        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);


            }
            else{
                BColor=res.getColor(R.color.row_odd);


            }

            holder.lblTitle.setTextColor(res.getColor(R.color.row_link));
            holder.lblTitle.setPaintFlags(holder.lblTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblTitle.getPaint().setUnderlineText(true);

            holder.lblTitle.setTextColor(res.getColor(R.color.row_link));
            holder.lblTitle.setPaintFlags(holder.lblTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblTitle.getPaint().setUnderlineText(true);

            holder.lblTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("report_id", report_id);

                    String month_year = String.valueOf(holder.hItem.MWeekEnding4);
                    inputUri.putString("month_year", month_year);

                    String student_id = String.valueOf(holder.hItem.MstudentId6);
                    inputUri.putString("student_id", student_id);

                    inputUri.putString("generator", "174");
                    inputUri.putString("stipend_id", stipend_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MStudentReportsDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);

                }
            });
        }
      

      
        holder.lblWeekEnding.setText(aObjList.get(holder.getAdapterPosition()).MWeekEnding4);
        holder.lblWeekEnding.setBackgroundColor(BColor);
       
        holder.lblNameOfDepartment.setText(aObjList.get(holder.getAdapterPosition()).MDepartmentName5);
        holder.lblNameOfDepartment.setBackgroundColor(BColor);
        

        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).MTitle3);
        holder.lblTitle.setBackgroundColor(BColor);


        holder.lblWeekEnding.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MWeekEnding4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblWeekEnding,sToolTip,4);
            }
        }); 

        holder.lblNameOfDepartment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).MDepartmentName5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblNameOfDepartment,sToolTip,4);
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

    public class ReportHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MWeeklyReportObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblTitle,lblWeekEnding,lblNameOfDepartment;
       

        public ReportHolder(View itemView) {
            super(itemView);
            hView = itemView;
           
            tRow= (TableRow) itemView.findViewById(R.id.tRowFeedback);
            
            lblTitle= (TextView) itemView.findViewById(R.id.lblTitle);
            lblWeekEnding= (TextView) itemView.findViewById(R.id.lblWeekEnding);
            lblNameOfDepartment= (TextView) itemView.findViewById(R.id.lblNameOfDepartment);
            


        }
    }
}
