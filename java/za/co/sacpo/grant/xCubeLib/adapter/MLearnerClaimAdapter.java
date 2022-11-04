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
import za.co.sacpo.grant.xCubeLib.dataObj.MLearnerClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.claims.MClaimAttList;
import za.co.sacpo.grant.xCubeMentor.claims.MLearnerAbsentList;
import za.co.sacpo.grant.xCubeMentor.claims.MLearnerClaimList;
import za.co.sacpo.grant.xCubeMentor.claims.MLearnerLeaveListA;

public class MLearnerClaimAdapter extends RecyclerView.Adapter<MLearnerClaimAdapter.ClaimHolder> {
    private List<MLearnerClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels,clailExit;
    MLearnerClaimList baseActivity;
    String studentName;

    public MLearnerClaimAdapter(List<MLearnerClaimObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,String studentName, MLearnerClaimList baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
        this.studentName = studentName;
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
    public ClaimHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_learnerclaimlist, parent, false);
        return new ClaimHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClaimHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblClaimDate.setTextColor(res.getColor(R.color.white));
            holder.lblClaimDate.setTypeface(holder.lblClaimDate.getTypeface(), Typeface.BOLD);

/*

            Labels =this.getLabelFromDb("l_605_SClaim_download",R.string.l_605_SClaim_download);
            holder.lblAdminAmmount.setText(Labels);
            holder.lblAdminAmmount.setTextColor(res.getColor(R.color.white));
            holder.lblAdminAmmount.setVisibility(View.VISIBLE);
            holder.btnActionForm.setVisibility(View.GONE);
*/



            holder.lblMAmmount.setTypeface(holder.lblMAmmount.getTypeface(), Typeface.BOLD);
            holder.lblAdminAmmount.setTypeface(holder.lblAdminAmmount.getTypeface(), Typeface.BOLD);


            holder.lblClaimMonth.setTextColor(res.getColor(R.color.white));
            holder.lblClaimMonth.setTypeface(holder.lblClaimMonth.getTypeface(), Typeface.BOLD);

            holder.lblClaimYear.setTextColor(res.getColor(R.color.white));
            holder.lblClaimYear.setTypeface(holder.lblClaimYear.getTypeface(), Typeface.BOLD);

             holder.lblClaimAmount.setTextColor(res.getColor(R.color.white));
            holder.lblClaimAmount.setTypeface(holder.lblClaimAmount.getTypeface(), Typeface.BOLD);

            holder.lblDaysWorked.setTextColor(res.getColor(R.color.white));
            holder.lblDaysWorked.setTypeface(holder.lblDaysWorked.getTypeface(), Typeface.BOLD);

            holder.lblLeaveCount.setTextColor(res.getColor(R.color.white));
            holder.lblLeaveCount.setTypeface(holder.lblLeaveCount.getTypeface(), Typeface.BOLD);

            holder.lblAbsentCount.setTextColor(res.getColor(R.color.white));
            holder.lblAbsentCount.setTypeface(holder.lblAbsentCount.getTypeface(), Typeface.BOLD);

            holder.lblMstatus.setTextColor(res.getColor(R.color.white));
            holder.lblMstatus.setTypeface(holder.lblMstatus.getTypeface(), Typeface.BOLD);

            holder.lblMDate.setTextColor(res.getColor(R.color.white));
            holder.lblMDate.setTypeface(holder.lblMDate.getTypeface(), Typeface.BOLD);


            holder.lblAdminAmmount.setTextColor(res.getColor(R.color.white));
            holder.lblAdminAmmount.setTypeface(holder.lblAdminAmmount.getTypeface(), Typeface.BOLD);

            holder.lblMAmmount.setTextColor(res.getColor(R.color.white));
            holder.lblMAmmount.setTypeface(holder.lblMAmmount.getTypeface(), Typeface.BOLD);


