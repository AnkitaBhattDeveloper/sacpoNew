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
import za.co.sacpo.grantportal.xCubeLib.dataObj.SGrantObj;
import za.co.sacpo.grantportal.xCubeLib.db.DbHelper;
import za.co.sacpo.grantportal.xCubeStudent.grant.SGrantDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class SGrantAdapter extends RecyclerView.Adapter<SGrantAdapter.GrantHolder> {
    private List<SGrantObj.Item> aObjList;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    protected DbHelper dbSetaObj;
    String Labels;

    public SGrantAdapter(List<SGrantObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall) {
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
    public GrantHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.att_row, parent, false);
        return new GrantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GrantHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        int BColor;
        if(holder.getAdapterPosition()==0){
            BColor = res.getColor(R.color.row_head_1);
            holder.lblLoginDate.setTextColor(res.getColor(R.color.white));
            holder.lblLoginDate.setTypeface(holder.lblLoginDate.getTypeface(), Typeface.BOLD);


            Labels =this.getLabelFromDb("l_607_SGrant_action",R.string.l_607_SGrant_action);
            holder.lblAction.setText(Labels);
            holder.lblAction.setTextColor(res.getColor(R.color.white));
            holder.lblAction.setVisibility(View.VISIBLE);
            holder.btnAction.setVisibility(View.GONE);

            holder.lblAction.setTypeface(holder.lblAction.getTypeface(), Typeface.BOLD);
            holder.lblDay.setTextColor(res.getColor(R.color.white));
            holder.lblDay.setTypeface(holder.lblDay.getTypeface(), Typeface.BOLD);
            holder.lblLoginTime.setTextColor(res.getColor(R.color.white));
            holder.lblLoginTime.setTypeface(holder.lblLoginTime.getTypeface(), Typeface.BOLD);
            holder.lblLogoutTime.setTextColor(res.getColor(R.color.white));
            holder.lblLogoutTime.setTypeface(holder.lblLogoutTime.getTypeface(), Typeface.BOLD);
            holder.lblSpentTime.setTextColor(res.getColor(R.color.white));
            holder.lblSpentTime.setTypeface(holder.lblSpentTime.getTypeface(), Typeface.BOLD);
            holder.lblStatus.setTextColor(res.getColor(R.color.white));
            holder.lblStatus.setTypeface(holder.lblStatus.getTypeface(), Typeface.BOLD);
        }
        else{
            holder.lblAction.setVisibility(View.GONE);
            holder.btnAction.setVisibility(View.VISIBLE);
            if((holder.getAdapterPosition()%2)==0){
                BColor=res.getColor(R.color.row_even);
            }
            else{
                BColor=res.getColor(R.color.row_odd);
            }
        }
        holder.lblLoginDate.setText(aObjList.get(holder.getAdapterPosition()).aLiDate3);
        holder.lblLoginDate.setBackgroundColor(BColor);
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                inputUri.putInt("att_id", holder.hItem.aId2);
                Context context = v.getContext();
                Intent intent = new Intent(context, SGrantDetailsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });

        holder.linearLAction.setBackgroundColor(BColor);
        Labels =this.getLabelFromDb("l_607_SGrant_details",R.string.l_607_SGrant_details);
        holder.btnAction.setText(Labels);

        holder.lblDay.setText(aObjList.get(holder.getAdapterPosition()).aDay9);
        holder.lblDay.setBackgroundColor(BColor);
        holder.lblLoginTime.setText(aObjList.get(holder.getAdapterPosition()).aLiTime4);
        holder.lblLoginTime.setBackgroundColor(BColor);
        holder.lblLogoutTime.setText(aObjList.get(holder.getAdapterPosition()).aLoTime6);
        holder.lblLogoutTime.setBackgroundColor(BColor);
        holder.lblSpentTime.setText(aObjList.get(holder.getAdapterPosition()).aTimeSpent7);
        holder.lblSpentTime.setBackgroundColor(BColor);
        holder.lblStatus.setText(aObjList.get(holder.getAdapterPosition()).aStatus8);
        holder.lblStatus.setBackgroundColor(BColor);

        holder.lblLoginDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLiDate3;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginDate,sToolTip,4);
            }
        });
        holder.lblDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aDay9;
                ((BaseAPC)activityInCall).showTooltip(holder.lblDay,sToolTip,4);
            }
        });
        holder.lblLogoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLoTime6;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLogoutTime,sToolTip,4);
            }
        });
        holder.lblSpentTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aTimeSpent7;
                ((BaseAPC)activityInCall).showTooltip(holder.lblSpentTime,sToolTip,4);
            }
        });
        holder.lblLoginTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aLiTime4;
                ((BaseAPC)activityInCall).showTooltip(holder.lblLoginTime,sToolTip,4);
            }
        });
        holder.lblStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sToolTip=aObjList.get(holder.getAdapterPosition()).aStatus8;
                ((BaseAPC)activityInCall).showTooltip(holder.lblStatus,sToolTip,4);
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

    public class GrantHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public SGrantObj.Item hItem;
        public final TableRow tRow;
        public final TextView lblLoginDate,lblAction,lblDay,lblLoginTime,lblLogoutTime,lblSpentTime,lblStatus;
        public final Button btnAction;
        public final LinearLayout linearLAction;
        public GrantHolder(View itemView) {
            super(itemView);
            hView = itemView;
            tRow= (TableRow) itemView.findViewById(R.id.tRow);
            linearLAction = (LinearLayout) itemView.findViewById(R.id.linearLAction);
            lblLoginDate= (TextView) itemView.findViewById(R.id.lblLoginDate);
            btnAction= (Button) itemView.findViewById(R.id.btnAction);
            lblAction= (TextView) itemView.findViewById(R.id.lblAction);
            lblDay= (TextView) itemView.findViewById(R.id.lblDay);
            lblLoginTime= (TextView) itemView.findViewById(R.id.lblLoginTime);
            lblLogoutTime= (TextView) itemView.findViewById(R.id.lblLogoutTime);
            lblSpentTime= (TextView) itemView.findViewById(R.id.lblSpentTime);
            lblStatus= (TextView) itemView.findViewById(R.id.lblStatus);
        }
    }
}
