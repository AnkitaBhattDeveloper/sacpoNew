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
import za.co.sacpo.grant.xCubeLib.dataObj.TObj;
import za.co.sacpo.grant.xCubeStudent.queries.SQueriesDetailsA;

public class TGAAdapter  extends RecyclerView.Adapter<TGAAdapter.NGHolder> {
    private List<TObj.Item> aObjList;
    private String CAId;
    private Context baseActivityContext;
    private AppCompatActivity activityInCall;
    //Todo: Table Width Problem
    public TGAAdapter(List<TObj.Item > aObjList, Context baseActivityContext, AppCompatActivity activityInCall, String CAId) {
        this.aObjList = aObjList;
        this.baseActivityContext = baseActivityContext;
        this.activityInCall = activityInCall;
        this.CAId = CAId;
    }
    @Override
    public TGAAdapter.NGHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.t_row, parent, false);
        return new TGAAdapter.NGHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TGAAdapter.NGHolder holder, final int position) {
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