            holder.lblAproveDate.setTextColor(res.getColor(R.color.white));
            holder.lblAproveDate.setTypeface(holder.lblAproveDate.getTypeface(), Typeface.BOLD);


            holder.lblDaysWorked.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).dayswork6;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblDaysWorked,sToolTip,4);
                }
            });

            holder.lblLeaveCount.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).leavecount7;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveCount,sToolTip,4);
                }
            });

            holder.lblAbsentCount.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    String sToolTip=aObjList.get(holder.getAdapterPosition()).absentcount8;
                    ((BaseAPC)activityInCall).showTooltip(holder.lblAbsentCount,sToolTip,4);
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




            if(aObjList.get(holder.getAdapterPosition()).dayswork6.equals("0")){


                //DO NOTHING FOR NOW


/*
                holder.lblDaysWorked.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String sToolTip=aObjList.get(holder.getAdapterPosition()).dayswork6;
                        ((BaseAPC)activityInCall).showTooltip(holder.lblDaysWorked,sToolTip,4);
                    }
                });*/

            }

            else {


                holder.lblDaysWorked.setTextColor(res.getColor(R.color.row_link));
                holder.lblDaysWorked.setPaintFlags(holder.lblDaysWorked.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblDaysWorked.getPaint().setUnderlineText(true);


                holder.lblDaysWorked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();
                        String month_year = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("month_year", month_year);
                        String student_id = String.valueOf(holder.hItem.claStudentId14);
                        inputUri.putString("student_id", student_id);

                        String present_type = String.valueOf(holder.hItem.present_type17);
                        inputUri.putString("present_type", present_type);

                        String grant_id = String.valueOf(holder.hItem.claGrantId15);
                        inputUri.putString("grant_id", grant_id);

                        String status = String.valueOf(holder.hItem.claStatus16);
                        inputUri.putString("status", status);

                        String month = String.valueOf(holder.hItem.month20);
                        inputUri.putString("month", month);



                        String year = String.valueOf(holder.hItem.year21);
                        inputUri.putString("year", year);
                        inputUri.putString("studentName", studentName);

                        inputUri.putString("generator", "517");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MClaimAttList.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                    }
                });
            }

            if(aObjList.get(holder.getAdapterPosition()).absent_type19.equals("0"))

            {

                //DO NOTHING FOR NOW
            }

            else {



                holder.lblAbsentCount.setTextColor(res.getColor(R.color.row_link));
                holder.lblAbsentCount.setPaintFlags(holder.lblAbsentCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblAbsentCount.getPaint().setUnderlineText(true);




                holder.lblAbsentCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();
                        String month_year = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("month_year", month_year);
                        String student_id = String.valueOf(holder.hItem.claStudentId14);
                        inputUri.putString("student_id", student_id);

                        String absent_type = String.valueOf(holder.hItem.absent_type19);
                        inputUri.putString("absent_type", absent_type);

                        String grant_id = String.valueOf(holder.hItem.claGrantId15);
                        inputUri.putString("grant_id", grant_id);

                        String status = String.valueOf(holder.hItem.claStatus16);
                        inputUri.putString("status", status);
                        inputUri.putString("studentName", studentName);
                        String month = String.valueOf(holder.hItem.month20);
                        inputUri.putString("month", month);



                        String year = String.valueOf(holder.hItem.year21);
                        inputUri.putString("year", year);


                        inputUri.putString("generator", "517");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MLearnerAbsentList.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                    }
                });
            }
            if(aObjList.get(holder.getAdapterPosition()).leave_type18.equals("0"))

            {

                //DO NOTHING FOR NOW

               /* holder.lblLeaveCount.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String sToolTip=aObjList.get(holder.getAdapterPosition()).leavecount7;
                        ((BaseAPC)activityInCall).showTooltip(holder.lblLeaveCount,sToolTip,4);
                    }
                });*/

            }
            else {


                holder.lblLeaveCount.setTextColor(res.getColor(R.color.row_link));
                holder.lblLeaveCount.setPaintFlags(holder.lblLeaveCount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblLeaveCount.getPaint().setUnderlineText(true);

                holder.lblLeaveCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle inputUri = new Bundle();
                        String month_year = String.valueOf(holder.hItem.aId2);
                        inputUri.putString("month_year", month_year);
                        String student_id = String.valueOf(holder.hItem.claStudentId14);
                        inputUri.putString("student_id", student_id);

                        String leave_type = String.valueOf(holder.hItem.leave_type18);
                        inputUri.putString("leave_type", leave_type);

                        String grant_id = String.valueOf(holder.hItem.claGrantId15);
                        inputUri.putString("grant_id", grant_id);

                        String status = String.valueOf(holder.hItem.claStatus16);
                        inputUri.putString("status", status);

                        String month = String.valueOf(holder.hItem.month20);
                        inputUri.putString("month", month);

                        inputUri.putString("studentName", studentName);
                        String year = String.valueOf(holder.hItem.year21);
                        inputUri.putString("year", year);


                        inputUri.putString("generator", "517");
                        Context context = view.getContext();
                        Intent intent = new Intent(context, MLearnerLeaveListA.class);
                        intent.putExtras(inputUri);
                        context.startActivity(intent);

                    }
                });

            }

        }


        holder.lblClaimDate.setText(aObjList.get(holder.getAdapterPosition()).claDate3);
        holder.lblClaimDate.setBackgroundColor(BColor);
