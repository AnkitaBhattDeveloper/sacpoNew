package za.co.sacpo.grant.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dataObj.MFeedbackObj;
import za.co.sacpo.grant.xCubeMentor.feedback.MEditFeedbackA;
import za.co.sacpo.grant.xCubeMentor.feedback.MDeleteFeedbackA;


/**
 * Created by xcube-06 on 7/8/18.
 */

public class MFeedbackAdapter extends RecyclerView.Adapter<MFeedbackAdapter.Holder> {
    private List<MFeedbackObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    boolean full;


    Bitmap bitmap;

    public MFeedbackAdapter(List<MFeedbackObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.m_supervisor_comments_row, parent, false);
        return new Holder(itemView);
    }
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem=aObjList.get(holder.getAdapterPosition());
        String feedback = aObjList.get(holder.getAdapterPosition()).feedback3;
        holder.txtFeedback.setText(feedback);
        holder.txtDate.setText(aObjList.get(holder.getAdapterPosition()).date5);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String c_u_r_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("c_u_r_id", c_u_r_id);
                inputUri.putString("generator", "337");
                Context context = v.getContext();
                String feedback = String.valueOf(holder.hItem.feedback3);
                inputUri.putString("feedback", feedback);
                Intent intent = new Intent(context, MEditFeedbackA.class);
                intent.putExtras(inputUri);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String c_u_r_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("generator", "337");
                inputUri.putString("c_u_r_id", c_u_r_id);
                String feedback = String.valueOf(holder.hItem.feedback3);
                inputUri.putString("feedback", feedback);
                String date = String.valueOf(holder.hItem.date5);
                inputUri.putString("date", date);

                Context context = v.getContext();
                Intent intent = new Intent(context, MDeleteFeedbackA.class);
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
        public MFeedbackObj.Item hItem;
        public final TextView txtDate,txtFeedback;
        public final CardView lblCardView;
        public final Button  btnEdit,btnDelete;
        public Holder(View itemView) {
            super(itemView);
            hView = itemView;
            txtFeedback= (TextView) itemView.findViewById(R.id.txtFeedback);
            txtDate= (TextView) itemView.findViewById(R.id.txtDate);
            lblCardView = (CardView)itemView.findViewById(R.id.cardView);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}