package za.co.sacpo.grant.xCubeLib.adapter;

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

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.NObj;
import za.co.sacpo.grant.xCubeStudent.alerts.SAlertDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class NAdapter extends RecyclerView.Adapter<NAdapter.NGHolder> {
    private List<NObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
//Todo: Table Width Problem
    public NAdapter(List<NObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }
    @Override
    public NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.n_row, parent, false);
        return new NGHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NGHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());

        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNTitle3);
        String DATA = aObjList.get(holder.getAdapterPosition()).aNData4;
        Spanned Message;
        if (Build.VERSION.SDK_INT >= 24) {
            Message = Html.fromHtml(DATA, Html.FROM_HTML_MODE_LEGACY);
        }
        else {
            Message = Html.fromHtml(DATA);
        }
        holder.lblData.setText(Message);
        holder.lblDate.setText(aObjList.get(holder.getAdapterPosition()).aNDate5);
        if(aObjList.get(holder.getAdapterPosition()).aNReadStatus6.equals("1")){
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
        holder.lblCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String n_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("n_id", n_id);
                String group_id = String.valueOf(holder.hItem.group_id7);
                inputUri.putString("generator", CAId);
                inputUri.putString("group_id", group_id);
                Context context = v.getContext();
                Intent intent = new Intent(context, SAlertDetailsA.class);
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
        public NObj.Item hItem;
        public final TextView lblTitle,lblDate,lblData;
        public final CardView lblCardView;
        public final LinearLayout lblHeadView,lblBodyView,lblFooterView;

        public NGHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblTitle= (TextView) itemView.findViewById(R.id.lblTitle);
            lblDate= (TextView) itemView.findViewById(R.id.lblDate);
            lblData= (TextView) itemView.findViewById(R.id.lblData);
            lblCardView = (CardView)itemView.findViewById(R.id.cardView);
            lblHeadView = (LinearLayout)itemView.findViewById(R.id.head_container);
            lblBodyView = (LinearLayout)itemView.findViewById(R.id.body_container);
            lblFooterView = (LinearLayout)itemView.findViewById(R.id.footer_container);




        }
    }
}