/*

        holder.btnUserApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aObjList.get(holder.getAdapterPosition()).studentStatus9.equals("1")) {

                }
                else {
                    Bundle inputUri = new Bundle();
                    String stipend_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("stipend_id", stipend_id);
                    inputUri.putString("m_student_name", baseActivity.getStudentName());
                    inputUri.putString("student_id",baseActivity.getStudentId());
                    Context context = view.getContext();
                    Intent intent = new Intent(context, GAApprovalClaim.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            }
        });

        //SUBMIT CLAIM BY STUDNET
        holder.btnActionForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(view,aObjList.get(holder.getAdapterPosition()).aId2,aObjList.get(holder.getAdapterPosition()).claimDownloadStatus15,aObjList.get(holder.getAdapterPosition()).claimUploadStatus16);


            }
        });

        holder.linearLActionUserApproval.setBackgroundColor(BColor);
        if(aObjList.get(holder.getAdapterPosition()).mentorStatus10.equals("1")) {
            Labels = this.getLabelFromDb("l_605_M_APPROVAL_SUBMITTED", R.string.l_605_M_APPROVAL_SUBMITTED);
            holder.btnUserApproval.setBackground(res.getDrawable(R.drawable.themed_small_button_disabled));
            holder.btnUserApproval.setTextColor(res.getColor(R.color.white));
        }
        else if(aObjList.get(holder.getAdapterPosition()).studentStatus9.equals("1")){
            Labels = this.getLabelFromDb("l_605_M_APPROVAL_NEW", R.string.l_605_M_APPROVAL_NEW);
        }
        else{
            Labels = "-";
        }
        holder.btnUserApproval.setText(Labels);
*/

        holder.lblClaimDate.setText(aObjList.get(holder.getAdapterPosition()).claDate3);
        holder.lblClaimDate.setBackgroundColor(BColor);

        holder.lblClaimMonth.setText(aObjList.get(holder.getAdapterPosition()).claMonth4);
        holder.lblClaimMonth.setBackgroundColor(BColor);

        holder.lblClaimYear.setText(aObjList.get(holder.getAdapterPosition()).year21);
        holder.lblClaimYear.setBackgroundColor(BColor);

        holder.lblClaimAmount.setText(aObjList.get(holder.getAdapterPosition()).cla_ammount5);
        holder.lblClaimAmount.setBackgroundColor(BColor);

        holder.lblDaysWorked.setText(aObjList.get(holder.getAdapterPosition()).dayswork6);
        holder.lblDaysWorked.setBackgroundColor(BColor);

        holder.lblLeaveCount.setText(aObjList.get(holder.getAdapterPosition()).leavecount7);
        holder.lblLeaveCount.setBackgroundColor(BColor);

        holder.lblAbsentCount.setText(aObjList.get(holder.getAdapterPosition()).absentcount8);
        holder.lblAbsentCount.setBackgroundColor(BColor);

        holder.lblMstatus.setText(aObjList.get(holder.getAdapterPosition()).m_status9);
        holder.lblMstatus.setBackgroundColor(BColor);

        holder.lblMDate.setText(aObjList.get(holder.getAdapterPosition()).m_date10);
        holder.lblMDate.setBackgroundColor(BColor);

        holder.lblMAmmount.setText(aObjList.get(holder.getAdapterPosition()).m_ammount_status11);
        holder.lblMAmmount.setBackgroundColor(BColor);



        holder.lblAdminAmmount.setText(aObjList.get(holder.getAdapterPosition()).admin_approve_ammount12);
        holder.lblAdminAmmount.setBackgroundColor(BColor);

         holder.lblAproveDate.setText(aObjList.get(holder.getAdapterPosition()).admin_approve_date13);
         holder.lblAproveDate.setBackgroundColor(BColor);





        holder.lblClaimDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).claDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimDate,sToolTip, 4);
            }
        });
        holder.lblClaimMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).claMonth4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimMonth,sToolTip,4);
            }
        });
        holder.lblClaimYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).year21;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimYear,sToolTip,4);
            }
        });

  holder.lblClaimAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).cla_ammount5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblClaimAmount,sToolTip,4);
            }
        });



        holder.lblMstatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).m_status9;
                ((BaseAPC)activityInCall).showTooltip(holder.lblMstatus,sToolTip,4);
            }
        });

        holder.lblMDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).m_date10;
                ((BaseAPC)activityInCall).showTooltip(holder.lblMDate,sToolTip,4);
            }
        });  holder.lblMAmmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).m_ammount_status11;
                ((BaseAPC)activityInCall).showTooltip(holder.lblMAmmount,sToolTip,4);
            }
        });
        holder.lblAdminAmmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).admin_approve_ammount12;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAdminAmmount,sToolTip,4);
            }
        });
        holder.lblAproveDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).admin_approve_date13;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAproveDate,sToolTip,4);
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

    public class ClaimHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public MLearnerClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblMAmmount,lblClaimDate,lblClaimMonth,lblClaimYear,lblClaimAmount,lblDaysWorked,lblLeaveCount,lblAbsentCount,
                lblMstatus,lblMDate,lblAdminAmmount,lblAproveDate;

        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.ga_Learner_claim_Row);

            lblClaimDate= (TextView) itemView.findViewById(R.id.lblClaimDate);
            lblClaimMonth= (TextView) itemView.findViewById(R.id.lblClaimMonth);
            lblClaimYear= (TextView) itemView.findViewById(R.id.lblClaimYear);
            lblClaimAmount= (TextView) itemView.findViewById(R.id.lblClaimAmount);
            lblDaysWorked= (TextView) itemView.findViewById(R.id.lblDaysWorked);
            lblLeaveCount= (TextView) itemView.findViewById(R.id.lblLeaveCount);
            lblAbsentCount= (TextView) itemView.findViewById(R.id.lblAbsentCount);
            lblMstatus= (TextView) itemView.findViewById(R.id.lblMstatus);
            lblMDate= (TextView) itemView.findViewById(R.id.lblMDate);
            lblMAmmount= (TextView) itemView.findViewById(R.id.lblMAmmount);
            lblAdminAmmount= (TextView) itemView.findViewById(R.id.lblAdminAmmount);
            lblAproveDate= (TextView) itemView.findViewById(R.id.lblAproveDate);


        }
    }
}
