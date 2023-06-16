package za.co.sacpo.grantportal.xCubeLib.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.dataObj.AnnounceObj;
import za.co.sacpo.grantportal.xCubeStudent.announcment.SAnnouncementDetailsA;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.AHolder> {
    private List<AnnounceObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;

    //Todo: Table Width Problem
    public AnnounceAdapter(List<AnnounceObj.Item> aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }

    @Override
    public AHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_row, parent, false);
        return new AHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AHolder holder, final int position) {
        Resources res = holder.itemView.getContext().getResources();
        holder.hItem = aObjList.get(holder.getAdapterPosition());

        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNTitle3);


        holder.lblTitle.setText(aObjList.get(holder.getAdapterPosition()).aNTitle3);


        String origStr = aObjList.get(holder.getAdapterPosition()).aSData4;

        holder.lblDate.setText(origStr);


        holder.lblCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle inputUri = new Bundle();
                String m_u_id = String.valueOf(holder.hItem.aId2);
                inputUri.putString("m_u_id", m_u_id);

                Context context = v.getContext();
                Intent intent = new Intent(context, SAnnouncementDetailsA.class);
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

    public class AHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public AnnounceObj.Item hItem;
        public final TextView lblTitle, lblDate;
        public final CardView lblCardView;
        public final LinearLayout lblHeadView, lblBodyView, lblFooterView;


        public AHolder(View itemView) {
            super(itemView);
            hView = itemView;
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            lblDate = (TextView) itemView.findViewById(R.id.lblDate);
            lblCardView = (CardView) itemView.findViewById(R.id.cardView);
            lblHeadView = (LinearLayout) itemView.findViewById(R.id.head_container);
            lblBodyView = (LinearLayout) itemView.findViewById(R.id.body_container);
            lblFooterView = (LinearLayout) itemView.findViewById(R.id.footer_container);


        }
    }
}
