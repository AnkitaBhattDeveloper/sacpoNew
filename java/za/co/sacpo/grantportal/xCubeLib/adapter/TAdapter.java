package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.dataObj.TObj;
import za.co.sacpo.grantportal.xCubeMentor.queries.MQueriesDetailsA;
import za.co.sacpo.grantportal.xCubeStudent.queries.SQueriesDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class TAdapter extends RecyclerView.Adapter<TAdapter.NGHolder> {
    private List<TObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    //Todo: Table Width Problem
    public TAdapter(List<TObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }
    @Override
    public NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.t_row, parent, false);
        return new NGHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NGHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());

        holder.lblRef.setText(aObjList.get(holder.getAdapterPosition()).aTRef4);
        String DATA = aObjList.get(holder.getAdapterPosition()).aTTitle3;
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(DATA, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(DATA);
        }
        holder.lblTitle.setText(Message);
        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).aTDate5);
        holder.lblStatus.setText(aObjList.get(holder.getAdapterPosition()).aTStatusText6);
        if(aObjList.get(holder.getAdapterPosition()).aTStatus7.equals("1")){
            holder.lblHeadView.setBackgroundColor(res.getColor(R.color.background_unread));
            holder.lblTitle.setTextColor(res.getColor(R.color.colorPrimaryDark));
            holder.lblBodyView.setBackgroundColor(res.getColor(R.color.background_read));
            holder.lblFooterView.setBackgroundColor(res.getColor(R.color.background_unread));
        }
        else{
            holder.lblHeadView.setBackgroundColor(res.getColor(R.color.background_read));
            holder.lblBodyView.setBackgroundColor(res.getColor(R.color.background_unread));
            holder.lblFooterView.setBackgroundColor(res.getColor(R.color.background_read));
        }
        if(CAId.equals("111")) {
            holder.lblCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String t_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("t_id", t_id);
                    String group_id = String.valueOf(holder.hItem.group_id8);
                    inputUri.putString("generator", CAId);
                    inputUri.putString("group_id", group_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MQueriesDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }
        if(CAId.equals("317")) {
            holder.lblCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String t_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("t_id", t_id);
                    String group_id = String.valueOf(holder.hItem.group_id8);
                    inputUri.putString("generator", CAId);
                    inputUri.putString("group_id", group_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MQueriesDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }

        if(CAId.equals("188")) {
            holder.lblCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String t_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("t_id", t_id);
                    String group_id = String.valueOf(holder.hItem.group_id8);
                    inputUri.putString("generator", CAId);
                    inputUri.putString("group_id", group_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SQueriesDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }
        if(CAId.equals("207")) {
            holder.lblCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle inputUri = new Bundle();
                    String t_id = String.valueOf(holder.hItem.aId2);
                    inputUri.putString("t_id", t_id);
                    String group_id = String.valueOf(holder.hItem.group_id8);
                    inputUri.putString("generator", CAId);
                    inputUri.putString("group_id", group_id);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SQueriesDetailsA.class);
                    intent.putExtras(inputUri);
                    context.startActivity(intent);
                }
            });
        }




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
        public TObj.Item hItem;
        public final TextView lblTitle,lblDate,lblRef,lblStatus;
        public final CardView lblCardView;
        public final LinearLayout lblHeadView,lblBodyView,lblFooterView;

        public NGHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblRef= (TextView) itemView.findViewById(R.id.lblRef);
            lblTitle= (TextView) itemView.findViewById(R.id.lblTitle);
            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            lblStatus= (TextView) itemView.findViewById(R.id.lblStatus);
            lblCardView = (CardView)itemView.findViewById(R.id.cardView);
            lblHeadView = (LinearLayout)itemView.findViewById(R.id.head_container);
            lblBodyView = (LinearLayout)itemView.findViewById(R.id.body_container);
            lblFooterView = (LinearLayout)itemView.findViewById(R.id.footer_container);
        }
    }
}