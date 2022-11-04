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
import za.co.sacpo.grant.xCubeLib.dataObj.MLeavesObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.leaves.MLeaveApproval;
import za.co.sacpo.grant.xCubeMentor.leaves.MSLeaveDetailListA;

public class MLeavesAdapter extends RecyclerView.Adapter<MLeavesAdapter.LeavesHolder> {
    private List<MLeavesObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MSLeaveDetailListA baseActivity;
    public MLeavesAdapter(List<MLeavesObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MSLeaveDetailListA baseActivity) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.baseActivity = baseActivity;
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
    public void gotoLeaveApproval(String att_ids,String attendance_id,Context context){
        Bundle inputUri = new Bundle();
        inputUri.putString("student_id", baseActivity.getStudentId());
        inputUri.putString("m_student_name",baseActivity.getStudentName());
        inputUri.putString("generator", "M409");
        inputUri.putString("att_ids", att_ids);
        inputUri.putString("attendance_id", attendance_id);
        inputUri.putString("type", baseActivity.getType());
        Intent intent = new Intent(context, MLeaveApproval.class);
        intent.putExtras(inputUri);
        context.startActivity(intent);
    }
    @Override
    public MLeavesAdapter.LeavesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.m_leave_row, parent, false);
        return new MLeavesAdapter.LeavesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MLeavesAdapter.LeavesHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        int BColorRed = res.getColor(R.color.red_link);
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);

            Labels = this.getLabelFromDb("l_M409_txt_month", R.string.l_M409_txt_month);
            holder.tvMonth3.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_fromdate", R.string.l_M409_txt_fromdate);
            holder.tvFromDate4.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_todate", R.string.l_M409_txt_todate);
            holder.tvToDate5.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_annual", R.string.l_M409_txt_annual);
            holder.tvAnnualLeave6.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_sick", R.string.l_M409_txt_sick);
            holder.tvSickLeave7.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_opaid", R.string.l_M409_txt_opaid);
            holder.tvOPaidLeave8.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_unpaid", R.string.l_M409_txt_unpaid);
            holder.tvUPLeave9.setText(Labels);
            Labels = this.getLabelFromDb("l_M409_txt_motivation", R.string.l_M409_txt_motivation);
            holder.lblActionNotes.setText(Labels);


            holder.lblActionNotes.setTypeface(holder.lblActionNotes.getTypeface(), Typeface.BOLD);
            holder.lblActionNotes.setTextColor(res.getColor(R.color.white));
            holder.lblActionNotes.setVisibility(View.VISIBLE);
            holder.btnActionNotes.setVisibility(View.GONE);

            holder.tvMonth3.setTypeface(holder.tvMonth3.getTypeface(), Typeface.BOLD);
            holder.tvFromDate4.setTypeface(holder.tvFromDate4.getTypeface(), Typeface.BOLD);
            holder.tvToDate5.setTypeface(holder.tvToDate5.getTypeface(), Typeface.BOLD);
            holder.tvAnnualLeave6.setTypeface(holder.tvAnnualLeave6.getTypeface(), Typeface.BOLD);
            holder.tvSickLeave7.setTypeface(holder.tvSickLeave7.getTypeface(), Typeface.BOLD);
            holder.tvOPaidLeave8.setTypeface(holder.tvOPaidLeave8.getTypeface(), Typeface.BOLD);
            holder.tvUPLeave9.setTypeface(holder.tvUPLeave9.getTypeface(), Typeface.BOLD);
            holder.lblActionNotes.setTypeface(holder.lblActionNotes.getTypeface(), Typeface.BOLD);

            holder.linearActionNotes.setBackgroundColor(BColor);
            holder.tvMonth3.setBackgroundColor(BColor);
            holder.tvFromDate4.setBackgroundColor(BColor);
            holder.tvToDate5.setBackgroundColor(BColor);
            holder.tvAnnualLeave6.setBackgroundColor(BColor);
            holder.tvSickLeave7.setBackgroundColor(BColor);
            holder.tvOPaidLeave8.setBackgroundColor(BColor);
            holder.tvUPLeave9.setBackgroundColor(BColor);

            holder.tvMonth3.setTextColor(res.getColor(R.color.white));
            holder.tvFromDate4.setTextColor(res.getColor(R.color.white));
            holder.tvToDate5.setTextColor(res.getColor(R.color.white));
            holder.tvAnnualLeave6.setTextColor(res.getColor(R.color.white));
            holder.tvSickLeave7.setTextColor(res.getColor(R.color.white));
            holder.tvOPaidLeave8.setTextColor(res.getColor(R.color.white));
            holder.tvUPLeave9.setTextColor(res.getColor(R.color.white));
        }
        else{

            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            holder.lblActionNotes.setVisibility(View.GONE);
            holder.btnActionNotes.setVisibility(View.VISIBLE);
            Labels = this.getLabelFromDb("l_M409_btn_view", R.string.l_M409_btn_view);
            holder.btnActionNotes.setText(Labels);

            holder.tvMonth3.setText(aObjList.get(holder.getAdapterPosition()).month3);
            holder.tvFromDate4.setText(aObjList.get(holder.getAdapterPosition()).fromDate4);
            holder.tvToDate5.setText(aObjList.get(holder.getAdapterPosition()).toDate5);
            holder.tvAnnualLeave6.setText(aObjList.get(holder.getAdapterPosition()).annualLeave6);
            holder.tvSickLeave7.setText(aObjList.get(holder.getAdapterPosition()).sickLeave7);
            holder.tvOPaidLeave8.setText(aObjList.get(holder.getAdapterPosition()).oPaidLeave8);
            holder.tvUPLeave9.setText(aObjList.get(holder.getAdapterPosition()).uPLeave9);
            holder.tvMonth3.setBackgroundColor(BColor);
            holder.tvFromDate4.setBackgroundColor(BColor);
            holder.tvToDate5.setBackgroundColor(BColor);
            holder.tvAnnualLeave6.setBackgroundColor(BColor);
            holder.tvSickLeave7.setBackgroundColor(BColor);
            holder.tvOPaidLeave8.setBackgroundColor(BColor);
            holder.tvUPLeave9.setBackgroundColor(BColor);

            String type = baseActivity.getType();
            if(type.equalsIgnoreCase("p")) {
                if(!aObjList.get(holder.getAdapterPosition()).annualLeave6.equalsIgnoreCase("-")) {
                    if (Integer.parseInt(aObjList.get(holder.getAdapterPosition()).annualLeave6) > 0) {
                        holder.tvAnnualLeave6.setBackgroundColor(BColorRed);
                        holder.tvAnnualLeave6.setTextColor(res.getColor(R.color.row_link));
                        holder.tvAnnualLeave6.setPaintFlags(holder.tvAnnualLeave6.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        holder.tvAnnualLeave6.getPaint().setUnderlineText(true);
                        holder.tvAnnualLeave6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String att_ids = aObjList.get(holder.getAdapterPosition()).attIds11;
                                String attendance_id = String.valueOf(holder.hItem.aId2);
                                gotoLeaveApproval(att_ids,attendance_id,v.getContext());
                            }
                        });
                    }
                }
                if(!aObjList.get(holder.getAdapterPosition()).sickLeave7.equalsIgnoreCase("-")) {
                    if (Integer.parseInt(aObjList.get(holder.getAdapterPosition()).sickLeave7) > 0) {
                        holder.tvSickLeave7.setBackgroundColor(BColorRed);
                        holder.tvSickLeave7.setTextColor(res.getColor(R.color.row_link));
                        holder.tvSickLeave7.setPaintFlags(holder.tvSickLeave7.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        holder.tvSickLeave7.getPaint().setUnderlineText(true);
                        holder.tvSickLeave7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String att_ids = aObjList.get(holder.getAdapterPosition()).attIds11;
                                String attendance_id = String.valueOf(holder.hItem.aId2);
                                gotoLeaveApproval(att_ids,attendance_id,v.getContext());

                            }
                        });
                    }
                }
                if(!aObjList.get(holder.getAdapterPosition()).oPaidLeave8.equalsIgnoreCase("-")) {
                    if (Integer.parseInt(aObjList.get(holder.getAdapterPosition()).oPaidLeave8) > 0) {
                        holder.tvOPaidLeave8.setBackgroundColor(BColorRed);
                        holder.tvOPaidLeave8.setTextColor(res.getColor(R.color.row_link));
                        holder.tvOPaidLeave8.setPaintFlags(holder.tvOPaidLeave8.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        holder.tvOPaidLeave8.getPaint().setUnderlineText(true);
                        holder.tvOPaidLeave8.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String att_ids = aObjList.get(holder.getAdapterPosition()).attIds11;
                                String attendance_id = String.valueOf(holder.hItem.aId2);
                                gotoLeaveApproval(att_ids,attendance_id,v.getContext());

                            }
                        });
                    }
                }
                if(!aObjList.get(holder.getAdapterPosition()).uPLeave9.equalsIgnoreCase("-")) {
                    if (Integer.parseInt(aObjList.get(holder.getAdapterPosition()).uPLeave9) > 0) {
                        holder.tvUPLeave9.setBackgroundColor(BColorRed);
                        holder.tvUPLeave9.setTextColor(res.getColor(R.color.row_link));
                        holder.tvUPLeave9.setPaintFlags(holder.tvUPLeave9.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        holder.tvUPLeave9.getPaint().setUnderlineText(true);
                        holder.tvUPLeave9.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String att_ids = aObjList.get(holder.getAdapterPosition()).attIds11;
                                String attendance_id = String.valueOf(holder.hItem.aId2);
                                gotoLeaveApproval(att_ids,attendance_id,v.getContext());

                            }
                        });
                    }
                }
            }
           /* holder.tvAnnualLeave6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();
                    String leave_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("leave_id", leave_id);
                    inputUri.putString("student_id",baseActivity.getStudentId());
                    inputUri.putString("generator", "310");
                    inputUri.putString("student_name",baseActivity.getStudentName());
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MLeaveApproval.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });*/

            holder.btnActionNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle inputUri = new Bundle();
                    String attendance_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("attendance_id", attendance_id);
                    inputUri.putString("generator", "M409");
                    inputUri.putString("student_id", baseActivity.getStudentId());
                    inputUri.putString("m_student_name",baseActivity.getStudentName());
                    inputUri.putString("type", baseActivity.getType());
                    Context context = v.getContext();
                    Intent intent = new Intent(context,MLeaveApproval.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }
        String type = baseActivity.getType();
        if(type.equalsIgnoreCase("p")){
            holder.linearActionNotes.setVisibility(View.GONE);
            ///link.. background red
        }
        else{
            holder.linearActionNotes.setVisibility(View.VISIBLE);
        }

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
        public MLeavesObj.Item hItem;
        public final TableRow tRow;
        public final TextView tvMonth3,tvFromDate4,tvToDate5,tvAnnualLeave6,tvSickLeave7,tvOPaidLeave8,tvUPLeave9,lblActionNotes;
        public final LinearLayout linearActionNotes;
        public final Button btnActionNotes;
        public LeavesHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.m_leave_Row);
            tvMonth3=itemView.findViewById(R.id.tvMonth3);
            tvFromDate4=itemView.findViewById(R.id.tvFromDate4);
            tvToDate5=itemView.findViewById(R.id.tvToDate5);
            tvAnnualLeave6=itemView.findViewById(R.id.tvAnnualLeave6);
            tvSickLeave7=itemView.findViewById(R.id.tvSickLeave7);
            tvOPaidLeave8=itemView.findViewById(R.id.tvOPaidLeave8);
            tvUPLeave9=itemView.findViewById(R.id.tvUPLeave9);
            lblActionNotes=itemView.findViewById(R.id.lblActionNotes);
            btnActionNotes=itemView.findViewById(R.id.btnActionNotes);
            linearActionNotes=itemView.findViewById(R.id.linearActionNotes);
        }
    }
}
