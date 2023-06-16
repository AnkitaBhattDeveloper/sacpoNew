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
import za.co.sacpo.grantportal.xCubeLib.dataObj.MStudentObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.student.StudentsA;


/**
 * Created by xcube-06 on 7/8/18.
 */

public class MStudentAdapter extends RecyclerView.Adapter<MStudentAdapter.StudentHolder> {
    private List<MStudentObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public MStudentAdapter(List<MStudentObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_student_row, parent, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StudentHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lbl_s_LearnerName.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_LearnerName.setTypeface(holder.lbl_s_LearnerName.getTypeface(), Typeface.BOLD);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction_DownloadLearnerCv.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction2.setText(Labels);
            holder.lblAction2.setTextColor(res.getColor(R.color.white));
            holder.lblAction2.setVisibility(View.VISIBLE);
            holder.btnAction_ApprovedAttendance.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction3.setText(Labels);
            holder.lblAction3.setTextColor(res.getColor(R.color.white));
            holder.lblAction3.setVisibility(View.VISIBLE);
            holder.btnAction_DownloadClaimForm.setVisibility(View.GONE);

            Labels =this.getLabelFromDb("l_615_surveylist_Action",R.string.l_615_surveylist_Action);
            holder.lblAction4.setText(Labels);
            holder.lblAction4.setTextColor(res.getColor(R.color.white));
            holder.lblAction4.setVisibility(View.VISIBLE);
            holder.btnAction_Approved_Approve.setVisibility(View.GONE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction2.setTypeface(holder.lblAction2.getTypeface(), Typeface.BOLD);
            holder.lblAction3.setTypeface(holder.lblAction3.getTypeface(), Typeface.BOLD);
            holder.lblAction4.setTypeface(holder.lblAction4.getTypeface(), Typeface.BOLD);
            holder.lbl_s_RegistrationNumber.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_RegistrationNumber.setTypeface(holder.lbl_s_RegistrationNumber.getTypeface(), Typeface.BOLD);
            holder.lbl_s_LearnerId.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_LearnerId.setTypeface(holder.lbl_s_LearnerId.getTypeface(), Typeface.BOLD);
            holder.lbl_s_University.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_University.setTypeface(holder.lbl_s_University.getTypeface(), Typeface.BOLD);
            holder.lbl_s_Duration.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_Duration.setTypeface(holder.lbl_s_Duration.getTypeface(), Typeface.BOLD);
            holder.lbl_s_StartDate.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_StartDate.setTypeface(holder.lbl_s_StartDate.getTypeface(), Typeface.BOLD);

            holder.lbl_s_EndDate.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_EndDate.setTypeface(holder.lbl_s_EndDate.getTypeface(), Typeface.BOLD);
            holder.lbl_s_WorkedDay.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_WorkedDay.setTypeface(holder.lbl_s_WorkedDay.getTypeface(), Typeface.BOLD);
            holder.lbl_s_LeaveDay.setTextColor(res.getColor(R.color.white));
            holder.lbl_s_LeaveDay.setTypeface(holder.lbl_s_LeaveDay.getTypeface(), Typeface.BOLD);



        }
        else{
            holder.lblAction.setVisibility(View.GONE);
            holder.btnAction_DownloadLearnerCv.setVisibility(View.VISIBLE);
            holder.lblAction2.setVisibility(View.GONE);
            holder.btnAction_ApprovedAttendance.setVisibility(View.VISIBLE);
            holder.lblAction3.setVisibility(View.GONE);
            holder.btnAction_DownloadClaimForm.setVisibility(View.VISIBLE);
            holder.lblAction4.setVisibility(View.GONE);
            holder.btnAction_Approved_Approve.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.lbl_s_LearnerName.setText(aObjList.get(holder.getAdapterPosition()).mSLearnerName3);
        holder.lbl_s_LearnerName.setBackgroundColor(BColor);
        holder.btnAction_DownloadLearnerCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("stu_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, StudentsA.class);
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

    public class StudentHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MStudentObj.Item hItem;
        public final TableRow tRow;

        public final TextView lbl_s_LearnerName,lbl_s_RegistrationNumber,lbl_s_LearnerId,lbl_s_University,lbl_s_Duration,lbl_s_StartDate,
                lbl_s_EndDate,lbl_s_WorkedDay,lbl_s_LeaveDay,lblAction2,lblAction,lblAction3,lblAction4;

        public final Button btnAction_DownloadLearnerCv,btnAction_ApprovedAttendance,btnAction_DownloadClaimForm,btnAction_Approved_Approve;

        public final LinearLayout linearLAction,linearLAction2,linearLAction3,linearLAction4;

        public StudentHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.student_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            linearLAction2 = (LinearLayout) itemView.findViewById(R.id.linearLAction2);
            linearLAction3 = (LinearLayout) itemView.findViewById(R.id.linearLAction3);
            linearLAction4 = (LinearLayout) itemView.findViewById(R.id.linearLAction4);

            btnAction_DownloadLearnerCv= (Button) itemView.findViewById(R.id.btnAction_DownloadLearnerCv);
            btnAction_ApprovedAttendance= (Button) itemView.findViewById(R.id.btnAction_ApprovedAttendance);
            btnAction_DownloadClaimForm= (Button) itemView.findViewById(R.id.btnAction_DownloadClaimForm);
            btnAction_Approved_Approve= (Button) itemView.findViewById(R.id.btnAction_Approved_Approve);


            lbl_s_LearnerName= (TextView) itemView.findViewById(R.id.lbl_s_LearnerName);
            lbl_s_RegistrationNumber= (TextView) itemView.findViewById(R.id.lbl_s_RegistrationNumber);
            lbl_s_LearnerId= (TextView) itemView.findViewById(R.id.lbl_s_LearnerId);
            lbl_s_University= (TextView) itemView.findViewById(R.id.lbl_s_University);
            lbl_s_Duration= (TextView) itemView.findViewById(R.id.lbl_s_Duration);
            lbl_s_StartDate= (TextView) itemView.findViewById(R.id.lbl_s_StartDate);
            lbl_s_EndDate= (TextView) itemView.findViewById(R.id.lbl_s_EndDate);
            lbl_s_WorkedDay= (TextView) itemView.findViewById(R.id.lbl_s_WorkedDay);
            lbl_s_LeaveDay= (TextView) itemView.findViewById(R.id.lbl_s_LeaveDay);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblAction2= (TextView) itemView.findViewById(R.id.lblAction2);
            lblAction3= (TextView) itemView.findViewById(R.id.lblAction3);
            lblAction4= (TextView) itemView.findViewById(R.id.lblAction4);
        }
    }
}
