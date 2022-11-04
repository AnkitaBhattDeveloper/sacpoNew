package za.co.sacpo.grant.xCubeLib.adapter;

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

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPC;
import za.co.sacpo.grant.xCubeLib.dataObj.MPendingClaimObj;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeMentor.claims.MClaimAttApproveA;
import za.co.sacpo.grant.xCubeMentor.claims.MPendingClaimsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MPendingClaimAdapter extends RecyclerView.Adapter<MPendingClaimAdapter.ClaimHolder> {

    private List<MPendingClaimObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;
    MPendingClaimsA baseActivity;
    String m_student_name;


    public MPendingClaimAdapter(List<MPendingClaimObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, MPendingClaimsA baseActivity) {
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

    @Override
    public ClaimHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pending_claim, parent, false);
        return new ClaimHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClaimHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);


            holder.lblYear.setTextColor(res.getColor(R.color.white));
            holder.lblYear.setTypeface(holder.lblYear.getTypeface(), Typeface.BOLD);
            holder.lblMonth.setTextColor(res.getColor(R.color.white));
            holder.lblMonth.setTypeface(holder.lblMonth.getTypeface(), Typeface.BOLD);
            holder.lblAmount.setTextColor(res.getColor(R.color.white));
            holder.lblAmount.setTypeface(holder.lblAmount.getTypeface(), Typeface.BOLD);
            holder.lblLearnerName.setTextColor(res.getColor(R.color.white));
            holder.lblLearnerName.setTypeface(holder.lblLearnerName.getTypeface(), Typeface.BOLD);


            Labels =this.getLabelFromDb("l_340_approve_stipend",R.string.l_340_approve_stipend);
            holder.lblApprovedStipend.setText(Labels);
            holder.lblApprovedStipend.setTextColor(res.getColor(R.color.white));
            holder.lblApprovedStipend.setTypeface(holder.lblApprovedStipend.getTypeface(), Typeface.BOLD);
            holder.lblApprovedStipend.setVisibility(View.VISIBLE);
            holder.btnApprovedStipend.setVisibility(View.GONE);



        }
        else{



            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }




            if (aObjList.get(holder.getAdapterPosition()).PcApproval8.equals("1")){

                Labels =this.getLabelFromDb("l_btn_pending_claim",R.string.l_btn_pending_claim);
                holder.btnApprovedStipend.setText(Labels);
                holder.btnApprovedStipend.setTextColor(res.getColor(R.color.row_link));
                holder.btnApprovedStipend.setTypeface(holder.btnApprovedStipend.getTypeface(), Typeface.BOLD);
                holder.btnApprovedStipend.setVisibility(View.VISIBLE);
                holder.lblApprovedStipend.setVisibility(View.GONE);



            }else{

                Labels =this.getLabelFromDb("l_btn_approved_stipend",R.string.l_btn_approved_stipend);
                holder.lblApprovedStipend.setText(Labels);
                holder.lblApprovedStipend.setVisibility(View.VISIBLE);
                holder.btnApprovedStipend.setVisibility(View.GONE);
            }
        }

        holder.btnApprovedStipend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Bundle inputUri = new Bundle();
            String student_id = baseActivity.getStudentId();
            inputUri.putString("student_id", student_id);
            String month_year = String.valueOf(holder.hItem.month_year3);
            inputUri.putString("month_year", month_year);
            String stipend_id =  String.valueOf(holder.hItem.aId2);
            inputUri.putString("stipend_id", stipend_id);
            String mName = String.valueOf(holder.hItem.PcLearnerName7);
            inputUri.putString("m_student_name", mName);
            Context context = view.getContext();
            Intent intent = new Intent(context, MClaimAttApproveA.class);
            intent.putExtras(inputUri);
            context.startActivity(intent);
                    /*TODO::ON HOLD */

                }


        });

        holder.linearLAction1.setBackgroundColor(BColor);

        holder.lblYear.setText(aObjList.get(holder.getAdapterPosition()).PcYear4);
        holder.lblYear.setBackgroundColor(BColor);
        holder.lblMonth.setText(aObjList.get(holder.getAdapterPosition()).PcMonth5);
        holder.lblMonth.setBackgroundColor(BColor);
        holder.lblAmount.setText(aObjList.get(holder.getAdapterPosition()).PcAmount6);
        holder.lblAmount.setBackgroundColor(BColor);
        holder.lblLearnerName.setText(aObjList.get(holder.getAdapterPosition()).PcLearnerName7);
        holder.lblLearnerName.setBackgroundColor(BColor);

        holder.lblYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).PcYear4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblYear,sToolTip,4);
            }
        });
        holder.lblMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).PcMonth5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblMonth,sToolTip,4);
            }
        });
        holder.lblAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).PcAmount6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAmount,sToolTip,4);
            }
        });
        holder.lblLearnerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).PcLearnerName7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblAmount,sToolTip,4);
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
        public MPendingClaimObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblYear,lblMonth,lblAmount,lblLearnerName,lblApprovedStipend;

        public final Button btnApprovedStipend;
        public final LinearLayout linearLAction1;

        public ClaimHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.claim_Row);
            linearLAction1 = (LinearLayout) itemView.findViewById(R.id.linearLAction1);

            lblYear = (TextView) itemView.findViewById(R.id.lblYear);
            lblMonth = (TextView) itemView.findViewById(R.id.lblMonth);
            lblAmount = (TextView) itemView.findViewById(R.id.lblAmount);
            lblLearnerName = (TextView) itemView.findViewById(R.id.lblLearnerName);
            lblApprovedStipend = (TextView) itemView.findViewById(R.id.lblApprovedStipend);
            btnApprovedStipend = (Button) itemView.findViewById(R.id.btnApprovedStipend);

        }
    }
}
