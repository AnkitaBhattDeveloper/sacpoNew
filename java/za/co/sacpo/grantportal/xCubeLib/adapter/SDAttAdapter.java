package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SDAttObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SAttDA;
import za.co.sacpo.grantportal.xCubeStudent.attendance.SAttVMCommentA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SDAttAdapter extends RecyclerView.Adapter<SDAttAdapter.Holder> {
    private List<SDAttObj.Item> aObjList;
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

    public SDAttAdapter(List<SDAttObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
                if (!res.isClosed()) {
                    res.close();
                    dbSetaObj.close();
                }
            }
        }
        return ValueLabel;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.att_monthly_row, parent, false);
        return new Holder(itemView);
    }
    public void goToAttendance(int aId2, Context context){
        Bundle inputUri = new Bundle();
        String dateInput = String.valueOf(aId2);
        inputUri.putString("date_input", dateInput);
        inputUri.putString("generator", "S109");
        Intent intent = new Intent(context, SAttDA.class);
        intent.putExtras(inputUri);
        context.startActivity(intent);
    }
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
           // BColor = res.getColor(R.color.row_head_1);
            BColor = res.getColor(baseAPC.getTextcolorResourceId("row_head"));

            holder.txtSickLeave.setTextColor(res.getColor(R.color.white));
            holder.txtAnnualLeave.setTextColor(res.getColor(R.color.white));
            holder.txtOPaidLeave.setTextColor(res.getColor(R.color.white));
            holder.txtUnpaidLeave.setTextColor(res.getColor(R.color.white));
            holder.txtSA.setTextColor(res.getColor(R.color.white));
            holder.txtAcSC.setTextColor(res.getColor(R.color.white));
            holder.txtAcD.setTextColor(res.getColor(R.color.white));
            holder.txtMonth.setTextColor(res.getColor(R.color.white));
            holder.txtYear.setTextColor(res.getColor(R.color.white));
            holder.txtCount.setTextColor(res.getColor(R.color.white));

            holder.txtSickLeave.setTypeface(holder.txtSickLeave.getTypeface(), Typeface.BOLD);
            holder.txtAnnualLeave.setTypeface(holder.txtAnnualLeave.getTypeface(), Typeface.BOLD);
            holder.txtOPaidLeave.setTypeface(holder.txtOPaidLeave.getTypeface(), Typeface.BOLD);
            holder.txtUnpaidLeave.setTypeface(holder.txtUnpaidLeave.getTypeface(), Typeface.BOLD);
            holder.txtSA.setTypeface(holder.txtSA.getTypeface(), Typeface.BOLD);
            holder.txtAcSC.setTypeface(holder.txtAcSC.getTypeface(), Typeface.BOLD);
            holder.txtAcD.setTypeface(holder.txtAcD.getTypeface(), Typeface.BOLD);
            holder.txtMonth.setTypeface(holder.txtMonth.getTypeface(), Typeface.BOLD);
            holder.txtYear.setTypeface(holder.txtYear.getTypeface(), Typeface.BOLD);
            holder.txtCount.setTypeface(holder.txtCount.getTypeface(), Typeface.BOLD);

            holder.txtSickLeave.setBackgroundColor(BColor);
            holder.txtAnnualLeave.setBackgroundColor(BColor);
            holder.txtOPaidLeave.setBackgroundColor(BColor);
            holder.txtUnpaidLeave.setBackgroundColor(BColor);
            holder.txtSA.setBackgroundColor(BColor);
            holder.txtAcSC.setBackgroundColor(BColor);
            holder.txtAcD.setBackgroundColor(BColor);
            holder.txtMonth.setBackgroundColor(BColor);
            holder.txtYear.setBackgroundColor(BColor);
            holder.txtCount.setBackgroundColor(BColor);
        }
        else {
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
            int BRColor = res.getColor(R.color.infoR);
            holder.txtMonth.setBackgroundColor(BColor);
            holder.txtYear.setBackgroundColor(BColor);
            holder.linearLActionD.setBackgroundColor(BColor);
            holder.linearLAction.setBackgroundColor(BColor);
            holder.txtCount.setBackgroundColor(BColor);
            holder.txtSickLeave.setBackgroundColor(BColor);
            holder.txtAnnualLeave.setBackgroundColor(BColor);
            holder.txtOPaidLeave.setBackgroundColor(BColor);
            holder.txtUnpaidLeave.setBackgroundColor(BColor);

            if(aObjList.get(holder.getAdapterPosition()).aSupervisorStatus10.equalsIgnoreCase("PENDING")){
                //Log.i("SDAttAdapter", "PENDING" + aObjList.get(holder.getAdapterPosition()).aSupervisorStatus10);
                holder.txtSA.setBackgroundColor(BRColor);
                holder.txtSA.setTextColor(res.getColor(R.color.white));
            }
            else{
                holder.txtSA.setBackgroundColor(BColor);
            }
            Labels = this.getLabelFromDb("b_S109_s_c", R.string.b_S109_s_c);
            holder.btnASC.setText(Labels);
            if(position!=0){
                int supervisorComment = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aCommentLink14);
                if(supervisorComment>0){
                    holder.btnASC.setVisibility(View.VISIBLE);
                    holder.txtAcSC.setVisibility(View.GONE);
                }
                else{
                    holder.btnASC.setVisibility(View.GONE);
                    holder.txtAcSC.setVisibility(View.VISIBLE);
                }
            }
            Labels = this.getLabelFromDb("b_S109_download", R.string.b_S109_download);
            if(position!=0){
                int downloadLink = Integer.parseInt(aObjList.get(holder.getAdapterPosition()).aDownloadLink13);
                if(downloadLink>0){
                    holder.txtAcD.setVisibility(View.GONE);
                    holder.btnAcD.setVisibility(View.VISIBLE);
                }
                else{
                    holder.txtAcD.setVisibility(View.VISIBLE);
                    holder.btnAcD.setVisibility(View.GONE);
                }
            }
            holder.btnAcD.setText(Labels);

            holder.txtAcSC.setBackgroundColor(BColor);
            holder.txtAcD.setBackgroundColor(BColor);
            holder.txtCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAttendance(holder.hItem.aId2, v.getContext());
                }
            });
            holder.txtSickLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAttendance(holder.hItem.aId2, v.getContext());
                }
            });
            holder.txtAnnualLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAttendance(holder.hItem.aId2, v.getContext());
                }
            });
            holder.txtOPaidLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAttendance(holder.hItem.aId2, v.getContext());
                }
            });
            holder.txtUnpaidLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToAttendance(holder.hItem.aId2, v.getContext());
                }
            });
            if((holder.getAdapterPosition()%2)==0){
                holder.txtCount.setBackgroundResource(R.drawable.theme_view_underline_small_e);
                holder.txtSickLeave.setBackgroundResource(R.drawable.theme_view_underline_small_e);
                holder.txtAnnualLeave.setBackgroundResource(R.drawable.theme_view_underline_small_e);
                holder.txtOPaidLeave.setBackgroundResource(R.drawable.theme_view_underline_small_e);
                holder.txtUnpaidLeave.setBackgroundResource(R.drawable.theme_view_underline_small_e);
            }
            else{
                holder.txtCount.setBackgroundResource(R.drawable.theme_view_underline_small_o);
                holder.txtSickLeave.setBackgroundResource(R.drawable.theme_view_underline_small_o);
                holder.txtAnnualLeave.setBackgroundResource(R.drawable.theme_view_underline_small_o);
                holder.txtOPaidLeave.setBackgroundResource(R.drawable.theme_view_underline_small_o);
                holder.txtUnpaidLeave.setBackgroundResource(R.drawable.theme_view_underline_small_o);
            }
        }
        holder.txtMonth.setText(aObjList.get(holder.getAdapterPosition()).aMonth3);

        holder.txtYear.setText(aObjList.get(holder.getAdapterPosition()).aYear4);
        holder.txtCount.setText(aObjList.get(holder.getAdapterPosition()).aCount5);
        holder.txtSickLeave.setText(aObjList.get(holder.getAdapterPosition()).aSLeaveCount7);
        holder.txtAnnualLeave.setText(aObjList.get(holder.getAdapterPosition()).aALeaveCount6);
        holder.txtOPaidLeave.setText(aObjList.get(holder.getAdapterPosition()).aOPaidLeaveCount8);
        holder.txtUnpaidLeave.setText(aObjList.get(holder.getAdapterPosition()).aUPaidLeaveCount9);
        holder.txtSA.setText(aObjList.get(holder.getAdapterPosition()).aSupervisorStatus10);
        holder.txtAcSC.setText(aObjList.get(holder.getAdapterPosition()).aSupervisorComment11);
        holder.txtAcD.setText(aObjList.get(holder.getAdapterPosition()).aDownloadReg12);
        holder.btnAcD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                String downloadPath =  holder.hItem.aDownloadReg12;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                URL url = null;
                try {
                    url = new URL(URLHelper.DOMAIN_URL+""+downloadPath);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                context.startActivity(intent);
            }
        });
        holder.btnASC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bundle inputUri = new Bundle();
            String month = holder.hItem.aMonth3;
            String year = holder.hItem.aYear4;
            String comment = holder.hItem.aSupervisorComment11;
            inputUri.putString("month", month);
            inputUri.putString("year", year);
            inputUri.putString("comment", comment);
            inputUri.putString("generator", "S109");
            Context context = v.getContext();
            Intent intent = new Intent(context, SAttVMCommentA.class);
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

    public class Holder extends RecyclerView.ViewHolder {
        public final View hView;
        public SDAttObj.Item hItem;
        public final TextView txtCount,txtYear,txtMonth,txtSickLeave,txtAnnualLeave,txtOPaidLeave,txtUnpaidLeave,txtSA,txtAcSC,txtAcD;
        public final LinearLayout tRow,linearLAction,linearLActionD;
        public final Button btnAcD,btnASC;
        public Holder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow = (LinearLayout) itemView.findViewById(R.id.tRow);
            txtMonth= (TextView) itemView.findViewById(R.id.lblMonth);
            txtYear= (TextView) itemView.findViewById(R.id.lblYear);
            txtCount= (TextView) itemView.findViewById(R.id.lblAttendanceDays);
            linearLActionD = (LinearLayout) itemView.findViewById(R.id.linearLActionD);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            txtSickLeave= (TextView) itemView.findViewById(R.id.lblSickLeave);
            txtAnnualLeave= (TextView) itemView.findViewById(R.id.lblAnnualLeave);
            txtOPaidLeave= (TextView) itemView.findViewById(R.id.lblOtherPaidLeave);
            txtUnpaidLeave= (TextView) itemView.findViewById(R.id.lblUnpaidLeave);
            txtSA= (TextView) itemView.findViewById(R.id.lblSupervisorApproval);
            txtAcSC= (TextView) itemView.findViewById(R.id.lblActionSC);
            btnASC= (Button) itemView.findViewById(R.id.btnActionSC);
            txtAcD= (TextView) itemView.findViewById(R.id.lblActionD);
            btnAcD= (Button) itemView.findViewById(R.id.btnActionD);


        }
    }
}
