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

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.SFeedbackObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeStudent.feedback.SEditFeedbackA;
import za.co.sacpo.grant.xCubeStudent.feedback.SRemoveFeedbackA;
import za.co.sacpo.grant.xCubeStudent.feedback.SReportDetailsA;


public class SFeedbackAdapter extends RecyclerView.Adapter<SFeedbackAdapter.LeavesHolder> {
    private List<SFeedbackObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
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

    public SFeedbackAdapter(List<SFeedbackObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
    }

    public String getLabelFromDb(String inputLabel, int resId) {
        String ValueLabel = baseActivityContext.getString(resId);
        dbSetaObj = DbHelper.getInstance(baseActivityContext);
        Cursor res = dbSetaObj.getDataValueByName(inputLabel);
        if (res.getCount() > 0) {
            try {
                while (res.moveToNext()) {
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
    public LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.s_feedback_row, parent, false);
        return new LeavesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());
        int BColor;
        if (holder.getAdapterPosition() == 0) {
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.lblSReportNo.setTypeface(holder.lblSReportNo.getTypeface(), Typeface.BOLD);
            holder.lblSReportNo.setTextColor(res.getColor(R.color.white));

            holder.TxtYear.setTypeface(holder.TxtYear.getTypeface(), Typeface.BOLD);
            holder.TxtYear.setTextColor(res.getColor(R.color.white));
            holder.TxtMonth.setTypeface(holder.TxtMonth.getTypeface(), Typeface.BOLD);
            holder.TxtMonth.setTextColor(res.getColor(R.color.white));
            holder.lblSRepotStatus.setTypeface(holder.lblSRepotStatus.getTypeface(), Typeface.BOLD);
            holder.lblSRepotStatus.setTextColor(res.getColor(R.color.white));
            holder.lblSTitle.setTypeface(holder.lblSTitle.getTypeface(), Typeface.BOLD);
            holder.lblSTitle.setTextColor(res.getColor(R.color.white));
            Labels = this.getLabelFromDb("b_174_edit", R.string.b_174_edit);
            holder.lblActionEdit.setText(Labels);
            holder.lblActionEdit.setTextColor(res.getColor(R.color.white));
            holder.lblActionEdit.setTypeface(holder.lblActionEdit.getTypeface(), Typeface.BOLD);
            holder.lblActionEdit.setVisibility(View.VISIBLE);
            holder.btnActionEdit.setVisibility(View.GONE);

            Labels = this.getLabelFromDb("b_174_remove", R.string.b_174_remove);
            holder.lblActionRemove.setText(Labels);
            holder.lblActionRemove.setTextColor(res.getColor(R.color.white));
            holder.lblActionRemove.setTypeface(holder.lblActionRemove.getTypeface(), Typeface.BOLD);
            holder.lblActionRemove.setVisibility(View.VISIBLE);
            holder.btnActionRemove.setVisibility(View.GONE);

            Labels = this.getLabelFromDb("b_174_details", R.string.b_174_details);
            holder.lblActionDetails.setText(Labels);
            holder.lblActionDetails.setTextColor(res.getColor(R.color.white));
            holder.lblActionDetails.setVisibility(View.VISIBLE);
            holder.btnActionDetails.setVisibility(View.GONE);

            holder.lblSTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sToolTip = aObjList.get(holder.getAdapterPosition()).ReportTitle4;
                    ((BaseAPC) activityInCall).showTooltip(holder.lblSTitle, sToolTip, 4);
                }
            });


        } else {
            holder.lblActionEdit.setVisibility(View.GONE);
            holder.btnActionEdit.setVisibility(View.VISIBLE);
            holder.lblActionRemove.setVisibility(View.GONE);
            holder.btnActionRemove.setVisibility(View.VISIBLE);
            holder.lblActionDetails.setVisibility(View.GONE);
            holder.btnActionDetails.setVisibility(View.VISIBLE);
            if ((holder.getAdapterPosition() % 2) == 0) {
                BColor = res.getColor(R.color.row_even);
            } else {
                BColor = res.getColor(R.color.row_odd);
            }

            holder.lblSTitle.setTextColor(res.getColor(R.color.row_link));
            holder.lblSTitle.setPaintFlags(holder.lblSTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblSTitle.getPaint().setUnderlineText(true);

            holder.lblSReportNo.setTextColor(res.getColor(R.color.row_link));
            holder.lblSReportNo.setPaintFlags(holder.lblSReportNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.lblSReportNo.getPaint().setUnderlineText(true);

            holder.lblSTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "174");
                    String date_input = String.valueOf(holder.hItem.Month5);
                    inputUri.putString("month_year", date_input);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SReportDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);

                }
            });
            holder.lblSReportNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();
                    String report_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("report_id", report_id);
                    inputUri.putString("generator", "174");
                    String date_input = String.valueOf(holder.hItem.Month5);
                    inputUri.putString("month_year", date_input);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SReportDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);

                }
            });
        }

        holder.lblSReportNo.setText(aObjList.get(holder.getAdapterPosition()).ReportNo3);
        holder.lblSReportNo.setBackgroundColor(BColor);

        holder.TxtYear.setText(aObjList.get(holder.getAdapterPosition()).Year6);
        holder.TxtYear.setBackgroundColor(BColor);

        holder.TxtMonth.setText(aObjList.get(holder.getAdapterPosition()).Month5);
        holder.TxtMonth.setBackgroundColor(BColor);
        holder.lblSRepotStatus.setBackgroundColor(BColor);
        if (holder.getAdapterPosition() != 0) {
            int supervisorStatusId = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).SupervisorStatusId8);
            if(supervisorStatusId==0) {
                holder.lblSRepotStatus.setBackgroundColor(res.getColor(R.color.red_link));
            }
            int edit_button = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).EditBtn9);
            if(edit_button==1) {
                holder.btnActionEdit.setVisibility(View.VISIBLE);
                holder.btnActionEdit.setTextColor(baseActivityContext.getResources().getColor(baseAPC.getTextcolorResourceId("btn_textColor")));
                holder.btnActionEdit.setBackground(baseActivityContext.getResources().getDrawable(baseAPC.getDrwabaleResourceId("themed_small_button")));
                holder.lblActionEdit.setVisibility(View.GONE);
            }
            else{
                holder.btnActionEdit.setVisibility(View.GONE);
                holder.lblActionEdit.setVisibility(View.VISIBLE);
            }
        }
        holder.lblSRepotStatus.setText(aObjList.get(holder.getAdapterPosition()).SupervisorStatus7);


        holder.lblSTitle.setText(aObjList.get(holder.getAdapterPosition()).ReportTitle4);
        holder.lblSTitle.setBackgroundColor(BColor);

        holder.linearEdit.setBackgroundColor(BColor);
        Labels = this.getLabelFromDb("b_174_edit", R.string.b_174_edit);
        holder.btnActionEdit.setText(Labels);

        holder.linearRemove.setBackgroundColor(BColor);
        Labels = this.getLabelFromDb("b_174_remove", R.string.b_174_remove);
        holder.btnActionRemove.setText(Labels);

        holder.linearDetails.setBackgroundColor(BColor);
        Labels = this.getLabelFromDb("b_174_details", R.string.b_174_details);
        holder.btnActionDetails.setText(Labels);


        holder.TxtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).Year6;
                ((BaseAPC) activityInCall).showTooltip(holder.TxtYear, sToolTip, 4);
            }
        });
        holder.TxtMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).Month5;
                ((BaseAPC) activityInCall).showTooltip(holder.TxtMonth, sToolTip, 4);
            }
        });

        holder.lblSRepotStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sToolTip = aObjList.get(holder.getAdapterPosition()).SupervisorStatus7;
                ((BaseAPC) activityInCall).showTooltip(holder.lblSRepotStatus, sToolTip, 4);
            }
        });


        holder.btnActionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle inputUri = new Bundle();
                String s_w_r_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("s_w_r_id", s_w_r_id);
                inputUri.putString("generator", "174");
                String date = String.valueOf(holder.hItem.Year6);
                inputUri.putString("date", date);
                String date_input = String.valueOf(holder.hItem.Month5);
                inputUri.putString("date", date_input);
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
                String date = String.valueOf(holder.hItem.Year6);
                inputUri.putString("date", date);

                String feedback = String.valueOf(holder.hItem.ReportTitle4);

                inputUri.putString("feedback", feedback);
                inputUri.putString("generator", "174");

                String date_input = String.valueOf(holder.hItem.Month5);
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
        if (aObjList != null) {
            return aObjList.size();
        }
        return 0;
    }

    public class LeavesHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SFeedbackObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblSTitle, TxtYear, TxtMonth, lblSReportNo,lblSRepotStatus;
        LinearLayout linearEdit, linearRemove, linearDetails;
        TextView lblActionEdit, lblActionRemove, lblActionDetails;
        Button btnActionEdit, btnActionRemove, btnActionDetails;

        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (TableRow) itemView.findViewById(R.id.tRowFeedback);
            TxtYear = (TextView) itemView.findViewById(R.id.TxtYear);
            TxtMonth = (TextView) itemView.findViewById(R.id.TxtMonth);

            lblSReportNo= (TextView) itemView.findViewById(R.id.lblSReportNo);
            lblSTitle = (TextView) itemView.findViewById(R.id.lblSTitle);
            lblSRepotStatus = itemView.findViewById(R.id.lblSRepotStatus);
            linearEdit = (LinearLayout) itemView.findViewById(R.id.linearEdit);
            linearRemove = (LinearLayout) itemView.findViewById(R.id.linearRemove);
            linearDetails = (LinearLayout) itemView.findViewById(R.id.linearDetails);

            lblActionEdit = (TextView) itemView.findViewById(R.id.lblActionEdit);
            lblActionRemove = (TextView) itemView.findViewById(R.id.lblActionRemove);
            lblActionDetails = (TextView) itemView.findViewById(R.id.lblActionDetails);

            btnActionEdit = (Button) itemView.findViewById(R.id.btnActionEdit);
            btnActionRemove = (Button) itemView.findViewById(R.id.btnActionRemove);
            btnActionDetails = (Button) itemView.findViewById(R.id.btnActionDetails);


        }
    }
}
