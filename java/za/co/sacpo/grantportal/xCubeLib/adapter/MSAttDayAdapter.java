package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
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
import za.co.sacpo.grantportal.xCubeLib.dataObj.MSAttDayObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.attendance.MAttApprovalA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MSAttDayAdapter extends RecyclerView.Adapter<MSAttDayAdapter.StudentAttendenceHolder> {
    private List<MSAttDayObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    protected DbHelper dbSetaObj;
    String Labels;
    private AppCompatActivity activityInCall;

    public MSAttDayAdapter(List<MSAttDayObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
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
    public StudentAttendenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.att_row_daywise, parent, false);
        return new StudentAttendenceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StudentAttendenceHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lbl_Learner.setTextColor(res.getColor(R.color.white));
            holder.lbl_Learner.setTypeface(holder.lbl_Learner.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_306_Action",R.string.l_306_Action);
            holder.lbl_mAction.setText(Labels);
            holder.lbl_mAction.setTextColor(res.getColor(R.color.white));
            holder.lbl_mAction.setVisibility(View.VISIBLE);
            holder.btnActionPaid_Unpaid.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_306_Action",R.string.l_306_Action);
            holder.lbl_mAction2.setText(Labels);
            holder.lbl_mAction2.setTextColor(res.getColor(R.color.white));
            holder.lbl_mAction2.setVisibility(View.VISIBLE);
            holder.btnActionAdd_Remark.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_306_Action",R.string.l_306_Action);
            holder.lbl_mAction3.setText(Labels);
            holder.lbl_mAction3.setTextColor(res.getColor(R.color.white));
            holder.lbl_mAction3.setVisibility(View.VISIBLE);
            holder.btnActionApproval.setVisibility(View.GONE);




            holder.lbl_mAction.setTypeface(holder.lbl_mAction.getTypeface(), Typeface.BOLD);
            holder.lbl_mAction2.setTypeface(holder.lbl_mAction.getTypeface(), Typeface.BOLD);
            holder.lbl_mAction3.setTypeface(holder.lbl_mAction.getTypeface(), Typeface.BOLD);
            holder.lbl_Learner.setTextColor(res.getColor(R.color.white));
            holder.lbl_Learner.setTypeface(holder.lbl_Learner.getTypeface(), Typeface.BOLD);
            holder.lbl_mLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lbl_mLoginTime.setTypeface(holder.lbl_mLoginTime.getTypeface(), Typeface.BOLD);
            holder.lbl_mLogoutTime.setTextColor(res.getColor(R.color.white));
            holder.lbl_mLogoutTime.setTypeface(holder.lbl_mLogoutTime.getTypeface(), Typeface.BOLD);
            holder.lbl_mSpendTime.setTextColor(res.getColor(R.color.white));
            holder.lbl_mSpendTime.setTypeface(holder.lbl_mSpendTime.getTypeface(), Typeface.BOLD);
            holder.lbl_mStudentAttendanceStatus.setTextColor(res.getColor(R.color.white));
            holder.lbl_mStudentAttendanceStatus.setTypeface(holder.lbl_mStudentAttendanceStatus.getTypeface(), Typeface.BOLD);
            holder.lbl_mMentorAttendanceStatus.setTextColor(res.getColor(R.color.white));
            holder.lbl_mMentorAttendanceStatus.setTypeface(holder.lbl_mMentorAttendanceStatus.getTypeface(), Typeface.BOLD);
             holder.lbl_mLeadEmpAdminAttendanceStatus.setTextColor(res.getColor(R.color.white));
            holder.lbl_mLeadEmpAdminAttendanceStatus.setTypeface(holder.lbl_mLeadEmpAdminAttendanceStatus.getTypeface(), Typeface.BOLD);
             holder.lbl_mCordDifference.setTextColor(res.getColor(R.color.white));
            holder.lbl_mCordDifference.setTypeface(holder.lbl_mCordDifference.getTypeface(), Typeface.BOLD);

        }
        else{
            holder.lbl_mAction.setVisibility(View.GONE);
            holder.btnActionPaid_Unpaid.setVisibility(View.VISIBLE);
            holder.lbl_mAction2.setVisibility(View.GONE);
            holder.btnActionAdd_Remark.setVisibility(View.VISIBLE);
            holder.lbl_mAction3.setVisibility(View.GONE);
            holder.btnActionApproval.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.lbl_Learner.setText(aObjList.get(holder.getAdapterPosition()).mAsDate3);
        holder.lbl_Learner.setBackgroundColor(BColor);

        holder.btnActionPaid_Unpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("student_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, MAttApprovalA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });




        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_306_Approve",R.string.l_306_Approve);
        holder.btnActionPaid_Unpaid.setText(Labels);

        holder.linearLAction2.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_306_Details",R.string.l_306_Details);
        holder.btnActionAdd_Remark.setText(Labels);

        holder.linearLAction3.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_306_Details",R.string.l_306_Details);
        holder.btnActionApproval.setText(Labels);
        holder.linearLAction2.setVisibility(View.GONE);
        holder.linearLAction3.setVisibility(View.GONE);

        holder.lbl_mLoginTime.setText(aObjList.get(holder.getAdapterPosition()).mAsLoginTime4);
        holder.lbl_mLoginTime.setBackgroundColor(BColor);
        holder.lbl_mLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).mAsLogoutTime5);
        holder.lbl_mLogoutTime.setBackgroundColor(BColor);
        holder.lbl_mSpendTime.setText(aObjList.get(holder.getAdapterPosition()).mAsSpendTime6);
        holder.lbl_mSpendTime.setBackgroundColor(BColor);
        holder.lbl_mStudentAttendanceStatus.setText(aObjList.get(holder.getAdapterPosition()).mAsStudentAttendenceStatus7);
        holder.lbl_mStudentAttendanceStatus.setBackgroundColor(BColor);
        holder.lbl_mMentorAttendanceStatus.setText(aObjList.get(holder.getAdapterPosition()).mAsMentorAttendanceStatus8);
        holder.lbl_mMentorAttendanceStatus.setBackgroundColor(BColor);
        holder.lbl_mLeadEmpAdminAttendanceStatus.setText(aObjList.get(holder.getAdapterPosition()).mAsLeadAdminEmpAttStatus9);
        holder.lbl_mLeadEmpAdminAttendanceStatus.setBackgroundColor(BColor);
        holder.lbl_mCordDifference.setText(aObjList.get(holder.getAdapterPosition()).mAsCordDifference10);
        holder.lbl_mCordDifference.setBackgroundColor(BColor);






        holder.lbl_Learner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_Learner,sToolTip, 4);
            }
        });
        holder.lbl_mLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsLoginTime4;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mLoginTime,sToolTip,4);
            }
        });
        holder.lbl_mLogoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsLogoutTime5;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mLogoutTime,sToolTip,4);
            }
        });

        holder.lbl_mSpendTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsSpendTime6;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mSpendTime,sToolTip,4);
            }
        });
        holder.lbl_mStudentAttendanceStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsStudentAttendenceStatus7;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mStudentAttendanceStatus,sToolTip,4);
            }
        });
        holder.lbl_mMentorAttendanceStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsMentorAttendanceStatus8;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mMentorAttendanceStatus,sToolTip,4);
            }
        });
        holder.lbl_mLeadEmpAdminAttendanceStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsLeadAdminEmpAttStatus9;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mLeadEmpAdminAttendanceStatus,sToolTip,4);
            }
        });

        holder.lbl_mCordDifference.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).mAsCordDifference10;
                ((BaseAPC)activityInCall).showTooltip(holder.lbl_mCordDifference,sToolTip,4);
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

    public class StudentAttendenceHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MSAttDayObj.Item hItem;
        public final TableRow tRow;
        public final TextView  lbl_Learner, lbl_mLoginTime, lbl_mLogoutTime, lbl_mSpendTime, lbl_mStudentAttendanceStatus, lbl_mMentorAttendanceStatus,
                lbl_mLeadEmpAdminAttendanceStatus, lbl_mCordDifference, lbl_mAction, lbl_mAction2, lbl_mAction3;
        public final Button btnActionPaid_Unpaid, btnActionAdd_Remark, btnActionApproval;
        public final LinearLayout linearLAction,linearLAction2,linearLAction3;
        public StudentAttendenceHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.mStudentAttendance_Row);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            linearLAction3 = (LinearLayout) itemView.findViewById(R.id.linearLAction3);
            lbl_Learner= (TextView) itemView.findViewById(R.id.lbl_Learner);
            lbl_mLoginTime= (TextView) itemView.findViewById(R.id.lbl_mLoginTime);
            lbl_mLogoutTime= (TextView) itemView.findViewById(R.id.lbl_mLogoutTime);
            lbl_mSpendTime= (TextView) itemView.findViewById(R.id.lbl_mSpendTime);
            lbl_mStudentAttendanceStatus= (TextView) itemView.findViewById(R.id.lbl_mStudentAttendanceStatus);
            lbl_mMentorAttendanceStatus= (TextView) itemView.findViewById(R.id.lbl_mMentorAttendanceStatus);
            lbl_mLeadEmpAdminAttendanceStatus= (TextView) itemView.findViewById(R.id.lbl_mLeadEmpAdminAttendanceStatus);
            lbl_mCordDifference= (TextView) itemView.findViewById(R.id.lbl_mCordDifference);
            lbl_mAction= (TextView) itemView.findViewById(R.id.lbl_mAction);
            lbl_mAction2= (TextView) itemView.findViewById(R.id.lbl_mAction2);
            lbl_mAction3= (TextView) itemView.findViewById(R.id.lbl_mAction3);
            btnActionPaid_Unpaid= (Button) itemView.findViewById(R.id.btnActionPaid_Unpaid);
            btnActionAdd_Remark= (Button) itemView.findViewById(R.id.btnActionAdd_Remark);
            btnActionApproval= (Button) itemView.findViewById(R.id.btnActionApproval);
        }
    }
}
