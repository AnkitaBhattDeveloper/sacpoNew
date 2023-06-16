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
import za.co.sacpo.grantportal.xCubeLib.dataObj.SStipendObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeStudent.stipends.SStipendDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SStipendAdapter extends RecyclerView.Adapter<SStipendAdapter.StipendHolder> {
    private List<SStipendObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public SStipendAdapter(List<SStipendObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public StipendHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stipend_row_list, parent, false);
        return new StipendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StipendHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblStipendDate.setTextColor(res.getColor(R.color.white));
            holder.lblStipendDate.setTypeface(holder.lblStipendDate.getTypeface(), Typeface.BOLD);
            holder.lblStipendMonth.setTextColor(res.getColor(R.color.white));
            holder.lblStipendMonth.setTypeface(holder.lblStipendMonth.getTypeface(), Typeface.BOLD);
            holder.lblStipendYear.setTextColor(res.getColor(R.color.white));
            holder.lblStipendYear.setTypeface(holder.lblStipendYear.getTypeface(), Typeface.BOLD);
            holder.lblTotalStipend.setTextColor(res.getColor(R.color.white));
            holder.lblTotalStipend.setTypeface(holder.lblTotalStipend.getTypeface(), Typeface.BOLD);
            Labels =this.getLabelFromDb("l_609_SStipend_details",R.string.l_609_SStipend_details);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction_StipendDetails.setVisibility(View.GONE);

        }
        else{
            holder.lblAction.setVisibility(View.GONE);
            holder.btnAction_StipendDetails.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.lblStipendDate.setText(aObjList.get(holder.getAdapterPosition()).sStDate3);
        holder.lblStipendDate.setBackgroundColor(BColor);
        holder.btnAction_StipendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String stipend_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("stipend_id", stipend_id);
                inputUri.putString("date_input", "");
                inputUri.putString("generator", "sStipendA");
                Context context = v.getContext();
                Intent intent = new Intent(context, SStipendDetailsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_609_SStipend_Btn_details",R.string.l_609_SStipend_Btn_details);
        holder.btnAction_StipendDetails.setText(Labels);
        holder.lblStipendMonth.setText(aObjList.get(holder.getAdapterPosition()).sStMonth4);
        holder.lblStipendMonth.setBackgroundColor(BColor);
        holder.lblStipendYear.setText(aObjList.get(holder.getAdapterPosition()).sStYear5);
        holder.lblStipendYear.setBackgroundColor(BColor);
        holder.lblTotalStipend.setText(aObjList.get(holder.getAdapterPosition()).sStTotalStipend6);
        holder.lblTotalStipend.setBackgroundColor(BColor);

        holder.lblStipendDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sStDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStipendDate,sToolTip,4);
            }
        });
        holder.lblStipendMonth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sStMonth4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStipendMonth,sToolTip,4);
            }
        });
        holder.lblStipendYear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sStYear5;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStipendYear,sToolTip,4);
            }
        });
        holder.lblTotalStipend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).sStTotalStipend6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblTotalStipend,sToolTip,4);
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
       public class StipendHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SStipendObj.Item hItem;
        public final TableRow Stipend_Row;
        public final TextView lblStipendDate,lblStipendMonth,lblStipendYear,lblTotalStipend,lblAction;
        public final Button btnAction_StipendDetails;
        public final LinearLayout linearLAction;
        public StipendHolder(View itemView) {
            super(itemView);
            hView = itemView;
            Stipend_Row = (TableRow) itemView.findViewById(R.id.Stipend_Row);

            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);

            lblStipendDate = (TextView) itemView.findViewById(R.id.lblStipendDate);
            lblStipendMonth = (TextView) itemView.findViewById(R.id.lblStipendMonth);
            lblStipendYear = (TextView) itemView.findViewById(R.id.lblStipendYear);
            lblTotalStipend = (TextView) itemView.findViewById(R.id.lblTotalStipend);
            lblAction = (TextView) itemView.findViewById(R.id.lblAction);
            btnAction_StipendDetails = (Button) itemView.findViewById(R.id.btnAction_StipendDetails);

        }
    }
}
