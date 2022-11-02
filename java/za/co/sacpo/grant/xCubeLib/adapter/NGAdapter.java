package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.NGObj;
import za.co.sacpo.grant.xCubeStudent.alerts.SAlertsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class NGAdapter extends RecyclerView.Adapter<NGAdapter.NGHolder> {
    private List<NGObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
//Todo: Table Width Problem
    public NGAdapter(List<NGObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall,String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }
    @Override
    public NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ng_row, parent, false);
        return new NGHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NGHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());

        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNGTitle3);
        holder.lblCount.setText(aObjList.get(holder.getAdapterPosition()).aNGCount4);
        holder.lblCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String group_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("group_id", group_id);
                inputUri.putString("generator", CAId);
                Context context = v.getContext();
                Intent intent = new Intent(context, SAlertsA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
        holder.lblTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String group_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("group_id", group_id);
                inputUri.putString("generator", CAId);
                Context context = v.getContext();
                Intent intent = new Intent(context, SAlertsA.class);
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

    public class NGHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public NGObj.Item hItem;
        public final TextView lblTitle,lblCount;
        public NGHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblTitle= (TextView) itemView.findViewById(R.id.lblTitle);
            lblCount= (TextView) itemView.findViewById(R.id.lblCount);
        }
    }
}
