package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MAttSummaryObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeMentor.attendance.MAttDetailsA;
import za.co.sacpo.grantportal.xCubeMentor.attendance.MAttMonthlyA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MAttSummaryAdapter extends RecyclerView.Adapter<MAttSummaryAdapter.ViewHolder> {
    private List<MAttSummaryObj.Item> aObjList;

    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    String remark;
    String approve,disapprove;
    private RadioButton lastCheckedRB = null;
    private RadioGroup lastCheckedRadioGroup = null;
    RecyclerView recyclerViewQ;

    int flag;
    private ArrayList<String> approveListId=new ArrayList<>();
    private ArrayList<String> remarksArrList=new ArrayList<>();
    private ArrayList<String> disApproveListId=new ArrayList<>();

    private Map<String, String> approvalStatusMap = new HashMap<String, String>();
    private Map<String, String> inputRemarkMap = new HashMap<String, String>();

    String typeId;


    MAttMonthlyA baseActivity;
    private double total;
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

    public MAttSummaryAdapter(List<MAttSummaryObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MAttMonthlyA baseActivity) {
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_m_att_summary, parent, false);
        return new ViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;


        if (position == 0) {
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.lblTime.setTextColor(res.getColor(R.color.white));
            holder.lblTime.setTypeface(holder.lblTime.getTypeface(), Typeface.BOLD);

            holder.lblDate.setTextColor(res.getColor(R.color.white));
            holder.lblDate.setTypeface(holder.lblDate.getTypeface(), Typeface.BOLD);

            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);

            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);

            holder.lblLogOutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogOutTime.setTypeface(holder.lblLogOutTime.getTypeface(), Typeface.BOLD);

            holder.lblCordDifference.setTextColor(res.getColor(R.color.white));
            holder.lblCordDifference.setTypeface(holder.lblCordDifference.getTypeface(), Typeface.BOLD);

            holder.lblAttendanceStatus.setTextColor(res.getColor(R.color.white));
            holder.lblAttendanceStatus.setTypeface(holder.lblAttendanceStatus.getTypeface(), Typeface.BOLD);

            holder.lblLComment.setTextColor(res.getColor(R.color.white));
            holder.lblLComment.setTypeface(holder.lblLComment.getTypeface(), Typeface.BOLD);
            holder.lblLComment.setText(aObjList.get(holder.getAdapterPosition()).remark11);
        }

        else {
            if ((position % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }

            if(aObjList.get(holder.getAdapterPosition()).remark11.equalsIgnoreCase("0")) {
                holder.lblLComment.setText("-");
            }
            else{
                holder.lblLComment.setTextColor(res.getColor(R.color.row_link));
                holder.lblLComment.setPaintFlags(holder.lblLComment.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.lblLComment.getPaint().setUnderlineText(true);
                holder.lblLComment.setText("VIEW");
            }
            holder.lblCordDifference.setTextColor(res.getColor(R.color.row_link));
            holder.lblCordDifference.setPaintFlags(holder.lblCordDifference.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblCordDifference.getPaint().setUnderlineText(true);


            holder.lblCordDifference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String attendance_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("attendance_id", attendance_id);
                    inputUri.putString("date_input", baseActivity.getDateInput());
                    inputUri.putString("month", baseActivity.getMonth());
                    inputUri.putString("year", baseActivity.getYear());
                    inputUri.putString("m_student_name", baseActivity.getStudentName());
                    inputUri.putString("generator", "M405");


                    Context context = view.getContext();
                    Intent intent = new Intent(context, MAttDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });

            holder.lblLComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle inputUri = new Bundle();
                    String attendance_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("attendance_id", attendance_id);
                    inputUri.putString("date_input", baseActivity.getDateInput());
                    inputUri.putString("month", baseActivity.getMonth());
                    inputUri.putString("year", baseActivity.getYear());
                    inputUri.putString("m_student_name", baseActivity.getStudentName());
                    inputUri.putString("generator", "M405");


                    Context context = view.getContext();
                    Intent intent = new Intent(context, MAttDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });


        }


            holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).mVADate3);
            holder.lblDate.setBackgroundColor(BColor);

            holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).mVADay4);
            holder.lblDay.setBackgroundColor(BColor);

            holder.lblTime.setText(aObjList.get(holder.getAdapterPosition()).mVATime5);
            holder.lblTime.setBackgroundColor(BColor);

            holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).lblLoginTime7);
            holder.lblLoginTime.setBackgroundColor(BColor);

            holder.lblLogOutTime.setText(aObjList.get(holder.getAdapterPosition()).lblLogoutTime6);
            holder.lblLogOutTime.setBackgroundColor(BColor);

            holder.lblCordDifference.setText(aObjList.get(holder.getAdapterPosition()).mcord_diff8);
            holder.lblCordDifference.setBackgroundColor(BColor);

            holder.lblAttendanceStatus.setText(aObjList.get(holder.getAdapterPosition()).approval_status10);
            holder.lblAttendanceStatus.setBackgroundColor(BColor);

            holder.lblLComment.setBackgroundColor(BColor);

            holder.lblTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVATime5;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblTime, sToolTip, 4);
                }
            });
            holder.lblDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVADay4;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblDay, sToolTip, 4);
                }
            });

            holder.lblDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).mVADate3;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblDate, sToolTip, 4);
                }
            });
            holder.lblLoginTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).lblLoginTime7;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblLoginTime, sToolTip, 4);
                }
            });
            holder.lblLogOutTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).lblLogoutTime6;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblLogOutTime, sToolTip, 4);
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
        public MAttSummaryObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblTime, lblDay, lblDate, lblAttendanceStatus,lblLComment,lblLoginTime,lblLogOutTime,lblCordDifference;

        public ViewHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (TableRow) itemView.findViewById(R.id.mViewAttList_Row);
            lblAttendanceStatus = (TextView) itemView.findViewById(R.id.lblAttendanceStatus);
            lblLComment = (TextView) itemView.findViewById(R.id.lblLComment);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            lblDay = (TextView) itemView.findViewById(R.id.lblDay);
            lblTime = (TextView) itemView.findViewById(R.id.lblTime);
            lblLoginTime = (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogOutTime = (TextView) itemView.findViewById(R.id.lblLogOutTime);
            lblCordDifference = (TextView) itemView.findViewById(R.id.lblCordDifference); 
             
        }
    }
}
